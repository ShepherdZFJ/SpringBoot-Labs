package cn.iocoder.springboot.lab28.task;

import cn.iocoder.springboot.lab28.task.config.ScheduleConfiguration;
import cn.iocoder.springboot.lab28.task.job.Job1;
import cn.iocoder.springboot.lab28.task.job.Job2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class QuartzSchedulerTest {

    @Autowired
    private Scheduler scheduler;
    @Resource
    private ScheduleConfiguration scheduleConfiguration;

    @Test
    public void addDemoJob01Config() throws SchedulerException {
        // 创建 JobDetail
        JobDetail jobDetail = JobBuilder.newJob(Job1.class)
                .withIdentity("demoJob01") // 名字为 demoJob01
                .storeDurably() // 没有 Trigger 关联的时候任务是否被保留。因为创建 JobDetail 时，还没 Trigger 指向它，所以需要设置为 true ，表示保留。
                .build();
        // 创建 Trigger
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(5) // 频率。
                .repeatForever(); // 次数。
        Trigger trigger = TriggerBuilder.newTrigger()
                .forJob(jobDetail) // 对应 Job 为 demoJob01
                .withIdentity("demoJob01Trigger") // 名字为 demoJob01Trigger
                .withSchedule(scheduleBuilder) // 对应 Schedule 为 scheduleBuilder
                .build();
        // 添加调度任务
        scheduler.scheduleJob(jobDetail, trigger);
    }

    @Test
    public void addDemoJob02Config() throws SchedulerException {
        // 创建 JobDetail
        JobDetail jobDetail = JobBuilder.newJob(Job2.class)
                .withIdentity("demoJob02") // 名字为 demoJob02
                .storeDurably() // 没有 Trigger 关联的时候任务是否被保留。因为创建 JobDetail 时，还没 Trigger 指向它，所以需要设置为 true ，表示保留。
                .build();
        // 创建 Trigger
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/10 * * * * ? *");
        Trigger trigger = TriggerBuilder.newTrigger()
                .forJob(jobDetail) // 对应 Job 为 demoJob01
                .withIdentity("demoJob02Trigger") // 名字为 demoJob01Trigger
                .withSchedule(scheduleBuilder) // 对应 Schedule 为 scheduleBuilder
                .build();
        // 添加调度任务
        scheduler.scheduleJob(jobDetail, trigger);
//        scheduler.scheduleJob(jobDetail, Sets.newSet(trigger), true);
    }

    @Test
    public void test() throws SchedulerException {
        scheduleConfiguration.test();
    }

}
