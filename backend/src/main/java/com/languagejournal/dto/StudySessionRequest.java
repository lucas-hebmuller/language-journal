package com.languagejournal.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class StudySessionRequest {

    @NotNull(message = "Language must have an id.")
    private Long languageId;

    @NotNull(message = "Skill must have an id.")
    private Long skillId;

    @NotNull(message = "Date cannot be blank!")
    private LocalDate date;

    @NotNull(message = "Duration cannot be blank!")
    @Min(value = 1, message = "Duration must be at least 1 minute.")
    private Integer durationMinutes;

    private String notes;

    public StudySessionRequest() {}

    public StudySessionRequest(Long languageId, Long skillId, LocalDate date,
                               Integer durationMinutes, String notes) {
        this.languageId = languageId;
        this.skillId = skillId;
        this.date = date;
        this.durationMinutes = durationMinutes;
        this.notes = notes;
    }

    public Long getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Long languageId) {
        this.languageId = languageId;
    }

    public Long getSkillId() {
        return skillId;
    }

    public void setSkillId(Long skillId) {
        this.skillId = skillId;
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
}
