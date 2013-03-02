package io.cyb.graphs;

import java.util.ArrayList;
import java.util.LinkedList;

import javax.print.attribute.standard.Finishings;

import gnu.trove.list.array.TIntArrayList;
import gnu.trove.list.linked.TIntLinkedList;

public class DFS {
	
	/**
	 *  1. zeroth element in TIntLinkedList is Vertex label,
	 *     so we could mark it as explored. 
	 *  2. row number in ArrayList is also Vertex number
	 */
	private ArrayList<TIntArrayList> graph;
	private TIntLinkedList finTimes = new TIntLinkedList();
	private TIntLinkedList leaders = new TIntLinkedList();
	private int finTime;
	private int leader;
	
	public DFS() {
		for (int i = 0; i< graph.size(); i++) {
			finTimes.add(0);
			leaders.add(0);
		}
	}
	
	//FIXME: leaders
	public void search(ArrayList<TIntArrayList> graph, int i) {
		this.graph = graph;
		
		
		TIntArrayList vertices = adjacentTo(i);
		markExploredVertex(i);		
		int v = 0;

		for (int j = 1; j < vertices.size(); j++) {
			v = vertices.get(j);
			markExploredEdge(i, v);
			setLeaderFor(j);
			if (isUnexplored(v)) {
				search(graph, v);
			}
		}
		
		setFinishTimeFor(i);
	}
	
	public void dfsLoop(ArrayList<TIntArrayList> graph, ArrayList<TIntArrayList> graphReverse) {
		for (int s = graph.size()-1; s >= 1; s--) {
			if (isUnexplored(s)) {
				leader = s;
				search(graph, s);
			}
		}
	}
	
	/** 
	 *  1. if vertex explored - add minus sign to it
	 *  2. push into stack
	 */
	private void markExploredVertex(int s) {
		graph.get(s).set(0, s * (-1));
	}
	
	private void markExploredEdge(int s, int v) {
		int i = edgeVertexToIndex(s, v);
		graph.get(s).set(i, v * (-1));
	}
	
	private boolean isUnexplored(int v) {
		return (v > 0) && (graph.get(v).get(0) > 0);
	}
	
	private int convertLabelToFinishTime(int label) {
		return finTimes.get(label);
	}
	
	//FIXME: Trove stack bug?
//	private int backtrack() {
//		return stack.size() > 0? stack.pop() : 0;
//	}
	
	//FIXME: Trove indexOf bug? 
	private int edgeVertexToIndex(int s, int v) {
		return graph.get(Math.abs(s)).indexOf(v);
	}
	
	private TIntArrayList adjacentTo(int s) {
		return graph.get(s);
	}
	
	private void setLeaderFor(int i) {
		leaders.set(i, leader);
	}
	
	private void setFinishTimeFor(int i) {
		finTimes.set(i, ++finTime);
	}
	
}
