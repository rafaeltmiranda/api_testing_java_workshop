package org.workshop.api.testing.utils;

import static org.workshop.api.testing.utils.ApiService.*;

public enum ApiServiceAggregator {

    //not capital letters because this is used on the endpoint and I want to keep /test/ach
    all("all"),
    emelGiraStation("emel", EMEL_GIRA_STATION);

    private String serviceName;
    private ApiService[] tests;

    ApiServiceAggregator(String serviceName, ApiService... tests) {
        this.serviceName = serviceName;
        this.tests = tests;
    }

    public ApiService[] getTests() {
        return tests;
    }

    public String getServiceName() {
        return serviceName;
    }
}

