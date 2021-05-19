package com.site.plazam;

import com.site.plazam.repository.CinemaRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

//@SpringBootApplication (scanBasePackages=
//		"com.site.plazam.repository")
@SpringBootApplication
@EnableGlobalMethodSecurity(
		prePostEnabled = true,
		securedEnabled = true,
		jsr250Enabled = true)
//@EntityScan("com.site.plazam.domain")
//@EnableJpaRepositories("com.site.plazam.repository")
//@ComponentScan({"com.site.plazam.repository"})
public class PlazamApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(PlazamApplication.class, args);
	}

}

