// Reducer for the pagerank problem

import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class PageRankReducer
extends Reducer<Text, Text, Text, Text> {

	@Override
	public void reduce(Text key, Iterable<Text> values, Context context)
	throws IOException, InterruptedException {
		String outlinks = "";
		Double newPagerank = 0.000000;
		for (Text value : values) {
			String part = value.toString();
			if (part.contains(", ")) {
				newPagerank += Double.parseDouble(part.substring(3));
			}
			else {
				outlinks = part + " ";
			}
		}
		String result = outlinks + newPagerank;
		context.write(key, new Text(result));
	}
}