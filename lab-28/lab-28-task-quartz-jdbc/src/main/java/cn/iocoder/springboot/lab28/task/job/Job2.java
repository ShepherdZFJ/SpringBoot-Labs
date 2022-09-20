package cn.iocoder.springboot.lab28.task.job;

import cn.iocoder.springboot.lab28.task.service.DemoService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

@DisallowConcurrentExecution
public class Job2 extends QuartzJobBean {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private static SimpleDateFormat fullDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @Override
    protected void executeInternal(JobExecutionContext context) {
        logger.info("[job2的执行了，时间: {}]", fullDateFormat.format(new Date()));
    }
}
