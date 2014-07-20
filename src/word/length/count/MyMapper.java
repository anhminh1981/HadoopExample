package word.length.count;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MyMapper extends Mapper<Object, Text, IntWritable, IntWritable> {
	private static final IntWritable ONE = new IntWritable(1);
	private IntWritable keyOut = new IntWritable();
	@Override
	protected void map(Object key, Text value, Context context)
			throws IOException, InterruptedException {
		for(String word: value.toString().split("\\W+")) {
			if(!word.isEmpty()) {
				keyOut.set(word.length());
				context.write(keyOut, ONE);
			}
		}
	}
	

}
