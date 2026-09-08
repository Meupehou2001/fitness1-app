# 🏋️ Fitness-App mit Statistiken

Eine vollständige Trainings-Tracking-Anwendung, mit der Nutzer ihre
Trainingseinheiten erfassen und ihren Fortschritt über die Zeit auswerten
können. Das Projekt zeigt eine saubere Trennung zwischen Backend und
Frontend: ein einziges Spring-Boot-Backend stellt eine REST-API bereit,
die von zwei unabhängigen Clients konsumiert wird — einer Web-Oberfläche
(React) und einer Desktop-Anwendung (JavaFX).

---

## Inhaltsverzeichnis

- [Funktionsumfang](#funktionsumfang)
- [Architektur](#architektur)
- [Tech-Stack](#tech-stack)
- [Projektstruktur](#projektstruktur)
- [Datenmodell](#datenmodell)
- [Voraussetzungen](#voraussetzungen)
- [Installation & Start](#installation--start)
  - [1. MySQL-Datenbank](#1-mysql-datenbank)
  - [2. Backend (Spring Boot)](#2-backend-spring-boot)
  - [3a. Frontend React (Web)](#3a-frontend-react-web)
  - [3b. Frontend JavaFX (Desktop)](#3b-frontend-javafx-desktop)
- [API-Endpunkte](#api-endpunkte)
- [Konfiguration](#konfiguration)
- [Roadmap](#roadmap)

---

## Funktionsumfang

- 📅 Erfassen von Trainingseinheiten mit Datum, Dauer und Notizen
- 🏋️ Zuordnung mehrerer Übungen pro Einheit (Sätze, Wiederholungen, Gewicht)
- 📈 Verlaufsstatistik des maximal gehobenen Gewichts pro Übung, als Diagramm
- 🗂️ Übungskatalog, kategorisiert nach Muskelgruppe (Beine, Brust, Rücken, Cardio)
- 💻🌐 Zwei vollständig funktionsfähige Clients (Web und Desktop) auf derselben API

## Architektur

Ein zentrales Backend, zwei Clients — beide sprechen dieselbe REST-API an:

Diese Trennung erlaubt es, Backend und Frontends unabhängig voneinander
zu entwickeln, zu testen und zu deployen.

## Tech-Stack

| Bereich          | Technologie                                  |
|------------------|-----------------------------------------------|
| Backend          | Java 17, Spring Boot 3, Spring Data JPA       |
| Datenbank        | MySQL 8                                       |
| Web-Frontend     | React 18, Vite, Recharts                      |
| Desktop-Frontend | JavaFX 21, Jackson (JSON)                     |
| Build-Tools      | Maven (Backend & Desktop), npm (Web)          |

## Projektstruktur

## Datenmodell

| Tabelle             | Zweck                                                          |
|---------------------|------------------------------------------------------------------|
| `users`              | Benutzerkonten                                                   |
| `exercises`          | Übungskatalog (Name, Kategorie), gemeinsam für alle Nutzer       |
| `workouts`           | Eine Trainingseinheit (Datum, Dauer, Notizen)                    |
| `workout_exercises`  | Details je Einheit: Sätze, Wiederholungen, Gewicht pro Übung     |

Die Tabelle `workout_exercises` ist die Grundlage aller Statistiken; die
View `v_workout_volume` berechnet daraus das Gesamttrainingsvolumen
(Gewicht × Sätze × Wiederholungen) pro Einheit.

## Voraussetzungen

- Java 17+
- Maven 3.9+
- MySQL 8+
- Node.js 18+ und npm (für den React-Client)
- Für JavaFX: keine zusätzliche Installation nötig — die Abhängigkeiten
  werden über Maven aufgelöst

## Installation & Start

### 1. MySQL-Datenbank

```bash
mysql -u root -p < database/schema.sql
```

Erstellt die Datenbank `fitness_app` mit ihren 4 Tabellen, der View
`v_workout_volume` und Beispieldaten (1 Benutzer, 5 Übungen,
2 Trainingseinheiten).

### 2. Backend (Spring Boot)

```bash
cd backend
mvn spring-boot:run
```

Läuft auf `http://localhost:8080`. Vor dem Start `application.properties`
prüfen (siehe [Konfiguration](#konfiguration)).

### 3a. Frontend React (Web)

```bash
cd frontend-react
npm install
npm run dev
```

Läuft standardmäßig auf `http://localhost:5173`.

### 3b. Frontend JavaFX (Desktop)

```bash
cd frontend-javafx
mvn javafx:run
```

## API-Endpunkte

| Methode | Endpunkt                                          | Beschreibung                          |
|---------|----------------------------------------------------|----------------------------------------|
| GET     | `/api/workouts/{userId}`                           | Trainingseinheiten eines Benutzers      |
| POST    | `/api/workouts`                                     | Neue Trainingseinheit anlegen           |
| GET     | `/api/stats/{userId}/progression?exerciseId={id}`  | Verlauf des max. Gewichts für eine Übung |
| GET     | `/api/exercises`                                    | Übungskatalog abrufen                   |

## Konfiguration

Datenbankzugang in `backend/src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/fitness_app?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=change_me
```

CORS für den React-Client ist in `backend/.../config/WebConfig.java`
konfiguriert (erlaubt `localhost:5173` und `localhost:3000`).

## Roadmap

- [ ] Authentifizierung (Spring Security + JWT) — aktuell ist `USER_ID = 1` fest codiert
- [ ] Eingabeformular für neue Trainingseinheiten (React und JavaFX)
- [ ] Weitere Statistiken: Gesamtvolumen pro Woche, persönliche Rekorde
- [ ] Kalenderansicht der Trainingseinheiten
- [ ] Unit- und Integrationstests (JUnit, Testcontainers)

---

**Autor:** Uli — Medieninformatik, Hochschule Osnabrück
