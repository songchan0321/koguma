package com.fiveguys.koguma;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class KogumaApplication {

	public static void main(String[] args) {
		SpringApplication.run(KogumaApplication.class, args);
	}

}
