package be.jorandeboever.batch.event;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ArchiveEventsJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;

    public ArchiveEventsJobConfiguration(JobBuilderFactory jobBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
    }

    @Bean
    public Job archiveEventsJob(Step renameEventStep, Step removeEventStep) throws Exception {
        return this.jobBuilderFactory.get("eventRenamingJob")
                .incrementer(new RunIdIncrementer())
                .start(removeEventStep)
                .next(renameEventStep)
                .build();
    }
}
