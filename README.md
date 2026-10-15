# ProjectFlow — Project Management Tool

A Java web-based project and task manager built with Java 17, Jakarta Servlets, JSP, JDBC, MySQL, HTML, and CSS.

## Features
- User registration and login with BCrypt password hashing
- Session-based authentication for application pages
- Create and delete projects
- Create tasks, set priority and due date
- Update task status (Pending, In Progress, Completed)
- Per-user project/task visibility
- Responsive dashboard
- Prepared statements for SQL operations

## Requirements
- JDK 17+
- Apache Maven 3.8+
- MySQL 8+
- Apache Tomcat 10.1 (Jakarta Servlet 6)
- VS Code (recommended)

## Database setup
1. Open MySQL Workbench or a MySQL terminal.
2. Run `database/schema.sql`.
3. Set environment variables before starting Tomcat:
   - `DB_USER` (defaults to `root`)
   - `DB_PASSWORD` (your MySQL password; do not commit it)
   - Optional `DB_URL` (defaults to local `project_management` database URL)

PowerShell example for the current terminal:
```powershell
$env:DB_USER="root"
$env:DB_PASSWORD="your_mysql_password"
```

## Build and run locally
1. Ensure MySQL is running and the schema has been created.
2. In the project root, run:
   ```powershell
   mvn clean package
   ```
3. Copy `target/ProjectManagementTool.war` to Tomcat 10.1's `webapps` directory.
4. Start Tomcat.
5. Open `http://localhost:8080/ProjectManagementTool/`.
6. Register a user and log in.

## Important notes
- This is a learning/demo project, not yet a production-hardened service. Before public deployment, add CSRF protection, secure cookie settings, rate limiting, stronger validation, and managed secret configuration.
- The example assumes MySQL's `root` user is configured for the local environment. Use a dedicated database user for anything beyond local development.
- The online landing page's sample metrics are decorative; the authenticated dashboard displays actual database data.
- Do not upload database passwords or production secrets to GitHub.
