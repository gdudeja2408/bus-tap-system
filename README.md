# Bus Tap System

This project processes tap-on and tap-off events from passengers and generates corresponding trip records with calculated charges based on the journey.

---

## Assumptions Made

- The input file `taps.csv` is well-formed, with no missing or malformed fields.
- Tap events are ordered chronologically based on `DateTimeUTC`.
- Each passenger (identified by PAN) can only have one active tap-on at a time.
- Trip statuses are defined as:
  - COMPLETED: Tap-on and tap-off occur at different stops.
  - CANCELLED: Tap-on and tap-off occur at the same stop.
  - INCOMPLETE: Tap-on without a corresponding tap-off.
- Fares are symmetric between stops, meaning traveling from Stop1 to Stop2 costs the same as Stop2 to Stop1.

---

## Instructions to Run the Application

### Prerequisites
- Java 17+
- Gradle installed (or use the Gradle wrapper `./gradlew`)

- ### Steps

1. Clone the repository:
   ```bash
   git clone https://github.com/gdudeja2408/bus-tap-system.git
   cd bus-tap-system

## Test Harness to Validate the Solution

- A basic JUnit 5 test class MainTest.java is provided under:
**src/test/java/com/tapsystem/MainTest.java**
The test validates:
-That tap data is processed correctly into trips.
-That trips are generated and classified properly (e.g., COMPLETED).

## OUTPUT and INPUT files
The application will:
Read from **src/main/resources/taps.csv**
Generate **src/main/resources/trips.csv** in the project.

