package com.app.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(info=@Info(title="Auth Service"))
@SpringBootApplication()
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	} 
	
 	 protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	 return application.sources(Application.class);
	 }
}
