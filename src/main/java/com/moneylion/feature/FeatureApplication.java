package com.moneylion.feature;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class FeatureApplication {

    public static void main(String[] args) {
        SpringApplication.run(FeatureApplication.class, args);
    }

}
