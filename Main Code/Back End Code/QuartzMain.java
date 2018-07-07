import org.quartz.TriggerBuilder;
import org.quartz.JobDetail;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.ScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzMain {
	
	public static void main(String[] args) throws SchedulerException{
		
		JobDetail job = JobBuilder.newJob(DownloadJob.class).build();
		JobDetail job1 = JobBuilder.newJob(chopData.class).build();
		//Trigger t1=TriggerBuilder.newTrigger().withIdentity("simple").startNow().build();
		Trigger t=TriggerBuilder.newTrigger().withIdentity("CronTrigger").withSchedule(CronScheduleBuilder.cronSchedule("0 0/1 * 1/1 * ? *")).build();
		Trigger t1=TriggerBuilder.newTrigger().withIdentity("CronTri").withSchedule(CronScheduleBuilder.cronSchedule("0 0/2 * 1/1 * ? *")).build();
		Scheduler sc=StdSchedulerFactory.getDefaultScheduler();
		sc.start();
		sc.scheduleJob(job, t);
		sc.scheduleJob(job1, t1);
		
	}
}