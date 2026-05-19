package com.languagejournal.controller;

import com.languagejournal.dto.DashboardSummaryResponse;
import com.languagejournal.dto.WeeklyProgressResponse;
import com.languagejournal.exception.ResourceNotFoundException;
import com.languagejournal.security.JwtUtil;
import com.languagejournal.service.DashboardService;
import com.languagejournal.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final JwtUtil jwtUtil;
    private final DashboardService dashboardService;
    private final UserService userService;

    public DashboardController(JwtUtil jwtUtil, DashboardService dashboardService,
                               UserService userService) {
        this.jwtUtil = jwtUtil;
        this.dashboardService = dashboardService;
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
    public ResponseEntity<DashboardSummaryResponse> getSummary(HttpServletRequest request) {
        UUID userId = extractUserId(request);
        DashboardSummaryResponse dashboardSummary = dashboardService.getSummary(userId);
        return ResponseEntity.ok(dashboardSummary);
    }

    @GetMapping("/weekly-progress")
    public ResponseEntity<List<WeeklyProgressResponse>> getWeeklyProgress(HttpServletRequest request) {
        UUID userId = extractUserId(request);
        List<WeeklyProgressResponse> weeklyProgress = dashboardService.getWeeklyProgress(userId);
        return ResponseEntity.ok(weeklyProgress);
    }
}
