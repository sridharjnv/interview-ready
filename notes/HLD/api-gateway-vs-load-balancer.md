# API Gateway vs Load Balancer

> **Quick Summary for Interviews:**  
> API Gateway is the intelligent front door for your APIs (handles authentication, rate limiting, routing), while Load Balancer is the traffic cop (distributes requests across servers for high availability). They work together - Gateway handles "what and who", Balancer handles "where and how many".

---

## Table of Contents
1. [Core Concepts](#core-concepts)
2. [Detailed Comparison](#detailed-comparison)
3. [Complete Request Flow](#complete-request-flow)
4. [Interview Questions & Answers](#interview-questions--answers)
5. [Real-World Scenarios](#real-world-scenarios)
6. [System Design Considerations](#system-design-considerations)
7. [Common Pitfalls](#common-pitfalls)

---

## Core Concepts

### What is a Load Balancer?

**Definition:** A Load Balancer distributes incoming network traffic across multiple backend servers to ensure no single server becomes overwhelmed.

**Key Analogy:** Think of it like a restaurant host who distributes customers evenly across available tables to prevent any waiter from being overworked.

**Primary Goals:**
- **High Availability**: If one server fails, traffic goes to healthy servers
- **Scalability**: Add more servers to handle increased traffic
- **Performance**: Distribute load to prevent bottlenecks
- **Fault Tolerance**: Automatic failover when servers go down

**Operating Layers:**
- **Layer 4 (Transport Layer)**: Routes based on IP address and TCP/UDP ports
  - Faster, simpler
  - No content inspection
  - Example: AWS Network Load Balancer (NLB)
  
- **Layer 7 (Application Layer)**: Routes based on HTTP headers, URL paths, cookies
  - Smarter routing decisions
  - Can inspect request content
  - Example: AWS Application Load Balancer (ALB), NGINX

---

### What is an API Gateway?

**Definition:** An API Gateway is a server that acts as a single entry point for a collection of microservices, handling cross-cutting concerns like authentication, rate limiting, and request routing.

**Key Analogy:** Think of it like a security guard and receptionist at a building entrance who checks your ID, directs you to the right floor, and tracks who visits what.

**Primary Goals:**
- **Abstraction**: Hide internal microservice complexity
- **Security**: Centralized authentication and authorization
- **Rate Limiting**: Prevent API abuse
- **Monitoring**: Centralized logging and analytics
- **Transformation**: Adapt requests/responses between client and backend

**Always operates at Layer 7** (Application Layer) because it needs to understand HTTP/HTTPS content.

---

## Detailed Comparison

### Feature-by-Feature Breakdown

| Feature | Load Balancer | API Gateway | Interview Talking Point |
|---------|---------------|-------------|------------------------|
| **Primary Purpose** | Traffic distribution | API management | LB focuses on infrastructure, AG focuses on application logic |
| **Authentication** | ❌ No | ✅ JWT, OAuth, API Keys | AG validates tokens before forwarding to backend |
| **Rate Limiting** | ❌ No (basic IP-based only) | ✅ Per user/API key | AG tracks quota: "User X can make 1000 req/hour" |
| **Request Transformation** | ❌ No | ✅ Yes | AG can add headers, transform JSON, aggregate responses |
| **Protocol Translation** | ❌ No | ✅ REST→gRPC, SOAP→REST | AG can convert between different API formats |
| **Routing Logic** | Server health & load | URL path, headers, query params | LB: "Which server?", AG: "Which service?" |
| **Caching** | ❌ Limited | ✅ Full HTTP caching | AG can cache GET responses to reduce backend load |
| **SSL Termination** | ✅ Yes | ✅ Yes | Both can decrypt HTTPS |
| **Health Checks** | ✅ Active monitoring | ❌ Delegates to LB | LB pings servers: "Are you alive?" |
| **Session Persistence** | ✅ Sticky sessions | ❌ Stateless (uses tokens) | LB: IP hash, AG: JWT contains state |
| **API Versioning** | ❌ No | ✅ /v1, /v2 routing | AG routes /v1/users to old service, /v2/users to new |
| **Analytics** | Basic (connection count) | Detailed (per endpoint, user) | AG tracks: "POST /orders called 1000 times today" |
| **Error Handling** | Generic 502/503 | Custom error messages | AG returns: `{"error": "Invalid API key"}` |
| **Cost** | Lower | Higher (more features) | AG has more overhead but provides more value |

---

### When to Use What?

#### Use **Only Load Balancer** When:
```
Scenario: Monolithic application with multiple identical instances
Example: WordPress site running on 5 servers
Why: Simple traffic distribution is all you need
```

#### Use **Only API Gateway** When:
```
Scenario: Single backend service but need API management
Example: Public API for a SaaS product (rate limiting, API keys)
Why: Need security and monitoring, but no load distribution yet
```

#### Use **Both Together** When: ⭐ (Most Common in Production)
```
Scenario: Microservices architecture
Example: E-commerce with Product, User, Order, Payment services
Architecture:
  Client → API Gateway → Load Balancer → [Service Instance Pool]
Why: AG handles API concerns, LB handles high availability
```

---

## Complete Request Flow

### Visual Architecture

```
┌─────────────────────────────────────────────────────────┐
│                       Internet                           │
└────────────────────────┬────────────────────────────────┘
                         │
                         ▼
                  ┌─────────────┐
                  │     DNS      │  api.example.com → 203.0.113.45
                  └──────┬───────┘
                         │
                         ▼
                  ┌─────────────┐
                  │ Firewall/WAF │  DDoS protection, IP filtering
                  └──────┬───────┘
                         │
        ┏━━━━━━━━━━━━━━━━▼━━━━━━━━━━━━━━━━┓
        ┃        🔐 API Gateway            ┃  Layer 7 (Application)
        ┃  • Authentication (JWT/OAuth)    ┃
        ┃  • Rate Limiting (1000 req/hr)   ┃
        ┃  • Request Transformation        ┃
        ┃  • Routing (/products → Service) ┃
        ┃  • Caching                       ┃
        ┃  • Analytics & Logging           ┃
        ┗━━━━━━━━━━━━━━━━┬━━━━━━━━━━━━━━━━┛
                         │
                         ▼
        ┏━━━━━━━━━━━━━━━━▼━━━━━━━━━━━━━━━━┓
        ┃        ⚖️  Load Balancer          ┃  Layer 4 or 7
        ┃  • Health Checks                 ┃
        ┃  • Algorithm (Round Robin)       ┃
        ┃  • SSL Termination               ┃
        ┃  • Session Persistence           ┃
        ┗━━━━━━━━━━━━━━━━┬━━━━━━━━━━━━━━━━┛
                         │
         ┌───────────────┼───────────────┐
         ▼               ▼               ▼
    ┌────────┐      ┌────────┐      ┌────────┐
    │Server 1│      │Server 2│      │Server 3│  Product Service
    │10.0.1.1│      │10.0.1.2│      │10.0.1.3│  (Multiple instances)
    └───┬────┘      └───┬────┘      └───┬────┘
        └───────────────┼───────────────┘
                        ▼
                 ┌──────────────┐
                 │   Database   │
                 └──────────────┘
```

---

### Step-by-Step Breakdown with Timing

**Example Request:** `POST https://api.example.com/v1/products`

#### **Step 1: Client → DNS (20-50ms)**

```
Client: "What's the IP for api.example.com?"
DNS Server: "It's 203.0.113.45"
```

**What you'd say in interview:**
> "First, the client resolves the domain name to an IP address via DNS. This IP usually points to the API Gateway or a CDN edge location in production systems."

---

#### **Step 2: Client → Firewall/WAF (5-10ms)**

```javascript
// Firewall checks
if (requestsFromIP > 10000/hour) {
  return 429; // Too Many Requests
}
if (ipAddress in blacklist) {
  return 403; // Forbidden
}
if (containsSQLInjection(requestBody)) {
  return 400; // Bad Request
}
// Pass through ✓
```

**What you'd say in interview:**
> "The WAF (Web Application Firewall) provides the first line of defense against common attacks like SQL injection, XSS, and DDoS. It operates before the API Gateway to filter out malicious traffic early."

---

#### **Step 3: API Gateway Processing (20-50ms)**

##### **3a. Authentication (10ms)**

```javascript
// Extract token from Authorization header
const token = request.headers['Authorization'].replace('Bearer ', '');

// Validate JWT
const decoded = jwt.verify(token, PUBLIC_KEY);
// decoded = {
//   userId: 'user-123',
//   email: 'john@example.com',
//   role: 'admin',
//   exp: 1706364847
// }

if (Date.now() > decoded.exp * 1000) {
  return 401; // Token expired
}

// Attach user context to request
request.user = decoded;
```

**What you'd say in interview:**
> "API Gateway validates the JWT token's signature and expiration. It extracts user information and attaches it to the request, so backend services don't need to re-validate tokens. This is a key security pattern."

##### **3b. Authorization (5ms)**

```javascript
// Check if user has permission for this endpoint
if (request.method === 'POST' && request.path === '/v1/products') {
  if (!request.user.role.includes('admin')) {
    return 403; // Forbidden
  }
}
```

##### **3c. Rate Limiting (5ms)**

```javascript
// Check Redis for user's request count
const key = `ratelimit:${request.user.userId}:${currentHour}`;
const count = await redis.incr(key);

if (count === 1) {
  await redis.expire(key, 3600); // Expire after 1 hour
}

if (count > 1000) {
  return 429; // Too Many Requests
  // Response: { error: 'Rate limit exceeded. Try again in 45 minutes' }
}

// Add header to response
response.headers['X-RateLimit-Remaining'] = 1000 - count;
```

**What you'd say in interview:**
> "Rate limiting is typically implemented using Redis or a similar key-value store. We use a sliding window or fixed window algorithm. The key includes user ID and time window to track requests per user per hour."

##### **3d. Request Transformation (10ms)**

```javascript
// BEFORE (from client)
POST /v1/products
{
  "name": "Laptop",
  "price": 999.99
}

// AFTER (API Gateway enriches)
POST /internal/product-service/products
{
  "name": "Laptop",
  "price": 999.99,
  "userId": "user-123",          // From JWT
  "userEmail": "john@example.com", // From JWT
  "requestId": "req-abc-123",    // Generated
  "timestamp": "2026-01-27T18:24:27Z",
  "clientIp": "192.168.1.100",
  "userAgent": "Mozilla/5.0..."
}
```

**What you'd say in interview:**
> "API Gateway can transform requests by adding metadata like user information, request IDs for tracing, and timestamps. This enrichment means backend services receive context without having to extract it themselves."

##### **3e. Service Routing (5ms)**

```javascript
// API Gateway routing rules
const routes = {
  '/v1/products':  'http://product-service-lb.internal:8080',
  '/v1/users':     'http://user-service-lb.internal:8080',
  '/v1/orders':    'http://order-service-lb.internal:8080',
  '/v1/payments':  'http://payment-service-lb.internal:8080'
};

const targetService = routes[request.path.match(/\/v1\/\w+/)[0]];
// targetService = 'http://product-service-lb.internal:8080'
```

**What you'd say in interview:**
> "In a microservices architecture, the API Gateway routes requests to the appropriate backend service based on URL path. This shields clients from knowing about multiple backend services."

##### **3f. Cache Check (if applicable)**

```javascript
// For GET requests, check cache
if (request.method === 'GET') {
  const cacheKey = `cache:${request.path}:${request.query}`;
  const cached = await redis.get(cacheKey);
  
  if (cached) {
    return JSON.parse(cached); // Return cached response (skip backend!)
  }
}
```

---

#### **Step 4: Load Balancer Processing (5-10ms)**

##### **4a. Health Check Verification**

```javascript
// Load balancer maintains health status
const healthStatus = {
  'server-1 (10.0.1.1:8080)': {
    healthy: true,
    lastCheck: '2026-01-27T18:24:20Z',
    responseTime: 45ms,
    failureCount: 0
  },
  'server-2 (10.0.1.2:8080)': {
    healthy: true,
    lastCheck: '2026-01-27T18:24:20Z',
    responseTime: 38ms,
    failureCount: 0
  },
  'server-3 (10.0.1.3:8080)': {
    healthy: false,  // ❌ Failed last 3 health checks
    lastCheck: '2026-01-27T18:24:15Z',
    responseTime: null,
    failureCount: 3
  }
};

// Only route to healthy servers
const availableServers = ['server-1', 'server-2']; // server-3 excluded
```

**Health Check Mechanism:**
```javascript
// Load balancer pings each server every 10 seconds
setInterval(async () => {
  for (const server of servers) {
    try {
      const response = await http.get(`${server}/health`);
      if (response.status === 200) {
        server.healthy = true;
        server.failureCount = 0;
      }
    } catch (error) {
      server.failureCount++;
      if (server.failureCount >= 3) {
        server.healthy = false; // Mark unhealthy after 3 failures
      }
    }
  }
}, 10000);
```

**What you'd say in interview:**
> "Load balancers continuously perform health checks by sending requests to a health endpoint (like /health or /ping). If a server fails consecutive checks, it's marked unhealthy and removed from rotation until it recovers."

##### **4b. Load Balancing Algorithm**

**Round Robin (Most Common):**
```javascript
let currentIndex = 0;

function roundRobin(servers) {
  const server = servers[currentIndex % servers.length];
  currentIndex++;
  return server;
}

// Request 1 → server-1
// Request 2 → server-2
// Request 3 → server-1 (cycles back)
```

**Least Connections:**
```javascript
function leastConnections(servers) {
  return servers.reduce((min, server) => 
    server.activeConnections < min.activeConnections ? server : min
  );
}

// server-1: 10 connections
// server-2: 5 connections  ← Route here!
```

**IP Hash (Sticky Sessions):**
```javascript
function ipHash(clientIP, servers) {
  const hash = crypto.createHash('md5').update(clientIP).digest('hex');
  const index = parseInt(hash, 16) % servers.length;
  return servers[index];
}

// Same client IP always goes to same server
// Good for stateful applications
```

**Weighted Round Robin:**
```javascript
const servers = [
  { id: 'server-1', weight: 3 },  // More powerful
  { id: 'server-2', weight: 2 },
  { id: 'server-3', weight: 1 }   // Less powerful
];

// Distribution: 3:2:1
// Out of 6 requests:
// server-1 gets 3, server-2 gets 2, server-3 gets 1
```

**What you'd say in interview:**
> "Different algorithms serve different purposes. Round Robin is simple and fair. Least Connections is better when requests have varying processing times. IP Hash maintains session affinity. Weighted algorithms account for servers with different capacities."

##### **4c. Forward Request**

```javascript
const selectedServer = roundRobin(availableServers);
// selectedServer = 'server-2 (10.0.1.2:8080)'

// Forward the enriched request from API Gateway
http.post('http://10.0.1.2:8080/internal/product-service/products', {
  headers: request.headers,
  body: request.body
});
```

---

#### **Step 5: Backend Server Processing (50-200ms)**

##### **Server receives and processes:**

```javascript
// Express.js backend (server-2)
app.post('/internal/product-service/products', async (req, res) => {
  
  // 1. Validate business rules (10ms)
  if (!req.body.name || req.body.price <= 0) {
    return res.status(400).json({ 
      error: 'Invalid product data' 
    });
  }
  
  // 2. Database operations (100-150ms)
  const product = await db.products.create({
    name: req.body.name,
    price: req.body.price,
    createdBy: req.body.userId,
    createdAt: new Date()
  });
  
  // 3. Publish event (optional, 20ms)
  await eventBus.publish('product.created', {
    productId: product.id,
    name: product.name
  });
  
  // 4. Return response (5ms)
  res.status(201).json({
    id: product.id,
    name: product.name,
    price: product.price,
    createdAt: product.createdAt
  });
});
```

**Database Query:**
```sql
INSERT INTO products (id, name, price, created_by, created_at)
VALUES (uuid_generate_v4(), 'Laptop', 999.99, 'user-123', NOW())
RETURNING *;
```

---

#### **Step 6: Response Journey Back (30-70ms)**

##### **6a. Backend → Load Balancer (5ms)**

```javascript
// Server-2 sends response
HTTP/1.1 201 Created
Content-Type: application/json

{
  "id": "prod-67890",
  "name": "Laptop",
  "price": 999.99,
  "createdAt": "2026-01-27T18:24:27Z"
}
```

Load balancer simply forwards it (no modification).

##### **6b. Load Balancer → API Gateway (5ms)**

##### **6c. API Gateway Response Processing (20-50ms)**

**Add Response Headers:**
```javascript
response.headers['X-Request-ID'] = 'req-abc-123';
response.headers['X-RateLimit-Remaining'] = '994';
response.headers['X-Response-Time'] = '156ms';
response.headers['X-Served-By'] = 'server-2';
```

**Cache Response (for GET requests):**
```javascript
if (request.method === 'GET') {
  const cacheKey = `cache:${request.path}:${request.query}`;
  await redis.setex(cacheKey, 60, JSON.stringify(response.body)); // Cache for 60 seconds
}
```

**Log Analytics:**
```javascript
await analytics.log({
  requestId: 'req-abc-123',
  userId: 'user-123',
  method: 'POST',
  path: '/v1/products',
  statusCode: 201,
  responseTime: 156,
  backendServer: 'server-2',
  timestamp: '2026-01-27T18:24:27Z'
});
```

##### **6d. API Gateway → Client (10ms)**

```http
HTTP/1.1 201 Created
Content-Type: application/json
X-Request-ID: req-abc-123
X-RateLimit-Remaining: 994
X-Response-Time: 156ms

{
  "id": "prod-67890",
  "name": "Laptop",
  "price": 999.99,
  "createdAt": "2026-01-27T18:24:27Z"
}
```

---

### Complete Timing Summary

```
┌─────────────────────────────────┬──────────┐
│ Step                            │ Duration │
├─────────────────────────────────┼──────────┤
│ DNS Resolution                  │   30ms   │
│ Firewall/WAF                    │   8ms    │
│ API Gateway                     │   45ms   │
│   ├─ Authentication             │    10ms  │
│   ├─ Authorization              │     5ms  │
│   ├─ Rate Limiting              │     5ms  │
│   ├─ Transformation             │    10ms  │
│   ├─ Routing                    │     5ms  │
│   └─ Cache Check                │    10ms  │
│ Load Balancer                   │    8ms   │
│   ├─ Health Check Lookup        │     3ms  │
│   ├─ Algorithm Selection        │     2ms  │
│   └─ Forward                    │     3ms  │
│ Backend Processing              │  150ms   │
│   ├─ Business Logic             │    20ms  │
│   ├─ Database Query             │   120ms  │
│   └─ Response Creation          │    10ms  │
│ Return Path                     │   50ms   │
│   ├─ LB → AG                    │     5ms  │
│   ├─ AG Processing              │    35ms  │
│   └─ AG → Client                │    10ms  │
├─────────────────────────────────┼──────────┤
│ **TOTAL RESPONSE TIME**         │ **291ms**│
└─────────────────────────────────┴──────────┘
```

---

## Interview Questions & Answers

### Question 1: "Can you explain the difference between API Gateway and Load Balancer?"

**Perfect Answer:**
> "Great question! While both handle traffic routing, they serve different purposes:
>
> A **Load Balancer** is focused on **distributing traffic** across multiple server instances to ensure high availability and prevent any single server from being overwhelmed. It operates at Layer 4 (TCP/UDP) or Layer 7 (HTTP), performs health checks, and uses algorithms like round-robin or least-connections to distribute load.
>
> An **API Gateway**, on the other hand, is focused on **API management**. It handles cross-cutting concerns like authentication, authorization, rate limiting, request transformation, and routing to different microservices. It always operates at Layer 7 because it needs to inspect HTTP content.
>
> In production, they work together: API Gateway handles API-specific logic, then forwards requests to a Load Balancer which distributes them across service instances."

---

### Question 2: "Why would you use both API Gateway and Load Balancer together?"

**Perfect Answer:**
> "You'd use both in a **microservices architecture** for separation of concerns:
>
> **API Gateway** provides:
> - Single entry point for clients (they don't need to know about multiple services)
> - Authentication/authorization (validate once at the edge)
> - Rate limiting per user or API key
> - Request/response transformation
> - Service-level routing (/products → Product Service, /users → User Service)
>
> **Load Balancer** provides:
> - High availability within each service (if one instance crashes, traffic goes to others)
> - Horizontal scalability (easily add more instances)
> - Health monitoring
>
> For example, at Amazon, a request might flow:
> ```
> Client → API Gateway → 
>   Product Service Load Balancer → [Instance 1, Instance 2, Instance 3]
> ```
> 
> API Gateway decided 'this goes to Product Service', then Load Balancer decided 'send to Instance 2'."

---

### Question 3: "How does an API Gateway handle authentication?"

**Perfect Answer:**
> "API Gateway typically validates authentication tokens **at the edge**, before requests reach backend services. Here's the flow:
>
> 1. **Client sends token**: Usually a JWT in the Authorization header
>    ```
>    Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
>    ```
>
> 2. **Gateway validates**:
>    - Verifies JWT signature using public key
>    - Checks expiration timestamp
>    - Validates issuer and audience claims
>
> 3. **Extract user context**:
>    ```javascript
>    { userId: 'user-123', role: 'admin', email: 'john@example.com' }
>    ```
>
> 4. **Enrich request**: Gateway adds user info to request headers/body before forwarding to backend
>
> 5. **Benefits**:
>    - Backend services don't need to validate tokens (reduces latency and code duplication)
>    - Centralized security policy
>    - Easy to update authentication logic without changing every service
>
> This is a key pattern in **Zero Trust Architecture** - validate at the perimeter."

---

### Question 4: "What load balancing algorithms are there and when would you use each?"

**Perfect Answer:**
> "There are several algorithms, each suited for different scenarios:
>
> **1. Round Robin** (Most Common)
> - Cycles through servers in order
> - Use when: Servers have similar capacity and requests have similar processing time
> - Example: Stateless API servers
>
> **2. Least Connections**
> - Routes to server with fewest active connections
> - Use when: Requests have varying processing times (some quick, some slow)
> - Example: File upload service where some uploads take seconds, others minutes
>
> **3. IP Hash / Sticky Sessions**
> - Same client always goes to same server
> - Use when: Application has server-side session state
> - Example: Legacy app that stores session in memory
> - Downside: If server crashes, sessions are lost
>
> **4. Weighted Round Robin**
> - Distributes based on server capacity
> - Use when: Servers have different specs
> - Example: 3 m5.large instances + 1 m5.2xlarge, give the larger one 2x weight
>
> **5. Least Response Time**
> - Routes to server with lowest latency
> - Use when: Servers are in different geographic regions or have varying load
>
> In practice, I'd use **Round Robin for stateless microservices**, **Least Connections for variable workloads**, and avoid IP Hash in favor of stateless design with JWT tokens."

---

### Question 5: "How does rate limiting work in an API Gateway?"

**Perfect Answer:**
> "Rate limiting prevents API abuse by restricting the number of requests a client can make in a time window. Here's how it's implemented:
>
> **Algorithm: Token Bucket or Sliding Window**
>
> **Implementation with Redis:**
> ```javascript
> const key = `ratelimit:${userId}:${currentHour}`;
> const count = await redis.incr(key);
> 
> if (count === 1) {
>   await redis.expire(key, 3600); // Expire in 1 hour
> }
> 
> if (count > 1000) {
>   return 429; // Too Many Requests
> }
> ```
>
> **Why Redis?**
> - Fast (in-memory)
> - Distributed (multiple API Gateway instances can share state)
> - Atomic operations (INCR is atomic, prevents race conditions)
>
> **Different Granularities:**
> - Per IP address: `ratelimit:ip:192.168.1.1`
> - Per API key: `ratelimit:apikey:abc123`
> - Per user: `ratelimit:user:user-123`
> - Per endpoint: `ratelimit:user:user-123:/products`
>
> **Response Headers:**
> ```
> X-RateLimit-Limit: 1000
> X-RateLimit-Remaining: 994
> X-RateLimit-Reset: 1706368800
> ```
>
> **Time Windows:**
> - Fixed window: Reset at exact intervals (e.g., top of each hour)
> - Sliding window: More accurate, counts last 60 minutes
>
> In production at scale, services like AWS API Gateway, Kong, or Cloudflare handle this automatically with their built-in rate limiting."

---

### Question 6: "What happens when a backend server crashes?"

**Perfect Answer:**
> "Great question - this tests the resilience of the system. Here's the sequence:
>
> **Scenario: Server-2 crashes**
>
> **1. Load Balancer Detects Failure (10-30 seconds)**
> - Health check to Server-2 fails
>   ```
>   GET http://server-2:8080/health
>   → Connection refused (or timeout)
>   ```
> - After configured threshold (e.g., 3 consecutive failures), mark Server-2 as unhealthy
>
> **2. Remove from Pool**
> ```javascript
> availableServers = ['server-1', 'server-3']; // server-2 removed
> ```
>
> **3. In-Flight Requests**
> - Requests already sent to Server-2: **Fail with 502 Bad Gateway**
> - Load balancer might retry on another server (if configured)
>
> **4. New Requests**
> - Distributed only among healthy servers (server-1, server-3)
> - **No client impact** (assuming sufficient capacity)
>
> **5. Monitoring & Alerts**
> - Load balancer triggers alert: "Server-2 down"
> - Auto-scaling might spin up replacement instance
>
> **6. Recovery**
> - Server-2 comes back online
> - Health checks succeed → Added back to pool
> - Gradual traffic ramp-up (optional: slow start)
>
> **Interview Tip:** Mention that this is why we need **at least 2 healthy instances** at all times for high availability (N+1 redundancy)."

---

### Question 7: "How would you design an API Gateway for a large-scale system?"

**Perfect Answer (System Design Approach):**
> "For a large-scale API Gateway, I'd consider these aspects:
>
> **1. High Availability**
> - Deploy API Gateway in **multiple availability zones**
> - Use **DNS load balancing** or **Global Load Balancer** in front
> - At least 3 instances per AZ (N+2 redundancy)
>
> **2. Stateless Design**
> - API Gateway itself should be stateless
> - Store rate limit counters in **distributed cache (Redis Cluster)**
> - Use **JWT tokens** instead of server-side sessions
>
> **3. Performance Optimizations**
> - **Connection pooling** to backend services
> - **HTTP/2** for multiplexing
> - **Response caching** for GET requests (Redis/CDN)
> - **Circuit breaker** pattern to fail fast when backends are down
>
> **4. Security**
> - **JWT validation with public key** (no database lookup)
> - **WAF integration** for common attack patterns
> - **mTLS** for backend communication
> - **API key management** with rotation
>
> **5. Observability**
> - **Distributed tracing** (Jaeger/Zipkin) with request IDs
> - **Metrics**: Request rate, latency percentiles (p50, p95, p99), error rate
> - **Logging**: Structured logs with correlation IDs
> - **Dashboards**: Real-time monitoring (Grafana)
>
> **6. Rate Limiting Strategy**
> - **Tiered limits**: Free tier (100/hour), Pro (10000/hour)
> - **Burst allowance**: Allow short bursts above limit
> - **Per-endpoint limits**: Different limits for read vs write operations
>
> **7. Scalability**
> - **Horizontal auto-scaling** based on CPU/request rate
> - **Geo-distributed**: API Gateways in multiple regions
> - **CDN integration** for static content
>
> **Technology Choices:**
> - AWS: **API Gateway** + **Lambda** (serverless) or **ALB** + **Kong/Tyk** (container-based)
> - Open source: **Kong**, **Tyk**, **KrakenD**
> - Custom: **Nginx/OpenResty** with Lua scripting
>
> **Example Architecture:**
> ```
> Route53 (DNS) → 
>   CloudFront (CDN) → 
>     WAF → 
>       API Gateway (Multi-AZ) → 
>         Service Mesh (Istio) → 
>           Microservices
> ```"

---

### Question 8: "What's the difference between Layer 4 and Layer 7 load balancing?"

**Perfect Answer:**
> "This refers to the OSI model layers where the load balancer operates:
>
> **Layer 4 (Transport Layer) - TCP/UDP**
>
> **How it works:**
> - Makes routing decisions based on **IP address and port**
> - Doesn't inspect packet content
> - Faster (less processing overhead)
>
> **Example:**
> ```
> Client:192.168.1.100:54321 → LB:203.0.113.45:443 → Server:10.0.1.5:8080
>                                                  → Server:10.0.1.6:8080
> ```
>
> **Use cases:**
> - High-throughput scenarios (millions of connections)
> - Non-HTTP protocols (TCP databases, game servers)
> - When you need maximum performance
>
> **AWS Example:** Network Load Balancer (NLB)
>
> ---
>
> **Layer 7 (Application Layer) - HTTP/HTTPS**
>
> **How it works:**
> - Inspects HTTP headers, URL path, cookies, request body
> - Terminates SSL/TLS connection
> - Can route based on content
>
> **Routing Examples:**
> ```javascript
> // Path-based routing
> /api/products/*  → Product Service
> /api/users/*     → User Service
>
> // Header-based routing
> Host: mobile.example.com → Mobile Backend
> Host: web.example.com    → Web Backend
>
> // Cookie-based (A/B testing)
> Cookie: version=beta → Beta Servers
> Cookie: version=prod → Production Servers
> ```
>
> **Use cases:**
> - Microservices (route to different services)
> - A/B testing
> - Blue-green deployments
> - Web applications
>
> **AWS Example:** Application Load Balancer (ALB)
>
> ---
>
> **Comparison:**
> | Aspect | Layer 4 | Layer 7 |
> |--------|---------|---------|
> | Speed | Faster | Slower |
> | Routing | IP:Port only | Content-based |
> | SSL Termination | Optional (passthrough) | Yes |
> | WAF Integration | No | Yes |
> | Use case | High performance | Smart routing |
>
> **Interview Tip:** 'For most modern web APIs, Layer 7 is preferred for its routing flexibility. Layer 4 is for ultra-high-performance scenarios like database proxies or when you need to preserve client IP without additional headers.'"

---

### Question 9: "How do you handle API versioning?"

**Perfect Answer:**
> "API versioning is crucial for backward compatibility. There are several approaches:
>
> **1. URL Path Versioning** (Most Common, Recommended)
> ```
> /v1/products
> /v2/products
> ```
> **Pros:**
> - Clear and visible
> - Easy to route in API Gateway
> - Can cache different versions separately
>
> **Gateway Routing:**
> ```javascript
> /v1/products → product-service-v1.internal:8080
> /v2/products → product-service-v2.internal:8080
> ```
>
> **2. Header Versioning**
> ```
> GET /products
> Accept: application/vnd.myapi.v2+json
> ```
> **Pros:** Clean URLs
> **Cons:** Less visible, harder to test in browser
>
> **3. Query Parameter Versioning**
> ```
> /products?version=2
> ```
> **Pros:** Easy to implement
> **Cons:** Can interfere with caching
>
> **4. Subdomain Versioning**
> ```
> v1.api.example.com/products
> v2.api.example.com/products
> ```
>
> ---
>
> **Best Practices:**
>
> 1. **URL path versioning** for public APIs
> 2. **Default to latest stable** if no version specified
> 3. **Deprecation timeline**: Announce deprecation 6-12 months in advance
> 4. **Sunset headers**:
>    ```
>    Sunset: Sat, 31 Dec 2026 23:59:59 GMT
>    Link: <https://api.example.com/docs/deprecation>; rel="deprecation"
>    ```
> 5. **Monitor version usage** before deprecating
> 6. **Maintain at least N-1 versions** (current + previous)
>
> **When to create a new version:**
> - Breaking changes (removing fields, changing data types)
> - NOT for adding optional fields (that's backward compatible)
>
> In my experience at [company], we used URL path versioning and gave clients 12 months notice before deprecating v1, with monitoring to ensure <1% traffic before shutdown."

---

### Question 10: "What is a circuit breaker and how does it relate to API Gateway?"

**Perfect Answer:**
> "A **circuit breaker** is a design pattern that prevents cascading failures when a backend service is down. It's like an electrical circuit breaker that trips to prevent damage.
>
> **States:**
>
> ```
>     ┌─────────┐
>     │  CLOSED │  (Normal operation, requests flow through)
>     └────┬────┘
>          │ Too many failures detected
>          ▼
>     ┌─────────┐
>     │  OPEN   │  (Stop sending requests, fail fast)
>     └────┬────┘
>          │ After timeout period
>          ▼
>     ┌──────────┐
>     │ HALF-OPEN│  (Try a few requests to test recovery)
>     └────┬─────┘
>          │ If successful → CLOSED
>          │ If failed → OPEN
> ```
>
> **How it works in API Gateway:**
>
> ```javascript
> class CircuitBreaker {
>   constructor() {
>     this.state = 'CLOSED';
>     this.failureCount = 0;
>     this.threshold = 5;           // Trip after 5 failures
>     this.timeout = 60000;         // Wait 60s before retry
>   }
>
>   async call(service) {
>     if (this.state === 'OPEN') {
>       if (Date.now() > this.nextAttempt) {
>         this.state = 'HALF-OPEN';
>       } else {
>         throw new Error('Circuit breaker is OPEN');
>       }
>     }
>
>     try {
>       const response = await service.call();
>       this.onSuccess();
>       return response;
>     } catch (error) {
>       this.onFailure();
>       throw error;
>     }
>   }
>
>   onSuccess() {
>     this.failureCount = 0;
>     this.state = 'CLOSED';
>   }
>
>   onFailure() {
>     this.failureCount++;
>     if (this.failureCount >= this.threshold) {
>       this.state = 'OPEN';
>       this.nextAttempt = Date.now() + this.timeout;
>     }
>   }
> }
> ```
>
> **Example Scenario:**
>
> 1. **Payment Service goes down**
> 2. API Gateway tries to call it → **5 consecutive failures**
> 3. Circuit breaker **trips to OPEN**
> 4. For next 60 seconds, API Gateway **immediately returns 503** without calling Payment Service
> 5. After 60 seconds, **HALF-OPEN**: Try one request
> 6. If successful → **CLOSED** (normal operation resumes)
>
> **Benefits:**
> - **Fail fast**: Don't waste time waiting for timeouts
> - **Prevent cascading failures**: Don't overload a struggling service
> - **Give service time to recover**: Stop sending traffic during recovery
> - **Better user experience**: Return errors in 10ms instead of 30s timeout
>
> **In practice:** Tools like **Netflix Hystrix**, **Resilience4j**, or API Gateway platforms (Kong, AWS API Gateway) have built-in circuit breakers.
>
> This is a critical pattern for building resilient microservices architectures."

---

## Real-World Scenarios

### Scenario 1: E-Commerce Flash Sale

**Challenge:** During a flash sale, traffic spikes from 1,000 req/sec to 100,000 req/sec.

**Architecture:**

```
                    ┌─────────────────┐
  100K req/sec ───→ │   API Gateway   │
                    │  (Auto-scaled)  │
                    └────────┬────────┘
                             │
                    ┌────────▼────────┐
                    │ Rate Limiter    │ Limit per user: 10 req/sec
                    │ (Redis Cluster) │ Bot traffic blocked
                    └────────┬────────┘
                             │
                    ┌────────▼────────┐
                    │  Load Balancer  │
                    └────────┬────────┘
                             │
         ┌───────────────────┼───────────────────┐
         ▼                   ▼                   ▼
    ┌────────┐          ┌────────┐          ┌────────┐
    │Product │          │Checkout│          │Payment │
    │Service │          │Service │          │Service │
    │20 pods │          │50 pods │          │10 pods │
    └────────┘          └────────┘          └────────┘
```

**Key Decisions:**

1. **API Gateway**: 
   - Aggressive caching for product details (60s TTL)
   - Rate limit: 10 req/sec per user to prevent bot abuse
   - Priority queuing: Checkout requests get priority over browsing

2. **Load Balancer**:
   - Auto-scale checkout service (most loaded during flash sale)
   - Least connections algorithm (checkout has variable processing time)

3. **Result**: 
   - <1% error rate
   - p95 latency: 300ms (acceptable for flash sale)
   - Blocked 60% of bot traffic via rate limiting

---

### Scenario 2: Multi-Region Deployment

**Challenge:** Serve users globally with low latency and high availability.

**Architecture:**

```
               ┌──────────────────┐
               │  Route53 (DNS)   │  Geolocation routing
               └────────┬─────────┘
                        │
        ┌───────────────┼───────────────┐
        ▼               ▼               ▼
   ┌─────────┐     ┌─────────┐     ┌─────────┐
   │  US-East│     │ EU-West │     │ AP-South│
   │ Gateway │     │ Gateway │     │ Gateway │
   └────┬────┘     └────┬────┘     └────┬────┘
        │               │               │
        └───────────────┼───────────────┘
                        ▼
              ┌──────────────────┐
              │  Global Services │ (User, Auth)
              │   US-East only   │
              └──────────────────┘
```

**Key Decisions:**

1. **API Gateway per region**: 
   - Users routed to nearest region (low latency)
   - Each gateway can fail independently

2. **Service placement**:
   - **Read-heavy services** (Product Catalog): Replicated in all regions with eventual consistency
   - **Write-heavy services** (Orders): Write to primary region, replicate async
   - **Global services** (User Auth): Single region with global caching

3. **Cross-region failover**:
   - If US-East gateway fails, Route53 routes US traffic to EU-West
   - Health check every 30s

---

### Scenario 3: Migrating Monolith to Microservices

**Challenge:** Gradually migrate from monolith to microservices without downtime.

**Strangler Fig Pattern:**

```
Phase 1: All traffic to monolith
┌─────────────┐
│ API Gateway │ → Monolith
└─────────────┘

Phase 2: Route new features to microservices
┌─────────────┐
│ API Gateway │ ─→ /v1/legacy/*    → Monolith
└─────────────┘ ─→ /v1/products/*  → Product Service (NEW)

Phase 3: Migrate endpoint by endpoint
┌─────────────┐
│ API Gateway │ ─→ /v1/users/*     → User Service (NEW)
└─────────────┘ ─→ /v1/products/*  → Product Service
                ─→ /v1/orders/*    → Monolith (remaining)

Phase 4: Monolith fully decomposed
┌─────────────┐
│ API Gateway │ ─→ /v1/users/*     → User Service
└─────────────┘ ─→ /v1/products/*  → Product Service
                ─→ /v1/orders/*    → Order Service
```

**API Gateway Configuration:**

```javascript
// Week 1: All to monolith
routes = {
  '/*': 'http://monolith.internal'
};

// Week 4: Products migrated
routes = {
  '/v1/products/*': 'http://product-service-lb.internal',
  '/*': 'http://monolith.internal'  // Catch-all
};

// Week 12: Users migrated
routes = {
  '/v1/products/*': 'http://product-service-lb.internal',
  '/v1/users/*': 'http://user-service-lb.internal',
  '/*': 'http://monolith.internal'
};
```

**Benefits:**
- Zero downtime migration
- Can test new services with shadow traffic or canary deployments
- Easy rollback (just update routes)

---

## System Design Considerations

### Capacity Planning

**Calculating Required Capacity:**

```
Given:
- Expected traffic: 10,000 req/sec
- Average response time: 100ms
- Target availability: 99.99%

API Gateway Instances:
- Each instance can handle: 2,000 req/sec
- Required: 10,000 / 2,000 = 5 instances
- With N+2 redundancy: 7 instances
- Across 3 AZs: 9 instances (3 per AZ)

Backend Service Instances:
- Each instance handles: 500 req/sec
- Required: 10,000 / 500 = 20 instances
- With auto-scaling: 20-40 instances (50% headroom)
```

---

### Cost Optimization

**Typical Costs (AWS):**

```
API Gateway:
- $3.50 per million requests
- At 10,000 req/sec = 864M req/month
- Cost: ~$3,000/month

Load Balancer (ALB):
- $0.0225 per hour = $16.20/month
- $0.008 per LCU-hour
- Typical: $50-100/month per ALB

Backend EC2:
- 20 × m5.large = $1,680/month
- Alternative: Fargate containers = $2,100/month

Total: ~$5,000/month for compute + $3,000 for API Gateway
```

**Optimization strategies:**
- Use caching to reduce backend calls (can save 50%+ costs)
- Right-size instances based on actual CPU/memory usage
- Reserved instances for predictable load
- Spot instances for bursty workloads

---

### Security Checklist

**API Gateway Security:**
- ✅ JWT validation with RS256 (asymmetric keys)
- ✅ Rate limiting per user/IP
- ✅ API key rotation policy
- ✅ CORS configuration
- ✅ Request size limits (prevent large payload attacks)
- ✅ WAF integration (SQL injection, XSS protection)
- ✅ DDoS protection (AWS Shield)

**Load Balancer Security:**
- ✅ TLS 1.2+ only (disable TLS 1.0/1.1)
- ✅ Strong cipher suites
- ✅ SSL certificate auto-renewal
- ✅ Security groups (restrict inbound to HTTPS only)
- ✅ VPC isolation (backend in private subnets)

**Network Security:**
- ✅ mTLS between API Gateway and backends
- ✅ Private VPC endpoints (no internet exposure for internal services)
- ✅ Secrets management (AWS Secrets Manager, not hardcoded)

---

## Common Pitfalls

### Pitfall 1: Not Handling Timeouts Properly

**Problem:**
```javascript
// Bad: No timeout
const response = await fetch('http://backend-service/api');
// If backend hangs, this waits forever
```

**Solution:**
```javascript
// Good: Set aggressive timeout at API Gateway
const response = await fetch('http://backend-service/api', {
  timeout: 5000  // 5 second timeout
});

// Also set timeout at load balancer
// ALB: Target idle timeout = 30s
```

**Interview Point:** "API Gateway should have shorter timeout than load balancer to fail fast."

---

### Pitfall 2: Cascading Failures

**Problem:**
```
Payment Service down → 
  API Gateway keeps trying (30s timeout) → 
    Thread pool exhausted → 
      All API Gateway instances crash → 
        Entire system down
```

**Solution:**
```javascript
// Use circuit breaker pattern
if (circuitBreaker.isOpen('payment-service')) {
  return 503; // Fail fast in 10ms instead of 30s timeout
}
```

---

### Pitfall 3: Ignoring HTTP Caching

**Problem:**
```javascript
// Product details endpoint called 1000 times/sec
// Each time queries database
GET /v1/products/123
```

**Solution:**
```javascript
// API Gateway caching
GET /v1/products/123
Cache-Control: public, max-age=60

// Now 1000 req/sec becomes 1 backend call/minute
// 99.9% cache hit rate
```

**Savings:**
- Latency: 150ms → 10ms
- Backend load: 1000 req/sec → 1 req/min
- Cost: 99% reduction

---

### Pitfall 4: Stateful Load Balancing

**Problem:**
```
Server stores sessions in memory →
  Load balancer uses IP hash (sticky sessions) →
    Server crashes → All sessions lost for those users →
      Users logged out
```

**Solution:**
```javascript
// Use stateless JWT tokens
// Session state in token, not server memory
const token = jwt.sign({
  userId: 'user-123',
  role: 'admin'
}, SECRET_KEY, { expiresIn: '1h' });

// Now any server can handle any request
// Enable round-robin load balancing
```

---

### Pitfall 5: No Rate Limiting

**Problem:**
```
User writes script:
  while (true) { callAPI(); }

Result: 
  - 100,000 req/sec from single user
  - $10,000 AWS bill
  - Service overload for all users
```

**Solution:**
```javascript
// API Gateway rate limiting
const limit = await rateLimiter.check(userId);
if (limit.exceeded) {
  return {
    statusCode: 429,
    headers: {
      'Retry-After': 3600
    },
    body: { error: 'Rate limit exceeded. Try again in 1 hour.' }
  };
}
```

---

## Key Takeaways for Interviews

### Elevator Pitch (30 seconds)

> "**Load Balancer** distributes traffic across servers for high availability and performance. It's infrastructure-focused - health checks, SSL termination, and traffic distribution.
>
> **API Gateway** is the smart front door for APIs - handles authentication, rate limiting, request transformation, and service routing. It's application-focused.
>
> In production microservices, they work together: Gateway decides which service, Balancer decides which instance."

---

### Must-Know Points

1. **Layer 4 vs Layer 7**: 
   - L4 = IP/port routing (faster)
   - L7 = Content-based routing (smarter)

2. **Load Balancer Algorithms**:
   - Round Robin (default)
   - Least Connections (varying workloads)
   - IP Hash (sticky sessions - avoid if possible)

3. **API Gateway Capabilities** (Remember: ARRTC)
   - **A**uthentication
   - **R**ate limiting
   - **R**outing
   - **T**ransformation
   - **C**aching

4. **Circuit Breaker**: Prevents cascading failures (Closed → Open → Half-Open)

5. **Health Checks**: How load balancers know which servers are healthy

6. **Stateless Design**: Use JWT tokens, not server-side sessions

---

## Quick Reference

### Popular Tools

**Load Balancers:**
- AWS: ALB (Layer 7), NLB (Layer 4)
- GCP: Cloud Load Balancing
- Azure: Application Gateway
- Open Source: NGINX, HAProxy

**API Gateways:**
- AWS: API Gateway, App Mesh
- Open Source: Kong, Tyk, KrakenD, Traefik
- Cloud: Apigee (Google), Azure API Management

---

### Useful Metrics to Know

**API Gateway:**
- Request rate (req/sec)
- Error rate (4xx, 5xx)
- Latency (p50, p95, p99)
- Cache hit rate
- Rate limit violations

**Load Balancer:**
- Active connections
- Unhealthy target count
- Target response time
- Request count per target
- Rejected connections

---

### Interview Red Flags to Avoid

❌ "Load balancer and API gateway are the same thing"
❌ "You don't need rate limiting if you have a good architecture"  
❌ "Health checks are unnecessary if you monitor your servers"
❌ "Sticky sessions are the best way to handle state"
❌ "Circuit breakers add unnecessary complexity"

✅ Instead, show understanding of tradeoffs and when to use each pattern.

---

## Conclusion

**API Gateway** and **Load Balancer** are complementary components in modern distributed systems:

- **Load Balancer**: Infrastructure resilience, traffic distribution
- **API Gateway**: Application intelligence, API management

Together, they create robust, scalable, secure API platforms that can handle millions of requests while providing excellent developer and user experiences.

**For Interviews:** Focus on understanding *why* each component exists, not just *what* it does. Be ready to discuss tradeoffs, real-world scenarios, and how they fit into modern cloud architectures.

---

*Document created: 2026-01-27*  
*Last updated: 2026-01-27*
