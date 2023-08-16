package com.hanium.diARy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.hanium.diARy", "com.hanium.diARy.auth"})
public class DiARyApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiARyApplication.class, args);
	}

}
