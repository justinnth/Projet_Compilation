package compilation;

import java.util.ArrayList;

/**
 * Classe représentant un automate à états finis
 * @author justinnorth
 */
public class AEF {
    private String commentaire;
    private static Character meta = '#';
    private ArrayList<Character> vocabulaireEntree, vocabulaireSortie;
    private int nbEtats;

    private ArrayList<Etat> etats;
    private ArrayList<Etat> etatsInitiaux;
    private ArrayList<Etat> etatsAcceptants;
    private ArrayList<Transition> transitions;

    /**
     * Constructeur par défaut
     * Constructeur d'un automate vide
     */
    public AEF() {
        this.etats = new ArrayList<>();
        this.etats.add(new Etat("0", true, false));

        this.etatsInitiaux = new ArrayList<>();
        this.etatsInitiaux.add(this.etats.get(0));

        this.commentaire = "";
        this.vocabulaireEntree = new ArrayList<>();
        this.vocabulaireSortie = new ArrayList<>();
        this.nbEtats = this.etats.size();

        this.etatsAcceptants = new ArrayList<>();
        this.transitions = new ArrayList<>();
    }

    /**
     * Initialisation d'un automate à partir de valeurs connues
     * @param commentaire Nom de l'automate
     * @param vocabulaireEntree Vocabulaire d'entrée
     * @param vocabulaireSortie Vocabulaire de sortie
     * @param nbEtats Nombre d'états
     * @param etatsInitiaux Liste des états initiaux
     * @param etatsAcceptants Liste des états acceptants
     * @param transitions Liste des transition
     */
    public AEF(String commentaire, ArrayList<Character> vocabulaireEntree, ArrayList<Character> vocabulaireSortie, int nbEtats, ArrayList<Etat> etatsInitiaux, ArrayList<Etat> etatsAcceptants, ArrayList<Transition> transitions) {
        this();
        this.commentaire = commentaire;
        this.vocabulaireEntree = vocabulaireEntree;
        this.vocabulaireSortie = vocabulaireSortie;
        this.nbEtats = nbEtats;
        this.etatsInitiaux = etatsInitiaux;
        this.etatsAcceptants = etatsAcceptants;
        this.transitions = transitions;
    }

    /**
     * Initialisation d'un automate à partir de valeurs connues
     * @param commentaire Nom de l'automate
     * @param meta Meta-caractère
     * @param vocabulaireEntree Vocabulaire d'entrée
     * @param vocabulaireSortie Vocabulaire de sortie
     * @param nbEtats Nombre d'états
     * @param etatsInitiaux Liste des états initiaux
     * @param etatsAcceptants Liste des états acceptants
     * @param transitions Liste des transition
     */
    public AEF(String commentaire, Character meta, ArrayList<Character> vocabulaireEntree, ArrayList<Character> vocabulaireSortie, int nbEtats, ArrayList<Etat> etatsInitiaux, ArrayList<Etat> etatsAcceptants, ArrayList<Transition> transitions) {
        this();
        this.commentaire = commentaire;
        AEF.meta = meta;
        this.vocabulaireEntree = vocabulaireEntree;
        this.vocabulaireSortie = vocabulaireSortie;
        this.nbEtats = nbEtats;
        this.etatsInitiaux = etatsInitiaux;
        this.etatsAcceptants = etatsAcceptants;
        this.transitions = transitions;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public static Character getMeta() {
        return meta;
    }

    public static void setMeta(Character meta) {
        AEF.meta = meta;
    }

    public ArrayList<Character> getVocabulaireEntree() {
        return vocabulaireEntree;
    }

    public void setVocabulaireEntree(ArrayList<Character> vocabulaireEntree) {
        this.vocabulaireEntree = vocabulaireEntree;
    }

    public ArrayList<Character> getVocabulaireSortie() {
        return vocabulaireSortie;
    }

    public void setVocabulaireSortie(ArrayList<Character> vocabulaireSortie) {
        this.vocabulaireSortie = vocabulaireSortie;
    }

    public int getNbEtats() {
        return nbEtats;
    }

    public void setNbEtats(int nbEtats) {
        this.nbEtats = nbEtats;
    }

    public ArrayList<Etat> getEtatsInitiaux() {
        return etatsInitiaux;
    }

    public void setEtatsInitiaux(ArrayList<Etat> etatsInitiaux) {
        this.etatsInitiaux = etatsInitiaux;
    }

    public ArrayList<Etat> getEtatsAcceptants() {
        return etatsAcceptants;
    }

    public void setEtatsAcceptants(ArrayList<Etat> etatsAcceptants) {
        this.etatsAcceptants = etatsAcceptants;
    }

    public ArrayList<Transition> getTransitions() {
        return transitions;
    }

    public void setTransitions(ArrayList<Transition> transitions) {
        this.transitions = transitions;
    }

    public ArrayList<Etat> getEtats() {
        return etats;
    }

    public void setEtats(ArrayList<Etat> etats) {
        this.etats = etats;
    }

    /**
     * Donne toutes les transition qui ont comme état initial celui fournit en paramètres
     * @param initial État initial
     * @return Liste de transitions
     */
    public ArrayList<Transition> getTransitionsDeE(Etat initial){
        ArrayList<Transition> t = new ArrayList<>();
        for(Transition t2: this.transitions){
            if (t2.getEtatEntree().getNom().equals(initial.getNom())){
                t.add(t2);
            }
        }
        return t;
    }

    @Override
    public String toString() {
        String affiche = "Nom : " + this.commentaire +
                "\nCaractère par défaut : " + AEF.meta +
                "\nVocabulaire d'entrée : ";
        for (char c : this.vocabulaireEntree)
            affiche += c;
        affiche += "\nVocabulaire de sortie : ";
        for (char c : this.vocabulaireSortie)
            affiche += c;
        affiche += "\nNombre d'états : " + this.nbEtats;
        affiche += "\nListe des états initiaux : ";
        for (Etat e : this.etatsInitiaux)
            affiche += e.toString();
        affiche += "\nListe des états acceptants : ";
        for (Etat e : this.etatsAcceptants)
            affiche += e.toString();
        affiche += "\nListe des transitions : ";
        for (Transition t : this.transitions)
            affiche += t.toString();
        return affiche;
    }
}
