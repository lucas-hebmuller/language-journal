package com.languagejournal.dto;

public class SkillBreakdownResponse {

    private String skillName;
    private Integer totalMinutes;

    public SkillBreakdownResponse() {}

    public SkillBreakdownResponse(String skillName, Integer totalMinutes) {
        this.skillName = skillName;
        this.totalMinutes = totalMinutes;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public Integer getTotalMinutes() {
        return totalMinutes;
    }

    public void setTotalMinutes(Integer totalMinutes) {
        this.totalMinutes = totalMinutes;
    }
}
