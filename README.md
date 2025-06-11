# 🛡️ StormShield: Distributed Rate Limiting as a Service

## Overview

**StormShield** is a scalable, distributed Rate Limiting as a Service (RLaaS) platform designed for developers and companies to protect their APIs from abuse, ensure fair usage, and provide predictable API behavior. Inspired by Cloudflare, Kong, and Stripe's internal tooling, StormShield exposes REST APIs and plugins that can be integrated into client applications or gateways.

---

## 🌟 Key Features

* **Multi-tenant support** with custom limits per user/API key
* **Multiple algorithms**: Token Bucket, Leaky Bucket, Sliding Window
* **Global distributed counters** via Redis or Hazelcast
* **Real-time headers**: `X-RateLimit-Limit`, `X-RateLimit-Remaining`, `Retry-After`
* **Developer Dashboard** for managing limits, plans, and visualizing usage
* **Low-latency, horizontally scalable**, and resilient against node failures

---

## 📊 Use Cases

* SaaS APIs protecting public endpoints
* Internal microservices rate-limiting
* Tier-based pricing for API plans
* Fraud/threat mitigation for public-facing systems

---

## 🤖 Architecture

### ✈️ Components:

* **API Gateway / Proxy** (optional): Spring Cloud Gateway
* **Rate Limit Engine**: Core logic in Java (Spring Boot)
* **Data Layer**:

  * Redis (for fast counters)
  * PostgreSQL (for persistent configurations)
* **Control Plane**:

  * Admin APIs and Dashboard (React or Thymeleaf)
  * Plan & config management
* **Observability**:

  * Prometheus + Grafana for metrics
  * ELK Stack for logs

### 🔗 Interactions:

1. Client app sends request with `X-API-KEY`
2. StormShield API receives request and extracts metadata
3. Checks Redis for rate limit window
4. If within limit ➜ Allow request and update counters
5. If over limit ➜ Deny request with HTTP 429 + headers

---

## 🔋 Rate Limiting Algorithms

* **Token Bucket**: Refills at a fixed rate, ideal for bursty traffic
* **Leaky Bucket**: Fixed outflow rate, queues bursts
* **Sliding Window Log/Counter**: Real-time, fine-grained control

---

## 📒 API Endpoints

### ✅ For Consumers:

```http
GET /check-limit
Headers:
  X-API-KEY: abc123
  X-User-ID: user_001
Response:
  200 OK
  X-RateLimit-Remaining: 12
  X-RateLimit-Reset: 60
```

### ⚙️ For Admins:

```http
POST /plans
{
  "plan": "pro",
  "limit": 1000,
  "duration": "1h"
}
```

```http
POST /register-key
{
  "userId": "u123",
  "apiKey": "abc123",
  "plan": "pro"
}
```

---

## 🌐 Dashboard (Optional)

* Login/signup with JWT auth
* See plan limits, consumption
* View breach trends (charts, logs)
* Create/edit API keys and plans

---

## ⚖️ Tech Stack

| Layer            | Technology              |
| ---------------- | ----------------------- |
| Language         | Java 17                 |
| Framework        | Spring Boot 3.x         |
| Auth             | JWT, API Key            |
| Database         | PostgreSQL              |
| Cache/Atomic ops | Redis + Lua / Hazelcast |
| Queue (optional) | Kafka for audit logs    |
| Deployment       | Docker, K8s             |
| Monitoring       | Prometheus + Grafana    |

---

## 🪄 Scaling & Reliability

* Horizontal scaling across pods/nodes
* Redis Cluster or Hazelcast for distributed counting
* Retry logic + circuit breaker (Resilience4j)
* Stateless services using Redis as source of truth for limits

---

## 🚀 Roadmap Ideas

* Dynamic limit updates without downtime
* GraphQL support
* Mobile SDKs
* Webhooks on limit breach
* Billing integration for paid plans

---

## 🌐 GitHub Repo Structure (Suggested)

```
stormshield/
|-- api/ (rate-limit endpoints)
|-- admin/ (plan and key mgmt)
|-- core/ (rate-limiting algorithms)
|-- config/ (Redis, DB, Security configs)
|-- dashboard/ (React frontend)
|-- tests/
|-- scripts/ (DB setup, Redis LUA scripts)
```

---

## ✨ Ideal for:

* SDE-2/3 candidates showing concurrency, distributed systems skills
* Real-world system design showcase
* Open-source contribution ready

---

Want to add diagrams or runbook next?

---

## 🚀 Quick Start

```bash
mvn spring-boot:run
```

The server exposes `/check-limit` for consumers and simple admin endpoints. This implementation uses an in-memory token bucket for demo purposes.

