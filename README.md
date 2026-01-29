# AmIHired

A Spring Boot REST API application for managing job applications with user authentication and authorization.

## Overview

AmIHired is a job application tracking system that allows users to manage different types of job applications (Full-time, Part-time, and Internships) with secure JWT-based authentication.

## Features

- **User Management**
  - User registration and authentication
  - JWT-based security
  - Password management and updates
  - User profile updates

- **Job Application Management**
  - Create, read, update, and delete job applications
  - Support for multiple job types:
    - Full-time positions
    - Part-time positions
    - Internships
  - Track application status and details

- **Security**
  - JWT token-based authentication
  - Spring Security integration
  - Role-based access control
  - Secure password handling

## Technology Stack

- **Java**: 21
- **Spring Boot**: 4.0.1
- **Framework Components**:
  - Spring Web MVC
  - Spring Data JPA
  - Spring Security
  - Spring Validation
- **Database**: MySQL
- **Authentication**: JWT (JSON Web Tokens)
- **Build Tool**: Maven
- **Additional Libraries**:
  - Lombok
  - dotenv-java
  - JJWT (JWT library)

## Prerequisites

- Java 21 or higher
- Maven 3.6+
- MySQL 8.0+

## Getting Started

### 1. Clone the Repository

```bash
git clone <your-repository-url>
cd AmIHired-
```

### 2. Database Setup

Create a MySQL database for the application:

```sql
CREATE DATABASE amihired;
```

### 3. Configuration

Copy the example properties file and configure your database settings:

```bash
cp src/main/resources/application.properties.example src/main/resources/application.properties
```

Edit `application.properties` and update the following:

```properties
spring.application.name=amIHired
spring.datasource.url=jdbc:mysql://localhost:3306/amihired
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password
server.port=8080

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
```

### 4. Build the Project

```bash
./mvnw clean install
```

Or on Windows:

```bash
mvnw.cmd clean install
```

### 5. Run the Application

```bash
./mvnw spring-boot:run
```

Or on Windows:

```bash
mvnw.cmd spring-boot:run
```

The application will start on `http://localhost:8080`

## API Endpoints

### User Endpoints

- `POST /api/users/register` - Register a new user
- `POST /api/users/login` - Login and receive JWT token
- `PUT /api/users/update` - Update user information
- `PUT /api/users/change-password` - Change user password

### Job Application Endpoints

- `GET /api/jobs` - Get all job applications
- `GET /api/jobs/{id}` - Get a specific job application
- `POST /api/jobs` - Create a new job application
- `PUT /api/jobs/{id}` - Update a job application
- `DELETE /api/jobs/{id}` - Delete a job application

## Project Structure

```
src/
├── main/
│   ├── java/com/casey/aimihired/
│   │   ├── config/              # Configuration classes
│   │   ├── controller/          # REST controllers
│   │   ├── DTO/                 # Data Transfer Objects
│   │   ├── exception/           # Exception handlers
│   │   ├── impl/                # Service implementations
│   │   ├── models/              # Entity classes
│   │   ├── repo/                # Repository interfaces
│   │   ├── security/            # Security filters and utilities
│   │   ├── service/             # Service interfaces
│   │   └── util/                # Utility classes
│   └── resources/
│       └── application.properties
└── test/
    └── java/com/casey/aimihired/
        └── unitTest/            # Unit tests
```

## Testing

Run the tests using Maven:

```bash
./mvnw test
```

Or on Windows:

```bash
mvnw.cmd test
```

## Security

The application uses JWT (JSON Web Tokens) for authentication. To access protected endpoints:

1. Register a new user or login with existing credentials
2. Include the JWT token in the `Authorization` header:
   ```
   Authorization: Bearer <your-jwt-token>
   ```

## Error Handling

The application includes global exception handling that returns standardized error responses with appropriate HTTP status codes.

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

[Specify your license here]

## Contact

[Your contact information]

## Acknowledgments

- Spring Boot for the excellent framework
- JWT for secure authentication
- MySQL for reliable data storage
