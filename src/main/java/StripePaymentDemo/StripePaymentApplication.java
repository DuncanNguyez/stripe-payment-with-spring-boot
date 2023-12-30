package StripePaymentDemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableMongoRepositories
@EnableMongoAuditing
public class StripePaymentApplication implements ApplicationRunner {
	@Autowired
	ExampleObject exampleObject;
	public static void main(String[] args) {
		SpringApplication.run(StripePaymentApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		exampleObject.init();
	}
}
