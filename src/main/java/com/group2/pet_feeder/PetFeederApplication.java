package com.group2.pet_feeder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class PetFeederApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetFeederApplication.class, args);
    }

}
