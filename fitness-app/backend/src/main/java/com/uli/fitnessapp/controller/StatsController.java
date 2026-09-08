package com.uli.fitnessapp.controller;

import com.uli.fitnessapp.dto.ProgressionPointDTO;
import com.uli.fitnessapp.service.StatsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stats")
public class StatsController {

    private final StatsService statsService;

    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    // GET /api/stats/1/progression?exerciseId=1
    @GetMapping("/{userId}/progression")
    public List<ProgressionPointDTO> getProgression(@PathVariable Long userId,
                                                      @RequestParam Long exerciseId) {
        return statsService.getProgression(userId, exerciseId);
    }
}
