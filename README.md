# Data Hub API

Spring Boot and Maven REST API for user management CRUD operations.

## Requirements

- Java 17+
- Maven 3.9+

## Run

```bash
mvn spring-boot:run
```

The API starts on `http://localhost:8080` and uses an in-memory H2 database by default.

## Azure MySQL

Use the `azure` profile when deploying with Azure Database for MySQL Flexible Server. Set these environment variables through the App Service configuration or deployment environment; do not commit database credentials:

```text
AZURE_MYSQL_HOST=your-server.mysql.database.azure.com
AZURE_MYSQL_PORT=3306
AZURE_MYSQL_DATABASE=datahub
AZURE_MYSQL_USERNAME=your-user
AZURE_MYSQL_PASSWORD=your-password
```

Start with the Azure profile:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=azure
```

The Azure profile requires the database schema to already exist and uses TLS. Apply schema changes through a migration tool before deployment.

## Endpoints

| Method | Path | Description |
| --- | --- | --- |
| `GET` | `/api/users` | List users |
| `GET` | `/api/users/{id}` | Get one user |
| `POST` | `/api/users` | Create a user |
| `PUT` | `/api/users/{id}` | Update a user |
| `DELETE` | `/api/users/{id}` | Delete a user |

Create and update requests use this JSON body:

```json
{
	"name": "Ada Lovelace",
	"email": "ada@example.com"
}
```

Run the test suite with:

```bash
mvn test
```
