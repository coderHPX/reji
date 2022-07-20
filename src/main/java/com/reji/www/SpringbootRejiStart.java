package com.reji.www;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class SpringbootRejiStart {
    public static void main(String[] args) {
        SpringApplication.run(SpringbootRejiStart.class,args);
    }
}
