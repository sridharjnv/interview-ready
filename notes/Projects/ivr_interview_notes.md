# 🎯 IVR Voice Agent — Interview Preparation Notes

---

## 📋 Quick Elevator Pitch (30 seconds)

> "I built an **enterprise-grade AI-powered IVR payment system** that enables customers to make loan payments over phone calls using **natural voice conversations**. It integrates **Twilio** for telephony, supports **dual AI providers** (Deepgram & AWS Bedrock Nova Sonic) via a pluggable Strategy pattern, and uses **Model Context Protocol (MCP) function calling** so the AI autonomously orchestrates the entire payment flow — from authentication to payment confirmation — all in **real-time bidirectional audio streaming** with sub-100ms latency. The system supports **English and Spanish**, handles **barge-in interruptions**, and follows **PCI-DSS compliance** for sensitive financial data."

---

## 🏢 Business Perspective

### What Problem Does It Solve?

| Problem | How IVR Solves It |
|---|---|
| Customers wait in long queues to make payments via call centers | Fully automated self-service payment channel — **zero human agents needed** |
| Call center operational costs are high ($5–$15 per human-handled call) | AI voice agent handles calls at a **fraction of the cost** (~66% reduction with Nova Sonic) |
| Customers need 24/7 payment access | Available **round-the-clock**, no staffing constraints |
| Language barriers for Spanish-speaking customers | **Native bilingual support** (English & Spanish) with localized voices |
| Legacy IVR systems use rigid menu trees ("Press 1 for...") | **Conversational AI** — customers speak naturally, no memorizing menu options |
| PCI compliance risks when agents handle card numbers verbally | **Data minimization** — sensitive data stays server-side, only masked info reaches AI |

### Business Impact & Metrics You Can Mention

- **Cost Reduction**: ~66% savings on voice AI operations using AWS Bedrock Nova Sonic vs. per-minute pricing models
- **Scalability**: Supports up to **1,000 concurrent sessions** with in-memory + Redis distributed state
- **Multi-Tenant**: Single deployment serves **multiple partner organizations** (banks, lenders) via DNIS-based routing and partner-specific configurations
- **Customer Experience**: Natural language conversations, real-time barge-in, inactivity management with polite warnings

### Revenue/Value Proposition

- **Self-service payment channel** reduces dependency on expensive human CSR agents
- **Multi-tenant architecture** means one deployment serves many clients → higher margins
- **CSR handoff flow** ensures complex cases still get human attention (best of both worlds)
- **Audit trail** — every conversation turn is logged to DB + audio stored in S3 for compliance

---

## 🏗️ Technical Architecture Overview

### System Architecture Diagram

```
┌─────────────────┐    WebSocket (μ-law 8kHz)     ┌──────────────────────────────┐
│     Customer     │ ──► Twilio Telephony ◄──────► │   Spring Boot Application    │
│   (Phone Call)   │                               │    Java 21 / Spring Boot 3.5 │
└─────────────────┘                               └──────────┬───────────────────┘
                                                              │
                                          ┌───────────────────┴───────────────────┐
                                          ▼                                       ▼
                           ┌──────────────────────────┐         ┌──────────────────────────┐
                           │  Deepgram Voice Agent     │         │  AWS Bedrock Nova Sonic   │
                           │  (WSS: Nova-3 STT         │         │  (HTTP/2 Bidirectional    │
                           │   + GPT-4.1-mini LLM      │         │   Speech-to-Speech)       │
                           │   + Aura-2 TTS)           │         │                          │
                           └────────────┬─────────────┘         └────────────┬─────────────┘
                                        │                                    │
                                        └───────────────┬────────────────────┘
                                                        │  MCP Tool Execution
                                                        ▼
                                           ┌──────────────────────────┐
                                           │   MCPOrbipayService      │
                                           │   (15+ Payment Tools)    │
                                           └──────────┬───────────────┘
                                                      │
                              ┌────────────────────────┼────────────────────────┐
                              ▼                        ▼                        ▼
                   ┌──────────────────┐    ┌──────────────────┐    ┌──────────────────┐
                   │ Orbipay REST APIs │    │  PostgreSQL/MySQL │    │  Redis/Valkey    │
                   │ (Payments +       │    │  (Partner Config  │    │  (Session Cache  │
                   │  Ancillary)       │    │   + Conv. Logs)   │    │   + Prompts)     │
                   └──────────────────┘    └──────────────────┘    └──────────────────┘
```

### Tech Stack Summary Table

| Layer | Technology | Version |
|---|---|---|
| **Language** | Java (Amazon Corretto) | 21 |
| **Framework** | Spring Boot | 3.5.0 |
| **Telephony** | Twilio Media Streams | WebSocket + TwiML |
| **Voice AI #1** | Deepgram Voice Agent API | Nova-3 STT, GPT-4.1-mini, Aura-2 TTS |
| **Voice AI #2** | AWS Bedrock Nova Sonic | amazon.nova-sonic-v1:0 (HTTP/2) |
| **Database** | PostgreSQL / MySQL | 42.7.3 / 8.0.33 |
| **Cache** | Redis / Valkey Cluster | 3-node cluster (Redisson) |
| **Object Storage** | AWS S3 | Audio recordings per turn |
| **Security** | HMAC-SHA256, nCipher HSM | Custom OPAY3 auth headers |
| **Build** | Maven (multi-module) | — |
| **Testing** | JUnit 5, Mockito, Jacoco | 5.9.1 / 5.14.2 / 0.8.11 |

---

## 🔄 Payment Flow — Step by Step

> [!TIP]
> This is the **most likely interview question**: *"Walk me through the payment flow."*

```
1. Customer dials phone number
       │
2. Twilio routes call → POST /twilio webhook
       │
3. App queries DB → maps DNIS phone number to partner_id + partner_key
       │
4. Fetches partner config from Orbipay Ancillary API
       │
5. Validates: Is it Single Profile Account (SPA)?
       ├── NO  → Play apology → Hang up in 10s
       └── YES → Continue
       │
6. Opens WebSocket (/media) → Bidirectional audio stream begins
       │
7. Initializes Voice AI session (Deepgram or Nova Sonic)
       │
8. Sends welcome message → "Welcome to payments. English or Spanish?"
       │
═══════════════════════════════════════════════════════
   MCP TOOL CHAIN (AI autonomously drives these steps)
═══════════════════════════════════════════════════════
       │
9.  select_language          → Sets locale (en/es)
10. get_auth_parameters      → Returns required fields (Account #, SSN, ZIP, etc.)
11. propose + confirm fields → Step-by-step verbal collection & confirmation
12. authenticate_customer    → POST to Auth API → returns customerId
13. retrieve_customer_accounts → Fetches loan accounts (shows last 4 digits only)
14. get_payment_amounts      → Shows: min due, statement balance, payoff, custom
15. set_payment_amount_type  → Customer chooses amount
16. retrieve_funding_accounts → Fetches saved bank/card accounts (masked)
17. confirm_payment          → "Pay $X from account ending 1234?"
       ├── YES → POST /payments → Returns confirmation #
       └── NO  → Cancel → End call
       │
18. Confirmation message → "Payment confirmed. Reference: A12N12I12L"
19. end_call → Graceful hangup
```

---

## 🎨 Design Patterns Used

> [!IMPORTANT]
> **Interviewers love design pattern questions.** Here are the ones you used and WHY.

### 1. Strategy Pattern — Voice AI Provider Switching

```java
interface VoiceAiProvider {
    void initializeSession(CallSession session);
    void sendAudio(byte[] audioData);
    void close();
}

// Implementations:
class DeepgramVoiceAiProvider implements VoiceAiProvider { ... }
class NovaSonicVoiceAiProvider implements VoiceAiProvider { ... }
```

**Why?** Allows swapping between Deepgram and AWS Bedrock Nova Sonic with a **single config property** (`app.voice.provider=deepgram|bedrock-nova-sonic`). No code changes needed. Provider is selected at **startup time** (not runtime), so hot audio paths have zero branching overhead.

**Interview Answer**: *"We used the Strategy pattern to decouple the voice AI layer. This let us evaluate multiple vendors — Deepgram for rapid prototyping and Nova Sonic for cost optimization — without touching any business logic. The same MCPOrbipayService serves both providers identically."*

### 2. Model Context Protocol (MCP) — Function Calling

**Why?** Instead of hardcoding conversation flow logic, we define **structured tool specifications** with JSON Schema. The AI model autonomously decides WHEN to call each tool based on conversation context.

**Interview Answer**: *"We implemented MCP function calling where the LLM acts as the orchestrator. It receives tool definitions (like `authenticate_customer`, `retrieve_accounts`) with parameter schemas, and autonomously invokes them at the right moment. This makes the flow self-driving — we don't need if/else conversation trees."*

### 3. Finite State Machine — Payment Flow State

```
WELCOME → LANGUAGE_SELECTION → AUTHENTICATION → ACCOUNT_SELECTION
    → PAYMENT_AMOUNT → FUNDING_SELECTION → PAYMENT_CONFIRMATION → PAYMENT_COMPLETED
```

**Why?** Prevents invalid state transitions (e.g., can't confirm payment before selecting an account). Each MCP tool validates the current state before executing.

### 4. Adapter Pattern — Audio Codec Bridging

```java
class AudioCodecAdapter {
    // Twilio sends: μ-law 8kHz 8-bit mono
    // Nova Sonic needs: PCM 16-bit LE 16kHz input
    // Nova Sonic returns: PCM 16-bit LE 24kHz output
    // Must convert back to μ-law 8kHz for Twilio
}
```

**Why?** Keeps both Twilio handlers and voice AI providers completely decoupled from audio format details. Deepgram uses native μ-law (zero transcoding), while Nova Sonic requires full PCM conversion.

### 5. Circuit Breaker / Fallback Pattern

- Redis cache miss → falls back to DB query
- Voice AI connection drop → plays localized apology via TwiML → graceful hangup
- Auth failure counter → locks after N attempts → polite termination or CSR transfer

### 6. Template Method Pattern — Cache Loading

`PromptCacheLoader` extends `CacheLoader` — standardized warmup, sync, and stale-entry eviction across Redis clusters.

---

## 🔐 Security & Compliance

> [!CAUTION]
> For fintech/payments interviews, security questions are **guaranteed**. Know these cold.

| Security Measure | Implementation |
|---|---|
| **PCI-DSS Data Minimization** | Full account/card numbers stay in server memory (`CallSession`). Only last 4 digits + nicknames sent to AI voice stream |
| **PII Redaction in Logs** | `SensitiveDataRedactor` strips account_number, pin, cvv, pan, ssn, dob, email before logging |
| **CVV Handling** | Held in transient memory ONLY during payment confirmation step. Never persisted to DB or cache |
| **API Authentication** | All Orbipay API calls use `OPAY3-HMAC-SHA256` signed headers |
| **HSM Integration** | nCipher Hardware Security Module for encryption at rest and in transit |
| **CORS Protection** | Whitelist enforcement for Twilio domains and designated tunnel patterns |
| **Rate Limiting** | Sliding-window rate limiter (60 req/min default) |
| **WebSocket Security** | Max 1000 concurrent sessions, 1 connection per call, 1MB message size limit, 8KB audio chunks, max 50 buffered chunks |
| **Input Validation** | Strict regex on DTMF, tool args, phone numbers. Type-safe deserialization prevents injection |
| **CSR SIP Transfer** | IP ACLs + TLS on Twilio SIP domains. Silent auth validates X-Customer-Id headers |

---

## 🧩 Key Components Deep Dive

### MediaStreamService — The Central Orchestrator

This is the **heart of the system**. It:
- Accepts Twilio WebSocket connections on `/ivrvoiceagent/media`
- Extracts `CallSid` and `StreamSid` from the Twilio `start` event
- Routes inbound μ-law audio frames to the active Voice AI provider
- Receives synthesized speech from Voice AI → forwards to Twilio
- Handles **barge-in** (user interruption) via atomic `audioInterrupted` flag
- Manages DTMF keypad events via `DTMFDetectionService`
- Coordinates session lifecycle (create → active → cleanup)

### MCPOrbipayService — The Business Logic Engine

Implements **15+ MCP tools** that the AI model calls:

| Tool | Purpose |
|---|---|
| `select_language` | Switch English ↔ Spanish |
| `get_authentication_parameters` | Returns required auth fields per partner config |
| `propose_authentication_field` | Step-by-step verbal collection of auth values |
| `confirm_authentication_field` | Verbal confirmation of each collected field |
| `authenticate_customer` | Submits credentials to Orbipay Auth API |
| `retrieve_customer_accounts` | Fetches loan/billing accounts |
| `get_payment_amounts` | Returns configured amount types (min due, payoff, etc.) |
| `set_payment_amount_type` | Stores chosen payment amount |
| `retrieve_funding_accounts` | Fetches saved bank/card payment methods |
| `start_add_funding_account` | Begins inline funding account creation flow |
| `propose/confirm_funding_account_field` | Staged collection of new bank/card details |
| `add_funding_account` | Creates new funding account via Payments API |
| `set_card_cvv_for_payment` | Collects 3-4 digit CVV for card payments |
| `confirm_payment` | Executes payment → returns confirmation number |
| `return_to_csr` | Redirects call back to human agent |
| `end_call` | Graceful call termination |

### CSR Transfer Flow — Hybrid Human+AI

```
Customer on IVR → Needs to add funding account → AI can't handle card input securely
       │
       ▼
IVR transfers call to CSR via Twilio SIP with custom headers:
  X-Session-Id, X-Customer-Id, X-Original-DNIS, X-Csr-Transfer-Number
       │
       ▼
CSR adds funding account manually → Transfers back to IVR
       │
       ▼
IVR receives call on /twilio/csr → Parses SIP headers
       │
       ▼
Silent authentication (trust-based, no re-login needed)
       │
       ▼
Scoped tools: Only funding account addition + CSR return exposed
       │
       ▼
On completion → TwiML redirect dials CSR back
```

---

## ⚡ Performance & Scalability

| Aspect | Implementation |
|---|---|
| **Latency** | Sub-100ms audio streaming via WebSocket (Twilio) and HTTP/2 (Nova Sonic) |
| **Concurrency** | 1,000 max concurrent WebSocket sessions |
| **Thread Pools** | Dedicated async pools: `VoiceAI-Executor`, `NovaConnect`, `AudioS3Upload` |
| **Session Management** | In-memory `ConcurrentHashMap` + Redis distributed cache (dual-layer) |
| **Memory Safety** | Bounded audio buffers (8KB/chunk, max 50 chunks). Scheduled cleanup every 5 min. 1-hour max session TTL |
| **Cache Strategy** | Two-tier Redis: CONFIG cache (partner prompts) + DATA cache (sessions, conversations) |
| **Audio Storage** | Async S3 upload per conversational turn (non-blocking) |

---

## 🧪 Testing Strategy

| Test Type | Coverage |
|---|---|
| **Unit Tests** | `MCPOrbipayServiceTest`, `PaymentsServiceTest`, `LanguageServiceTest`, `CsrSipHeaderParserTest` |
| **Integration Tests** | `DeepgramConnectionIntegrationTest`, `NovaSonicIntegrationTest`, `MediaStreamIntegrationTest`, `TwiloWebhookIntegrationTest` |
| **Cache Tests** | `IVRSessionCacheServiceTest`, `TwiloSessionCacheServiceTest`, `ConversationMessagesCacheServiceIntegrationTest` |
| **Simulator** | Standalone Spring Boot mock (`ivr-simulator`) on port 8030 — mocks all Orbipay EBPP APIs for local dev |
| **Code Coverage** | Jacoco 0.8.11 |

---

## 🎤 Common Interview Questions & Answers

### Q1: "Tell me about a challenging project you worked on."

> "I built an AI-powered IVR payment system that lets customers make loan payments via natural voice conversations. The biggest challenge was implementing **real-time bidirectional audio streaming** between Twilio (μ-law 8kHz) and two different AI providers — Deepgram (WebSocket) and AWS Bedrock Nova Sonic (HTTP/2). Each had different audio formats, so I built an `AudioCodecAdapter` that handles μ-law ↔ PCM conversion with sample rate changes (8kHz ↔ 16kHz ↔ 24kHz), keeping the core services completely decoupled from format details."

### Q2: "How did you handle security for a payment system?"

> "We followed PCI-DSS principles. Full account numbers and card details stay in server-side memory (`CallSession`) and are never sent to the AI voice stream — only masked last-4-digit representations. CVV is held in transient memory only during the payment confirmation step and is never persisted. All API calls use HMAC-SHA256 signed headers, and we integrate with nCipher HSM for encryption. A `SensitiveDataRedactor` strips PII from all application logs."

### Q3: "How did you make the system extensible?"

> "I used the **Strategy pattern** for voice AI providers. Adding a new AI vendor means implementing the `VoiceAiProvider` interface — no changes to telephony, business logic, or payment code. The MCP tool architecture is also extensible — adding a new payment capability means defining a new tool schema and implementing the handler in `MCPOrbipayService`."

### Q4: "How does the AI know what to do? You don't hardcode conversation flows?"

> "No hardcoded flows. We use **Model Context Protocol (MCP) function calling**. The AI receives tool definitions with JSON schemas (e.g., `authenticate_customer` needs `account_number` and `ssn`). It decides autonomously when to call each tool based on the conversation context. We guide it with a carefully crafted system prompt and enforce valid state transitions via a **Finite State Machine** (`PaymentFlowState`)."

### Q5: "How do you handle when the user interrupts the AI mid-sentence?"

> "We implemented a **deterministic barge-in mechanism**. When the Voice AI detects user speech (via `UserStartedSpeaking` event from Deepgram or `contentStart{role:USER}` from Nova Sonic), we set an atomic flag `audioInterrupted=true`. This immediately stops forwarding any buffered agent audio to Twilio, so the user doesn't hear overlapping speech. The AI then processes what the user said and responds naturally."

### Q6: "What's your deployment architecture?"

> "Docker containers on Amazon Linux 2023 with Corretto 21. The `boot.sh` startup script pulls environment-specific configs from S3. We have 4 environment profiles — local, dockerdev, dev, and QA — each with their own `application.properties`, Redis configs, and SSL keystores. For local development, we have Docker scripts for PostgreSQL and a 3-node Valkey/Redis cluster, plus a standalone API simulator."

### Q7: "How do you handle failures gracefully?"

> "Multiple layers: (1) MCP tool errors return structured JSON with polite `conversational_response` text — the AI reads it naturally without exposing stack traces. (2) Auth failures have retry limits (2-3 attempts) before lockout. (3) Voice AI disconnections trigger a TwiML apology message in the caller's language. (4) Inactivity timeouts have a graduated warning system — first warning, 15-second buffer, final warning, then hangup. (5) Redis cache misses fall back to DB queries."

### Q8: "Why two AI providers? Deepgram AND Nova Sonic?"

> "**Deepgram** was our first integration — it offers best-in-class STT (Nova-3) and was faster to prototype with via WebSocket. **AWS Bedrock Nova Sonic** was added for **cost optimization** (~66% cheaper) and because it's a unified speech-to-speech model (no separate STT/LLM/TTS pipeline). The Strategy pattern made adding the second provider a clean exercise — same business logic, different transport."

### Q9: "Walk me through the data flow of a single audio frame."

> "1. Customer speaks → analog audio hits their phone carrier → Twilio receives it and encodes as **μ-law 8kHz 8-bit mono**. 2. Twilio sends a base64-encoded `media` event over the WebSocket to our `/media` endpoint. 3. `MediaWebSocketController` decodes it and passes raw bytes to `MediaStreamService`. 4. For Deepgram: direct pass-through (same format). For Nova Sonic: `AudioCodecAdapter` converts μ-law 8kHz → PCM 16-bit LE 16kHz. 5. AI processes speech, generates response, synthesizes audio. 6. Return path: Nova Sonic returns PCM 24kHz → `AudioCodecAdapter` converts to μ-law 8kHz → base64 → WebSocket `media` event back to Twilio → customer hears it."

### Q10: "How is this multi-tenant?"

> "When a call comes in, the DNIS (called phone number) is used to query `ivr_cnfg_tbl` and `ivr_property_cnfg_tbl` in the database to find the `partner_id`. Each partner has their own configurations — authentication fields, allowed payment types, system prompts, welcome messages (in both languages), and TTS voice preferences. These are stored in the `ivr_voice_agent_partner_prompt_cnfg_tbl` table and cached in Redis. A single deployment serves all partners."

---

## 📊 Numbers to Remember

| Metric | Value |
|---|---|
| **Codebase** | Java 21, Spring Boot 3.5.0 |
| **Lines of Code** | ~50+ source files across 12+ packages |
| **MCP Tools** | 15+ payment/auth tools |
| **Concurrent Sessions** | Up to 1,000 |
| **Audio Latency** | Sub-100ms |
| **Languages** | English + Spanish |
| **Cost Saving** | ~66% with Nova Sonic |
| **Session TTL** | 1 hour max, cleanup every 5 min |
| **Auth Retries** | 2-3 before lockout |
| **Test Coverage** | Unit + Integration + Simulator |
| **Environments** | Local, DockerDev, Dev, QA |

---

## 🗂️ Project Structure Cheat Sheet

```
ivr-voice-agent/
├── codebase/
│   ├── ivrvoiceagentconf/          # Per-environment configs (local/dev/qa)
│   │   └── {env}/
│   │       ├── application.properties
│   │       ├── application.yaml
│   │       ├── encryption/         # SSL keystores (nCipher HSM)
│   │       └── redis/              # Redisson cluster configs
│   └── ivrvoiceagentservice/       # Main Spring Boot microservice
│       └── src/main/java/
│           ├── controller/         # Twilio webhooks, WebSocket, health
│           ├── service/
│           │   ├── voiceai/        # VoiceAiProvider Strategy (Deepgram + Nova Sonic)
│           │   ├── payment/        # MCPOrbipayService (business logic)
│           │   ├── twilio/         # MediaStreamService, DTMF, call warnings
│           │   ├── audio/          # AudioCodecAdapter, S3 storage, WAV conversion
│           │   ├── csr/            # CSR SIP transfer, silent auth
│           │   ├── language/       # Bilingual prompt management
│           │   └── conversation/   # Conversation logging
│           ├── cache/              # Redis cache (sessions, prompts, conversations)
│           ├── model/              # Domain models (CallSession, PaymentDetails, etc.)
│           ├── config/             # Spring configs (WebSocket, async, Jackson, etc.)
│           └── util/               # Redaction, HTTP helpers, template rendering
├── simulators/                     # Mock Orbipay EBPP APIs (port 8030)
└── utilities/                      # Docker scripts (PostgreSQL, Valkey cluster)
```

---

> [!TIP]
> **Interview Pro Tips:**
> - Lead with the **business impact** first, then dive into technical details when asked
> - Use the **audio frame data flow** answer to showcase your systems thinking
> - Mention **trade-offs** you considered (e.g., Deepgram vs Nova Sonic, WebSocket vs HTTP/2)
> - The **barge-in mechanism** and **MCP function calling** are unique talking points that differentiate this from standard IVR projects
> - Always tie security decisions back to **PCI-DSS compliance** — interviewers love hearing about real-world regulatory constraints
