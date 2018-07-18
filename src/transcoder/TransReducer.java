package transcoder;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class TransReducer extends Reducer<Text, LongWritable, Text, LongWritable>{
	
	@Override
	protected void reduce(Text key, Iterable<LongWritable> values,Context context)
			throws IOException, InterruptedException {
		
		/////////////////////////////////
		//...
		/////////////////////////////////
		
		context.write(key, new LongWritable(1L));
	}
	
}
