package com.example.bankaccount;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BankAccountApplication {

    public static void main(String[] args) {
        var context = SpringApplication.run(BankAccountApplication.class, args);
        String port = context.getEnvironment().getProperty("server.port");
        System.out.println("http://localhost:" + port + "/");
    }

}
