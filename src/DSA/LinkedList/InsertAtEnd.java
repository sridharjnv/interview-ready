package DSA.LinkedList;

public class InsertAtEnd {
    static class Node{
        int data;
        Node next;

        Node(int data){
            this.data=data;
            next=null;
        }
    }

    public static Node insertAtEnd(Node head,int data){
        Node newnode = new Node(data);

        if(head == null) return newnode;
        Node curr = head;
        while(curr.next != null){
            curr= curr.next;
        }
        curr.next = newnode;
        return head;
    }

    public static void printList(Node head){
        Node curr = head;
        while(curr != null){
            System.out.println(curr.data);
            curr=curr.next;
        }
    }


    public static void main(String[] args){
        Node head = new Node(10);
        head.next = new Node(20);
        head.next.next = new Node(30);

        head = insertAtEnd(head,40);

        printList(head);
    }
}
