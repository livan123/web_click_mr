package transcoder;



import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.File;
import java.io.IOException;

public class TransMapper extends Mapper<LongWritable, Text, Text, LongWritable> {

	// mapreduce框架每读一行数据就调用一次该方法，key表示每一行的偏移量，value表示每一行的内容（即每个分片的地址）
	@Override
	protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		// 将value转换成string类型
		String path = value.toString();
		Runtime run = Runtime.getRuntime();// 返回与当前 Java 应用程序相关的运行时对象

		try {
			// 分片转换后的文件的名称（路径）
			String newPath = path.substring(0, path.lastIndexOf(".") + 1) + "flv";

			// 获取hdfs文件系统实例
			Configuration conf = new Configuration();
			FileSystem fs_down = FileSystem.get(conf);
			FileSystem fs_upload = FileSystem.get(conf);

			if (!fs_down.exists(new Path(newPath))) { // 判断该分片是否已被其他map转换（查询hdfs中newPath是否已经存在）
				/**
				 * 从HDFS上下载该分片到本地
				 */
				Path srcPath = new Path(path);
				String src_temp = "/home/hadoop/temp/temp." + path.substring(path.lastIndexOf("."));
				Path src_tempPath = new Path(src_temp);
				fs_down.copyToLocalFile(srcPath, src_tempPath);

				// 启动另一个进程来执行命令
				String target_temp = "/home/hadoop/temp/temp.flv";
				Process process = run
						.exec("ffmpeg -i " + src_temp + " -f flv -vcodec h264 -acodec aac -strict -2 " + target_temp);

				// 检查命令是否执行失败
				if (process.waitFor() != 0) {
					if (process.exitValue() == 1) {// 0表示正常结束，1非正常结束
						System.err.println("分片" + path + "转码失败!");
					}
				} else {
					/**
					 * 将转换后的分片再上传至HDFS
					 */
					Path target_tempPath = new Path(target_temp);
					Path dst = new Path("hdfs://hadoop-00:9000/splits/");
					try {
						fs_upload.copyFromLocalFile(target_tempPath, dst);
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						File src = new File(src_temp);
						File target = new File(target_temp);
						if(src.isFile()) {
							src.delete();
						}
						if(target.isFile()) {
							target.delete();
						}
					}
				}
			}
			
			context.write(new Text(path), new LongWritable(1L));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
