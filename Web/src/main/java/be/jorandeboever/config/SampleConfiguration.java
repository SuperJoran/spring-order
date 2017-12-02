package be.jorandeboever.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;

@Configuration
@EnableAutoConfiguration
@EntityScan({"be.jorandeboever.domain"})
@EnableJpaRepositories({"be.jorandeboever.dao"})
@ComponentScan({"be.jorandeboever.services", "be.jorandeboever.controllers"})
public class SampleConfiguration {
    @Bean
    public DataSource dataSource(
            @Value("${application.db.owner.driver}") String driverClassname,
            @Value("${application.db.owner.user}") String username,
            @Value("${application.db.owner.password}") String password,
            @Value("${application.db.owner.url}") String url
    ) {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();

        dataSourceBuilder.driverClassName(driverClassname)
                .username(username)
                .password(password)
                .url(url);

        return dataSourceBuilder.build();
    }
}
