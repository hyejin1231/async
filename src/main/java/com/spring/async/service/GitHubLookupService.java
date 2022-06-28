package com.spring.async.service;

import com.spring.async.vo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class GitHubLookupService {

    private final RestTemplate restTemplate;

    public GitHubLookupService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    /**
     * @Async의 반환유형은 비동기 User 서비스에 대한 요구사항
     * findUser 메서드는 다른 쓰레드로 실행되도록 @Async로 어노테이션
     * @param user
     * @return 반환 타입은 User가 아닌 비동기 서비스의 요구사항인 CompleteableFuture이다.
     * @throws InterruptedException
     */
    @Async
    public CompletableFuture<User> findUser(String user) throws InterruptedException {
        log.info("Looking up " + user);
        String url = String.format("https://api.github.com/users/%s", user);
        User resultUser = restTemplate.getForObject(url, User.class);
        Thread.sleep(1000L);

        return CompletableFuture.completedFuture(resultUser);
    }
}
