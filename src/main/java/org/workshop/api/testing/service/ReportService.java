package org.workshop.api.testing.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.seratch.jslack.api.webhook.Payload;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.workshop.api.testing.model.Result;
import org.workshop.api.testing.model.TestsReport;
import org.workshop.api.testing.properties.TestingProperties;
import com.github.seratch.jslack.*;

import java.util.*;

import static org.workshop.api.testing.utils.ReportUtils.buildReport;

@Service
public class ReportService {
    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ReportService.class);

    private ObjectMapper objectMapper;
    private String bucketName;
    private Map<String, TestsReport> testsReportMap = new HashMap<>();
    private TestingProperties testingProperties;

    public ReportService(ObjectMapper objectMapper,
                         TestingProperties testingProperties
    ) {
        this.objectMapper = objectMapper;
        this.testingProperties = testingProperties;
    }

    public List<TestsReport> getReportList() {
        List<TestsReport> reportsList = new ArrayList<>();
        for (Map.Entry<String, TestsReport> entry : testsReportMap.entrySet()) {
            reportsList.add(entry.getValue());
        }
        return reportsList;
    }

    public void setOrUpdateReport(TestsReport testsReport) {
        this.testsReportMap.put(testsReport.getServiceName(), testsReport);
    }

    public void sendSlackMessage(String endpoint, List<Result> failedSkippedTests) {
        if (failedSkippedTests.size() > 0) {
            messageAlert(endpoint, String.format(
                    "Found errors or skipped tests on endpoint *%s*. \r Check the report:\r```\r%s--\rEnd of report\r```",
                    endpoint,
                    buildReport(failedSkippedTests)));
            return;
        }
        messageAlert(endpoint, String.format(
                "Tests performed on endpoint *%s* successfully", endpoint));
    }

    public void messageAlert(String endpoint, String message) {
        try {
            LOGGER.info("Slack alerts are enabled: {}", testingProperties.getSlack().getActive());

            if (testingProperties.getSlack().getActive()) {
                LOGGER.info("Sending alert message for slack channel {} and webhook {}.",
                        testingProperties.getSlack().getChannel(), testingProperties.getSlack().getWebhook());

                message = formatMessage(message, endpoint);

                Payload payload = Payload.builder()
                        .channel(testingProperties.getSlack().getChannel())
                        .text(message)
                        .build();

                Slack.getInstance().send(testingProperties.getSlack().getWebhook(), payload);
            }
        } catch (Exception e) {
            LOGGER.error("Error posting slack message ...", e);
        }
    }

    private String formatMessage(String message, String endpoint) {
        message = message.replace(" \n", "");
        message = message.replace("\n ", " ");
        message = message.replace("\n", " ");

        message = message.replace("exceptionApi=null, ", "");
        message = message.replace("exceptionMessage=null, ", "");
        message = message.replace("valid=false, ", "");
        message = message.replace("valid=null, ", "");
        message = message.replace("skipped=null, ", "");
        message = message.replace("uuid=null", "");
        message = message.replace("failedExpectation=null, ", "");

        message = message.replace("failedExpectation=", "failedExpectation: ");
        message = message.replace("skipped=i.testing.exception.ApiTestingException:", "skippedReason: ");

        message = message.replace("Report(", String.format("Report of tests on endpoint %s\r--\r\r", endpoint));

        message = message.replace("error=[]", "FAILED\r\r  All tests ok.\r");
        message = message.replace("error=[", "FAILED\r\r");

        message = message.replace(")], skipped=[]", "\rSKIPPED\r\r  No tests were skipped.\r");
        message = message.replace(")], skipped=[", "\rSKIPPED\r\r");
        message = message.replace(", skipped=[", "\rSKIPPED\r\r");

        message = message.replace("Result(test=", "  Test ");

        message = message.replace(")])", "\r");
        message = message.replace("), ", "\r");
        message = message.replace(", ", "\r    ");
        message = message.replace("Expecting:", "\r    expecting:");
        message = message.replace(")]", "");
        message = message.replace("])", "");
        message = message.replace(")", "\r");

        return message;
    }

    public TestingProperties.Slack getSlack() {
        return testingProperties.getSlack();
    }
}
