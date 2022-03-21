package com.example.teamaapp.polly;

public enum Language {
    PL("pl"),
    EN("en"),
    DE("de"),
    RU("ru"),
    ES("es"),
    TR("tr"),
    ZH("zh"),
    JA("ja"),
    FR("fr");

    public final String countryCode;

    Language(String countryCode) {
        this.countryCode = countryCode;
    }
}
