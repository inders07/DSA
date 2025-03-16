package com.inders.dsa.datastructures.hashtable;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings("unchecked")
public abstract class HashTableOpenAddressingBase<K, V> implements HashTable<K, V> {

    protected double loadFactor;
    protected int capacity, threshold, modificationCount;

    // 'usedBuckets' counts the total number of used buckets inside the
    // hash-table (includes cells marked as deleted). While 'keyCount'
    // tracks the number of unique keys currently inside the hash-table.
    protected int usedBuckets, keyCount;

    // Arrays to store key-value pairs
    protected K[] keys;
    protected V[] values;

    // Special marker token used to indicate the deletion of a key-value pair
    // This helps in removal of key-value pairs to not lose probing chain
    protected final K TOMBSTONE = (K) (new Object());

    private static final int DEFAULT_CAPACITY = 7;
    private static final double DEFAULT_LOAD_FACTOR = 0.65;

    protected HashTableOpenAddressingBase() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    protected HashTableOpenAddressingBase(int capacity) {
        this(capacity, DEFAULT_LOAD_FACTOR);
    }

    protected HashTableOpenAddressingBase(int capacity, double loadFactor) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Illegal capacity");
        }
        if (loadFactor <=0 || Double.isNaN(loadFactor) || Double.isInfinite(loadFactor)) {
            throw new IllegalArgumentException("Illegal loadFactor");
        }
        this.loadFactor = loadFactor;
        this.capacity = Math.max(DEFAULT_CAPACITY, capacity);
        adjustCapacity();
        threshold = (int) (this.capacity * this.loadFactor);

        keys = (K[]) (new Object[this.capacity]);
        values = (V[]) (new Object[this.capacity]);
    }

    // These three methods are used to dictate how the probing is to actually
    // occur for whatever open addressing scheme you are implementing.
    protected abstract void setupProbing(K key);

    protected abstract int probe(int x);

    // Adjusts the capacity of the hashtable after it's been made larger
    protected abstract void adjustCapacity();

    // Increases the capacity of the hashtable
    protected void increaseCapacity() {
        capacity = (2 * capacity) + 1;
    }

    @Override
    public void clear() {
        for (int i = 0; i < capacity; i++) {
            keys[i] = null;
            values[i] = null;
        }
        keyCount = usedBuckets = 0;
        modificationCount++;
    }

    // Returns the number of keys in the hashtable
    @Override
    public int size() {
        return keyCount;
    }

    // Returns the capacity of the hashtable
    public int getCapacity() {
        return capacity;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    // Returns list of all the keys in the hashtable
    @Override
    public List<K> keys() {
        List<K> keys = new ArrayList<>(keyCount);
        for (int i = 0; i < capacity; i++) {
            if (this.keys[i] != null && this.keys[i] != TOMBSTONE) {
                keys.add(this.keys[i]);
            }
        }
        return keys;
    }

    // Returns list of all the keys in the hashtable
    @Override
    public List<V> values() {
        List<V> values = new ArrayList<>(keyCount);
        for (int i = 0; i < capacity; i++) {
            if (this.keys[i] != null && this.keys[i] != TOMBSTONE) {
                values.add(this.values[i]);
            }
        }
        return values;
    }

    // Place a key-value pair into the hash-table. If the value already
    // exists inside the hash-table then the value is updated.
    @Override
    public V put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Null key");
        }
        if (usedBuckets >= threshold) {
            resizeTable();
        }

        setupProbing(key);
        final int offset = normalizeIndex(key.hashCode());

        for (int i = offset, j = -1, x = 1; ; i = normalizeIndex(offset + probe(x++))) {

            // The current slot was previously deleted
            if (keys[i] == TOMBSTONE) {
                if (j == -1) {
                    j = i;
                }
            } else if (keys[i] != null) {
                // The key already exists in the table, update its value
                if (keys[i].equals(key)) {
                    V oldValue = values[i];
                    // update the value in this index if no tombstone was found
                    if (j == -1) {
                        values[i] = value;
                    } else {
                        keys[i] = TOMBSTONE;
                        values[i] = null;
                        keys[j] = key;
                        values[j] = value;
                    }
                    modificationCount++;
                    return oldValue;
                }
            } else {
                if (j == -1) {
                    usedBuckets++;
                    keys[i] = key;
                    values[i] = value;
                } else {
                    keys[j] = key;
                    values[j] = value;
                }
                keyCount++;
                modificationCount++;
                return null;
            }
        }
    }

    // Returns true/false on whether a given key exists within the hash-table
    @Override
    public boolean contains(K key) {
        if (key == null){
            throw new IllegalArgumentException("Null key");
        }

        setupProbing(key);
        final int offset = normalizeIndex(key.hashCode());

        for (int i = offset, j = -1, x = 1; ; i = normalizeIndex(offset + probe(x++))) {

            if (keys[i] == TOMBSTONE) {
                if (j == -1) {
                    j = i;
                }
            } else if (keys[i] != null) {
                if (keys[i].equals(key)) {
                    if (j != -1) {
                        keys[j] = key;
                        values[j] = values[i];
                        keys[i] = TOMBSTONE;
                        values[i] = null;
                    }
                    return true;
                }
            } else {
                return false;
            }
        }
    }

    // Get the value associated with the input key.
    @Override
    public V get(K key) {
        if (key == null){
            throw new IllegalArgumentException("Null key");
        }

        setupProbing(key);
        final int offset = normalizeIndex(key.hashCode());

        for (int i = offset, j = -1, x = 1; ; i = normalizeIndex(offset + probe(x++))) {

            if (keys[i] == TOMBSTONE) {
                if (j == -1) {
                    j = i;
                }
            } else if (keys[i] != null) {
                if (keys[i].equals(key)) {
                    if (j != -1) {
                        keys[j] = key;
                        values[j] = values[i];
                        keys[i] = TOMBSTONE;
                        values[i] = null;
                        return values[j];
                    } else {
                        return values[i];
                    }
                }
            } else {
                return null;
            }
        }
    }

    // Removes a key from the map and returns the value.
    @Override
    public V remove(K key) {
        if (key == null){
            throw new IllegalArgumentException("Null key");
        }

        setupProbing(key);
        final int offset = normalizeIndex(key.hashCode());

        for (int i = offset, x = 1; ; i = normalizeIndex(offset + probe(x++))) {

            if (keys[i] == TOMBSTONE) {
                continue;
            } else if (keys[i] != null) {
                if (keys[i].equals(key)) {
                    keyCount--;
                    modificationCount++;
                    V value = values[i];
                    keys[i] = TOMBSTONE;
                    values[i] = null;
                    return value;
                }
            } else {
                return null;
            }
        }
    }

    // Double the size of the table
    protected void resizeTable() {
        increaseCapacity();
        adjustCapacity();

        threshold = (int) (capacity * loadFactor);

        K[] oldKeys = (K[]) new Object[capacity];
        V[] oldValues = (V[]) new Object[capacity];

        K[] tempKeys = keys;
        keys = oldKeys;
        oldKeys = tempKeys;

        V[] tempValues = values;
        values = oldValues;
        oldValues = tempValues;

        keyCount = usedBuckets = 0;

        for (int i = 0; i < oldKeys.length; i++) {
            if (oldKeys[i] != null && oldKeys[i] != TOMBSTONE) {
                put(oldKeys[i], oldValues[i]);
            }
            oldKeys[i] = null;
            oldValues[i] = null;
        }
    }

    // Converts a hash value to an index. Essentially, this strips the
    // negative sign and places the hash value in the domain [0, capacity)
    protected final int normalizeIndex(int keyHash) {
        return (keyHash & 0x7FFFFFFF) % capacity;
    }

    // Finds the greatest common denominator of a and b.
    protected static int gcd(int a, int b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }

    // Return a String view of this hash-table.
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("{");
        for (int i = 0; i < capacity; i++) {
            if (keys[i] != null && keys[i] != TOMBSTONE) {
                sb.append(keys[i]).append(" => ").append(values[i]).append(", ");
            }
        }
        sb.append("}");

        return sb.toString();
    }

    @Override
    public Iterator<K> iterator() {
        // Before the iteration begins record the number of modifications
        // done to the hash-table. This value should not change as we iterate
        // otherwise a concurrent modification has occurred :0
        final int MODIFICATION_COUNT = modificationCount;

        return new Iterator<K>() {
            int index, keysLeft = keyCount;

            @Override
            public boolean hasNext() {
                // The contents of the table have been altered
                if (MODIFICATION_COUNT != modificationCount) {
                    throw new ConcurrentModificationException();
                }

                return keysLeft != 0;
            }

            // Find the next element and return it
            @Override
            public K next() {
                while (keys[index] == null || keys[index] == TOMBSTONE) {
                    index++;
                }
                keysLeft--;
                return keys[index++];
            }
        };
    }
}
