package com.codewithisa.userservice.scheduler;

import com.codewithisa.userservice.service.FirstServiceScheduler;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

@Slf4j
public class FirstScheduler extends QuartzJobBean {
    @Autowired
    public FirstServiceScheduler firstServiceScheduler;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        firstServiceScheduler.firstMethod();
    }
}
