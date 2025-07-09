# Java CRUD Framework

A lightweight, reusable Java framework to simplify database CRUD operations using JDBC and environment configuration. Designed for beginners and developers who want to quickly build data-driven applications with models, queries, and future REST API support.

> ⚠️ **Note:** This project is currently **under development**. Features and APIs may change, and not all functionalities are fully implemented yet.

## Features

- Connect to MySQL (or other JDBC databases) using environment variables
- Insert, read (select), and check tables dynamically
- Use Java collections (`Stack<String>`) for flexible field/value input
- Easy to extend for updates, deletes, and more complex queries
- Planned REST API integration for web services

## Getting Started

### Prerequisites

- Java 11+
- MySQL database (or other JDBC-compatible DB)
- [MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/)
- [dotenv-java](https://github.com/cdimascio/dotenv-java) library

### Setup

1. Clone the repo:

   ```bash
   git clone https://github.com/yourusername/java-crud-framework.git
   cd java-crud-framework

    Create a .env file in the project root with your DB credentials:

    BD_CONNECTION=mysql
    DB_NAME=your_db_name
    DB_USER=your_username
    DB_HOST=localhost
    DB_PORT=3306
    DB_PASSWORD=your_password

    Add the required dependencies (mysql-connector-java and dotenv-java) to your classpath or build tool.

    Build and run your application.

Usage Example

DBConnection db = new DBConnection();
db.connect();

Stack<String> fields = new Stack<>();
fields.push("name");
fields.push("email");

Stack<String> values = new Stack<>();
values.push("Mehdi");
values.push("mehdi@example.com");

QueryBuilder qb = new QueryBuilder();
qb.insertOne("users", fields, values);

List<String> columns = db.getColumnNames("users");
System.out.println(columns);

Future Plans

    Add full CRUD methods: update, delete

    Implement model classes for tables with automatic mapping

    Build REST API endpoints on top of QueryBuilder using frameworks like Javalin or Spring Boot

    Add input validation and error handling

    Support multiple DB types and connection pooling

Contributing

Contributions are welcome! Feel free to open issues or submit pull requests.
License

MIT License

Made with ☕ by Mehdi Elbakouri