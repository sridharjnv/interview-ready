# Backend Engineer Interview Preparation Roadmap
## For SDE-1 with ~1 Year Experience (Java/Spring Boot/Microservices)

---

## 📊 Understanding Interview Expectations at Your Level

| Area | What Interviewers Expect | What Gets You Rejected |
|------|-------------------------|------------------------|
| **DSA** | Solve medium LeetCode in 30-40 mins with clean code | Brute force only, no optimization discussion |
| **Core Java** | Deep understanding of fundamentals, not just syntax | "I just use it, never looked into how it works" |
| **Spring Boot** | Know DI, annotations, request lifecycle | Copy-paste from tutorials without understanding |
| **System Design** | Basic LLD, understand HLD concepts | Jumping to solutions without requirements |
| **Databases** | Write efficient queries, understand indexing | No idea why queries are slow |

---

## 1️⃣ Data Structures & Algorithms

### Must-Know (Cannot Skip)

| Topic | Depth Expected | Time to Master |
|-------|---------------|----------------|
| Arrays/Strings | Two pointers, sliding window, prefix sums | Week 1-2 |
| HashMap/HashSet | Frequency counting, two-sum patterns | Week 1 |
| Stack/Queue | Monotonic stacks, BFS basics | Week 2 |
| Binary Search | On arrays AND on answer space | Week 2 |
| Linked Lists | Reversal, cycle detection, merge | Week 3 |
| Trees | Traversals, BST operations, LCA | Week 3-4 |
| Graphs | BFS/DFS, simple cycle detection | Week 4-5 |
| Recursion/Backtracking | Subsets, permutations | Week 5 |
| Basic DP | 1D DP (climbing stairs, house robber pattern) | Week 6 |

### Common Interview Questions (Increasing Difficulty)

**Arrays/Strings:**
1. Two Sum → Three Sum → Four Sum
2. Best Time to Buy/Sell Stock → with Cooldown
3. Longest Substring Without Repeating Characters
4. Merge Intervals (very common for backend roles)

**Trees (Backend favorites):**
1. Level Order Traversal (relates to BFS in distributed systems)
2. Validate BST
3. Lowest Common Ancestor
4. Serialize/Deserialize Binary Tree

### 🔴 Red Flags That Cause Rejection

```java
// WEAK ANSWER: Brute force without acknowledging optimization
public int[] twoSum(int[] nums, int target) {
    for (int i = 0; i < nums.length; i++) {
        for (int j = i + 1; j < nums.length; j++) {
            if (nums[i] + nums[j] == target) return new int[]{i, j};
        }
    }
    return new int[]{};
}
// "This is O(n²), I think it's fine"

// STRONG ANSWER: Shows optimization awareness
// "I'll start with brute force O(n²), but we can optimize to O(n) 
// using a HashMap to store complement lookups"
public int[] twoSum(int[] nums, int target) {
    Map<Integer, Integer> seen = new HashMap<>();
    for (int i = 0; i < nums.length; i++) {
        int complement = target - nums[i];
        if (seen.containsKey(complement)) {
            return new int[]{seen.get(complement), i};
        }
        seen.put(nums[i], i);
    }
    return new int[]{};
}
```

### How to Prepare Effectively

1. **Don't grind blindly** - Follow a pattern-based approach (Neetcode 150 or Blind 75)
2. **Time yourself** - 25 mins for easy, 40 mins for medium
3. **Practice explaining out loud** - Interviewers evaluate communication
4. **Review after solving** - Look at top solutions, understand trade-offs

---

## 2️⃣ Core Java (JVM, Concurrency, Memory)

### Must-Know

| Topic | Interview Questions | Why It Matters |
|-------|-------------------|----------------|
| **Collections Framework** | ArrayList vs LinkedList, HashMap internals, ConcurrentHashMap | Daily backend work |
| **Multithreading Basics** | Thread lifecycle, synchronized, volatile, wait/notify | Async systems |
| **JVM Memory Model** | Heap vs Stack, GC basics (G1), memory leaks | Production debugging |
| **Exception Handling** | Checked vs Unchecked, try-with-resources | Code quality |
| **Java 8+ Features** | Streams, Optional, functional interfaces, lambdas | Modern codebases |

### Good-to-Know (Helps Stand Out)

- ThreadLocal usage patterns
- CompletableFuture for async composition
- Memory visibility issues
- ClassLoader basics

### Overkill (Not Expected at 1 YoE)

- Bytecode manipulation
- Custom garbage collectors
- JIT compiler internals
- Unsafe class operations

### Common Interview Questions

**HashMap Internals (Asked 70% of interviews):**
```
Q: "How does HashMap work internally?"

AVERAGE ANSWER: "It stores key-value pairs using hashing"

HIRE ANSWER: "HashMap uses an array of buckets. When we put a key:
1. hashCode() is called on key
2. Hash is spread using (h ^ h >>> 16) to reduce collisions
3. Bucket index = hash & (n-1) where n is capacity
4. If collision, before Java 8 it was linked list, after Java 8 
   it converts to red-black tree when bucket size > 8
5. Load factor 0.75 triggers resize (doubles capacity + rehashes)"
```

**ConcurrentHashMap vs Hashtable:**
```
Q: "Why prefer ConcurrentHashMap over Hashtable?"

WEAK: "ConcurrentHashMap is thread-safe and faster"

STRONG: "Both are thread-safe, but:
- Hashtable locks entire map (coarse-grained)
- ConcurrentHashMap uses segment-level locking (Java 7) or 
  CAS + synchronized on bucket level (Java 8+)
- ConcurrentHashMap allows concurrent reads without locking
- Null keys/values not allowed in ConcurrentHashMap (avoids ambiguity)"
```

**Volatile vs Synchronized:**
```java
// When to use volatile?
// ✅ Single writer, multiple readers
// ✅ Simple flags (stop threads)
private volatile boolean running = true;

// When to use synchronized?
// ✅ Compound operations (check-then-act)
// ✅ Multiple fields need atomic update
synchronized void transfer(Account from, Account to, int amount) {
    from.balance -= amount;
    to.balance += amount;
}
```

### 🔴 Red Flags

- "I've never looked at how HashMap works internally"
- Confusion between `==` and `.equals()` for objects
- Not knowing difference between final/finally/finalize
- Using raw types instead of generics

---

## 3️⃣ Spring Boot & Microservices

### Must-Know

| Topic | Depth Expected | Common Questions |
|-------|---------------|------------------|
| **Dependency Injection** | Constructor vs Field injection, why DI matters | How does @Autowired work? |
| **Bean Lifecycle** | @PostConstruct, @PreDestroy, scopes | Singleton vs Prototype beans |
| **Request Flow** | DispatcherServlet → Controller → Service → Repository | Draw the flow |
| **REST Best Practices** | HTTP methods, status codes, versioning | When 200 vs 201 vs 204? |
| **Exception Handling** | @ControllerAdvice, @ExceptionHandler | Global exception handling |
| **Async Processing** | @Async, CompletableFuture | When to use async? |
| **Inter-service Communication** | REST vs Message Queues | Sync vs Async trade-offs |

### Good-to-Know

- Circuit breaker pattern (Resilience4j)
- Distributed tracing concepts
- API Gateway basics
- Service discovery

### Common Interview Questions

**Bean Scopes:**
```
Q: "Explain Spring Bean scopes"

AVERAGE: "Singleton, Prototype, Request, Session"

STRONG: "Singleton is default - one instance per Spring container.
Prototype creates new instance each time bean is requested.
Request/Session are web-scoped.

Real gotcha: Injecting prototype into singleton doesn't work as 
expected - you get same prototype instance. Solutions:
1. ObjectFactory<PrototypeBean>
2. @Lookup annotation
3. ApplicationContext.getBean()"
```

**@Transactional Behavior:**
```java
// Q: "Will this transaction work?"
@Service
public class OrderService {
    
    @Transactional
    public void placeOrder(Order order) {
        saveOrder(order);      // ✅ Transactional
        updateInventory(order); // ❌ NOT transactional - same class call!
    }
    
    @Transactional
    public void updateInventory(Order order) {
        // This won't create new transaction when called internally
    }
}

// EXPLANATION: Spring AOP proxies intercept external calls only.
// Internal method calls bypass the proxy.
// Solution: Inject self or use TransactionTemplate
```

### Microservices Questions

```
Q: "How do you handle distributed transactions?"

WEAK: "We use @Transactional annotation"

STRONG: "In microservices, 2PC is generally avoided due to blocking.
Better patterns:
1. SAGA Pattern - Choreography (events) or Orchestration (central coordinator)
2. Eventual consistency with compensation logic
3. Outbox pattern for reliable event publishing

Example: Order service publishes OrderCreated event. If Payment 
fails, it publishes PaymentFailed, and Order service compensates 
by canceling the order."
```

---

## 4️⃣ Databases (SQL + NoSQL Basics)

### Must-Know

| Topic | What You Should Know | Interview Questions |
|-------|---------------------|-------------------|
| **SQL Queries** | JOINs, GROUP BY, HAVING, subqueries | Write query to find 2nd highest salary |
| **Indexing** | B-tree, when indexes help/hurt, composite indexes | Why is my query slow? |
| **Transactions** | ACID properties, isolation levels | Read Committed vs Serializable |
| **Normalization** | 1NF, 2NF, 3NF, when to denormalize | Why normalize? |
| **N+1 Problem** | Detection and solutions | Common in ORM-heavy apps |

### Common Interview Questions

**Indexing:**
```sql
-- Q: "This query is slow, how to optimize?"
SELECT * FROM orders WHERE customer_id = 123 AND status = 'PENDING' 
ORDER BY created_at DESC;

-- AVERAGE: "Add index on customer_id"

-- STRONG: "Add composite index: (customer_id, status, created_at DESC)
-- Order matters - most selective first.
-- Including created_at in index avoids sorting.
-- But if we SELECT * with many columns, might still need table lookup.
-- Consider covering index if specific columns needed."
```

**N+1 Problem:**
```java
// Q: "What's wrong with this code?"
List<Order> orders = orderRepository.findByCustomerId(customerId);
for (Order order : orders) {
    List<Item> items = order.getItems(); // Lazy load - N+1 queries!
}

// SOLUTION:
// 1. @Query with JOIN FETCH
// 2. @EntityGraph
// 3. Batch fetching @BatchSize
```

### NoSQL (Basic Understanding)

| When to Use | Examples |
|-------------|----------|
| Flexible schema, high write throughput | MongoDB |
| Key-value caching | Redis |
| Wide-column for time-series | Cassandra |
| Graph relationships | Neo4j |

You're NOT expected to be an expert, but know trade-offs:
- CAP theorem awareness
- When relational vs document model
- Why Redis for caching

---

## 5️⃣ Low-Level Design (LLD)

### Must-Know

At 1 YoE, you should design simple systems with clean OOP:

| Problem Type | Examples |
|--------------|----------|
| In-memory systems | Parking Lot, Library Management, Elevator |
| Game design | Tic-Tac-Toe, Snake and Ladder, Chess |
| Real-world objects | ATM, Vending Machine, BookMyShow |

### LLD Approach Framework

```
1. CLARIFY requirements (5 mins)
   - What entities exist?
   - What operations needed?
   - Constraints (concurrent users?)

2. IDENTIFY core objects (5 mins)
   - Nouns → Classes
   - Verbs → Methods

3. DEFINE relationships (5 mins)
   - Has-a, Is-a
   - Composition vs Inheritance

4. APPLY design patterns (5 mins)
   - Only where they naturally fit
   - Don't force patterns

5. CODE key parts (15-20 mins)
   - Start with models
   - Add core services
   - Handle edge cases
```

### Example: Parking Lot (Frequently Asked)

```java
// WEAK ANSWER: Single class with everything
public class ParkingLot {
    List<Vehicle> vehicles;
    public void park(Vehicle v) { ... }
}

// STRONG ANSWER: Proper separation
public abstract class Vehicle {
    String licensePlate;
    VehicleType type;
}

public class Car extends Vehicle { ... }
public class Bike extends Vehicle { ... }

public class ParkingSpot {
    int spotNumber;
    SpotType type;
    Vehicle currentVehicle;
    
    boolean canFitVehicle(Vehicle v) { ... }
    void occupy(Vehicle v) { ... }
    void release() { ... }
}

public class ParkingLot {
    List<ParkingFloor> floors;
    
    ParkingSpot findAvailableSpot(VehicleType type) { ... }
}

public interface ParkingStrategy {
    ParkingSpot findSpot(List<ParkingFloor> floors, VehicleType type);
}

public class NearestSpotStrategy implements ParkingStrategy { ... }
```

### Design Patterns You MUST Know

| Pattern | When Asked | Real Example |
|---------|-----------|--------------|
| Singleton | Logger, Config | `@Service` beans in Spring |
| Factory | Object creation varies | Payment gateway selector |
| Strategy | Algorithm varies | Sorting strategies |
| Observer | Event notification | Event listeners |
| Builder | Complex object construction | StringBuilder, Lombok @Builder |

---

## 6️⃣ High-Level Design (HLD Basics)

### What's Expected at 1 YoE

You won't design YouTube, but you should understand:

| Concept | What to Know |
|---------|-------------|
| **Scalability basics** | Horizontal vs Vertical scaling |
| **Load Balancing** | Why needed, round-robin vs least-connections |
| **Caching** | Cache-aside, write-through, TTL, Redis basics |
| **Database choices** | When SQL vs NoSQL |
| **Message Queues** | Kafka/RabbitMQ use cases, async processing |
| **API Design** | RESTful principles, pagination, rate limiting |

### Frequently Asked (Junior Level)

```
Q: "Design a URL shortener"

Framework:
1. Requirements
   - Generate short URL from long URL
   - Redirect short → long
   - Analytics (basic)
   - Expiry?

2. API Design
   POST /shorten { longUrl } → { shortUrl }
   GET /{shortCode} → 301 Redirect

3. Data Model
   Table: urls (id, short_code, long_url, created_at, expires_at)
   Index on short_code

4. Short code generation
   Option A: Counter + Base62 encoding
   Option B: Hash + take first 7 chars (handle collisions)

5. Scaling considerations
   - Read-heavy → Cache hot URLs in Redis
   - Counter → Distributed ID generator (Snowflake)
   - Sharding by short_code range
```

---

## 7️⃣ CS Fundamentals (OS, Networking)

### Must-Know

**Operating Systems:**
| Topic | Interview Questions |
|-------|-------------------|
| Process vs Thread | Why threads share memory? |
| Concurrency | Deadlock conditions, prevention |
| Memory | Virtual memory basics, paging |
| Scheduling | Round-robin, priority |

**Networking:**
| Topic | Interview Questions |
|-------|-------------------|
| TCP vs UDP | When to use each? |
| HTTP/HTTPS | How TLS works (high level) |
| DNS | How domain resolution works |
| REST vs gRPC | Trade-offs |

### Common Questions

```
Q: "What happens when you type google.com in browser?"

EXPECTED ANSWER:
1. Browser checks cache → OS cache → Router → ISP → DNS
2. DNS resolves to IP address
3. TCP 3-way handshake (SYN, SYN-ACK, ACK)
4. TLS handshake if HTTPS
5. HTTP request sent
6. Server processes, returns response
7. Browser renders HTML, loads assets
```

---

## 📅 8-Week Preparation Plan (2-3 hrs/day)

### Week 1-2: Foundation
| Day | Focus | Tasks |
|-----|-------|-------|
| 1-3 | DSA | Arrays, Strings (10 problems) |
| 4-5 | DSA | HashMap patterns (8 problems) |
| 6-7 | Core Java | Collections deep-dive, HashMap internals |
| 8-10 | DSA | Two pointers, Sliding window (10 problems) |
| 11-12 | Core Java | Multithreading basics |
| 13-14 | SQL | JOINs, GROUP BY practice |

### Week 3-4: Building Blocks
| Day | Focus | Tasks |
|-----|-------|-------|
| 15-17 | DSA | Binary Search, Stack/Queue (12 problems) |
| 18-19 | Spring | DI, Bean lifecycle, request flow |
| 20-21 | DSA | Linked Lists (8 problems) |
| 22-24 | DSA | Trees basics (10 problems) |
| 25-26 | Spring | @Transactional, Exception handling |
| 27-28 | LLD | Study Parking Lot, practice once |

### Week 5-6: Intermediate
| Day | Focus | Tasks |
|-----|-------|-------|
| 29-31 | DSA | Trees continued, BST operations |
| 32-33 | Microservices | Inter-service communication, async |
| 34-36 | DSA | Graph BFS/DFS basics (8 problems) |
| 37-38 | Database | Indexing, N+1, transactions |
| 39-40 | LLD | Library Management System |
| 41-42 | OS/Networking | Process, threads, TCP/HTTP |

### Week 7: Advanced Topics
| Day | Focus | Tasks |
|-----|-------|-------|
| 43-44 | DSA | Recursion, Backtracking (8 problems) |
| 45-46 | DSA | Basic DP (6 problems) |
| 47-48 | HLD | URL Shortener, Caching concepts |
| 49 | Java | Finalize JVM, GC, memory concepts |

### Week 8: Mock & Polish
| Day | Focus | Tasks |
|-----|-------|-------|
| 50-51 | Mock | LeetCode contests, timed practice |
| 52-53 | Mock | LLD mock (record yourself) |
| 54-55 | Review | Weak areas, error patterns |
| 56 | Behavioral | STAR method for project questions |

---

## 🎯 Final Pro Tips

### What Juniors Misunderstand vs Strong Candidates

| Area | Junior Mistake | Strong Approach |
|------|---------------|-----------------|
| DSA | Memorizes solutions | Understands patterns, explains trade-offs |
| Java | "It just works" | Knows WHY it works that way |
| Design | Jumps to coding | Clarifies requirements first |
| Debugging | Random changes | Systematic hypothesis testing |
| Communication | Silent coding | Talks through approach |

### During the Interview

1. **First 5 minutes matter** - State approach before coding
2. **Think out loud** - Silence is uncomfortable for interviewer
3. **Admit gaps honestly** - "I haven't used X, but I'd approach it like Y"
4. **Test your code** - Walk through example before saying "done"
5. **Ask smart questions** - Shows genuine interest

### Resources (Prioritized)

| Resource | For What |
|----------|----------|
| Neetcode 150 | DSA (pattern-based) |
| Java Brains (YouTube) | Spring Boot |
| Baeldung | Java/Spring deep-dives |
| Designing Data-Intensive Applications (Ch 1-5) | HLD concepts |
| LeetCode Explore | Topic-wise practice |

---

Good luck with your preparation! Focus on depth over breadth at your level. An interviewer would rather see you **deeply understand HashMap** than superficially know 20 topics. 🚀
