# 🌿 AGMS: Automated Green Management System

**Advanced Microservices Architecture with Centralized Security & Telemetry**

---

## 📖 Overview

The **Automated Green Management System (AGMS)** is a cloud-native microservices-based platform designed for **precision agriculture**. It integrates IoT telemetry, automated decision-making, and secure API communication to optimize environmental conditions for crops.

The system includes:

* A **centralized Auth Service** for authentication & authorization (JWT-based)
* A **Telemetry Bridge (Sensor Service)** for continuous IoT data ingestion
* A **Decision Engine (Automation Service)** to trigger smart actions
* A **Crop Service** for crop lifecycle and metadata management
* A **Zone Service** for managing agricultural zones and sensor mapping

---

## 🏗️ System Architecture

AGMS follows a **decoupled microservices architecture** ensuring scalability, fault tolerance, and maintainability.

---

### 🛠️ Infrastructure & Security Layer

| Service           | Port | Description                                          |
| ----------------- | ---- | ---------------------------------------------------- |
| **Config Server** | 8888 | Centralized configuration using Git-based repository |
| **Eureka Server** | 8761 | Service discovery and registration                   |
| **Auth Service**  | 8085 | JWT authentication & user authorization              |
| **API Gateway**   | 8080 | Single entry point with routing & security filtering |

---

### 🍃 Domain Services

| Service                | Port | Description                                                     |
| ---------------------- | ---- | --------------------------------------------------------------- |
| **Sensor Service**     | 8082 | Fetches IoT data every 10 seconds with fallback support         |
| **Automation Service** | 8083 | Processes telemetry and triggers hardware actions               |
| **Crop Service**       | 8084 | Manages crop data (type, growth stage, optimal conditions)      |
| **Zone Service**       | 8081 | Manages farm zones, sensor allocation, and environment grouping |

---

## 🔄 System Workflow

1. **Zone Service**

   * Defines farm zones (e.g., Greenhouse A, Field B)
   * Maps sensors to zones

2. **Crop Service**

   * Stores crop-specific thresholds (temperature, humidity, soil moisture)

3. **Sensor Service**

   * Fetches IoT telemetry data periodically
   * Sends data to Automation Service

4. **Automation Service**

   * Combines:

     * Sensor data
     * Zone mapping
     * Crop thresholds
   * Triggers actions (fan ON/OFF, irrigation, etc.)

5. **API Gateway**

   * Routes all external requests

6. **Auth Service**

   * Validates JWT tokens for secure access

---

## 🚀 Startup & Execution Guide

⚠️ **Important:** Services must be started in the correct order.

---

### 🔹 Phase 1: Infrastructure

1. Start **Config Server**
2. Start **Eureka Server**

   * Verify: http://localhost:8761
3. Start **Auth Service**

---

### 🔹 Phase 2: Application Services

4. Start **API Gateway**
5. Start **Crop Service**
6. Start **Zone Service**
7. Start **Automation Service**
8. Start **Sensor Service**

---

## 🔍 Validation Steps

### ✅ Eureka Dashboard

* Open: http://localhost:8761
* Ensure all services show **STATUS: UP**

**Expected Services (8 Total):**

* Config Server
* Eureka Server
* Auth Service
* API Gateway
* Sensor Service
* Automation Service
* Crop Service
* Zone Service

---

### ✅ API Testing (Postman)

1. **Login Request**

   * Endpoint: `/auth/login`
   * Get JWT token

2. **Access Secure Endpoint**

   ```
   GET http://localhost:8080/api/v1/sensors/latest
   ```

   * Add header:

   ```
   Authorization: Bearer <JWT_TOKEN>
   ```

---

## 📂 Project Structure

```text
AGMS-System/
├── agms-config-server/
├── agms-eureka-server/
├── agms-auth-service/
├── agms-api-gateway/
├── agms-sensor-service/
├── agms-automation-service/
├── agms-crop-service/
├── agms-zone-service/
├── config-repo/
├── docs/
│   └── eureka-status.png
├── README.md
└── AGMS.postman_collection.json
```

---

## 🛡️ Resilience & Fault Tolerance

* **Sensor Service Fallback**

  * Injects mock data when IoT API fails
  * Ensures uninterrupted automation

* **Loose Coupling**

  * Services operate independently
  * Failures are isolated

---

## 🔐 Security

* JWT-based authentication via **Auth Service**
* API Gateway enforces:

  * Token validation
  * Route protection
* Stateless and scalable authentication

---

## 🧪 Testing Strategy

* Postman collection included
* All endpoints accessed via API Gateway
* Protected routes require JWT

---

## 📝 Commit Guidelines

| Component      | Commit Message                                                  |
| -------------- | --------------------------------------------------------------- |
| Auth Service   | `feat(security): implement JWT-based Auth Service on port 8085` |
| Config Updates | `config(auth): connect to config server and enable discovery`   |
| Documentation  | `docs: update architecture with Auth, Crop, and Zone services`  |

---

## 🏁 Submission Checklist

* [ ] All 8 services visible in Eureka dashboard
* [ ] JWT authentication working
* [ ] Postman collection updated (includes login)
* [ ] Proper commit history (no bulk uploads)
* [ ] README documentation complete

---

## 📬 Support

For issues or improvements, open an issue or submit a pull request.

---

**AGMS — Intelligent Farming for a Sustainable Future 🌱**
