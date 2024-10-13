package org.iut.mastermind.domain.proposition;

public class MotSecret {
    private final String motSecret;

    public MotSecret(String mot) {
        this.motSecret = mot;
    }

    // on retourne le résultat de la comparaison
    // du mot essayé avec le mot secret
    public Reponse compareProposition(String essai) {
        Reponse rep = new Reponse(motSecret); //on crée la réponse
        rep.compare(essai); // on va utiliser compare de Reponse pour parcourir chaque lettre de l'essai et comparer
        return rep;
    }
}
