package cn.iocoder.springboot.lab28.task.config;

import cn.iocoder.springboot.lab28.task.job.Job1;
import cn.iocoder.springboot.lab28.task.job.Job2;
import cn.iocoder.springboot.lab28.task.job.Job3;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
public class ScheduleConfiguration {



        @Bean
        public JobDetail job1() {
            return JobBuilder.newJob(Job1.class)
                    .withIdentity("job1") // 名字为 demoJob01
                    .storeDurably() // 没有 Trigger 关联的时候任务是否被保留。因为创建 JobDetail 时，还没 Trigger 指向它，所以需要设置为 true ，表示保留。
                    .usingJobData("k1", "v1")
                    .build();
        }

        @Bean
        public Trigger simpleJobTrigger() {
            // 简单的调度计划的构造器
            SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                    .withIntervalInSeconds(30) // 频率。
                    .repeatForever(); // 次数。
            // Trigger 构造器
            return TriggerBuilder.newTrigger()
                    .forJob(job1()) // 对应 Job 为 demoJob01
                    .withIdentity("job1Trigger") // 名字为 demoJob01Trigger
                    .withSchedule(scheduleBuilder) // 对应 Schedule 为 scheduleBuilder
                    .build();
        }


    @Bean
    public JobDetail job3() {
        return JobBuilder.newJob(Job3.class)
                .withIdentity("job3") // 名字为 demoJob01
                .storeDurably() // 没有 Trigger 关联的时候任务是否被保留。因为创建 JobDetail 时，还没 Trigger 指向它，所以需要设置为 true ，表示保留。
                .usingJobData("k1", "value1")
                .build();
    }

    @Bean
    public Trigger originJobTrigger() {
        // 简单的调度计划的构造器
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(10) // 频率。
                .repeatForever(); // 次数。
        // Trigger 构造器
        return TriggerBuilder.newTrigger()
                .forJob(job3()) // 对应 Job 为 demoJob01
                .withIdentity("job3Trigger") // 名字为 demoJob01Trigger
                .withSchedule(scheduleBuilder) // 对应 Schedule 为 scheduleBuilder
                .build();
    }


        @Bean
        public JobDetail job2() {
            return JobBuilder.newJob(Job2.class)
                    .withIdentity("job2") // 名字为 demoJob02
                    .storeDurably() // 没有 Trigger 关联的时候任务是否被保留。因为创建 JobDetail 时，还没 Trigger 指向它，所以需要设置为 true ，表示保留。
                    .build();
        }

        @Bean
        public Trigger cronJobTrigger() {
            // 简单的调度计划的构造器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0 0/1 * * * ? *");
            // Trigger 构造器
            return TriggerBuilder.newTrigger()
                    .forJob(job2()) // 对应 Job 为 demoJob02
                    .withIdentity("job2Trigger") // 名字为 demoJob02Trigger
                    .withSchedule(scheduleBuilder) // 对应 Schedule 为 scheduleBuilder
                    .build();
        }





    /**
     * 原生创建任务流程示例，有助于分析quartz实现原理
     * @throws SchedulerException
     */
    public static void test() throws SchedulerException {
        //1.创建Scheduler的工厂
        SchedulerFactory sf = new StdSchedulerFactory();
        //2.从工厂中获取调度器实例
        Scheduler scheduler = sf.getScheduler();
        //3.创建JobDetail
        JobDetail jb = JobBuilder.newJob(Job1.class)
                .withDescription("this is a  job") //job的描述
                .withIdentity("job1", "test-job") //job 的name和group
                .build();

        //任务运行的时间，SimpleSchedule类型触发器有效
        long time=  System.currentTimeMillis() + 3*1000L; //3秒后启动任务
        Date statTime = new Date(time);

        //4.创建Trigger
        //使用SimpleScheduleBuilder或者CronScheduleBuilder
        Trigger t = TriggerBuilder.newTrigger()
                .withDescription("")
                .withIdentity("job1Trigger", "job1TriggerGroup")
                //.withSchedule(SimpleScheduleBuilder.simpleSchedule())
                .startAt(statTime)  //默认当前时间启动
                .withSchedule(CronScheduleBuilder.cronSchedule("0/10 * * * * ?")) //两秒执行一次
                .build();

        //5.注册任务和定时器
        scheduler.scheduleJob(jb, t);//源码分析

        //6.启动 调度器
        scheduler.start();
    }

    public static void main(String[] args) throws SchedulerException {
        test();
    }

}
