package DSA.Tree;

import java.util.LinkedList;
import java.util.Queue;

public class MaxWidth {
    static class Node{
        int key;
        Node right;
        Node left;
        Node(int x){
            this.key=x;
        }
    }
    static int maxWidth(Node root){
        if(root==null) return 0;
        Queue<Node> q = new LinkedList<>();
        q.offer(root);
        int res=0;
        while(!q.isEmpty()){
            int count = q.size();
            res = Math.max(res,count);
            for(int i=0;i<count;i++){
                Node curr = q.poll();
                if(curr.left != null) q.offer(curr.left);
                if(curr.right != null) q.offer(curr.right);
            }
        }
        return res;
    }

    public static void main(String[] args){
        Node root = new Node(10);
        root.left = new Node(20);
        root.right = new Node(30);
        root.left.left = new Node(40);
        root.left.right = new Node(50);
        root.right.left = new Node(60);
        System.out.println(maxWidth(root));
    }
}
