package com.example.V1.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//AI秘钥配置
@Configuration
public class OpenAiConfig {


    @Bean
    public String openAiApiKey() {
        Dotenv dotenv = Dotenv.load();
        return dotenv.get("OPENAI_API_KEY");
    }

}

