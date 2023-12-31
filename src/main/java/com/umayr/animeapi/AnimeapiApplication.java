package com.umayr.animeapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@EnableReactiveMongoRepositories
@SpringBootApplication
public class AnimeapiApplication {
	public static void main(String[] args) {
		SpringApplication.run(AnimeapiApplication.class, args);
	}
}
