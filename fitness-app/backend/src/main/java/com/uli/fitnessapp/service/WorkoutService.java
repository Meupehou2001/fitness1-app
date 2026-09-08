package com.uli.fitnessapp.service;

import com.uli.fitnessapp.dto.WorkoutSummaryDTO;
import com.uli.fitnessapp.entity.Workout;
import com.uli.fitnessapp.repository.WorkoutRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkoutService {

    private final WorkoutRepository workoutRepository;

    public WorkoutService(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    public List<WorkoutSummaryDTO> getWorkoutsForUser(Long userId) {
        return workoutRepository.findByUserIdOrderByDateDesc(userId).stream()
                .map(w -> new WorkoutSummaryDTO(
                        w.getId(),
                        w.getDate(),
                        w.getDureeMin(),
                        w.getExercises().size()))
                .toList();
    }

    public Workout save(Workout workout) {
        return workoutRepository.save(workout);
    }
}
