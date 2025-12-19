package com.hengtiansoft.fastop.service;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan({
        "com.hengtiansoft.fastop.model.planner.dto",
        "com.hengtiansoft.fastop.model.designer.dto"
})
@SpringBootApplication
public class FastopServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FastopServiceApplication.class, args);
    }

}
