package com.spring.async.rest.service;

import com.spring.async.rest.vo.TestVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service("restTestService")
public class RestTestServiceImpl implements RestTestService {

    private final RestTemplate restTemplate;

    public RestTestServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * exchange() : Http 헤더를 새로 만들 수 있고, 어떤 Http 메서드도 사용 가능하다.
     * @return
     */
    @Override
    public String restGetTest() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

        ResponseEntity<TestVo> result = restTemplate.exchange("https://jsonplaceholder.typicode.com/todos/1", HttpMethod.GET, entity, TestVo.class);

        log.info("restGetTest={}", result.getBody());
        return result.getBody().toString();
    }

    /**
     * getForObject() : 주어진 url 주소로 http get 메서드로 객체 결과를 반환 받는다.
     * @return
     */
    @Override
    public String restGetTest2() {

        TestVo testVo = restTemplate.getForObject("https://jsonplaceholder.typicode.com/todos/1", TestVo.class);

        return testVo.toString();
    }

    /**
     * getForEntity() : 주어진 url 주소로 http get 메서드로 결과는 ResponseEntity로 반환 받는다.
     * @return
     */
    @Override
    public String restGetTest3() {
        ResponseEntity<TestVo> responseEntity = restTemplate.getForEntity("https://jsonplaceholder.typicode.com/todos/1", TestVo.class);
        log.info("status code = {}", responseEntity.getStatusCode());
        log.info("status code value ={}", responseEntity.getStatusCodeValue());
        return responseEntity.getBody().toString();
    }

    /**
     * post exchange() 사용
     * @return
     */
    @Override
    public String restPostTest() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("title", "foo");
        paramMap.put("body", "bar");
        paramMap.put("userId", 1);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(paramMap, httpHeaders);

        ResponseEntity<TestVo> responseEntity = restTemplate.exchange("https://jsonplaceholder.typicode.com/posts", HttpMethod.POST, entity, TestVo.class);
        log.info("status code = {} ", responseEntity.getStatusCode());
        log.info("status code value = {} ", responseEntity.getStatusCodeValue());

        return responseEntity.getBody().toString();
    }

    /**
     * postForObject() : Post 요청을 보내고 객체로 결과를 반환 받는다.
     * @return
     */
    @Override
    public String restPostTest2() {
        TestVo testVo = TestVo.builder()
                .title("foo")
                .body("bar")
                .userId(1).build();

        TestVo resultTestVo = restTemplate.postForObject("https://jsonplaceholder.typicode.com/posts", testVo, TestVo.class);

        return resultTestVo.toString();
    }

    /**
     * postForEntity() : Post 요청을 보내고 결과로 ResponseEntity로 반환 받는다.
     * @return
     */
    @Override
    public String restPostTest3() {
        TestVo testVo = TestVo.builder()
                .title("foo")
                .body("bar")
                .userId(1).build();

        ResponseEntity<TestVo> responseEntity = restTemplate.postForEntity("https://jsonplaceholder.typicode.com/posts", testVo, TestVo.class);
        log.info("status code = {}", responseEntity.getStatusCode());
        log.info("status code value = {} ", responseEntity.getStatusCodeValue());

        return responseEntity.getBody().toString();
    }

    /**
     * postForEntity() 메서드에 header 정보 포함해서 보내기
     * @return
     */
    @Override
    public String restPostTest4() {
        TestVo testVo = TestVo.builder()
                .title("foo")
                .body("bar")
                .userId(1).build();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<TestVo> request = new HttpEntity<>(testVo, httpHeaders);

        ResponseEntity<TestVo> responseEntity = restTemplate.postForEntity("https://jsonplaceholder.typicode.com/posts", request, TestVo.class);

        log.info("status code = {} ", responseEntity.getStatusCode());
        log.info("request body = {}", responseEntity.getBody());

        return responseEntity.getBody().toString();
    }
}
