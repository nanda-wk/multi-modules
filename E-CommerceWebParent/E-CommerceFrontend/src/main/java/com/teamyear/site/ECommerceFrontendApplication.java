package com.teamyear.site;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.teamyear.common.entity"})
public class ECommerceFrontendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ECommerceFrontendApplication.class, args);
	}

}