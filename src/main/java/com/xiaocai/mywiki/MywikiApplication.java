package com.xiaocai.mywiki;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 添加数据库扫描注解
 */


@ComponentScan("com.xiaocai")
@SpringBootApplication
@MapperScan("com.xiaocai.mywiki.mapper")
public class MywikiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MywikiApplication.class, args);
    }

}
