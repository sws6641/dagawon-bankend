package kr.co.anbu.component.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("push")
@Getter @Setter
public class PushProperties {
    private String apiKey;
}
