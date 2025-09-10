package com.bankle;

import com.bankle.admin.config.AppConfigList;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.TimeZone;

@SpringBootApplication(
        scanBasePackages = {
                "com.bankle.common"
                , "com.bankle.admin"
        }
)
@EnableJpaAuditing
@Import(AppConfigList.class)
public class AdminApplication {
    public static void main(String[] args) {
        System.out.println("AdminApplication Hello world!");
        SpringApplication.run(AdminApplication.class, args);
    }

    @PostConstruct
    public void setTimeZone() {
        System.out.println("TimeZone \"Asia/Seoul\" ");
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }
}