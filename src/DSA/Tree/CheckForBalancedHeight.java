package DSA.Tree;

public class CheckForBalancedHeight {
    static class Node{
        int key;
        Node right;
        Node left;
        Node(int x){
            this.key=x;
        }
    }

    static int isBalanced(Node root){
        if(root == null) return 0;
        int lh = isBalanced(root.left);
        if(lh==1) return -1;
        int rh = isBalanced(root.right);
        if(rh==1) return -1;
        if(Math.abs(lh-rh)>1) return -1;
        else return Math.max(lh,rh)+1;

    }
    public static void main(String[] args){
        Node root = new Node(20);
        root.right = new Node(30);
        root.left = new Node(40);
        root.left.left = new Node(50);
        System.out.println(isBalanced(root));
    }
}
