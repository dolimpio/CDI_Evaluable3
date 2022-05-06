package rmiserver;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import rmiinterface.Wordle;


public class WordleServer implements Wordle{
    public static Queue palabrasUsadas = new LinkedList();
    public static void main(String[] args) throws RemoteException {
        WordleServer srv = new WordleServer();

        Remote stub =  UnicastRemoteObject.exportObject(srv, 0);//Esto es para varias interfaces tener cuidado


        try {
            Naming.bind("//localhost:1099/Wordle", stub);


            System.out.println("Server up and running...");


        } catch (MalformedURLException e1) {
            System.out.println("URL malformed!!!  "+e1);
            e1.printStackTrace();
        } catch (RemoteException e1) {
            System.out.println("Host not reachable // comunication failure!! "+e1);
            e1.printStackTrace();
        } catch (AlreadyBoundException e1) {
            System.out.println("Name already bound to another object!!!  "+e1);
            e1.printStackTrace();
        }
    }

        public String palabraDia() throws RemoteException{
            List<String> listaPalabras = leerDiccionario("dictionary.txt");
            Random rand = new Random();
            int tam = listaPalabras.size();
            int random = rand.nextInt(tam);
            String palabra = listaPalabras.get(random);
            while(palabrasUsadas.contains(palabra)){
                random = rand.nextInt(tam);
                palabra = listaPalabras.get(random);
            }
            introducirRepetida(palabra);
            return palabra.toUpperCase();


        }
        public List<String> leerDiccionario(String archivo) throws RemoteException{
           
            List<String> listaPalabras = new ArrayList<>();

            try {
                listaPalabras =
                Files.readAllLines(Paths.get(archivo), StandardCharsets.UTF_8);


            } catch (Exception e) {
                System.out.println("Error al leer el archivo!!!"+e);
            }
            return listaPalabras;
        }
        public void introducirRepetida(String palabra) throws RemoteException{
            if(palabrasUsadas.size()==5){
                palabrasUsadas.remove();
                palabrasUsadas.add(palabra);
            }else{
                palabrasUsadas.add(palabra);
            }
        }
        public float compro(float a, float b) throws RemoteException{
            return a*b;
        }

}
