package com.languagejournal.dto;

public class WeeklyProgressResponse {

    private Long languageId;
    private String languageName;
    private Integer targetMinutes;
    private Integer actualMinutes;

    public WeeklyProgressResponse() {}

    public WeeklyProgressResponse(Long languageId, String languageName, Integer targetMinutes,
                                  Integer actualMinutes) {
        this.languageId = languageId;
        this.languageName = languageName;
        this.targetMinutes = targetMinutes;
        this.actualMinutes = actualMinutes;
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

    public Integer getActualMinutes() {
        return actualMinutes;
    }

    public void setActualMinutes(Integer actualMinutes) {
        this.actualMinutes = actualMinutes;
    }
}
