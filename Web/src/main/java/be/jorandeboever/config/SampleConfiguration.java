package be.jorandeboever.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableAutoConfiguration
@EntityScan({"be.jorandeboever.domain"})
@EnableJpaRepositories({"be.jorandeboever.dao"})
@ComponentScan({"be.jorandeboever.services", "be.jorandeboever.controllers"})
public class SampleConfiguration {

}
