# Virtual Fruit Machine Backend

This is the backend for the Virtual Fruit Machine project. The backend is responsible for handling game logic and providing an API for the frontend to interact with.

## Table of Contents

- [Technologies](#technologies)
- [Setup](#setup)
- [Endpoints](#endpoints)
- [Testing](#testing)

## Technologies

The backend is built using:

- Java 11
- Spring Boot
- Spring Web
- Spring Test
- JUnit
- Mockito
- Maven

## Setup

To set up the project, follow these steps:

1. Ensure you have Java 11 and Maven installed on your system.
2. Clone the repository.
3. Open a terminal and navigate to the project directory.
4. Run `mvn clean install` to build the project and run tests.
5. Run `mvn spring-boot:run` to start the server.

The server will be running on `http://localhost:8080`.

## Endpoints

The backend exposes a single API endpoint:

- `/api/game/play` (POST)

The endpoint expects a JSON object in the request body containing the player's current money amount:

```json
{
  "money": 100
}
```
The response will be a JSON object containing the result of the game, including whether the player has won the jackpot, the remaining money, and the colors in the slots:

```json
{
  "jackpot": false,
  "remainingMoney": 99,
  "slots": ["red", "blue", "green"]
}
```

## Testing
Tests are written using JUnit, Mockito, and Spring Test. To run the tests, navigate to the project directory and run mvn test.

The tests cover various scenarios such as playing with sufficient and insufficient funds, validating the colors in the slots, and checking if the remaining money increases or decreases correctly.