package compilation;

import java.util.ArrayList;

public class Etat {
    private String nom;
    private boolean estInitial;
    private boolean estAcceptant;
    private ArrayList<Transition> transitions;

    /**
     * Constructeur d'un état vide
     */
    public Etat() {
        this.nom = "";
        this.estInitial = false;
        this.estAcceptant = false;
        this.transitions = new ArrayList<>();
    }

    /**
     * Constructeur d'un état avec un nom
     * @param nom Nom de l'état
     */
    public Etat(String nom) {
        this();
        this.nom = nom;
    }

    /**
     * Constrcteur d'un état avec un nom et s'il est acceptant ou initial
     * @param nom Nom de l'état
     * @param estInitial Initial ou non
     * @param estAcceptant Acceptant ou non
     */
    public Etat(String nom, boolean estInitial, boolean estAcceptant) {
        this();
        this.nom = nom;
        this.estInitial = estInitial;
        this.estAcceptant = estAcceptant;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public boolean isEstInitial() {
        return estInitial;
    }

    public void setEstInitial(boolean estInitial) {
        this.estInitial = estInitial;
    }

    public boolean isEstAcceptant() {
        return estAcceptant;
    }

    public void setEstAcceptant(boolean estAcceptant) {
        this.estAcceptant = estAcceptant;
    }

    public ArrayList<Transition> getTransitions() {
        return transitions;
    }

    public void addTransition(char entree, Etat fin, char sortie){

    }
}
