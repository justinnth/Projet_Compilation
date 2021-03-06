package compilation;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Determinisation {
    private AEF automateInitial;
    private AEF automateDeterminise;

    /**
     * Constructeur par défaut
     */
    public Determinisation(){}

    /**
     * Constructeur avec un automate déjà créé
     * @param automate Automate initial à déterminiser
     */
    public Determinisation(AEF automate){
        this.automateInitial = automate;
    }

    /**
     * Défini un automate à déterminiser
     * @param automateInitial Automate à déterminiser
     */
    public void setAutomateInitial(AEF automateInitial) {
        this.automateInitial = automateInitial;
    }

    /**
     * Algorithme de calcul lambda-fermeture
     * @param p Ensemble d'états
     * @return Liste des lambda-fermeture
     */
    private ArrayList<Etat> lambdaFermeture(ArrayList<Etat> p){
        ArrayList<Etat> F = new ArrayList<>();
        Character meta = AEF.getMeta();
        while (!p.isEmpty()){
            Etat t = p.get(0);
            if(!F.contains(t)){
                F.add(t);
                ArrayList<Transition> transitionDeU = this.automateInitial.getTransitionsDeE(t);
                for(Transition t2: transitionDeU){
                    if(t2.getEntree().equals(meta))
                        p.add(t2.getEtatSortie());
                }
            }
            p.remove(0);
        }
        return F;
    }

    /**
     *
     * @param e État pour lequel on souhaite obtenir les lambda fermetures
     * @return Liste d'états
     */
    public ArrayList<Etat> lambdaFermeture(Etat e){
        ArrayList<Etat> listeEtats = new ArrayList<>();
        listeEtats.add(e);
        return lambdaFermeture(listeEtats);
    }

    /**
     * Méthode implémentant l'algorithme 2 du cours pour le calcul de transiter
     * @param T Liste d'états
     * @param a Caractère
     * @return Liste d'états
     */
    public ArrayList<Etat> transiter(ArrayList<Etat> T, Character a){
        ArrayList<Etat> F = new ArrayList<>();
        for(Etat t: T){
            ArrayList<Transition> TransitionsDet = this.automateInitial.getTransitionsDeE(t);
            for(Transition trans: TransitionsDet) {
                if(trans.getEntree().equals(a)){
                    if (!F.contains(trans.getEtatSortie()))
                        F.add(trans.getEtatSortie());
                }
            }
        }
        return F;
    }

    /**
     * Méthode permettant de déterminiser l'automate initial
     * Algorithme 3 du cours
     * @return Un automate à états finis déterministe
     */
    public AEF determinise(){
        ArrayList<ArrayList<Etat>> P = new ArrayList<>();
        P.add(lambdaFermeture(this.automateInitial.getEtatsInitiaux()));

        ArrayList<ArrayList<Etat>> L = new ArrayList<>();
        HashMap<ArrayList<Etat>, Etat> hashMap = new HashMap<>();
        ArrayList<Transition> D = new ArrayList<>();
        ArrayList<Etat> etatsAcceptants = new ArrayList<>();

        while (!P.isEmpty()){
            ArrayList<Etat> T = P.get(0);
            if(!L.contains(T)){
                L.add(T);
                Etat etatInitial;
                if(hashMap.containsKey(T))
                    etatInitial = hashMap.get(T);
                else{
                    etatInitial = new Etat(String.valueOf(hashMap.size()), true, false);
                    hashMap.put(T, etatInitial);
                }
                ArrayList<Character> vocEntree = this.automateInitial.getVocabulaireEntree();
                for(char a: vocEntree){
                    ArrayList<Etat> U = lambdaFermeture(transiter(T, a));
                    Etat etatFinal;
                    if(hashMap.containsKey(U))
                        etatFinal = hashMap.get(U);
                    else{
                        etatFinal = new Etat(String.valueOf(hashMap.size()), false, true);
                        hashMap.put(U, etatFinal);
                    }
                    for(Etat acceptant: this.automateInitial.getEtatsAcceptants()){
                        if(U.contains(acceptant)){
                            if(!etatsAcceptants.contains(etatFinal))
                                etatsAcceptants.add(etatFinal);
                        }
                    }
                    D.add(new Transition(etatInitial, etatFinal, a, AEF.getMeta()));
                    P.add(U);
                }
            }
            P.remove(0);
        }
        ArrayList<Etat> initiaux = new ArrayList<>();
        initiaux.add(new Etat("0", true, false));
        this.automateDeterminise = new AEF(this.automateInitial.getCommentaire() + " - Deterministe", AEF.getMeta(), this.automateInitial.getVocabulaireEntree(), this.automateInitial.getVocabulaireSortie(), this.automateInitial.getNbEtats(), initiaux, etatsAcceptants, D);
        System.out.println(automateDeterminise);
        return this.automateDeterminise;
    }

    /**
     * Génère le fichier .descr de l'automate déterministe
     * Cette méthode ajoute ligne par ligne dans un fichier avec d'obtenir une description valable
     * @param automate Automate
     */
    public void getDescr(AEF automate){
        Scanner in = new Scanner(System.in);
        System.out.println("Saisir le nom du fichier à enregistrer : ");
        String nomFichier = in.nextLine();
        try(Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Moteur.DOSSIER + Moteur.DESCR + nomFichier + ".descr"), "utf-8"))){
            if(!automate.getCommentaire().isEmpty())
                writer.write("C " + automate.getCommentaire() + "\n");

            writer.write("M " + AEF.getMeta() + "\n");

            String v = "\"";
            for(char c: automate.getVocabulaireEntree())
                v += c;
            v += "\"";
            writer.write("V " + v + "\n");

            if(!automate.getVocabulaireSortie().isEmpty()){
                String o = "\"";
                for (char c: automate.getVocabulaireSortie())
                    o += c;
                o += "\"";
                writer.write("O " + o + "\n");
            }

            writer.write("E " + automate.getNbEtats() + "\n");

            String i = "";
            for (Etat e: automate.getEtatsInitiaux())
                i += e.getNom() + " ";
            writer.write("I " + i + "\n");

            String f = "";
            for (Etat e: automate.getEtatsAcceptants())
                f += e.getNom() + " ";
            writer.write("F " + f + "\n");

            for (Transition t: automate.getTransitions())
                writer.write("T " + t.getEtatEntree().getNom() + " '" + t.getEntree() + "' " + t.getEtatSortie().getNom() + " '" + t.getSortie() + "'");

            System.out.println("Fichier .descr généré dans le dossier files/descr");
        } catch (UnsupportedEncodingException e){
            System.out.println(e);
        } catch (FileNotFoundException e){
            System.out.println(e);
        } catch (IOException e){
            System.out.println(e);
        }
    }
}