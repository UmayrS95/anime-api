package com.umayr.animeapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@EnableReactiveMongoRepositories
@SpringBootApplication
public class AnimeapiApplication {
	public static void main(String[] args) {
		SpringApplication.run(AnimeapiApplication.class, args);
	}
}
