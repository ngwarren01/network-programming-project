// This is ReverseString class that declare the reverse() function
// reverse() will reverse the order of a given String

public class ReverseString {

    public String reverse(String s){
        char[] letters = new char[s.length()];

        int index=0;
        for( int i = s.length()-1; i>=0; i--){
            letters[index]=s.charAt(i);
            index++;
        }

        String reverse="";
        for( int i =0; i < s.length();i++){
            reverse= reverse+ letters[i];
        }

        return reverse;
    }

}
