package com.inders.dsa.datastructures.binarysearchtree;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public final class BinarySearchTree<T extends Comparable<T>> {

    // number of nodes in the tree
    private int size = 0;

    // root node of the tree
    private Node root = null;

    // internal class for Node which store data and pointers to left and right child
    private class Node {
        T data;
        Node left, right;

        public Node(Node left, Node right, T elem) {
            this.data = elem;
            this.left = left;
            this.right = right;
        }
    }

    // return number of nodes in the tree
    public int size() {
        return size;
    }

    // return if the tree is empty
    public boolean isEmpty() {
        return size == 0;
    }

    // Add element to this tree. Return true if the
    // insertion is successful
    public boolean add(T elem) {
        if (contains(elem)) {
            // skip insertion and return false if element already exists in the tree
            return false;
        }
        root = add(root, elem);
        size++;
        return true;
    }

    // return true if the element is present in the tree
    public boolean contains(T elem) {
        return contains(root, elem);
    }

    // remove elem from the tree and return true if removal is successful
    public boolean remove(T elem) {
        if (contains(elem)) {
            root = remove(root, elem);
            size--;
            return true;
        }
        return false;
    }

    // remove elem from subtree with given root
    private Node remove(Node root, T elem) {
        if (root == null) {
            return null;
        }

        int cmp = elem.compareTo(root.data);

        if (cmp < 0) {
            // traverse the left subtree if elem is smaller than root
            root.left = remove(root.left, elem);
        } else if (cmp > 0) {
            // traverse the right subtree if the elem is greater than root
            root.right = remove(root.right, elem);
        } else {
            // found the node to be removed

            if (root.left == null) {
                // if left subtree is null or both right and left subtrees are null
                // then simply swap the current node with the right child
                return root.right;
            } else if (root.right == null) {
                // if right subtree is null
                // then simply swap the current node with the left child
                return root.left;
            } else {
                // here both left and right subtrees are present so either
                // we have to find the largest node from the left subtree
                // (which is smaller than all the nodes in right subtree) or
                // find the smallest from the right subtree (which is largest
                // than all the nodes in the left subtree)

//                // find max from the left subtree
//                final Node max = findMax(root.left);
//                root.data = max.data;
//                // remove the max node we found from the left subtree
//                root.left = remove(root.left, max.data);

                // find min from the right subtree
                final Node min = findMin(root.right);
                root.data = min.data;
                // remove the max node we found from the left subtree
                root.right = remove(root.right, min.data);
            }
        }
        return root;
    }

    // get height of the tree
    public int height() {
        return height(root);
    }

    // height of subtree with given root
    private int height(Node root) {
        if (root == null) {
            // breaking condition
            return 0;
        }
        // get max of left and right subtree height and add 1 for current level
        return Math.max(height(root.left), height(root.right)) + 1;
    }

    // find node with minimum data in this subtree
    private Node findMin(Node root) {
        while (root.left != null) {
            root = root.left;
        }
        return root;
    }

    // find node with maximum data in this subtree
    private Node findMax(Node root) {
        while (root.right != null) {
            root = root.right;
        }
        return root;
    }

    // add new node to the subtree with given root
    private Node add(Node root, T elem) {
        if (root == null) {
            // we have reached the leaf node, create a new node and add it here
            root = new Node(null, null, elem);
        } else {
            if (elem.compareTo(root.data) < 0) {
                // add element in left subtree if it is smaller than root
                root.left = add(root.left, elem);
            } else {
                // else add into right subtree as element is greater than root
                // here the assumption is that elem does not preexist in subtree
                root.right = add(root.right, elem);
            }
        }
        return root;
    }

    // check if elem is present in the subtree with given root
    private boolean contains(Node root, T elem) {
        // if root is null that means we have reached end of the tree
        // and the elem is not present in the tree
        if (root == null) {
            return false;
        }

        int cmp = elem.compareTo(root.data);
        if (cmp < 0) {
            // if elem is less than root then search in left subtree
            return contains(root.left, elem);
        }
        if (cmp > 0) {
            // if elem is greater than root then search in right subtree
            return contains(root.right, elem);
        }
        // return true here we found the elem
        return true;
    }

    // This method returns an iterator for a given TreeTraversalOrder.
    // The ways in which you can traverse the tree are in four different ways:
    // preorder, inorder, postorder and levelorder.
    public Iterator<T> traverse(TreeTraversalOrder order) {
        switch (order) {
            case PRE_ORDER:
                return preOrderTraversal();
            case IN_ORDER:
                return inOrderTraversal();
            case POST_ORDER:
                return postOrderTraversal();
            case LEVEL_ORDER:
                return levelOrderTraversal();
            default:
                return null;
        }
    }

    // Returns as iterator to traverse the tree in preorder
    private Iterator<T> preOrderTraversal() {

        final int expectedNodeCount = size;
        final Stack<Node> stack = new Stack<>();
        stack.push(root);

        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                if (expectedNodeCount != size) {
                    throw new java.util.ConcurrentModificationException();
                }
                return root != null && !stack.isEmpty();
            }

            @Override
            public T next() {
                if (expectedNodeCount != size) {
                    throw new java.util.ConcurrentModificationException();
                }
                Node top = stack.pop();
                if (top.right != null) {
                    stack.push(top.right);
                }
                if (top.left != null) {
                    stack.push(top.left);
                }
                return top.data;
            }
        };
    }

    // Returns as iterator to traverse the tree in inorder
    private Iterator<T> inOrderTraversal() {

        final int expectedNodeCount = size;
        final Stack<Node> stack = new Stack<>();
        stack.push(root);

        return new Iterator<T>() {
            Node trav = root;

            @Override
            public boolean hasNext() {
                if (expectedNodeCount != size) {
                    throw new java.util.ConcurrentModificationException();
                }
                return root != null && !stack.isEmpty();
            }

            @Override
            public T next() {
                if (expectedNodeCount != size) {
                    throw new java.util.ConcurrentModificationException();
                }
                // dig to the left
                while (trav != null && trav.left != null) {
                    stack.push(trav.left);
                    trav = trav.left;
                }
                Node top = stack.pop();
                if (top.right != null) {
                    stack.push(top.right);
                    trav = top.right;
                }
                return top.data;
            }
        };
    }

    // Returns as iterator to traverse the tree in postorder
    private Iterator<T> postOrderTraversal() {

        final int expectedNodeCount = size;
        final Stack<Node> stack1 = new Stack<>();
        final Stack<Node> stack2 = new Stack<>();
        stack1.push(root);
        while (!stack1.isEmpty()) {
            Node top = stack1.pop();
            if (top != null) {
                stack2.push(top);
                if (top.left != null) {
                    stack1.push(top.left);
                }
                if (top.right != null) {
                    stack1.push(top.right);
                }
            }
        }

        return new Iterator<T>() {

            @Override
            public boolean hasNext() {
                if (expectedNodeCount != size) {
                    throw new java.util.ConcurrentModificationException();
                }
                return root != null && !stack2.isEmpty();
            }

            @Override
            public T next() {
                if (expectedNodeCount != size) {
                    throw new java.util.ConcurrentModificationException();
                }
                return stack2.pop().data;
            }
        };
    }

    // Returns as iterator to traverse the tree in levelorder
    private Iterator<T> levelOrderTraversal() {

        final int expectedNodeCount = size;
        final Queue<Node> queue = new LinkedList<>();
        queue.offer(root);

        return new Iterator<T>() {

            @Override
            public boolean hasNext() {
                if (expectedNodeCount != size) {
                    throw new java.util.ConcurrentModificationException();
                }
                return root != null && !queue.isEmpty();
            }

            @Override
            public T next() {
                if (expectedNodeCount != size) {
                    throw new java.util.ConcurrentModificationException();
                }
                Node front = queue.poll();
                if (front.left != null) {
                    queue.offer(front.left);
                }
                if (front.right != null) {
                    queue.offer(front.right);
                }
                return front.data;
            }
        };
    }
}
