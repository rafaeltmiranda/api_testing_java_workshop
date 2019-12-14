package org.workshop.api.testing.api;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.RequestEntity.BodyBuilder;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.workshop.api.testing.api.utils.RFC3339DateFormat;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.*;

@Component("ApiClient")
public class ApiClient {
    public static final String API_KEY = "984f73abc0931414e3adee46a54c0786";

    private boolean debugging = false;
    private HttpHeaders defaultHeaders = new HttpHeaders();
    private String basePath = "https://emel.city-platform.com/opendata";
    private RestTemplate restTemplate;
    private HttpStatus statusCode;
    private MultiValueMap<String, String> responseHeaders;
    private DateFormat dateFormat;

    public ApiClient() {
        this.restTemplate = this.buildRestTemplate();
        this.init();
    }

    protected void init() {
        this.dateFormat = new RFC3339DateFormat();
        this.dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        this.setUserAgent("Java-SDK");
        this.setApiKey(API_KEY);
    }

    public String getBasePath() {
        return this.basePath;
    }

    public ApiClient setBasePath(String basePath) {
        this.basePath = basePath;
        return this;
    }

    public HttpStatus getStatusCode() {
        return this.statusCode;
    }

    public MultiValueMap<String, String> getResponseHeaders() {
        return this.responseHeaders;
    }

    public ApiClient setUserAgent(String userAgent) {
        this.addDefaultHeader("User-Agent", userAgent);
        return this;
    }

    public ApiClient setApiKey(String apiKey) {
        if (defaultHeaders.getFirst("api_key") != null ) {
            defaultHeaders.set("api_key",apiKey);
            return this;
        }
        this.addDefaultHeader("api_key", apiKey);
        return this;
    }

    public ApiClient addDefaultHeader(String name, String value) {
        this.defaultHeaders.add(name, value);
        return this;
    }

    public void setDebugging(boolean debugging) {
        List<ClientHttpRequestInterceptor> currentInterceptors = this.restTemplate.getInterceptors();
        if (debugging) {
            if (currentInterceptors == null) {
                currentInterceptors = new ArrayList();
            }

            ClientHttpRequestInterceptor interceptor = new ApiClient.ApiClientHttpRequestInterceptor();
            ((List)currentInterceptors).add(interceptor);
            this.restTemplate.setInterceptors((List)currentInterceptors);
        } else if (currentInterceptors != null && !((List)currentInterceptors).isEmpty()) {
            Iterator iter = ((List)currentInterceptors).iterator();

            while(iter.hasNext()) {
                ClientHttpRequestInterceptor interceptor = (ClientHttpRequestInterceptor)iter.next();
                if (interceptor instanceof ApiClient.ApiClientHttpRequestInterceptor) {
                    iter.remove();
                }
            }

            this.restTemplate.setInterceptors((List)currentInterceptors);
        }

        this.debugging = debugging;
    }

    public boolean isDebugging() {
        return this.debugging;
    }

    public DateFormat getDateFormat() {
        return this.dateFormat;
    }

    public ApiClient setDateFormat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
        return this;
    }

    public Date parseDate(String str) {
        try {
            return this.dateFormat.parse(str);
        } catch (ParseException var3) {
            throw new RuntimeException(var3);
        }
    }

    public String formatDate(Date date) {
        return this.dateFormat.format(date);
    }

    public String parameterToString(Object param) {
        if (param == null) {
            return "";
        } else if (param instanceof Date) {
            return this.formatDate((Date)param);
        } else if (param instanceof Collection) {
            StringBuilder b = new StringBuilder();

            Object o;
            for(Iterator var3 = ((Collection)param).iterator(); var3.hasNext(); b.append(String.valueOf(o))) {
                o = var3.next();
                if (b.length() > 0) {
                    b.append(",");
                }
            }

            return b.toString();
        } else {
            return String.valueOf(param);
        }
    }

    public MultiValueMap<String, String> parameterToMultiValueMap(ApiClient.CollectionFormat collectionFormat, String name, Object value) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap();
        if (name != null && !name.isEmpty() && value != null) {
            if (collectionFormat == null) {
                collectionFormat = ApiClient.CollectionFormat.CSV;
            }

            Collection<?> valueCollection = null;
            if (!(value instanceof Collection)) {
                params.add(name, this.parameterToString(value));
                return params;
            } else {
                valueCollection = (Collection)value;
                if (valueCollection.isEmpty()) {
                    return params;
                } else if (collectionFormat.equals(ApiClient.CollectionFormat.MULTI)) {
                    Iterator var9 = valueCollection.iterator();

                    while(var9.hasNext()) {
                        Object item = var9.next();
                        params.add(name, this.parameterToString(item));
                    }

                    return params;
                } else {
                    List<String> values = new ArrayList();
                    Iterator var7 = valueCollection.iterator();

                    while(var7.hasNext()) {
                        Object o = var7.next();
                        values.add(this.parameterToString(o));
                    }

                    params.add(name, collectionFormat.collectionToString(values));
                    return params;
                }
            }
        } else {
            return params;
        }
    }

    public boolean isJsonMime(String mediaType) {
        try {
            return this.isJsonMime(MediaType.parseMediaType(mediaType));
        } catch (InvalidMediaTypeException var3) {
            return false;
        }
    }

    public boolean isJsonMime(MediaType mediaType) {
        return mediaType != null && (MediaType.APPLICATION_JSON.isCompatibleWith(mediaType) || mediaType.getSubtype().matches("^.*\\+json[;]?\\s*$"));
    }

    public List<MediaType> selectHeaderAccept(String[] accepts) {
        if (accepts.length == 0) {
            return null;
        } else {
            String[] var2 = accepts;
            int var3 = accepts.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                String accept = var2[var4];
                MediaType mediaType = MediaType.parseMediaType(accept);
                if (this.isJsonMime(mediaType)) {
                    return Collections.singletonList(mediaType);
                }
            }

            return MediaType.parseMediaTypes(StringUtils.arrayToCommaDelimitedString(accepts));
        }
    }

    public MediaType selectHeaderContentType(String[] contentTypes) {
        if (contentTypes.length == 0) {
            return MediaType.APPLICATION_JSON;
        } else {
            String[] var2 = contentTypes;
            int var3 = contentTypes.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                String contentType = var2[var4];
                MediaType mediaType = MediaType.parseMediaType(contentType);
                if (this.isJsonMime(mediaType)) {
                    return mediaType;
                }
            }

            return MediaType.parseMediaType(contentTypes[0]);
        }
    }

    protected Object selectBody(Object obj, MultiValueMap<String, Object> formParams, MediaType contentType) {
        boolean isForm = MediaType.MULTIPART_FORM_DATA.isCompatibleWith(contentType) || MediaType.APPLICATION_FORM_URLENCODED.isCompatibleWith(contentType);
        return isForm ? formParams : obj;
    }

    public <T> T invokeAPI(String path, HttpMethod method, MultiValueMap<String, String> queryParams, Object body, HttpHeaders headerParams, MultiValueMap<String, Object> formParams, List<MediaType> accept, MediaType contentType, ParameterizedTypeReference<T> returnType) throws RestClientException {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.basePath).path(path);
        if (queryParams != null) {
            builder.queryParams(queryParams);
        }

        RequestEntity.BodyBuilder requestBuilder = RequestEntity.method(method, builder.build().toUri());
        if (accept != null) {
            requestBuilder.accept((MediaType[])accept.toArray(new MediaType[accept.size()]));
        }

        if (contentType != null) {
            requestBuilder.contentType(contentType);
        }

        this.addHeadersToRequest(headerParams, requestBuilder);
        this.addHeadersToRequest(this.defaultHeaders, requestBuilder);
        RequestEntity<Object> requestEntity = requestBuilder.body(this.selectBody(body, formParams, contentType));
        ResponseEntity<T> responseEntity = this.restTemplate.exchange(requestEntity, returnType);
        this.statusCode = responseEntity.getStatusCode();
        this.responseHeaders = responseEntity.getHeaders();
        if (responseEntity.getStatusCode() == HttpStatus.NO_CONTENT) {
            return null;
        } else if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return returnType == null ? null : responseEntity.getBody();
        } else {
            throw new RestClientException("API returned " + this.statusCode + " and it wasn't handled by the RestTemplate error handler");
        }
    }

    protected void addHeadersToRequest(HttpHeaders headers, RequestEntity.BodyBuilder requestBuilder) {
        Iterator var3 = headers.entrySet().iterator();

        while(var3.hasNext()) {
            Map.Entry<String, List<String>> entry = (Map.Entry)var3.next();
            List<String> values = (List)entry.getValue();
            Iterator var6 = values.iterator();

            while(var6.hasNext()) {
                String value = (String)var6.next();
                if (value != null) {
                    requestBuilder.header((String)entry.getKey(), new String[]{value});
                }
            }
        }

    }

    protected RestTemplate buildRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(restTemplate.getRequestFactory()));
        return restTemplate;
    }

    private class ApiClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {
        private final Log log;

        private ApiClientHttpRequestInterceptor() {
            this.log = LogFactory.getLog(ApiClient.ApiClientHttpRequestInterceptor.class);
        }

        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
            this.logRequest(request, body);
            ClientHttpResponse response = execution.execute(request, body);
            this.logResponse(response);
            return response;
        }

        private void logRequest(HttpRequest request, byte[] body) throws UnsupportedEncodingException {
            this.log.info("URI: " + request.getURI());
            this.log.info("HTTP Method: " + request.getMethod());
            this.log.info("HTTP Headers: " + this.headersToString(request.getHeaders()));
            this.log.info("Request Body: " + new String(body, StandardCharsets.UTF_8));
        }

        private void logResponse(ClientHttpResponse response) throws IOException {
            this.log.info("HTTP Status Code: " + response.getRawStatusCode());
            this.log.info("Status Text: " + response.getStatusText());
            this.log.info("HTTP Headers: " + this.headersToString(response.getHeaders()));
            this.log.info("Response Body: " + this.bodyToString(response.getBody()));
        }

        private String headersToString(HttpHeaders headers) {
            StringBuilder builder = new StringBuilder();
            Iterator var3 = headers.entrySet().iterator();

            while(var3.hasNext()) {
                Map.Entry<String, List<String>> entry = (Map.Entry)var3.next();
                builder.append((String)entry.getKey()).append("=[");
                Iterator var5 = ((List)entry.getValue()).iterator();

                while(var5.hasNext()) {
                    String value = (String)var5.next();
                    builder.append(value).append(",");
                }

                builder.setLength(builder.length() - 1);
                builder.append("],");
            }

            builder.setLength(builder.length() - 1);
            return builder.toString();
        }

        private String bodyToString(InputStream body) throws IOException {
            StringBuilder builder = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(body, StandardCharsets.UTF_8));

            for(String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                builder.append(line).append(System.lineSeparator());
            }

            bufferedReader.close();
            return builder.toString();
        }
    }

    public static enum CollectionFormat {
        CSV(","),
        TSV("\t"),
        SSV(" "),
        PIPES("|"),
        MULTI((String)null);

        private final String separator;

        private CollectionFormat(String separator) {
            this.separator = separator;
        }

        private String collectionToString(Collection<? extends CharSequence> collection) {
            return StringUtils.collectionToDelimitedString(collection, this.separator);
        }
    }
}
