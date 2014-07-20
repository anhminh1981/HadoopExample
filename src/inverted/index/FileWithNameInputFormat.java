package inverted.index;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

public class FileWithNameInputFormat extends FileInputFormat<Text, Text> {

	@Override
	public FileWithNameRecordReader createRecordReader(InputSplit arg0,
			TaskAttemptContext arg1) throws IOException, InterruptedException {
		FileWithNameRecordReader fileRecordReader = new FileWithNameRecordReader();
//		fileRecordReader.initialize(arg0, arg1);
		return fileRecordReader;
	}

}
