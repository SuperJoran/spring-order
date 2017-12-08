package be.jorandeboever.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration
@EntityScan({"be.jorandeboever.domain"})
@EnableJpaRepositories({"be.jorandeboever.dao"})
@ComponentScan({"be.jorandeboever.services", "be.jorandeboever.controllers", "be.jorandeboever.config.datasource"})
@PropertySource(value = "classpath:configuration/${application.config}/application.properties", ignoreResourceNotFound = true)
@EnableTransactionManagement
@EnableAspectJAutoProxy
public class SampleConfiguration {

}
