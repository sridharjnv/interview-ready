# System Design Fundamentals

## Why this file matters
The feature documents are great for real examples, but interviews are usually cleared by combining:
- clear structure
- strong fundamentals
- good tradeoff discussion

If you know only feature stories, you may struggle when the interviewer asks a fresh problem like chat, URL shortener, or ride matching.

## The 12 core building blocks

### 1. Clients
- mobile app
- web app
- internal admin panel
- partner systems

Main questions:
- who calls the system
- what is the request rate
- are requests sync or async

### 2. API Gateway
Responsibilities:
- routing
- auth
- rate limiting
- request tracing
- response aggregation

Use when:
- many services sit behind one entry point

### 3. Load Balancer
Responsibilities:
- spread traffic across replicas
- improve availability
- remove unhealthy instances

Common interview language:
- L4 or L7 load balancer
- round robin, least connections

### 4. Application Servers
Responsibilities:
- business logic
- validation
- orchestration
- calling downstream systems

Rule:
- keep them stateless whenever possible

### 5. Cache
Use for:
- hot reads
- repeated calculations
- session-like fast lookups

Common choices:
- Redis / Valkey
- in-memory local cache

Tradeoffs:
- stale reads
- invalidation complexity

### 6. Database
SQL is strong for:
- transactions
- joins
- consistency
- structured data

NoSQL is strong for:
- flexible schema
- huge scale
- document or key-value access

Always discuss:
- primary key
- indexes
- read/write ratio
- archival strategy

### 7. Message Queue / Event Bus
Use when:
- work should be async
- services should be decoupled
- retries are needed

Examples:
- Kafka
- SQS
- RabbitMQ

### 8. Search System
Use when:
- filtering is rich
- full-text search is needed
- ranking matters

Examples:
- Elasticsearch / OpenSearch

### 9. File / Object Storage
Use for:
- images
- PDFs
- videos
- attachments

Store metadata in DB, file bytes in object storage.

### 10. Scheduler / Cron
Use for:
- reminders
- cleanup
- retries
- campaign execution
- expiry processing

### 11. Monitoring and Observability
Must discuss:
- logs
- metrics
- traces
- alerts
- dashboards

Golden rule:
- if you cannot observe it, you cannot operate it safely.

### 12. Security Layer
Must discuss:
- authentication
- authorization
- encryption
- token expiry
- audit logs
- secrets management

## Most important tradeoffs

### SQL vs NoSQL
- SQL:
  - strong consistency
  - joins
  - transactions
- NoSQL:
  - flexible schema
  - easier horizontal scale for some workloads

### Sync vs Async
- sync:
  - simpler user feedback
  - tighter coupling
- async:
  - better resilience and throughput
  - more eventual consistency

### Strong consistency vs Eventual consistency
- strong consistency:
  - needed for money, quotas, inventory
- eventual consistency:
  - okay for analytics, notifications, feeds

### Monolith vs Microservices
- monolith:
  - simpler to build initially
  - easier local transactions
- microservices:
  - better domain separation
  - higher operational complexity

## Capacity estimation basics
In most interviews, estimate these:
- daily active users
- requests per second
- peak traffic
- average payload size
- daily storage growth
- read/write ratio

Quick formula style:
- `1M requests/day ~= 12 requests/sec average`
- peak is often `5x` to `10x` average

## Database questions you should always answer
- what are the main tables
- what is the primary key
- what fields are indexed
- what is the hottest query
- what grows fastest
- do we need partitioning later

## Caching questions you should always answer
- what exactly is cached
- who invalidates it
- how stale is acceptable
- what happens on cache miss

## Queue questions you should always answer
- what event goes into the queue
- who produces it
- who consumes it
- what happens on retry
- do we need DLQ

## Reliability questions you should always answer
- what if DB is down
- what if provider is down
- what if a message is duplicated
- what if a request times out after partial success

## Security questions you should always answer
- how do users authenticate
- how do services authenticate
- how do we authorize access
- what is logged for audit
- what sensitive data is encrypted or masked

## Good interview sentence starters
- "I’ll first clarify scale and requirements."
- "I’ll separate the write path and read path."
- "I’ll keep the stateless layer horizontally scalable."
- "This part needs strong consistency because..."
- "This part can be asynchronous because..."
- "I’d add idempotency here because retries are expected."

## Final advice
If you remember only one thing, remember this:
- every system design answer should cover users, APIs, data, scale, failures, and tradeoffs.
