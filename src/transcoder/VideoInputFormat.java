package transcoder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

public class VideoInputFormat extends FileInputFormat<Text, BytesWritable>{


	
	@Override
	public RecordReader<Text, BytesWritable> createRecordReader(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
		
		return new WholeFileRecordReader();
	}

	
	/**
	 * 读取整个切片为一个kv对
	 * @author
	 *
	 */
	public static class WholeFileRecordReader extends RecordReader<Text, BytesWritable>{

		Text key;
		BytesWritable value;
		FileSplit fileSplit;
		FileSystem fs ;
		FSDataInputStream inputStream;
		Path path;
		
		@Override
		public void initialize(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
			 fileSplit = (FileSplit) split;
			 path = fileSplit.getPath();

			 fs = FileSystem.get(context.getConfiguration());
			 inputStream = fs.open(path);
		}

		@Override
		public boolean nextKeyValue() throws IOException, InterruptedException {
			 
			 key.set(path.toString());
			 
			 ByteArrayOutputStream bo = new ByteArrayOutputStream();
			 IOUtils.copy(inputStream, bo);
			 byte[] videoBytes = bo.toByteArray();
			 IOUtils.closeQuietly(bo);
			 IOUtils.closeQuietly(inputStream);
			 
			 value.set(videoBytes, 0, videoBytes.length);
			 
			 
			return false;
		}

		@Override
		public Text getCurrentKey() throws IOException, InterruptedException {
			
			return this.key;
		}

		@Override
		public BytesWritable getCurrentValue() throws IOException, InterruptedException {

			return this.value;
		}

		@Override
		public float getProgress() throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void close() throws IOException {
			IOUtils.closeQuietly(inputStream);
			
		}
		
		
	}
	
	
}
