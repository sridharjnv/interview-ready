package DSA.Strings;

public class ReverseWordsInString {
    public static void main(String[] args){
        String s = "welcome to gfg";
        char[] arr = s.toCharArray();
        reverseString(arr);
        System.out.println(new String(arr));
    }
    public static void reverseString(char[] arr){
        int start =0;
        for(int end =0;end<arr.length;end++){
            if(arr[end]==' '){
                reverseWords(arr, start, end-1);
                start = end+1;
            }
        }
        reverseWords(arr,start,arr.length-1);
        reverseWords(arr,0,arr.length-1);
    }

    public static void reverseWords(char[] arr, int low, int high){
        while(low<=high) {
            char temp = arr[low];
            arr[low] = arr[high];
            arr[high] = temp;
            low++;
            high--;
        }
    }
}
