package rmiinterface;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Wordle extends Remote{
    
    public String play(int id) throws RemoteException;
    public String checkWord(String[] word) throws RemoteException;
    public String getWord();

}