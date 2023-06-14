package com.quadrangle.depofenvkmla;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.session.data.mongo.JacksonMongoSessionConverter;

@SpringBootApplication
public class DepofenvkmlaApplication {
	public static void main(String[] args) {
		SpringApplication.run(DepofenvkmlaApplication.class, args);
	}

	@Bean
	JacksonMongoSessionConverter mongoSessionConverter() {
    	return new JacksonMongoSessionConverter();
	}
}
