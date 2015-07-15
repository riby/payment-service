package org.example.poc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableAutoConfiguration
@EnableDiscoveryClient
public class PaymentServiceApplication {

    private static final Logger LOG = LoggerFactory.getLogger(PaymentServiceApplication.class);


    public static void main(String[] args) {
        SpringApplication.run(PaymentServiceApplication.class, args);
        LOG.debug("Payment Microservice Application Running!!!");
    }

}
