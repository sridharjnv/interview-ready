package DSA.LinkedList;

public class InsertAtBegin {
    static class Node{
        int data;
        Node next;

        Node(int data){
            this.data = data;
            this.next = null;
        }
    }

    public static Node insertBegin(Node head, int data){
        Node newNode = new Node(data);
        newNode.next = head;
        return head;
    }

    public static void printList(Node head){
        Node curr = head;

        while(curr != null){
            System.out.println(curr.data);
            curr = curr.next;
        }
    }

    public static void main(String[] args){
        Node head = null;
        head = insertBegin(head,30);
        head = insertBegin(head,20);
        head = insertBegin(head,10);

        printList(head);
    }
}
