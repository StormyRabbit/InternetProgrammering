import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.server.RemoteServer;

public class RmiClient extends Thread {
    private String host = "atlas.dsv.su.se";
    private String url = "rmi://" + host + "/";
    private RemoteServer remoteServer;

    public RmiClient() throws MalformedURLException, RemoteException, NotBoundException {
        remoteServer = (RemoteServer)Naming.lookup(url + "server");


    }
    @Override
    public void run() {

    }
}
