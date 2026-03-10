package DSA.Strings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnagramSearch {
    public static void main(String[] args){
        String txt = "cbaebabacd";
        String pat = "abc";
        System.out.println(findAnagrams(txt,pat));
    }
    public static List<Integer> findAnagrams(String txt, String pat){
        List<Integer> result = new ArrayList<>();
        int m = pat.length();
        int n = txt.length();

        int[] patFreq = new int[26];
        int[] txtFreq = new int[26];

        for(int i =0;i<m;i++){
            patFreq[pat.charAt(i) - 'a']++;
            txtFreq[txt.charAt(i) - 'a']++;
        }

        for(int i =m;i<n;i++){
            if(Arrays.equals(patFreq,txtFreq)){
                result.add((i-m));
            }
            txtFreq[txt.charAt(i) - 'a']++;
            txtFreq[txt.charAt(i-m) - 'a']--;
        }
        if(Arrays.equals(patFreq,txtFreq)){
            result.add(n-m);
        }
        return result;
    }
}
