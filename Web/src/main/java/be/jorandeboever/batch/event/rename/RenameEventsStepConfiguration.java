package be.jorandeboever.batch.event.rename;

import be.jorandeboever.domain.Event;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.support.PostgresPagingQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class RenameEventsStepConfiguration {

    private final StepBuilderFactory stepBuilderFactory;

    @Autowired
    public RenameEventsStepConfiguration(StepBuilderFactory stepBuilderFactory) {
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    @Autowired
    public JdbcPagingItemReader<Event> reader(DataSource dataSource, PagingQueryProvider queryProvider) {
        JdbcPagingItemReader<Event> reader = new JdbcPagingItemReader<>();
        reader.setDataSource(dataSource);
        reader.setQueryProvider(queryProvider);
        reader.setPageSize(1000);
        reader.setFetchSize(100);
        reader.setRowMapper(new BeanPropertyRowMapper<>(Event.class));
        return reader;
    }

    @Bean
    public PostgresPagingQueryProvider queryProvider() {
        Map<String, Order> sortKeys = new HashMap<>();
        sortKeys.put("uuid", Order.ASCENDING);

        PostgresPagingQueryProvider queryProvider = new PostgresPagingQueryProvider();
        queryProvider.setSelectClause("select *");
        queryProvider.setFromClause("from SPR_EVENT event");
        queryProvider.setWhereClause("WHERE event.datetime < current_date - 14");
        queryProvider.setSortKeys(sortKeys);
        return queryProvider;
    }

    @Bean
    @Autowired
    public JdbcBatchItemWriter<Event> writer(DataSource dataSource) {
        JdbcBatchItemWriter<Event> writer = new JdbcBatchItemWriter<>();
        writer.setSql("UPDATE SPR_EVENT SET NAME = :name WHERE UUID = :uuid");
        writer.setDataSource(dataSource);
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        return writer;
    }

    @Bean
    @Autowired
    public Step renameEventStep(ItemReader<Event> reader, ItemProcessor<Event, Event> renameEventNameItemProcessor, ItemWriter<Event> writer) {
        return this.stepBuilderFactory.get("renameEventStep")
                .<Event, Event>chunk(100)
                .reader(reader)
                .processor(renameEventNameItemProcessor)
                .writer(writer)
                .build();
    }


}
