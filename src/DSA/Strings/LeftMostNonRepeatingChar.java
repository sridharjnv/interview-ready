package DSA.Strings;

public class LeftMostNonRepeatingChar {
    public static void main(String[] args){
        String s = "abbcda";
        System.out.println(leftMostNonRepeatingChar(s));
    }
    public static int leftMostNonRepeatingChar(String s){
        int[] freq = new int[256];
        for(int i=0;i<s.length();i++){
            freq[s.charAt(i)]++;
        }
        for(int i=0;i<s.length();i++) {
            if ((freq[s.charAt(i)]) == 1) return i;
        }
        return -1;
    }
}
