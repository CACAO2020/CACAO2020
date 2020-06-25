package abstraction.eq5Transformateur3;

import java.util.ArrayList;
import java.util.List;

import abstraction.eq4Transformateur2.Transformateur2;
import abstraction.eq4Transformateur2.Transformateur2_contratCadre;
import abstraction.eq8Romu.contratsCadres.ContratCadre;
import abstraction.eq8Romu.contratsCadres.Echeancier;
import abstraction.eq8Romu.contratsCadres.ExemplaireContratCadre;
import abstraction.eq8Romu.contratsCadres.IVendeurContratCadre;
import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.Feve;
import abstraction.eq8Romu.produits.Pate;
import abstraction.fourni.Filiere;

/**@author Eva DUPUY et Simon MORO
*  Gère les achats de pates avec le contrat cadre et négocie les prix.
*  Elle est agrégé dans la classe Transformateur3.
*/
public class AchatPate {
	
    private Transformateur3 acteur;
	private double quantiteParTourMax;
	private ExemplaireContratCadre contractActuel;
	private int defaultContractTime;
		
    public AchatPate(Transformateur3 acteur, int defaultContractTime) {
		this.acteur = acteur;
		this.quantiteParTourMax = 100;
		this.contractActuel = null;
		this.defaultContractTime = defaultContractTime;
    }
    public boolean valideOffre(ExemplaireContratCadre contrat, int stepFin ) {
    	boolean flag = true;
    	for (int i = contrat.getEcheancier().getStepDebut(); i< 1+ stepFin; i++) {
    		if (contrat.getEcheancier().getQuantite(i) > quantiteParTourMax ) {
    			flag = false;
    		}
    	}
    	return flag;
    }
    
    public List<Double> offreAdaptee (ExemplaireContratCadre contrat, int stepFin){
    	List<Double> offreAdaptee = new ArrayList<>(contrat.getEcheancier().getNbEcheances());
    	for (int i = contrat.getEcheancier().getStepDebut(); i< stepFin; i++) {
    		if (contrat.getEcheancier().getQuantite(i) > quantiteParTourMax ) {
    			offreAdaptee.add(quantiteParTourMax);
    		}
    		else {
    			offreAdaptee.add(contrat.getEcheancier().getQuantite(i));
    		}
    	}
    	return offreAdaptee;
    }
    
    public Echeancier contrePropositionDeLAcheteur(ExemplaireContratCadre contrat) {
    	
		//on releve les parametres importants de l'offre du vendeur
		int stepDeb = contrat.getEcheancier().getStepDebut();
		int stepFin = contrat.getEcheancier().getStepFin();
				
		//notre offre initial
		int stepDeb0 = contrat.getEcheancier().getStepDebut();
		
		//si on est d'accord avec la proposition:
		if (stepDeb == stepDeb0 && stepFin < 10 && valideOffre(contrat, stepFin)) {
			return contrat.getEcheancier();
		}
		//sinon, on peut soit arreter les negociations, soit reproproposer

		//StepFin pas OK; Quantitées OK
		else if(stepFin >= 10 && valideOffre(contrat, 10)) {
			List<Double> quantites = new ArrayList<>();
			for (int i = stepDeb; i < stepDeb + 10; i++) {
				quantites.add(contrat.getEcheancier().getQuantite(i));
				}
			return new Echeancier(stepDeb0, quantites);
			}
		//StepFin OK mais pas quantitées
		else if (stepFin< 10 && !valideOffre(contrat, stepFin)) {
			return new Echeancier (stepDeb0, offreAdaptee (contrat, stepFin));
		}
		//StepFin pas OK et quantitées pas OK
		else if (stepFin >= 10 && !valideOffre(contrat, 10)) {
			return new Echeancier (stepDeb0, offreAdaptee (contrat, 10));
		}
		// il reste juste le cas où step0 pas OK mais les deux autres OK
		else {
			List<Double> quantites = new ArrayList<>();
			for (int i = stepDeb; i< stepFin; i++) {
				quantites.add(contrat.getEcheancier().getQuantite(i));
			}
			return new Echeancier(stepDeb0, quantites);
     }
}

	public double contrePropositionPrixAcheteur(ExemplaireContratCadre contrat) {
		//le prix max est celui qui nous permet de faire 40% de marge
		System.out.println();
		Pate pate =  (Pate)(contrat.getProduit());
		double prix1 = 0.6*this.acteur.getInfoCoursVente().getMoy(Stock.getProduct(pate)) - 
				this.acteur.getStock().getTransformationCostPate().getValeur();
		double prix2 = 0.9*this.acteur.getInfoCoursVente().getMoy(Stock.getProduct(pate)) - 
				this.acteur.getStock().getTransformationCostPate().getValeur();
		double prixMax = 0;
		if (prix1 > 0) {
			prixMax = prix1;
		}
		else if (prix2 > 0) {
			prixMax = prix2;
		}
		
		if(contrat.getPrixALaTonne() <= prixMax) {
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
		} else {
			Feve feve = (Feve) (produit);
			this.acteur.getStock().ajoutFeves(feve, quantite, contrat.getPrixALaTonne());
		}
		this.acteur.getTresorier().jaiAchetePrincipale(quantite * contrat.getPrixALaTonne());
		this.contractActuel = contrat;
	}

	/**
	 * Decide et commence les négociations d'un nouveau contract cadre
	 * Est appellé par next() de l'acteur Transformateur3
	 */
	public void commencerNegociations() {
		//On attends avant d'initier des contrats cadres car les cours moyens calculés sont trop haut au début.
		if((contractActuel != null && contractActuel.getEcheancier().getStepFin() == Filiere.LA_FILIERE.getEtape() + 1) || Filiere.LA_FILIERE.getEtape() == 5){
			IVendeurContratCadre vendeur = (Transformateur2) Filiere.LA_FILIERE.getActeur("EQ4");
			Filiere.LA_FILIERE.getSuperviseurContratCadre().demande(acteur, vendeur, Pate.PATE_BASSE,
				new Echeancier(Filiere.LA_FILIERE.getEtape() + 1, Filiere.LA_FILIERE.getEtape() + this.defaultContractTime, quantiteParTourMax),
				this.acteur.getCryptogramme());
		}
	}
}