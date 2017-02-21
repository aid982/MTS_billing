package com.capital.dragon;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;


@SpringBootApplication

public class MtsBillingApplication {

    public static String UPLOAD_DIR = "upload-dir";
    public static String WORKING_DIR = "working-dir";
    public static String UploadedFileName = "uploaded.csv";

    public static void main(String[] args) {
        SpringApplication.run(MtsBillingApplication.class, args);
    }

    @Bean
    CommandLineRunner init() {
        return (String[] args) -> {
            new File(UPLOAD_DIR).mkdir();
            new File(WORKING_DIR).mkdir();
        };
    }
}
