package skku.skkujoon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import skku.skkujoon.aop.DelayAop;

@SpringBootApplication
public class SkkujoonApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkkujoonApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public DelayAop delayAop() {
        return new DelayAop();
    }

}
