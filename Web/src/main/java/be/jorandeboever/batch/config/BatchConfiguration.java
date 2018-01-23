package be.jorandeboever.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class BatchConfiguration {

    private final JobLauncher jobLauncher;
    private final Job archiveEventsJob;

    public BatchConfiguration(JobLauncher jobLauncher, Job archiveEventsJob) {
        this.jobLauncher = jobLauncher;
        this.archiveEventsJob = archiveEventsJob;
    }

    @Scheduled(cron = "0 0/1 * 1/1 * ?")
    public void launchArchiveEventsJob() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("date", DateTimeFormatter.ofPattern("yyyy-MM-dd-hh-mm-ss").format(LocalDateTime.now()))
                .toJobParameters();

        this.jobLauncher.run(this.archiveEventsJob, jobParameters);
    }
}
