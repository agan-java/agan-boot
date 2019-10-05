package agan.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AganConfig {

    @Bean
    public Agan agan(){
        return new Agan();
    }
}
