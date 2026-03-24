package DSA.LinkedList;

public class DeleteFunctionLinkedList {
    static class Node{
        int data;
        Node next;

        Node(int data){
            this.data = data;
            next = null;
        }
    }

    public static Node deleteAtBegin(Node head){
        if(head == null) return null;

        head = head.next;
        return head;
    }

    public static void printList(Node head){
        Node curr = head;
        while(curr != null){
            System.out.print(curr.data + " -> ");
            curr=curr.next;
        }
        System.out.println("null");
    }

    public static Node deleteAtLast(Node head){
        if(head == null) return null;

        if(head.next == null) return null;

        Node curr = head;
        while(curr.next.next != null){
            curr = curr.next;
        }
        curr.next = null;
        return head;
    }

    public static void main(String[] args){
        Node head = new Node(10);
        head.next = new Node(20);
        head.next.next = new Node(30);

        System.out.println("Original List");
        printList(head);

        head = deleteAtLast(head);

        System.out.println("After List");
        printList(head);
    }

}
