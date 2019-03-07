package compilation;

import java.util.*;

/**
 * Classe représentant un Etat
 */
public class Etat implements Comparable {
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
     * Mappage des états avec une clé etant le caractère d'entrée
     */
    private Map<Character, Set<Transition>> transitions;

    /**
     * Constructeur d'un état vide
     */
    public Etat() {
        this.nom = "";
        this.estInitial = false;
        this.estAcceptant = false;
        this.transitions = new HashMap<>();
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
        this.transitions = new HashMap<>(e.transitions);
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

    /**
     * Getter pour la liste des transitions
     * @return Liste des transitions et leur caractère associé
     */
    public Map<Character, Set<Transition>> getTransitions() {
        return transitions;
    }

    /**
     * Getter pour l'ensemble des caractères d'entrée accéptés par les transitions de l'état
     * @return Ensemble des caractères
     */
    public Set<Character> ensembleCaracteresEntree(){
        return this.transitions.keySet();
    }

    /**
     * Getter pour l'ensemble des caractères de sortie donnés par les transitions de l'état
     * Pour cela on va utiliser une méthode de la classe Transition
     * @return Ensemble des caractères
     */
    public Set<Character> ensembleCaracteresSortie(){
        Set<Character> ensembleSortie = new HashSet<>();

        for (Set<Transition> ensembleTransition : this.transitions.values()){
            for(Transition t: ensembleTransition){
                ensembleSortie.add(t.getSortie());
            }
        }
        return ensembleSortie;
    }

    /**
     * Méthode permettant d'ajouter une transition à l'état
     * @param entree Caractère d'entrée de la transition
     * @param t Transition à ajouter
     * @return true si ajoutée, false sinon
     */
    private boolean addTransition(char entree, Transition t){
        boolean added;
        if (this.transitions.containsKey(entree))
            added = this.transitions.get(entree).add(t);
        else{
            Set<Transition> set = new HashSet<>();
            set.add(t);
            this.transitions.put(entree, set);
            added = true;
        }
        return added;
    }

    /**
     * Méthodes pour ajouter une transition à l'état
     * @param entree caractère d'entrée dans la transition
     * @param etatSortie état de sortie de la transition
     * @param sortie caractère de sortie de la transition
     * @return true si la transition est ajoutée, false sinon
     */
    public boolean addTransition(char entree, Etat etatSortie, char sortie){
        return this.addTransition(entree, new Transition(etatSortie, sortie));
    }

    public boolean addTransition(char entree, Etat etatSortie){
        return this.addTransition(entree, new Transition(etatSortie));
    }

    public boolean addTransition(Etat etatSortie){
        return this.addTransition(Transition.meta, new Transition(etatSortie));
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    @Override
    public String toString() {
        String toString = "Etat { nom=" + this.nom + ", initial=";
        toString += (this.estInitial) ? " oui" : " non";
        toString += ", acceptant=";
        toString += (this.estAcceptant) ? " oui" : " non";
        return toString;
    }
}
