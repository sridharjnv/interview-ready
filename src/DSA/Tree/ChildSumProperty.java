package DSA.Tree;

public class ChildSumProperty {
    static class Node{
        int key;
        Node right;
        Node left;
        Node(int x){
            this.key = x;
            right=null;
            left=null;
        }
    }
    public static Boolean isSum(Node root){
        if(root== null) return true;
        if(root.left==null && root.right ==null) return true;
        int sum=0;
        if(root.left != null) sum+=root.left.key;
        if(root.right != null) sum+=root.right.key;

        return (sum==root.key && isSum(root.left) && isSum(root.right));

    }
    public static void main(String[] args){
        Node root = new Node(20);
        root.left = new Node(15);
        root.right = new Node(5);
        System.out.print(isSum(root));
    }
}
