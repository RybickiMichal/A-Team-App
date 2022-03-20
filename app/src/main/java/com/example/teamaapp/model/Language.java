package com.example.teamaapp.model;

public enum Language {
    PL("pl"),
    EN("en"),
    DE("de"),
    FR("fr");

    public final String countryCode;

    Language(String countryCode) {
        this.countryCode = countryCode;
    }
}
