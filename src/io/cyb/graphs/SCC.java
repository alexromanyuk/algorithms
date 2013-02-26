package io.cyb.graphs;

/**
 * Strongly connected components
 * 
 * @author ARomanyuk
 */
public class SCC {
	/*
	 * The file contains the edges of a directed graph. Vertices are labeled as positive integers from 1 to 875714.
	 * Every row indicates an edge, the vertex label in first column is the tail and the vertex label in second column 
	 * is the head (recall the graph is directed, and the edges are directed from the first column vertex to the second 
	 * column vertex). So for example, the 11th row looks liks : "2 47646". This just means that the vertex with label 2 
	 * has an outgoing edge to the vertex with label 47646
	 * 
	 * Your task is to code up the algorithm from the video lectures for computing strongly connected components (SCCs), 
	 * and to run this algorithm on the given graph.
	 * 
	 * Output Format: You should output the sizes of the 5 largest SCCs in the given graph, in decreasing order of sizes,
	 *  separated by commas (avoid any spaces). So if your algorithm computes the sizes of the five largest SCCs to be 
	 *  500, 400, 300, 200 and 100, then your answer should be "500,400,300,200,100". If your algorithm finds less than 
	 *  5 SCCs, then write 0 for the remaining terms. Thus, if your algorithm computes only 3 SCCs whose sizes are 
	 *  400, 300, and 100, then your answer should be "400,300,100,0,0".
	 */
	
	 /**
	  *  1. Use Trove lib for primitive Java collections
	  * 
	  *  2. once you have an answer, run your program on Grev. It should be exactly the same, since G and Grev have exactly 
	  *  the same SCC's. If there's something wrong on your program, it is very unlikely to obtain the same answers in both 
	  *  runnings.
	  *  
	  *  3. how to reverse a graph
	  *  -- topological sort
	  *  http://stackoverflow.com/questions/595029/seeking-algorithm-to-invert-reverse-mirror-turn-inside-out-a-dag
	  *  -- when you create the graph from the file, the file is conveniently in a format that you could create a reversed graph if you want.
	  *
	  *  4. -Xms512m -Xmx1024m -Xss1m
	  *     try to tune up Xss, or remove recursion, if not enough Stack size.
	  *  
	  */
}
