package io.cyb.graphs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @author ARomanyuk
 */
public class GraphContraction {

	private final int SEED_SIZE = 200;
	
	/**
	 * Karger graph contraction algorithm
	 * @return min cuts
	 */
	public int contract(List<Integer[]> adjList) {
		while (adjList.size() > 2) {
			//FIXME
		}		
		return 0;
	}
	
	/**
	 * @return vertices v,u 
	 */
	private int[] chooseRandomEdge() { 
		Random rand = new Random(SEED_SIZE);
		return new int[] {rand.nextInt(), rand.nextInt()};
	}

	/**
	 * 1. merge v+u
	 * 2. change all adjacent vertices to point on v (rather than u).
	 * 3. remove self-loops
	 * 4. remove u 
	 */
	 void merge(int v, int u, LinkedList<LinkedList<Integer>> adjList) {
		LinkedList<Integer> vVertices = adjList.get(v);
		LinkedList<Integer> uVertices = adjList.get(u);
		vVertices.addAll(uVertices);
				
		for (Integer vertex : vVertices) {
			if (vertex == u) vertex = v;
			removeEdge(v, u, adjList.get(vertex));
		}
		
		//FIXME remove self-loops
		vVertices.removeAll(Arrays.asList(new int[]{v}));
		vVertices.addFirst(v);
		
		adjList.remove(u);
	}
	
	private void removeEdge(int v, int u, LinkedList<Integer> vertices) {
		for (Integer vertex : vertices) {
			if (vertex == u) vertex = v; 
		}
	}
		
	private static LinkedList<LinkedList<Integer>> readFile(String fileName) throws IOException {
		LinkedList<LinkedList<Integer>> adjList = new LinkedList<LinkedList<Integer>>();
		String line = null;
		
		File file = new File(fileName);
		BufferedReader reader = new BufferedReader(new FileReader(file));
		
		while (null != (line = reader.readLine())) {
			adjList.add(toList(line));
		}
		return adjList;
	}
	
	private static LinkedList<Integer> toList(String line) {
		LinkedList<Integer> vertices = new LinkedList<Integer>();
		for (String vertex :line.split("	")) {
			vertices.add(new Integer(vertex)); 
		}
		return vertices;
	}
	
	public static void main(String[] args) throws IOException {
		String path = new File(".").getCanonicalPath();
		LinkedList<LinkedList<Integer>> adjList = readFile(path+"/data/kargerMinCut.txt");
		System.out.println();
	}
}