package org.workshop.api.testing.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Result {

    private String test;

    private String exceptionApi;

    private String exceptionMessage;

    private String failedExpectation;

    private Boolean valid;

    private String skipped;

    private String uuid;

}
