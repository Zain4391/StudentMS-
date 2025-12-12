
# StudentMS

StudentMS is a simple Student Management System built with Java and Spring Boot. It provides core models and services for managing students, teachers, courses, and enrollments intended as an educational/sample project.

## Features

- Manage students, teachers, courses, and enrollments (models under `src/main/java/com/StudentMS/StudentMS/models`).
- Service interfaces and implementations for business logic (see `services/interfaces`).
- DTOs, mappers, and custom exceptions to support clean layering.

## Tech Stack

- Java 17+
- Spring Boot
- Maven (included `mvnw` wrapper)

## Requirements

- JDK 17 or newer
- Maven or use the included `mvnw` wrapper

## Run locally

From the repository root on Windows (PowerShell or CMD):

```powershell
./mvnw spring-boot:run
```

Or with a local Maven installation:

```powershell
mvn spring-boot:run
```

The application entry point is `src/main/java/com/StudentMS/StudentMS/StudentMsApplication.java`.

## Build

```powershell
./mvnw clean package
```

## Tests

Run unit tests with:

```powershell
./mvnw test
```

## Project Structure (important paths)

- `src/main/java/com/StudentMS/StudentMS` — main application package
- `src/main/java/com/StudentMS/StudentMS/controllers` — web controllers
- `src/main/java/com/StudentMS/StudentMS/models` — domain models (`Student`, `Teacher`, `Course`, `Enrollment`)
- `src/main/java/com/StudentMS/StudentMS/services` — business logic and interfaces
- `src/main/resources` — application properties, templates, static assets

## Configuration

See `src/main/resources/application.properties` for configuration used at runtime. Adjust database or server settings there if needed.

## Contributing

Contributions are welcome. Suggested workflow:

1. Create a branch `feat/your-feature` or `fix/issue`.
2. Add tests and run `./mvnw test`.
3. Open a pull request describing your changes.

## Next steps / Suggestions

- Add API documentation (Swagger/OpenAPI) for controllers.
- Add persistent storage configuration for a real database.

## License

This project is licensed under the MIT License — see the [LICENSE](LICENSE) file.

