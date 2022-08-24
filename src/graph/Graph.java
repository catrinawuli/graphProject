package graph;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;

public class Graph {
	
	class Vertice {
		private Set<String> neighbours;
		private boolean isVisited;
		
		Vertice() {this.isVisited = false; neighbours = new HashSet<String>();}
		public Set<String> getNeighbours() { return neighbours; }		
		public boolean isVisited () {return isVisited;}
		public void setVisit() {isVisited = true;}
	}

	private int edges;
	private Map<String, Vertice> adjacencyTable;
	private int numberOfCliques;
	private int numberOfComponentsWithAtLeastThreeVertices;

	public Graph() {
		adjacencyTable = new HashMap<String, Vertice>();
	}

	private void addVertice(String id, String neighbour) {
		if (!adjacencyTable.containsKey(id)) {
			adjacencyTable.put(id, new Vertice());
		}
		adjacencyTable.get(id).getNeighbours().add(neighbour);
	}
	
	private int dfs(String id, boolean isVisited, int count, Set<String> component) {
		int totalCount = count;
		if (!isVisited) {
			component.add(id);
			adjacencyTable.get(id).setVisit();
			totalCount = ++count;
			for (String neighbour : adjacencyTable.get(id).getNeighbours()) {
				boolean neighbourVisited = adjacencyTable.get(neighbour).isVisited();
				totalCount = dfs(neighbour, neighbourVisited, totalCount, component);
			}
		}
		return totalCount;
	}
	
	private void checkComponents() {
		for (Map.Entry<String, Vertice> entry : adjacencyTable.entrySet()) {
			String id = entry.getKey();
			boolean isVisited = entry.getValue().isVisited();
			if (!isVisited) {
				Set<String> component = new HashSet<String>();
				int totalCount = dfs(id, isVisited, 0, component);
				if (totalCount >= 3) {
					this.numberOfComponentsWithAtLeastThreeVertices++;
					
					if (component.stream().allMatch(n -> adjacencyTable.get(n).getNeighbours().size() == totalCount-1)) {
						this.numberOfCliques++;
					}
				
				}
			}
		}
	}
	
	public int getNumberOfCliques() {
		return this.numberOfCliques;
	}
	
	public int getNumberOfComponentsWithAtLeastThreeVertices() {
		return this.numberOfComponentsWithAtLeastThreeVertices;
	}

	public void addEdge(String id1, String id2) {
		addVertice(id1, id2);
		addVertice(id2, id1);
		this.edges++;
	}

	public int getVertices() {
		return this.adjacencyTable.size();
	}

	public int getEdges() {
		return this.edges;
	}

	public void histogram()	{
		Map<String, Integer> converted = this.adjacencyTable.entrySet().stream()
				.collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().getNeighbours().size()));
		
		System.out.println("\nHistogram by the first 10\n");
		for (int i = 1; i <= 10; i++) {
			System.out.printf("%-8s  %-10d%n", i, Collections.frequency(new ArrayList<Integer>(converted.values()), i));
		}
//		int max = 10; //return the first 10
//		int index = 1;
//		System.out.println("\nHistogram by the first " + max);
//		System.out.println("---------------------------------------------------------------");
//		for (Map.Entry<String, List<String>> entry : adjacencyTable.entrySet()) {
//			if (index++ > max) {
//				break;
//			}
//			System.out.printf("%-60s  %-10s%n", entry.getKey(), entry.getValue().size());
//		}
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
			graph.checkComponents();
			System.out.println("Total vertices: " + graph.getVertices() + " total edges: " + graph.getEdges() + " for first " + max + " records");
			graph.histogram();
			float a = graph.getNumberOfComponentsWithAtLeastThreeVertices();
			float b = graph.getNumberOfCliques();
			float c = b/a;
			
			System.out.println("\nThe number of components of G with at least three vertices is " + a);
			System.out.println("\nThe number of cliques in G is " + b);
			System.out.println("\nThe fraction of above components that are cliques is " + c);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}
