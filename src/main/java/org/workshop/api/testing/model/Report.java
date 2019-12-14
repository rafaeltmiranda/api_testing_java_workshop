package org.workshop.api.testing.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Report {

    private List<Result> error;

    private List<Result> skipped;

}
