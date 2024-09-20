package com.inders.dsa.datastructures.unionfind;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UnionFindTest {

    @Test
    public void testNumComponents() {

        UnionFind uf = new UnionFind(5);
        assertEquals(5, uf.components());

        uf.unify(0, 1);
        assertEquals(4, uf.components());

        uf.unify(1, 0);
        assertEquals(4, uf.components());

        uf.unify(1, 2);
        assertEquals(3, uf.components());

        uf.unify(0, 2);
        assertEquals(3, uf.components());

        uf.unify(2, 1);
        assertEquals(3, uf.components());

        uf.unify(3, 4);
        assertEquals(2, uf.components());

        uf.unify(4, 3);
        assertEquals(2, uf.components());

        uf.unify(1, 3);
        assertEquals(1, uf.components());

        uf.unify(4, 0);
        assertEquals(1, uf.components());
    }

    @Test
    public void testComponentSize() {

        UnionFind uf = new UnionFind(5);
        assertEquals(1, uf.componentSize(0));
        assertEquals(1, uf.componentSize(1));
        assertEquals(1, uf.componentSize(2));
        assertEquals(1, uf.componentSize(3));
        assertEquals(1, uf.componentSize(4));

        uf.unify(0, 1);
        assertEquals(2, uf.componentSize(0));
        assertEquals(2, uf.componentSize(1));
        assertEquals(1, uf.componentSize(2));
        assertEquals(1, uf.componentSize(3));
        assertEquals(1, uf.componentSize(4));

        uf.unify(1, 0);
        assertEquals(2, uf.componentSize(0));
        assertEquals(2, uf.componentSize(1));
        assertEquals(1, uf.componentSize(2));
        assertEquals(1, uf.componentSize(3));
        assertEquals(1, uf.componentSize(4));

        uf.unify(1, 2);
        assertEquals(3, uf.componentSize(0));
        assertEquals(3, uf.componentSize(1));
        assertEquals(3, uf.componentSize(2));
        assertEquals(1, uf.componentSize(3));
        assertEquals(1, uf.componentSize(4));

        uf.unify(0, 2);
        assertEquals(3, uf.componentSize(0));
        assertEquals(3, uf.componentSize(1));
        assertEquals(3, uf.componentSize(2));
        assertEquals(1, uf.componentSize(3));
        assertEquals(1, uf.componentSize(4));

        uf.unify(2, 1);
        assertEquals(3, uf.componentSize(0));
        assertEquals(3, uf.componentSize(1));
        assertEquals(3, uf.componentSize(2));
        assertEquals(1, uf.componentSize(3));
        assertEquals(1, uf.componentSize(4));

        uf.unify(3, 4);
        assertEquals(3, uf.componentSize(0));
        assertEquals(3, uf.componentSize(1));
        assertEquals(3, uf.componentSize(2));
        assertEquals(2, uf.componentSize(3));
        assertEquals(2, uf.componentSize(4));

        uf.unify(4, 3);
        assertEquals(3, uf.componentSize(0));
        assertEquals(3, uf.componentSize(1));
        assertEquals(3, uf.componentSize(2));
        assertEquals(2, uf.componentSize(3));
        assertEquals(2, uf.componentSize(4));

        uf.unify(1, 3);
        assertEquals(5, uf.componentSize(0));
        assertEquals(5, uf.componentSize(1));
        assertEquals(5, uf.componentSize(2));
        assertEquals(5, uf.componentSize(3));
        assertEquals(5, uf.componentSize(4));

        uf.unify(4, 0);
        assertEquals(5, uf.componentSize(0));
        assertEquals(5, uf.componentSize(1));
        assertEquals(5, uf.componentSize(2));
        assertEquals(5, uf.componentSize(3));
        assertEquals(5, uf.componentSize(4));
    }

    @Test
    public void testConnectivity() {

        int sz = 7;
        UnionFind uf = new UnionFind(sz);

        for (int i = 0; i < sz; i++) {
            assertTrue(uf.connected(i, i));
        }

        uf.unify(0, 2);

        assertTrue(uf.connected(0, 2));
        assertTrue(uf.connected(2, 0));

        assertFalse(uf.connected(0, 1));
        assertFalse(uf.connected(3, 1));
        assertFalse(uf.connected(6, 4));
        assertFalse(uf.connected(5, 0));

        for (int i = 0; i < sz; i++) {
            assertTrue(uf.connected(i, i));
        }

        uf.unify(3, 1);

        assertTrue(uf.connected(0, 2));
        assertTrue(uf.connected(2, 0));
        assertTrue(uf.connected(1, 3));
        assertTrue(uf.connected(3, 1));

        assertFalse(uf.connected(0, 1));
        assertFalse(uf.connected(1, 2));
        assertFalse(uf.connected(2, 3));
        assertFalse(uf.connected(1, 0));
        assertFalse(uf.connected(2, 1));
        assertFalse(uf.connected(3, 2));

        assertFalse(uf.connected(1, 4));
        assertFalse(uf.connected(2, 5));
        assertFalse(uf.connected(3, 6));

        for (int i = 0; i < sz; i++) {
            assertTrue(uf.connected(i, i));
        }

        uf.unify(2, 5);
        assertTrue(uf.connected(0, 2));
        assertTrue(uf.connected(2, 0));
        assertTrue(uf.connected(1, 3));
        assertTrue(uf.connected(3, 1));
        assertTrue(uf.connected(0, 5));
        assertTrue(uf.connected(5, 0));
        assertTrue(uf.connected(5, 2));
        assertTrue(uf.connected(2, 5));

        assertFalse(uf.connected(0, 1));
        assertFalse(uf.connected(1, 2));
        assertFalse(uf.connected(2, 3));
        assertFalse(uf.connected(1, 0));
        assertFalse(uf.connected(2, 1));
        assertFalse(uf.connected(3, 2));

        assertFalse(uf.connected(4, 6));
        assertFalse(uf.connected(4, 5));
        assertFalse(uf.connected(1, 6));

        for (int i = 0; i < sz; i++) {
            assertTrue(uf.connected(i, i));
        }

        // Connect everything
        uf.unify(1, 2);
        uf.unify(3, 4);
        uf.unify(4, 6);

        for (int i = 0; i < sz; i++) {
            for (int j = 0; j < sz; j++) {
                assertTrue(uf.connected(i, j));
            }
        }
    }

    @Test
    public void testSize() {

        UnionFind uf = new UnionFind(5);
        assertEquals(5, uf.size());
        uf.unify(0, 1);
        uf.find(3);
        assertEquals(5, uf.size());
        uf.unify(1, 2);
        assertEquals(5, uf.size());
        uf.unify(0, 2);
        uf.find(1);
        assertEquals(5, uf.size());
        uf.unify(2, 1);
        assertEquals(5, uf.size());
        uf.unify(3, 4);
        uf.find(0);
        assertEquals(5, uf.size());
        uf.unify(4, 3);
        uf.find(3);
        assertEquals(5, uf.size());
        uf.unify(1, 3);
        assertEquals(5, uf.size());
        uf.find(2);
        uf.unify(4, 0);
        assertEquals(5, uf.size());
    }

    @ParameterizedTest(name = "Size: {0}")
    @ValueSource(ints = {-1, -3463, 0})
    public void testBadUnionFindCreation(int size) {
        assertThrows(IllegalArgumentException.class, () -> new UnionFind(size));
    }
}
