package com.uli.fitnessapp.controller;

import com.uli.fitnessapp.entity.Exercise;
import com.uli.fitnessapp.repository.ExerciseRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercises")
public class ExerciseController {

    private final ExerciseRepository exerciseRepository;

    public ExerciseController(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    @GetMapping
    public List<Exercise> getAll() {
        return exerciseRepository.findAll();
    }
}
