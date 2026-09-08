package com.uli.fitnessapp.service;

import com.uli.fitnessapp.dto.ProgressionPointDTO;
import com.uli.fitnessapp.repository.WorkoutExerciseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatsService {

    private final WorkoutExerciseRepository workoutExerciseRepository;

    public StatsService(WorkoutExerciseRepository workoutExerciseRepository) {
        this.workoutExerciseRepository = workoutExerciseRepository;
    }

    // Verlauf des maximal gehobenen Gewichts für eine bestimmte Übung, Einheit für Einheit.
    // Basiert direkt auf der nach Datum gruppierten SQL/JPQL-Abfrage (siehe schema.sql: v_workout_volume
    // für das Äquivalent "Gesamtvolumen", falls später benötigt).
    public List<ProgressionPointDTO> getProgression(Long userId, Long exerciseId) {
        return workoutExerciseRepository.findProgression(userId, exerciseId).stream()
                .map(row -> new ProgressionPointDTO(row.getDate(), row.getPoidsMax()))
                .toList();
    }
}
