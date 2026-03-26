package DSA.Queue;

public class QueueWithArray {
    int size,cap;
    int[] arr;
    QueueWithArray(int c){
        this.cap=c;
        arr = new int[cap];
    }
    boolean isFull(){
        return size==cap;
    }
    boolean isEmpty(){
        return size==0;
    }
    void enqueue(int x){
        if(isFull()) return;
        arr[size] = x;
        size++;
    }
    void deque(){
        if(isEmpty()) return;
        for(int i=0;i<size-1;i++){
            arr[i]=arr[i+1];
        }
        size--;
    }

}
