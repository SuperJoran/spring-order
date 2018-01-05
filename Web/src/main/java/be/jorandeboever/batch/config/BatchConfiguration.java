package be.jorandeboever.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
public class BatchConfiguration {

    private final JobLauncher jobLauncher;
    private final Job eventRenamingJob;

    public BatchConfiguration(JobLauncher jobLauncher, Job eventRenamingJob) {
        this.jobLauncher = jobLauncher;
        this.eventRenamingJob = eventRenamingJob;
    }

    @Scheduled(cron = "0 0/5 * 1/1 * ?")
    public void launch() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        this.jobLauncher.run(this.eventRenamingJob, new JobParametersBuilder().toJobParameters());
    }
}
