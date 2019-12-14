package org.workshop.api.testing.service.tests.emel;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.workshop.api.testing.annotations.ApiWorkshopTest;
import org.workshop.api.testing.api.EmelGiraStationApi;
import org.workshop.api.testing.api.apiModel.StationList;
import org.workshop.api.testing.exception.ApiTestingException;
import org.workshop.api.testing.api.apiModel.StationInfo;
import org.workshop.api.testing.service.ServiceTest;
import org.workshop.api.testing.utils.ApiService;

import static org.assertj.core.api.Assertions.assertThat;

// Doc. of API we are testing: https://emel.city-platform.com/opendata/

@Service
public class EmelGiraStationTestingService implements ServiceTest {

    private static final String API_KEY = "984f73abc0931414e3adee46a54c0786";
    private static final String STATION_ID = "029";

    private EmelGiraStationApi emelGiraStationApi;

    public EmelGiraStationTestingService(EmelGiraStationApi emelGiraStationApi) {
        this.emelGiraStationApi=emelGiraStationApi;
    }

    @ApiWorkshopTest
    public void testSkippedExample() {
        try {
            throw new ApiTestingException("Skipped test - Skipping test by example");
        } catch (Exception e) {
            throw new ApiTestingException(e);
        }
    }

    @ApiWorkshopTest
    public void testPassedExample() {
        try {
            assertThat(1).isEqualTo(1);
        } catch (Exception e) {
            throw new ApiTestingException(e);
        }
    }

    @ApiWorkshopTest
    public void invalidTestExample() {
        try {
            assertThat(1).isEqualTo(2);
        } catch (Exception e) {
            throw new ApiTestingException(e);
        }
    }

    @Override
    public ApiService getServiceName() {
        return ApiService.EMEL_GIRA_STATION;
    }
}