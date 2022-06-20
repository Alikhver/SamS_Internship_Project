package com.alikhver.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication(scanBasePackages = "com.alikhver")
@EnableScheduling
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }


    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

//    @Bean
//    public ErrorPageRegistrar errorPageRegistrar() {
//        return new MyErrorPageRegistrar();
//    }
//
//    private static class MyErrorPageRegistrar implements ErrorPageRegistrar {
//
//        @Override
//        public void registerErrorPages(ErrorPageRegistry registry) {
//            registry.addErrorPages(new ErrorPage(HttpStatus.BAD_REQUEST, "/400"));
//            registry.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/404"));
//        }
//    }
}
