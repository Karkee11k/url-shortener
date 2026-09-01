# URL Shortener

## Features
- Create a short URL from a long URL.
- Redirect a short URL to the original URL.
- Return 404 if the short URL does not exist

## Tech Stack
- Java
- Spring Boot
- Spring Data JPA
- MySQL
- Docker
- Docker Compose

## Running the Application

### Prerequisites
- Docker

### 1. Clone the Repository
```bash
git clone https://github.com/Karkee11k/url-shortener.git
cd url-shortener
```

### 2. Create the ```.env``` file
```
MYSQL_USERNAME=urlshortener
MYSQL_PASSWORD=change-me 
MYSQL_ROOT_PASSWORD=change-root-me
```
> The ```.env``` file contains local database credentials  and should not
> be commited to the repository

### 3. Start the application
```docker compose up --build```

The application will be available at:
```http://localhost:8080```

### 4. Stop the application
```docker compose down```

MySQL data is persisted using a Docker volume

## API

### Create a Short URL
```
POST /api/urls
Content-Type: application/json
```

Request:
```json
{
  "url": "https://example.com"
}
```

Response:
```json
{
  "shortUrl": "http://localhost:8080/a"
}
```

### Redirect
```
Get /{shortCode}
```

For example

Get /a

redirects to the original URL.