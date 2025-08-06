package com.natha.dev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
//@EnableDiscoveryClient
public class MultitenantBackEnd {

    public static void main(String[] args) {
        SpringApplication.run(MultitenantBackEnd.class, args);
    }
}
