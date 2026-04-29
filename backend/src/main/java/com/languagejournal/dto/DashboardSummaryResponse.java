package com.languagejournal.dto;

public class DashboardSummaryResponse {

    private Integer totalMinutesThisWeek;
    private Integer totalMinutesThisMonth;
    private Integer currentStreak;
    private Long totalSessions;

    public DashboardSummaryResponse() {}

    public DashboardSummaryResponse(Integer totalMinutesThisWeek, Integer totalMinutesThisMonth,
                                    Integer currentStreak, Long totalSessions) {
        this.totalMinutesThisWeek = totalMinutesThisWeek;
        this.totalMinutesThisMonth = totalMinutesThisMonth;
        this.currentStreak = currentStreak;
        this.totalSessions = totalSessions;
    }

    public Integer getTotalMinutesThisWeek() {
        return totalMinutesThisWeek;
    }

    public void setTotalMinutesThisWeek(Integer totalMinutesThisWeek) {
        this.totalMinutesThisWeek = totalMinutesThisWeek;
    }

    public Integer getTotalMinutesThisMonth() {
        return totalMinutesThisMonth;
    }

    public void setTotalMinutesThisMonth(Integer totalMinutesThisMonth) {
        this.totalMinutesThisMonth = totalMinutesThisMonth;
    }

    public Integer getCurrentStreak() {
        return currentStreak;
    }

    public void setCurrentStreak(Integer currentStreak) {
        this.currentStreak = currentStreak;
    }

    public Long getTotalSessions() {
        return totalSessions;
    }

    public void setTotalSessions(Long totalSessions) {
        this.totalSessions = totalSessions;
    }
}
