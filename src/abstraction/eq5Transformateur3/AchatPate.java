package abstraction.eq5Transformateur3;

import java.util.ArrayList;
import java.util.List;

import abstraction.eq4Transformateur2.Transformateur2;
import abstraction.eq4Transformateur2.Transformateur2_contratCadre;
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
	
	private double prixMax;
    private Transformateur3 acteur;
	private double quantiteParTourMax;
	private int stepSinceLastContract;
	private int defaultContractTime;
		
    public AchatPate(Transformateur3 acteur, int defaultContractTime) {
		this.acteur = acteur;
		this.quantiteParTourMax = 200;
		this.stepSinceLastContract = 0;
		this.defaultContractTime = defaultContractTime;
    }
    public boolean valideOffre(ExemplaireContratCadre contrat, double quantiteParTourMax, int stepFin ) {
    	boolean flag = true;
    	for (int i = 0; i< 1+ stepFin; i++) {
    		if (contrat.getEcheancier().getQuantite(i) > quantiteParTourMax ) {
    			flag = false;
    		}
    	}
    	return flag;
    }
    
    public List<Double> offreAdaptee (ExemplaireContratCadre contrat, double quantiteParTourMax, int stepFin){
    	List<Double> offreAdaptee = new ArrayList<>(contrat.getEcheancier().getNbEcheances());
    	for (int i = 0; i< stepFin; i++) {
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
		if(stepDeb == stepDeb0 && stepFin < 10 && valideOffre(contrat, quantiteParTourMax, stepFin)) {
			return contrat.getEcheancier();
		}
		//sinon, on peut soit arreter les negociations, soit reproproposer

		//StepFin pas OK; Quantitées OK
		else if(stepFin>= 10 && valideOffre(contrat, quantiteParTourMax, 10)) {
			List<Double> quantites = new ArrayList<>();
			for (int i = 0; i<10; i++) {
				quantites.add(contrat.getEcheancier().getQuantite(i));
				}
			return new Echeancier(stepDeb0, quantites ) ;
			}
		//StepFin OK mais pas quantitées
		else if (stepFin< 10 && valideOffre(contrat, quantiteParTourMax, stepFin)==false) {
			return new Echeancier (stepDeb0, offreAdaptee (contrat, quantiteParTourMax, stepFin));
		}
		//StepFin pas OK et quantitées pas OK
		else if (stepFin >= 10 && valideOffre(contrat, quantiteParTourMax, 10)==false) {
			return new Echeancier (stepDeb0, offreAdaptee (contrat, quantiteParTourMax, 10));
		}
		// il reste juste le cas où step0 pas OK mais les deux autres OK
		else {
			List<Double> quantites = new ArrayList<>();
			for (int i = 0; i<stepFin; i++) {
				quantites.add(contrat.getEcheancier().getQuantite(i));
			}
			return new Echeancier(stepDeb0, quantites);
     }
}

	public double contrePropositionPrixAcheteur(ExemplaireContratCadre contrat) {
		//le prix max est celui qui nous permet de faire 40% de marge
		Pate pate =  (Pate)(contrat.getProduit());
		prixMax = 0.6*this.acteur.getInfoCoursVente().getMoy(Stock.getProduct(pate)) - 
				this.acteur.getStock().getTransformationCostPate().getValeur();
		if (0.6*this.acteur.getInfoCoursVente().getMoy(Stock.getProduct(pate)) - 
				this.acteur.getStock().getTransformationCostPate().getValeur() < 0) {
				prixMax = 0.9*this.acteur.getInfoCoursVente().getMoy(Stock.getProduct(pate)) - 
					this.acteur.getStock().getTransformationCostPate().getValeur();
			} else if( 0.9*this.acteur.getInfoCoursVente().getMoy(Stock.getProduct(pate)) - 
			this.acteur.getStock().getTransformationCostPate().getValeur() < 0 ){
				return 0;
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
		this.stepSinceLastContract = Math.min(stepSinceLastContract,
				contrat.getEcheancier().getStepFin() - Filiere.LA_FILIERE.getEtape());
	}

	/**
	 * Decide et commence les négociations d'un nouveau contract cadre
	 * Est appellé par next() de l'acteur Transformateur3
	 */
	public void commencerNegociations() {
		if(this.stepSinceLastContract <= 0 ){
			IVendeurContratCadre vendeur = (Transformateur2) Filiere.LA_FILIERE.getActeur("EQ4");
			Filiere.LA_FILIERE.getSuperviseurContratCadre().demande(acteur, vendeur, Pate.PATE_BASSE,
				new Echeancier(Filiere.LA_FILIERE.getEtape() + 1, Filiere.LA_FILIERE.getEtape() + this.defaultContractTime, quantiteParTourMax),
				this.acteur.getCryptogramme());
		}
	}
}