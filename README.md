FBank API 🏦
============

FBank is a backend I built to practice Spring Boot while working on something closer to a real-world project. It manages customers, their bank accounts, and the relations between them. I also wanted to explore Spring Security with JWT and role-based access. All data is stored in PostgreSQL.

Key Features
------------

*   Authentication and roles handled with Spring Security + JWT
*   Create and manage customers and accounts (including joint accounts)
*   Validation for emails, NIFs, and IBANs with error messages
*   Swagger UI to explore and test the API
*   Unit and integration tests with JUnit 5 + Testcontainers (no need for a local test DB)

  
ERD Diagram
------------
![ERD Diagram](docs/schema.png)

Environment variables
---------------------
| Variable    | Description                                      |
|------------|--------------------------------------------------|
| DB_HOST     | PostgreSQL host                                  |
| DB_PORT     | PostgreSQL port (default 5432)                  |
| DB_NAME     | Database name                                   |
| DB_USERNAME | Database user                                   |
| DB_PASSWORD | Database password                               |
| JWT_SECRET  | JWT secret (must be at least 256-bit / 32 bytes) |


Generate a 256-bit secret with:

```bash
openssl rand -base64 32
```

Quick Start
-----------
**Local without Docker**

1. **Install PostgreSQL** if not installed.  
2. **Create the database**:

```bash
createdb -U postgres fbank
```

3. **Initialize the schema**:

```bash
psql -U postgres -d fbank_local -f src/main/resources/schema.sql
```

4. **Run the app**:
```bash
DB_HOST=localhost DB_PORT=5432 DB_NAME=fbank DB_USERNAME=postgres DB_PASSWORD=postgres JWT_SECRET=<32byte_secret> ./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

5. Open Swagger UI: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)


**Docker / Docker Compose**

1. **Build the app**:

```bash
./mvnw clean package -DskipTests
```

2. **Start everything using the included `docker-compose.yaml`**:

```bash
docker compose up --build
```

3. Access the app at [http://localhost:8080](http://localhost:8080)

> The DB is automatically initialized from `schema.sql`.

---

## Project Structure

* `account/` – Bank account logic  
* `customer/` – Customer data and validation  
* `customeraccount/` – Links customers with accounts  
* `user/` – Users, roles, and permissions  
* `address/` – Customer addresses  
* `auth/` – Login and JWT handling  
* `config/` – App and security configuration

---

## Testing

Tests use **JUnit 5 + Testcontainers**, so no local DB is required.

Run all tests:

```bash
./mvnw clean install -Dspring.profiles.active=test
```
---

## Notes

* Backend-only, frontend planned.  

---

## Contact ✉️

Open an issue in GitHub for suggestions or questions.
