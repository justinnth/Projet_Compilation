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
        deterministe = true;
        automate = new AEF();
        determinisation = new Determinisation();

        Scanner in = new Scanner(System.in);
        System.out.println("Saisir le fichier .descr pour construire un automate : ");
        String nomFichier = in.nextLine();

        Moteur moteur = new Moteur(automate, nomFichier);
        //automate = moteur.getAutomate();
        //determinisation.setAutomateInitial(automate);

        //automate = determinisation.determinise(); // Déterminisation de l'automate
        //moteur.setAutomate(automate);

        if(automate.getEtatsInitiaux().size() > 1)
            deterministe = false;

        if (deterministe)
            moteur.lire(new BufferedReader(new InputStreamReader(System.in)));
        else{
            System.out.println("L'automate n'est pas déterministe, le programme génère un nouveau fichier .descr deterministe");
            determinisation.getDescr(automate);
        }

        System.out.println("Le moteur génère le fichier dot et png de l'automate");
        moteur.getDotFile();
    }
}
