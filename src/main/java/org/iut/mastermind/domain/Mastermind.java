package org.iut.mastermind.domain;

import org.iut.mastermind.domain.partie.Joueur;
import org.iut.mastermind.domain.partie.Partie;
import org.iut.mastermind.domain.partie.PartieRepository;
import org.iut.mastermind.domain.partie.ResultatPartie;
import org.iut.mastermind.domain.proposition.Reponse;
import org.iut.mastermind.domain.tirage.MotsRepository;
import org.iut.mastermind.domain.tirage.ServiceNombreAleatoire;
import org.iut.mastermind.domain.tirage.ServiceTirageMot;
import java.util.Optional;

public class Mastermind {
    private final PartieRepository partieRepository;
    private final ServiceTirageMot serviceTirageMot;

    public Mastermind(PartieRepository pr, MotsRepository mr, ServiceNombreAleatoire na) {
        this.partieRepository = pr;
        this.serviceTirageMot = new ServiceTirageMot(mr, na);
    }

    // on récupère éventuellement la partie enregistrée pour le joueur
    // si il y a une partie en cours, on renvoie false (pas de nouvelle partie)
    // sinon on utilise le service de tirage aléatoire pour obtenir un mot
    // et on initialise une nouvelle partie et on la stocke
    public boolean nouvellePartie(Joueur joueur) {
        if (partieRepository.getPartieEnregistree(joueur).isPresent()) {
            return false; //pas de nouvelle partie
        }else {
            String mot = serviceTirageMot.tirageMotAleatoire(); //on obtient un mot aléatoire
            Partie partie = Partie.create(joueur, mot); //on initialise une nouvelle partie
            partieRepository.create(partie); //on la stocke
            return true; // on a crée une nouvelle partie
        }
    }

    // on récupère éventuellement la partie enregistrée pour le joueur
    // si la partie n'est pas une partie en cours, on renvoie une erreur
    // sinon on retourne le resultat du mot proposé
    public ResultatPartie evaluation(Joueur joueur, String motPropose) {
        if (isJeuEnCours(partieRepository.getPartieEnregistree(joueur))) {
            return ResultatPartie.ERROR;
        }else{
            Partie partie = partieRepository.getPartieEnregistree(joueur).get();//on récupère la réponse du tour de jeu
            ResultatPartie resultat = calculeResultat(partie, motPropose); //on calcule le résultat de la partie
            return resultat;
        }
    }

    // on évalue le résultat du mot proposé pour le tour de jeu
    // on met à jour la bd pour la partie
    // on retourne le résulat de la partie
    private ResultatPartie calculeResultat(Partie partie, String motPropose) {
        Reponse res = partie.tourDeJeu(motPropose); //on évalue le résultat du mot proposé
        partieRepository.update(partie); //on met à jour la bd pour la partie
        return ResultatPartie.create(res, partie.isTerminee()); //on retourne le résultat de la partie
    }

    // si la partie en cours est vide, on renvoie false
    // sinon, on évalue si la partie est terminée
    private boolean isJeuEnCours(Optional<Partie> partieEnCours) {
        if(partieEnCours.isEmpty()) {
            return false;
        }else{
            return partieEnCours.get().isTerminee();
        }
    }

}
