package dev.jian.CloneReddit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

import dev.jian.config.SwaggerConfiguration;

@SpringBootApplication
@EntityScan("dev.jian.model")
@ComponentScan("dev.jian")
@EnableJpaRepositories("dev.jian.repository")
@EnableAsync
@Import(SwaggerConfiguration.class)
public class CloneRedditApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloneRedditApplication.class, args);
	}

}
