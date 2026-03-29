package com.languagejournal.dto;

import jakarta.validation.constraints.NotBlank;

public class LanguageRequest {

    @NotBlank(message = "Name cannot be blank!")
    private String name;

    @NotBlank(message = "Color Hex cannot be blank!")
    private String colorHex;

    public LanguageRequest() {}

    public LanguageRequest(String name, String colorHex) {
        this.name = name;
        this.colorHex = colorHex;
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
}
