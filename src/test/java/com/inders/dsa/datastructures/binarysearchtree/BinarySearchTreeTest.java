package com.inders.dsa.datastructures.binarysearchtree;

import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestTreeNode {

    Integer data;
    TestTreeNode left, right;

    public TestTreeNode(Integer data, TestTreeNode l, TestTreeNode r) {
        this.data = data;
        this.right = r;
        this.left = l;
    }

    static TestTreeNode add(TestTreeNode node, int data) {

        if (node == null) {
            node = new TestTreeNode(data, null, null);
        } else {
            // Place lower elem values on left
            if (data < node.data) {
                node.left = add(node.left, data);
            } else {
                node.right = add(node.right, data);
            }
        }
        return node;
    }

    static void preOrder(List<Integer> lst, TestTreeNode node) {

        if (node == null) {
            return;
        }

        lst.add(node.data);
        if (node.left != null) {
            preOrder(lst, node.left);
        }
        if (node.right != null) {
            preOrder(lst, node.right);
        }
    }

    static void inOrder(List<Integer> lst, TestTreeNode node) {

        if (node == null) {
            return;
        }

        if (node.left != null) {
            inOrder(lst, node.left);
        }
        lst.add(node.data);
        if (node.right != null) {
            inOrder(lst, node.right);
        }
    }

    static void postOrder(List<Integer> lst, TestTreeNode node) {

        if (node == null) {
            return;
        }

        if (node.left != null) {
            postOrder(lst, node.left);
        }
        if (node.right != null) {
            postOrder(lst, node.right);
        }
        lst.add(node.data);
    }

    static void levelOrder(List<Integer> lst, TestTreeNode node) {

        Deque<TestTreeNode> q = new ArrayDeque<>();
        if (node != null) {
            q.offer(node);
        }

        while (!q.isEmpty()) {
            node = q.poll();
            lst.add(node.data);
            if (node.left != null) {
                q.offer(node.left);
            }
            if (node.right != null) {
                q.offer(node.right);
            }
        }
    }
}

public class BinarySearchTreeTest {

    static final int LOOPS = 100;

    @Test
    public void testIsEmpty() {

        BinarySearchTree<String> tree = new BinarySearchTree<>();
        assertTrue(tree.isEmpty());

        tree.add("Hello World!");
        assertFalse(tree.isEmpty());
    }

    @Test
    public void testSize() {
        BinarySearchTree<String> tree = new BinarySearchTree<>();
        assertEquals(0, tree.size());

        tree.add("Hello World!");
        assertEquals(1, tree.size());
    }

    @Test
    public void testHeight() {
        BinarySearchTree<String> tree = new BinarySearchTree<>();

        // Tree should look like:
        //        M
        //      J  S
        //    B   N Z
        //  A

        // No tree
        assertEquals(0, tree.height());

        // Layer One
        tree.add("M");
        assertEquals(1, tree.height());

        // Layer Two
        tree.add("J");
        assertEquals(2, tree.height());
        tree.add("S");
        assertEquals(2, tree.height());

        // Layer Three
        tree.add("B");
        assertEquals(3, tree.height());
        tree.add("N");
        assertEquals(3, tree.height());
        tree.add("Z");
        assertEquals(3, tree.height());

        // Layer 4
        tree.add("A");
        assertEquals(4, tree.height());
    }

    @Test
    public void testAdd() {

        // Add element which does not yet exist
        BinarySearchTree<Character> tree = new BinarySearchTree<>();
        assertTrue(tree.add('A'));

        // Add duplicate element
        assertFalse(tree.add('A'));

        // Add a second element which is not a duplicate
        assertTrue(tree.add('B'));
    }

    @Test
    public void testRemove() {

        // Try removing an element which doesn't exist
        BinarySearchTree<Character> tree = new BinarySearchTree<>();
        tree.add('A');
        assertEquals(1, tree.size());
        assertFalse(tree.remove('B'));
        assertEquals(1, tree.size());

        // Try removing an element which does exist
        tree.add('B');
        assertEquals(2, tree.size());
        assertTrue(tree.remove('B'));
        assertEquals(1, tree.size());
        assertEquals(1, tree.height());

        // Try removing the root
        assertTrue(tree.remove('A'));
        assertEquals(0, tree.size());
        assertEquals(0, tree.height());
    }

    @Test
    public void testContains() {

        // Setup tree
        BinarySearchTree<Character> tree = new BinarySearchTree<>();

        tree.add('B');
        tree.add('A');
        tree.add('C');

        // Try looking for an element which doesn't exist
        assertFalse(tree.contains('D'));

        // Try looking for an element which exists in the root
        assertTrue(tree.contains('B'));

        // Try looking for an element which exists as the left child of the root
        assertTrue(tree.contains('A'));

        // Try looking for an element which exists as the right child of the root
        assertTrue(tree.contains('C'));
    }

    @Test
    public void concurrentModificationErrorPreOrder() {

        BinarySearchTree<Integer> bst = new BinarySearchTree<>();

        bst.add(1);
        bst.add(2);
        bst.add(3);

        Iterator<Integer> iter = bst.traverse(TreeTraversalOrder.PRE_ORDER);

        assertThrows(
                ConcurrentModificationException.class,
                () -> {
                    while (iter.hasNext()) {
                        bst.add(0);
                        iter.next();
                    }
                });
    }

    @Test
    public void concurrentModificationErrorInOrderOrder() {

        BinarySearchTree<Integer> bst = new BinarySearchTree<>();

        bst.add(1);
        bst.add(2);
        bst.add(3);

        Iterator<Integer> iter = bst.traverse(TreeTraversalOrder.IN_ORDER);

        assertThrows(
                ConcurrentModificationException.class,
                () -> {
                    while (iter.hasNext()) {
                        bst.add(0);
                        iter.next();
                    }
                });
    }

    @Test
    public void concurrentModificationErrorPostOrder() {

        BinarySearchTree<Integer> bst = new BinarySearchTree<>();

        bst.add(1);
        bst.add(2);
        bst.add(3);

        Iterator<Integer> iter = bst.traverse(TreeTraversalOrder.POST_ORDER);

        assertThrows(
                ConcurrentModificationException.class,
                () -> {
                    while (iter.hasNext()) {
                        bst.add(0);
                        iter.next();
                    }
                });
    }

    @Test
    public void concurrentModificationErrorLevelOrder() {

        BinarySearchTree<Integer> bst = new BinarySearchTree<>();

        bst.add(1);
        bst.add(2);
        bst.add(3);

        Iterator<Integer> iter = bst.traverse(TreeTraversalOrder.LEVEL_ORDER);

        assertThrows(
                ConcurrentModificationException.class,
                () -> {
                    while (iter.hasNext()) {
                        bst.add(0);
                        iter.next();
                    }
                });
    }

    @Test
    public void concurrentModificationErrorRemovingPreOrder() {

        BinarySearchTree<Integer> bst = new BinarySearchTree<>();

        bst.add(1);
        bst.add(2);
        bst.add(3);

        Iterator<Integer> iter = bst.traverse(TreeTraversalOrder.PRE_ORDER);

        assertThrows(
                ConcurrentModificationException.class,
                () -> {
                    while (iter.hasNext()) {
                        bst.remove(2);
                        iter.next();
                    }
                });
    }

    @Test
    public void concurrentModificationErrorRemovingInOrderOrder() {

        BinarySearchTree<Integer> bst = new BinarySearchTree<>();

        bst.add(1);
        bst.add(2);
        bst.add(3);

        Iterator<Integer> iter = bst.traverse(TreeTraversalOrder.IN_ORDER);

        assertThrows(
                ConcurrentModificationException.class,
                () -> {
                    while (iter.hasNext()) {
                        bst.remove(2);
                        iter.next();
                    }
                });
    }

    @Test
    public void concurrentModificationErrorRemovingPostOrder() {

        BinarySearchTree<Integer> bst = new BinarySearchTree<>();

        bst.add(1);
        bst.add(2);
        bst.add(3);

        Iterator<Integer> iter = bst.traverse(TreeTraversalOrder.POST_ORDER);

        assertThrows(
                ConcurrentModificationException.class,
                () -> {
                    while (iter.hasNext()) {
                        bst.remove(2);
                        iter.next();
                    }
                });
    }

    @Test
    public void concurrentModificationErrorRemovingLevelOrder() {

        BinarySearchTree<Integer> bst = new BinarySearchTree<>();

        bst.add(1);
        bst.add(2);
        bst.add(3);

        Iterator<Integer> iter = bst.traverse(TreeTraversalOrder.LEVEL_ORDER);

        assertThrows(
                ConcurrentModificationException.class,
                () -> {
                    while (iter.hasNext()) {
                        bst.remove(2);
                        iter.next();
                    }
                });
    }

    @Test
    public void randomRemoveTests() {

        for (int i = 0; i < LOOPS; i++) {

            int size = i;
            BinarySearchTree<Integer> tree = new BinarySearchTree<>();
            List<Integer> lst = genRandList(size);
            for (Integer value : lst) {
                tree.add(value);
            }

            Collections.shuffle(lst);
            // Remove all the elements we just placed in the tree
            for (int j = 0; j < size; j++) {
                Integer value = lst.get(j);

                assertTrue(tree.remove(value));
                assertFalse(tree.contains(value));
                assertEquals(size - j - 1, tree.size());
            }
            assertTrue(tree.isEmpty());
        }
    }

    static List<Integer> genRandList(int sz) {
        List<Integer> lst = new ArrayList<>(sz);
        for (int i = 0; i < sz; i++) lst.add(i);
        Collections.shuffle(lst);
        return lst;
    }

    public boolean validateTreeTraversal(TreeTraversalOrder trav_order, List<Integer> input) {

        List<Integer> out = new ArrayList<>();
        List<Integer> expected = new ArrayList<>();

        TestTreeNode testTree = null;
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();

        // Construct Binary Tree and test tree
        for (Integer value : input) {
            testTree = TestTreeNode.add(testTree, value);
            tree.add(value);
        }

        // Generate the expected output for the particular traversal
        switch (trav_order) {
            case PRE_ORDER:
                TestTreeNode.preOrder(expected, testTree);
                break;
            case IN_ORDER:
                TestTreeNode.inOrder(expected, testTree);
                break;
            case POST_ORDER:
                TestTreeNode.postOrder(expected, testTree);
                break;
            case LEVEL_ORDER:
                TestTreeNode.levelOrder(expected, testTree);
                break;
        }

        // Get traversal output
        Iterator<Integer> iter = tree.traverse(trav_order);
        while (iter.hasNext()) {
            out.add(iter.next());
        }

        // The output and the expected size better be the same size
        if (out.size() != expected.size()) {
            return false;
        }

        // Compare output to expected
        for (int i = 0; i < out.size(); i++) {
            if (!expected.get(i).equals(out.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Test
    public void testPreOrderTraversal() {
        for (int i = 0; i < LOOPS; i++) {
            List<Integer> input = genRandList(i);
            assertTrue(validateTreeTraversal(TreeTraversalOrder.PRE_ORDER, input));
        }
    }

    @Test
    public void testInOrderTraversal() {
        for (int i = 0; i < LOOPS; i++) {
            List<Integer> input = genRandList(i);
            assertTrue(validateTreeTraversal(TreeTraversalOrder.IN_ORDER, input));
        }
    }

    @Test
    public void testPostOrderTraversal() {
        for (int i = 0; i < LOOPS; i++) {
            List<Integer> input = genRandList(i);
            assertTrue(validateTreeTraversal(TreeTraversalOrder.POST_ORDER, input));
        }
    }

    @Test
    public void testLevelOrderTraversal() {
        for (int i = 0; i < LOOPS; i++) {
            List<Integer> input = genRandList(i);
            assertTrue(validateTreeTraversal(TreeTraversalOrder.LEVEL_ORDER, input));
        }
    }
}
