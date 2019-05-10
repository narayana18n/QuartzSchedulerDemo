package com.avaya.scheduler.quartz.job;

import org.quartz.DateBuilder;
import org.quartz.DateBuilder.IntervalUnit;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
@DisallowConcurrentExecution
public class SampleJob extends QuartzJobBean {
	
	public static final Logger logger = LoggerFactory.getLogger(SampleJob.class);
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		logger.info(String.format("Job of type %s ran successfully...", this.getClass().getSimpleName()));
	}
	
	@Bean
    public JobDetail sampleJobDetail() {
        return JobBuilder.newJob(this.getClass()).withIdentity(this.getClass().getSimpleName())
                .storeDurably().build();
    }

    @Bean
    public Trigger sampleJobTrigger(JobDetail sampleJobDetail) {
        
        return TriggerBuilder.newTrigger().forJob(sampleJobDetail)
                .withIdentity("sampleJobTrigger")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                				.withIntervalInHours(1)
                				.repeatForever())
                .startAt(DateBuilder.futureDate(5, IntervalUnit.SECOND))
                .build();
    }

}
