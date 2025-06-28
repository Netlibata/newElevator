package com.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.example.V1.mapper")

@SpringBootApplication
public class NewElevatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewElevatorApplication.class, args);
    }

}
