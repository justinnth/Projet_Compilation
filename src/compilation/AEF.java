package compilation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Représentation de l'automate
 */
public class AEF {
    private final String DOSSIER = "files/";
    private String C, V, O;
    private int E;

    private ArrayList<Etat> lesEtats;
    private ArrayList<Etat> I;
    private ArrayList<Etat> F;

    /**
     * Constructeur par défaut
     */
    public AEF() {
        this.lesEtats = new ArrayList<>();
        this.lesEtats.add(new Etat("0", true, false));
        this.C = "";
        this.V = "";
        this.O = "";
        this.E = this.lesEtats.size();
        this.I = new ArrayList<>();
        this.I.add(this.lesEtats.get(0));

        this.F = new ArrayList<>();
    }

    /**
     * Initialisation d'un automate à partir d'un fichier .descr
     * @param file nom du fichier
     * @throws IOException
     */
    public AEF(String file) throws IOException {
        this();

        if(!file.endsWith(".descr"))
            throw new IllegalArgumentException("Le format de fichier attendu est .descr");

        lireFichierDescr(file);
    }

    /**
     * Lecture d'un fichier .descr
     * @param f fichier .descr
     * @throws IOException
     */
    private void lireFichierDescr(String f) throws IOException {
        try{
            BufferedReader br = new BufferedReader(new FileReader(f));
            String ligne;
            int numLigne;

            while ((ligne = br.readLine()) != null && !ligne.equals("")){
                System.out.println(ligne);
            }
        } catch (IOException e){
            System.out.println("Erreur lors de l'ouverture du fichier : " + e);
        }
    }
}
