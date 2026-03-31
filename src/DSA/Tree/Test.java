package DSA.Tree;

public class Test {
    static class Node{
        int key;
        Node right;
        Node left;
        Node(int x) {
            this.key = x;
            right = null;
            left = null;
        }
    }
    public static void main(String[] args){
        Node root = new Node(10);
        root.left = new Node(20);
        root.right = new Node(30);
        root.left.left = new Node(40);
        root.right.right = new Node(50);
    }
}
