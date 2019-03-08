package compilation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

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
        determinisation.setAutomateInitial(automate);
        automate = determinisation.determinise();
        Moteur moteur = new Moteur(automate);
        moteur.lire(new BufferedReader(new InputStreamReader(System.in)));
        moteur.getDotFile();
    }
}
