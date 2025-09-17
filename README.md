# 📊 Expense Tracker API

A backend RESTful API built with **Spring Boot**, **PostgreSQL**, and **MongoDB** that allows users to register, log in, and track their expenses and incomes.  
This project demonstrates best practices in **Spring Security with JWT**, **Inversion of Control (IoC)**, **unit testing**, and multi-database integration.

---

## 🚀 Features

- **User Authentication**
  - Register new users (`/api/auth/register`)
  - Login with JWT (`/api/auth/login`)
- **Transactions Management**
  - Create income and expense transactions (`POST /api/transactions`)
  - List all transactions of the authenticated user (`GET /api/transactions`)
  - View a financial summary (total income, total expenses, balance, by category) (`GET /api/transactions/summary`)
- **Security**
  - JWT-based authentication
  - Password hashing with Spring Security’s `PasswordEncoder`
- **Databases**
  - PostgreSQL → users (relational data)
  - MongoDB → transactions (document-based data)
- **Testing**
  - Unit tests with **JUnit 5** and **Mockito**
  - Repository tests with **@DataJpaTest** and **@DataMongoTest**
  - Controller tests with **MockMvc**

---

## 🛠️ Tech Stack

- **Java 17+**
- **Spring Boot 3+**
- **Spring Security 6**
- **JWT (jjwt)**
- **PostgreSQL** (users)
- **MongoDB** (transactions)
- **Maven**
- **JUnit 5 + Mockito**

---

## ⚙️ Setup & Installation

### 1. Clone the repository
```bash
git clone https://github.com/yourusername/expense-tracker-api.git
cd expense-tracker-api
```

### 2. Configure databases

Update your `application.properties`:

```properties
# PostgreSQL (Users)
spring.datasource.url=jdbc:postgresql://localhost:5432/expense_db
spring.datasource.username=postgres
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update

# MongoDB (Transactions)
spring.data.mongodb.uri=mongodb://localhost:27017/expense_tracker
```

### 3. Run the application
```
mvn spring-boot:run
```

The API will be available at:
👉 http://localhost:8080

🔑 Authentication Flow
Register a user
POST /api/auth/register
Content-Type: application/json

```JSON
{
  "username": "johndoe",
  "email": "john@example.com",
  "password": "password123"
}
```

POST /api/auth/login
Content-Type: application/json

```JSON
{
  "username": "johndoe",
  "password": "password123"
}
```

Response

```JSON
{
  "jwt": "your.jwt.token"
}
```

## 📌 Example Transactions

```json
{
  "type": "EXPENSE",
  "category": "Food",
  "amount": 45.50,
  "date": "2025-09-16"
}
```

```json
{
  "type": "INCOME",
  "category": "Salary",
  "amount": 1500.00,
  "date": "2025-09-01"
}
```

## 🧪 Running Tests

Unit tests are included for services, repositories, and controllers.

Run:
```bash
mvn test
```

You’ll see output from **JUnit + MockMvc** validating functionality like:

- User registration & login  
- Saving and retrieving transactions  
- Repository queries (`findByUsername`, `findByUserId`)  
- JWT utilities  

---

## 📖 Principles Applied

- **Inversion of Control (IoC)** → Services and repositories are injected via Spring, making it easy to swap databases or implementations.  
- **Separation of Concerns** → Controllers handle HTTP, services handle business logic, repositories handle persistence.  
- **Security Best Practices** → Password hashing, JWT authentication, stateless sessions.  
- **Testing** → Unit tests at multiple levels to ensure maintainability.  

---

## 👨‍💻 Author

**Javier Alvarado**  
📧 alv21188@uvg.edu.gt
