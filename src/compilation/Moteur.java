package compilation;

import java.io.*;
import java.util.ArrayList;

/**
 * Représentation de l'automate
 */
public class Moteur {
    public static final String DOSSIER = "files/", DESCR = "descr/", DOT = "dot/", PNG = "png/";
    private AEF automate;

    /**
     * Constructeur par défaut
     */
    public Moteur() {
        this.automate = new AEF();
    }

    public Moteur(AEF automate, String nom) throws IOException {
        this();
        this.automate = automate;
        this.lireFichierDescr(nom);
    }

    /**
     * Lecture d'un fichier .descr
     *
     * @param f fichier .descr
     * @throws IOException Exception pour l'ouvertur du fichier
     */
    private void lireFichierDescr(String f) throws IOException {
        boolean deterministe = false;
        try {
            BufferedReader br = new BufferedReader(new FileReader(DOSSIER + DESCR + f));
            String ligne;
            int numLigne = 0;

            System.out.println("Automate");
            System.out.println("======================");
            while ((ligne = br.readLine()) != null && !ligne.equals("")) {
                System.out.println(ligne);
                ++numLigne;
                switch (ligne.charAt(0)) {
                    case 'C':
                        this.automate.setCommentaire(ligne.substring(3, ligne.length() - 1).trim());
                        break;
                    case 'M':
                        char meta = ligne.charAt(3);
                        if (!AEF.getMeta().equals(meta))
                            AEF.setMeta(meta);
                        break;
                    case 'V':
                        ArrayList<Character> v = new ArrayList<>();
                        String l = ligne.substring(3, ligne.length() - 1);
                        for (int i = 0; i < l.length(); i++)
                            v.add(l.charAt(i));
                        this.automate.setVocabulaireEntree(v);
                        break;
                    case 'O':
                        ArrayList<Character> o = new ArrayList<>();
                        l = ligne.substring(3, ligne.length() - 1);
                        for (int i = 0; i < l.length(); i++)
                            o.add(l.charAt(i));
                        this.automate.setVocabulaireSortie(o);
                        break;
                    case 'E':
                        this.automate.setNbEtats(Integer.parseInt(ligne.substring(2)));

                        for(int i = 1; i < this.automate.getNbEtats(); i++){
                            this.automate.getEtats().add(new Etat(String.valueOf(i)));
                        }
                        break;
                    case 'I':
                        this.automate.getEtats().get(0).setEstInitial(false);
                        this.automate.getEtatsInitiaux().clear();
                        for (String nomEtat : ligne.substring(2).split(" ")) {
                            Etat etat = this.automate.getEtats().get(Integer.parseInt(nomEtat));
                            etat.setEstInitial(true);
                            this.automate.getEtatsInitiaux().add(etat);
                        }
                        break;
                    case 'F':
                        for (String nomEtat : ligne.substring(2).split(" ")) {
                            Etat etat = this.automate.getEtats().get(Integer.parseInt(nomEtat));
                            etat.setEstAcceptant(true);
                            this.automate.getEtatsAcceptants().add(etat);
                        }
                        break;
                    case 'T':
                        String[] ligneSplit = ligne.substring(2).split(" ");

                        Etat source, fin;
                        char entree, sortie;

                        source = this.automate.getEtats().get(Integer.parseInt(ligneSplit[0]));
                        fin = this.automate.getEtats().get(Integer.parseInt(ligneSplit[2]));

                        entree = ligneSplit[1].charAt(1);
                        sortie = AEF.getMeta();

                        if (ligneSplit.length > 3) {
                            if (sortie != ligneSplit[3].charAt(1))
                                sortie = ligneSplit[3].charAt(1);
                        }
                        this.automate.getTransitions().add(new Transition(source, fin, entree, sortie));
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

        System.out.println("======================\n" +
                "Automate : \"" + this.automate.getCommentaire() + "\"\nVocabulaire accepté : " + this.automate.getVocabulaireEntree() +
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
        ArrayList<Etat> etatsAutomate = this.automate.getEtats();
        Etat etatCourant = etatsAutomate.get(0);

        boolean containsC = false;

        for (int i = 0; i < mot.length(); ++i) {
            char c = mot.charAt(i);
            affichage += "État courant : " + etatCourant.getNom() + ", Entrée : " + c;
            ArrayList<Transition> transitionsDeE = this.automate.getTransitionsDeE(etatCourant);
            Transition t = new Transition();
            for(Transition t2: transitionsDeE){
                if (t2.getEntree().equals(c)){
                    t = t2;
                    containsC = true;
                }
            }

            if(containsC){
                etatCourant = t.getEtatSortie();
                char sortie = t.getSortie();

                if(sortie != AEF.getMeta()){
                    affichage += ", Sortie : " + sortie;
                    sortieS += sortie;
                }

                affichage += " Transition trouvée vers l'état : " + t.getEtatSortie().getNom() + '\n';
            } else{
                affichage += " Aucune transition trouvée\n";
                break;
            }
        }
        affichage += "État courant : " + etatCourant.getNom() + " Fin de chaine";
        if (etatCourant.isEstAcceptant())
            affichage += "\nÉtat acceptant";
        else
            affichage += "\nÉtat non acceptant";

        if(sortieS != "")
            affichage += "\nLa sortie de cette phrase est : " + sortieS;
        else
            affichage += "\nAucune sortie possible";
        affichage += "\n-- Fin de phrase --\n";
        motsTraites.add(affichage);
        return motsTraites;
    }

    /**
     * Méthode permettant d'obtenir un fichier .dot et d'éxecuter la commande pour obtenir le fichier .png correspondant
     */
    public void getDotFile(){
        String dotFile = "dotFile.dot";
        String pngFile = "pngFile.png";
        System.out.println("Création du fichier .dot");
        try(Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(DOSSIER + DOT + dotFile), "utf-8"))){
            writer.write("digraph G{");
            for(Transition t: this.automate.getTransitions())
                writer.write(t.getEtatEntree().getNom() + "->" + t.getEtatSortie().getNom() + " [label=\"" + t.getEntree() + "/" + t.getSortie() + "\"];");
            for(Etat i: this.automate.getEtatsInitiaux())
                writer.write("\"\"->" + i.getNom() + ";");
            writer.write("\"\" [shape=none]");
            for (Etat f: this.automate.getEtatsAcceptants())
                writer.write(f.getNom() + " [peripheries=2];");
            writer.write("}");

            System.out.println("Fichier .dot créé dans le dossier files/dot");

            Runtime rt = Runtime.getRuntime();
            try{
                Process exec = rt.exec("dot -Tpng " + DOSSIER + DOT + dotFile + " -o " + DOSSIER + PNG + pngFile);
                System.out.println("Fichier .png créé dans le dossier files/png");
            } catch (IOException e){
                System.out.println(e);
            }
        } catch (UnsupportedEncodingException e) {
            System.out.println(e);
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public AEF getAutomate() {
        return automate;
    }

    public void setAutomate(AEF automate) {
        this.automate = automate;
    }
}