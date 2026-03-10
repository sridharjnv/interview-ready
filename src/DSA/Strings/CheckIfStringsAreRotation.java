package DSA.Strings;

public class CheckIfStringsAreRotation {
    public static void main(String[] args){
        String s1 = "ABCD";
        String s2 = "CDAB";
        System.out.println(areRotations(s1,s2));
    }
    public static boolean areRotations(String s1, String s2){
        if(s1.length() != s2.length()) return false;

        String temp = s1+s1;
        return temp.contains(s2);
    }
}
