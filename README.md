# SpringbootHome

A **beginner‑friendly, production‑style Spring Boot project** that teaches how to build real backend applications using **clean architecture, REST APIs, authentication, and database integration** — without unnecessary complexity.

It is done based on a  **comprehensive Spring Boot backend** for managing supermarket operations including **Products, Categories, and Billing**. This project follows **clean architecture, REST API best practices, and production-ready design**.

Designed for learners who want to move from **Java theory → real industry projects**.

---

## ✨ What You’ll Learn

* How to structure a **professional Spring Boot backend**
* Building **RESTful APIs** with Spring Web
* Using **Spring Data JPA + Hibernate** for database operations
* Implementing **JWT authentication & security**
* Writing **clean, maintainable Java code**
* Handling **validation, errors, and pagination**
* Preparing a project for **real‑world deployment**

---

## 🧰 Tech Stack

| Layer            | Technology                     |
| ---------------- | ------------------------------ |
| Language         | **Java 17+**                   |
| Framework        | **Spring Boot**                |
| Web              | Spring Web (REST APIs)         |
| Database         | PostgreSQL / MySQL / H2        |
| ORM              | Spring Data JPA + Hibernate    |
| Security         | Spring Security + JWT          |
| Validation       | Jakarta Validation (`@Valid`)  |
| Build Tool       | Maven                          |
| API Docs         | Swagger / OpenAPI *(optional)* |
| Containerization | Docker *(optional)*            |

---

## 📁 Project Structure

```
src/main/java/com/example/project
│
├── controller      → REST endpoints
├── service         → Business logic
├── repository      → Database access
├── model/entity    → JPA entities
├── dto             → Request/response objects
├── config          → Security & app configuration
├── exception       → Global error handling
└── ProjectApplication.java
```

This structure reflects **industry‑standard clean architecture** used in real backend systems.

---

## 🧩 Features Implemented

### 🔹 Core CRUD

* Create, read, update, delete resources
* Pagination & search support
* Clean JSON responses

### 🔐 Authentication & Security (Phase 3)

This project implements a robust security layer answering the question: **"Who can access what?"**

*   **Day 13: Spring Security**: Protects all endpoints by default using a Filter Chain.
*   **Day 14: BCrypt Hashing**: Passwords are never stored as plain text. We use the BCrypt algorithm to safely hash passwords before they hit the database.
*   **Day 15: JWT (JSON Web Tokens)**: Stateless authentication.
    1.  **Login**: User sends credentials.
    2.  **Token**: Server validates and returns a signed JWT.
    3.  **Access**: Client sends token in `Authorization: Bearer <token>` header for every request.
*   **Day 16: Role-Based Authorization**:
    *   `ROLE_USER`: Can view products (`GET`).
    *   `ROLE_ADMIN`: Can perform sensitive operations (`POST`, `PUT`, `DELETE`).
*   **Day 17: Secure Error Responses**: Prevents information leakage by hiding stack traces and internal SQL details, returning clean JSON error bodies.

### ✅ Validation & Error Handling

* DTO validation using `@Valid`
* Global exception handling
* Standardized API error responses

### 🧪 Testing *(optional but recommended)*

* Basic unit or integration tests using **JUnit & SpringBootTest**

---

## ▶️ Running the Project Locally

### 1. Clone the repository

```bash
git clone https://github.com/IGITANGAZA23/SpringbootHome.git
cd SpringbootHome
```

### 2. Configure environment variables

Create an `.env` or update `application.properties`:

```
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/app_db
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=your_password
JWT_SECRET=your_secret_key
```

### 3. Run the application

```bash
./mvnw spring-boot:run
```

App will start at:

```
http://localhost:8080
```

---

## 📬 Example API Endpoints

| Method | Endpoint             | Description             | Auth? | Role         |
| ------ | -------------------- | ----------------------- | ----- | ------------ |
| POST   | `/api/auth/register` | Register new user       | ❌    | None         |
| POST   | `/api/auth/login`    | Authenticate user       | ❌    | None         |
| GET    | `/api/products`      | List all products       | ❌    | None         |
| POST   | `/api/products`      | Create new product      | ✅    | `ROLE_ADMIN` |
| GET    | `/api/products/{id}` | Get product by ID       | ❌    | None         |
| PUT    | `/api/products/{id}` | Update product          | ✅    | `ROLE_ADMIN` |
| DELETE | `/api/products/{id}` | Delete product          | ✅    | `ROLE_ADMIN` |
| GET    | `/api/resources`     | List resources (Legacy) | ✅    | `ROLE_USER`  |

> [!TIP]
> **Testing Admin Access**: By default, new registrations are assigned `ROLE_USER`. To test admin-only features, you can manually update a user's role in the database:
> ```sql
> INSERT INTO user_roles (user_id, roles) VALUES (1, 'ROLE_ADMIN');
> ```

---

## 🧭 Learning Path Using This Repo

1. Explore the **project structure**
2. Run the app locally
3. Test endpoints with **Postman or Swagger**
4. Modify a CRUD feature
5. Add a new entity or endpoint
6. Deploy your own version 🚀

---

## 🌍 Real‑World Inspiration

This starter is ideal for building apps like:

* Clinic / patient management system
* Task manager API
* Blog backend
* Student management system

---

## 🤝 Contributing

Contributions are welcome!

If you’d like to improve this learning project:

1. Fork the repo
2. Create a new branch
3. Make your changes
4. Submit a pull request

---

## 📜 License

This project is open‑source and available under the **MIT License**.

---

## 👨‍💻 Author

**Noble Prince**
Backend developer focused on building what matters

* GitHub: [https://github.com/IGITANGAZA23](https://github.com/IGITANGAZA23)
* Email: [igitangazanobleprince@gmail.com](mailto:igitangazanobleprince@gmail.com)

---

> ⭐ If this project helps you learn Spring Boot, consider giving it a star to support the mission of making **Java backend development simpler and cooler for everyone**.
