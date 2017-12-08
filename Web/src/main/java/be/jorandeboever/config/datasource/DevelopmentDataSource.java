package be.jorandeboever.config.datasource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
public class DevelopmentDataSource {
    private static final Logger LOG = LogManager.getLogger();

    @Bean
    @Profile(value = "development")
    public DataSource dataSource(
            @Value("${application.db.owner.driver}") String driverClassname,
            @Value("${application.db.owner.user}") String username,
            @Value("${application.db.owner.password}") String password,
            @Value("${application.db.owner.url}") String url
    ) {
        LOG.info(() -> "Using development datasource");
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();

        dataSourceBuilder.driverClassName(driverClassname)
                .username(username)
                .password(password)
                .url(url);

        return dataSourceBuilder.build();
    }
}
