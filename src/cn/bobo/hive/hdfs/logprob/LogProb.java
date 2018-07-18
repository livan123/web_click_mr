package cn.bobo.hive.hdfs.logprob;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;

/**
 * 
 * @author
 * 
 */
public class LogProb {

	public static void main(String[] args) throws Exception {
		Runnable runnable = new Runnable() {
			public void run() {
				// task to run goes here
				System.out.println("Hello !!");
			}
		};
		
		Runnable runnable2 = new Runnable() {
			public void run() {
				// task to run goes here
				System.out.println("Hello222 !!");
			}
		};
		
		
		
		ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
		service.scheduleAtFixedRate(runnable2,8,1,TimeUnit.SECONDS);
		// 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
		service.scheduleAtFixedRate(runnable, 5, 2, TimeUnit.SECONDS);
	}

}
