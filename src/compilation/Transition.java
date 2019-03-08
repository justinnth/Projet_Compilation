package compilation;

/**
 * Classe représentant une transition
 * Une transition est nécessairement liée à un État et à un caractère d'entrée
 * Pas nécessairement à un caractère de sortie, celui-ci peut être lambda
 */
class Transition {
    private Etat etatEntree; // État d'entrée de la transition
    private Etat etatSortie; // État de sortie auquel elle est associée
    private Character entree; // Entrée nécessaire pour la transition
    private Character sortie; // Sortie possible de la transition

    /**
     * Constructeur d'une transition avec un nouvel etatSortie vide, et sans sortie associée
     */
    public Transition() {
        this.etatEntree = new Etat();
        this.etatSortie = new Etat();
        this.entree = AEF.getMeta();
        this.sortie = AEF.getMeta();
    }

    /**
     * Constructeur d'une transition avec des paramètres déjà définis
     * @param etatEntree État d'entrée
     * @param etatSortie État de sortie
     * @param entree Caractère d'entrée
     * @param sortie Caractère de sortie
     */
    public Transition(Etat etatEntree, Etat etatSortie, Character entree, Character sortie) {
        this.etatEntree = etatEntree;
        this.etatSortie = etatSortie;
        this.entree = entree;
        this.sortie = sortie;
    }

    public Etat getEtatEntree() {
        return etatEntree;
    }

    public Etat getEtatSortie() {
        return etatSortie;
    }

    public Character getEntree() {
        return entree;
    }

    public Character getSortie() {
        return sortie;
    }

    /**
     * Override de la méthode equals
     * @param o Object auquel on compare la transition pour savoir si elles sont les mêmes
     * @return true si égalité, false sinon
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Si o est l'objet alors il y a forcément égalité
        else if(o instanceof Transition){
            // Si o est une Transition alors on compare chaque valeurs de la transition
            if (((Transition) o).getEntree().equals(this.getEntree()) &&
                    ((Transition) o).getSortie().equals(this.getSortie()) &&
                    ((Transition) o).getEtatEntree().equals(this.getEtatEntree()) &&
                    ((Transition) o).getEtatSortie().equals(this.getEtatSortie()))
                return true;
            return false;
        }
        else return false; // Sinon il n'y a pas égalité
    }

    /**
     * Override de la méthode toString afin d'obtenir un affichage plus joli
     * @return String
     */
    @Override
    public String toString() {
        return "Transition { " + "etatEntree=" + etatEntree + ", entree=" + entree + ", etatSortie=" + etatSortie + ", sortie=" + sortie + " }";
    }
}