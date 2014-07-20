package word.length.count;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;

public class MyReducer extends Reducer<IntWritable, IntWritable, IntWritable, IntWritable> {

	@Override
	protected void reduce(IntWritable key, Iterable<IntWritable> values,
			Context context)
			throws IOException, InterruptedException {
		int total = 0;
		for(IntWritable value: values) {
			total += value.get();
		}
		context.write(key, new IntWritable(total));
	}

	

}
