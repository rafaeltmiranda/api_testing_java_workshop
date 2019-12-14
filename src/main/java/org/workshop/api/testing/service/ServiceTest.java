package org.workshop.api.testing.service;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.workshop.api.testing.annotations.ApiWorkshopTest;
import org.workshop.api.testing.exception.ApiTestingException;
import org.workshop.api.testing.model.Result;
import org.workshop.api.testing.utils.ApiService;
import org.pacesys.reflect.Reflect;
import org.workshop.api.testing.utils.ApiTestsUtils;
import org.assertj.core.util.Strings;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public interface ServiceTest {
    Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ServiceTest.class);

    ApiService getServiceName();

    default List<Result> run(String methodName, boolean retry) {
        List<Result> results = new ArrayList<>();
        List<Method> methods = Reflect.on(this.getClass())
                .methods().annotatedWith(ApiWorkshopTest.class)
                .stream()
                .collect(Collectors.toList());

        if (!Strings.isNullOrEmpty(methodName)) {
            methods = methods.stream().filter(method -> method.getName().equals(methodName)).collect(Collectors.toList());
        }

        methods.forEach(m -> {
            String testName = "[" + this.getServiceName().getText() + "] " + m.getName();
            try {
                m.setAccessible(true);
                List<Class> paramsType = Arrays.asList(m.getParameterTypes());
                if (paramsType.size() == 0) {
                    m.invoke(this);

                    // results.add(PlatformTestsUtils.buildSuccessResult(testName, null, null));
                    LOGGER.info(String.format("VALID test <%s>", testName));
                } else {
                    throw new ApiTestingException("Method needs a signature without args");
                }
                results.add(ApiTestsUtils.buildSuccessResult(testName));
            } catch (Exception e) {
                if (!(e.getCause().getMessage() == null) && e.getCause().getMessage().contains("Skipped test")) {
                    results.add(ApiTestsUtils.buildSkippedResult(testName, e.getCause().getMessage().substring(15)));
                    LOGGER.info(String.format("SKIPPED test <%s>", testName));
                } else {
                    if (retry) {
                        LOGGER.warn(String.format("RETRYING test <%s>", testName));
                        List<Result> results1 = run(m.getName(), false);
                        results.add(results1.get(0));
                    } else {
                        LOGGER.error(ExceptionUtils.getStackTrace(e));
                        LOGGER.error(String.format("FAILED test <%s>", testName));
                        results.add(ApiTestsUtils.convertExceptionToFailResult(e, testName));
                    }
                }
            }
        });

        return results;
    }
}
