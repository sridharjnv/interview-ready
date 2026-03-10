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

        for(int i=0;i<s1.length();i++){
            freq[s1.charAt(i) - 'a']++;
            freq[s2.charAt(i) - 'a']--;
        }

        for(int count: freq){
            if(count != 0){
                return false;
            }
        }
        return true;
    }
}
