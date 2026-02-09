## README: Java Thread Lifecycle (JVM → OS → CPU)

This is a **README-style guide** that builds an end-to-end mental model of Java threads from the **Java API** down to the **JVM**, the **OS kernel**, and the **CPU**.

### Who this is for

- Backend engineers working with **Spring Boot / WebFlux**
- Anyone debugging production issues (thread dumps, latency spikes, deadlocks)
- Anyone who wants a **systems-level** understanding (not just `Thread.State`)

### How to use this document

- **If you’re new**: read in order (Sections 1 → 10).
- **If you’re debugging**: jump to [Practical Debugging](#8-practical-debugging-jstack-deadlocks-tips) and then reference:
  - [States](#3-java-thread-states-newrunnableblockedwaitingtimed_waitingterminated)
  - [State transitions](#4-state-transitions-map--meaning)
  - [Layer map](#2-master-layer-map-java--jvm--os--cpu)

### What you’ll be able to do after reading

- Explain what actually happens when you call `start()`
- Interpret `RUNNABLE` vs `BLOCKED` vs `WAITING` in a real `jstack`
- Understand where CPU time goes (scheduler, context switching, blocking I/O)
- Avoid common concurrency mistakes (locks, wait/notify misuse)

---

## Table of Contents

- [0. Quick glossary](#0-quick-glossary)
- [1. Big Picture (TL;DR)](#1-big-picture-tldr)
- [2. Master Layer Map (Java → JVM → OS → CPU)](#2-master-layer-map-java--jvm--os--cpu)
- [3. Java Thread States (NEW/RUNNABLE/BLOCKED/WAITING/TIMED_WAITING/TERMINATED)](#3-java-thread-states-newrunnableblockedwaitingtimed_waitingterminated)
- [4. State Transitions (Map + Meaning)](#4-state-transitions-map--meaning)
- [5. Chronological Lifecycle (Phases 1–8)](#5-chronological-lifecycle-phases-18)
- [6. Thread Representation by Layer (What It Is / Stores / Why)](#6-thread-representation-by-layer-what-it-is--stores--why)
- [7. JVM + OS Deep Dive (Stacks, Scheduling, Memory, Sync)](#7-jvm--os-deep-dive-stacks-scheduling-memory-sync)
- [8. Practical Debugging (jstack, deadlocks, tips)](#8-practical-debugging-jstack-deadlocks-tips)
- [9. Key Takeaways (Memorize)](#9-key-takeaways-memorize)
- [10. One-Line Master Summary](#10-one-line-master-summary)

---

## 0. Quick glossary

- **Java thread object**: the `java.lang.Thread` instance on the heap (metadata + API).
- **Platform thread**: a Java thread that maps 1:1 to an OS kernel thread (common default model).
- **Kernel thread**: OS scheduler entity (Linux `task_struct`) that runs on CPU cores.
- **Event loop**: a small set of threads that multiplex many connections using readiness events (e.g., Netty).
- **Context switch**: OS saving/restoring registers/IP/SP to switch CPU execution between threads.
- **Safepoint**: JVM point where threads can be paused for GC / deoptimization.

---

## 1. Big Picture (TL;DR)

- **A `Thread` object is not execution**. It’s metadata + control methods.
- **`start()` is the “creation of execution” moment**: it creates the JVM/OS execution context.
- **RUNNABLE does not mean “running”**. It means “eligible to run” (ready or actually running).
- **The OS scheduler** decides which thread gets CPU time and when context switches occur.
- **Each thread has its own stack + program counter state**, which allows it to pause/resume correctly.

---

## 2. Master Layer Map (Java → JVM → OS → CPU)

```
Java Heap
 └── java.lang.Thread
        ↓
JVM Native Memory
 ├── JavaThread
 ├── Java Stack (user stack)
 ├── Program Counter (PC)
 └── OSThread
        ↓
OS Kernel Memory
 ├── Kernel Thread (task_struct)
 ├── Kernel Stack
 └── Scheduler Metadata
        ↓
CPU Hardware
 ├── Registers
 ├── Instruction Pointer
 └── Caches
```

---

## 3. Java Thread States (NEW/RUNNABLE/BLOCKED/WAITING/TIMED_WAITING/TERMINATED)

Java exposes 6 thread states via `Thread.State`:

### NEW
- Thread object created but not started.
- `start()` not called yet.

### RUNNABLE
- Thread is ready to run or currently running.
- Includes “ready” and “running” (Java merges them).

### BLOCKED
- Waiting to acquire a monitor lock (`synchronized`).

### WAITING
- Waiting indefinitely for another thread/action:
  - `Object.wait()` (no timeout)
  - `Thread.join()` (no timeout)
  - `LockSupport.park()`

### TIMED_WAITING
- Waiting with a timeout:
  - `Thread.sleep(timeout)`
  - `Object.wait(timeout)`
  - `Thread.join(timeout)`
  - `LockSupport.parkNanos()` / `parkUntil()`

### TERMINATED
- `run()` finished (or died via uncaught exception).
- Cannot be restarted.

---

## 4. State Transitions (Map + Meaning)

### High-level transition map

```
NEW
 ↓ start()
RUNNABLE
 ↓ scheduled (OS decides)
RUNNING  (still reported as RUNNABLE in Java)
 ↓ lock contention (synchronized)
BLOCKED
 ↓ lock acquired
RUNNABLE
 ↓ wait()/join()/park()
WAITING
 ↓ notify()/unpark()/interrupt()
RUNNABLE
 ↓ sleep()/timed wait/join(timeout)
TIMED_WAITING
 ↓ timeout/notify/unpark/interrupt
RUNNABLE
 ↓ run() ends / uncaught exception
TERMINATED
```

### What Java cannot show you directly

- Java does not directly expose “READY” vs “RUNNING”.
- Java does not expose OS scheduler state; it only shows high-level categories.

---

## 5. Chronological Lifecycle (Phases 1–8)

This section answers: **what exists at each phase** (Java / JVM / OS / CPU).

### PHASE 1: Thread object creation (NEW)

- **Java level**

```java
Thread t = new Thread(runnable);
```

- Java object exists on heap.
- `t.getState()` is NEW.
- No execution happens.

- **JVM level**
- Allocates the `java.lang.Thread` object on heap and initializes fields (name/priority/daemon/target/group).
- **Does not** create:
  - Stack
  - PC
  - Native OS thread

- **OS level**
- OS has no knowledge of this “thread” yet.

- **CPU level**
- CPU keeps running the current thread (usually “main”).

---

### PHASE 2: `start()` (NEW → RUNNABLE)

```java
t.start();
```

- **Java level**
- `start()` can be called only once.
- Second call throws `IllegalThreadStateException`.

- **JVM level (critical)**
- Validates thread is NEW.
- Calls native entrypoint (`start0()` via JNI).
- Creates execution environment:
  - `JavaThread` (JVM control structure)
  - Java stack (`-Xss`)
  - PC bookkeeping (bytecode index / instruction pointer mapping)
  - `OSThread` (JVM↔OS bridge)

- **OS level**
- Creates an OS thread (e.g., `pthread_create()` on Linux).
- Allocates kernel structures and schedules it.

- **CPU level**
- The thread becomes READY (eligible to run).
- It may run immediately or later—scheduler decides.

---

### PHASE 3: RUNNABLE (READY + RUNNING)

- **Java level**

```java
t.getState(); // RUNNABLE (covers ready + running)
```

- **JVM level**
- Prepares `run()` entry frame.
- Marks `JavaThread` runnable.
- Stack + PC state become “live”.

- **OS level**
- Thread enters scheduler runqueue.
- Scheduler selects threads based on policy (e.g., Linux CFS uses fairness via `vruntime`).

- **CPU level**
- When scheduled:
  - Context switch loads registers
  - Instructions execute on a CPU core

---

### PHASE 4: Execution + context switching

While running:
- **Stack** contains frames, locals, operand stack, return addresses.
- **PC** tracks “next instruction” (bytecode index or machine address).

Preemption:
- Time slice ends or higher-priority work arrives.
- OS saves CPU context and returns thread to runqueue.
- Java still shows RUNNABLE.

---

### PHASE 5: BLOCKED (monitor lock contention)

```java
synchronized (obj) {
    // ...
}
```

- Java state becomes **BLOCKED** when waiting to enter a monitor.
- JVM places thread into the monitor’s contention structures (entry list).
- OS deschedules it (it stops consuming CPU).

---

### PHASE 6: WAITING / TIMED_WAITING

WAITING examples:

```java
obj.wait();
thread.join();
LockSupport.park();
```

TIMED_WAITING examples:

```java
Thread.sleep(ms);
obj.wait(ms);
thread.join(ms);
```

JVM/OS behavior:
- Thread is parked/sleeping.
- Stack + PC are preserved.
- `wait()` releases the monitor lock (sleep does not).

---

### PHASE 7: Resumption (WAIT/BLOCK → RUNNABLE)

Causes:
- `notify()` / `notifyAll()`
- `unpark()`
- timeout
- interrupt

Result:
- Thread re-enters runqueue and competes for CPU again.

---

### PHASE 8: Termination

Two cases:
- **Normal**: `run()` returns.
- **Abnormal**: uncaught exception escapes `run()`.

JVM:
- Final frames popped.
- `JavaThread` torn down.
- Java state becomes TERMINATED.

OS:
- Kernel thread exits and releases kernel resources.

Java heap:
- `java.lang.Thread` object may still exist until GC collects it.

---

## 6. Thread Representation by Layer (What It Is / Stores / Why)

This section answers (per component): **what it is, what it stores, why it stores it, what layer it belongs to**.

---

### 6.1 `java.lang.Thread` (Java heap object)

- **Level**: Java heap (GC-managed)
- **What it is**: User-facing API object representing thread identity + lifecycle control
- **What it stores (Java-level metadata)**:
  - Name
  - Java-level thread ID
  - Priority, daemon flag
  - Runnable target reference
  - ThreadGroup reference
  - UncaughtExceptionHandler
  - State (`NEW`, `RUNNABLE`, …)
  - Interrupt status flag
- **What it does NOT store**:
  - ❌ Java stack
  - ❌ PC
  - ❌ OS scheduler/kernel identity
- **Why it exists**:
  - API abstraction + lifecycle control (`start/join/interrupt`)
  - JVM-independent programming model
  - **It never executes code by itself**

---

### 6.2 `JavaThread` (JVM internal control structure)

- **Level**: JVM native memory (C/C++, not GC-managed)
- **What it is**: JVM’s execution controller for a thread
- **What it stores (high-signal categories)**:
  - **Execution / safepoint state** (for GC)
  - **Stack metadata** (bounds, guards, overflow checks)
  - **Frame pointers** (current/last Java/last native frame)
  - **Synchronization state** (monitors owned, park/unpark)
  - **GC metadata** (TLAB, root refs, oop maps)
  - **Interrupt + signal metadata**
  - **Linkage** to:
    - the `OSThread`
    - the `java.lang.Thread` object
- **Why it’s critical**:
  - Without it, JVM cannot:
    - stop threads for GC (safepoints)
    - track ownership of locks/monitors
    - unwind exceptions correctly
    - manage transitions between Java and native (JNI)

---

### 6.3 Java Stack (user stack)

- **Level**: JVM native memory (per-thread)
- **What it is**: Method-call stack (frames) for Java execution
- **Allocation / lifetime**:
  - allocated at `start()`
  - size controlled by `-Xss`
  - freed on thread termination
- **What it stores**:
  - **One stack frame per method call**, including:
    - local variables (primitives + object references)
    - operand stack (bytecode evaluation)
    - return address / linkage
    - method metadata pointers
- **Why it exists**:
  - call isolation + recursion
  - thread safety for locals (no sharing across threads)

Example:

```java
int sum(int a, int b) {
    int c = a + b;
    return c;
}
```

Frame holds `a`, `b`, `c`, and intermediate results.

---

### 6.4 Program Counter (PC)

- **Level**: JVM abstraction + CPU register state
- **What it is**: “Next instruction” pointer for the thread
- **What it stores**:
  - bytecode index (interpreted)
  - machine address (JIT-compiled/native)
- **Why it’s required**:
  - resume after context switch
  - exception handling and control flow
  - correct multithreaded interleaving

---

### 6.5 `OSThread` (JVM ↔ OS bridge)

- **Level**: JVM native memory
- **What it is**: OS-specific thread wrapper (portability layer)
- **What it stores**:
  - native thread id/handle
  - scheduling hints (priority/policy)
  - parked/unparked state and OS primitives references
  - signal masks / interrupt delivery metadata
- **Why it exists**:
  - JVM portability across OSes
  - centralized safe interaction with OS thread primitives

---

### 6.6 OS kernel thread (`task_struct` on Linux)

- **Level**: OS kernel memory
- **What it is**: the entity the OS actually schedules
- **What it stores**:
  - saved CPU register context (IP/SP/general regs/SIMD/FPU)
  - scheduling metadata (`vruntime`, priority/nice, class, affinity)
  - memory context (page tables shared with process)
  - identity (TID, parent process id)
  - kernel-level state (running/ready/sleeping/waiting)
- **Why it exists**:
  - only kernel can preempt, schedule, and handle interrupts

---

### 6.7 Kernel stack

- **Level**: OS kernel memory
- **What it is**: stack used in kernel mode (syscalls/interrupts)
- **What it stores**:
  - syscall frames
  - interrupt handling frames
  - kernel-local variables
- **Key rule**:
  - **not** the Java stack
  - used only in kernel mode

---

### 6.8 CPU hardware state

- **Level**: physical CPU
- **What it is**: live execution state while scheduled on a core
- **What it stores (while running)**:
  - instruction pointer, stack pointer
  - registers, flags
  - caches (data/instruction cache lines)
- **Why it matters**:
  - context switching saves/restores this state

---

### 6.9 What persists vs what dies at termination

| Component | Freed on Termination |
|----------|-----------------------|
| Java stack | ✅ |
| JavaThread | ✅ |
| OSThread | ✅ |
| Kernel thread | ✅ |
| Kernel stack | ✅ |
| Java Thread object | ❌ (GC later) |

---

## 7. JVM + OS Deep Dive (Stacks, Scheduling, Memory, Sync)

### 7.1 JVM startup: initial threads you typically see

When you run `java MyClass`, the OS starts a JVM process, and the JVM creates several threads in addition to your “main” thread (examples vary by JVM/flags):

- **main**: runs `public static void main(...)`
- **Reference Handler** (daemon)
- **Finalizer** (daemon, legacy behavior)
- **Signal Dispatcher** (daemon)
- **GC threads** (daemon; count depends on GC)
- **JIT compiler threads** (daemon; C1/C2)

---

### 7.2 Java ↔ OS mapping (platform threads)

On common JVMs, Java platform threads map **1:1** to OS threads:

```
Java thread ↔ JVM internal thread ↔ OS kernel thread
```

*(Virtual threads are different; see debugging section for how to compare.)*

---

### 7.3 Scheduling and context switching (OS decides CPU)

What the OS scheduler does:
- chooses a runnable thread to run on a core
- preempts threads when the time slice expires
- saves and restores CPU register context during context switches

Linux CFS note (intuition):
- tries to be fair over time (tracks “virtual runtime”)
- doesn’t guarantee strict FIFO fairness for lock acquisition

---

### 7.4 Thread memory model: per-thread vs shared

**Per-thread (private):**
- Java stack
- PC bookkeeping
- thread-local storage (e.g., `ThreadLocal`)

**Shared (all threads):**
- heap (object instances)
- method area / metaspace (class metadata)

Why this matters:
- locals on stack are naturally thread-confined
- heap access needs synchronization/visibility rules

---

### 7.5 Synchronization: `synchronized`, monitors, and why BLOCKED happens

```java
synchronized (obj) {
    // critical section
}
```

Conceptually:
- each object can have an associated monitor
- a thread entering a monitor becomes the owner
- if another thread wants that monitor, it becomes BLOCKED

---

### 7.6 `wait()`/`notify()` vs lock contention (WAITING vs BLOCKED)

Inside a `synchronized` block:

```java
synchronized (obj) {
    obj.wait(); // releases monitor, then WAITING
}
```

And waking:

```java
synchronized (obj) {
    obj.notify(); // moves a waiting thread to contend for the monitor again
}
```

Key distinction:
- **BLOCKED**: waiting to acquire a monitor
- **WAITING**: parked by a coordination API (wait/join/park)

---

### 7.7 How a BLOCKED thread “knows” the lock was released (high-level)

At a high level:
- JVM maintains contention bookkeeping for monitors.
- When the owner exits the monitor, JVM/OS wake up a blocked contender.
- The contender becomes runnable again and retries acquisition.

Important intuition:
- blocked threads are typically **parked** at OS level; they do not spin forever in Java code.
- JVMs may use adaptive spinning for short critical sections, then park.

---

## 8. Practical Debugging (jstack, deadlocks, tips)

### 8.1 Thread dump (jstack)

```bash
jstack <pid>
```

What to look for:
- thread name + state
- “waiting to lock” (BLOCKED) vs “waiting on condition” (WAITING/TIMED_WAITING)
- monitor/lock identities in stack traces

### 8.2 Deadlocks

Typical symptom in dumps:
- threads in BLOCKED with cyclic lock ownership
- many threads stuck in WAITING due to missed signaling / wrong lock usage

### 8.3 Platform threads vs virtual threads (conceptual)

- Platform thread: generally maps to a kernel thread (1:1)
- Virtual thread: scheduled by the JVM onto a smaller set of carrier (platform) threads

---

## 9. Key Takeaways (Memorize)

1. **Thread object ≠ execution**
2. **`start()` creates execution context (and typically the OS thread)**
3. **RUNNABLE ≠ running**
4. **OS scheduler decides CPU time**
5. **Stack + PC define resumable execution**
6. **BLOCKED ≠ WAITING**
7. **Termination frees JVM/OS resources; Java object can remain until GC**

---

## 10. One-Line Master Summary

A Java thread starts as a heap-level `Thread` object, becomes a JVM-managed execution context (`JavaThread` + private stack + PC) bridged via `OSThread` to an OS kernel thread that the scheduler runs on CPU cores, transitions through RUNNABLE/BLOCKED/WAITING states as synchronization and coordination require, and terminates by freeing JVM/OS resources while the Java object persists until garbage collection.


