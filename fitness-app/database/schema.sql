-- ============================================================
-- Fitness-App mit Statistiken
-- MySQL-Schema
-- ============================================================

CREATE DATABASE IF NOT EXISTS fitness_app
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE fitness_app;

-- ------------------------------------------------------------
-- Tabelle: users
-- ------------------------------------------------------------
CREATE TABLE users (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    username        VARCHAR(50)  NOT NULL UNIQUE,
    email           VARCHAR(120) NOT NULL UNIQUE,
    password_hash   VARCHAR(255) NOT NULL,
    date_naissance  DATE,
    date_creation   TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- ------------------------------------------------------------
-- Tabelle: exercises (Übungskatalog, gemeinsam für alle User)
-- ------------------------------------------------------------
CREATE TABLE exercises (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom         VARCHAR(100) NOT NULL,
    categorie   VARCHAR(50)  NOT NULL,   -- z.B.: Beine, Brust, Ruecken, Cardio
    description TEXT
) ENGINE=InnoDB;

-- ------------------------------------------------------------
-- Tabelle: workouts (eine Trainingseinheit)
-- ------------------------------------------------------------
CREATE TABLE workouts (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id     BIGINT NOT NULL,
    date        DATE NOT NULL,
    duree_min   INT,
    notes       TEXT,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- ------------------------------------------------------------
-- Tabelle: workout_exercises (Details: Saetze/Wiederholungen/Gewicht pro Uebung)
-- Diese Tabelle liefert die Grundlage fuer alle Statistiken
-- ------------------------------------------------------------
CREATE TABLE workout_exercises (
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    workout_id   BIGINT NOT NULL,
    exercise_id  BIGINT NOT NULL,
    series       INT NOT NULL,
    reps         INT NOT NULL,
    poids_kg     DECIMAL(6,2),
    ordre        INT DEFAULT 1,
    FOREIGN KEY (workout_id)  REFERENCES workouts(id)  ON DELETE CASCADE,
    FOREIGN KEY (exercise_id) REFERENCES exercises(id) ON DELETE RESTRICT
) ENGINE=InnoDB;

-- ------------------------------------------------------------
-- Nuetzliche Indizes fuer die Statistiken (Verlauf ueber die Zeit)
-- ------------------------------------------------------------
CREATE INDEX idx_workouts_user_date ON workouts(user_id, date);
CREATE INDEX idx_we_exercise ON workout_exercises(exercise_id);

-- ------------------------------------------------------------
-- View: Gesamtvolumen pro Trainingseinheit (Gewicht x Saetze x Wiederholungen)
-- Wird direkt vom Statistik-Endpoint genutzt
-- ------------------------------------------------------------
CREATE VIEW v_workout_volume AS
SELECT
    w.id AS workout_id,
    w.user_id,
    w.date,
    SUM(we.series * we.reps * COALESCE(we.poids_kg, 0)) AS volume_total
FROM workouts w
JOIN workout_exercises we ON we.workout_id = w.id
GROUP BY w.id, w.user_id, w.date;

-- ------------------------------------------------------------
-- Beispieldaten
-- ------------------------------------------------------------
INSERT INTO exercises (nom, categorie) VALUES
    ('Kniebeuge', 'Beine'),
    ('Bankdruecken', 'Brust'),
    ('Klimmzuege', 'Ruecken'),
    ('Liegestuetze', 'Brust'),
    ('Laufen', 'Cardio');

INSERT INTO users (username, email, password_hash, date_naissance) VALUES
    ('uli', 'uli@mail.com', '$2a$10$beispielHashBcrypt', '1998-03-12');

INSERT INTO workouts (user_id, date, duree_min, notes) VALUES
    (1, '2026-09-01', 45, 'Beintraining'),
    (1, '2026-09-05', 50, 'Oberkoerpertraining');

INSERT INTO workout_exercises (workout_id, exercise_id, series, reps, poids_kg, ordre) VALUES
    (1, 1, 4, 10, 60.0, 1),
    (2, 2, 4, 8, 50.0, 1),
    (2, 3, 3, 10, NULL, 2);
