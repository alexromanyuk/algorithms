package io.cyb.graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import gnu.trove.list.array.TIntArrayList;
import gnu.trove.list.linked.TIntLinkedList;

public class DFS {
	
	/**
	 *  1. zeroth element in TIntLinkedList is Vertex label,
	 *     so we could mark it as explored. 
	 *  2. row number in ArrayList is also Vertex number
	 */
	private ArrayList<TIntArrayList> graph;
	private ArrayList<TIntArrayList> current;
	private Map<Integer, Integer> result = new HashMap<Integer, Integer>();
	
	private TIntLinkedList finTimes = new TIntLinkedList();
	private TIntLinkedList leaders = new TIntLinkedList();
	private int finTime;
	private int leader;
	
	public DFS(ArrayList<TIntArrayList> graph, ArrayList<TIntArrayList> graphRev) {
		this.graph = graph;
		this.current = graphRev;
		
		for (int i = 0; i< graph.size(); i++) {
			finTimes.add(0);
			leaders.add(0);
		}
	}
	
	public void search(int i) {
		
		TIntArrayList vertices = adjacentTo(i);
		markExploredVertex(i);
		setLeaderFor(i);
		int v = 0;
		
		for (int j = 1; j < vertices.size(); j++) {
			v = vertices.get(j);
			markExploredEdge(i, v);
			if (isUnexplored(v)) {
				search(v);
			}
		}
		
		setFinishTimeFor(i);
	}
	
	public void dfsLoop() {
		for (int s = current.size()-1; s >= 1; s--) {
			if (isUnexplored(s)) {
				leader = s;
				search(s);
			}
		}
		
		convertGraph();
		for (int s = current.size()-1; s >= 1; s--) {
			if (isUnexplored(s)) {
				leader = s;
				search(s);
			}
		}
		
		countSizeOfBiggestSCC();
	}
	
	private void countSizeOfBiggestSCC() {
		int count = 0;
		for (int i = 1; i < leaders.size(); i++) {
			count = result.get(leaders.get(i)) == null? 1 : result.get(leaders.get(i)) + 1;
			result.put(leaders.get(i), count);
		}
		
		Object[] arr = result.values().toArray();
		Arrays.sort(arr);
	}
	
	/** 
	 *  1. if vertex explored - add minus sign to it
	 *  2. push into stack
	 */
	private void markExploredVertex(int s) {
		current.get(s).set(0, s * (-1));
	}
	
	private void markExploredEdge(int s, int v) {
		int i = edgeVertexToIndex(s, v);
		current.get(s).set(i, v * (-1));
	}
	
	private boolean isUnexplored(int v) {
		return (v > 0) && (current.get(v).get(0) > 0);
	}
	
	private void convertGraph() {
		ArrayList<TIntArrayList> converted = new ArrayList<TIntArrayList>();
		TIntArrayList vertices = null;
		int head = 0;
		int tail = 0;
		
		for (int j = 1; j <= graph.size(); j++) {
			converted.add(new TIntArrayList());
		}
		
		for (int i = 1; i < graph.size(); i++) {
			vertices = graph.get(i);
			tail = convLabelToFinTime(i);
			converted.get(tail).add(tail);
			
			for (int j = 1; j < vertices.size(); j++) {
				head = vertices.get(j);
				head = convLabelToFinTime(head);
				converted.get(tail).add(head);
			}
		}
		
		current = converted;
	}
	
	private int convLabelToFinTime(int label) {
		return finTimes.get(Math.abs(label));
	}
	
	//FIXME: Trove stack bug?
//	private int backtrack() {
//		return stack.size() > 0? stack.pop() : 0;
//	}
	
	//FIXME: Trove indexOf bug? 
	private int edgeVertexToIndex(int s, int v) {
		return current.get(Math.abs(s)).indexOf(v);
	}
	
	private TIntArrayList adjacentTo(int s) {
		return current.get(s);
	}
	
	private void setLeaderFor(int i) {
		leaders.set(i, leader);
	}
	
	private void setFinishTimeFor(int i) {
		finTimes.set(i, ++finTime);
	}
	
}
