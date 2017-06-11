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

		Path inPath = new Path(args[0]);
		Path outPath =  null;
		for (int i = 1; i <= 3; i++) {
			outPath = new Path(args[1]+i);
			Job job = new Job();
			job.setNumReduceTasks(1);
			job.setJarByClass(PageRankExtra.class);
			Configuration conf = job.getConfiguration();
			conf.set("mapreduce.output.textoutputformat.separator", " ");
			FileInputFormat.addInputPath(job, inPath);
    		FileOutputFormat.setOutputPath(job, outPath);
			job.setMapperClass(PageRankMapper.class);
			job.setReducerClass(PageRankReducer.class);
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(Text.class);
			job.waitForCompletion(true);
			inPath = outPath;
		}
	}
}