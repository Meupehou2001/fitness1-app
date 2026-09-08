Fitness-App mit Statistiken
Ein einziges Backend (Spring Boot + MySQL), das von zwei Clients genutzt wird: React (Web) und JavaFX (Desktop).

1. MySQL-Datenbank (zuerst einrichten)
mysql -u root -p < database/schema.sql
Das erstellt die Datenbank fitness_app, ihre 4 Tabellen (users, exercises, workouts, workout_exercises), eine View v_workout_volume für die Statistiken sowie Beispieldaten (1 Benutzer, 5 Übungen, 2 Trainingseinheiten).

Prüfe anschließend, dass backend/src/main/resources/application.properties auf deine Datenbank zeigt (MySQL-Benutzername/Passwort).

2. Backend (Spring Boot)
cd backend
mvn spring-boot:run
Startet auf http://localhost:8080. Wichtigste Endpunkte:

GET /api/workouts/{userId} — Trainingseinheiten eines Benutzers
POST /api/workouts — neue Trainingseinheit anlegen
GET /api/stats/{userId}/progression?exerciseId=1 — Verlauf des max. Gewichts
GET /api/exercises — Übungskatalog
3a. Frontend React (Web)
cd frontend-react
npm install
npm run dev
3b. Frontend JavaFX (Desktop)
cd frontend-javafx
mvn javafx:run
Mögliche nächste Schritte
Authentifizierung (Spring Security + JWT) — aktuell ist USER_ID = 1 fest codiert
Eingabeformular für eine Trainingseinheit (React und JavaFX)
Weitere Statistiken: Gesamtvolumen pro Woche, persönliche Rekorde, Kalenderansicht
