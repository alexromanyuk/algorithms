package io.cyb.graphs;

import java.util.Arrays;
import java.util.LinkedList;

import org.junit.Assert;
import org.junit.Test;

//TODO: rewrite to testng with dataprovider
public class GraphContractionTest {

	@Test
	public void testMerge() {
		LinkedList<LinkedList<Integer>> adjList = new LinkedList<LinkedList<Integer>>();
		adjList.add(vertices(1,2,3));
		adjList.add(vertices(2,1,3,4));
		adjList.add(vertices(3,1,2,4));
		adjList.add(vertices(4,2,3));
		
		LinkedList<LinkedList<Integer>> expected1 = new LinkedList<LinkedList<Integer>>();
		expected1.add(vertices(1,3,3,4));
		expected1.add(vertices(3,1,1,4));
		expected1.add(vertices(4,1,3));
		
		LinkedList<LinkedList<Integer>> expected2 = new LinkedList<LinkedList<Integer>>();
		expected2.add(vertices(1,3,3,3));
		expected2.add(vertices(3,1,1,1));
		
		GraphContraction gc = new GraphContraction(adjList);
		gc.merge(gc.getIndexByTailVertex(1), gc.getIndexByTailVertex(2));
		Assert.assertEquals(3, adjList.size());
		Assert.assertArrayEquals(expected1.get(0).toArray(), adjList.get(0).toArray());
		Assert.assertArrayEquals(expected1.get(1).toArray(), adjList.get(1).toArray());
		Assert.assertArrayEquals(expected1.get(2).toArray(), adjList.get(2).toArray());
		
		gc.merge(gc.getIndexByTailVertex(1), gc.getIndexByTailVertex(4));
		Assert.assertEquals(2, adjList.size());
		Assert.assertArrayEquals(expected2.get(0).toArray(), adjList.get(0).toArray());
		Assert.assertArrayEquals(expected2.get(1).toArray(), adjList.get(1).toArray());
	}
	
	@Test
	public void testContract() {
//(2)
//		1 2 4
//		2 1 3 4
//		3 2 4
//		4 1 2 3
		
		GraphContraction gc = null;
		LinkedList<LinkedList<Integer>> adjList = null;
		
		
			int minCut = Integer.MAX_VALUE;
			int contractResult = 0;
		
			//FIXME do deep copy of LL
			//FIXME move mincut into GraphContraction.minCut()
			for (int i=0; i<100; i++) {
				adjList = new LinkedList<LinkedList<Integer>>();
				adjList.add(vertices(1,2,4));
				adjList.add(vertices(2,1,3,4));
				adjList.add(vertices(3,2,4));
				adjList.add(vertices(4,1,2,3));

			    gc = new GraphContraction(adjList);
				contractResult = gc.contract();
				if (minCut > contractResult) {
					minCut = contractResult;
				} 
			}

			Assert.assertEquals(2, minCut);
//(2)
//		1 2 3 4
//		2 1 3 4 5
//		3 1 2 4 8
//		4 1 2 3
//		5 2 6 7 8 
//		6 5 7 8
//		7 5 6 8
//		8 3 5 6 7
		
		minCut = Integer.MAX_VALUE;
		contractResult = 0;

		for (int i=0; i<100; i++) {
			adjList = new LinkedList<LinkedList<Integer>>();
			adjList.add(vertices(1,2,3,4));
			adjList.add(vertices(2,1,3,4,5));
			adjList.add(vertices(3,1,2,4,8));
			adjList.add(vertices(4,1,2,3));
			adjList.add(vertices(5,2,6,7,8));
			adjList.add(vertices(6,5,7,8));
			adjList.add(vertices(7,5,6,8));
			adjList.add(vertices(8,3,5,6,7));

		    gc = new GraphContraction(adjList);
			contractResult = gc.contract();
			if (minCut > contractResult) {
				minCut = contractResult;
			} 
		}
		Assert.assertEquals(2, minCut);

//(1)
//		1 2 3 4 5
//		2 1 3 4 5
//		3 1 2 4 5
//		4 1 2 3 5
//		5 1 2 3 4 6
//		6 5 7 8 9 10 11
//		7 6 8 9 10 11
//		8 6 7 9 10 11
//		9 6 7 8 10 11
//		10 6 7 8 9 11
//		11 6 7 8 9 10

		minCut = Integer.MAX_VALUE;
		contractResult = 0;

		for (int i=0; i<100; i++) {
			adjList = new LinkedList<LinkedList<Integer>>();
			adjList.add(vertices(1,2,3,4,5));
			adjList.add(vertices(2,1,3,4,5));
			adjList.add(vertices(3,1,2,4,5));
			adjList.add(vertices(4,1,2,3,5));
			adjList.add(vertices(5,1,2,3,4,6));
			adjList.add(vertices(6,5,7,8,9,10,11));
			adjList.add(vertices(7,6,8,9,10,11));
			adjList.add(vertices(8,6,7,9,10,11));
			adjList.add(vertices(9,6,7,8,10,11));
			adjList.add(vertices(10,6,7,8,9,11));
			adjList.add(vertices(11,6,7,8,9,10));

		    gc = new GraphContraction(adjList);
			contractResult = gc.contract();
			if (minCut > contractResult) {
				minCut = contractResult;
			} 
		}
		Assert.assertEquals(1, minCut);

		
//(3)
//		1 2 3 4 5
//		2 1 3 4 5 6
//		3 1 2 4 5
//		4 1 2 3 5 9
//		5 1 2 3 4 12
//		6 2 7 8 9 10 11 12
//		7 6 8 9 10 11 12
//		8 6 7 9 10 11 12
//		9 4 6 7 8 10 11 12
//		10 6 7 8 9 11 12
//		11 6 7 8 9 10 12
//		12 5 6 7 8 9 10 11	
	
//(4)  
//		1 2 3 4 5 6 7 14
//		2 1 3 4 5 6 7 10 15
//		3 1 2 5 6 7 13 16
//		4 1 2 5 6 7 17
//		5 1 2 3 4 6 7 18
//		6 1 2 3 4 5 7 19
//		7 1 2 3 4 5 6 20
//		8 9 10 11 12 13 17
//		9 8 10 11 12 13
//		10 2 8 9 11 12 13
//		11 8 9 10 12 13 20
//		12 8 9 10 11 13
//		13 3 8 9 10 11 12
//		14 1 15 16 17 18 19 20
//		15 2 14 16 17 18 19 20
//		16 3 14 15 17 18 20
//		17 4 8 14 15 16 18 19 20
//		18 5 14 15 16 17 19 20
//		19 6 14 15 17 18 20
//		20 7 11 14 15 16 17 18 19
		
		minCut = Integer.MAX_VALUE;
		contractResult = 0;

		for (int i=0; i<100; i++) {
			adjList = new LinkedList<LinkedList<Integer>>();
			adjList.add(vertices(1,2,3,4,5,6,7,14));
			adjList.add(vertices(2,1,3,4,5,6,7,10,15));
			adjList.add(vertices(3,1,2,5,6,7,13,16));
			adjList.add(vertices(4,1,2,5,6,7,17));
			adjList.add(vertices(5,1,2,3,4,6,7,18));
			adjList.add(vertices(6,1,2,3,4,5,7,19));
			adjList.add(vertices(7,1,2,3,4,5,6,20));
			adjList.add(vertices(8,9,10,11,12,13,17));
			adjList.add(vertices(9,8,10,11,12,13));
			adjList.add(vertices(10,2,8,9,11,12,13));
			adjList.add(vertices(11,8,9,10,12,13,20));
			adjList.add(vertices(12,8,9,10,11,13));
			adjList.add(vertices(13,3,8,9,10,11,12));
			adjList.add(vertices(14,1,15,16,17,18,19,20));
			adjList.add(vertices(15,2,14,16,17,18,19,20));
			adjList.add(vertices(16,3,14,15,17,18,20));
			adjList.add(vertices(17,4,8,14,15,16,18,19,20));
			adjList.add(vertices(18,5,14,15,16,17,19,20));
			adjList.add(vertices(19,6,14,15,17,18,20));
			adjList.add(vertices(20,7,11,14,15,16,17,18,19));

		    gc = new GraphContraction(adjList);
			contractResult = gc.contract();
			if (minCut > contractResult) {
				minCut = contractResult;
			} 
		}
		Assert.assertEquals(4, minCut);
	}
	
	private LinkedList<Integer> vertices(Integer... a) {
		return new LinkedList<Integer>(Arrays.asList(a));
	}
}
