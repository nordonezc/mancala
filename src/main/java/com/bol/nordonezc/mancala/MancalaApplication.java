package com.bol.nordonezc.mancala;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MancalaApplication {

    public static void main(String[] args) {
        SpringApplication.run(MancalaApplication.class, args);
    }

}
