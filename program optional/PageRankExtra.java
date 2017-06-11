// Pagerank extra

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class PageRankExtra {
	public static void main(String[] args) throws Exception {
		if (args.length != 2) {
			System.err.println("Usage: PageRankExtra <input path> <output path>");
			System.exit(-1);
		}
		for (int i = 1; i <= 3; i++) {
			Job job = new Job();
			job.setNumReduceTasks(1);
			job.setJarByClass(PageRankExtra.class);
			job.setJobName("PageRankExtra" + i);
			Configuration conf = job.getConfiguration();
			conf.set("mapreduce.output.textoutputformat.separator", " ");
			
			if (i == 1) {
				FileInputFormat.addInputPath(job, new Path(args[0]));
				FileOutputFormat.setOutputPath(job, new Path(args[1] + "/tmp1"));
			}
			else if (i == 2) {
				FileInputFormat.addInputPath(job, new Path(args[1] + "/tmp1/part-r-00000"));
				FileOutputFormat.setOutputPath(job, new Path(args[1] + "/tmp2"));
			}
			else {
				FileInputFormat.addInputPath(job, new Path(args[1] + "/tmp2/part-r-00000"));
				FileOutputFormat.setOutputPath(job, new Path(args[1] + "/result"));
			}
			
			job.setMapperClass(PageRankMapper.class);
			job.setReducerClass(PageRankReducer.class);
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(Text.class);
			job.waitForCompletion(true);
		}
	}
}