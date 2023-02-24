// This java file declare the Menu function and clearConsole function
/* Besides, it also declares retry and pause function that require user to
   press enter to continue  */

public class Text {

    public static void getWelcomeMenu() {
       
        System.out.println("- Welcome to the Client Interface -");
        
        System.out.println("\nPlease select a role");
        System.out.println("0. Sender");
        System.out.println("1. Receiver");
        System.out.print("Please select an option (0,1): ");
    }


    public static void getFunctionMenu(){
        System.out.println("- Sender Interface -");
        System.out.println("\nPlease select one function to be performed:");
        System.out.println("1. Display the system time");
        System.out.println("2. Display the port number");
        System.out.println("3. Reverse characters");
        System.out.println("4. Send Information through multicast addr");
        System.out.println("5. Perform addition on 2 numbers (RMI)");
        System.out.println("6. Perform arrangement of number sequence (RMI)");
        System.out.println("7. Quit");
        System.out.print("Please key in an option (1-7): ");
    }

    public final static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public final static void retry(){
        System.out.println("\nPress ENTER to try again...");
        try{System.in.read();}
        catch(Exception ex){}
    }

    public final static void pause(){
        System.out.println("\nPress ENTER to continue...");
        try{System.in.read();}
        catch(Exception ex){}
    }





}
