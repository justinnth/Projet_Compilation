package compilation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Représentation de l'automate
 */
public class AEF {
    private final String DOSSIER = "files/";
    private static char meta = '#';
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
        this.C = "sansNom";
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
        try {
            BufferedReader br = new BufferedReader(new FileReader(DOSSIER + f));
            String ligne;
            int numLigne = 0;

            while ((ligne = br.readLine()) != null && !ligne.equals("")) {
                System.out.println(ligne);
                ++numLigne;
                switch (ligne.charAt(0)) {
                    case 'C':
                        this.C = ligne.substring(3, ligne.length() - 1).trim();
                        break;
                    case 'M':
                        char meta = ligne.charAt(3);
                        break;
                    case 'V':
                        this.V = ligne.substring(3, ligne.length() - 2);
                        break;
                    case 'O':
                        this.O = ligne.substring(3, ligne.length() - 2);
                        break;
                    case 'E':
                        this.E = Integer.parseInt(ligne.substring(2));
                        for (int i = 1; i < this.E; ++i) {
                            this.lesEtats.add(new Etat(Integer.toString(i)));
                        }
                        break;
                    case 'I':
                        this.lesEtats.get(0).setEstInitial(false);
                        this.I.clear();
                        for (String nomEtat : ligne.substring(2).split(" ")) {
                            Etat etat = this.lesEtats.get(Integer.parseInt(nomEtat));
                            etat.setEstInitial(true);
                            this.I.add(etat);
                        }
                        break;
                    case 'F':
                        for (String nomEtat : ligne.substring(2).split(" ")) {
                            Etat etat = this.lesEtats.get(Integer.parseInt(nomEtat));
                            etat.setEstAcceptant(true);
                            this.F.add(etat);
                        }
                        break;
                    case 'T':
                        String[] ligneSplit = ligne.substring(2).split(" ");

                        Etat source, fin;
                        char entree, sortie;

                        source = this.lesEtats.get(Integer.parseInt(ligneSplit[0]));
                        fin = this.lesEtats.get(Integer.parseInt(ligneSplit[2]));

                        entree = ligneSplit[1].charAt(1);
                        sortie = AEF.meta;

                        if (ligneSplit.length > 3) {
                            if (sortie != ligneSplit[3].charAt(1))
                                sortie = ligneSplit[3].charAt(1);
                        }
                        source.addTransition(entree, fin, sortie);
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println("Erreur lors de l'ouverture du fichier : " + e);
        }
    }
}
