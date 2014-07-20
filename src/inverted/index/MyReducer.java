package inverted.index;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class MyReducer extends Reducer<Text, Text, Text, Text> {

	@Override
	protected void reduce(Text arg0, Iterable<Text> arg1, Context arg2)
			throws IOException, InterruptedException {
		ArrayList<Text> list = new ArrayList<>();
		for(Text value: arg1) {
			list.add(value);
		}
		arg2.write(arg0, new Text(list.toString()));
	}

}
