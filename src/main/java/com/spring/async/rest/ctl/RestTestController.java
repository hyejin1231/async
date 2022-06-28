package com.spring.async.rest.ctl;

import com.spring.async.rest.service.RestTestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestTestController {

    private final RestTestService restTestService;

    public RestTestController(RestTestService restTestService) {
        this.restTestService = restTestService;
    }

    @GetMapping("/restTest")
    public String test1() {
        return restTestService.restGetTest();
    }

    @GetMapping("/restTest2")
    public String test2() {
        return restTestService.restGetTest2();
    }

    @GetMapping("/restTest3")
    public String test3() {
        return restTestService.restGetTest3();
    }

    @GetMapping("/restTest4")
    public String test4() {
        return restTestService.restPostTest();
    }

    @GetMapping("/restTest5")
    public String test5() {
        return restTestService.restPostTest2();
    }

    @GetMapping("/restTest6")
    public String test6() {
        return restTestService.restPostTest3();
    }

    @GetMapping("/restTest7")
    public String test7() {
        return restTestService.restPostTest4();
    }
}
