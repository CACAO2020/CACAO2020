package abstraction.eq5Transformateur3;

import java.util.ArrayList;
import java.util.List;

import abstraction.eq2Producteur2.Producteur2;
import abstraction.eq8Romu.contratsCadres.Echeancier;
import abstraction.eq8Romu.contratsCadres.ExemplaireContratCadre;
import abstraction.eq8Romu.contratsCadres.IVendeurContratCadre;
import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.Pate;
import abstraction.fourni.Filiere;

/**@Eva DUPUY et @Simon MORO*/
public class AchatPate {
    /**
     * Gère les achats de pates avec le contrat cadre et négocie les prix.
     * Elle est agrégé dans la classe Transformateur3.
     */
    //TODO ajouter les methodes pour acheter de la pate de cacao
	
	private double prixMax;
    private Transformateur3 acteur;
    private double quantiteParTourMax;
//la quantité max reste à calculer!!!
		
    public AchatPate(Transformateur3 acteur) {
        this.acteur = acteur;
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
    	//List<double> offreAdaptee = Collections.emptyList();
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
		if(stepDeb == stepDeb0 && stepFin<10 && valideOffre(contrat, quantiteParTourMax, stepFin)) {
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
		//IVendeurContratCadre vendeur = (Transformateur4) Filiere.LA_FILIERE.getActeur("EQ4");
		//Filiere.LA_FILIERE.getSuperviseurContratCadre().demande(acteur, vendeur, Pate.PATE_BASSE, new Echeancier(), this.acteur.getCryptogramme());
	}
}