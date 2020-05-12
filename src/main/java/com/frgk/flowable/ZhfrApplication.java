package com.frgk.flowable;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;
@EnableTransactionManagement(proxyTargetClass = true)
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
//@ServletComponentScan
@MapperScan("com.frgk.flowable.dao")
public class ZhfrApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZhfrApplication.class, args);

    }


}
