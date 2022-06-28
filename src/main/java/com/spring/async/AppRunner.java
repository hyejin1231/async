package com.spring.async;

import com.spring.async.service.GitHubLookupService;
import com.spring.async.vo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * findUser() : @Async 어노테이션을 사용해 비동기식으로 처리를 지정했기 때문에 PivotalSoftware, CloudFoundry, Spring-Projects
 *              유저 정보 조회 api는 응답이 오지 않아도 먼저 요청이 이루어진다.
 *              만약 동기식이었다면 PivotalSoftware에 대한 유저 정보를 요청하고 응답을 받은 뒤 다음 코드가 진행된다.
 *              -> 이러한 결과를 통해 비동기 처리는 웹 서버로 요청을 먼저 전송한 이후, 응답 결과를 수신하기 전에 다른 로직을 실행할 수 있으므로
 *                 작업의 효율을 높일 수 있음.
 * CommandLineRunner : 스프링 부트 애플리케이션 구동 시점에 특정 코드 실행 시키기 위해서 사용
 *                     이를 구현한 클래스에서 @Component 어노테이션을 선언해두면 컴포넌트 스캔이 되고, 구동 시점에 run 메서드의 코드가 실행된다.
    */
@Component
@Slf4j
public class AppRunner implements CommandLineRunner {

    private final GitHubLookupService gitHubLookupService;

    public AppRunner(GitHubLookupService gitHubLookupService) {
        this.gitHubLookupService = gitHubLookupService;
    }
    @Override
    public void run(String... args) throws Exception {
        long start = System.currentTimeMillis();
        CompletableFuture<User> page1 = gitHubLookupService.findUser("PivotalSoftware");
        CompletableFuture<User> page2 = gitHubLookupService.findUser("CloudFoundry");
        CompletableFuture<User> page3 = gitHubLookupService.findUser("Spring-Projects");

        // 3개의 요청이 완료된 후 응답을 3개의 응답을 받는데까지 걸린 시간을 측정하기 위해서 join() 사용
        // allOf() : 병렬 실행해서 결과를 조합할 때 사용
        // join() : 응답을 기다리는 역할
        CompletableFuture.allOf(page1, page2, page3).join();

        log.info("Elapsed time : " + (System.currentTimeMillis() - start));
        log.info("--> " + page1.get());
        log.info("--> " + page2.get());
        log.info("--> " + page3.get());


    }
}
