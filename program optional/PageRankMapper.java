// Mapper for the pagerank problem

import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class PageRankMapper
extends Mapper<LongWritable, Text, Text, Text> {

	@Override
	public void map(LongWritable key, Text value, Context context)
	throws IOException, InterruptedException {

		String line = value.toString();
		String[] parts = line.split(" ");
		int lengthParts = parts.length;
		String pagename = parts[0];
		int numberOutlinks = lengthParts - 2;
		double pagerankVote = Double.parseDouble(parts[lengthParts - 1]) / numberOutlinks;
		String outlinks = "";
		for (int i = 1; i <= numberOutlinks; i++) {
			String currentPage = parts[i];
			String currentValue = pagename + ", " + pagerankVote;
			outlinks = outlinks + " " + currentPage;
			context.write(new Text(currentPage), new Text(currentValue));
		}
		context.write(new Text(pagename), new Text(outlinks.trim()));
	}
}