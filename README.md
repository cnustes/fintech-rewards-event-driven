# Fintech Rewards System POC

This Proof of Concept (POC) demonstrates a microservices-based rewards system for a Fintech (Fintech style). The system processes rent payments and asynchronously grants reward points using Kafka.

## 🚀 Architecture
- **Payment Service (Port 8081):** Receives rent payments and publishes events to Kafka.
- **Rewards Service (Port 8082):** Listens to payment events and assigns points (1 point per dollar).
- **Infrastructure:** PostgreSQL, Kafka, Zookeeper, Prometheus, and Grafana.

## 🛠️ Prerequisites
- **Java 21**
- **Maven 3.8+**
- **Docker & Docker Compose**

---

## 🏃 Steps to Run Locally

### 1. Start Infrastructure
First, bring up the databases and messaging system:

```bash
docker compose up -d
```
*This command will start Postgres (for both services), Kafka, Zookeeper, Prometheus, and Grafana.*

### 2. Build the Project
From the project root, execute:

```bash
mvn clean install -DskipTests
```

### 3. Run Microservices
You can run them from your favorite IDE (like IntelliJ IDEA) or via terminal:

#### For Payment Service:
```bash
cd payment-service
mvn spring-boot:run
```

#### For Rewards Service:
```bash
cd rewards-service
mvn spring-boot:run
```

---

## 🧪 How to Test the Flow?

### 1. Simulate a Rent Payment
Send a POST request to the payments endpoint:

```bash
curl -X POST http://localhost:8081/api/payments/rent \
-H "Content-Type: application/json" \
-d '{
    "userId": "user-123",
    "amount": 1500.00
}'
```

### 2. Verify Logs
- The `payment-service` should show it received the payment and published the event to Kafka.
- The `rewards-service` should show it received the event and assigned 1500 points to the user.

### 3. Monitoring & Metrics
You can access the following observability tools:

- **Prometheus:** `http://localhost:9090` (Search for metric `fintech_rewards_points_total`)
- **Actuator Health (Payment):** `http://localhost:8081/actuator/health`
- **Actuator Health (Rewards):** `http://localhost:8082/actuator/health`
- **Grafana:** `http://localhost:3000`

---

## 🛡️ Implemented Patterns
- **Event-Driven Architecture:** Asynchronous communication via Kafka.
- **Idempotent Consumer:** Rewards Service verifies if the transaction ID was already processed.
- **Microservices Observability:** Native integration with Micrometer and Prometheus.
- **Clean Architecture:** Domain-centric design with clear separation of concerns.
- **Constructor Injection:** Use of `@RequiredArgsConstructor` and immutable components.
