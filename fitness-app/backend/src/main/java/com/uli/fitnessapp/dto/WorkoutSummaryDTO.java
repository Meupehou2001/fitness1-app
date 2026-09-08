package com.uli.fitnessapp.dto;

import java.time.LocalDate;

public record WorkoutSummaryDTO(Long id, LocalDate date, Integer dureeMin, int nombreExercices) {
}
