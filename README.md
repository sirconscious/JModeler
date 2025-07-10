# Java CRUD Framework

A lightweight, reusable Java framework to simplify database CRUD operations using JDBC and environment configuration. Designed for beginners and developers who want to quickly build data-driven applications with models, queries, and future REST API support.

> ⚠️ **Note:** This project is currently **under development**. Features and APIs may change, and not all functionalities are fully implemented yet.

---

## ✨ Features

- ✅ Easy connection to MySQL (or other JDBC-compatible databases) via `.env` configuration
- ✅ Perform `insert`, `read`, `update`, and `delete` operations using `com.mehdi.crud.QueryBuilder`
- ✅ Chain queries fluently using `com.mehdi.crud.FluentQuery` (e.g. `.table("users").insertOne(...).execute()`)
- ✅ Supports both single and bulk inserts
- ✅ Dynamically fetch column names and check table existence
- ✅ Uses Java collections like `List<String>` and `List<List<String>>` for input flexibility
- 🔄 Ready for extension into model mapping, REST API support, and query filters

---

## ✅ Getting Started

### 🧰 Prerequisites

- Java 11+
- MySQL database (or other JDBC-compatible DB)
- [MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/)
- [dotenv-java](https://github.com/cdimascio/dotenv-java) for environment config

---

### ⚙️ Setup

1. **Clone the repo**

   ```bash
   git clone https://github.com/yourusername/java-crud-framework.git
   cd java-crud-framework

    Create a .env file in the project root:

    DB_CONNECTION=mysql
    DB_NAME=your_db_name
    DB_USER=your_username
    DB_HOST=localhost
    DB_PORT=3306
    DB_PASSWORD=your_password

    Add dependencies (manually or with Maven/Gradle):

        mysql-connector-java

        dotenv-java

    Compile and run your application with your preferred Java IDE or CLI.

💡 Usage Example
🧱 Basic DB Connection

com.mehdi.crud.DBConnection db = new com.mehdi.crud.DBConnection();
db.connect();

📦 Using com.mehdi.crud.QueryBuilder (classic)

com.mehdi.crud.QueryBuilder qb = new com.mehdi.crud.QueryBuilder();

List<String> fields = List.of("name", "email");
List<String> values = List.of("Mehdi", "mehdi@example.com");

qb.insertOne("users", fields, values);

🚀 Using com.mehdi.crud.FluentQuery (recommended)

com.mehdi.crud.FluentQuery fq = new com.mehdi.crud.FluentQuery();

// Insert one user
fq.table("users")
.insertOne(List.of("name", "email"), List.of("Mehdi", "mehdi@example.com"))
.execute();

// Read one user
List<String> user = fq.table("users")
.readOne()
.execute()
.getResult();

System.out.println(user);

// Update a user
fq.table("users")
.update("id", "=", "1", List.of("email"), List.of("new@example.com"))
.execute();

// Delete a user
fq.table("users")
.delete("id", "=", "2")
.execute();

🚧 Future Plans

Add full CRUD methods (done ✅)

Implement model classes with automatic table-column mapping

Build REST API endpoints using Javalin or Spring Boot

Add input validation and custom error handling

Support multiple DB types and connection pooling (e.g., HikariCP)

    Implement filters (where, orderBy, limit) in com.mehdi.crud.FluentQuery

🤝 Contributing

Contributions are welcome! Feel free to open issues or submit pull requests.
📝 License

This project is licensed under the MIT License.
👤 Author

Made with ☕ by Mehdi El bakouri