package com.gyc.mallchat.consumer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ClassName: MallChatServerApplication
 * Package: com.gyc.mallchat
 * Description:
 *
 * @Author gyc
 * @Create 2024/4/2 17:01
 * @Version 1.0
 */
@SpringBootApplication( scanBasePackages = {"com.gyc.mallchat"} )
@MapperScan( {"com.gyc.mallchat.consumer.**.mapper"} )
public class MallChatServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallChatServerApplication.class, args);
    }
}
