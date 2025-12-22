package org.example.adsproject;
import java.util.LinkedList;
import java.util.Queue;
class BTreeNode {
    int[] keys;
    int t; // Minimum degree
    BTreeNode[] children;
    int n;
    boolean isLeaf;
    BTreeNode(int t, boolean isLeaf) {
        this.t = t;
        this.isLeaf = isLeaf;
        this.keys = new int[2 * t - 1];
        this.children = new BTreeNode[2 * t];
        this.n = 0;
    }
}
public class BTree {
    private BTreeNode root;
    private int t; // Minimum degree
    public BTree(int t) {
        this.root = null;
        this.t = t;
    }
    public void insert(int key) {
        if (root == null) {
            root = new BTreeNode(t, true);
            root.keys[0] = key;
            root.n = 1;
        } else {
            if (root.n == 2 * t - 1) {
                BTreeNode s = new BTreeNode(t, false);
                s.children[0] = root;
                splitChild(s, 0, root);
                int i = (s.keys[0] < key) ? 1 : 0;
                insertNonFull(s.children[i], key);
                root = s;
            } else {
                insertNonFull(root, key);
            }
        }
    }
    private void insertNonFull(BTreeNode node, int key) {
        int i = node.n - 1;
        if (node.isLeaf) {
            while (i >= 0 && key < node.keys[i]) {
                node.keys[i + 1] = node.keys[i];
                i--;
            }
            node.keys[i + 1] = key;
            node.n++;
        } else {
            while (i >= 0 && key < node.keys[i]) {
                i--;
            }
            i++;
            if (node.children[i].n == 2 * t - 1) {
                splitChild(node, i, node.children[i]);
                if (key > node.keys[i]) {
                    i++;
                }
            }
            insertNonFull(node.children[i], key);
        }
    }
    private void splitChild(BTreeNode parent, int i, BTreeNode child) {
        BTreeNode z = new BTreeNode(t, child.isLeaf);
        z.n = t - 1;
        for (int j = 0; j < t - 1; j++) {
            z.keys[j] = child.keys[j + t];
        }
        if (!child.isLeaf) {
            for (int j = 0; j < t; j++) {
                z.children[j] = child.children[j + t];
            }
        }
        child.n = t - 1;
        for (int j = parent.n; j >= i + 1; j--) {
            parent.children[j + 1] = parent.children[j];
        }
        parent.children[i + 1] = z;
        for (int j = parent.n - 1; j >= i; j--) {
            parent.keys[j + 1] = parent.keys[j];
        }
        parent.keys[i] = child.keys[t - 1];
        parent.n++;
    }
    public void delete(int key) {
        if (root == null) {
            System.out.println("The tree is empty");
            return;
        }
        delete(root, key);
        if (root.n == 0) {
            root = (root.isLeaf) ? null : root.children[0];
        }
    }
    private void delete(BTreeNode node, int key) {
        int idx = findKey(node, key);
        if (idx < node.n && node.keys[idx] == key) {
            if (node.isLeaf) {
                for (int i = idx + 1; i < node.n; i++) {
                    node.keys[i - 1] = node.keys[i];
                }
                node.n--;
            } else {
                if (node.children[idx].n >= t) {
                    int pred = getPredecessor(node, idx);
                    node.keys[idx] = pred;
                    delete(node.children[idx], pred);
                } else if (node.children[idx + 1].n >= t) {
                    int succ = getSuccessor(node, idx);
                    node.keys[idx] = succ;
                    delete(node.children[idx + 1], succ);
                } else {
                    merge(node, idx);
                    delete(node.children[idx], key);
                }
            }
        } else {
            if (node.isLeaf) {
                System.out.println("The key " + key + " is not in the tree");
                return;
            }
            boolean flag = (idx == node.n);
            if (node.children[idx].n < t) {
                fill(node, idx);
            }
            if (flag && idx > node.n) {
                delete(node.children[idx - 1], key);
            } else {
                delete(node.children[idx], key);
            }
        }
    }
    private int findKey(BTreeNode node, int key) {
        int idx = 0;
        while (idx < node.n && node.keys[idx] < key) {
            idx++;
        }
        return idx;
    }
    private int getPredecessor(BTreeNode node, int idx) {
        BTreeNode cur = node.children[idx];
        while (!cur.isLeaf) {
            cur = cur.children[cur.n];
        }
        return cur.keys[cur.n - 1];
    }
    private int getSuccessor(BTreeNode node, int idx) {
        BTreeNode cur = node.children[idx + 1];
        while (!cur.isLeaf) {
            cur = cur.children[0];
        }
        return cur.keys[0];
    }
    private void merge(BTreeNode node, int idx) {
        BTreeNode child = node.children[idx];
        BTreeNode sibling = node.children[idx + 1];
        child.keys[t - 1] = node.keys[idx];
        for (int i = 0; i < sibling.n; i++) {
            child.keys[i + t] = sibling.keys[i];
        }
        if (!child.isLeaf) {
            for (int i = 0; i <= sibling.n; i++) {
                child.children[i + t] = sibling.children[i];
            }
        }
        for (int i = idx + 1; i < node.n; i++) {
            node.keys[i - 1] = node.keys[i];
        }
        for (int i = idx + 2; i <= node.n; i++) {
            node.children[i - 1] = node.children[i];
        }
        child.n += sibling.n + 1;
        node.n--;
    }
    private void fill(BTreeNode node, int idx) {
        if (idx != 0 && node.children[idx - 1].n >= t) {
            borrowFromPrev(node, idx);
        } else if (idx != node.n && node.children[idx + 1].n >= t) {
            borrowFromNext(node, idx);
        } else {
            if (idx != node.n) {
                merge(node, idx);
            } else {
                merge(node, idx - 1);
            }
        }
    }
    private void borrowFromPrev(BTreeNode node, int idx) {
        BTreeNode child = node.children[idx];
        BTreeNode sibling = node.children[idx - 1];
        for (int i = child.n - 1; i >= 0; i--) {
            child.keys[i + 1] = child.keys[i];
        }
        if (!child.isLeaf) {
            for (int i = child.n; i >= 0; i--) {
                child.children[i + 1] = child.children[i];
            }
        }
        child.keys[0] = node.keys[idx - 1];
        if (!child.isLeaf) {
            child.children[0] = sibling.children[sibling.n];
        }
        node.keys[idx - 1] = sibling.keys[sibling.n - 1];
        child.n++;
        sibling.n--;
    }
    private void borrowFromNext(BTreeNode node, int idx) {
        BTreeNode child = node.children[idx];
        BTreeNode sibling = node.children[idx + 1];
        child.keys[child.n] = node.keys[idx];
        if (!child.isLeaf) {
            child.children[child.n + 1] = sibling.children[0];
        }
        node.keys[idx] = sibling.keys[0];
        for (int i = 1; i < sibling.n; i++) {
            sibling.keys[i - 1] = sibling.keys[i];
        }
        if (!sibling.isLeaf) {
            for (int i = 1; i <= sibling.n; i++) {
                sibling.children[i - 1] = sibling.children[i];
            }
        }
        child.n++;
        sibling.n--;
    }
    public String toDOT() {
        if (root == null) {
            return "digraph G {\n\tempty;\n}";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("digraph G {\n");
        generateDOT(root, sb, 0);
        sb.append("}\n");
        return sb.toString();
    }
    private int generateDOT(BTreeNode node, StringBuilder sb, int id) {
        int currentId = id;
        sb.append("\tnode").append(currentId).append(" [label=\"");
        for (int i = 0; i < node.n; i++) {
            sb.append(node.keys[i]);
            if (i < node.n - 1) sb.append("|");
        }
        sb.append("\"];\n");
        if (!node.isLeaf) {
            for (int i = 0; i <= node.n; i++) {
                int childId = ++id;
                sb.append("\tnode").append(currentId).append(" -> node").append(childId).append(";\n");
                id = generateDOT(node.children[i], sb, childId);
            }
        }
        return id;
    }
    public boolean search(int key) {
        return search(root, key);
    }
    private boolean search(BTreeNode node, int key) {
        if (node == null) {
            return false;
        }
        int i = 0;
        while (i < node.n && key > node.keys[i]) {
            i++;
        }
        if (i < node.n && node.keys[i] == key) {
            return true;
        }
        if (node.isLeaf) {
            return false;
        }
        return search(node.children[i], key);
    }
    public int getMin() {
        if (root == null) {
            throw new IllegalStateException("The tree is empty.");
        }
        return getMin(root);
    }
    private int getMin(BTreeNode node) {
        if (node.isLeaf) {
            return node.keys[0];
        }
        return getMin(node.children[0]);
    }
    public int getMax() {
        if (root == null) {
            throw new IllegalStateException("The tree is empty.");
        }
        return getMax(root);
    }
    private int getMax(BTreeNode node) {
        if (node.isLeaf) {
            return node.keys[node.n - 1];
        }
        return getMax(node.children[node.n]);
    }
    public Integer getPredecessor(int key) {
        BTreeNode node = root;
        Integer predecessor = null;
        while (node != null) {
            int i = 0;
            while (i < node.n && key > node.keys[i]) {
                predecessor = node.keys[i];
                i++;
            }
            if (i < node.n && node.keys[i] == key) {
                if (!node.isLeaf) {
                    return findMax(node.children[i]);
                }
                break;
            }
            node = node.isLeaf ? null : node.children[i];
        }
        return predecessor;
    }
    public Integer getSuccessor(int key) {
        BTreeNode node = root;
        Integer successor = null;
        while (node != null) {
            int i = 0;
            while (i < node.n && key >= node.keys[i]) {
                i++;
            }
            if (i < node.n) {
                successor = node.keys[i];
            }
            if (i > 0 && node.keys[i - 1] == key && !node.isLeaf) {
                return findMin(node.children[i]);
            }
            node = node.isLeaf ? null : node.children[i];
        }
        return successor;
    }
    private int findMax(BTreeNode node) {
        while (!node.isLeaf) {
            node = node.children[node.n];
        }
        return node.keys[node.n - 1];
    }
    private int findMin(BTreeNode node) {
        while (!node.isLeaf) {
            node = node.children[0];
        }
        return node.keys[0];
    }
    public String inorderTraversal() {
        if (root == null) {
            return "The tree is empty.";
        }
        StringBuilder result = new StringBuilder();
        inorderTraversal(root, result);
        return result.toString();
    }
    private void inorderTraversal(BTreeNode node, StringBuilder result) {
        int i;
        for (i = 0; i < node.n; i++) {
            if (!node.isLeaf) {
                inorderTraversal(node.children[i], result);
            }
            result.append(node.keys[i]).append(" ");
        }
        if (!node.isLeaf) {
            inorderTraversal(node.children[i], result);
        }
    }
}