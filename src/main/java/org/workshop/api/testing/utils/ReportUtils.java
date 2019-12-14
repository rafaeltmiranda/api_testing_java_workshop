package org.workshop.api.testing.utils;

import org.workshop.api.testing.model.Report;
import org.workshop.api.testing.model.Result;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class ReportUtils {

    public static Report buildReport(List<Result> results) {
        return Report
                .builder()
                .error(results.stream().filter(r -> isNull(r.getSkipped())).collect(Collectors.toList()))
                .skipped(results.stream().filter(r -> nonNull(r.getSkipped())).collect(Collectors.toList()))
                .build();

    }
}
