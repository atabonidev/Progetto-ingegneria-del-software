package main_package.persistence;

import com.google.gson.GsonBuilder;

public class JsonManager {

    private final GsonBuilder builder;
    private final String parametriConfigurazionePath;
    private final String parametriRistorantePath;

    public JsonManager(String parametriConfigurazionePath, String parametriRistorantePath) {
        this.builder = new GsonBuilder();
        this.parametriConfigurazionePath = parametriConfigurazionePath;
        this.parametriRistorantePath = parametriRistorantePath;
    }

    public GsonBuilder getBuilder() {
        return builder;
    }

    public String getParametriConfigurazionePath() {
        return parametriConfigurazionePath;
    }

    public String getParametriRistorantePath() {
        return parametriRistorantePath;
    }
}
