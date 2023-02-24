// This is Server program that perform corresponding function
/* Client will send the function selection value to this server program and
   then server will process the function...
*/

import java.net.*;
import java.io.*;
import java.rmi.*;
import java.rmi.registry.*;
import java.util.concurrent.TimeUnit;


class MyServer{

    public static void main(String args[])throws Exception{
    
        Text.clearConsole();
        System.out.println("- Welcome to the Server Interface -\n");


        // init  components needed in TCP server
        // ServerTCP socket
        ServerSocket serverTCPSock = null;
        Socket s = null;

        try{
            serverTCPSock =new ServerSocket(3002);
            System.out.println("Succesfully initializing server TCP socket...");
        }
        catch(Exception e){
            System.out.println("Failed initializing server TCP socket...");
            e.printStackTrace();
            System.exit(0);
        }

        System.out.println("Listening to client TCP socket...");

        try{
            s = serverTCPSock.accept();
            System.out.println("Accepted client TCP socket...");
        }
        catch(Exception e){
            System.out.println("Failed accepting client TCP socket...");
            e.printStackTrace();
            System.exit(0);
        }


        // init Utils
        DataInputStream din=new DataInputStream(s.getInputStream());
        DataOutputStream dout=new DataOutputStream(s.getOutputStream());

        // init the components needed in Multicast communication
        // Specifying the multicast_addr
        InetAddress group = InetAddress.getByName("225.0.0.1");
        MulticastSocket multicastSock = null;

        try{
            multicastSock = new MulticastSocket();
            System.out.println("Successfully created Multicast Socket...");
        }catch(Exception e){
            System.out.println("Failed to create Multicast Socket...");
            e.printStackTrace();
            System.exit(0);
        }


        // init Rmi registry
        try{
            //Constructs a new Rmi object
            Rmi stub =new RmiRemote();

            //Binds that object "stub" to the name of the URI rmi://localhost:5000/sonoo
            Naming.bind("rmi://localhost:5000/sonoo",stub);
            System.out.println("Successfully binding of RMI...\n\n");

        }catch(Exception e){
            System.out.println("Failed binding of RMI...\n\n");
            e.printStackTrace();
            System.exit(0);
        }

        // init cipher class for en/decryption and func option variables
        CaesarCipher cipher = new CaesarCipher();
        int counter =0;
        int func_opt = 0;

        TimeUnit.SECONDS.sleep(3);
        Text.clearConsole();
        System.out.println("- Server Interface -");
        System.out.println("\nThe process performed will be as shown as below:\n\n");

        // Loops until user wish to exit
        while (func_opt != 7) {

            //Obtain the function number from client
            String str = din.readUTF();
            func_opt = Integer.parseInt(cipher.decrypt(str));
            counter++;

            switch(func_opt){

                //1. System time
                case 1:

                    // Gets the latest time with Time class
                    Time time = new Time();
                    String cur_time =  time.getCurrentTime();

                    // Encrypts and Sends the time to client
                    dout.writeUTF(cipher.encrypt(cur_time));
                    System.out.println(counter +". Server func1: sent the current time " + cur_time + " to the client");
                    break;

                //2. Port number
                case 2:

                    // Gets the port number
                    int port = s.getLocalPort();

                    // Encrypts and Sends the port number to client
                    dout.writeInt(cipher.encrypt(port));
                    System.out.println(counter +". Server func2: sent the port number " + port + " to the client");
                    break;

                //Reverse characters
                case 3:

                    // Gets user input from client
                    String client_str = cipher.decrypt(din.readUTF());

                    // Pass the string into reverse()
                    ReverseString rev = new ReverseString();
                    String reversed_str = rev.reverse(client_str);

                    // Encrypts and Sends the reversed_str back to client
                    dout.writeUTF(cipher.encrypt(reversed_str));
                    System.out.println(counter +". Server func3: sent the reversed string " + reversed_str + " to the client");
                    break;

                //Send information
                case 4:

                    // Gets msg from sender
                    String msg = cipher.decrypt(din.readUTF());

                    // Encrypt the message before sending to multicast_group
                    String en = cipher.encrypt(msg);

                    // Use UDP packet to send the msg to multicast_group
                    DatagramPacket toReceiver = new DatagramPacket(en.getBytes(),en.length(), group,3052);
                    multicastSock.send(toReceiver);

                    System.out.println(counter +". Server func4: sent the msg " + msg + " to the receiver");
                    String status = "Successfully sent the message " + "\"" + msg + "\"" + " to the receiver";

                    // Encrypts and Sends the status back to client
                    dout.writeUTF(cipher.encrypt(status));
                    break;


                //Perform addition (RMI)
                case 5:

                    // Gets the status from client
                    int stat1 = cipher.decrypt(din.readInt());
                    if (stat1==1) {System.out.println(counter +". Server func5: Performed RMI addition function at the client");}
                    else{System.out.println("Server func5: Failed");}
                    break;

                //Arrange Sequence (RMI)
                case 6:

                    // Gets the status from client
                    int stat2 = cipher.decrypt(din.readInt());
                    if (stat2==1) {System.out.println(counter +". Server func6: Performed RMI arrange sequence function at the client");}
                    else{System.out.println("Server func6: Failed");}
                    break;

                //Quit
                case 7:

                    /// Goodbye Msg
                    String goodbye = "Goodbye, Have a Nice Day!";

                    // Sends the goodbye msg to the client
                    dout.writeUTF(cipher.encrypt(goodbye));

                    System.out.println(counter +". Server func7: Good Bye to Client, closes connection");
                    break;

            }

        }

        // Closes all the utils and exit the program
        dout.close();
        din.close();
        s.close();
        serverTCPSock.close();
        multicastSock.close();
        System.out.println("\n\nTCP Connection Closed");
        System.exit(0);
    }
}
