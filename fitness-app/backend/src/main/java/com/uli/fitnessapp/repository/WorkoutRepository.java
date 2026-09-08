package com.uli.fitnessapp.repository;

import com.uli.fitnessapp.entity.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    List<Workout> findByUserIdOrderByDateDesc(Long userId);
}
