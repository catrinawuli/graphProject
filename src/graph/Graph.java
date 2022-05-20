package graph;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.zip.GZIPInputStream;

public class Graph {

	public static void main(String[] args) {
		try {
			InputStream fileStream = new FileInputStream("overlaps.m4.gz");
			InputStream gzipStream = new GZIPInputStream(fileStream);
			Reader decoder = new InputStreamReader(gzipStream, "ASCII");
			BufferedReader buffered = new BufferedReader(decoder);

			int max = 10000;
			String line;
			for (int index = 0; (line = buffered.readLine()) != null && index < max; index++) {
				String[] array = line.split("\\s+");
				String id1 = array[0];
				String id2 = array[1];
				int lengthOfFirstContig = Integer.parseInt(array[7]);
				int lengthOfSecondContig = Integer.parseInt(array[11]);
				if (lengthOfFirstContig >= 1000 && lengthOfSecondContig >= 1000) {
					System.out.println(id1 + " " + id2 + " " + lengthOfFirstContig + " " +lengthOfSecondContig);
				}
			}
			buffered.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}
