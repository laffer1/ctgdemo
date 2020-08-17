package com.capitaltg.reactiveapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

/**
 * Example REST API using Spring WebFlux and Netty. Uses a spring reactive redis library for caching. 
 */
@EnableWebFlux
@SpringBootApplication
public class RestRedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestRedisApplication.class, args);
    }

}
