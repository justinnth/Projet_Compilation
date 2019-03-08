package compilation;

import java.io.PrintWriter;
import java.util.*;

/**
 * Classe représentant un Etat
 */
public class Etat {
    /**
     * Nom de l'état ou numéro suivant l'automate
     */
    private String nom;

    /**
     * Etat initial ou non
     */
    private boolean estInitial;

    /**
     * Etat acceptant ou non
     */
    private boolean estAcceptant;

    /**
     * Constructeur d'un état vide
     */
    public Etat() {
        this.nom = "";
        this.estInitial = false;
        this.estAcceptant = false;
    }

    /**
     * Constructeur d'un état avec un nom ou id
     * @param nom Nom de l'état
     */
    public Etat(String nom) {
        this();
        this.nom = nom;
    }

    /**
     * Constrcteur d'un état avec un nom/id et s'il est acceptant et/ou initial
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

    /**
     * Constructeur d'un etat à partir d'un autre etat
     * @param e Etat
     */
    public Etat(Etat e){
        this.nom = e.nom;
        this.estAcceptant = e.estAcceptant;
        this.estInitial = e.estInitial;
    }

    /**
     * Getter pour le nom
     * @return Nom de l'état
     */
    public String getNom() {
        return nom;
    }

    /**
     * Setter pour le nom
     * @param nom Nom à donner à l'etat
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Initial ou non
     * @return true si initial, false sinon
     */
    public boolean isEstInitial() {
        return estInitial;
    }

    /**
     * Setter pour l'etat initial ou non
     * @param estInitial true pour rendre l'etat initial, false sinon
     */
    public void setEstInitial(boolean estInitial) {
        this.estInitial = estInitial;
    }

    /**
     * Acceptant ou non
     * @return true si acceptant, false sinon
     */
    public boolean isEstAcceptant() {
        return estAcceptant;
    }

    /**
     * Setter pour l'etat acceptant ou non
     * @param estAcceptant true pour rendre l'état acceptant, false sinon
     */
    public void setEstAcceptant(boolean estAcceptant) {
        this.estAcceptant = estAcceptant;
    }

    @Override
    public String toString() {
        String toString = "Etat { nom=" + this.nom + ", initial=";
        toString += (this.estInitial) ? "oui" : "non";
        toString += ", acceptant=";
        toString += (this.estAcceptant) ? "oui }" : "non }";
        return toString;
    }
}
