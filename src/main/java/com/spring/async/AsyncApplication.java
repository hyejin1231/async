package com.spring.async;

import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Executor;

/**
 * @EnableAsync : 비동기 함수를 실행할 수 있도록 해준다.
 * 이 어노테이션은 스프링이 뒤에서 설명할 @Async 어노테이션이 정의된 메서드를 스프링이 백그라운드 스레드 풀을 사용하여 실행할수 있도록 한다.
 */
@SpringBootApplication
@EnableAsync
public class AsyncApplication {

	public static void main(String[] args) {
		// close the application context to shut down the custom ExecutorService
		SpringApplication.run(AsyncApplication.class, args);
	}

	/**
	 * 새롭게 빈을 생성해서 Executor를 커스터마이징 했음
	 * Executor : 비동기적 작업 실행 프레임워크 (애플리케이션에서 작업 실행 과정을 관리하고 모니터링하기 위한 기능..등등)
	 * ExecutorService : 병렬 작업시 여러 개의 작업을 효율적으로 처리하기 위해 제공되는 java 라이브러리
	 * @return
	 */
	@Bean
	public Executor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2); // 기본 실행 대기하는 Thread 수
		executor.setMaxPoolSize(2); // 동시 스레드 수 2 제한
		executor.setQueueCapacity(500);// 대기열 크기 500 제한, MaxPoolsize 초과 요청에서 Thread 생성 요청 시 해당 요청을 Queue에 저장하는데 이때 최대 수용 가능한 Queue의 수
		executor.setThreadNamePrefix("GithubLookup-"); // 생성되는 Thread의 접두사 지정
		executor.initialize();
		return executor;
	}
}
