// This is CaesarCipher class that declare the en/decrypt function
// encrypt() function will encrypt given String/int type value with shift key=3
// decrypt() function will decrypt given String/int type value with shift key=3

public class CaesarCipher {

    // Encrypts text using a key(shift=3)
    public String encrypt(String text)
    {
        int key = 3;
        char[] chars = text.toCharArray(); //pass text into chars array

        //Shifting of the value in the array with key=3
        for( int i =0; i < chars.length; i++){
            char c = chars[i];
            c += key; //Encryption, increments offset of the array with key=3
            chars[i] = c;
        }

        return String.valueOf(chars);
    }

    public int encrypt(int integer)
    {

        String text = String.valueOf(integer);

        int key = 3;
        char[] chars = text.toCharArray(); //pass text into chars array

        //Shifting of the value in the array with key=3
        for( int i =0; i < chars.length; i++){
            char c = chars[i];
            c += key; //Encryption, increments offset of the array with key=3
            chars[i] = c;
        }

        return Integer.valueOf(String.valueOf(chars));
    }


    public String decrypt(String text)
    {
        int key = 3;
        char[] chars = text.toCharArray(); //pass text into chars array

        //Shifting of the value in the array with key=3
        for( int i =0; i < chars.length; i++){
            char c = chars[i];
            c -= key; //Decryption, decrements offset of the array with key=3
            chars[i] = c;
        }

        return String.valueOf(chars);
    }


    public int decrypt(int integer)
    {

        String text = String.valueOf(integer);

        int key = 3;
        char[] chars = text.toCharArray(); //pass text into chars array

        //Shifting of the value in the array with key=3
        for( int i =0; i < chars.length; i++){
            char c = chars[i];
            c -= key; //Decryption, increments offset of the array with key=3
            chars[i] = c;
        }

        return Integer.valueOf(String.valueOf(chars));
    }

}