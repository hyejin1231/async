package com.spring.async.service;

import org.springframework.stereotype.Service;

@Service
public class ExceptionService {

    private final TestService testService;

    public ExceptionService(TestService testService) {
        this.testService = testService;
    }


    public void exceptionTest() {
        testService.test();
    }
}
