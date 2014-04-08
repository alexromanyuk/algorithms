package io.cyb.unionfind;

import java.util.Arrays;

public class QuickUnion implements IUnionFind {
    private int[] arr;

    public QuickUnion(int numOfElements) {
        arr = new int[numOfElements];

        for(int i=0; i<numOfElements; i++) {
            arr[i] = i;
        }
    }

    public boolean find(int a, int b) {
        return indexInBounds(a, b) && (rootOf(a) == rootOf(b));
    }

    public void connect(int a, int b) {
        //FIXME seems that's Quick Find implementation
        if (!indexInBounds(a, b)) {return; }

        int aRoot = rootOf(a);
        int bRoot = rootOf(b);

        arr[aRoot] = bRoot;
    }

    private int rootOf(int index) {
        while (arr[index] != index) { index = arr[index]; }
        return index;
    }

    private boolean indexInBounds(int index) {
        return index < arr.length;
    }

    private boolean indexInBounds(int a, int b) {
        //TODO: add logger
        return indexInBounds(a) && indexInBounds(b);
    }

    public void printState() {
        System.out.println(Arrays.toString(arr));
    }

    public static void main(String[] args) {
        //      1       4      0
        //    /  \    /  \
        //   2   3   5    6
        //                 \
        //                 7

        QuickUnion qu = new QuickUnion(8);
        qu.connect(2, 1);
        qu.connect(3, 1);

        qu.connect(5, 4);
        qu.connect(6, 4);
        qu.connect(7, 6);

        System.out.printf("Connected %s to %s ?: %s \n", 8, 1, qu.find(8, 1));
        System.out.printf("Connected %s to %s ?: %s \n", 3, 1, qu.find(3, 1));
        System.out.printf("Connected %s to %s ?: %s \n", 7, 5, qu.find(7, 5));
        System.out.printf("Connected %s to %s ?: %s \n", 7, 3, qu.find(7, 3));

        qu.printState();

    }
}
