package org.workshop.api.testing.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.workshop.api.testing.exception.InvalidRequestException;
import org.workshop.api.testing.model.Result;
import org.workshop.api.testing.model.TestsReport;
import org.workshop.api.testing.utils.ApiService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TestingService {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(TestingService.class);
    @Autowired
    private List<ServiceTest> serviceTests;
    private ReportService reportService;

    public TestingService(ReportService reportService) {
        this.reportService = reportService;
    }

    public List<Result> runServiceTest(String testName, ApiService apiService) {
        List<Result> results = new ArrayList<>();

        LOGGER.info("Running {} test on {}", Objects.isNull(testName) ? "" : testName, apiService.getText());
        List<ServiceTest> st = this.serviceTests.stream()
                .filter(serv -> serv.getServiceName().equals(apiService))
                .collect(Collectors.toList());

        if (st.isEmpty()) {
            throw new InvalidRequestException("Service not found.");
        }

        st.forEach(serv -> results.addAll(runTests(testName, serv)));

        List<Result> failedSkippedTests = results.stream()
                .filter(result -> (result.getValid() != null && !result.getValid()) || result.getSkipped() != null)
                .collect(Collectors.toList());

        reportService.sendSlackMessage(apiService.getText(), failedSkippedTests);

        return failedSkippedTests;
    }

    public List<Result> runServiceTests(ApiService apiService) {
        List<Result> results = new ArrayList<>();

        LOGGER.info("Running tests on {}", apiService.getText());
        List<ServiceTest> st = this.serviceTests.stream().filter(serv -> serv.getServiceName().equals(apiService)).collect(Collectors.toList());

        if (org.springframework.util.CollectionUtils.isEmpty(st)) {
            throw new InvalidRequestException("Service not found.");
        }

        st.forEach(serv -> results.addAll(runTests(null, serv)));

        List<Result> failedSkippedTests = results.stream()
                .filter(result -> (result.getValid() != null && !result.getValid()) || result.getSkipped() != null)
                .collect(Collectors.toList());

        reportService.sendSlackMessage(apiService.getText(), failedSkippedTests);

        return failedSkippedTests;
    }

    private List<Result> runTests(String testName, ServiceTest st) {
        List<Result> results = new ArrayList<>();
        //run simple methods
        results.addAll(st.run(testName, true));

        TestsReport testsReport = new TestsReport(st.getServiceName().getText());
        results.forEach(result -> {
            if (result.getSkipped() != null) {
                testsReport.incrementSkipped();
            } else {
                if (result.getValid()) {
                    testsReport.incrementSuccess();
                } else {
                    testsReport.incrementFailure();
                }
            }
        });

        reportService.setOrUpdateReport(testsReport);

        return results;
    }

    public ReportService getReportService() {
        return reportService;
    }

    public List<Result> runAllTests() {

        List<Result> results = new ArrayList<>();
        //for every service
        Arrays.stream(ApiService.values()).forEach(s -> {
            LOGGER.info(String.format("Running tests for %s!", s.getText()));

            results.addAll(this.runServiceTests(s));
        });

        return results;
    }
}
