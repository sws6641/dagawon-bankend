package com.bankle;

import com.bankle.app.config.AppConfigList;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.TimeZone;

@SpringBootApplication(
        scanBasePackages = {
                "com.bankle.common"
                , "com.bankle.app"
        }
)
@EnableJpaAuditing
@EnableAsync
@Import(AppConfigList.class)
public class AppApplication {
    public static void main(String[] args) {
        System.out.println("AppApplication Hello world!");
        SpringApplication.run(AppApplication.class, args);
    }

    @PostConstruct
    public void setTimeZone() {
        System.out.println("TimeZone \"Asia/Seoul\" ");
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }
}

