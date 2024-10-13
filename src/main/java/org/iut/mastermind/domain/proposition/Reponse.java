package org.iut.mastermind.domain.proposition;

import java.util.ArrayList;
import java.util.List;
import static java.util.Collections.unmodifiableList;

public class Reponse {
    private final String motSecret;
    private final List<Lettre> resultat = new ArrayList<>();
    private int position;

    public Reponse(String mot) {
        this.motSecret = mot;
    }

    // on récupère la lettre à la position dans le résultat
    public Lettre lettre(int position) {
        return resultat.get(position);
    }

    // on construit le résultat en analysant chaque lettre
    // du mot proposé
    public void compare(String essai) {
        if (resultat.isEmpty()){
            //on initialise le résultat avec des lettres incorrectes partout
            for(int i =0 ; i<motSecret.length();i++)
                resultat.add(Lettre.INCORRECTE);
        }

        for(position = 0; position < motSecret.length(); position++) {
            Lettre lettre = evaluationCaractere(essai.charAt(position)); //on évalue le caractère pour le statut de la lettre
            resultat.set(position,lettre); //on ajoute la lettre au résultat
        }
    }

    // si toutes les lettres sont placées
    public boolean lettresToutesPlacees() {
        boolean toutesPlacees = true;
        for (Lettre lettre : resultat) { //on parcourt tout le résultat
            if (lettre != Lettre.PLACEE) { //si une lettre n'est bien pas placée
                toutesPlacees = false; //on renvoie faux
            }
        }
        return toutesPlacees;
    }

    public List<Lettre> lettresResultat() {
        return unmodifiableList(resultat);
    }

    // renvoie le statut du caractère
    private Lettre evaluationCaractere(char carCourant) {
        if (estPlace(carCourant)) { //si le carac courant est egal à la lettre à la position c'est placee
            return Lettre.PLACEE;
        }
        if (estPresent(carCourant)){ //si le carac courant est different de la lettre à la position c'est non placee
            return Lettre.NON_PLACEE;
        }
        return Lettre.INCORRECTE; //sinon c'est incorrecte

    }

    // le caractère est présent dans le mot secret
    private boolean estPresent(char carCourant) {
        return motSecret.contains(String.valueOf(carCourant));
    }

    // le caractère est placé dans le mot secret
    private boolean estPlace(char carCourant) {
        return carCourant == motSecret.charAt(position);
    }

}
