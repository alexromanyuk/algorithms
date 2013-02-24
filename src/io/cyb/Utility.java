package io.cyb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class Utility {
	
	public static LinkedList<LinkedList<Integer>> readAdjacencyList(String fileName) throws IOException {
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
}