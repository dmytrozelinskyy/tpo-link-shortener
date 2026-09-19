# TPO Link Shortener

A URL shortener built with **Spring Boot*, offering both a REST API and a simple web UI. Users can generate short, unique links for long URLs, optionally protect them with a password, and track visit counts.

## Features

- **Shorten URLs** - generates a random 10-character alphanumeric short code for any target URL
- **Redirect** - visiting a short link's `/red/{id}` route looks it up and logs a visit
- **Password-protected links** - optional password required to edit or delete a link
- **Full CRUD REST API** - create, read, update, and delete links via JSON
- **Web UI** - Thymeleaf-based pages to create and manage links without calling the API directly
- **Custom validation** - target URLs must be HTTPS and unique; passwords (when set) must meet complexity rules (upper/lowercase, digits, special characters, minimum length)
- **Internationalization** - UI and validation message available in English, Polish, and German

## Tech Stack 

- **Java 21**, **Spring Boot 3.5**
- Spring Web, Spring Data JPA, Spring Validation, Thymeleaf
- **H2** in-memory database
- Gradle

## Usage

### Prerequisites
- Java 21+


### Run locally
```bash
./gradlew bootRun
```
The app starts on `http://localhost:8080`.

- Web UI: `http://localhost:8080/create`
- H2 console: `http://localhost:8080/tpo_11-console`

## Example: create a link
```bash
curl -X POST http://localhost:8080/api/links \
     -H "Content-Type: application/json" \
     -d '{"name": "my-link", "targetUrl": "https://example.com", "password" "Optional123!@#"}'
```

## Project Structure 

```
src/main/java/pl/edu/tpo_10/
|- controller/   # REST + web MVC endpoints
|- service/      # Business logic
|- repository/   # Spring Data JPA repository
|- model/        # JPA entity
|- dto/          # Request/response objects
|- mapper/       # Entity - DTO mapping
|- constraint/   # Custom Bean Validation annotations
```




