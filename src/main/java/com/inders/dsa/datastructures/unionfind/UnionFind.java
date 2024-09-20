package com.inders.dsa.datastructures.unionfind;

public final class UnionFind {

    // number of elements in the union find
    private final int size;

    // size of each component group
    private final int[] sizeMap;

    // parent mapping, id[i] maps to parent of i, id[i] = i is a root node
    private final int[] id;

    // number of components
    private int nComponents;

    public UnionFind(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException();
        }

        this.size = nComponents = size;
        sizeMap = new int[size];
        id = new int[size];

        for (int i = 0; i < size; i++) {
            // initialize size of each component to 1
            sizeMap[i] = 1;
            // initialize every node to be a root node
            id[i] = i;
        }
    }

    // find and returns the root node to which node i belongs
    public int find(int i) {
        int root = i;
        while (root != id[root]) {
            // traverse recursively until we find a root node
            root = id[root];
        }

        // compress the path traversing back to root node from node i
        // this is called path compression which will give us amortized
        // constant time complexity
        while (i != root) {
            int j = id[i]; // store the next node temporarily
            id[i] = root; // update the parent of i to root
            i = j; // update the pointer to next node
        }

        return root;
    }

    // return whether node i and j are connected/in the same component/set.
    public boolean connected(int i, int j) {
        return find(i) == find(j);
    }

    // returns size of the component node i belongs to
    public int componentSize(int i) {
        return sizeMap[find(i)];
    }

    // returns the number of elements/nodes in the UnionFind
    public int size() {
        return size;
    }

    // returns the number of components in the UnionFind
    public int components() {
        return nComponents;
    }

    // unify the components/sets to which node i and j belongs to
    public void unify(int i, int j) {
        int root1 = find(i); // root node of node i
        int root2 = find(j); // root node of node j

        // do nothing if node i and j are already in the same group
        if (root1 == root2) {
            return;
        }

        // merge the smaller group into larger group
        if (sizeMap[root1] > sizeMap[root2]) {
            sizeMap[root1] += sizeMap[root2];
            id[root2] = root1;
            sizeMap[root2] = 0;
        } else {
            sizeMap[root2] += sizeMap[root1];
            id[root1] = root2;
            sizeMap[root1] = 0;
        }

        // decrement the total number of components
        nComponents--;
    }
}
