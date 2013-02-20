package io.cyb.sorting;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Arrays;

public class MergeSort {
	
	private long inversionCounter = 0;
	
	public void sort(double a[]) {
		if (a.length == 1) return;
		split(a, 0, a.length - 1);
		
		for(double el : a) {
			System.out.printf("%s ",(int)el);
		}
		
		System.out.printf("Inversions #: %s", inversionCounter);
	}
		
	private void split(double a[], int lo, int hi) {	
		int mid = Math.round((float)lo +(hi - lo)/ 2);		
		if (hi <= lo) return;
			split(a, lo, mid);
			split(a, mid + 1, hi);
		
		merge(a, lo, mid + 1, hi);
	}
	
	private void merge(double[] a, int lo, int mid, int hi) {
		int i = 0, j = 0;
		
		
		double[] b = Arrays.copyOfRange(a, lo, mid + 1);
		double[] c = Arrays.copyOfRange(a, mid, hi + 2);
		b[b.length-1] = Double.POSITIVE_INFINITY;
		c[c.length-1] = Double.POSITIVE_INFINITY;
				
		for (int k=lo; k < hi+1; k++) {
			if (b[i] < c[j]) {   
				a[k] = b[i];
				i++;
			} else
				if (c[j] < b[i]) {
					a[k] = c[j];					
					j++;
					inversionCounter += b.length - 1 - i;
				}		
		}
	}
	
	private static double[] readArray(File file) throws IOException {
				
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = null;
				
		LineNumberReader lnr = new LineNumberReader(new FileReader(file));
		lnr.skip(Long.MAX_VALUE);
		int arraySize = lnr.getLineNumber();
		double a[] = new double[arraySize];
		int i = 0;
		
		while (null != (line = br.readLine())) {			
			a[i++] = Double.parseDouble(line);
		}		
		
		return a;
	}
	
	public static void main(String[] args) throws IOException {
				
//		new MergeSort().sort(new double[] {6,5,4,3,2,1});
		new MergeSort().sort(readArray(new File("D:/Sasha/Dropbox/Public/research/src/stanford/part1/IntegerArray.txt")));
	}
}