/******
 This program is to define a class that implements this remote interface.
 UnicastRemoteObject provides a number of methods that make remote method invocation work.
 */
import java.rmi.*;
import java.rmi.server.*;
import java.util.Arrays;

public class RmiRemote extends UnicastRemoteObject implements Rmi {
    RmiRemote()throws RemoteException{
        super();  // Exports the objects
    }
    public double add(double x,double y){return x+y;}


    public String arg(String s) {

        int[] numbers = new int[s.length()];

        for(int i =0; i<numbers.length; i++){
            numbers[i] = Integer.parseInt(String.valueOf(s.charAt(i)));
        }

        int temp;
        for(int i =0; i<numbers.length; i++){
            for(int j=i; j<s.length();j++){
                if(numbers[i]>numbers[j]){
                    temp = numbers[i];
                    numbers[i] = numbers[j];
                    numbers[j] = temp;
                }
            }
        }

        return Arrays.toString(numbers).replaceAll("\\[|\\]|,|\\s", "");
    }

}
