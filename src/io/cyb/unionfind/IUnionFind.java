package io.cyb.unionfind;

public interface IUnionFind {
    public boolean find(int a, int b);

    public void connect(int a, int b);
}
