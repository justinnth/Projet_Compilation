package compilation;

import java.util.Objects;

class Transition {

    public static char meta = '#';
    private final Etat etat;
    private final char sortie;

    Transition() {
        this.etat = new Etat();
        this.sortie = Transition.meta;
    }

    Transition(Etat e) {
        this.etat = e;
        this.sortie = Transition.meta;
    }

    Transition(Etat e, char s) {
        this.etat = e;
        this.sortie = s;
    }

    Etat getEtat() {
        return this.etat;
    }

    Character getSortie() {
        return this.sortie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transition that = (Transition) o;
        return sortie == that.sortie &&
                Objects.equals(etat, that.etat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(etat, sortie);
    }

    @Override
    public String toString() {
        return "Transition{" +
                "etat=" + etat +
                ", sortie=" + sortie +
                '}';
    }
}