package DSA.Stack;

public class KStacks {
    int[] arr, top, next;
    int free_top=0,k,cap;
    KStacks(int k, int n){
        this.k=k;
        this.cap=n;

        arr = new int[n];
        top = new int[k];
        next = new int[n];

        for(int i=0;i<k;i++){
            top[i]=-1;
        }
        for(int i=0;i<n-1;i++){
            next[i] = i+1;
        }
        next[n-1]=-1;
    }

    void push(int sn,int x){
        if(free_top == -1){
            System.out.println("Stack Overflow");
            return;
        }

        int i = free_top;
        free_top = next[i];
        next[i] = top[sn];
        top[sn]= i;
        arr[i]=x;
    }
    int pop(int sn){
        if(top[sn] == -1){
            System.out.println("Stack Underflow");
            return -1;
        }

        int i = top[sn];
        top[sn]=next[i];
        next[i]=free_top;
        free_top=i;
        return arr[i];
    }
    boolean isEmpty(int sn){
        return top[sn]==-1;
    }
}
