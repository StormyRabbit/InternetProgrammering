import java.util.*;
import java.rmi.*;

public interface __RemoteServer extends Remote {
    public void addBall() throws RemoteException;
    public void pauseBalls() throws RemoteException;
    public Vector getBalls() throws RemoteException;
}