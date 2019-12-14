package org.workshop.api.testing.model;

public class TestsReport {
    private String serviceName;
    private Integer testsPassed;
    private Integer testsFailed;
    private Integer testsSkipped;
    private Double testPassedPercentage;

    public TestsReport(String serviceName) {
        this.serviceName = serviceName;
        this.testsPassed = 0;
        this.testsFailed = 0;
        this.testsSkipped = 0;
        this.testPassedPercentage = 0d;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void incrementSuccess() {
        this.testsPassed++;
        updateTestPassedPercentage();
    }

    public void incrementFailure() {
        this.testsFailed++;
        updateTestPassedPercentage();
    }

    public void incrementSkipped() {
        this.testsSkipped++;
        updateTestPassedPercentage();
    }

    public Integer getTestsPassed() {
        return testsPassed;
    }

    public Integer getTestsFailed() {
        return testsFailed;
    }

    public Integer getTestsSkipped() {
        return testsSkipped;
    }

    public Double getTestPassedPercentage() {
        return testPassedPercentage;
    }

    private void updateTestPassedPercentage() {
        this.testPassedPercentage = ((double) this.testsPassed/(this.testsFailed + this.testsPassed + this.getTestsSkipped())) * 100;
    }

    @Override
    public String toString() {
        return "TestsReport{" +
                "serviceName='" + serviceName + '\'' +
                ", testsPassed=" + testsPassed +
                ", testsFailed=" + testsFailed +
                ", testsSkipped=" + testsSkipped +
                '}';
    }
}
