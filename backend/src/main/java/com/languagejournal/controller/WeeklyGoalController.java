package com.languagejournal.controller;

import com.languagejournal.dto.WeeklyGoalRequest;
import com.languagejournal.dto.WeeklyGoalResponse;
import com.languagejournal.exception.ResourceNotFoundException;
import com.languagejournal.security.JwtUtil;
import com.languagejournal.service.UserService;
import com.languagejournal.service.WeeklyGoalService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/goals")
public class WeeklyGoalController {

    private final JwtUtil jwtUtil;
    private final WeeklyGoalService weeklyGoalService;
    private final UserService userService;

    public WeeklyGoalController(JwtUtil jwtUtil, WeeklyGoalService weeklyGoalService, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.weeklyGoalService = weeklyGoalService;
        this.userService = userService;
    }

    private UUID extractUserId(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        String email = jwtUtil.extractEmail(token);
        return userService.getUserByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"))
                .getId();
    }

    @GetMapping
    public ResponseEntity<List<WeeklyGoalResponse>> getWeeklyGoals(HttpServletRequest request) {
        UUID userId = extractUserId(request);
        List<WeeklyGoalResponse> weeklyGoals = weeklyGoalService.getWeeklyGoalsByUser(userId);
        return ResponseEntity.ok(weeklyGoals);
    }

    @PostMapping
    public ResponseEntity<WeeklyGoalResponse> upsertWeeklyGoal(
            HttpServletRequest request,
            @Valid @RequestBody WeeklyGoalRequest weeklyGoalRequest) {
        UUID userId = extractUserId(request);
        WeeklyGoalResponse weeklyGoalUpserted =  weeklyGoalService.upsertWeeklyGoal(userId, weeklyGoalRequest);
        return ResponseEntity.ok(weeklyGoalUpserted);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWeeklyGoal(
            HttpServletRequest request,
            @PathVariable("id") Long weeklyGoalId) {
        UUID userId = extractUserId(request);
        weeklyGoalService.deleteWeeklyGoal(userId, weeklyGoalId);
        return ResponseEntity.noContent().build();
    }
}
