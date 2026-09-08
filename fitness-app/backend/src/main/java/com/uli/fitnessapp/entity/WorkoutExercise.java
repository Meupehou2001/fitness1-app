package com.uli.fitnessapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Table(name = "workout_exercises")
@Getter
@Setter
public class WorkoutExercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_id", nullable = false)
    private Workout workout;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    @Column(nullable = false)
    private Integer series;

    @Column(nullable = false)
    private Integer reps;

    @Column(name = "poids_kg", precision = 6, scale = 2)
    private BigDecimal poidsKg;

    @Column
    private Integer ordre = 1;
}
