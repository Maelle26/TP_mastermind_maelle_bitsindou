package org.iut.mastermind.domain.partie;

public class Joueur {

    private final String nom;

    // constructeur
    public Joueur(String nom) {
        this.nom = nom;
    }

    // getter nom joueur
    public String getNom() {
        return this.nom;
    }

    // Méthode equals qui va comparer les noms de chaque objet
    @Override
    public boolean equals(Object o) {
        if (this.getClass() != o.getClass())
            return false; // si les classes sont diff, on renvoie faux
        Joueur joueur = (Joueur) o; // on cast l'objet en Joueur
        return nom.equals(joueur.nom); // on compare les noms
    }

    //Méthode hashCode qui va retourner le hashcode du nom
    @Override
    public int hashCode() {
        return nom.hashCode();
    }

}
