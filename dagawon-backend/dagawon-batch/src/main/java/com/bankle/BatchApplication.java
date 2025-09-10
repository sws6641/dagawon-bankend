package com.bankle;

import com.bankle.batch.config.AppConfigList;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.TimeZone;

@SpringBootApplication(
        scanBasePackages = {
                "com.bankle.common"
                ,"com.bankle.batch"
        }
)
@EnableScheduling
@EnableJpaAuditing
@Import(AppConfigList.class)
public class BatchApplication {
    public static void main(String[] args) {
        System.out.println("BatchApplication Hello world!");
        SpringApplication.run(BatchApplication.class, args);
    }

    @PostConstruct
    public void setTimeZone() {
        System.out.println("TimeZone \"Asia/Seoul\" ");
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }
}