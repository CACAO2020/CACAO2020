package abstraction.eq5Transformateur3;

import abstraction.eq8Romu.contratsCadres.Echeancier;
import abstraction.eq8Romu.contratsCadres.ExemplaireContratCadre;

public class AchatPate {
    /**
     * Gère les achats de pates avec le contrat cadre et négocie les prix.
     * Elle est agrégé dans la classe Transformateur3.
     */
    //TODO ajouter les methodes pour acheter de la pate de cacao

    private Transformateur3 acteur;
		
    public AchatPate(Transformateur3 acteur) {
        this.acteur = acteur;
    }
    public Echeancier contrePropositionDeLAcheteur(ExemplaireContratCadre contrat) {
		
		//on releve les parametres importants de l'offre du vendeur
		int stepDeb = contrat.getEcheancier().getStepDebut();
		double quantite = contrat.getEcheancier().getQuantite(step);
		//notre offre initial
		int stepDeb0 = contrat.getEcheancier().get(0).getStepDebut();
		double quantite0 = contrat.getEcheancier().get(0).getQuantite(step);
		
		//si on est d'accord avec la proposition:
		if(stepDeb == stepDeb0 && Math.abs(quantite - quantite0) < 0.25*quantite0) {
			// la date intitile gardee et l'écart est inférieur à 25% de la quantité demandée
			return contrat.getEcheancier();
		}
		
		//sinon, on peut soit arreter les negociations, soit reproproposer
		else if(/*acheteur met fin aux négo*/) {
			return null;
			}
		
		else {
			if(quantite-quantite0<0) {
				return new Echeancier(stepDeb0,contrat.getEcheancier().getStepFin(),quantite0*(1-0.25));
			}
			else {
				return new Echeancier( stepDeb0, contrat.getEcheancier().getStepFin(), quantite0*(1+0.25));
			}
			}
	};



	public double contrePropositionPrixAcheteur(ExemplaireContratCadre contrat) {
		return 0;
	}

	public void receptionner(Object produit, double quantite, ExemplaireContratCadre contrat) {
	}
}