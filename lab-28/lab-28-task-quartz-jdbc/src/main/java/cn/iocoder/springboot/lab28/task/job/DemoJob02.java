package cn.iocoder.springboot.lab28.task.job;

import cn.iocoder.springboot.lab28.task.service.DemoService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

@DisallowConcurrentExecution
public class DemoJob02 extends QuartzJobBean {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DemoService demoService;

    @Override
    protected void executeInternal(JobExecutionContext context) {
        logger.info("[job2的执行了，时间: {}]", new Date());
    }


}
