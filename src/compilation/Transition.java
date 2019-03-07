package compilation;

import java.util.Objects;

/**
 * Classe représentant une transition
 * Une transition est nécessairement liée à un État et à un caractère d'entrée
 * Pas nécessairement à un caractère de sortie, celui-ci peut être lambda
 */
class Transition implements Comparable {
    /**
     * Caractère d'entrée par défaut (lambda)
     */
    public static Character meta = '#';

    private final Etat etatSortie; // État de sortie auquel elle est associée
    private final Character sortie; // Sortie possible de la transition

    /**
     * Constructeur d'une transition avec un nouvel etatSortie vide, et sans sortie associée
     */
    Transition() {
        this.etatSortie = new Etat();
        this.sortie = Transition.meta;
    }

    /**
     * Constructeur d'une transition avec un etatSortie, et sans sortie associée
     * @param e État auquel la transition est liée
     */
    Transition(Etat e) {
        this.etatSortie = e;
        this.sortie = Transition.meta;
    }

    /**
     * Constructeur d'une transition avec un etatSortie et un caractère de sortie associé
     * @param e État auquel la transition est associée
     * @param s Caractère de sortie auquel la transition est associée
     */
    Transition(Etat e, char s) {
        this.etatSortie = e;
        this.sortie = s;
    }

    /**
     * Récupère l'état de sortie de la transition
     * @return État de sortie
     */
    Etat getEtatSortie() {
        return this.etatSortie;
    }

    /**
     * Récupère le caractère de sortie
     * @return Caractère de sortie
     */
    Character getSortie() {
        return this.sortie;
    }

    /**
     * Override de la méthode equals
     * @param o Object auquel on compare la transition pour savoir si elles sont les mêmes
     * @return true si égalité, false sinon
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Si o est l'objet alors il y a forcément égalité
        else if(o instanceof Transition) return this.compareTo((Transition) o) == 0; // Si o est une Transition alors on compare les 2 grâce à compareTo, si la différence de leur hash code est de 0, alors il y a égalité
        else return false; // Sinon il n'y a pas égalité
    }

    /**
     * Override de la méthode hashCode
     * @return
     */
    @Override
    public int hashCode() {
        int hash = this.etatSortie.hashCode() * this.etatSortie.hashCode();
        hash += this.sortie.hashCode() * this.sortie.hashCode();
        return hash;
    }

    /**
     * Override de la méthode toString afin d'obtenir un affichage plus joli
     * @return String
     */
    @Override
    public String toString() {
        return "Transition {\n" + "\tetatSortie=" + etatSortie + ", sortie=" + sortie + "\n}";
    }

    /**
     * Override de la méthode compareTo implémentée de l'interface Comparable
     * @param o Objet auquel on compare la transition
     * @return La différence des 2 hash codes des objets comparés
     */
    @Override
    public int compareTo(Object o) {
        if(o == null)
            throw new NullPointerException();
        if(o.getClass() != this.getClass())
            throw new ClassCastException();

        final Transition t = (Transition) o;
        return this.hashCode() - t.hashCode();
    }
}