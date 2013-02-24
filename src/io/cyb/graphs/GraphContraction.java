package io.cyb.graphs;

import io.cyb.Utility;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

/**
 * @author ARomanyuk
 */
public class GraphContraction {
	LinkedList<LinkedList<Integer>> adjList;
	
	public GraphContraction(LinkedList<LinkedList<Integer>> adjList) {
		this.adjList = adjList;
	}
	
	/**
	 * Karger graph contraction algorithm
	 * @return possible # of minCuts
	 */
	public int contract() {
		int[] vu = null;
		while (adjList.size() > 2) {
			vu = chooseRandomEdge();
			merge(vu[0], vu[1]);
		}	
		//minCut candidate	
		return adjList.get(0).size() - 1;
	}
	
	/**
     * TODO: Refactor
	 * @return vertices v,u 
	 */
	private int[] chooseRandomEdge() { 
		Random rand = new Random();
		int v = rand.nextInt(adjList.size());
		
		//[1..size] get random cell data, then get index of row
		int u = rand.nextInt(adjList.get(v).size() -1) + 1;
		u = adjList.get(v).get(u);
		u = getIndexByTailVertex(u);
		
		return new int[] {v, u};
	}

	/**
	 * <ul><li>merge v+u</li>
	 *     <li>change all adjacent vertices to point on v (rather than u).</li>
	 *     <li>remove self-loops</li>
	 *     <li>remove u</li> 
     *
	 * @param v - row index 
	 * @param u - row index
	 * TODO: Refactor
	 */
	 void merge(int v, int u) {
		LinkedList<Integer> adjToV = null;
		LinkedList<Integer> vVertices = adjList.get(v);
		LinkedList<Integer> uVertices = adjList.get(u);
		vVertices.addAll(uVertices);
		
		//Indices to verticies:
		v = adjList.get(v).get(0);
		u = adjList.get(u).get(0);
		
		int curVertex = -1; 
		
		for (int i=1; i<vVertices.size(); i++) {
			curVertex = vVertices.get(i); 
			if (curVertex == u || curVertex == v) {
				// Remove self-loop
				vVertices.remove(i--);
			}
			else {
				adjToV = adjList.get(getIndexByTailVertex(curVertex));
				for (int j=1; j < adjToV.size(); j++) {
					if (adjToV.get(j) == u) {
						adjToV.set(j, v);
					}
				}
			}
		}
		
		vVertices.removeAll(Arrays.asList(new int[]{v}));		
		adjList.remove(getIndexByTailVertex(u));
	}
	 
	int getIndexByTailVertex(int vertex) {
		for (int i=0; i<=adjList.size(); i++) {
			if (adjList.get(i).get(0) == vertex) 
				return i;
		}
		throw new IllegalStateException();
	}
	
	public static void main(String[] args) throws IOException {
		String path = new File(".").getCanonicalPath();
		int minCut = Integer.MAX_VALUE;
		int contractResult = 0;

		// FIXME do deep copy of LL
		// FIXME move mincut into GraphContraction.minCut()
		for (int i = 0; i < 100; i++) {
			LinkedList<LinkedList<Integer>> adjList = Utility.readAdjacencyList(
					path + "/data/kargerMinCut.txt");

			GraphContraction gc = new GraphContraction(adjList);
			contractResult = gc.contract();
			if (minCut > contractResult) {
				minCut = contractResult;
			}
		}
		System.out.println("MinCut = "+minCut);
	}
}