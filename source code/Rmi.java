/******
 Creates a remote interface that extends the Remote interface
 The subinterface of Remote determines which method of the remote object clients may call
 A remove object may have many public methods,
 But only those declared in a remote interface can be invoked remotely (By the client).
 Other public methods (Not declared in the remote interface) can only be invoked locally.

 This interface will not tell you how the calculation is implemented.
 */
import java.rmi.*;  //java.rmi.Remote;

public interface Rmi extends Remote{
    public double add(double x,double y)throws RemoteException;

    public String arg(String x) throws RemoteException;


}
