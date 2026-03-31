package DSA.Tree;

public class BST {

    static class Node {
        int key;
        Node left, right;

        Node(int x) {
            this.key = x;
            left = right = null;
        }
    }
    // Recursive Search
    static boolean search(Node root, int x) {
        if (root == null) return false;

        if (root.key == x) return true;
        else if (x < root.key) return search(root.left, x);
        else return search(root.right, x);
    }
    // Iterative Search
    static boolean iterativeSearch(Node root, int x) {
        while (root != null) {
            if (root.key == x) return true;
            else if (x < root.key) root = root.left;
            else root = root.right;
        }
        return false;
    }
    // Insert in BST
    static Node insert(Node root, int x) {
        if (root == null) return new Node(x);

        if (x < root.key)
            root.left = insert(root.left, x);
        else if (x > root.key)
            root.right = insert(root.right, x);

        return root;
    }
    // Find Successor (smallest in right subtree)
    static Node successor(Node root) {
        Node curr = root.right;
        while (curr != null && curr.left != null) {
            curr = curr.left;
        }
        return curr;
    }
    // Delete node from BST
    static Node delete(Node root, int x) {
        if (root == null) return null;

        if (x < root.key)
            root.left = delete(root.left, x);
        else if (x > root.key)
            root.right = delete(root.right, x);
        else {
            // Node found
            if (root.left == null) return root.right;
            else if (root.right == null) return root.left;
            else {
                Node succ = successor(root);
                root.key = succ.key;
                root.right = delete(root.right, succ.key);
            }
        }
        return root;
    }
    // Floor in BST (largest <= x)
    static Node floor(Node root, int x) {
        Node res = null;

        while (root != null) {
            if (root.key == x) return root;

            if (root.key > x)
                root = root.left;
            else {
                res = root;
                root = root.right;
            }
        }
        return res;
    }
    // Kth Smallest Element
    static int count = 0;

    static void printKthSmallest(Node root, int k) {
        if (root == null) return;

        printKthSmallest(root.left, k);

        count++;
        if (count == k) {
            System.out.println("Kth smallest: " + root.key);
            return;
        }

        printKthSmallest(root.right, k);
    }

    // Inorder Traversal (for testing)
    static void inorder(Node root) {
        if (root != null) {
            inorder(root.left);
            System.out.print(root.key + " ");
            inorder(root.right);
        }
    }
     static boolean isBst(Node root, int min, int max){
        if(root==null) return true;
        return (root.key > min &&
                root.key < max &&
                isBst(root.left,min,root.key) &&
                isBst(root.right,max,root.key));
     }
     static int prev = Integer.MIN_VALUE;
    static boolean checkIsBst(Node root){
        if(root==null) return true;
        if(!checkIsBst(root.left)) return false;
        if(root.key<=prev) return false;
        prev=root.key;
        return checkIsBst(root.right);
    }

    public static void main(String[] args) {

        Node root = null;

        // Insert elements
        root = insert(root, 10);
        root = insert(root, 5);
        root = insert(root, 15);
        root = insert(root, 2);
        root = insert(root, 7);
        root = insert(root, 12);
        root = insert(root, 20);

        // Print BST
        System.out.print("Inorder Traversal: ");
        inorder(root);
        System.out.println();

        // Search
        System.out.println("Search 7: " + search(root, 7));
        System.out.println("Search 100: " + iterativeSearch(root, 100));

        // Floor
        Node f = floor(root, 13);
        System.out.println("Floor of 13: " + (f != null ? f.key : "No floor"));

        // Kth Smallest
        count = 0; // reset before calling
        printKthSmallest(root, 3);

        // Delete
        root = delete(root, 10);
        System.out.print("After deleting 10: ");
        inorder(root);
    }
}