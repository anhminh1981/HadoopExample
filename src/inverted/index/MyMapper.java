package inverted.index;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MyMapper extends Mapper<Text, Text, Text, Text> {
	private Text keyOut = new Text();
	private Text valueOut = new Text();
	@Override
	protected void map(Text key, Text value, Context context)
			throws IOException, InterruptedException {
		Set<String> sentWords = new HashSet<String>() ;
		for(String word: value.toString().split("\\W+")) {
			if(!word.isEmpty() && ! sentWords.contains(word)) {
				sentWords.add(word);
				keyOut.set(word);
				valueOut.set(key);
				context.write(keyOut, valueOut);
			}
		}
	}

}
