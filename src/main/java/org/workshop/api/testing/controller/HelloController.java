package org.workshop.api.testing.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping(value = "/test-controller", method = RequestMethod.GET)
    public String testGET() {
        return "GET is working";
    }

    @RequestMapping(value = "/test-controller", method = RequestMethod.POST)
    public String testPOST() {
        return "POST is working";
    }

    @RequestMapping(value = "/test-controller", method = RequestMethod.PUT)
    public String testPUT() {
        return "PUT is working";
    }

    @RequestMapping(value = "/test-controller", method = RequestMethod.DELETE)
    public String testDELETE() {
        return "DELETE is working";
    }
}

