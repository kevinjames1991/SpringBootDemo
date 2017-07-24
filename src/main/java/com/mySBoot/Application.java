package com.mySBoot;  
  
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;  
  
@MapperScan(basePackages = "${mybatis.mapperBeanPackage:com.mySBoot.**.dao}")
@SpringBootApplication  
public class Application {  
  
    public static void main(String[] args) {
    	SpringApplication application = new SpringApplication(Application.class);
    	application.setBannerMode(Banner.Mode.OFF);
    	application.run(args);
//        SpringApplication.run(Application.class, args);  
    }  
}  