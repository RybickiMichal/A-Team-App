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
    PT("pt"),
    NO("no"),
    IT("it"),
    NL("nl"),
    AR("ar"),
    DA("fa"),
    RO("ro"),
    IS("is"),
    SV("sv"),
    FR("fr");

    public final String countryCode;

    Language(String countryCode) {
        this.countryCode = countryCode;
    }
}
