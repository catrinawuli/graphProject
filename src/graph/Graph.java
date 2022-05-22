package graph;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPInputStream;

public class Graph {

	private int vertices;

	private int edges;

	private Map<String, List<String>> adjacencyTable;

	private Set<String> componentsWithAtLeastThreeVertices;

	public Graph() {
		adjacencyTable = new HashMap<String, List<String>>();
		componentsWithAtLeastThreeVertices = new HashSet<String>();
	}

	private void addVertice(String id) {
		if (this.adjacencyTable.get(id) == null) {
			List<String> list = new ArrayList<String>();
			this.adjacencyTable.putIfAbsent(id, list);
			this.vertices++;
		}
	}

	public void addEdge(String id1, String id2) {
		addVertice(id1);
		addVertice(id2);
		this.adjacencyTable.get(id1).add(id2);
		this.adjacencyTable.get(id2).add(id1);
		if (this.adjacencyTable.get(id1).size() >= 3) {
			this.componentsWithAtLeastThreeVertices.add(id1);
		}
		if (this.adjacencyTable.get(id2).size() >= 3) {
			this.componentsWithAtLeastThreeVertices.add(id2);
		}
		this.edges++;
	}

	public int getVertices() {
		return this.vertices;
	}

	public int getEdges() {
		return this.edges;
	}

	public int getNumberOfComponentsWithAtLeastThreeVertices() {
		return this.componentsWithAtLeastThreeVertices.size();
	}

	public void histogram()	{
		int max = 10; //return the first 10
		int index = 1;
		System.out.println("\nHistogram by the first " + max);
		System.out.println("---------------------------------------------------------------");
		for (Map.Entry<String, List<String>> entry : adjacencyTable.entrySet()) {
			if (index++ > max) {
				break;
			}
			System.out.printf("%-60s  %-10s%n", entry.getKey(), entry.getValue().size());
		}
	}

	public static void main(String[] args) {
		try {
			Graph graph = new Graph();
			InputStream fileStream = new FileInputStream("overlaps.m4.gz");
			InputStream gzipStream = new GZIPInputStream(fileStream);
			Reader decoder = new InputStreamReader(gzipStream, "ASCII");
			BufferedReader buffered = new BufferedReader(decoder);

			int max = 1000000; // 1M
			String line;
			for (int index = 0; (line = buffered.readLine()) != null && index < max ; index++) {
				String[] array = line.split("\\s+");
				String id1 = array[0];
				String id2 = array[1];
				int lengthOfFirstContig = Integer.parseInt(array[7]);
				int lengthOfSecondContig = Integer.parseInt(array[11]);
				if (lengthOfFirstContig >= 1000 && lengthOfSecondContig >= 1000) {
					graph.addEdge(id1, id2);
				}
			}
			System.out.println("Total vertices: " + graph.getVertices() + " total edges: " + graph.getEdges() + " for first " + max + " records");
			graph.histogram();
			System.out.println("\nThe number of components of G with at least three vertices is " + graph.getNumberOfComponentsWithAtLeastThreeVertices());
			buffered.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}
