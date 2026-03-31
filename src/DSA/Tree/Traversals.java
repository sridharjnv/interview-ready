package DSA.Tree;

import java.util.LinkedList;
import java.util.Queue;

public class Traversals {
    static class Node{
        int key;
        Node left;
        Node right;
        Node(int x){
            key = x;
        }
    }
    static void inorder(Node root){
        if(root != null){
            inorder(root.left);
            System.out.print(root.key+ " ");
            inorder(root.right);
        }
    }
    static void preorder(Node root){
        if(root != null){
            System.out.print(root.key+ " ");
            preorder(root.left);
            preorder(root.right);
        }
    }
    static void postorder(Node root){
        if(root != null){
            postorder(root.left);
            postorder(root.right);
            System.out.print(root.key+ " ");
        }
    }
    static int height(Node root){
        if(root == null) return 0;
        else return Math.max(height(root.left),height(root.right))+1;
    }
    static void printKLevel(Node root, int k){
        if(root == null) return;
        if(k==0) System.out.println(root.key+" ");
        else{
            printKLevel(root.left,k-1);
            printKLevel(root.right,k-1);
        }
    }
    static void printLevelOrderTraversal(Node root){
        if(root==null) return;
        Queue<Node> q = new LinkedList<>();
        q.add(root);
        while(!q.isEmpty()){
            Node curr = q.peek();
            q.poll();
            System.out.print(curr.key+" ");
            if(curr.left != null) q.add(curr.left);
            if(curr.right != null) q.add(curr.right);
        }
    }
    static void printLevelOrderLineByLine(Node root){
        if(root == null) return;
        Queue<Node> q = new LinkedList<>();
        q.add(root);
        while(!q.isEmpty()){
            int count = q.size();
            for(int i=0;i<count;i++){
                Node curr = q.poll();
                assert curr != null;
                System.out.print(curr.key+" ");
                if(curr.left != null) q.add(curr.left);
                if(curr.right != null) q.add(curr.right);
            }
            System.out.println();
        }
    }
    static int getSize(Node root){
        if(root == null) return 0;
        else{
            return 1+getSize(root.left)+getSize(root.right);
        }
    }
    static int getMax(Node root){
        if(root==null) return Integer.MIN_VALUE;
        else{
            return Math.max(root.key, Math.max(getMax(root.left),getMax(root.right)));
        }
    }
    static int maxLevel=0;
    static void printLeftNodeAtEveryLevel(Node root, int level){
        if(root==null) return;
        if(maxLevel<level){
            System.out.print(root.key+" ");
            maxLevel=level;
        }
        printLeftNodeAtEveryLevel(root.left,level+1);
        printLeftNodeAtEveryLevel(root.right,level+1);
    }
    static void printLeftNode(Node root){
        maxLevel=0;
        printLeftNodeAtEveryLevel(root,1);
    }
    public static void main(String[] args){
        Node root = new Node(10);
        root.left = new Node(20);
        root.right = new Node(30);
        root.right.left = new Node(40);
        root.right.right = new Node(50);
        inorder(root);
        System.out.println();
        preorder(root);
        System.out.println();
        postorder(root);
        System.out.println();
        System.out.println(height(root));
        printKLevel(root,1);
        printLevelOrderTraversal(root);
        System.out.println();
        printLevelOrderLineByLine(root);
        System.out.println(getSize(root));
        System.out.println(getMax(root));
        printLeftNode(root);
    }
}
