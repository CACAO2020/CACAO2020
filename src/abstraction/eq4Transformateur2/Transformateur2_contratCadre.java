package abstraction.eq4Transformateur2;

import java.util.LinkedList;
import java.util.List;

import abstraction.eq8Romu.contratsCadres.Echeancier;
import abstraction.eq8Romu.contratsCadres.ExemplaireContratCadre;
import abstraction.eq8Romu.contratsCadres.IAcheteurContratCadre;
import abstraction.eq8Romu.contratsCadres.IVendeurContratCadre;
import abstraction.eq8Romu.produits.Feve;
import abstraction.eq8Romu.produits.Pate;
import abstraction.eq8Romu.produits.Chocolat;

public class Transformateur2_contratCadre extends Transformateur2_negoce implements IVendeurContratCadre {
	
	// NOTES : ne marche que si on a qu'un seul contrat à la fois, sinon les quantités max vont être largement
	// dépassées
	
	protected List<ExemplaireContratCadre> mesContratEnTantQueVendeur;
	
	private double margePate ;
	private double facteurQuantiteLimite ;
	private double stockPateMin ;
	
	public Transformateur2_contratCadre() {
		super();
		this.mesContratEnTantQueVendeur=new LinkedList<ExemplaireContratCadre>();
		this.margePate = 1.3 ; // marge sur le prix de revente de la pâte
		this.facteurQuantiteLimite = 0.8 ; // pourcentage de la capacité de production à utiliser sur les contrats cadres
		this.stockPateMin = 0 ; // quantité de stock minimale pour pouvoir envisager de vendre la pâte
	}
	
	// permet d'obtenir la pate de type PateInterne correspondant au produit mis en argument
	// retourne null si ça ne fait pas partie des deux types de pates vendables
	
	public PateInterne conversionPate (Object produit) {
		if (produit != null) {
			if (produit == Pate.PATE_BASSE) {
				return PateInterne.PATE_BASSE ;
			}
			else {
				if (produit == Pate.PATE_MOYENNE) {
					return PateInterne.PATE_MOYENNE ;
				}
				else { return null ; }
			}
		} else { return null ; }
	}
	
	// calcule l'écart relatif entre deux doubles (servira de norme pour mesurer la différence entre 
	// deux échéanciers en première approche)
	
	public double ecartRelatif (Echeancier e1, Echeancier e2) {
		double a = e1.getQuantiteTotale() ;
		double b = e2.getQuantiteTotale() ;
		if (b == 0) {
			return a ;
		}
		else {
			return Math.abs(a-b)/b ;
		}
	}
	
	// renvoie un échéancier, avec la capacité actuelle (ou prévue, par la suite) permettant d'assurer la 
	// quantité voulue par l'acheteur pour chaque étape
	// si parfait == true, retourne l'échéancier idéal pour notre production
	// on prend une marge et on fixe le seuil plus bas, à 80%, dans le cas d'une rupture d'approvisionnement
	// on prend une marge sur la première quantité, histoire d'assurer le coup
	
	public Echeancier echeancierLimite (Echeancier e, PateInterne pate, boolean parfait) {
		
		Echeancier echeancier = e ;
		double quantiteLimite = this.facteurQuantiteLimite*super.getCapaciteMaxTFEP() ;
		
		for (int i = e.getStepDebut() ; i < e.getNbEcheances() ; i++) {
			if (parfait || e.getQuantite(i)>quantiteLimite) {
				echeancier.set(i, quantiteLimite);
			}
		}
		if (super.getStockPateValeur(pate) - e.getQuantite(e.getStepDebut()) < this.stockPateMin) {
			echeancier.set(e.getStepDebut(), super.getStockPateValeur(pate) - this.stockPateMin) ;
		}
		
		return echeancier ;
	}
	
	// renvoie un échéancier dont les quantités sont la moyenne des quantités des deux échéanciers en argument
	
	public Echeancier echeancierMoyen (Echeancier e1, Echeancier e2) {
			
			Echeancier e = e1 ;
			for (int i = e.getStepDebut() ; i < e.getNbEcheances() ; i++) {
				e.set(i, (e1.getQuantite(i)+e2.getQuantite(i))/2);
			}
			return e ;
		}

	public Echeancier contrePropositionDuVendeur(ExemplaireContratCadre contrat) {
		
		PateInterne pate = this.conversionPate(contrat.getProduit()) ;
		Echeancier echeancier = contrat.getEcheancier() ;
		List<Echeancier> liste = contrat.getEcheanciers() ;
		
		if (!this.vend(pate)) { return null ; } // on ne vend pas cette pâte 
		
		if (liste.size()<2) { //aucune proposition n'a été faite, on envoie notre échéancier idéal
			 return this.echeancierLimite(echeancier, pate, true) ;
		}
		else {
			Echeancier echeancierPrecedent = liste.get(liste.size()-2) ;
			double norme = this.ecartRelatif(echeancier,echeancierPrecedent) ;
			
			//on accepte après trop d'itérations, on ajustera notre production en conséquence mais on garde 
			// un écart relatif correct pour pas avoir de valeur aberrante si la demande est trop grande
			if (liste.size()>30) { 
				if (norme < 0.3) { 
					return echeancier ;
				}
				else { return null ;}
			}
			// si l'écart relatif est suffisamment faible, on regarde si la répartition respecte nos limitations
			if (norme < 0.1) {
				Echeancier e = this.echeancierLimite(echeancier, pate, false) ;
				if (this.ecartRelatif(echeancier, e) < 0.1) {
					return echeancier ;
				} }
			// sinon, on envoie l'échéancier moyen entre notre précédent échéancier et celui qu'on reçoit, et on
			// le corrige de manière à respecter nos contraintes de production
			Echeancier e = this.echeancierMoyen(echeancier, echeancierPrecedent) ;
			return this.echeancierLimite(e, pate, false) ;
			}
	}
	
	// proposition iniatiale pour le prix, pour l'instant simple marge par rapport au coût de production 
	// il faudrait diminuer la marge en fonction de la quantité, que ça soit plus rentable d'acheter en gros
	
	public double propositionPrix(ExemplaireContratCadre contrat) {
		PateInterne pate = this.conversionPate(contrat.getProduit()) ;
		if (contrat.getQuantiteTotale() == 0) { throw new IllegalArgumentException("quantité totale du contrat nulle") ;}
		else {return this.margePate*super.getCoutProdPate(pate) ;} 
			
				//5000.0 + (1000.0/contrat.getQuantiteTotale());}// plus la quantite est elevee, plus le prix est interessant
	}

	// la contre-proposition fait la moyenne de notre dernière proposition et de celle qui arrive, sauf si celle-ci
	// est acceptable au vu de notre marge
	
	public double contrePropositionPrixVendeur(ExemplaireContratCadre contrat) {
		double prixVoulu = 0 ;
		PateInterne pate = this.conversionPate(contrat.getProduit()) ;
		List<Double> liste = contrat.getListePrixALaTonne() ;
		int n = liste.size() ;
		
		if (contrat.getPrixALaTonne() >= propositionPrix(contrat)) { // si le contrat est plus intéressant que notre contrat maximal, on accepte
			return contrat.getPrixALaTonne() ;
		}
		
		if (liste.size() >= 30) { // s'il y a eu trop d'itérations, on refuse le prix de l'acheteur
			return 0 ;
		}
		
		if (liste.size() >= 2) { //on prend notre prix précédent et on fait la moyenne avec le prix de l'acheteur s'il excède une marge minimum
			prixVoulu = (liste.get(n-2) + liste.get(n-1))/2 ;
			double ecartRelatif = Math.abs(liste.get(n-2) - liste.get(n-1))/liste.get(n-1) ;
			
			if (prixVoulu < 1.1*super.getCoutProdPate(pate)) { //marge minimum à 10%
				return 0 ;
			} else { 
				if (ecartRelatif < 0.1) { // si l'écart relatif est inférieur à 10% (arbitraire), on ne chipote plus
					return contrat.getPrixALaTonne() ;
			}
			else { return prixVoulu ; }} // on propose notre prix
		}
		else { // on a jamais proposé de prix
			return propositionPrix(contrat) ;
		}
	}
	
	//

	public void notificationNouveauContratCadre(ExemplaireContratCadre contrat) {
		this.mesContratEnTantQueVendeur.add(contrat);
	}
	
	public void next() {
		List<ExemplaireContratCadre> contratsObsoletes=new LinkedList<ExemplaireContratCadre>();
		for (ExemplaireContratCadre contrat : this.mesContratEnTantQueVendeur) {
			if (contrat.getQuantiteRestantALivrer()==0.0 && contrat.getMontantRestantARegler()==0.0) {
				contratsObsoletes.add(contrat);
			}
		}
		this.mesContratEnTantQueVendeur.removeAll(contratsObsoletes);
	}
	
	// renvoie true si on vend le produit : si on a pas un stock minimal, on ne vend pas (ou alors on permet
	// d'avoir un contrat avec une première année à vide si on arrive à le négocier ?)
	
	public boolean vend(Object produit) {
		PateInterne pate = this.conversionPate(produit) ;
		if (pate != null) {
			return super.getStockPateValeur(pate) > this.stockPateMin ; // quantité de stock minimale
		}
		else { return false ; }
	}
	
	// met à jour le stock et renvoie la quantité livrée effectivement

	public double livrer(Object produit, double quantite, ExemplaireContratCadre contrat) {
		
		PateInterne pate = this.conversionPate(contrat.getProduit()) ;
		double livre = Math.min(super.getStockPateValeur(pate), quantite);
		if (livre>0.0) {
			super.setStockPateValeur(pate, super.getStockPateValeur(pate)-livre);
		}
		return livre;
	}

}
