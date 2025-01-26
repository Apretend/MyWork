package com.lyh.storyapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lyh.storyapi.mapper")
public class StoryApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(StoryApiApplication.class, args);
    }

}
