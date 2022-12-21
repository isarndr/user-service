package com.codewithisa.userservice.config;

import com.codewithisa.userservice.scheduler.FirstScheduler;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

@Configuration
public class QuartzScheduler {
    private final ApplicationContext ctx;

    @Autowired
    public QuartzScheduler(ApplicationContext ctx){
        this.ctx = ctx;
    }

    @Bean
    public SpringBeanJobFactory springBeanJobFactory(){
        SpringBeanJobFactory jobFactory = new SpringBeanJobFactory();
        jobFactory.setApplicationContext(ctx);
        return jobFactory;
    }

    @Bean
    public SchedulerFactoryBean scheduler(){
        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
        schedulerFactory.setJobFactory(springBeanJobFactory());
        JobDetail[] jobs = {
                firstJob().getObject()
        };
        Trigger[] triggers = {
                firstTrigger().getObject()
        };
        schedulerFactory.setJobDetails(jobs);
        schedulerFactory.setTriggers(triggers);
        return schedulerFactory;
    }

    @Bean
    public JobDetailFactoryBean firstJob(){
        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        jobDetailFactoryBean.setJobClass(FirstScheduler.class);
        jobDetailFactoryBean.setName("First Job");
        jobDetailFactoryBean.setDescription("This is description for first job");
        jobDetailFactoryBean.setDurability(true);
        return jobDetailFactoryBean;
    }

    @Bean
    public CronTriggerFactoryBean firstTrigger(){
        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
        trigger.setJobDetail(firstJob().getObject());
        trigger.setName("First Trigger");
        trigger.setCronExpression("10 * * * * ? *");
        return trigger;
    }
}
