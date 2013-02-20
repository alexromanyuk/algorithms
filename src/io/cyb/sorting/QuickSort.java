package io.cyb.sorting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import static io.cyb.sorting.QuickSort.PivotStrategy.*;

/**
 * @author ARomanyuk
 */
public class QuickSort {
	private int a[];
	private int comparisons;
	private PivotStrategy strategy;
	
	public enum PivotStrategy {LEFT, RIGHT, MEDIAN}; 
		
	public int[] sort(int a[]) {
		this.a = a;
		this.comparisons = 0;
		if (this.strategy == null) strategy = LEFT;
		
		partition(0, a.length-1);
		return a;
	}
	
	public void setPivotStrategy(PivotStrategy strategy) {
		this.strategy = strategy;
	}
	
	private void partition(int l, int r) {
		int size = r - l + 1;
		
		if (size <= 1) return; 
		comparisons += size - 1;
		
		int pivot = choosePivot(l, r);
		int i = l + 1;
		
		for (int j = l+1; j <= r; j++) {
			if (a[j] < pivot) {
				swap(i, j);
				i++;
			}
		}
		
		swap(i-1, l);
		
		partition(l, i-1); 
		partition(i+1, r); 	
	}
	
	private int choosePivot(int l, int r) {
		switch (strategy) {
			case LEFT: break;
			case RIGHT: swap(l, r); break;
			case MEDIAN: {
				int middle = r - l + 1 / 2;
				
				class Median {
					public Median(int i, int v) {index = i; value = v;}
					public int index;
					public int value;
				}
				
				//FIXME
				ArrayList<Median> list = new ArrayList<Median>();
				list.add(new Median(a[l], l));
				list.add(new Median(a[middle], middle));
				list.add(new Median(a[r], r));
				Collections.sort(list, new Comparator<Median>() {
					public int compare(Median one, Median other) {
						return one.value - other.value;
					};
				});

				int median = list.get(1).index;
				swap(l, median);
			};
		}
		return a[l];
	}
		
	private void swap(int i, int j) {
		int tmp = a[i];
		a[i] = a[j];
		a[j] = tmp;
	}
	
	public int getComparisons() {
		return comparisons;
	}
}