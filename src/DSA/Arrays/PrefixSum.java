package DSA.Arrays;

public class PrefixSum {
    public static void main(String[] args){

        int[] arr ={1,4,5,6,2,3};
        int n = arr.length;
        int[] prefix = new int[n];

        prefix[0]=arr[0];
        for(int i=1;i<n;i++){
            prefix[i]=prefix[i-1]+arr[i];
        }

        System.out.println(getSum(prefix,0,2));

    }

    public static int getSum(int[] prefix, int l, int r){
        if(l==0) return prefix[r];

        return prefix[r]-prefix[l-1];
    }
}
