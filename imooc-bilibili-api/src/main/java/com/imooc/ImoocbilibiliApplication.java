package com.imooc;

import com.imooc.websocket.WebSocketService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class ImoocbilibiliApplication {
    public static void main(String[] args) {
        ApplicationContext app = SpringApplication.run(ImoocbilibiliApplication.class, args);
        WebSocketService.setApplicationContext(app);
    }
}
