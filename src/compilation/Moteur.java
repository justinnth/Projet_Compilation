package compilation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

/**
 * Représentation de l'automate
 */
public class Moteur {
    /**
     * Dossier dans lequel se trouve les fichiers .descr
     */
    private final String DOSSIER = "files/";


    private String commentaire;
    private Set<Character> vocabulaireEntree, vocabulaireSortie;
    private int nbEtats;

    private ArrayList<Etat> lesEtats;
    private ArrayList<Etat> etatsInitiaux;
    private ArrayList<Etat> etatsAcceptants;

    /**
     * Constructeur par défaut
     */
    public Moteur() {
        this.lesEtats = new ArrayList<>();
        this.lesEtats.add(new Etat("0", true, false)); // Ajout d'un état nommé 0, initial

        this.commentaire = "sansNom";
        this.vocabulaireEntree = new TreeSet<>();
        this.vocabulaireSortie = new TreeSet<>();
        this.nbEtats = this.lesEtats.size();

        this.etatsInitiaux = new ArrayList<>();
        this.etatsInitiaux.add(this.lesEtats.get(0));

        this.etatsAcceptants = new ArrayList<>();
    }

    /**
     * Initialisation d'un automate à partir d'un fichier .descr
     *
     * @param file nom du fichier
     * @throws IOException
     */
    public Moteur(String file) throws IOException {
        this();

        if (!file.endsWith(".descr"))
            throw new IllegalArgumentException("Le format de fichier attendu est .descr");

        lireFichierDescr(file);
    }

    /**
     * Lecture d'un fichier .descr
     *
     * @param f fichier .descr
     * @throws IOException Exception pour l'ouvertur du fichier
     */
    private void lireFichierDescr(String f) throws IOException {
        try {
            BufferedReader br = new BufferedReader(new FileReader(DOSSIER + f));
            String ligne;
            int numLigne = 0;

            System.out.println("Automate");
            System.out.println("===========");
            while ((ligne = br.readLine()) != null && !ligne.equals("")) {
                System.out.println(ligne);
                ++numLigne;
                switch (ligne.charAt(0)) {
                    case 'C':
                        this.commentaire = ligne.substring(3, ligne.length() - 1).trim();
                        break;
                    case 'M':
                        char meta = ligne.charAt(3);
                        if (!Transition.meta.equals(meta))
                            Transition.meta = meta;
                        break;
                    case 'V':
                        String l = ligne.substring(3, ligne.length() - 1);
                        for (int i = 0; i < l.length(); i++)
                            this.vocabulaireEntree.add(l.charAt(i));
                        break;
                    case 'O':
                        l = ligne.substring(3, ligne.length() - 1);
                        for (int i = 0; i < l.length(); i++)
                            this.vocabulaireSortie.add(l.charAt(i));
                        break;
                    case 'E':
                        this.nbEtats = Integer.parseInt(ligne.substring(2));
                        for (int i = 1; i < this.nbEtats; ++i)
                            this.lesEtats.add(new Etat(Integer.toString(i)));
                        break;
                    case 'I':
                        this.lesEtats.get(0).setEstInitial(false);
                        this.etatsInitiaux.clear();
                        for (String nomEtat : ligne.substring(2).split(" ")) {
                            Etat etat = this.lesEtats.get(Integer.parseInt(nomEtat));
                            etat.setEstInitial(true);
                            this.etatsInitiaux.add(etat);
                        }
                        break;
                    case 'F':
                        for (String nomEtat : ligne.substring(2).split(" ")) {
                            Etat etat = this.lesEtats.get(Integer.parseInt(nomEtat));
                            etat.setEstAcceptant(true);
                            this.etatsAcceptants.add(etat);
                        }
                        break;
                    case 'T':
                        String[] ligneSplit = ligne.substring(2).split(" ");

                        Etat source, fin;
                        char entree, sortie;

                        source = this.lesEtats.get(Integer.parseInt(ligneSplit[0]));
                        fin = this.lesEtats.get(Integer.parseInt(ligneSplit[2]));

                        entree = ligneSplit[1].charAt(1);
                        sortie = Transition.meta;

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

    /**
     * Méthode pour la lecture d'un mot par l'automate
     *
     * @param reader Input de l'utilisateur
     * @throws IOException
     */
    public void lire(BufferedReader reader) throws IOException {
        int numLigne = 0;
        ArrayList<String> motsLus = new ArrayList<>();

        System.out.println("Automate : \"" + this.commentaire + "\"\nVocabulaire accepté : " + this.vocabulaireEntree +
                "\nVeuillez saisir les phrases à lire : ");

        String mot;

        while (!((mot = reader.readLine()).equals("###"))) {
            ++numLigne;
            motsLus.add(mot);
        }

        System.out.println("========================");
        System.out.println("Traitement :");

        for (String m : motsLus) {
            ArrayList<String> motTraite = this.traiterMot(m);

            if (motTraite.isEmpty())
                System.out.println("Aucuns mots");
            else {
                for (String affichage : motTraite) {
                    System.out.println(affichage);
                }
            }
        }
        System.out.println("Fin de traitement");
        System.out.println("========================");
    }

    /**
     * Traitement des mots entrés par l'utilisateur
     *
     * @param mot Mot à traiter
     * @return Liste des sorties fournies par l'automate
     */
    public ArrayList<String> traiterMot(String mot) {
        ArrayList<String> motsTraites = new ArrayList<>();
        String affichage = "";
        String sortieS = "";
        Etat etatCourant = new Etat(this.etatsInitiaux.get(0));

        for (int i = 0; i < mot.length(); ++i) {
            char c = mot.charAt(i);
            affichage += "État courant : " + etatCourant.getNom() + ", Entrée : " + c;
            if (etatCourant.getTransitions().containsKey(c)) {
                Transition t = new Transition();
                for (Transition t2 : etatCourant.getTransitions().get(c)) {
                    t = t2;
                }

                etatCourant = t.getEtatSortie();
                char sortie = t.getSortie();

                if (sortie != Transition.meta) {
                    affichage += ", Sortie : " + sortie;
                    sortieS += sortie;
                }

                affichage += " Transition trouvée vers l'état : " + t.getEtatSortie().getNom() + '\n';
            } else {
                affichage += "Aucune transition trouvée\n";
                break;
            }
        }
        affichage += "État courant : " + etatCourant.getNom() + " Fin de chaine";
        if (etatCourant.isEstAcceptant())
            affichage += "\nÉtat acceptant";
        else
            affichage += "\nÉtat non acceptant";

        affichage += "\nLa sortie de cette phrase est : " + sortieS;
        affichage += "\n-- Fin de phrase --\n";
        motsTraites.add(affichage);
        return motsTraites;
    }
}
