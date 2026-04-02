package com.languagejournal.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class StudySessionResponse {

    private Long id;

    private Long languageId;

    private String languageName;

    private Long skillId;

    private String skillName;

    private LocalDate date;

    private Integer durationMinutes;

    private String notes;

    private LocalDateTime createdAt;

    public StudySessionResponse() {}

    public StudySessionResponse(Long id, Long languageId, String languageName, Long skillId,
                                String skillName, LocalDate date, Integer durationMinutes,
                                String notes, LocalDateTime createdAt) {
        this.id = id;
        this.languageId = languageId;
        this.languageName = languageName;
        this.skillId = skillId;
        this.skillName = skillName;
        this.date = date;
        this.durationMinutes = durationMinutes;
        this.notes = notes;
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

    public Long getSkillId() {
        return skillId;
    }

    public void setSkillId(Long skillId) {
        this.skillId = skillId;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
