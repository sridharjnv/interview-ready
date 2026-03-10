package DSA.Strings;

public class LeftMostRepeatingCharacter {
    public static void main(String[] args){
        String s = "geeksforgeeks";
        System.out.println(leftMostRepeating(s));
    }

    public static int leftMostRepeating(String s){
        boolean[] visited = new boolean[256];
        int result = -1;
        for(int i=s.length()-1;i>=0;i--){
            if(visited[s.charAt(i)]){
                result =i;
            }
            else visited[s.charAt(i)]=true;
        }
        return result;
    }
}
