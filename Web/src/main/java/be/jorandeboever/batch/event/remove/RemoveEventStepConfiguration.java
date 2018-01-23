package be.jorandeboever.batch.event.remove;

import be.jorandeboever.domain.Event;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.PostgresPagingQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class RemoveEventStepConfiguration {

    private final StepBuilderFactory stepBuilderFactory;

    public RemoveEventStepConfiguration(StepBuilderFactory stepBuilderFactory) {
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    @Autowired
    public JdbcPagingItemReader<Event> emptyEventReader(DataSource dataSource, PostgresPagingQueryProvider emptyEventQueryProvider) {
        JdbcPagingItemReader<Event> reader = new JdbcPagingItemReader<>();
        reader.setQueryProvider(emptyEventQueryProvider);
        reader.setParameterValues(new HashMap<>());
        reader.setPageSize(1000);
        reader.setDataSource(dataSource);
        reader.setRowMapper(new BeanPropertyRowMapper<>(Event.class));

       return reader;
    }

    @Bean
    public PostgresPagingQueryProvider emptyEventQueryProvider() {
        Map<String, Order> sortKeys = new HashMap<>();
        sortKeys.put("datetime", Order.ASCENDING);

        PostgresPagingQueryProvider queryProvider = new PostgresPagingQueryProvider();
        queryProvider.setSelectClause("select event.*");
        queryProvider.setFromClause("from SPR_EVENT event");
        queryProvider.setWhereClause("WHERE 1 > (SELECT count(0)\n" +
                "           FROM SPR_FOOD_OPTION foodOption\n" +
                "             INNER JOIN spr_food_option_config config ON config.uuid = foodOption.configuration_uuid\n" +
                "           WHERE config.event_uuid = event.uuid)" +
                "   AND  event.datetime < current_date - 14");
        queryProvider.setSortKeys(sortKeys);

        return queryProvider;
    }


    @Bean
    @Autowired
    public Step removeEventStep(ItemReader<Event> emptyEventReader, ItemProcessor<Event, Event> removeEventItemProcessor, ItemWriter<Event> removeEventWriter) {
        return this.stepBuilderFactory.get("removeEventStep")
                .<Event, Event>chunk(100)
                .reader(emptyEventReader)
                .processor(removeEventItemProcessor)
                .writer(removeEventWriter)
                .build();
    }
}
