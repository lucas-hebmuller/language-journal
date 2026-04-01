package com.languagejournal.dto;

import java.time.LocalDateTime;

public class LanguageResponse {

    private Long id;
    private String name;
    private String colorHex;
    private LocalDateTime createdAt;

    public LanguageResponse() {};

    public LanguageResponse(Long id, String name, String colorHex, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.colorHex = colorHex;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColorHex() {
        return colorHex;
    }

    public void setColorHex(String colorHex) {
        this.colorHex = colorHex;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
