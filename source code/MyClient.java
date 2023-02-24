// This is Client program that let user choose to be a sender or receiver
// sender - TCP connection with server
// receiver - joins multicast group and receive UDP packet from sender

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.rmi.*;
import java.text.DecimalFormat;

class MyClient{

    public static void main(String args[]) throws Exception {

        // init Utils
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Scanner sc = new Scanner(System.in);

        // init CaesarCipher class for en/decryption and Decimal Format for float val
        CaesarCipher cipher = new CaesarCipher();
        DecimalFormat df = new DecimalFormat("#.#####");
        

        // init variables for wlc page
        int wlc_opt = 0;
        boolean wlc_bool;


        // Welcome page, let user select 1.Sender or 2.Receiver
        // User input validation for Welcome selection
        do{
            Text.clearConsole();
            Text.getWelcomeMenu();
            String str = sc.nextLine();

            try{
                wlc_opt = Integer.parseInt(str);

                // Valid input - 0 or 1
                if(wlc_opt == 0 || wlc_opt ==1){
                    wlc_bool=true;
                    Text.clearConsole();
                }
                // Invalid input - Wrong int value
                else{
                    System.out.println("\nInvalid input entered, please try again.\n");
                    wlc_bool=false;
                    Text.retry();
                    Text.clearConsole();
                }
            }
            // Invalid input - String or char
            catch(NumberFormatException e){
                System.out.println("\nInvalid input entered, Please try again.\n");
                wlc_bool = false;
                Text.retry();
                Text.clearConsole();
            }
        }while(!wlc_bool);


        // Sender Interface, function page that lets user select func 1-7
        // User selects Sender (0) - TCP connection with Server
        if(wlc_opt==0) {

            // init components needed in TCP client
            // Specifying host_addr and port_num
            Socket clientTCPSock = null;
            int port = 3002;
            String host = "127.0.0.1";


            try {
                clientTCPSock = new Socket(host, port);
            }
            catch(Exception ex){
                System.out.println("Failed creating client TCP socket");
                ex.printStackTrace();
                System.exit(0);
            }


            DataInputStream din = new DataInputStream(clientTCPSock.getInputStream());
            DataOutputStream dout = new DataOutputStream(clientTCPSock.getOutputStream());


            // init variables for func option
            int func_opt = 0;
            boolean func_bool = true;


            // Loops until user wish to exit
            while(func_opt!=7){

                // User input validation for the function selection
                do{
                    Text.clearConsole();
                    Text.getFunctionMenu();
                    String str = sc.nextLine();

                    try{
                        func_opt = Integer.parseInt(str);

                        // Valid function selection input
                        if(func_opt>0 && func_opt<8){
                            func_bool=true;
                        }
                        // Invalid function selection input - int value other than 1-7
                        else{
                            System.out.println("\nInvalid input entered, please try again.\n");
                            func_bool=false;
                            Text.retry();
                            Text.clearConsole();
                        }
                    }
                    //Invalid input - String, char value
                    catch(NumberFormatException e){
                        System.out.println("\nInvalid input entered, Please try again.\n");
                        func_bool = false;
                        Text.retry();
                        Text.clearConsole();
                    }
                }while(!func_bool);


                // Sends the function selection to the server
                dout.writeUTF(cipher.encrypt(String.valueOf(func_opt)));
                dout.flush();


                // Performs corresponding function
                switch(func_opt){

                    //1. System time
                    case 1:
                        String time = cipher.decrypt(din.readUTF());
                        System.out.println("\nServer:\nThe current time is: " + time);

                        Text.pause();
                        break;

                    //2. Port number
                    case 2:
                        int port_num = cipher.decrypt(din.readInt());
                        System.out.println("\nServer:\nThe port number in used is: " + port_num);

                        Text.pause();
                        break;

                    //3. Reverse characters
                    case 3:
                        // User input message
                        System.out.print("\nClient:\nSend a message to the server: ");
                        String str = br.readLine();

                        // Sends the encrypted message to server
                        dout.writeUTF(cipher.encrypt(str));
                        dout.flush();

                        // Decrypts the reversed message from server
                        String reverse_char = cipher.decrypt(din.readUTF());
                        System.out.println("\nServer:\nThe Reversed character is: " + reverse_char);

                        Text.pause();
                        break;

                    //4. Send information
                    case 4:
                        System.out.print("\nSend a message to the multicast receiver: ");
                        String multicast_msg = br.readLine();
                        dout.writeUTF(cipher.encrypt(multicast_msg));
                        dout.flush();

                        String status = cipher.decrypt(din.readUTF());
                        System.out.println("\nServer: \nStatus - "+ status);

                        Text.pause();
                        break;

                    //Perform addition (RMI)
                    case 5:

                        String input;
                        float num1 = 0, num2= 0;
                        boolean isNum1 = true, isNum2 = true;

                        System.out.println("\nKey in two numbers to send to ther server:");

                        //Num1 user input validation
                        do{
                            System.out.print("First number: ");
                            input = sc.nextLine();

                            try{
                                num1 = Float.parseFloat(input);
                                isNum1=true;

                            }
                            catch(NumberFormatException e){
                                System.out.println("Invalid input entered, Please try again.\n");
                                isNum1 = false;
                            }
                        }while(!isNum1);

                        //Num2 user input validation
                        do{
                            System.out.print("Second number: ");
                            input = sc.nextLine();

                            try{
                                num2 = Float.parseFloat(input);
                                isNum2=true;

                            }
                            catch(NumberFormatException e){
                                System.out.println("Invalid input entered, Please try again.\n");
                                isNum2 = false;
                            }
                        }while(!isNum2);

                        //Perform RMI addition function
                        try{
                            Rmi stub=(Rmi)Naming.lookup("rmi://localhost:5000/sonoo");

                            System.out.println("\nRmi:\nAddition of the numbers: " + df.format(stub.add(num1,num2)));
                        }
                        catch(Exception e){
                            e.printStackTrace();
                        }

                        //Sends status to server
                        dout.writeInt(cipher.encrypt(1));

                        Text.pause();
                        break;

                    //6. Arrange the sequence (RMI)
                    case 6:
                        //Checks
                        boolean seq_check = true;
                        String seq;

                        do{
                            System.out.println("\nE.g. 987654321, the output will be arrange in 123456789\n");
                            System.out.print("\nPlease key in a sequence of numbers (0-9):");
                            seq = sc.nextLine();

                            if(seq.chars().allMatch(Character::isDigit)){
                                seq_check = true;
                            }
                            else{
                                System.out.println("\nInvalid input entered, Please Key in digits only.");
                                seq_check = false;
                            }
                        }while(!seq_check);

                        try{
                            Rmi stub=(Rmi)Naming.lookup("rmi://localhost:5000/sonoo");

                            System.out.println("\nRmi:\nArranged sequence of the numbers: " + stub.arg(seq));
                        }
                        catch(Exception e){
                            e.printStackTrace();
                        }

                        //Sends status to server
                        dout.writeInt(cipher.encrypt(1));

                        Text.pause();
                        break;

                    //7. Quit
                    case 7:
                        String bye_msg = cipher.decrypt(din.readUTF());
                        System.out.println("\nServer:\nGreeting goodbye: " + bye_msg);

                        break;
                }
            }
            din.close();
            dout.close();
            clientTCPSock.close();

            System.out.println("\n\nTCP Connection Closed....");
        }

        // User selects receiver - UDP multicast group
        if (wlc_opt==1) {

            try {

                // Initialize the components needed in a Multicast communication
                InetAddress group = InetAddress.getByName("225.0.0.1");
                MulticastSocket multicastSock = new MulticastSocket(3052);
                multicastSock.joinGroup(group);


                String str = "";
                int counter = 0;

                System.out.println("- Receiver Interface -");
                System.out.println("This program will exits if sender sends \"exit\"....");
                System.out.println("Getting input from sender....\n\n");
                

                    while (true) {
                        byte[] buffer = new byte[1024];
                        counter++;

                        // Create a UDP socket and receive incoming msg
                        DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
                        multicastSock.receive(reply);

                        // Gets msg from Server
                        byte[] data = reply.getData();
                        String val = new String(data, 0, reply.getLength());

                        // Decrypts the msg
                        String de = cipher.decrypt(val);


                        // Checks for user input, if input == exit, quit the Receiver Interface
                        if (de.equals("exit")){
                            System.out.println(counter +". Sender sent: " + de);
                            System.out.println("Exiting Receiver Interface...");
                            Text.pause();
                            break;
                        }
                        else {
                            System.out.println(counter +". Sender sent: " + de);
                        }

                    }

                multicastSock.close();
                System.out.println("\n\nMulticast Socket Closed....");

            } catch (Exception e) {
                System.out.println("Failed to create Multicast Socket...");
                e.printStackTrace();
                System.exit(0);
            }
        }
        br.close();
        sc.close();
    }
}

