package com.languagejournal.dto;

import java.time.LocalDate;

public class TrendResponse {

    private LocalDate date;
    private Integer totalMinutes;

    public TrendResponse() {}

    public TrendResponse(LocalDate date, Integer totalMinutes) {
        this.date = date;
        this.totalMinutes = totalMinutes;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getTotalMinutes() {
        return totalMinutes;
    }

    public void setTotalMinutes(Integer totalMinutes) {
        this.totalMinutes = totalMinutes;
    }
}
