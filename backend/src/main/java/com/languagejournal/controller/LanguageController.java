package com.languagejournal.controller;

import com.languagejournal.dto.LanguageRequest;
import com.languagejournal.dto.LanguageResponse;
import com.languagejournal.security.JwtUtil;
import com.languagejournal.service.LanguageService;
import com.languagejournal.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/languages")
public class LanguageController {

    private final JwtUtil jwtUtil;
    private final LanguageService languageService;
    private final UserService userService;

    public LanguageController(JwtUtil jwtUtil, LanguageService languageService,
                              UserService userService) {
        this.jwtUtil = jwtUtil;
        this.languageService = languageService;
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
    public ResponseEntity<List<LanguageResponse>> getLanguages(HttpServletRequest request) {
        UUID userId = extractUserId(request);
        List<LanguageResponse> languages = languageService.getLanguagesByUser(userId);
        return ResponseEntity.ok(languages);
    }

    @PostMapping
    public ResponseEntity<LanguageResponse> createLanguage(
            HttpServletRequest request,
            @Valid @RequestBody LanguageRequest languageRequest) {
        UUID userId = extractUserId(request);
        LanguageResponse createdLanguage = languageService.createLanguage(userId, languageRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdLanguage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LanguageResponse> updateLanguage(
            HttpServletRequest request,
            @PathVariable("id") Long languageId,
            @Valid @RequestBody LanguageRequest languageRequest) {
        UUID userId = extractUserId(request);
        LanguageResponse updatedLanguage = languageService.updateLanguage(userId, languageId, languageRequest);
        return ResponseEntity.ok(updatedLanguage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLanguage(
            HttpServletRequest request,
            @PathVariable("id") Long languageId) {
        UUID userId = extractUserId(request);
        languageService.deleteLanguage(userId, languageId);
        return ResponseEntity.noContent().build();
    }
}
