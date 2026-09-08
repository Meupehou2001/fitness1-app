const BASE_URL = "http://localhost:8080/api";

export async function getWorkouts(userId) {
  const res = await fetch(`${BASE_URL}/workouts/${userId}`);
  if (!res.ok) throw new Error("Fehler beim Laden der Trainingseinheiten");
  return res.json();
}

export async function getProgression(userId, exerciseId) {
  const res = await fetch(`${BASE_URL}/stats/${userId}/progression?exerciseId=${exerciseId}`);
  if (!res.ok) throw new Error("Fehler beim Laden der Statistiken");
  return res.json();
}

export async function getExercises() {
  const res = await fetch(`${BASE_URL}/exercises`);
  if (!res.ok) throw new Error("Fehler beim Laden der Übungen");
  return res.json();
}
