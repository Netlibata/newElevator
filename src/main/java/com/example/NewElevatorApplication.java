package com.example;

import io.github.cdimascio.dotenv.Dotenv;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.File;

@MapperScan("com.example.V1.mapper")
@EnableScheduling
@SpringBootApplication
public class NewElevatorApplication {

    public static void main(String[] args) {
        // 自动从项目根目录加载 `.env` 文件
        Dotenv dotenv = Dotenv.load();
        String apiKey = dotenv.get("key");

        System.out.println("当前读取的key是: " + apiKey);

        SpringApplication.run(NewElevatorApplication.class, args);
    }

}
