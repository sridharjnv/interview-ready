package DSA.HashMap;

import java.util.HashMap;

public class LongestSubArrWithGivenSum {
    public static void main(String[] args){
        int[] a = {8,3,1,5,-6,6,2,2};
        int sum = 4;
        System.out.println(longestSubArr(a,sum));
    }
    static int longestSubArr(int[] a, int sum){
        HashMap<Integer,Integer> map = new HashMap<>();
        int prefixSum=0,result=0;
        for(int i=0;i<a.length;i++){
            prefixSum+=a[i];
            if(prefixSum==sum) result=i+1;
            if(!map.containsKey(prefixSum)) map.put(prefixSum,i);
            if(map.containsKey(prefixSum-sum)) result=Math.max(result,i-map.get(prefixSum-sum));
        }
        return result;
    }
}
