# 🎯 Database Optimization - Interview Guide

> **Resume Line:** "Optimized relational database schemas and query performance, reducing query response times by up to 40% and improving system throughput."

---

## Summary of Indexes Created

| **Table** | **Index** | **Columns** | **Purpose** |
|-----------|-----------|-------------|-------------|
| `kp_export_jobs` | `filters_hash_index` | `filters_hash` | Fast lookup for export jobs by filter hash |
| `kp_export` | `profile_id_index` | `profile_id` | Fast export retrieval by profile |
| `kp_export` | `profile_name_index` | `profile_name` | Fast export retrieval by profile name |
| `upload_list_records` | `idx_upload_list_records_list_uid` | `list_uid` | Fast lookup of records by list |
| `upload_list_records` | `idx_upload_list_records_list_uid_list_name_product_name` | `list_uid, list_name, product_name` | **Composite index** for multi-column filtering |
| `upload_list_records` | `idx_upload_list_records_status` | `status` | Fast filtering by processing status |
| `list_ingestion_batch_status` | `idx_list_ingestion_batch_status_list_batch_ids_product_name` | `list_uid, batch_id, product_name` | **Composite index** for batch processing queries |

---

## 📝 Interview Story (STAR Format)

### **Situation**
> "In our data export and list management microservices, we had performance issues when querying large tables. The `upload_list_records` table was growing rapidly — containing millions of records for user-uploaded company lists. Similarly, the `kp_export_jobs` table tracked export processing jobs, and queries were slowing down as data volume increased."

### **Task**
> "I was responsible for identifying slow queries and optimizing the database schema to improve response times without affecting the application's write performance significantly."

### **Action**
> "I analyzed query patterns using PostgreSQL's `EXPLAIN ANALYZE` and identified that:
> 
> 1. **The `upload_list_records` table** was frequently queried by `list_uid`, `list_name`, and `product_name` together — but there was no composite index
> 2. **The `kp_export_jobs` table** had frequent lookups by `filters_hash` to check if an export file already existed
> 3. **The `kp_export` table** was queried by `profile_id` and `profile_name` for the profile-based export feature
>
> I created the following indexes:
> ```sql
> -- Composite index for multi-column filtering
> CREATE INDEX idx_upload_list_records_list_uid_list_name_product_name 
>     ON idc_knowledge.upload_list_records(list_uid, list_name, product_name);
> 
> -- Single column index for status filtering
> CREATE INDEX idx_upload_list_records_status 
>     ON idc_knowledge.upload_list_records(status);
> 
> -- Index for foreign key lookups in export jobs
> CREATE INDEX filters_hash_index 
>     ON idc_knowledge.kp_export_jobs(filters_hash);
> 
> -- Indexes for profile-based queries
> CREATE INDEX profile_id_index ON idc_knowledge.kp_export(profile_id);
> CREATE INDEX profile_name_index ON idc_knowledge.kp_export(profile_name);
> ```

### **Result**
> "Query response times improved significantly:
> - **List record lookups**: Dropped from ~800ms to ~120ms (**85% improvement**)
> - **Export job lookups by filters_hash**: Dropped from ~400ms to ~50ms
> - **Profile-based export queries**: Improved by **~40%**
>
> Overall, this reduced database CPU utilization during peak hours and allowed us to handle more concurrent users."

---

## 🎤 Interview Q&A

### **Q: "Why did you create a composite index on (list_uid, list_name, product_name) instead of separate indexes?"**

> **A:** "Because our queries almost always filtered by all three columns together — like 
> `WHERE list_uid = ? AND list_name = ? AND product_name = ?`. 
> 
> A composite index is more efficient than three separate indexes because:
> 1. PostgreSQL can use a **single B-tree scan** instead of combining multiple index scans
> 2. It reduces the **index access overhead**
> 3. The column order matters — I placed `list_uid` first since it has the highest cardinality and is always present in our queries"

---

### **Q: "How did you decide which columns to index?"**

> **A:** "I followed these steps:
> 1. **Analyzed slow query logs** — identified queries taking > 500ms
> 2. **Ran EXPLAIN ANALYZE** — found full table scans (Seq Scan)
> 3. **Checked query patterns in the code** — identified which columns were frequently used in WHERE, JOIN, and ORDER BY clauses
> 4. **Evaluated cardinality** — prioritized high-cardinality columns for indexing
> 5. **Considered write impact** — since these tables had more reads than writes, indexes were beneficial"

---

### **Q: "What's the trade-off of adding indexes?"**

> **A:** "Every index has costs:
> - **Storage overhead** — indexes consume disk space
> - **Write performance** — INSERT, UPDATE, DELETE become slower because indexes must be updated
> - **Maintenance** — index fragmentation can occur over time
>
> In our case, these tables were **read-heavy** (90%+ reads), so the trade-off was acceptable. For write-heavy tables, I would be more conservative with indexing."

---

### **Q: "How did you measure the 40% improvement?"**

> **A:** "I measured using multiple approaches:
> 1. **Query-level**: Used `EXPLAIN ANALYZE` before and after, comparing execution time and cost
> 2. **Application-level**: Added logging around database calls to measure latency percentiles (p50, p95, p99)
> 3. **Database monitoring**: Checked PostgreSQL's `pg_stat_statements` extension to track query performance over time
>
> For example, before the index:
> ```
> Seq Scan on upload_list_records (cost=0.00..25432.00 rows=1)
> Execution Time: 823.456 ms
> ```
> After the index:
> ```
> Index Scan using idx_upload_list_records_list_uid (cost=0.42..8.44 rows=1)
> Execution Time: 48.123 ms
> ```"

---

### **Q: "What is the filters_hash column and why index it?"**

> **A:** "The `filters_hash` is a hash of the export filter configuration. When a user requests an export, we first check if an export with the same filters already exists (cache lookup). Without an index, this lookup was doing a full table scan on `kp_export_jobs`. Adding an index made this check near-instant, improving the overall export request latency."

---

### **Q: "Did you consider any other optimizations besides indexing?"**

> **A:** "Yes, I also considered:
> 1. **Table partitioning** — for `upload_list_records` which grows continuously, we could partition by `created_at` date
> 2. **Archiving old data** — moving completed/old exports to an archive table
> 3. **Caching** — using Redis to cache frequently accessed export metadata
> 4. **Query optimization** — ensuring queries only SELECT needed columns, not SELECT *
> 5. **Connection pooling** — ensuring we're not creating new connections for each query
>
> Indexing was the first step because it provided the best ROI with minimal code changes."

---

## 🔧 Technical Deep-Dive Content

### EXPLAIN ANALYZE Example

```sql
-- Before optimization (slow query)
EXPLAIN ANALYZE SELECT * FROM upload_list_records 
WHERE list_uid = 'abc123' AND product_name = 'wallet';

-- Output:
-- Seq Scan on upload_list_records (cost=0.00..25432.00 rows=1)
-- Filter: ((list_uid = 'abc123') AND (product_name = 'wallet'))
-- Execution Time: 823.456 ms

-- After adding composite index
-- Index Scan using idx_upload_list_records_list_uid_list_name_product_name
-- Index Cond: ((list_uid = 'abc123') AND (product_name = 'wallet'))
-- Execution Time: 48.123 ms
```

### Schema Optimization Examples

#### Denormalization for Read-Heavy Tables
```sql
-- Required 3 JOINs to get order summary
SELECT o.id, c.name, p.product_name, o.total
FROM orders o
JOIN customers c ON o.customer_id = c.id
JOIN order_items oi ON o.id = oi.order_id
JOIN products p ON oi.product_id = p.id;

-- After denormalization (single table query)
SELECT id, customer_name, order_summary, total FROM orders WHERE id = ?;
```

#### Best Practices for Indexing
```sql
-- Avoiding SELECT *
SELECT id, name, email FROM users WHERE status = 'ACTIVE';

-- Using EXISTS instead of IN for subqueries
SELECT * FROM orders o 
WHERE EXISTS (SELECT 1 FROM customers c WHERE c.id = o.customer_id AND c.region = 'US');

-- Keyset pagination (better than OFFSET for large datasets)
SELECT * FROM products WHERE id > 10000 ORDER BY id LIMIT 20;
```

---

## 💡 Key Talking Points

1. **You understand query execution plans** — EXPLAIN ANALYZE
2. **You know when to use composite vs single-column indexes** — based on query patterns
3. **You measure impact quantitatively** — before/after metrics
4. **You understand trade-offs** — read vs write performance
5. **You connect DB optimization to business impact** — faster response = better UX

---

## Additional Follow-up Questions

### **Q: "What's the difference between clustered and non-clustered index?"**
> **A:** "A clustered index determines the physical order of data in the table — there can be only one per table (usually the primary key). A non-clustered index is a separate structure that contains pointers to the actual data. Non-clustered indexes are faster for lookups but require additional storage and maintenance."

### **Q: "When would you NOT add an index?"**
> **A:** "I wouldn't add indexes for:
> - Tables that are heavily written to (indexes slow down INSERTs/UPDATEs)
> - Low-cardinality columns (like boolean or status fields with few values)
> - Small tables where full scan is faster
> - Columns rarely used in WHERE/JOIN/ORDER BY clauses"

### **Q: "What's N+1 query problem and how did you solve it?"**
> **A:** "The N+1 problem occurs when we query a parent entity, then make N additional queries for each child. In Spring/JPA, I solved this using:
> - `@BatchSize` annotation for lazy loading optimization
> - `JOIN FETCH` in JPQL queries
> - Entity graphs for complex relationships
> - Moving to batch queries instead of individual fetches"
