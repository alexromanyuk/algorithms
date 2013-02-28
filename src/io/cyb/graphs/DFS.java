package io.cyb.graphs;

import java.util.ArrayList;

import gnu.trove.list.array.TIntArrayList;
import gnu.trove.list.linked.TIntLinkedList;
import gnu.trove.stack.array.TIntArrayStack;

public class DFS {
	
	/**
	 *  1. zeroth element in TIntLinkedList is Vertex label,
	 *     so we could mark it as explored. 
	 *  2. row number in ArrayList is also Vertex number
	 */
	private ArrayList<TIntArrayList> graph;
	private int finTimes;
	private int leader;
	
	private TIntArrayStack stack = new TIntArrayStack();
	
	public DFS(ArrayList<TIntArrayList> graph) {
		this.graph = graph;
	}
	
	public void search(int s) {
		
		TIntArrayList vertices = adjacentTo(s);
		markExploredVertex(s);		
		int v = 0;
		
		while (stack.size() > 0) {
			for (int i = 1; i < vertices.size(); i++) {
				v = vertices.get(i);
				if (isUnexplored(v)) {
				//FIXME: how to properly backtrack?
					markExploredEdge(s, v);
					search(v);
				}				
			}
		}
	}
	
	/** 
	 *  1. if vertex explored - add minus sign to it
	 *  2. push into stack
	 */
	private void markExploredVertex(int s) {
		stack.push(s);
		graph.get(s).set(0, s * (-1));
	}
	
	private void markExploredEdge(int s, int v) {
		int i = edgeVertexToIndex(s, v);
		graph.get(s).set(i, v * (-1));
	}
	
	private boolean isUnexplored(int v) {
		return (v > 0) && (graph.get(v).get(0) > 0);
	}
	
	private int backtrack() {
		return stack.pop();
	}
	
	//FIXME: Trove indexOf bug? 
	private int edgeVertexToIndex(int s, int v) {
		return graph.get(Math.abs(s)).indexOf(v);
	}
	
	private TIntArrayList adjacentTo(int s) {
		return graph.get(s);
	}
	
	private void setLeader(int i) {}
	
	private void setFinalTime() {}
	
}
