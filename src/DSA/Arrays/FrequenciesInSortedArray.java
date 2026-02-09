package DSA.Arrays;

public class FrequenciesInSortedArray {
    public static void main(String[] args){
        int[] arr = {1,1,1,2,2,3,4,4,4,4};
//        frequencyCount(arr);
        V2(arr);

    }

    public static void frequencyCount(int[] arr){
        int n = arr.length;
        int freq = 1;
        int i =1;

        while(i<n){
            while(i<n && arr[i] == arr[i-1]){
                freq++;
                i++;
            }
            System.out.println(arr[i-1]+"-"+freq);
            freq=1;
            i++;
        }

        if(n==1 || arr[n-1] != arr[n-2]) System.out.println(arr[n-1]+"-"+1);

    }

    public static void V2(int[] arr){
        int freq=1;
        int n= arr.length;
        for(int i=1;i<n;i++){
            if(arr[i]==arr[i-1]){
                freq++;
            }
            else {
                System.out.println(arr[i-1]+"-"+freq);
                freq=1;
            }
        }
        System.out.println(arr[n-1]+"-"+freq);
    }
}
