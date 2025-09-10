package com.bankle.app.config;

import com.bankle.common.config.YamlPropertySourceFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(ignoreResourceNotFound = true, value = {
        "classpath:application-${spring.profiles.active}.yml" ,
        "classpath:application-common-${spring.profiles.active}.yml"
}, factory = YamlPropertySourceFactory.class)
public class PropertiesConfig {
}
