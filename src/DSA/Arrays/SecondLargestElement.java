package DSA.Arrays;

public class SecondLargestElement {
    public static void main(String[] args){

        int[] arr = {35, 23};
        int n = arr.length;
        int maxIndex = 0;
        int secondMaxIndex = -1;

        for(int i=1;i<n;i++){
            if(arr[i]>arr[maxIndex]){
                secondMaxIndex = maxIndex;
                maxIndex = i;
            }
            else if (arr[i] != arr[maxIndex]){
                if(secondMaxIndex == -1 ||(arr[i] > arr[secondMaxIndex] && arr[i]< arr[maxIndex])){
                    secondMaxIndex = i;
                }
            }
        }

        if(secondMaxIndex != -1){
            System.out.println("Second largest element:"+secondMaxIndex);
        }
        else System.out.println("No second largest element");
    }

}
