# CAP Theorem: Complete Guide

## 1. Overview

The **CAP theorem**, proven by Seth Gilbert and Nancy Lynch (2002), states:

> A distributed data store cannot simultaneously provide more than two of: **Consistency**, **Availability**, and **Partition Tolerance**.

| Property | Definition |
|----------|------------|
| **Consistency (C)** | Every read receives the most recent write or an error |
| **Availability (A)** | Every request receives a non-error response |
| **Partition Tolerance (P)** | System operates despite network failures between nodes |

---

## 2. The Critical Insight

### Partition Tolerance Is NOT Optional

Network partitions **will happen** in distributed systems. You must tolerate them.

**The real question is:** During a partition, do you choose **Consistency** or **Availability**?

```
During a partition:

┌─────────────────┐         ┌─────────────────┐
│       CP        │   OR    │       AP        │
├─────────────────┤         ├─────────────────┤
│ Return error/   │         │ Return possibly │
│ block until     │         │ stale data but  │
│ consistent      │         │ always respond  │
└─────────────────┘         └─────────────────┘
```

**Why CA isn't possible:** A "CA" system would be a single-node system—not distributed.

---

## 3. Deep Dive: Each Property

### 3.1 Consistency (Linearizability)

CAP's "C" means **linearizable consistency** (strongest form):
- Once a write completes, all subsequent reads see that write
- Operations appear instantaneous between invocation and response
- All clients see operations in the same order

**Consistency Spectrum:**
| Level | Description |
|-------|-------------|
| **Linearizable** | Real-time ordering, strongest |
| **Sequential** | Operations in *some* consistent order |
| **Causal** | Causally-related operations appear in order |
| **Eventual** | Reads *eventually* return latest value |

### 3.2 Availability

> Every request to a **non-failing node** must result in a **non-error response**

- Response must not be an error (not 500, not timeout)
- No bound on response time (but latency matters practically)

### 3.3 Partition Tolerance

A **network partition** = nodes cannot communicate:

```
Normal:     Node A ←→ Node B ←→ Node C

Partition:  [Node A ←→ Node B]  ✕  [Node C]
                 Partition 1        Partition 2
```

---

## 4. CAP in Practice: Example

### Scenario: E-commerce Inventory (Last Item in Stock)

```
US-East                           US-West
┌─────────────┐                   ┌─────────────┐
│ inventory=1 │       ✕           │ inventory=1 │
└─────────────┘   PARTITION       └─────────────┘
                                        ▲
                                  Customer wants
                                  to buy last item
```

### Option 1: Choose CP

```java
public PurchaseResult buyItem(String itemId) {
    boolean confirmed = replicaCoordinator.confirmStock(itemId);
    
    if (!confirmed) {
        // During partition: REFUSE the sale
        return PurchaseResult.error("Unable to confirm. Try again.");
    }
    return processPurchase(itemId);
}
```

| Pros | Cons |
|------|------|
| ✅ Never oversell | ❌ Customer can't buy during partition |
| ✅ Data consistent | ❌ Lost revenue during outages |

### Option 2: Choose AP

```java
public PurchaseResult buyItem(String itemId) {
    int localStock = localInventory.getStock(itemId);
    
    if (localStock > 0) {
        localInventory.decrement(itemId);
        eventLog.record(new SaleEvent(itemId, timestamp));
        return PurchaseResult.success();
    }
    return PurchaseResult.outOfStock();
}

// Later: reconcile event logs, compensate oversells
```

| Pros | Cons |
|------|------|
| ✅ Always responsive | ❌ Might oversell |
| ✅ Better UX | ❌ Need conflict resolution |

---

## 5. System Classifications

| System | Choice | Notes |
|--------|--------|-------|
| **MongoDB** (`w:majority`) | CP | Blocks writes without quorum |
| **Cassandra** | AP (tunable) | Eventual consistency, always writable |
| **DynamoDB** | AP (tunable) | Prioritizes availability |
| **Zookeeper** | CP | Coordination requires consistency |
| **etcd** | CP | Config/service discovery |
| **CockroachDB** | CP | Serializable transactions |
| **Redis Cluster** | CP-ish | Depends on configuration |

### ⚠️ Most Systems Are Tunable

```yaml
# Cassandra - per-query configuration
consistency_level: ONE      # AP: fast, eventual
consistency_level: QUORUM   # Balanced
consistency_level: ALL      # CP: slow, strong
```

---

## 6. Beyond CAP: PACELC

CAP only describes behavior **during partitions**. PACELC extends this:

```
IF Partition (P): Choose Availability (A) or Consistency (C)
ELSE (E):         Choose Latency (L) or Consistency (C)
```

| System | During Partition | Normal Operation |
|--------|------------------|------------------|
| Cassandra | PA (Available) | EL (Low latency) |
| MongoDB | PC (Consistent) | EC (Consistent) |
| DynamoDB | PA (Available) | EL (Low latency) |

---

## 7. Common Misconceptions

| ❌ Misconception | ✅ Reality |
|------------------|-----------|
| "Pick any two" | Partitions happen—you can't drop P |
| "CAP is binary" | Consistency/availability are spectrums |
| "MongoDB is CP, Cassandra is AP" | Both are configurable |
| "CAP applies all the time" | Only during partitions |
| "Eventual = No consistency" | It's weaker, but still a guarantee |

---

## 8. Practical Patterns

### Pattern 1: Saga (Cross-Service Consistency)

```
Order Service → Payment Service → Inventory Service
                    │
              If any fails:
              ← Compensate ←
```

### Pattern 2: Read-Your-Writes

```java
public void write(String key, String value) {
    long timestamp = writeToCluster(key, value);
    session.setLastWriteTimestamp(key, timestamp);
}

public String read(String key) {
    long required = session.getLastWriteTimestamp(key);
    return readWithMinTimestamp(key, required);
}
```

### Pattern 3: CRDTs (Conflict-Free Replicated Data Types)

Data structures that merge deterministically across nodes:

```java
// G-Counter: merges by taking max per node
public GCounter merge(GCounter other) {
    for (String nodeId : allNodes) {
        merged.put(nodeId, Math.max(this.get(nodeId), other.get(nodeId)));
    }
    return merged;
}
```

---

## 9. Decision Framework

```
┌─────────────────────────────────────────────────────────────────┐
│              When to Choose CP vs AP                            │
├───────────────────────────────┬─────────────────────────────────┤
│  Choose CP                    │  Choose AP                      │
├───────────────────────────────┼─────────────────────────────────┤
│ • Financial transactions      │ • User-facing features          │
│ • Inventory management        │ • Social media feeds            │
│ • Booking systems             │ • Shopping cart (pre-checkout)  │
│ • Leader election             │ • Session/preference data       │
│ • Configuration stores        │ • Analytics/metrics             │
│ • Any "double-spend" risk     │ • Content delivery              │
├───────────────────────────────┴─────────────────────────────────┤
│  Key Question: What's worse—wrong data or no service?           │
└─────────────────────────────────────────────────────────────────┘
```

---

## 10. Interview: Weak vs Strong Answers

### The Question
> "Explain CAP theorem and how you'd apply it in system design."

### 🔴 Weak Answer
> "CAP says you pick 2 of 3. MongoDB is CP, Cassandra is AP."

**Problems:** Memorized, no understanding of tradeoffs, no practical application.

### 🟢 Strong Answer
> "CAP states that during a network partition, you must choose between consistency and availability—partition tolerance isn't optional since partitions will happen.
>
> For our payment service, we chose CP—we'd rather reject a transaction than risk double-charging. But for user sessions, we went AP—showing a stale 'last seen' is better than making the feature unavailable.
>
> Most databases are actually tunable—MongoDB with `w:majority` is CP, but with `w:1` it's more AP."

**Strengths:** Understands the real tradeoff, provides business context, knows systems are configurable.
