package com.inders.dsa.datastructures.hashtable;

import java.util.List;

public interface HashTable<K, V> extends Iterable<K> {

    int size();

    boolean isEmpty();

    void clear();

    boolean contains(K key);

    V put(K key, V value);

    V get(K key);

    V remove(K key);

    List<K> keys();

    List<V> values();
}
