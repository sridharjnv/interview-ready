package DSA.LinkedList;

public class SearchLL {
    static class Node{
        int data;
        Node next;

        Node(int data){
            this.data = data;
            next = null;
        }
    }

    public static int search(Node head, int value){
        if(head == null) return -1;
        if(head.data == value) return 1;

        int position = 1;
        Node curr = head;
        while(curr != null){
            if(curr.data == value) return position;
            else{
                position++;
                curr=curr.next;
            }
        }
        return -1;
    }

    public static void main(String[] args){
        Node head = new Node(10);
        head.next = new Node(20);
        head.next.next = new Node(30);

        System.out.println(search(head,10));
    }
}
