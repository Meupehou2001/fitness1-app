package com.uli.fitnessapp.repository;

import com.uli.fitnessapp.entity.WorkoutExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface WorkoutExerciseRepository extends JpaRepository<WorkoutExercise, Long> {

    // Verlauf des maximal gehobenen Gewichts, Einheit für Einheit, für eine bestimmte Übung.
    // Wird von /api/stats/{userId}/progression genutzt
    @Query("""
        SELECT we.workout.date AS date, MAX(we.poidsKg) AS poidsMax
        FROM WorkoutExercise we
        WHERE we.workout.user.id = :userId
          AND we.exercise.id = :exerciseId
        GROUP BY we.workout.date
        ORDER BY we.workout.date ASC
        """)
    List<ProgressionRow> findProgression(@Param("userId") Long userId,
                                          @Param("exerciseId") Long exerciseId);

    interface ProgressionRow {
        LocalDate getDate();
        java.math.BigDecimal getPoidsMax();
    }
}
