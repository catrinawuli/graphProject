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

			String line;
			while ((line = buffered.readLine())!= null) {
				System.out.println(line);
			}
			buffered.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}
