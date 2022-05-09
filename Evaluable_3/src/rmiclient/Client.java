package rmiclient;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import rmiinterface.Wordle;


public class Client {
    public static void main(String[] args) {
        
        String resultado;

        System.out.println("Bienvenido al Wordle!! Conseguiras adivinar la palabra??");

        try {
                int id= 0;
                Wordle stub =  (Wordle) Naming.lookup("//localhost:1099/Wordle");
                resultado = stub.play(id);
                System.out.println("La palabra correcta es: " + resultado);
                System.out.println("_____________________");
                System.out.println("| " + " |" + "| " + " |" + "| " + " |");

                String msg = resultado;

                System.out.println(msg);
            
        } catch (RemoteException e) {
            //Muy importante detallar nuestros errores para saber de donde vienen nuestros errores
            System.out.println("Host not reachable // comunication failure!! "+e);
        }catch(NotBoundException e){
            System.out.println("Name not bound to any object!! "+e);
        }catch (MalformedURLException e) {
            System.out.println("URL malformed!!!  "+e);
            e.printStackTrace();
        }

        

    }
}
