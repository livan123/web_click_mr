package cn.bobo.hive.hive.mr.pre;

import cn.bobo.hive.hive.mrbean.WebLogBean;
import cn.bobo.hive.hive.mrbean.WebLogParser;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


/**
 * 处理原始日志，过滤出真实pv请求
 * 转换时间格式
 * 对缺失字段填充默认值
 * 对记录标记valid和invalid
 * 
 * @author
 *
 */

public class WeblogPreProcess {
	static class WeblogPreProcessMapper 
		extends Mapper<LongWritable, Text, Text, NullWritable> {
		//用来存储网站url分类数据
		Set<String> pages = new HashSet<String>();
		Text k = new Text();
		NullWritable v = NullWritable.get();
		/**
		 * 从外部加载网站url分类数据
		 */
		@Override
		protected void setup(Context context) 
				throws IOException, InterruptedException {
			pages.add("/about");
			pages.add("/black-ip-list/");
			pages.add("/cassandra-clustor/");
			pages.add("/finance-rhive-repurchase/");
			pages.add("/hadoop-family-roadmap/");
			pages.add("/hadoop-hive-intro/");
			pages.add("/hadoop-zookeeper-intro/");
			pages.add("/hadoop-mahout-roadmap/");
		}
		@Override
		protected void map(LongWritable key, Text value, Context context) 
				throws IOException, InterruptedException {
			String line = value.toString();
			WebLogBean webLogBean = WebLogParser.parser(line);
			// 过滤js/图片/css等静态资源
			WebLogParser.filtStaticResource(webLogBean, pages);
			/* if (!webLogBean.isValid()) return; */
			k.set(webLogBean.toString());
			context.write(k, v);
		}
	}
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);
		job.setJarByClass(WeblogPreProcess.class);
		job.setMapperClass(WeblogPreProcessMapper.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(NullWritable.class);
//		 FileInputFormat.setInputPaths(job, new Path(args[0]));
//		 FileOutputFormat.setOutputPath(job, new Path(args[1]));
		FileInputFormat.setInputPaths(job, new Path("d:/app/weblog"));
		FileOutputFormat.setOutputPath(job, new Path("d:/weblog/output"));
		job.setNumReduceTasks(0);
		job.waitForCompletion(true);
	}
}
