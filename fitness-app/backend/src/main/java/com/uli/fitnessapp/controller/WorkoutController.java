package com.uli.fitnessapp.controller;

import com.uli.fitnessapp.dto.WorkoutSummaryDTO;
import com.uli.fitnessapp.entity.Workout;
import com.uli.fitnessapp.service.WorkoutService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workouts")
public class WorkoutController {

    private final WorkoutService workoutService;

    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @GetMapping("/{userId}")
    public List<WorkoutSummaryDTO> getWorkouts(@PathVariable Long userId) {
        return workoutService.getWorkoutsForUser(userId);
    }

    @PostMapping
    public ResponseEntity<Workout> createWorkout(@RequestBody Workout workout) {
        return ResponseEntity.ok(workoutService.save(workout));
    }
}
