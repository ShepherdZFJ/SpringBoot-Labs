package cn.iocoder.springboot.lab28.task.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author fjzheng
 * @version 1.0
 * @date 2022/9/20 21:45
 */
public class Job3 implements Job {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private String k1;

    public void setK1(String k1) {
        this.k1 = k1;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println();
        logger.info("job3执行了，k1={}", k1);
    }
}
