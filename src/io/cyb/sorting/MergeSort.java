package io.cyb.sorting;

import java.util.Arrays;

/**
 * @author ARomanyuk
 */
public class MergeSort {
	
	private long inversionCounter = 0;
	
	public void sort(double a[]) {
		if (a.length == 1) return;
		split(a, 0, a.length - 1);
	}
	
	public long getInversionCount() {
		return inversionCounter;
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
}