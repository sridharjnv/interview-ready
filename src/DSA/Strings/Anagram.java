package DSA.Strings;

public class Anagram {
    public static void main(String[] args){
        String s1 = "abcd";
        String s2 = "cdba";
        System.out.println(isAnagram(s1,s2));
    }

    public static boolean isAnagram(String s1, String s2) {

        int[] freq = new int[26];

        if (s1.length() != s2.length()) return false;

        for(char c: s1.toCharArray()){
            freq[c -'a']++;
        }

        for(char c: s2.toCharArray()){
            freq[c -'a']--;
            if(freq[c - 'a'] < 0) return false;
        }
        return true;
    }
}
