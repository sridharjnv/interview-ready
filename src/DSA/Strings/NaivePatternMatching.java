package DSA.Strings;

public class NaivePatternMatching {
    public static void main(String[] args){
        String txt = "abcabcd";
        String pat = "abcd";
        patternMatching(txt,pat);
    }
    static void patternMatching(String txt, String pat){
        int n = txt.length();
        int m = pat.length();
        for(int i=0;i<=n-m;i++){
            int j;
            for(j=0;j<m;j++){
                if(txt.charAt(i+j) != pat.charAt(j)){
                    break;
                }
            }
            if(j==m){
                System.out.println(i);
            }
        }
    }
}
