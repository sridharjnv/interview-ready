package DSA.LinkedList;

public class InsertAtPos {
    static class Node{
        int data;
        Node next;
        Node(int data){
            this.data=data;
            next = null;
        }
    }

    public static Node insertAtPosition(Node head, int data, int position){
        Node newnode = new Node(data);
        if(position ==0) {
            newnode.next = head;
            return newnode;
        }

        Node curr = head;
        for(int i=0;i<position-1 && curr != null; i++){
            curr=curr.next;
        }

        if(curr == null) {
            System.out.println("Position is invalid");
            return head;
        }

        newnode.next = curr.next;
        curr.next=newnode;

        return head;
    }

    public static void printList(Node head){
        Node curr = head;
        while(curr != null){
            System.out.print(curr.data+ " -> ");
            curr= curr.next;
        }
        System.out.println("null");
    }


    public static void main(String[] args){
        Node head = new Node(10);
        head.next = new Node(20);
        head.next.next = new Node(30);

        System.out.println("Original List:");
        printList(head);

        //insert 25 at position 2
        head = insertAtPosition(head,25,2);

        System.out.println("After List:");
        printList(head);
    }
}
