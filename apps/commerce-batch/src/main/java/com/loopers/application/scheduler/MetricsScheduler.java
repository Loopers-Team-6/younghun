package com.loopers.application.scheduler;


import java.time.LocalDate;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MetricsScheduler {
  private final JobLauncher jobLauncher;
  private final Job weekJob;
  private final Job monthJob;

  public MetricsScheduler(JobLauncher jobLauncher, Job weekJob, Job monthJob) {
    this.jobLauncher = jobLauncher;
    this.weekJob = weekJob;
    this.monthJob = monthJob;
  }

  @Scheduled(cron = "0 50 23 * * *")
  public void runWeeklyJob() throws Exception {
    LocalDate date = LocalDate.now();
    JobParameters params = new JobParametersBuilder()
        .addString("date", date.toString())
        .addLong("time", System.currentTimeMillis())
        .toJobParameters();
    jobLauncher.run(weekJob, params);
  }

  @Scheduled(cron = "0 50 23 * * *")
  public void runMonthlyJob() throws Exception {
    LocalDate date = LocalDate.now();
    JobParameters params = new JobParametersBuilder()
        .addString("date", date.toString())
        .addLong("time", System.currentTimeMillis())
        .toJobParameters();
    jobLauncher.run(monthJob, params);
  }
}
