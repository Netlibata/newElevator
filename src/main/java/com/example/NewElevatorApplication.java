package com.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@MapperScan("com.example.V1.mapper")
@EnableScheduling
@SpringBootApplication
public class NewElevatorApplication {

    public static void main(String[] args){
        SpringApplication.run(NewElevatorApplication.class, args);
    }

}
