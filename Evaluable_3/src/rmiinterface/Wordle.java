package rmiinterface;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Wordle extends Remote{
    
    public String palabraDia() throws RemoteException;

}