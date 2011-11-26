import java.util.Iterator;
import java.util.Vector;

/**
 * Author: Volkman
 * Date: Sep 9, 2010
 */
public class BinarySearchTree<T> implements Iterable<T> {

    public void insert(T data, int key) {
        add(root, new Node<T>(data, key), root, 0);
    }

    public T find(int key) {
        return search(root, key);
    }

    public void remove(int key) {
        delete(root, key, root, 0);
    }

    public BinarySearchTree() {
        root = null;
    }

    public Iterator<T> iterator() {
        Vector<T> vector = new Vector<T>();
        traverse(vector, root);
        return vector.iterator();
    }

    private void add(Node<T> root, Node<T> node, Node<T> parent, int way) {

        if (root == null) {
            switch (way) {
                case 0:
                    this.root = node;
                    break;
                case 1:
                    parent.left = node;
                    break;
                case 2:
                    parent.right = node;
                    break;
            }
            System.out.println("Node added.");
        } else {
            if (node.key < root.key)
                add(root.left, node, root, 1);
            else
                add(root.right, node, root, 2);
        }
    }

    private T search(Node<T> root, int key) {
        T res = null;
        if (root == null) {
            System.out.println("The node hasn't found.");
        } else {
            if (root.key == key) {
                System.out.println("Node founded. Data: " + root.data);
                return root.data;
            }
            if (root.key > key)
                res = search(root.left, key);
            else res = search(root.right, key);
        }
        return res;
    }

    private void delete(Node<T> root, int key, Node<T> parent, int way) {
        if (root == null)
            System.out.println("The key hasn't found.");
        else {
            if (root.key < key)
                delete(root.right, key, root, 2);
            if (root.key > key)
                delete(root.left, key, root, 1);
            if (root.key == key) {
                if (root.left == null && root.right == null)
                    switch (way) {
                        case 1:
                            parent.left = null;
                        case 2:
                            parent.right = null;
                    }
                if (root.left == null && root.right != null)
                    root = root.right;
                if (root.left != null && root.right == null)
                    root = root.left;
                if (root.left != null && root.right != null) {
                    Node<T> cur = root.right;
                    while (cur.left != null)
                        cur = cur.left;
                    cur.left = root.left;
                    root = root.right;
                    System.out.println("Node deleted.");
                }
            }
        }
    }

    private class Node<T> {
        public T data;
        public int key;
        public Node<T> left;
        public Node<T> right;

        public Node(T data, int key) {
            this.data = data;
            this.key = key;
            this.left = null;
            this.right = null;
        }
    }

    private void traverse(Vector<T> vector, Node<T> curr) {
        if (curr != null) {
            traverse(vector, curr.left);
            vector.add(curr.data);
            traverse(vector, curr.right);
        }
    }

    private Node<T> root;
}
