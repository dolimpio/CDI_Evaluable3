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
        char tablero_juego[][] = new char[5][5];

        System.out.println("Bienvenido al Wordle!! Conseguiras adivinar la palabra??");

        try {
                int id= 0;
                Wordle stub =  (Wordle) Naming.lookup("//localhost:1099/Wordle");
                resultado = stub.play(id);

                System.out.println("La palabra correcta es: " + resultado);

        //Inicializamos el tablero de juego (la matriz) vacio (con "espacios")
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                tablero_juego[i][j] = ' ';
            }
        }

        System.out.format("+-----+-----+-----+-----+-----+%n");
        System.out.format("|  " + tablero_juego[0][0] + "  |  " + tablero_juego[0][1] + "  |  "  + tablero_juego[0][2] +   "  |  "  + tablero_juego[0][3] +  "  |  "  + tablero_juego[0][4] +  "  |%n");
        System.out.format("+-----+-----+-----+-----+-----+%n");
        System.out.format("|  " + tablero_juego[1][0] + "  |  " + tablero_juego[1][1] + "  |  "  + tablero_juego[1][2] +   "  |  "  + tablero_juego[1][3] +  "  |  "  + tablero_juego[1][4] +  "  |%n");
        System.out.format("+-----+-----+-----+-----+-----+%n");
        System.out.format("|  " + tablero_juego[2][0] + "  |  " + tablero_juego[2][1] + "  |  "  + tablero_juego[2][2] +   "  |  "  + tablero_juego[2][3] +  "  |  "  + tablero_juego[2][4] +  "  |%n");
        System.out.format("+-----+-----+-----+-----+-----+%n");
        System.out.format("|  " + tablero_juego[3][0] + "  |  " + tablero_juego[3][1] + "  |  "  + tablero_juego[3][2] +   "  |  "  + tablero_juego[3][3] +  "  |  "  + tablero_juego[3][4] +  "  |%n");
        System.out.format("+-----+-----+-----+-----+-----+%n");
        System.out.format("|  " + tablero_juego[4][0] + "  |  " + tablero_juego[4][1] + "  |  "  + tablero_juego[4][2] +   "  |  "  + tablero_juego[4][3] +  "  |  "  + tablero_juego[4][4] +  "  |%n");
        System.out.format("+-----+-----+-----+-----+-----+%n");

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
