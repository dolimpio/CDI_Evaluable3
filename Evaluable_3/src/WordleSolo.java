import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

public class WordleSolo {
    public static final String TEXT_GREEN = "\u001B[32m";
    public static final String TEXT_WHITE = "\u001B[37m";
    public static final String TEXT_YELLOW = "\u001B[33m";

    private static List<String> palabrasIntroducidas = new ArrayList<>(); // HAY QUE RELLENAR ESTE ARRAY EN BLANCO
    private static Queue palabrasUsadas = new LinkedList();
    private static List<String> listaPalabras = leerDiccionario("dictionary.txt");
    private static int intentos = 0;

    static int tries = 0;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int opcion;

        // Rellenamos las palabras con palabras vacias para dibujar el tablero en blanco
        vaciarTablero();

        do {
            System.out.println("Bienvenido a Wordle! Que quieres hacer? [1]Jugar - [2]Leer Intrucciones - [3]Salir");
            opcion = Integer.parseInt(scanner.nextLine());
            if (opcion == 1) {
                play();
            } else if (opcion == 2) {
                instrucciones();
            }
            vaciarTablero();

        } while ((opcion == 1 || opcion == 2) && opcion != 3);
    }

    private static void vaciarTablero() {
        for (int i = 0; i < 5; i++) {
            palabrasIntroducidas.add("     ");
        }
    }

    private static void play() {
        Scanner scanner = new Scanner(System.in);
        String solucion = WordleSolo.getWord();
        String palabra;
        boolean palabraCorrecta;
        boolean palabraValida;
        List<String> feedback;

        do {

            // Mientras la palabra no sea valida, el juego no continuara
            do {
                System.out.println("La palabra correcta es: " + solucion);

                // El usuario introduce una palabra por consola
                palabra = leerPalabra(scanner);

                // Es una palabra valida?
                palabraValida = validarPalabra(palabra);

            } while (!palabraValida);

            palabra = palabra.toUpperCase();
            // Si la palabra es valida, la pintamos con el feedback
            feedback = proporcinarFeedback(palabra, solucion);
            introducirIntento(palabra);
            dibujarTablero(palabra, feedback);// hay que implementar el pintar con feedback

            palabraCorrecta = esSolucion(palabra, solucion);

        } while (intentos < 0 && !palabraCorrecta);

    }

    public static void introducirIntento(String palabra) {
        if (intentos < 5) {
            palabrasIntroducidas.add(intentos, palabra);
            intentos++;
        }
    }

    private static void instrucciones() {

    }

    // Verificamos si la palabra introducida es la solucion
    private static boolean esSolucion(String intento, String solucion) {
        return intento.equals(solucion);
    }

    // Comprobamos los caracteres de la palabra introducida
    private static List<String> proporcinarFeedback(String intento, String solucion) {
        List<String> feedback = new ArrayList<>();

        // Rellenamos el feedback de espacios vacios para poder usar mas tarde el
        // add(index,)
        for (int i = 0; i < 5; i++) {
            feedback.add(" ");
        }
        for (int caracter = 0; caracter < 5; caracter++) {
            if (intento.charAt(caracter) == solucion.charAt(caracter)) { // Esta en la posicion correcta
                feedback.add(caracter, "bien");
            } else if (solucion.indexOf(intento.charAt(caracter)) != -1) { // El caracter esta contenido en la solucion
                feedback.add(caracter, "esta");
            } else { // El caracter no se encuentra en la solucion
                feedback.add(caracter, "noesta");
            }
        }
        return feedback;
    }

    private static String leerPalabra(Scanner scanner) {
        System.out.println("Introduce una palabra: ");
        return scanner.nextLine();
    }

    private static boolean validarPalabra(String word) {
        if (palabrasIntroducidas.contains(word)) {
            System.out.println("Ya has utilizado esa palabra!\n");
            return false;
        } else if (word.length() < 5) {
            System.out.println("La palabra es demasiado pequenha... Introduce otra!!\n");
            return false;
        } else if (word.length() > 5) {
            System.out.println("La palabra es demasiado GRANDE... Introduce otra!!\n");
            return false;
        } else if (!listaPalabras.contains(word)) {
            System.out.println("La palabra no existe en el diccionario! :(\n");
            System.out.println("Prueba con otra!!\n");
            return false;
        }
        return true;
    }

    private static void dibujarTablero(String palabra, List<String> feedback) {// BORRAR CADA VEZ QUE SE JUEGA UNA NUEVA PARTIDA
                                                                            // SI ganas, la palabra se queda ahi

        System.out.format("+-----+-----+-----+-----+-----+%n");
        System.out.format("|  " + palabrasIntroducidas.get(0).charAt(0) + "  |  "
                + palabrasIntroducidas.get(0).charAt(1) + "  |  "
                + palabrasIntroducidas.get(0).charAt(2) + "  |  " 
                + palabrasIntroducidas.get(0).charAt(3) + "  |  "
                + palabrasIntroducidas.get(0).charAt(4) + "  |%n");

        System.out.format("+-----+-----+-----+-----+-----+%n");

        System.out.format("|  " + palabrasIntroducidas.get(1).charAt(0) + "  |  "
                + palabrasIntroducidas.get(1).charAt(1) + "  |  "
                + palabrasIntroducidas.get(1).charAt(2) + "  |  " 
                + palabrasIntroducidas.get(1).charAt(3) + "  |  "
                + palabrasIntroducidas.get(1).charAt(4) + "  |%n");

        System.out.format("+-----+-----+-----+-----+-----+%n");

        System.out.format("|  " + palabrasIntroducidas.get(2).charAt(0) + "  |  "
                + palabrasIntroducidas.get(2).charAt(1) + "  |  "
                + palabrasIntroducidas.get(2).charAt(2) + "  |  " 
                + palabrasIntroducidas.get(2).charAt(3) + "  |  "
                + palabrasIntroducidas.get(2).charAt(4) + "  |%n");

        System.out.format("+-----+-----+-----+-----+-----+%n");

        System.out.format("|  " + palabrasIntroducidas.get(3).charAt(0) + "  |  "
                + palabrasIntroducidas.get(3).charAt(1) + "  |  "
                + palabrasIntroducidas.get(3).charAt(2) + "  |  " 
                + palabrasIntroducidas.get(3).charAt(3) + "  |  "
                + palabrasIntroducidas.get(3).charAt(4) + "  |%n");

        System.out.format("+-----+-----+-----+-----+-----+%n");

        System.out.format("|  " + palabrasIntroducidas.get(4).charAt(0) + "  |  "
                + palabrasIntroducidas.get(4).charAt(1) + "  |  "
                + palabrasIntroducidas.get(4).charAt(2) + "  |  " 
                + palabrasIntroducidas.get(4).charAt(3) + "  |  "
                + palabrasIntroducidas.get(4).charAt(4) + "  |%n");
        System.out.format("+-----+-----+-----+-----+-----+%n\n\n");

    }

    public static String getWord() {
        List<String> posibleWords = WordleSolo.leerDiccionario("dictionary.txt");
        Random random = new Random();
        int size = posibleWords.size();
        int selection = random.nextInt(size);
        String word = posibleWords.get(selection);
        while (palabrasUsadas.contains(word)) {
            selection = random.nextInt(size);
            word = posibleWords.get(selection);
        }
        palabrasUsadas.add(word);
        return word.toUpperCase();
    }

    private void timer() throws InterruptedException {
        boolean timer = false;
        wait(5000);
        timer = true;

    }

    private static void palabrasRepetidas(String word) {
        if (palabrasUsadas.size() == 5) {
            palabrasUsadas.remove();
        }
        palabrasUsadas.add(word);
    }

    public static List<String> leerDiccionario(String archivo) {

        List<String> listaPalabras = new ArrayList<>();

        try {
            File myObj = new File(archivo);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
              String data = myReader.nextLine();
              listaPalabras.add(data);
            }
            myReader.close();
          } catch (FileNotFoundException e) {
            System.out.println("No se encontro el archivo!");
            e.printStackTrace();
          }

        return listaPalabras;
    }
}
