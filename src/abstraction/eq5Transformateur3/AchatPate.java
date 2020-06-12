package abstraction.eq5Transformateur3;

import abstraction.eq2Producteur2.Producteur2;
import abstraction.eq8Romu.contratsCadres.Echeancier;
import abstraction.eq8Romu.contratsCadres.ExemplaireContratCadre;
import abstraction.eq8Romu.contratsCadres.IVendeurContratCadre;
import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.Pate;
import abstraction.fourni.Filiere;
/**Eva DUPUY*/
public class AchatPate {
    /**
     * Gère les achats de pates avec le contrat cadre et négocie les prix.
     * Elle est agrégé dans la classe Transformateur3.
     */
    //TODO ajouter les methodes pour acheter de la pate de cacao
	
	private double prixMax;
    private Transformateur3 acteur;
		
    public AchatPate(Transformateur3 acteur) {
        this.acteur = acteur;
    }
    public Echeancier contrePropositionDeLAcheteur(ExemplaireContratCadre contrat) {
		//TODO: changer les 4 lignes de code, elles sont écrites de façon a ne pas faire d'erreur de compilations 
		//on releve les parametres importants de l'offre du vendeur
		int stepDeb = contrat.getEcheancier().getStepDebut();
		double quantite = contrat.getEcheancier().getQuantite(0);
		//notre offre initial
		int stepDeb0 = contrat.getEcheancier().getStepDebut();
		double quantite0 = contrat.getEcheancier().getQuantite(0);
		
		//si on est d'accord avec la proposition:
		if(stepDeb == stepDeb0 && Math.abs(quantite - quantite0) < 0.25*quantite0) {
			// la date intitile gardee et l'écart est inférieur à 25% de la quantité demandée
			return contrat.getEcheancier();
		}
		
		//sinon, on peut soit arreter les negociations, soit reproproposer
		else if(true/*acheteur met fin aux négo*/) {
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
		//le prix max est celui qui nous permet de faire 40% de marge
		Chocolat choco =  (Chocolat)(contrat.getProduit());
		prixMax = 0.6*this.acteur.getInfoCoursVente().getCours(choco)- 
				this.acteur.getStock().getTransformationCostPate().getValeur(); //ajouter prix des stocks??
		if(contrat.getPrixALaTonne()<= prixMax) {
			return contrat.getPrixALaTonne();
		}
		else {
			return prixMax;
		}
	}

	
	public void receptionner(Object produit, double quantite, ExemplaireContratCadre contrat) {
		if(produit instanceof Pate) {
			Pate produitPate = (Pate)(produit);
			this.acteur.getStock().ajoutPate(produitPate, quantite, contrat.getPrixALaTonne());
		}	
	}

	/**
	 * Decide et commence les négociations d'un nouveau contract cadre
	 * Est appellé par next() de l'acteur Transformateur3
	 */
	public void commencerNegociations() {
		//decision de commencer les négociations
		//si c'est bon on fait cet appel 
		IVendeurContratCadre vendeur = (Producteur2) Filiere.LA_FILIERE.getActeur("EQ2");
		Filiere.LA_FILIERE.getSuperviseurContratCadre().demande(acteur, vendeur, Pate.PATE_BASSE, new Echeancier(), this.acteur.getCryptogramme());
	}
}