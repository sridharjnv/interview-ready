package DSA.Arrays;

public class LeadersInArray {
    public static void main(String[] args){
        int[] arr = {7,10,4,5,10,9,3,2};
        int n = arr.length;
//        for(int i=0;i<n;i++){
//            boolean flag = false;
//            for(int j=i+1;j<n;j++){
//                if(arr[i]<=arr[j]){
//                    flag = true;
//                    break;
//                }
//            }
//
//            if(!flag){
//                System.out.println(arr[i]);
//            }
//        }
        int curr_lr = arr[n-1];
        System.out.println(curr_lr);
        for(int i=n-2;i>=0;i--){
            if(arr[i]>curr_lr){
                curr_lr=arr[i];
                System.out.println(curr_lr);
            }
        }
    }

}
