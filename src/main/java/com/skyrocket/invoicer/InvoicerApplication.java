package com.skyrocket.invoicer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class InvoicerApplication {

    public static void main(String[] args) {
        SpringApplication.run(InvoicerApplication.class, args);
    }

}
