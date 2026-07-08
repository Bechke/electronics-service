# Electronics Service

Reactive Spring Boot microservice for managing electronics listings on the Bechke marketplace. Stores listings in MongoDB, publishes ad events to Kafka, caches via Redis, and registers with Eureka.

## Tech Stack

- Java 21 · Spring Boot 3 (WebFlux)
- MongoDB (reactive)
- Apache Kafka
- Redis
- Eureka client
- Zipkin tracing
- SpringDoc / Swagger UI

## Port

`9196` (gateway prefix: `/electronics`)

## Sub-categories

`MOBILE` · `LAPTOP` · `TABLET` · `TV` · `CAMERA` · `AUDIO` · `GAMING` · `APPLIANCE` · `ACCESSORIES` · `OTHER`

## Listing Status Values

`PENDING` · `ACTIVE` · `SOLD` · `INACTIVE`

## API Endpoints

All routes are exposed through the gateway at `/electronics/**`.

| Method | Path | Auth | Description |
|--------|------|------|-------------|
| `POST` | `/electronics` | Required | Create a new electronics listing |
| `GET` | `/electronics` | Public | List all electronics (paginated) |
| `GET` | `/electronics/{id}` | Public | Get listing by ID |
| `PUT` | `/electronics/{id}` | Required (owner only) | Update a listing |
| `DELETE` | `/electronics/{id}` | Required (owner only) | Delete a listing |
| `GET` | `/electronics/owner/{ownerId}` | Public | Get listings by owner |
| `GET` | `/electronics/org/{organizationId}` | Public | Get listings by organization |
| `GET` | `/electronics/search` | Public | Search with filters |

### Query Parameters — `/electronics/search`

| Param | Type | Description |
|-------|------|-------------|
| `subCategory` | string | Filter by sub-category |
| `city` | string | Filter by city |
| `minPrice` | decimal | Minimum price |
| `maxPrice` | decimal | Maximum price |

### Request Headers

| Header | Description |
|--------|-------------|
| `X-User-Id` | Injected by gateway — used as `ownerId` on create, ownership check on update/delete |
| `X-User-Email` | Injected by gateway |

## Listing Fields

```json
{
  "title": "iPhone 15 Pro 256GB",
  "subCategory": "MOBILE",
  "brand": "Apple",
  "model": "iPhone 15 Pro",
  "description": "Like new, 6 months old",
  "price": 75000,
  "currency": "₹",
  "originalPrice": 134900,
  "condition": "USED",
  "ageMonths": 6,
  "storageCapacity": "256GB",
  "ramCapacity": "8GB",
  "processorModel": "A17 Pro",
  "screenSize": "6.1 inch",
  "batteryCapacity": "3274 mAh",
  "os": "iOS 17",
  "color": "Natural Titanium",
  "warrantyAvailable": true,
  "warrantyDetails": "6 months seller warranty",
  "features": ["5G", "Face ID", "ProMotion"],
  "featureImage": "https://...",
  "imageUrls": ["https://...", "https://..."],
  "location": "Mumbai, Maharashtra",
  "country": "India",
  "state": "Maharashtra",
  "city": "Mumbai",
  "neighbourhood": "Andheri"
}
```

## Kafka Events

Topic: `electronics-ad-events`

Published on create, update, and delete. Event shape mirrors the `ElectronicsAdEvent` class.

## Environment Variables

| Variable | Default | Description |
|----------|---------|-------------|
| `SPRING_DATA_MONGODB_URI` | `mongodb://admin:adminpass@mongodb:27017/electronics-service-db?authSource=admin` | MongoDB connection URI |
| `REDIS_HOST` | `redis` | Redis host |
| `REDIS_PORT` | `6379` | Redis port |
| `KAFKA_BOOTSTRAP_SERVERS` | `kafka-container:9092` | Kafka brokers |
| `DISCOVERY_URL` | `http://localhost:8761` | Eureka server URL |

## Running with Docker Compose

The service is defined in `discovery-service/docker-compose.yml`:

```bash
cd discovery-service
docker compose up -d electronics-service
```

## Running Locally

```bash
./gradlew bootRun
```

Swagger UI available at: `http://localhost:9196/swagger-ui.html`
