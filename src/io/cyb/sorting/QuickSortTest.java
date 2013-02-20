package io.cyb.sorting;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import io.cyb.sorting.QuickSort.PivotStrategy;

//TODO: cleanup
public class QuickSortTest {
	
	public QuickSortTest() throws IOException {}
	
	QuickSort qs = new QuickSort();

	private int[] tenOrd = {1,2,3,4,5,6,7,8,9,10};
	private int[] tenOrdRes = {45, 45, 19}; 
	
	private int[] tenRev = {10,9,8,7,6,5,4,3,2,1};
	private int[] tenRevRes = {45, 45, 19};
	
	private int[] hundOrd = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100};
	private int[] hundOrdRes = {4950, 4950, 480};
	
	private int[] hundRev = {100,99,98,97,96,95,94,93,92,91,90,89,88,87,86,85,84,83,82,81,80,79,78,77,76,75,74,73,72,71,70,69,68,67,66,65,64,63,62,61,60,59,58,57,56,55,54,53,52,51,50,49,48,47,46,45,44,43,42,41,40,39,38,37,36,35,34,33,32,31,30,29,28,27,26,25,24,23,22,21,20,19,18,17,16,15,14,13,12,11,10,9,8,7,6,5,4,3,2,1};
	private int[] hundRevRes = {4950, 4950, 1302}; 
	
	private int[] array = {2, 8, 9, 3, 7, 5, 10, 1, 6, 4};
	private int[] arrayRes = {25, 20, 19}; 
	
	String path = new File(".").getCanonicalPath() + "data/IntegerArray.txt";
	private int[] arrayA1 = io.cyb.Utility.readIntArray(path);
	private int[] arrayA1Res = {1954287, 2047196, 1697653};
	
	//----------------------------------------------
	
	int[] a = //{{0, 9, 8, 7, 6, 5, 4, 3, 2, 1},{25}, // [0][], [1][]
//				 {0, 1, 2, 3, 4, 5, 6, 7, 8, 9},{19}, // [2][], [3][]
//				 {1, 11, 5, 15, 2, 12, 9, 99, 77, 0},{22}, //[4][], [5][]
//				 {999, 3, 2, 98, 765, 8, 14, 15, 16, 88, 145, 100},{29},//[6][], [7][]
				 {1, 11, 5, 15, 2, 999, 3, 2, 98, 765, 8, 14, 15, 16, 88, 145, 100, 12, 9, 99, 77, 0};//, {82} //[8][], [9][]
//				};
	int aExpected = 82;
	
	@Test
	public void testMediumOfThreePivot() {
		qs.setPivotStrategy(PivotStrategy.MEDIAN);
		qs.sort(a);
		Assert.assertEquals(aExpected, qs.getComparisons());		
	}
	
	@Test
	public void testSortLeftPivot() {
		qs.setPivotStrategy(PivotStrategy.LEFT);
		testSort();
	}
	
	@Test
	public void testSortRightPivot() {
		qs.setPivotStrategy(PivotStrategy.RIGHT);
		testSort();
	}
	
	@Test
	public void testSortMedianPivot() {
		qs.setPivotStrategy(PivotStrategy.MEDIAN);
		testSort();
	}
	
	public void testSort() {
		int[] expected = tenOrd.clone();
		Arrays.sort(expected);
		Assert.assertArrayEquals("ten ordered ", expected, qs.sort(tenOrd));
		
		expected = tenRev.clone();
		Arrays.sort(expected);
		Assert.assertArrayEquals("ten rev ", expected, qs.sort(tenRev));
		
		expected = hundOrd.clone();
		Arrays.sort(expected);
		Assert.assertArrayEquals("hund ord ", expected, qs.sort(hundOrd));
		
		expected = hundRev.clone();
		Arrays.sort(expected);
		Assert.assertArrayEquals("hund rev ", expected, qs.sort(hundRev));
		
		expected = array.clone();
		Arrays.sort(expected);
		Assert.assertArrayEquals("array ", expected, qs.sort(array));
	}
	
	@Test
	public void testComparisonsLeftPivot() {
		qs.setPivotStrategy(PivotStrategy.LEFT);
		
		qs.sort(tenOrd);
		Assert.assertEquals(tenOrdRes[0], qs.getComparisons());
		
		qs.sort(tenRev);
		Assert.assertEquals(tenRevRes[0], qs.getComparisons());
		
		qs.sort(hundOrd);
		Assert.assertEquals(hundOrdRes[0], qs.getComparisons());
		
		qs.sort(hundRev);
		Assert.assertEquals(hundRevRes[0], qs.getComparisons());
		
		qs.sort(array);
		Assert.assertEquals(arrayRes[0], qs.getComparisons());
		
		qs.sort(arrayA1);
		Assert.assertEquals(arrayA1Res[0], qs.getComparisons());
	}
	
	@Test
	public void testComparisonsRightPivot() {
		qs.setPivotStrategy(PivotStrategy.RIGHT);
		
		qs.sort(tenOrd);
		Assert.assertEquals("ten ord",tenOrdRes[1], qs.getComparisons());
		
		qs.sort(tenRev);
		Assert.assertEquals("ten rev", tenRevRes[1], qs.getComparisons());
		
		qs.sort(hundOrd);
		Assert.assertEquals("hund ord", hundOrdRes[1], qs.getComparisons());
		
		qs.sort(hundRev);
		Assert.assertEquals(hundRevRes[1], qs.getComparisons());
		
		qs.sort(array);
		Assert.assertEquals(arrayRes[1], qs.getComparisons());
		
		qs.sort(arrayA1);
		Assert.assertEquals(arrayA1Res[1], qs.getComparisons());
	}
	
	@Test
	public void testComparisonsMedianPivot() {
		qs.setPivotStrategy(PivotStrategy.MEDIAN);
		
		qs.sort(tenOrd);
		Assert.assertEquals(tenOrdRes[2], qs.getComparisons());
		
		qs.sort(tenRev);
		Assert.assertEquals(tenRevRes[2], qs.getComparisons());
		
		qs.sort(hundOrd);
		Assert.assertEquals(hundOrdRes[2], qs.getComparisons());
		
		qs.sort(hundRev);
		Assert.assertEquals(hundRevRes[2], qs.getComparisons());
		
		qs.sort(array);
		Assert.assertEquals(arrayRes[2], qs.getComparisons());
		
		qs.sort(arrayA1);
		Assert.assertEquals(arrayA1Res[2], qs.getComparisons());
	}
}