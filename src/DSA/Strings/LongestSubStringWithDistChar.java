package DSA.Strings;

import java.util.Arrays;

public class LongestSubStringWithDistChar {
    public static void main(String[] args){
        String s = "abcadbd";
        System.out.println(longDist(s));
    }
    public static int longDist(String s){
        int n = s.length();
        int res =0;
        int[] prev = new int[256];
        Arrays.fill(prev,-1);
        int i=0;
        for(int j =0;j<n;j++){
            i = Math.max(i,prev[s.charAt(j)]+1);
            int maxEnd = j-i+1;
            res = Math.max(res,maxEnd);
            prev[s.charAt(j)]=j;
        }
        return res;
    }
}
