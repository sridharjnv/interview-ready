# ☁️ Top 100 Microservices Interview Questions — SDE-1 Backend

> **Difficulty Legend:** 🟢 Basic | 🟡 Intermediate | 🔴 Advanced

---

## 1. Microservices Fundamentals (Q1–Q15)

1. 🟢 What are microservices? How are they different from monolithic architecture?
2. 🟢 What are the advantages and disadvantages of microservices?
3. 🟢 What are the key characteristics of microservices?
4. 🟢 When should you use microservices vs monolithic architecture?
5. 🟢 What is the Single Responsibility Principle in the context of microservices?
6. 🟢 What is a bounded context in microservices? How does it relate to Domain-Driven Design (DDD)?
7. 🟡 How do you decompose a monolith into microservices? What strategies do you follow?
8. 🟡 What is the difference between SOA (Service-Oriented Architecture) and microservices?
9. 🟡 What is the Strangler Fig pattern for migrating from monolith to microservices?
10. 🟡 What is the Database per Service pattern? Why is it important?
11. 🟡 What are the challenges of adopting microservices?
12. 🟡 What is domain-driven design (DDD)? How does it guide microservice boundaries?
13. 🟢 What is a service registry? Why is it needed?
14. 🟡 What is Conway's Law? How does it apply to microservices?
15. 🟡 What is the Twelve-Factor App methodology? How does it relate to microservices?

---

## 2. Inter-Service Communication (Q16–Q30)

16. 🟢 What are the different ways microservices communicate with each other?
17. 🟢 What is the difference between synchronous and asynchronous communication?
18. 🟢 What is REST? Why is it commonly used in microservices?
19. 🟡 What is gRPC? How does it differ from REST?
20. 🟡 What is GraphQL? When would you choose it over REST in microservices?
21. 🟡 What is message-driven communication? Explain with an example.
22. 🟡 What are message brokers? Name some popular ones (RabbitMQ, Kafka, SQS).
23. 🟡 What is the difference between a message queue and a publish-subscribe model?
24. 🟡 What is Apache Kafka? How does it work in a microservices ecosystem?
25. 🟡 What is the difference between Kafka and RabbitMQ?
26. 🟡 What is event-driven architecture? How does it work with microservices?
27. 🟡 What is a dead letter queue (DLQ)? When is it used?
28. 🟡 What is request-reply pattern in messaging?
29. 🟡 What is the difference between choreography and orchestration?
30. 🔴 How do you handle message ordering and idempotency in event-driven systems?

---

## 3. API Gateway & Service Discovery (Q31–Q42)

31. 🟢 What is an API Gateway? Why is it needed in microservices?
32. 🟢 What are the responsibilities of an API Gateway?
33. 🟡 What are some popular API Gateway implementations (Spring Cloud Gateway, Kong, NGINX, AWS API Gateway)?
34. 🟡 What is the difference between API Gateway and Load Balancer?
35. 🟡 What is rate limiting? How is it implemented at the API Gateway level?
36. 🟡 What is request routing in an API Gateway?
37. 🟢 What is service discovery? What are client-side vs server-side discovery?
38. 🟡 What is Netflix Eureka? How does it work as a service registry?
39. 🟡 What is Consul? How does it compare to Eureka?
40. 🟡 What is the difference between DNS-based and registry-based service discovery?
41. 🟡 What is the Backend for Frontend (BFF) pattern?
42. 🟡 How do you handle cross-cutting concerns (logging, auth) in an API Gateway?

---

## 4. Resilience & Fault Tolerance (Q43–Q55)

43. 🟢 What happens when one microservice goes down? How do you handle cascading failures?
44. 🟡 What is the Circuit Breaker pattern? How does it work?
45. 🟡 What are the states of a circuit breaker (Closed, Open, Half-Open)?
46. 🟡 What is Resilience4j? How do you use it in Spring Boot?
47. 🟡 What is the Retry pattern? When should you use it?
48. 🟡 What is the Bulkhead pattern? How does it isolate failures?
49. 🟡 What is the Rate Limiter pattern?
50. 🟡 What is the Timeout pattern? Why is it critical in microservices?
51. 🟡 What is the Fallback pattern? How do you implement it?
52. 🟡 What is the difference between Hystrix and Resilience4j?
53. 🟡 How do you implement health checks in microservices?
54. 🟡 What is graceful degradation in microservices?
55. 🔴 How do you design for eventual consistency in distributed systems?

---

## 5. Data Management (Q56–Q68)

56. 🟡 What is the Saga pattern? Explain choreography-based vs orchestration-based Sagas.
57. 🟡 What is eventual consistency? How does it differ from strong consistency?
58. 🟡 What is the CQRS (Command Query Responsibility Segregation) pattern?
59. 🟡 What is Event Sourcing? How does it work with CQRS?
60. 🟡 How do you handle distributed transactions in microservices?
61. 🟡 What is the Outbox pattern? How does it solve dual-write problems?
62. 🟡 What is database per service vs shared database? What are the trade-offs?
63. 🟡 How do you handle data consistency across multiple microservices?
64. 🟡 What is the CAP theorem? How does it affect microservice design?
65. 🟡 What is a materialized view in the context of microservices?
66. 🟡 How do you handle schema evolution in microservices?
67. 🟡 What is change data capture (CDC)? How is it used with microservices?
68. 🔴 What is the two-phase commit (2PC)? Why is it generally avoided in microservices?

---

## 6. Deployment & Containerization (Q69–Q80)

69. 🟢 What is Docker? Why is it important for microservices?
70. 🟢 What is the difference between a Docker image and a Docker container?
71. 🟢 What is a Dockerfile? Explain the common instructions (FROM, COPY, RUN, CMD, EXPOSE).
72. 🟡 What is Docker Compose? How do you orchestrate multiple microservices locally?
73. 🟡 What is Kubernetes? How does it help manage microservices?
74. 🟡 What are Pods, Deployments, and Services in Kubernetes?
75. 🟡 What is a CI/CD pipeline? How does it work with microservices?
76. 🟡 What is the difference between blue-green deployment and canary deployment?
77. 🟡 What is rolling deployment? How does it minimize downtime?
78. 🟡 What is infrastructure as code (IaC)? How do tools like Terraform fit?
79. 🟡 What is a container registry? Name some popular ones (Docker Hub, ECR, GCR).
80. 🟡 How do you handle configuration management for microservices in different environments?

---

## 7. Observability & Monitoring (Q81–Q90)

81. 🟢 What is observability in microservices? What are its three pillars?
82. 🟡 What is distributed tracing? Why is it important in microservices?
83. 🟡 What are trace ID and span ID? How do they help in debugging?
84. 🟡 What is Zipkin/Jaeger? How do they help with distributed tracing?
85. 🟡 What is the ELK stack (Elasticsearch, Logstash, Kibana)? How is it used for centralized logging?
86. 🟡 What is Prometheus? How does it collect metrics from microservices?
87. 🟡 What is Grafana? How do you visualize metrics and create dashboards?
88. 🟡 What is centralized logging? Why is it critical in a microservices architecture?
89. 🟡 What is a correlation ID? How do you propagate it across services?
90. 🟡 How do you set up alerting for microservice failures?

---

## 8. Security in Microservices (Q91–Q96)

91. 🟡 How do you handle authentication and authorization in microservices?
92. 🟡 What is OAuth 2.0? How is it used in microservices?
93. 🟡 What is an Identity Provider (IdP)? Examples: Keycloak, Auth0, Okta.
94. 🟡 What is mutual TLS (mTLS)? How does it secure service-to-service communication?
95. 🟡 What is the difference between API Key, OAuth, and JWT for API security?
96. 🔴 What is a service mesh? How does Istio/Linkerd help with security?

---

## 9. Spring Cloud & Ecosystem (Q97–Q100)

97. 🟡 What is Spring Cloud? What projects does it include?
98. 🟡 What is Spring Cloud Config? How does it provide centralized configuration?
99. 🟡 What is Spring Cloud Gateway? How does it work?
100. 🟡 What is Spring Cloud Sleuth / Micrometer Tracing? How does it enable distributed tracing?

---

> 💡 **Tip for candidates:** For SDE-1 roles, the most commonly asked areas are **Inter-Service Communication**, **Circuit Breaker/Resilience patterns**, **Saga pattern**, **API Gateway**, and **Docker/Kubernetes basics**. Make sure you can explain these with real-world examples.
