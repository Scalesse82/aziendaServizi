package it.dst.azienda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class StarterClass {
	
	public static void main(String[] args) {
		SpringApplication.run(StarterClass.class, args);
	}

}
