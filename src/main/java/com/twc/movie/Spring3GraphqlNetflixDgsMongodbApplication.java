package com.twc.movie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.web.reactive.config.EnableWebFlux;
@EnableWebFlux
@EnableReactiveMongoRepositories
@SpringBootApplication
public class Spring3GraphqlNetflixDgsMongodbApplication {

	public static void main(String[] args) {
		//BlockHound.install();
		SpringApplication.run(Spring3GraphqlNetflixDgsMongodbApplication.class, args);
	}

}
