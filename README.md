# Bank Management System - Authentication Service

A comprehensive banking system with JWT-based authentication, microservices architecture, and complete customer management functionality.

## 🏗️ Architecture Overview

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Frontend UI   │    │  API Gateway    │    │  Auth Service   │
│   (Port 8085)   │◄──►│   (Port 8080)   │◄──►│   (Port 8081)   │
└─────────────────┘    └─────────────────┘    └─────────────────┘
                                │                       │
                                ▼                       ▼
                       ┌─────────────────┐    ┌─────────────────┐
                       │ Customer Service│    │   Redis Cache   │
                       │   (Port 8082)   │    │   (Port 6379)   │
                       └─────────────────┘    └─────────────────┘
```

## 🚀 Services

### 1. Authentication Service (Port 8081)
- JWT token generation and validation
- Customer registration and login
- Token refresh mechanism
- Redis-based token storage
- Swagger UI documentation

### 2. API Gateway (Port 8080)
- Route management for all services
- Load balancing
- Service discovery integration

### 3. Customer Service (Port 8082)
- Customer data management
- Password encryption
- Email-based customer lookup

### 4. Frontend UI (Port 8085)
- Modern responsive web interface
- Customer registration and login forms
- Dashboard with banking features
- KYC integration

## 🛠️ Technology Stack

- **Backend**: Spring Boot 3.1.2, Spring Security, Spring Cloud
- **Authentication**: JWT (JSON Web Tokens)
- **Database**: Oracle Database
- **Cache**: Redis
- **Frontend**: JSP, Bootstrap 5, JavaScript
- **API Documentation**: Swagger/OpenAPI 3
- **Build Tool**: Maven

## 📋 Prerequisites

1. **Java 17** or higher
2. **Maven 3.6** or higher
3. **Oracle Database** (or update to your preferred database)
4. **Redis Server** (for token storage)
5. **Eureka Server** (for service discovery)

## 🚀 Quick Start

### 1. Start Redis Server
```bash
# Install Redis (Ubuntu/Debian)
sudo apt-get install redis-server

# Start Redis
sudo systemctl start redis-server

# Verify Redis is running
redis-cli ping
```

### 2. Start Eureka Server
```bash
cd eureka-server
mvn spring-boot:run
```

### 3. Start Customer Service
```bash
cd customer-service
mvn spring-boot:run
```

### 4. Start Auth Service
```bash
cd auth-service
mvn spring-boot:run
```

### 5. Start API Gateway
```bash
cd apiGateway-service
mvn spring-boot:run
```

### 6. Start Frontend UI
```bash
cd FRONTEND-UI
mvn spring-boot:run
```

## 🔐 Authentication Flow

### 1. Customer Registration
```
POST /auth/register
Content-Type: application/json

{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "phoneNumber": "1234567890",
  "address": "123 Main St, City, State",
  "dateOfBirth": "1990-01-01",
  "password": "securePassword123"
}
```

### 2. Customer Login
```
POST /auth/login
Content-Type: application/json

{
  "email": "john.doe@example.com",
  "password": "securePassword123"
}
```

### 3. Token Refresh
```
POST /auth/refresh
Cookie: refresh_token=<refresh_token_value>
```

### 4. Logout
```
POST /auth/logout
Cookie: jwt_token=<jwt_token_value>
```

## 📚 API Documentation

### Swagger UI URLs
- **Auth Service**: http://localhost:8081/swagger-ui/index.html
- **Customer Service**: http://localhost:8082/swagger-ui/index.html

### Available Endpoints

#### Authentication Service (Port 8081)
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/auth/register` | Register new customer |
| POST | `/auth/login` | Customer login |
| POST | `/auth/refresh` | Refresh JWT token |
| POST | `/auth/logout` | Customer logout |
| GET | `/auth/health` | Health check |

#### Customer Service (Port 8082)
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/customer/register` | Register customer (legacy) |
| POST | `/customers/register` | Register customer |
| GET | `/customer/email/{email}` | Get customer by email |
| GET | `/customer/{customerId}` | Get customer by ID |
| PUT | `/customers/{customerId}` | Update customer |
| PUT | `/customers/{customerId}/change-password` | Change password |

## 🧪 API Testing Guide

### Using cURL

#### 1. Register a New Customer
```bash
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "phoneNumber": "1234567890",
    "address": "123 Main St, City, State",
    "dateOfBirth": "1990-01-01",
    "password": "securePassword123"
  }'
```

#### 2. Login Customer
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john.doe@example.com",
    "password": "securePassword123"
  }' \
  -c cookies.txt
```

#### 3. Refresh Token
```bash
curl -X POST http://localhost:8080/auth/refresh \
  -b cookies.txt
```

#### 4. Logout
```bash
curl -X POST http://localhost:8080/auth/logout \
  -b cookies.txt
```

### Using Postman

#### 1. Register Customer
- **Method**: POST
- **URL**: `http://localhost:8080/auth/register`
- **Headers**: `Content-Type: application/json`
- **Body** (raw JSON):
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "phoneNumber": "1234567890",
  "address": "123 Main St, City, State",
  "dateOfBirth": "1990-01-01",
  "password": "securePassword123"
}
```

#### 2. Login Customer
- **Method**: POST
- **URL**: `http://localhost:8080/auth/login`
- **Headers**: `Content-Type: application/json`
- **Body** (raw JSON):
```json
{
  "email": "john.doe@example.com",
  "password": "securePassword123"
}
```

## 🌐 Frontend Flow

### 1. Homepage (http://localhost:8085/)
- Welcome page with registration and login options
- Modern UI with gradient background
- Feature highlights

### 2. Registration Page (http://localhost:8085/auth/register-page)
- Customer registration form
- Password strength indicator
- Form validation
- Redirects to login after successful registration

### 3. Login Page (http://localhost:8085/auth/login-page)
- Customer login form
- Error handling
- Redirects to dashboard after successful login

### 4. Customer Dashboard (http://localhost:8085/customer/dashboard)
- Account information display
- Banking services (deposit, withdraw, transfer)
- KYC status and requirements
- Logout functionality

## 🔧 Configuration

### Auth Service Configuration (application.yml)
```yaml
server:
  port: 8081

spring:
  application:
    name: auth-service
  data:
    redis:
      host: localhost
      port: 6379

jwt:
  secret: your-secret-key-here-make-it-very-long-and-secure-for-production-use
  expiration: 86400000 # 24 hours
  refresh-expiration: 604800000 # 7 days

customer-service:
  url: http://localhost:8082
```

### API Gateway Configuration
```yaml
server:
  port: 8080

spring:
  application:
    name: api-gateway
  cloud:
    gateway:
      routes:
        - id: auth-service
          uri: lb://auth-service
          predicates:
            - Path=/auth/**
```

## 🔒 Security Features

1. **JWT Authentication**: Secure token-based authentication
2. **Password Encryption**: BCrypt password hashing
3. **HTTP-Only Cookies**: Secure cookie storage
4. **Token Refresh**: Automatic token renewal
5. **Token Blacklisting**: Secure logout mechanism
6. **CORS Configuration**: Cross-origin resource sharing
7. **Input Validation**: Request validation and sanitization

## 📁 Project Structure

```
├── auth-service/                 # Authentication microservice
│   ├── src/main/java/com/bank/auth/
│   │   ├── controller/          # REST controllers
│   │   ├── service/             # Business logic
│   │   ├── dto/                 # Data transfer objects
│   │   ├── config/              # Configuration classes
│   │   ├── proxy/               # Feign clients
│   │   └── util/                # Utility classes
│   └── src/main/resources/
│       └── application.yml
├── customer-service/             # Customer management service
├── apiGateway-service/           # API Gateway
├── FRONTEND-UI/                  # Web interface
└── eureka-server/                # Service discovery
```

## 🐛 Troubleshooting

### Common Issues

1. **Redis Connection Error**
   - Ensure Redis server is running
   - Check Redis port (default: 6379)
   - Verify Redis configuration in application.yml

2. **Database Connection Error**
   - Check Oracle database connection
   - Verify database credentials
   - Ensure database tables exist

3. **Service Discovery Issues**
   - Ensure Eureka server is running
   - Check service registration
   - Verify service names in configuration

4. **JWT Token Issues**
   - Check JWT secret configuration
   - Verify token expiration settings
   - Ensure proper cookie handling

### Logs and Debugging

Enable debug logging in application.yml:
```yaml
logging:
  level:
    com.bank.auth: DEBUG
    org.springframework.security: DEBUG
```

## 📝 Development Notes

### Key Features Implemented

1. ✅ Complete JWT authentication system
2. ✅ Customer registration with validation
3. ✅ Secure login with password verification
4. ✅ Token refresh mechanism
5. ✅ Logout with token invalidation
6. ✅ Redis-based token storage
7. ✅ Microservices architecture
8. ✅ API Gateway integration
9. ✅ Modern responsive UI
10. ✅ Swagger API documentation
11. ✅ Comprehensive error handling
12. ✅ Security best practices

### Future Enhancements

1. **Multi-factor Authentication (MFA)**
2. **Role-based Access Control (RBAC)**
3. **Audit Logging**
4. **Rate Limiting**
5. **API Versioning**
6. **Containerization (Docker)**
7. **CI/CD Pipeline**
8. **Monitoring and Metrics**

## 📞 Support

For issues and questions:
1. Check the troubleshooting section
2. Review application logs
3. Verify service configurations
4. Test individual endpoints using provided examples

## 📄 License

This project is licensed under the MIT License.