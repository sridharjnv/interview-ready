# Semantic Search Service - Interview Preparation Guide

> **Target Role**: SDE-1 | **Experience Level**: 1 YOE | **Project**: Enterprise Semantic Search Service

---

## 📋 How to Explain This Project (The 2-Minute Pitch)

> **Interviewer**: "Tell me about a project you've worked on."

### Strong Response Template:

"I designed and implemented a **Semantic Search Service** using Python that enables **intelligent company matching** for enterprise clients. The service has **two main components**:

1. **Data Ingestion Pipeline** - An ETL service that extracts company data from Snowflake, generates AI embeddings using **AWS Bedrock Titan**, and indexes them into **Elasticsearch/OpenSearch** for fast retrieval.

2. **Reconciliation Service** - An event-driven **AWS Lambda** function that processes uploaded company lists and matches them against our database using a **three-tier matching strategy**: exact DUNS match, domain match, and semantic vector similarity.

**Key Technical Highlights**:
- Processed **millions of records** using parallel batch processing with **ThreadPoolExecutor**
- Implemented **512-dimensional vector embeddings** for semantic search
- Used **async/await patterns** for non-blocking I/O operations
- Designed for **fault tolerance** with retry mechanisms and status tracking

**Impact**: Reduced manual company matching time from hours to seconds with high accuracy confidence scores."

---

## 🏗️ Architecture Overview

```
┌─────────────────────────────────────────────────────────────────────────┐
│                        SEMANTIC SEARCH SERVICE                          │
└─────────────────────────────────────────────────────────────────────────┘
                                    │
           ┌────────────────────────┼────────────────────────┐
           ▼                        ▼                        ▼
┌─────────────────────┐  ┌─────────────────────┐  ┌─────────────────────┐
│   DATA INGESTION    │  │   RECONCILIATION    │  │    SEARCH ENGINE    │
│   (Batch ETL)       │  │   (Event-Driven)    │  │   (Elasticsearch)   │
└─────────────────────┘  └─────────────────────┘  └─────────────────────┘
         │                        │                        │
    Snowflake                 AWS Lambda               OpenSearch
    + Bedrock                 + SQS                    + KNN Index
```

### Tech Stack Summary

| Component | Technology | Purpose |
|-----------|------------|---------|
| Language | Python 3.x | Core application |
| Data Warehouse | Snowflake | Source of company data |
| Search Engine | Elasticsearch/OpenSearch | Fast full-text + vector search |
| AI/ML | AWS Bedrock Titan | 512-dim vector embeddings |
| Serverless | AWS Lambda | Event processing |
| Message Queue | AWS SQS | Async event-driven architecture |
| Database | PostgreSQL | Status tracking & metadata |
| ORM | SQLAlchemy | Database abstraction |
| Config | Dynaconf | Environment-based configuration |

---

## 🔑 Core Technical Concepts to Master

### 1. Vector Embeddings & Semantic Search

**What are embeddings?**
- Dense numerical representations of text in high-dimensional space (512 dimensions)
- Similar meanings → similar vectors → closer in vector space
- Enables matching "Acme Corp" to "Acme Corporation" or even "ACME LLC"

**How KNN Search works:**
```python
# Elasticsearch KNN Query
query = {
    "query": {
        "knn": {
            "COMPANY_VECTOR": {
                "vector": [0.1, 0.2, ...],  # 512 dimensions
                "k": 1  # Return top 1 match
            }
        }
    }
}
```

**Why Cosine Similarity?**
- Measures angle between vectors, not magnitude
- Score of 1.0 = identical, 0.0 = orthogonal
- Works well for text similarity comparison

---

### 2. Three-Tier Matching Strategy

```
┌─────────────────────────────────────────────────────────────────┐
│                    MATCHING PRIORITY                             │
└─────────────────────────────────────────────────────────────────┘

 TIER 1: DUNS Match (Highest Confidence = 1.0)
    │    └─ Exact match on D&B Universal Numbering System
    │
    ▼ (if no match)
 TIER 2: Domain Match (High Confidence = 1.0)  
    │    └─ Exact match on normalized website domain
    │
    ▼ (if no match)
 TIER 3: Vector Similarity (Variable Confidence)
    │    └─ Semantic match using AI embeddings
    │
    ▼ (if no match)
 NO_MATCH (Confidence = 0)
```

**Why this order?**
- DUNS is the most reliable business identifier (unique per company)
- Exact domain matching is faster and deterministic
- Vector search is expensive (API call) but flexible for fuzzy matching

---

### 3. Parallel Processing Architecture

**Two-Level Parallelization:**

```python
# Level 1: Batch-level (10 concurrent batches)
with ThreadPoolExecutor(max_workers=10) as executor:
    for batch in batches:
        executor.submit(process_single_batch_es, batch)

# Level 2: Item-level (100 concurrent items per batch)
with ThreadPoolExecutor(max_workers=100) as executor:
    for item in batch:
        executor.submit(process_item, item)
```

**Why ThreadPoolExecutor over multiprocessing?**
- I/O-bound operations (API calls, network requests)
- Lower memory overhead than ProcessPoolExecutor
- GIL not a bottleneck for I/O operations

---

### 4. Error Handling & Resilience

**Retry Strategy:**
```python
def index_to_es_with_retry(es_data, batch_id, max_retries=3):
    for attempt in range(max_retries):
        try:
            helpers.bulk(es.client, es_data)
            return True
        except Exception as e:
            if attempt < max_retries - 1:
                logger.warning(f"Attempt {attempt + 1} failed. Retrying...")
            else:
                logger.error(f"Failed after {max_retries} attempts")
    return False
```

**Thread-Safe Status Logging:**
```python
lock = threading.Lock()

def log_batch_status(batch_id, status):
    with lock:  # Prevents race conditions
        with open("batch_status.csv", 'a') as file:
            writer.writerow([batch_id, status])
```

---

## ❓ Interview Questions & Answers

### System Design Questions

---

**Q1: "Why did you choose Elasticsearch over a traditional database for search?"**

> **Answer**: "Traditional relational databases are optimized for exact matching and transactional operations. Elasticsearch excels at full-text search with features like:
> - **Inverted index** for fast text lookups
> - **Native KNN support** for vector similarity searches
> - **Horizontal scaling** with sharding
> - **Near real-time** indexing
> 
> For our use case of matching company names with typos and variations, Elasticsearch's combination of keyword matching and vector search was ideal."

---

**Q2: "How would you scale this system to handle 100 million records?"**

> **Answer**: "Several strategies:
> 1. **Elasticsearch scaling**: Add more shards and replicas, use index lifecycle management
> 2. **Batch processing**: Increase parallelism with distributed computing (e.g., AWS EMR, Spark)
> 3. **Embedding optimization**: Batch embedding generation to reduce API calls
> 4. **Caching**: Cache frequently queried embeddings in Redis
> 5. **Index partitioning**: Time-based or region-based index sharding
> 6. **Queue-based processing**: Use SQS/Kafka for reliable message processing"

---

**Q3: "What happens if AWS Bedrock is unavailable during processing?"**

> **Answer**: "We have multiple resilience layers:
> 1. **Retry with backoff**: The Bedrock client has built-in retries (10 attempts max)
> 2. **Graceful degradation**: If embedding fails, the record is marked as 'no match' rather than crashing the batch
> 3. **Failed record tracking**: We log failed MODEL_IDs to a CSV for reprocessing
> 4. **Transaction isolation**: Individual record failures don't affect the entire batch
> 5. **Resume capability**: The `initial_offset` parameter allows resuming from interruption points"

---

**Q4: "Why Lambda over a constantly running service?"**

> **Answer**: "Lambda provides several advantages for this use case:
> 1. **Cost efficiency**: Pay only for actual processing time
> 2. **Auto-scaling**: Handles variable load without manual scaling
> 3. **Event integration**: Seamlessly integrates with SQS triggers
> 4. **Managed infrastructure**: No server maintenance
> 
> However, for the data ingestion pipeline (bulk processing), we use a standalone script because Lambda has a 15-minute timeout limit."

---

### Coding & Implementation Questions

---

**Q5: "Explain the async/await pattern in your reconciliation service."**

> **Answer**: "We use async/await to handle I/O-bound operations efficiently:
> ```python
> async def process_records_batch(session, records, batch_id, product_name):
>     # Create async tasks for parallel processing
>     tasks = [process_record(session, record, product_config) 
>              for record in records]
>     await asyncio.gather(*tasks)  # Execute concurrently
>     session.commit()
> ```
> 
> The key benefit is that while waiting for one Elasticsearch query, we can process other records. The `asyncio.to_thread()` wrapper converts sync functions to non-blocking calls."

---

**Q6: "How do you handle thread safety when multiple threads write to the same file?"**

> **Answer**: "We use a mutex lock:
> ```python
> lock = threading.Lock()
> 
> def log_batch_status(batch_id, status):
>     with lock:  # Only one thread can enter
>         with open('batch_status.csv', 'a') as file:
>             writer.writerow([batch_id, status])
> ```
> 
> The `with lock` context manager ensures mutual exclusion. Alternative approaches include using a Queue or atomic file operations."

---

**Q7: "Why do you normalize DUNS numbers and URLs before matching?"**

> **Answer**: "Data quality is crucial for accurate matching:
> 
> **DUNS cleaning**: Remove dashes/spaces, pad to 9 digits
> ```python
> '123-456-789' → '123456789'
> '1234567'     → '001234567' (padded)
> ```
> 
> **URL normalization**: Extract clean domain
> ```python
> 'https://www.acme.com/about' → 'acme.com'
> 'HTTP://WWW.ACME.COM'        → 'acme.com'
> ```
> 
> This ensures 'www.acme.com' matches 'https://acme.com'."

---

**Q8: "What is the time complexity of your matching algorithm?"**

> **Answer**: 
> - **DUNS/Domain matching**: O(1) - Elasticsearch term queries use hash-based lookups
> - **Vector embedding generation**: O(n) - where n is text length, AWS Bedrock is the bottleneck
> - **KNN search**: O(log k) - Elasticsearch uses approximate nearest neighbor (HNSW algorithm)
> - **Overall per record**: O(1) + O(n) + O(log k) ≈ **O(n)** dominated by embedding generation
> 
> For M records with B batch size and T threads:
> - Parallel processing reduces time to: O(M × n / (B × T))

---

### Python-Specific Questions

---

**Q9: "ThreadPoolExecutor vs ProcessPoolExecutor - when to use which?"**

> **Answer**: 
> | Aspect | ThreadPoolExecutor | ProcessPoolExecutor |
> |--------|-------------------|---------------------|
> | Use case | I/O-bound (API calls, file I/O) | CPU-bound (computations) |
> | GIL impact | No issue (releases GIL during I/O) | Bypasses GIL completely |
> | Memory | Shared memory | Separate processes |
> | Overhead | Low (thread creation) | High (process creation) |
> 
> We chose ThreadPoolExecutor because our work is I/O-bound (waiting for Elasticsearch/Bedrock APIs)."

---

**Q10: "Explain the as_completed pattern you used."**

> **Answer**: "as_completed() yields futures as they complete, not in submission order:
> ```python
> with ThreadPoolExecutor(max_workers=10) as executor:
>     futures = {executor.submit(process, batch): batch_id 
>                for batch_id, batch in enumerate(batches)}
>     
>     for future in as_completed(futures):
>         batch_id = futures[future]
>         result = future.result()
>         # Process result immediately, don't wait for others
> ```
> 
> This maximizes throughput by processing results as soon as they're available."

---

### Database & Data Modeling

---

**Q11: "Explain your Elasticsearch index mapping for vector search."**

> **Answer**: "Key fields in our index:
> ```json
> {
>   'COMPANY_BUSINESS_NAME': {'type': 'text', 'fields': {'keyword': {...}}},
>   'DUNS_NUMBER': {'type': 'keyword'},
>   'WEBSITE': {'type': 'text', 'fields': {'keyword': {...}}},
>   'COMPANY_VECTOR': {'type': 'knn_vector', 'dimension': 512},
>   'MODEL_ID': {'type': 'keyword'}
> }
> ```
> 
> - `knn_vector` field enables approximate nearest neighbor search
> - `.keyword` subfields allow exact matching
> - `text` fields are analyzed for full-text search"

---

**Q12: "How do you prevent duplicate status updates in your PostgreSQL tracking?"**

> **Answer**: "We implement idempotency checks:
> ```python
> def update_upload_list_status(session, list_uid, product_name, new_status):
>     # Check current status before update
>     current = session.query(UploadListMetaInfo).filter_by(
>         list_uid=list_uid
>     ).first()
>     
>     # Prevent overwriting 'failed' status
>     if current.status_description == 'reconciliation_failed':
>         logger.info('Status already failed, skipping update')
>         return False
>     
>     current.status_description = new_status
>     session.commit()
> ```
> 
> This ensures we don't accidentally mark a failed job as 'in_progress'."

---

### AWS Questions

---

**Q13: "How does AWS Bedrock Titan embedding work?"**

> **Answer**: "AWS Bedrock is a managed ML inference service:
> ```python
> client = boto3.client('bedrock-runtime')
> 
> payload = {
>     'inputText': 'Acme Corporation',
>     'dimensions': 512
> }
> 
> response = client.invoke_model(
>     modelId='amazon.titan-embed-text-v2:0',
>     contentType='application/json',
>     body=json.dumps(payload)
> )
> 
> embedding = json.loads(response['body'].read())['embedding']
> # Returns: [0.123, 0.456, ..., 0.789]  # 512 floats
> ```
> 
> Key features:
> - No model training required (managed embeddings)
> - Serverless pricing (pay per token)
> - Low latency (~100ms per call)"

---

**Q14: "How does the SQS event-driven pattern work in your Lambda?"**

> **Answer**: "The flow is:
> 1. **User uploads list** → API creates SQS message
> 2. **SQS triggers Lambda** with event containing batch info
> 3. **Lambda processes** the batch asynchronously
> 4. **On completion** → Lambda sends event to target queue
> 5. **On failure** → Lambda sends failure event with error details
> 
> Benefits:
> - Decoupled components
> - Automatic retries (SQS retry policy)
> - Dead letter queue for failed messages
> - Scales automatically with queue depth"

---

## 📂 End-to-End Feature Flow: What Happens When User Uploads .xlsx File?

> **This is a KEY interview question**: "Walk me through what happens when a user uploads a file."

### Complete System Flow Diagram

```
┌─────────────────────────────────────────────────────────────────────────────────────────┐
│                           USER UPLOADS .XLSX FILE                                       │
└─────────────────────────────────────────────────────────────────────────────────────────┘
                                        │
                                        ▼
┌─────────────────────────────────────────────────────────────────────────────────────────┐
│  STEP 1: FRONTEND/API LAYER (External Service)                                          │
│  ┌─────────────────────────────────────────────────────────────────────────────────┐   │
│  │  • User selects .xlsx file in UI                                                 │   │
│  │  • Frontend validates file format                                                │   │
│  │  • API parses Excel file → extracts company records                              │   │
│  │  • Creates list_uid (unique identifier)                                          │   │
│  │  • Inserts records into PostgreSQL (upload_list_records table)                  │   │
│  │  • Creates batches: batch_0_1000, batch_1000_2000, etc.                          │   │
│  │  • Sends SQS message: "upload-list-reconciliation-started"                       │   │
│  └─────────────────────────────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────────────────────────────┘
                                        │
                                        ▼
┌─────────────────────────────────────────────────────────────────────────────────────────┐
│  STEP 2: SQS SOURCE QUEUE                                                               │
│  ┌─────────────────────────────────────────────────────────────────────────────────┐   │
│  │  Queue: kp-upload-list-batch-reconciliation-queue                               │   │
│  │  Message:                                                                         │   │
│  │  {                                                                                │   │
│  │    "event_name": "upload-list-reconciliation-started",                           │   │
│  │    "body": {                                                                      │   │
│  │      "list_uid": "abc-123",                                                       │   │
│  │      "batch_id": "batch_0_1000",                                                  │   │
│  │      "product_name": "wallet",                                                    │   │
│  │      "list_name": "Q4 Prospects"                                                  │   │
│  │    }                                                                              │   │
│  │  }                                                                                │   │
│  └─────────────────────────────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────────────────────────────┘
                                        │
                                        ▼
┌─────────────────────────────────────────────────────────────────────────────────────────┐
│  STEP 3: AWS LAMBDA TRIGGERED                                                           │
│  ┌─────────────────────────────────────────────────────────────────────────────────┐   │
│  │  Entry Point: app/main.py → aws_lambda_handler()                                 │   │
│  │                                                                                   │   │
│  │  async def lambda_handler(event, context):                                        │   │
│  │      for record in event['Records']:                                              │   │
│  │          message_data = json.loads(record['body'])                                │   │
│  │          await process_message(message_data, sqs_client)  # ← Route to handler    │   │
│  │          await delete_event(sqs_client, record['receiptHandle'])                 │   │
│  └─────────────────────────────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────────────────────────────┘
                                        │
                                        ▼
┌─────────────────────────────────────────────────────────────────────────────────────────┐
│  STEP 4: EVENT ROUTER (kp_list_batch_reconciliation_queue_receiver.py)                 │
│  ┌─────────────────────────────────────────────────────────────────────────────────┐   │
│  │  async def handle_event(event_data, sqs_client):                                  │   │
│  │      if event_name == "upload-list-reconciliation-started":                       │   │
│  │          status = await reconcile_service.get_records_and_reconcile(...)         │   │
│  │          if status:                                                               │   │
│  │              await send_reconciliation_completed_event(...)  # ✓ Success          │   │
│  │          else:                                                                    │   │
│  │              await send_reconciliation_failed_event(...)      # ✗ Failure         │   │
│  └─────────────────────────────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────────────────────────────┘
                                        │
                                        ▼
┌─────────────────────────────────────────────────────────────────────────────────────────┐
│  STEP 5: RECONCILIATION SERVICE (reconcile_service.py)                                  │
│  ┌─────────────────────────────────────────────────────────────────────────────────┐   │
│  │  1. Parse batch_id → extract start_index, end_index                              │   │
│  │  2. Update list status → "reconciliation_in_progress"                            │   │
│  │  3. Loop: Fetch records from PostgreSQL (50 at a time)                           │   │
│  │  4. Process each batch → call es_query_processor                                  │   │
│  │  5. Update batch status → "completed" or "failed"                                │   │
│  │  6. Commit transaction to PostgreSQL                                              │   │
│  └─────────────────────────────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────────────────────────────┘
                                        │
                                        ▼
┌─────────────────────────────────────────────────────────────────────────────────────────┐
│  STEP 6: MATCHING ENGINE (es_query_processor.py) - Per Record                          │
│  ┌─────────────────────────────────────────────────────────────────────────────────┐   │
│  │  async def process_record(session, record, product_config):                       │   │
│  │      # TRY DUNS MATCH FIRST                                                       │   │
│  │      if duns_number:                                                              │   │
│  │          result = query_by_duns(index, duns_number)                               │   │
│  │          if match: return ← set matched_criteria = "DUNS"                         │   │
│  │                                                                                   │   │
│  │      # TRY DOMAIN MATCH                                                           │   │
│  │      if website:                                                                  │   │
│  │          result = query_by_website(index, normalized_website)                     │   │
│  │          if match: return ← set matched_criteria = "DOMAIN"                       │   │
│  │                                                                                   │   │
│  │      # FALLBACK TO VECTOR SIMILARITY                                              │   │
│  │      embedding = get_vectors_for_each_group(company_name)  # AWS Bedrock call     │   │
│  │      result = query_top_match_knn(index, embedding)                               │   │
│  │      if match: set matched_criteria = "SIMILARITY", confidence_score             │   │
│  │      else: set matched_criteria = "NO_MATCH"                                      │   │
│  │                                                                                   │   │
│  │      session.add(record)  ← Update PostgreSQL with match results                  │   │
│  └─────────────────────────────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────────────────────────────┘
                                        │
                                        ▼
┌─────────────────────────────────────────────────────────────────────────────────────────┐
│  STEP 7: POSTGRESQL UPDATED (upload_list_records table)                                 │
│  ┌─────────────────────────────────────────────────────────────────────────────────┐   │
│  │  Before Processing:                     After Processing:                         │   │
│  │  ┌──────────────────────────────┐      ┌──────────────────────────────────────┐  │   │
│  │  │ record_id: 1                 │      │ record_id: 1                         │  │   │
│  │  │ company_name: "Acme Corp"    │  →   │ matched_company_id: "12345"          │  │   │
│  │  │ matched_criteria: NULL       │      │ matched_criteria: "SIMILARITY"       │  │   │
│  │  │ status: "pending"            │      │ matched_threshold: 0.92              │  │   │
│  │  └──────────────────────────────┘      │ confidence_level: "High"             │  │   │
│  │                                         │ status: "reconciliation_completed"   │  │   │
│  │                                         └──────────────────────────────────────┘  │   │
│  └─────────────────────────────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────────────────────────────┘
                                        │
                                        ▼
┌─────────────────────────────────────────────────────────────────────────────────────────┐
│  STEP 8: SQS TARGET QUEUE - COMPLETION EVENT SENT                                       │
│  ┌─────────────────────────────────────────────────────────────────────────────────┐   │
│  │  Queue: kp-upload-list-request-queue                                             │   │
│  │  Message:                                                                         │   │
│  │  {                                                                                │   │
│  │    "event_name": "upload-list-reconciliation-completed",  // or "failed"          │   │
│  │    "body": { "list_uid": "abc-123", "batch_id": "batch_0_1000", ... }             │   │
│  │  }                                                                                │   │
│  └─────────────────────────────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────────────────────────────┘
                                        │
                                        ▼
┌─────────────────────────────────────────────────────────────────────────────────────────┐
│  STEP 9: RESPONSE TO USER (External Service Consumes Target Queue)                     │
│  ┌─────────────────────────────────────────────────────────────────────────────────┐   │
│  │  • Target queue triggers downstream API/notification service                     │   │
│  │  • API queries PostgreSQL for final results                                       │   │
│  │  • Results returned to frontend:                                                  │   │
│  │    - List of matched companies with confidence scores                             │   │
│  │    - Unmatched records for manual review                                          │   │
│  │    - Downloadable .xlsx with enriched data                                        │   │
│  │  • User sees results in UI with "High/Medium/Low" confidence indicators          │   │
│  └─────────────────────────────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────────────────────────────┘
```

### Status Transitions Throughout the Flow

```
LIST STATUS (upload_list_meta_info table):
┌──────────────────────────────┐
│  reconciliation_started      │ ← Initial state when file uploaded
└──────────────┬───────────────┘
               ▼
┌──────────────────────────────┐
│  reconciliation_in_progress  │ ← Lambda starts processing
└──────────────┬───────────────┘
               ▼
       ┌───────┴───────┐
       ▼               ▼
┌────────────────┐  ┌────────────────────┐
│  completed     │  │  failed            │
│  ✓ All matched │  │  ✗ Error occurred  │
└────────────────┘  └────────────────────┘

BATCH STATUS (list_ingestion_batch_status table):
ingestion_started → in_progress → completed/failed
```

### Key Components Involved

| Component | File | Responsibility |
|-----------|------|----------------|
| Lambda Handler | `app/main.py` | Entry point, iterates SQS records |
| Event Router | `kp_list_batch_reconciliation_queue_receiver.py` | Routes events to reconcile service |
| Reconcile Service | `reconcile_service.py` | Orchestrates batch processing loop |
| Query Processor | `es_query_processor.py` | 3-tier matching logic per record |
| Message Sender | `kp_upload_list_request_queue_sender.py` | Sends completion/failure events |
| Status Service | `upload_list_meta_info_service.py` | Updates list-level status |

### Interview-Ready Explanation

> **Interviewer**: "What happens when a user uploads an Excel file?"

**Strong Answer**:

"The system uses an **event-driven architecture** with multiple stages:

1. **File Upload**: User uploads .xlsx → API parses it, inserts records into PostgreSQL, and sends an **SQS message** with batch details.

2. **Lambda Trigger**: SQS automatically triggers our Lambda function. The message contains `list_uid` and `batch_id` (e.g., `batch_0_1000`).

3. **Async Processing**: Lambda processes records in configurable batch sizes (50 records). For each record:
   - First try **DUNS match** (O(1) exact lookup)
   - Then **domain match** (O(1) exact lookup)  
   - Finally **vector similarity** using AWS Bedrock embeddings + Elasticsearch KNN

4. **Database Updates**: We update PostgreSQL with match results (`matched_criteria`, `confidence_level`, `matched_company_id`).

5. **Completion Notification**: Lambda sends a completion event to a **target SQS queue**, which triggers the API to notify the user.

6. **User Response**: The frontend polls for results or receives a webhook, then displays matched companies with **confidence indicators** (High/Medium/Low).

**Key design decisions**:
- **SQS decoupling** prevents API timeouts for large files
- **Batch processing** handles memory efficiently
- **Status tracking** enables progress monitoring and resume capability
- **Idempotent updates** prevent duplicate processing"

---

### Related Interview Q&A

**Q15: "How do you handle partial failures during batch processing?"**

> **Answer**: "We have graceful degradation at multiple levels:
> 1. **Record-level**: If one record fails (e.g., embedding API error), we mark it as `NO_MATCH` and continue with others
> 2. **Batch-level**: We commit successful records, then send a `batch-reconciliation-failed` event
> 3. **Transaction rollback**: SQLAlchemy handles rollback on database errors
> 4. **SQS visibility timeout**: If Lambda crashes, message becomes visible again for retry
> ```python
> except Exception as e:
>     session.rollback()
>     await send_reconciliation_failed_event(failure_event_data, sqs_client)
> ```"

---

**Q16: "Why use two separate SQS queues (source and target)?"**

> **Answer**: "This implements the **request-response pattern** in async systems:
> - **Source Queue**: Receives incoming work requests
> - **Target Queue**: Delivers completion notifications
> 
> Benefits:
> 1. **Decoupled producers/consumers**: Upload API doesn't wait for processing
> 2. **Different subscribers**: Multiple services can react to completion events
> 3. **Retry isolation**: Failures in one queue don't affect the other
> 4. **Observability**: Clear separation of ingress vs. egress events"

---

**Q17: "How does the user know processing is complete?"**

> **Answer**: "We support both **polling** and **push** mechanisms:
> 
> **Polling approach**:
> - Frontend polls `/api/lists/{list_uid}/status` every few seconds
> - API reads from `upload_list_meta_info.status_description`
> 
> **Push approach**:
> - Target queue triggers notification service
> - WebSocket or Server-Sent Events push updates to connected clients
> 
> We also track progress granularly:
> - `total_records`: Total uploaded
> - Per-record `status`: individually tracked
> - User can see live progress (e.g., '450/1000 records processed')"

---

## 🎯 Key Metrics to Mention

| Metric | Value | Context |
|--------|-------|---------|
| Vector Dimensions | 512 | Good balance of accuracy vs. storage |
| Batch Size | 100 records | Optimized for memory and throughput |
| Parallel Threads | 10-100 | Tuned based on I/O vs. compute |
| Retry Attempts | 3 | Industry standard for transient failures |
| ES Query Time | ~50-100ms | Sub-second response |
| Embedding Time | ~100-200ms | AWS Bedrock API latency |

---

## ⚠️ Common Follow-Up Questions

1. **"What would you do differently if starting over?"**
   - Consider using async HTTP client like `aiohttp` for ES queries
   - Add circuit breaker pattern for external service calls
   - Implement batch embedding API for efficiency

2. **"What challenges did you face?"**
   - Cold start latency in Lambda
   - Rate limiting on AWS Bedrock API
   - Data quality issues (invalid DUNS, malformed URLs)

3. **"How do you test this system?"**
   - Unit tests for utility functions (normalization, cleaning)
   - Integration tests with LocalStack (mocked AWS services)
   - Local OpenSearch container for Elasticsearch tests

---

## 📚 Terms to Know Cold

| Term | Definition |
|------|-----------|
| **ETL** | Extract, Transform, Load - data pipeline pattern |
| **KNN** | K-Nearest Neighbors - similarity search algorithm |
| **Cosine Similarity** | Metric measuring angle between vectors |
| **Vector Embedding** | Dense numerical representation of text |
| **Inverted Index** | Data structure for fast full-text search |
| **Sharding** | Horizontal partitioning of database |
| **Idempotency** | Same operation yields same result |
| **Circuit Breaker** | Pattern to prevent cascading failures |
| **Dead Letter Queue** | Queue for failed messages |
| **Connection Pooling** | Reusing database connections |

---

*Good luck with your interviews! Remember: explain your thought process, not just the implementation.*
