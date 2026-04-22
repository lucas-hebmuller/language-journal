package com.languagejournal.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class WeeklyGoalRequest {

    @NotNull(message = "Language must have an id.")
    private Long languageId;

    @NotNull(message = "Target minutes cannot be blank!")
    @Min(value = 1, message = "Target must be at least 1 minute.")
    private Integer targetMinutes;

    public WeeklyGoalRequest() {}

    public WeeklyGoalRequest(Long languageId, Integer targetMinutes) {
        this.languageId = languageId;
        this.targetMinutes = targetMinutes;
    }

    public Long getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Long languageId) {
        this.languageId = languageId;
    }

    public Integer getTargetMinutes() {
        return targetMinutes;
    }

    public void setTargetMinutes(Integer targetMinutes) {
        this.targetMinutes = targetMinutes;
    }
}
