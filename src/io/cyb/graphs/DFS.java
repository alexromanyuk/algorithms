package io.cyb.graphs;

import java.util.ArrayList;

import gnu.trove.list.linked.TIntLinkedList;
import gnu.trove.stack.array.TIntArrayStack;

public class DFS {
	
	/**
	 *  1. zeroth element in TIntLinkedList is Vertex label,
	 *     so we could mark it as explored. 
	 *  2. row number in ArrayList is also Vertex number
	 */
	private ArrayList<TIntLinkedList> graph;
	private int finTimes;
	private int leader;
	
	private TIntArrayStack stack = new TIntArrayStack();
	
	public DFS(ArrayList<TIntLinkedList> graph) {
		this.graph = graph;
	}
	
	public void search(int s) {
		
		TIntLinkedList vertices = adjacentTo(s);
		markExploredVertex(s);		
		int v = 0;
		
		while (stack.size() > 0) {
			for (int i = 1; i < vertices.size(); i++) {
				v = vertices.get(i);
				if (isUnexplored(v)) {
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
		graph.get(s).set(v, v * (-1));
	}
	
	private boolean isUnexplored(int v) {
		return (v > 0);
	}
	
	private int backtrack() {
		return stack.pop();
	}
	
	private TIntLinkedList adjacentTo(int s) {
		return graph.get(s);
	}
	
	private void setLeader(int i) {}
	
	private void setFinalTime() {}
	
}
