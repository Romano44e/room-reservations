# Room Reservations – Backend (REST API)

> REST API for an application managing conference room reservations.
> Backend application providing RESTful services for a Vaadin-based frontend.  
> Built with Spring Boot, integrated with MySQL database.

---

## 🧰 Stack

- **Language**: Java 21+
- **Framework**: Spring Boot
- **Database**: MySQL
- **Build Tool**: Gradle
- **Communication**: REST API (JSON)
- **Frontend**: [Vaadin Project](https://github.com/Romano44e/room-reservations-vaadin)

---

## 🚀 Getting Started

### ✅ Prerequisites

- Java 21+
- Gradle
- MySQL 8+

### 📦 Installation

```bash
# Clone the repository
git clone https://github.com/Romano44e/room-reservations.git
cd room-reservations

# Configure database
cp src/main/resources/application-example.properties src/main/resources/application.properties
# edit `application.properties` with your DB credentials


# Build & run
./gradlew bootRun
