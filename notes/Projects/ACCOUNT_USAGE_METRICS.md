# Account Usage Feature - Complete Interview Guide (SDE-1)

## 🎯 One-Liner Summary (Elevator Pitch)
> "I built an **event-driven account usage tracking system** that captures user activities (searches, exports, profile views) across a B2B SaaS platform, publishes events to AWS SQS, and persists them to PostgreSQL using an AWS Lambda consumer with reactive programming."

---

## 📋 High-Level Architecture

```
┌──────────────────────────────────────────────────────────────────────────────┐
│                           USER INTERACTION                                    │
│  (Search, Export, Profile View, List Upload, Partner Recommendation)         │
└────────────────────────────────────┬─────────────────────────────────────────┘
                                     │
                                     ▼
┌──────────────────────────────────────────────────────────────────────────────┐
│                 LIBRARY (kp-account-usage-lib)                               │
│  ┌────────────────────────────────────────────────────────────────────────┐  │
│  │  AccountUsagePublishingService (Orchestrator)                          │  │
│  │  • Extracts metadata (IP, timestamp, eventId)                          │  │
│  │  • Calls AccountUsageService for DTO construction                      │  │
│  │  • Stores large payloads in Redis (7-day TTL)                         │  │
│  │  • Sends event to SQS via UserReportRequestSender                      │  │
│  └────────────────────────────────────────────────────────────────────────┘  │
│                                     │                                         │
│  ┌────────────────────────────────────────────────────────────────────────┐  │
│  │  AccountUsageService (Business Logic)                                  │  │
│  │  • Constructs AccountUsageEventDTO                                     │  │
│  │  • Enriches with user context (account, profile, access rules)         │  │
│  │  • Builds feature paths (product.feature.sourcePath)                   │  │
│  └────────────────────────────────────────────────────────────────────────┘  │
└──────────────────────────────────────┬───────────────────────────────────────┘
                                       │
                                       ▼
┌──────────────────────────────────────────────────────────────────────────────┐
│                            AWS SQS QUEUE                                      │
└──────────────────────────────────────┬───────────────────────────────────────┘
                                       │ SQS Triggers Lambda
                                       ▼
┌──────────────────────────────────────────────────────────────────────────────┐
│                 LAMBDA (kp-account-usage-lambda)                             │
│  ┌────────────────────────────────────────────────────────────────────────┐  │
│  │  AccountUsagePersistenceService                                        │  │
│  │  • Deserializes SQS message to AccountUsageEventDTO                    │  │
│  │  • Routes based on event type (standard vs special)                    │  │
│  │  • Fetches enrichment data from Redis (if needed)                      │  │
│  │  • Persists to PostgreSQL via AccountUsageRepository                   │  │
│  └────────────────────────────────────────────────────────────────────────┘  │
│                                     │                                         │
│  ┌────────────────────────────────────────────────────────────────────────┐  │
│  │  AccountUsageRepository (Data Access Layer)                            │  │
│  │  • Uses Spring R2DBC for reactive database operations                  │  │
│  │  • Inserts 22 fields including JSONB columns                          │  │
│  │  • Implements retry logic for duplicate key handling (up to 5 times)   │  │
│  └────────────────────────────────────────────────────────────────────────┘  │
└──────────────────────────────────────┬───────────────────────────────────────┘
                                       │
                     ┌─────────────────┴─────────────────┐
                     ▼                                   ▼
          ┌──────────────────┐                ┌──────────────────┐
          │      Redis       │                │   PostgreSQL     │
          │   (Cache/Temp)   │                │   (Persistence)  │
          │   - Large payloads│               │   kp_user_events │
          │   - 7-day TTL    │                │      table       │
          └──────────────────┘                └──────────────────┘
```

---

## 🔄 Complete End-to-End Flow

### Step-by-Step Flow (How to Explain in Interview):

**1. User Action Trigger**
```
"When a user performs any action like searching, exporting, or viewing a profile, 
the controller calls our AccountUsagePublishingService."
```

**2. Event Construction (Library Side)**
```java
// Example: publishSearchAccountUsageMessageBuyer()
val eventId = UUIDUtils.getUUIDWithoutDashes();          // Generate unique ID
val remoteAddress = AccountUsageUtils.getIpAddress(req); // Extract IP
val eventTriggerTime = System.currentTimeMillis();       // Capture timestamp

// Construct search-specific payload
val searchInfo = accountUsageService.constructAccountProfileInfoForSearchBuyer(product, req, resultCount);

// Serialize to JSON
val body = SerializationUtils.serialize(searchInfo);

// Construct full event DTO with user context
val accountUsageDTO = accountUsageService.constructAccountUsageInfo(
    eventId, eventName, body, eventTriggerTime, 
    ipAddress, featureName, featurePath, userSessionDetails, date, product
);
```

**3. Large Payload Handling (Redis)**
```java
// For large payloads (list uploads, partner recommendations):
return valueOps.set(redisKey, body, Duration.ofDays(7))  // Store in Redis
    .flatMap(el -> {
        // Send only Redis key reference in SQS message
        val keyBuilder = SerializationUtils.serialize(
            RequestAndKey.builder().key(redisKey).build()
        );
        // Publish to SQS with key reference
        userReportRequestSender.sendMessage(accountUsageInfo, auditInfo);
    });
```

**4. SQS Message Publishing**
```
"The event is published to an AWS SQS queue. SQS provides:
- Decoupling between producer and consumer
- Guaranteed message delivery
- Built-in retry mechanisms"
```

**5. Lambda Trigger & Processing**
```java
// Lambda receives SQS event
public String handleRequest(Map<String, Object> sqsEvent) {
    // Extract SQS message  
    val sqsMessage = SerializationUtils.convertTo(
        sqsEvent.get("Records"), listOfMessageTypeRef).get(0);
    
    // Deserialize to DTO
    val accountUsageEventDTO = SerializationUtils.deserialize(
        sqsMessage.getBody(), AccountUsageEventDTO.class);
    
    // Route based on event type
    if (isPartnerRecommendation && hasRequestBody) {
        // Fetch response from Redis, merge, persist
    } else if (eventName.equals("list_upload")) {
        // Fetch full payload from Redis, persist
    } else if (redisKey != null) {
        // Enrich from Redis, persist
    } else {
        // Standard direct persistence
    }
}
```

**6. Data Enrichment (Redis)**
```java
// getResponseBasedOnKey() method
return valueOps.get(key)
    .flatMap(responseBody -> {
        // Merge Redis data into additional_attributes
        attributesMap.put("response_body", serializedValue);
        accountUsageEventDTO.setAdditionalAttributes(attributesString);
        return Mono.just(accountUsageEventDTO);
    })
    .switchIfEmpty(Mono.error(new RuntimeException("Key not found")));
```

**7. Database Persistence with Retry**
```java
// insertWithRetry() method
return kpDatabaseClient.sql(query)
    .bind("eventId", dto.getEventId())
    .bind("additionalAttributes", Json.of(dto.getAdditionalAttributes()))
    // ... 20+ other bindings
    .fetch().rowsUpdated()
    .onErrorResume(
        throwable -> throwable instanceof DataIntegrityViolationException 
                     && retryCount < 5,
        throwable -> {
            // Generate new event ID and retry
            dto.setEventId(UUIDUtils.getUUIDWithoutDashes());
            return insertWithRetry(query, dto, retryCount + 1);
        });
```

---

## 🛠️ Technologies & Design Patterns

### Technologies Used:
| Technology | Purpose |
|------------|---------|
| **Spring Boot** | Application framework |
| **Spring Cloud Function** | AWS Lambda integration |
| **Spring Data R2DBC** | Reactive database access |
| **Project Reactor (Mono/Flux)** | Reactive programming |
| **PostgreSQL with JSONB** | Primary data store |
| **Redis (Lettuce client)** | Caching layer with GZIP compression |
| **AWS Lambda** | Serverless event processing |
| **AWS SQS** | Message queue for event decoupling |
| **Jackson** | JSON serialization |
| **Lombok** | Boilerplate reduction |

### Design Patterns:
1. **Event-Driven Architecture** - Decoupled producer-consumer
2. **Repository Pattern** - Abstracted database operations
3. **Service Layer Pattern** - Separated business logic
4. **DTO Pattern** - Data transfer between layers
5. **Builder Pattern** - Using Lombok `@Builder`
6. **Strategy Pattern** - Conditional routing based on event type
7. **Retry Pattern** - Automatic retry on duplicate key violations

---

## 💡 Key Design Decisions (Why Questions)

### Q: Why use Reactive Programming?
```
"We chose reactive programming with Project Reactor for:
1. Non-blocking I/O - Database and Redis operations don't block threads
2. Better resource utilization - Handles high-throughput event processing
3. Composability - Chain async operations using flatMap, map, switchIfEmpty
4. Backpressure handling - Reactor handles flow control automatically

Example: Instead of blocking on Redis fetch, we use:
valueOps.get(key).flatMap(response -> processAndPersist(response))
```

### Q: Why store large payloads in Redis?
```
"AWS SQS has a 256KB message size limit. For large payloads like list uploads:
1. Store full payload in Redis with 7-day TTL
2. Send only Redis key reference in SQS message
3. Consumer fetches full payload from Redis during processing

Benefits:
- Reduces SQS costs (charged per message size)
- Avoids message size limit issues
- Enables temporary storage for large data"
```

### Q: Why use JSONB in PostgreSQL?
```
"JSONB provides:
1. Flexibility - Store variable event structures without schema changes
2. Queryability - PostgreSQL can query JSONB fields with GIN indexes
3. Storage efficiency - Binary JSON format is compact
4. Schema evolution - Add new fields without migrations"
```

### Q: Why implement retry on duplicate key?
```
"Even though we use UUIDs, collision is rare but possible at high scale:
1. Detect DataIntegrityViolationException (duplicate key)
2. Generate new UUID
3. Retry up to 5 times
4. Prevents data loss from ID collisions

This ensures idempotent event processing."
```

### Q: Why separate library and Lambda?
```
"Separation of concerns:
- Library (producer): Used by main application, handles event construction
- Lambda (consumer): Serverless, scales independently, handles persistence

Benefits:
- Independent deployment
- Different scaling requirements
- Clear responsibility boundaries"
```

---

## 🎤 Interview Questions & Answers

### Basic Questions:

**Q1: What is this feature about?**
> "It's an event-driven system that tracks user activities across our B2B SaaS platform. When users perform actions like searches, exports, or profile views, we capture these events, enrich them with user context, publish to SQS, and persist to PostgreSQL for analytics and billing."

**Q2: What were your main responsibilities?**
> "I was responsible for:
> - Implementing event publishing for different action types (search, export, list upload)
> - Building the Lambda consumer with reactive programming
> - Handling large payload storage in Redis
> - Implementing retry logic for data integrity
> - Writing JSONB-based database persistence"

**Q3: How does the data flow work?**
> "User action → Controller calls AccountUsagePublishingService → Event DTO constructed → Large payloads stored in Redis → Message published to SQS → Lambda triggered → Event deserialized → Redis enrichment (if needed) → PostgreSQL persistence"

### Technical Deep-Dive Questions:

**Q4: Explain reactive programming in your project.**
```java
// Instead of blocking:
Object result = redisTemplate.get(key);  // Blocks thread

// We use reactive:
valueOps.get(key)
    .flatMap(value -> processData(value))    // Chain operations
    .switchIfEmpty(Mono.error(...))          // Handle empty
    .onErrorResume(e -> handleError(e))      // Error handling
    .toFuture().join();                      // Block only at end
```
> "Reactive programming allows us to chain non-blocking operations. The thread isn't blocked while waiting for Redis or database responses, improving throughput."

**Q5: How do you handle failures?**
> "Multiple levels:
> 1. **SQS retry** - Failed Lambda invocations are retried automatically
> 2. **Database retry** - 5 retries on duplicate key with new UUID
> 3. **Graceful degradation** - If JSON parsing fails, we set additional_attributes to {} and continue
> 4. **Comprehensive logging** - AuditInfo with traceId, eventId for debugging"

**Q6: What if Redis key is not found?**
```java
return valueOps.get(key)
    .flatMap(response -> ...)
    .switchIfEmpty(Mono.error(new RuntimeException("Key not found in Redis")));
```
> "We use `switchIfEmpty()` to throw an error if the key doesn't exist. The error is logged and can be monitored via CloudWatch for investigation."

**Q7: How do you ensure data consistency?**
> "1. **Event IDs** - Each event has a unique UUID (primary key)
> 2. **Retry logic** - Duplicate keys get new UUIDs
> 3. **Atomic operations** - Single database insert per event
> 4. **Audit trail** - traceId links entire event lifecycle"

### System Design Questions:

**Q8: How would you scale this system?**
> "Scaling points:
> - **SQS** - Scales automatically with message volume
> - **Lambda** - Auto-scales based on queue depth
> - **Redis** - Can use Redis Cluster for horizontal scaling
> - **PostgreSQL** - Read replicas, partitioning by date
> - **Connection pooling** - Configured (initial: 2, max: 5)"

**Q9: What metrics would you monitor?**
> "Key metrics:
> - Event processing time (we log this per event)
> - SQS message age (backlog indicator)
> - Lambda invocation errors
> - Database connection pool utilization
> - Redis cache hit/miss ratio
> - Retry rate for duplicate keys"

**Q10: How would you handle message ordering?**
> "Current setup doesn't guarantee ordering (standard SQS). If needed:
> 1. Use SQS FIFO queues
> 2. Add sequence numbers to events
> 3. Handle out-of-order events in consumer logic"

---

## 🏆 What Made This Project Impressive

### Challenges Solved:
1. **Large Payload Handling** - Redis caching with TTL to bypass SQS limits
2. **Event Diversity** - Multiple event types with conditional routing
3. **Data Integrity** - Retry mechanism for duplicate key handling
4. **Performance** - Reactive programming for high-throughput processing
5. **Flexibility** - JSONB for variable event structures

### Impact:
- **Analytics** - Enabled product usage reporting
- **Billing** - Tracked usage for customer billing
- **Compliance** - Complete audit trail of user actions
- **Performance** - Sub-100ms processing time per event

---

## 📊 Event Types Supported

| Event Type | Description | Special Handling |
|------------|-------------|------------------|
| Search | User performs search | Captures filters, result count |
| Export | User creates export file | Export ID, company count |
| Export Download | User downloads export | Download timestamp |
| Company Profile View | User views profile | Company ID, name |
| List Upload | User uploads list | **Redis storage** (large payload) |
| Partner Recommendation | User requests recommendations | **Redis storage** (request/response) |
| Mini Profile Navigation | User navigates to mini profile | Navigation path |
| Credits Panel | User views credits info | Credit balance |

---

## 🧪 Testing Approach

If asked about testing:
```
"For testing this system:
1. Unit tests - Mock Redis, Database, SQS; test each service method
2. Integration tests - Use Testcontainers for real Redis/PostgreSQL
3. Edge cases tested:
   - Empty Redis key
   - Invalid JSON in additional_attributes
   - Duplicate event IDs (retry mechanism)
   - Large payloads exceeding memory
   - Missing optional fields (null handling)"
```

---

## 💬 Sample Interview Narrative

> "In my current role, I worked on an **Account Usage Tracking feature** for our B2B SaaS platform. 
> 
> The **problem** was that we needed to track user activities across multiple products for analytics, billing, and compliance purposes.
> 
> I built an **event-driven architecture** with two components:
> 1. A **library** that's integrated into our main application to capture and publish events
> 2. An **AWS Lambda** that consumes events from SQS and persists them to PostgreSQL
> 
> **Key technical decisions:**
> - Used **reactive programming** with Project Reactor for non-blocking I/O
> - Implemented **Redis caching** for large payloads that exceed SQS limits
> - Used **JSONB** in PostgreSQL for flexible event storage
> - Built **retry logic** for handling duplicate key violations
> 
> The feature now tracks **9 different event types** across **4 products**, processing thousands of events daily with sub-100ms latency."

---

## ⚠️ Common Mistakes to Avoid

1. **Don't just describe code** - Explain the "why" behind decisions
2. **Don't memorize** - Understand concepts so you can adapt to follow-up questions
3. **Don't oversell** - Be honest about what you personally did vs. team contributions
4. **Don't ignore failures** - Have answers ready for "what would you do differently?"
5. **Don't be vague** - Use specific numbers, technologies, and examples
