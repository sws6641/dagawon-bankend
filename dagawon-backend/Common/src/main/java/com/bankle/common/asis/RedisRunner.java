package kr.co.anbu;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * 레디스에 코드 테이블 저장
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class RedisRunner implements ApplicationRunner {

    private final RestTemplateBuilder restTemplateBuilder;

    @Override
    public void run(ApplicationArguments args) throws Exception{
        RestTemplate restTemplate = restTemplateBuilder
                .rootUri("http://127.0.0.1:8082")
                .build();
        
        Boolean result = restTemplate.getForObject("/api/codes/redis/save", Boolean.class);

        log.info("redis(codes) saved >> "+ result);

    }
}
