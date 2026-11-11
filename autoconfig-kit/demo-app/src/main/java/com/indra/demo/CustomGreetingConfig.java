package com.indra.demo;

import com.indra.greeting.GreetingService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Only active with --demo.custom=true. Shows @ConditionalOnMissingBean backing off. */
@Configuration
@ConditionalOnProperty(name = "demo.custom", havingValue = "true")
public class CustomGreetingConfig {

    @Bean
    GreetingService greetingService() {
        return name -> "Hello " + name + " (from MY OWN bean in demo-app)";
    }
}
