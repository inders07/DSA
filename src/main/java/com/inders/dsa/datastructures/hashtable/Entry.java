package com.inders.dsa.datastructures.hashtable;

import java.security.PublicKey;

class Entry<K, V> {

    int hash;
    K key;
    V value;

    public Entry(K key, V value) {
        this.key = key;
        this.value = value;
        this.hash = key.hashCode();
    }

    public boolean equals(Entry<K, V> other) {
        if (this.hash != other.hash) {
            return false;
        }
        return this.key == other.key;
    }

    @Override
    public String toString() {
        return key + " => " + value;
    }
}
