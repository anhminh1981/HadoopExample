package inverted.index;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

public class FileWithNameRecordReader extends RecordReader<Text, Text> {
	private boolean done = false;
	private FileSplit fileSplit;
	private Configuration configuration;
	private String text;
	
	private static final Log LOG = LogFactory.getLog(FileWithNameRecordReader.class);
	
	@Override
	public void close() throws IOException {
		// Nothing to do
	}

	@Override
	public Text getCurrentKey() throws IOException, InterruptedException {
		String fileName = fileSplit.getPath().getName();
		LOG.info("Obtaining key: " + fileName);
		return new Text(fileName);
	}

	@Override
	public Text getCurrentValue() throws IOException, InterruptedException {
		return new Text(text);
	}

	@Override
	public float getProgress() throws IOException, InterruptedException {
		return done ? 1:0;
	}

	@Override
	public void initialize(InputSplit arg0, TaskAttemptContext arg1)
			throws IOException, InterruptedException {
		this.fileSplit = (FileSplit) arg0;
		this.configuration = arg1.getConfiguration();
	}

	@Override
	public boolean nextKeyValue() throws IOException, InterruptedException {
		if(done) {
			return false;
		}
		FileSystem fs = FileSystem.get(configuration);
		FSDataInputStream in = null;
		BufferedReader reader = null;
		try {
			in = fs.open(fileSplit.getPath());
			reader = new BufferedReader(new InputStreamReader(in));
			String line = reader.readLine();
			while (line != null) {
				this.text += line + "\n";
				line = reader.readLine();
			}
		} finally {
			IOUtils.closeStream(reader);
			IOUtils.closeStream(in);
		}
		done = true;
		return true;
	}

	

}
