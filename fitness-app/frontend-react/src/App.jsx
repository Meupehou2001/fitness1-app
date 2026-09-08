import { useEffect, useState } from "react";
import { getWorkouts, getProgression, getExercises } from "./api";
import ProgressionChart from "./ProgressionChart";

const USER_ID = 1; // durch den eingeloggten Benutzer ersetzen, sobald Auth hinzugefügt ist

export default function App() {
  const [workouts, setWorkouts] = useState([]);
  const [exercises, setExercises] = useState([]);
  const [selectedExercise, setSelectedExercise] = useState(null);
  const [progression, setProgression] = useState([]);

  useEffect(() => {
    getWorkouts(USER_ID).then(setWorkouts).catch(console.error);
    getExercises().then((ex) => {
      setExercises(ex);
      if (ex.length > 0) setSelectedExercise(ex[0].id);
    }).catch(console.error);
  }, []);

  useEffect(() => {
    if (selectedExercise) {
      getProgression(USER_ID, selectedExercise).then(setProgression).catch(console.error);
    }
  }, [selectedExercise]);

  return (
    <div style={{ maxWidth: 800, margin: "2rem auto", fontFamily: "sans-serif" }}>
      <h1>Mein Fitness-Tracking</h1>

      <section>
        <h2>Letzte Trainingseinheiten</h2>
        <ul>
          {workouts.map((w) => (
            <li key={w.id}>
              {w.date} — {w.dureeMin} Min. — {w.nombreExercices} Übung(en)
            </li>
          ))}
        </ul>
      </section>

      <section>
        <h2>Fortschritt</h2>
        <select
          value={selectedExercise ?? ""}
          onChange={(e) => setSelectedExercise(Number(e.target.value))}
        >
          {exercises.map((ex) => (
            <option key={ex.id} value={ex.id}>{ex.nom}</option>
          ))}
        </select>
        <ProgressionChart data={progression} />
      </section>
    </div>
  );
}
