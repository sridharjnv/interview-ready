# How To Answer In Interview

## Goal
This file is about speaking well, not only knowing the design.

Many candidates know concepts but still underperform because they:
- jump into random boxes
- forget requirements
- do not discuss tradeoffs
- never explain why they chose something

## Best answer structure

### Step 1. Clarify the problem
Ask 3 to 5 useful questions:
- Who are the users?
- What is the scale?
- What are the key features?
- What matters more: latency, consistency, or cost?
- Are we designing MVP or internet-scale version?

### Step 2. State assumptions
Example:
- "I’ll assume 10 million users, 1 million DAU, and peak traffic 10x average."

This helps the interviewer see that your design choices are grounded.

### Step 3. Define APIs
Even at high level, say:
- create
- read
- update
- search
- async events

Example:
- `POST /payments`
- `GET /users/{id}/feed`
- `POST /messages`

### Step 4. Identify core entities
Say the key tables or documents early.

Example:
- user
- order
- payment
- subscription
- notification_event

### Step 5. Draw high-level architecture
Start simple:
- client
- gateway
- service
- database
- cache
- queue

Do not start with 25 boxes.

### Step 6. Explain one main flow end to end
Pick the most important one:
- creating payment
- sending message
- uploading video
- matching a ride

This is where your answer becomes convincing.

### Step 7. Talk about scale
Always discuss:
- caching
- DB indexes
- read replicas
- queues
- partitioning or sharding if needed

### Step 8. Talk about failures
Always include:
- retries
- idempotency
- timeouts
- fallback
- DLQ if async system exists

### Step 9. Talk about tradeoffs
This is where interviewer sees maturity.

Examples:
- SQL vs NoSQL
- sync vs async
- precompute vs compute on read
- stronger consistency vs lower latency

### Step 10. Close with improvements
End with:
- security
- observability
- future scaling ideas

## A reusable 30-minute answer template

### First 3 minutes
- clarify requirements
- state assumptions
- list core APIs

### Next 7 minutes
- draw high-level design
- explain main entities
- explain one key flow

### Next 10 minutes
- deep dive into scale
- cache
- queue
- database design
- failure handling

### Last 10 minutes
- tradeoffs
- bottlenecks
- edge cases
- future improvements

## What interviewers usually look for
- can you structure ambiguity
- can you identify the important parts
- can you protect correctness
- can you discuss scale sensibly
- can you justify decisions

## What to say when stuck
- "I’ll start with a simple version and then scale it."
- "For now I’ll optimize correctness first because this is money/inventory."
- "This can be made asynchronous to reduce latency on the user-facing path."
- "I’d put a cache here because the read-to-write ratio is likely high."

## Common mistakes
- drawing too many boxes too early
- never mentioning database schema
- forgetting rate limiting
- forgetting idempotency
- saying microservices for everything
- using NoSQL without reason
- not asking scale questions

## How to use your Aurum feature docs
Use them as real examples when answering generic questions.

Example mapping:
- payment system -> payment link, retries, webhook docs
- notification system -> notification platform docs
- access control -> RBAC and token permission docs
- targeting -> segmentation and audience docs
- campaign system -> scheduled notifications and ad campaigns docs

## One golden answer formula
Say this in your own words:
"I’ll first define requirements and scale, then propose a simple high-level architecture, then deep dive into storage, scaling, failure handling, and tradeoffs."

## Final advice
Interviewers are not expecting perfection.
They are checking whether you think like an engineer who can design systems safely and communicate clearly.
