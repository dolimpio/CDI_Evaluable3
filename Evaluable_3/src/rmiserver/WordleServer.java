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
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;

import rmiinterface.Wordle;

public class WordleServer implements Wordle {
    private static Queue usedWords = new LinkedList();
    private static Map<Integer, Integer> sessions = new HashMap<>();
    private static int tries = 0;
    private static List<String> wordList = readDictionary("diccionario.txt");

    public static void main(String[] args) throws RemoteException {
        
        WordleServer srv = new WordleServer();


        try {
            Wordle stub = (Wordle) UnicastRemoteObject.exportObject(srv, 0);

            Registry reg = LocateRegistry.getRegistry("localhost", 1099); //Si no anhadimos argumentos, hara exactamente lo mismo por defecto
            reg.bind("Wordle", stub);
            
            System.out.println("Server up and running...");

        } catch (RemoteException e1) {
            System.out.println("Host not reachable // comunication failure!!\n" + e1);
            e1.printStackTrace();
        } catch (AlreadyBoundException e1) {
            System.out.println("Name already bound to another object!!!\n" + e1);
            e1.printStackTrace();
        }
    }

    public String play(int id) throws RemoteException {
        
        int session = checkSession(id);
        if((session == -1)){
            // Error sesion no valida
        }
        int tries = sessions.get(session);
        return getWord();
        

    }

    private int checkSession(int id){
        int session = -1;
        if(id != 0 && sessions.containsKey(id)){
            sessions.put(id, 0);
            return sessions.get(id);
        }

        if(id == 0){
            Random random = new Random();
            return session = random.nextInt(1000);
        }

        return session; // En caso de que el id no estuviera en el mapa que hacemos??
    }

    public String getWord(){
        List<String> posibleWords = readDictionary("dictionary.txt");
        Random random = new Random();
        int size = posibleWords.size(); 
        int selection = random.nextInt(size);
        String word = posibleWords.get(selection);
        while (usedWords.contains(word)) {
            selection = random.nextInt(size);
            word = posibleWords.get(selection);
        }
        repeatedWord(word);
        return word.toUpperCase();
    }

    private void timer() throws InterruptedException{
        boolean timer = false;
        wait(5000);
        timer = true;

    }

    private void repeatedWord(String word){
        if(usedWords.size() == 5){
            usedWords.remove();
        }
        usedWords.add(word);
    }

    private static List<String> readDictionary(String archivo){

        List<String> wordList = new ArrayList<>();

        try {
            wordList = Files.readAllLines(Paths.get(archivo), StandardCharsets.UTF_8);

        } catch (Exception e) {
            System.out.println("Error al leer el archivo!!!" + e);
        }
        return wordList;
    }

    @Override
    public String checkWord(String[] word) throws RemoteException {
        String msg = null; 
        if(validateWord(word)){
            tries++;
            print(); // metodo por implementar que saque por pantalla las letras por colorines
        } else {
            msg = "La palabra introducida por pantalla no es valida";
        }

        return msg;
    }

    private boolean validateWord(String[] word){
        if(word.length != 5){ 
            System.out.println("La palabra debe tener 5 caracteres");
            return false;
        }
        if(!wordList.contains(word.toString())){
            System.out.println("La palabra no existe en el diccionario");
            return false;
        }
        return true;
    }

    private String print(){
        String print = null;
        return print; 
    }

}
