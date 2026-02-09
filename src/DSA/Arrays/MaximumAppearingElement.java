package DSA.Arrays;

public class MaximumAppearingElement {
    static final int MAX = 1000000;
    public static void main(String[] args){
        int[] L ={1,4,7};
        int[] R ={3,7,10};

        maximumAppearingElement(L,R);
    }

    public static void maximumAppearingElement(int[] L,int[] R){

        int n =L.length;
        int[] diff = new int[MAX+2];

        int maxElement=-1;
        for(int i=0;i<n;i++){
            diff[L[i]]+=1;
            diff[R[i]+1]-=1;
            maxElement=Math.max(maxElement,R[i]);
        }

        int sum=diff[0];
        int index=0;
        for(int i=1;i<=maxElement;i++){
            diff[i]+=diff[i-1];
            if(sum<diff[i]){
                sum=diff[i];
                index=i;
            }
        }
        System.out.println(index);
    }
}
