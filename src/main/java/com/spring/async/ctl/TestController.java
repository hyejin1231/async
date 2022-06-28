package com.spring.async.ctl;

import com.spring.async.service.ExceptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TestController {

    private final ExceptionService exceptionService;

    public TestController(ExceptionService exceptionService) {
        this.exceptionService = exceptionService;
    }


    @GetMapping("/test")
    public String test() {

        exceptionService.exceptionTest();

        return "ok";
    }
}
