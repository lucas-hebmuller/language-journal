package com.languagejournal.controller;

import com.languagejournal.dto.StudySessionRequest;
import com.languagejournal.dto.StudySessionResponse;
import com.languagejournal.security.JwtUtil;
import com.languagejournal.service.StudySessionService;
import com.languagejournal.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/sessions")
public class StudySessionController {

    private final JwtUtil jwtUtil;
    private final StudySessionService studySessionService;
    private final UserService userService;

    public StudySessionController(JwtUtil jwtUtil, StudySessionService studySessionService, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.studySessionService = studySessionService;
        this.userService = userService;
    }

    private UUID extractUserId(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        String email = jwtUtil.extractEmail(token);
        return userService.getUserByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"))
                .getId();
    }

    @GetMapping
    public ResponseEntity<List<StudySessionResponse>> getSessions(HttpServletRequest request) {
        UUID userId = extractUserId(request);
        List<StudySessionResponse> sessions = studySessionService.getSessionByUser(userId);
        return ResponseEntity.ok(sessions);
    }

    @PostMapping
    public ResponseEntity<StudySessionResponse> createSession(
            HttpServletRequest request,
            @Valid @RequestBody StudySessionRequest studySessionRequest) {
        UUID userId = extractUserId(request);
        StudySessionResponse createdSession = studySessionService.createSession(userId, studySessionRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSession);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudySessionResponse> updateSession (
            HttpServletRequest request,
            @PathVariable("id") Long sessionId,
            @Valid @RequestBody StudySessionRequest studySessionRequest) {
        UUID userId = extractUserId(request);
        StudySessionResponse updatedSession = studySessionService.updateSession(userId, sessionId, studySessionRequest);
        return ResponseEntity.ok(updatedSession);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSession (
            HttpServletRequest request,
            @PathVariable("id") Long sessionId) {
        UUID userId = extractUserId(request);
        studySessionService.deleteSession(userId, sessionId);
        return ResponseEntity.noContent().build();
    }

}
