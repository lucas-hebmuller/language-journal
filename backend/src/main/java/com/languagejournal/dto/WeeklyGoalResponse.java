package com.languagejournal.dto;

import java.time.LocalDateTime;

public class WeeklyGoalResponse {

    private Long id;

    private Long languageId;

    private String languageName;

    private Integer targetMinutes;

    private LocalDateTime createdAt;

    public WeeklyGoalResponse() {}

    public WeeklyGoalResponse(Long id, Long languageId, String languageName, Integer targetMinutes, LocalDateTime createdAt) {
        this.id = id;
        this.languageId = languageId;
        this.languageName = languageName;
        this.targetMinutes = targetMinutes;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Long languageId) {
        this.languageId = languageId;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public Integer getTargetMinutes() {
        return targetMinutes;
    }

    public void setTargetMinutes(Integer targetMinutes) {
        this.targetMinutes = targetMinutes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
