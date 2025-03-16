package com.inders.dsa.datastructures.hashtable;

public class HashTableQuadraticProbing<K, V> extends HashTableOpenAddressingBase<K, V> {

    public HashTableQuadraticProbing() {
        super();
    }

    public HashTableQuadraticProbing(int capacity) {
        super(capacity);
    }

    public HashTableQuadraticProbing(int capacity, double loadFactor) {
        super(capacity, loadFactor);
    }

    @Override
    protected void setupProbing(K key) {}

    @Override
    protected int probe(int x) {
        return (x * x + x) >> 1;
    }

    // Increase the capacity of the hashtable to the next power of two.
    @Override
    protected void increaseCapacity() {
        capacity = nextPowerOfTwo(capacity);
    }

    // Adjust the capacity so that the linear constant and
    // the table capacity are relatively prime.
    @Override
    protected void adjustCapacity() {
        int pow2 = Integer.highestOneBit(capacity);
        if (capacity == pow2) {
            return;
        }
        increaseCapacity();
    }

    // Given a number this method finds the next
    // power of two above this value.
    private static int nextPowerOfTwo(int n) {
        return Integer.highestOneBit(n) << 1;
    }
}
