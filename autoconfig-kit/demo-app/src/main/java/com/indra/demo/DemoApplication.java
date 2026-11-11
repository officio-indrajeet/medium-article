package com.indra.demo;

import com.indra.greeting.GreetingService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(GreetingService greetingService) {
        return args -> {
            System.out.println("=== RESULT ===");
            System.out.println("Bean class : " + greetingService.getClass().getName());
            System.out.println("Greeting : " + greetingService.greet("Indra"));
            System.out.println("==============");
        };
    }
}
