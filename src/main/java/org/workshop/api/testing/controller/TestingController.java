package org.workshop.api.testing.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.workshop.api.testing.model.Report;
import org.workshop.api.testing.model.Result;
import org.workshop.api.testing.service.TestingService;
import org.workshop.api.testing.utils.ApiService;
import org.workshop.api.testing.utils.ApiServiceAggregator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.util.Objects.nonNull;
import static org.workshop.api.testing.utils.ReportUtils.buildReport;

@RestController
@Api(value = "Testing", tags = "Testing")
public class TestingController extends BaseController {

    private TestingService testingService;

    @Autowired
    public TestingController(TestingService testingService) {
        this.testingService = testingService;
    }

    @GetMapping("/test/{serviceName}")
    @ApiOperation(value = "Testing Endpoint", response = Report.class)
    public CompletableFuture<ResponseEntity<Object>> runTests(
            @PathVariable(value = "serviceName") ApiServiceAggregator serviceName,
            @RequestParam(value = "testName", required = false) String testName) {

        LOGGER.info("Running " + serviceName.name() + " tests...");

        List<Result> results = new ArrayList<>();

        if (serviceName.equals(ApiServiceAggregator.all)) {
            results = this.testingService.runAllTests();
        } else {
            for (ApiService apiService : serviceName.getTests()) {
                results.addAll(this.testingService.runServiceTest(testName, apiService));
            }
        }

        Result r = results.stream()
                .filter(t -> Boolean.FALSE.equals(t.getValid()))
                .findFirst()
                .orElse(null);

        if (r == null) {
            return CompletableFuture.completedFuture(ResponseEntity.ok(results));
        }

        return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(buildReport(results)));
    }
}
