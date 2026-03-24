package DSA.LinkedList;

public class DoublyLinkedList {
    static class Node{
        int data;
        Node prev;
        Node next;

        Node(int data){
            this.data = data;
            prev = null;
            next = null;
        }
    }

    public static Node insertAtEnd(Node head,int data){
        Node newnode = new Node(data);
        if(head == null) return newnode;

        Node curr = head;
        while(curr.next != null){
            curr=curr.next;
        }
        curr.next = newnode;
        newnode.prev = curr;
        return head;
    }

    public static Node reverseList(Node head){
        if(head == null || head.next==null) return head;

        Node temp = null;
        Node curr = head;
        while(curr != null){
            temp = curr.prev;
            curr.prev= curr.next;
            curr.next = temp;

            curr=curr.prev;
        }
        if(temp != null){
            head=temp.prev;
        }
        return head;
    }

    public static Node insertAtBegin(Node head, int data){
        Node newnode = new Node(data);
        if(head == null) return newnode;

        newnode.next = head;
        head.prev = newnode;
        return newnode;
    }

    public static void printList(Node head){
        Node curr = head;
        while(curr != null){
            System.out.print(curr.data+" -> ");
            curr=curr.next;
        }
        System.out.println();
    }

    public static Node deleteAtStart(Node head){
        if(head == null || head.next == null) return null;

       head = head.next;
       head.prev = null;
       return head;
    }

    public static void main(String[] args){
        Node head = new Node(10);
        Node temp1 = new Node(20);
        Node temp2 = new Node(30);

        head.next = temp1;
        temp1.prev = head;
        temp1.next = temp2;
        temp2.prev = temp1;

        printList(head);
        head = insertAtEnd(head,40);
        printList(head);
        head = reverseList(head);
        printList(head);
    }
}
