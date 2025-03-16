package com.inders.dsa.datastructures.hashtable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class HashTableSeparateChaining<K, V> implements HashTable<K, V> {

    private static final int DEFAULT_CAPACITY = 3;
    private static final double DEFAULT_LOAD_FACTOR = 0.75;

    private double maxLoadFactor;
    private int capacity, threshold, size = 0;
    private LinkedList<Entry<K, V>>[] table;

    public HashTableSeparateChaining() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public HashTableSeparateChaining(int capacity) {
        this(capacity, DEFAULT_LOAD_FACTOR);
    }

    public HashTableSeparateChaining(int capacity, double maxLoadFactor) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Illegal capacity");
        }
        if (maxLoadFactor <=0 || Double.isNaN(maxLoadFactor) || Double.isInfinite(maxLoadFactor)) {
            throw new IllegalArgumentException("Illegal maxLoadFactor");
        }
        this.maxLoadFactor = maxLoadFactor;
        this.capacity = Math.max(DEFAULT_CAPACITY, capacity);
        threshold = (int) (this.capacity * this.maxLoadFactor);
        table = new LinkedList[this.capacity];
    }

    // Number of elements in the hashtable currently
    @Override
    public int size() {
        return size;
    }

    // Returns whether the hash table is empty or not
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    // Clear all the contents of the hashtable
    @Override
    public void clear() {
        Arrays.fill(table, null);
        size = 0;
    }

    // Returns whether this key exists in the hashtable or not
    @Override
    public boolean contains(K key) {
        int bucketIndex = normalizeIndex(key.hashCode());
        return bucketSeekEntry(bucketIndex, key) != null;
    }

    // Add a value into hashtable
    @Override
    public V put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Null key");
        }
        Entry<K, V> entry = new Entry<>(key, value);
        int bucketIndex = normalizeIndex(entry.hash);
        return bucketInsertEntry(bucketIndex, entry);
    }

    // Returns the value for a given key if exists else returns null
    @Override
    public V get(K key) {
        if (key == null) {
            return null;
        }
        int bucketIndex = normalizeIndex(key.hashCode());
        Entry<K, V> entry = bucketSeekEntry(bucketIndex, key);
        if (entry != null) {
            return entry.value;
        }
        return null;
    }

    // Removes from hashtable and returns the value for a given
    // key if exists else returns null
    @Override
    public V remove(K key) {
        if (key == null) {
            return null;
        }
        int bucketIndex = normalizeIndex(key.hashCode());
        return bucketRemoveEntry(bucketIndex, key);
    }

    // Returns list of all the keys in the hashtable
    @Override
    public List<K> keys() {
        List<K> keys = new ArrayList<>(size);
        for (LinkedList<Entry<K, V>> bucket : table) {
            if (bucket != null) {
                for (Entry<K, V> entry : bucket) {
                    keys.add(entry.key);
                }
            }
        }
        return keys;
    }

    // Returns list of all the keys in the hashtable
    @Override
    public List<V> values() {
        List<V> values = new ArrayList<>(size);
        for (LinkedList<Entry<K, V>> bucket : table) {
            if (bucket != null) {
                for (Entry<K, V> entry : bucket) {
                    values.add(entry.value);
                }
            }
        }
        return values;
    }

    // Return an iterator to iterate over all the keys in this map
    @Override
    public Iterator<K> iterator() {
        final int elementCount = size();
        return new Iterator<K>() {

            int bucketIndex = 0;
            Iterator<Entry<K, V>> bucketIterator = (table[0] == null) ? null : table[0].iterator();

            @Override
            public boolean hasNext() {
                // An item was added or removed while iterating
                if (elementCount != size) {
                    throw new ConcurrentModificationException();
                }
                // Current iterator is null or empty
                if (bucketIterator == null || !bucketIterator.hasNext()) {
                    // Search next bucket until a valid iterator is found
                    while (++bucketIndex < capacity) {
                        if (table[bucketIndex] != null) {
                            Iterator<Entry<K, V>> nextIterator = table[bucketIndex].iterator();
                            if (nextIterator.hasNext()) {
                                bucketIterator = nextIterator;
                                break;
                            }
                        }
                    }
                }
                return bucketIndex < capacity;
            }

            @Override
            public K next() {
                return bucketIterator.next().key;
            }
        };
    }

    // Returns a string representation of this hash table
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (int i = 0; i < capacity; i++) {
            if (table[i] == null) {
                continue;
            }
            for (Entry<K, V> entry : table[i]) {
                sb.append(entry + ", ");
            }
        }
        sb.append("}");
        return sb.toString();
    }

    // Converts the hash value to an index. The statement below strips
    // the negative sign and places the value in domain [0, capacity)
    private int normalizeIndex(int keyHash) {
        return (keyHash & 0x7FFFFFFF) % capacity;
    }

    // Finds and returns a particular entry in a given bucket if it exists, returns null otherwise
    private Entry<K, V> bucketSeekEntry(int bucketIndex, K key) {
        if (key == null) {
            return null;
        }
        LinkedList<Entry<K, V>> bucket = table[bucketIndex];
        if (bucket == null) {
            return null;
        }
        for (Entry<K, V> entry : bucket) {
            if (entry.key.equals(key)) {
                return entry;
            }
        }
        return null;
    }

    // Inserts a given entry into a given bucket if the entry does not already exist
    // update the entry if it exists in the given bucket
    private V bucketInsertEntry(int bucketIndex, Entry<K, V> entry) {

        LinkedList<Entry<K, V>> bucket = table[bucketIndex];
        if (bucket == null) {
            table[bucketIndex] = bucket = new LinkedList<>();
        }

        Entry<K, V> existingEntry = bucketSeekEntry(bucketIndex, entry.key);
        if (existingEntry == null) {
            bucket.add(entry);
            if (++size > threshold) {
                resizeTable();
            }
            return null; // Return null to indicate there was no previous value
        } else {
            V oldValue = existingEntry.value;
            existingEntry.value = entry.value;
            return oldValue;
        }
    }

    // Removes an entry from a given bucket if it exists
    private V bucketRemoveEntry(int bucketIndex, K key) {

        Entry<K, V> entry = bucketSeekEntry(bucketIndex, key);
        if (entry != null) {
            LinkedList<Entry<K, V>> bucket = table[bucketIndex];
            bucket.remove(entry);
            --size;
            return entry.value;
        } else {
            return null;
        }
    }

    // Resize the internal table holding hashtable entries
    private void resizeTable() {

        capacity *= 2;
        threshold = (int) (capacity * maxLoadFactor);

        LinkedList<Entry<K, V>>[] newTable = new LinkedList[capacity];

        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                for (Entry<K, V> entry : table[i]) {
                    int bucketIndex = normalizeIndex(entry.hash);
                    LinkedList<Entry<K, V>> bucket = newTable[bucketIndex];
                    if (bucket == null) {
                        newTable[bucketIndex] = bucket = new LinkedList<>();
                    }
                    bucket.add(entry);
                }

                // Clear memory used by old table
                table[i].clear();
                table[i] = null;
            }
        }
        table = newTable;
    }
}
