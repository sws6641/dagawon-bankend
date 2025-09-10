package com.bankle.admin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        PropertiesConfig.class
})
public class AppConfigList {
}
