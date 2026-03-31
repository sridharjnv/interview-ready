# Top Generic System Design Problems

## Why this file exists
Your Aurum feature docs give real product examples, but interviews also ask classic generic system design problems. These are the ones you should definitely practice.

## Tier 1: Must practice

### 1. Design a URL Shortener
What to learn:
- key generation
- redirect latency
- DB lookup
- caching
- analytics

Good Aurum connection:
- token generation and lookup patterns

### 2. Design a Notification System
What to learn:
- fanout
- retries
- templates
- scheduling
- multi-channel delivery

Good Aurum connection:
- docs 21 and 22

### 3. Design a Payment System
What to learn:
- idempotency
- transaction lifecycle
- webhook processing
- reconciliation

Good Aurum connection:
- docs 1 to 4 and 7

### 4. Design a Chat System
What to learn:
- websocket connections
- message persistence
- delivery acknowledgement
- ordering
- online presence

### 5. Design a Ride-Hailing System
What to learn:
- location updates
- nearest-driver search
- matching
- pricing
- live state updates

### 6. Design a Social Media Feed
What to learn:
- fanout on write vs fanout on read
- ranking
- caching
- storage growth

### 7. Design an E-commerce System
What to learn:
- catalog
- cart
- order
- payment
- inventory

Good Aurum connection:
- products, store, offers, payments

## Tier 2: Very commonly asked

### 8. Design YouTube / Video Streaming
What to learn:
- upload pipeline
- transcoding
- CDN
- metadata storage

### 9. Design a Rate Limiter
What to learn:
- token bucket
- sliding window
- distributed counters

### 10. Design an API Gateway
What to learn:
- auth
- routing
- throttling
- observability

### 11. Design Dropbox / File Storage
What to learn:
- chunking
- deduplication
- sync
- conflict resolution

### 12. Design Search / Autocomplete
What to learn:
- trie or index
- prefix search
- ranking
- freshness

## Tier 3: Strong differentiators

### 13. Design an Ad Serving System
What to learn:
- targeting
- campaign storage
- serving latency
- pacing
- analytics

Good Aurum connection:
- ad campaigns + audience + segmentation

### 14. Design a Recommendation System
What to learn:
- candidate generation
- ranking
- feedback loop
- online vs offline serving

### 15. Design an Online Auction / Bidding System
What to learn:
- real-time bids
- fairness
- anti-sniping
- settlement

Good Aurum connection:
- bidding marketplace docs

### 16. Design an Event Booking Platform
What to learn:
- seat locking
- timeouts
- payments
- oversell prevention

Good Aurum connection:
- exhibition/event workflows

### 17. Design a Subscription Platform
What to learn:
- plans
- entitlements
- billing
- renewals

Good Aurum connection:
- docs 5, 6, 8

## Best practice order
1. Notification system
2. Payment system
3. URL shortener
4. Chat system
5. Feed system
6. E-commerce
7. Ride-hailing
8. Search or autocomplete
9. Ad serving
10. Auction system

## How to practice each problem
For every problem:
- define requirements
- estimate scale
- define APIs
- list entities
- draw high-level design
- explain one core flow
- discuss scale
- discuss failures
- discuss tradeoffs

## Best mapping from your repo to generic problems
- Payments -> Aurum payment docs
- Notifications -> Aurum notification docs
- Subscription platform -> Aurum subscription docs
- Marketplace / auction -> Aurum bidding docs
- Ad serving -> Aurum ad campaign docs
- User targeting -> Aurum segmentation and audience docs
- RBAC -> Aurum role and token docs

## Final advice
If you master the 27 real Aurum features and also practice the Tier 1 generic problems above, you will have a very strong system design preparation base.
