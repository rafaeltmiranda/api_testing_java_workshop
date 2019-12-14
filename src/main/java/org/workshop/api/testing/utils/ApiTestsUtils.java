package org.workshop.api.testing.utils;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.workshop.api.testing.exception.ApiTestingException;
import org.workshop.api.testing.model.Result;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public class ApiTestsUtils {

    public static Result convertExceptionToFailResult(Exception e, String methodName) {
        if (e instanceof InvocationTargetException || e instanceof IllegalAccessException || e instanceof ApiTestingException) {

            String uuid = null;
            if (e instanceof InvocationTargetException && ((InvocationTargetException) e).getTargetException() instanceof ApiTestingException) {
                uuid = ((ApiTestingException)((InvocationTargetException) e).getTargetException()).getUuid();
            }
            try {
                JSONObject jsonObj = new JSONObject(e.getCause().getCause().getLocalizedMessage());
                String message = jsonObj.has("message") ? String.format("%s: %s", jsonObj.get("exception"), jsonObj.get("message")) : jsonObj.toString();
                return (ApiTestsUtils.buildFailedResult(
                        methodName,
                        e.getCause().getCause().getClass().toString(),
                        message,
                        null,
                        uuid));
            } catch (Exception e1) {
                try {
                    return (ApiTestsUtils.buildFailedResult(
                            methodName,
                            null,
                            null,
                            e.getCause().getCause().getLocalizedMessage(),
                            uuid));
                } catch (Exception e2) {
                    return (ApiTestsUtils.buildFailedResult(
                            methodName,
                            null,
                            null,
                            Objects.nonNull(e.getCause()) ? e.getCause().getLocalizedMessage() : "",
                            uuid));
                }
            }
        }

        return (ApiTestsUtils.buildFailedResult(methodName, null, null, ExceptionUtils.getStackTrace(e), null));
    }

    public static Result buildFailedResult(String testName, String exceptionApi, String exceptionMessage, String failedExpectation, String uuid) {
        return Result.builder()
                .test(testName)
                .exceptionApi(exceptionApi)
                .exceptionMessage(exceptionMessage)
                .failedExpectation(failedExpectation)
                .uuid(uuid)
                .valid(false)
                .build();
    }

    public static Result buildSkippedResult(String methodName, String skippedMotive) {
        return Result.builder()
                .test(methodName)
                .skipped(skippedMotive)
                .build();
    }

    public static Result buildSuccessResult(String methodName) {
        return Result.builder()
                .test(methodName)
                .valid(true)
                .build();
    }
}
