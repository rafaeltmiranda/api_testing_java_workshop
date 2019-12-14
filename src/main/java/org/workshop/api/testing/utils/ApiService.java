package org.workshop.api.testing.utils;

public enum ApiService {
    EMEL_GIRA_STATION("EMEL-Gira-Station");

    private String text;

    ApiService(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }
}
