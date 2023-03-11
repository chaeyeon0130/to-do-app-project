package com.codestates;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Section3Week3TemplateTxApplication {

	public static void main(String[] args) {
		System.out.println("Hello World");
		SpringApplication.run(Section3Week3TemplateTxApplication.class, args);
	}

}
