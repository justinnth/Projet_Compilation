package compilation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static boolean deterministe;
    private static AEF automate;
    private static Determinisation determinisation;

    /**
     * Méthode main à exécuter
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        System.out.println("Saisir le fichier .descr pour construire un automate : ");
        String nomFichier = in.nextLine();

        deterministe = true;
        automate = new AEF();
        //determinisation = new Determinisation();
        //determinisation.setAutomateInitial(automate);
        //automate = determinisation.determinise();

        Moteur moteur = new Moteur(automate, nomFichier);
        moteur.lire(new BufferedReader(new InputStreamReader(System.in)));
        moteur.getDotFile();
    }
}
