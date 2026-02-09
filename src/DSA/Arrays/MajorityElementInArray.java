package DSA.Arrays;

public class MajorityElementInArray {
    public static void main(String[] args){
        int[] arr = {2,9,1,0,2,2,1,9,2};
        System.out.println(majorityElement(arr));
    }

    public static int majorityElement(int[] arr){
        int n = arr.length;
        int res=0,count=1;
        for(int i=1;i<n;i++){
            if(arr[i]==arr[res]){
                count++;
            }
            else count--;

            if(count==0){
                res=i;
                count=1;
            }
        }

        for (int j : arr) {
            if (j == arr[res]) count++;
        }
        if(count>n/2) return res;
        else return -1;
    }
}
