package com.inders.dsa.datastructures.fenwicktree;

public class FenwickTreeRangeQueryPointUpdate {

    // Size of the array holding the fenwick tree values
    final int N;

    // The array containing the fenwick tree ranges
    private final long[] tree;

    // Create an empty fenwick tree with size
    public FenwickTreeRangeQueryPointUpdate(int sz) {
        // add 1 because fenwick tree has 1-based index
        tree = new long[N = sz + 1];
    }

    // Construct a Fenwick tree with an initial set of values.
    // The 'values' array MUST BE ONE BASED meaning values[0]
    // does not get used, O(n) construction.
    public FenwickTreeRangeQueryPointUpdate(long[] values) {
        if (values == null) {
            throw new IllegalArgumentException("Values array can not be null");
        }

        N = values.length;
        values[0] = 0L;

        // Make a clone of the values array since we manipulate
        // the array in place destroying all its original content.
        tree = values.clone();

        for (int i = 1; i < N; i++) {
            int parent = i + lsb(i);
            if (parent < N) {
                tree[parent] += tree[i];
            }
        }
    }

    // Returns the sum of the interval [left, right], O(log(n))
    public long sum(int left, int right) {
        if (left > right) {
            throw new IllegalArgumentException("Make sure right >= left");
        }
        return prefixSum(right) - prefixSum(left - 1);
    }

    // Get the value at index i
    public long get(int i) {
        return sum(i, i);
    }

    // Add 'v' to index 'i', O(log(n))
    public void add(int i, long v) {
        while (i < N) {
            tree[i] += v;
            i += lsb(i);
        }
    }

    // Set index i to be equal to v, O(log(n))
    public void set(int i, long v) {
        add(i, v - sum(i, i));
    }

    @Override
    public String toString() {
        return java.util.Arrays.toString(tree);
    }

    // Computes the prefix sum from [1, i], O(log(n))
    private long prefixSum(int i) {
        long sum = 0L;
        while (i != 0) {
            sum += tree[i];
            i &= ~lsb(i); // Equivalently, i -= lsb(i);
        }
        return sum;
    }

    // Returns the value of the least significant bit (LSB)
    // lsb(108) = lsb(0b1101100) =     0b100 = 4
    // lsb(104) = lsb(0b1101000) =    0b1000 = 8
    // lsb(96)  = lsb(0b1100000) =  0b100000 = 32
    // lsb(64)  = lsb(0b1000000) = 0b1000000 = 64
    private static int lsb(int i) {

        // Isolates the lowest one bit
        return i & -i;

        // An alternative method is to use the Java's built in method
        // return Integer.lowestOneBit(i);
    }
}
