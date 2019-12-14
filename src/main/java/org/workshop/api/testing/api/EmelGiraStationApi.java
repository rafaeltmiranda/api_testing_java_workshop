package org.workshop.api.testing.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;
import org.workshop.api.testing.api.apiModel.StationInfo;
import org.workshop.api.testing.api.apiModel.StationList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.workshop.api.testing.api.ApiClient.API_KEY;

@Component("UsersApi")
public class EmelGiraStationApi {

    private ApiClient apiClient;

    public EmelGiraStationApi() {
        this(new ApiClient());
    }

    @Autowired
    public EmelGiraStationApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return this.apiClient;
    }

    public StationInfo getStationByIdUsingGET(String stationId, String apiKey) throws RestClientException {
        Object postBody = null;
        if (stationId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'accountId' when calling getCommunicationPreferenceUsingGET");
        }
        apiClient.setApiKey(API_KEY);
        if (apiKey != null) {
            apiClient.setApiKey(apiKey);
        }
        Map<String, Object> uriVariables = new HashMap();
        uriVariables.put("stationId", stationId);
        String path = UriComponentsBuilder.fromPath("/gira/station/{stationId}").buildAndExpand(uriVariables).toUriString();
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap();
        HttpHeaders headerParams = new HttpHeaders();
        MultiValueMap<String, Object> formParams = new LinkedMultiValueMap();
        String[] accepts = new String[]{"application/json"};
        List<MediaType> accept = this.apiClient.selectHeaderAccept(accepts);
        String[] contentTypes = new String[0];
        MediaType contentType = this.apiClient.selectHeaderContentType(contentTypes);

        ParameterizedTypeReference<StationInfo> returnType = new ParameterizedTypeReference<StationInfo>() {
        };

        return (StationInfo) this.apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, returnType);
    }

    public StationInfo getStationByIdUsingGET(String stationId) throws RestClientException {
        return this.getStationByIdUsingGET(stationId, null);
    }

    public StationList getStationUsingGET(String apiKey) throws RestClientException {
        Object postBody = null;
        apiClient.setApiKey(API_KEY);
        if (apiKey != null) {
            apiClient.setApiKey(apiKey);
        }
        Map<String, Object> uriVariables = new HashMap();
        String path = UriComponentsBuilder.fromPath("/gira/station/list").buildAndExpand(uriVariables).toUriString();
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap();
        HttpHeaders headerParams = new HttpHeaders();
        MultiValueMap<String, Object> formParams = new LinkedMultiValueMap();
        String[] accepts = new String[]{"application/json"};
        List<MediaType> accept = this.apiClient.selectHeaderAccept(accepts);
        String[] contentTypes = new String[0];
        MediaType contentType = this.apiClient.selectHeaderContentType(contentTypes);

        ParameterizedTypeReference<StationList> returnType = new ParameterizedTypeReference<StationList>() {
        };

        return (StationList) this.apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, returnType);
    }

    public StationList getStationUsingGET() throws RestClientException {
        return this.getStationUsingGET(null);
    }

}
