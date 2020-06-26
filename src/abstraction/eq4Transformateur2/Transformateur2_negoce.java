package abstraction.eq4Transformateur2;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import abstraction.eq8Romu.cacaoCriee.IAcheteurCacaoCriee;
import abstraction.eq8Romu.cacaoCriee.LotCacaoCriee;
import abstraction.eq8Romu.cacaoCriee.PropositionCriee;
import abstraction.eq8Romu.cacaoCriee.SuperviseurCacaoCriee;
import abstraction.eq8Romu.chocolatBourse.IVendeurChocolatBourse;
import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.Feve;
import abstraction.fourni.Filiere;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;

public class Transformateur2_negoce extends Transformateur2_gestion_stocks implements IAcheteurCacaoCriee, IVendeurChocolatBourse {
	protected Map<PateInterne, Variable> MARGE_VISEE_PATE;
	protected Map<Chocolat, Variable> MARGE_VISEE_CHOCOLAT;

	
	public Transformateur2_negoce() { 
		super();   
		 
		this.MARGE_VISEE_PATE = new HashMap<PateInterne, Variable>() ;
		this.MARGE_VISEE_PATE.put(PateInterne.PATE_BASSE, new Variable(getNom()+" marge visee pate basse", this, 0.30)) ;
		this.MARGE_VISEE_PATE.put(PateInterne.PATE_MOYENNE, new Variable(getNom()+" marge visee pate moyenne", this, 0.30)) ;
		this.MARGE_VISEE_PATE.put(PateInterne.PATE_MOYENNE_EQUITABLE, new Variable(getNom()+" marge visee pate moyenne equitable", this, 1)) ;
		this.MARGE_VISEE_PATE.put(PateInterne.PATE_HAUTE_EQUITABLE,new Variable(getNom()+" marge visee pate haute equitable", this, 1)) ;
		this.MARGE_VISEE_PATE.put(PateInterne.PATE_HAUTE, new Variable(getNom()+" marge visee pate haute", this, 1)) ;
		
		this.MARGE_VISEE_CHOCOLAT = new HashMap<Chocolat, Variable>() ;
		this.MARGE_VISEE_CHOCOLAT.put(Chocolat.CHOCOLAT_BASSE, new Variable(getNom()+" marge visee chocolat basse", this, 1.5*MARGE_VISEE_PATE.get(PateInterne.PATE_BASSE).getValeur())) ;
		this.MARGE_VISEE_CHOCOLAT.put(Chocolat.CHOCOLAT_MOYENNE, new Variable(getNom()+" marge visee chocolat moyenne", this, 1.5*MARGE_VISEE_PATE.get(PateInterne.PATE_MOYENNE).getValeur())) ;
		this.MARGE_VISEE_CHOCOLAT.put(Chocolat.CHOCOLAT_MOYENNE_EQUITABLE, new Variable(getNom()+" marge visee chocolat moyenne equitable", this, 1)) ;
		this.MARGE_VISEE_CHOCOLAT.put(Chocolat.CHOCOLAT_HAUTE,new Variable(getNom()+" marge visee chocolat haute", this, 1)) ;
		this.MARGE_VISEE_CHOCOLAT.put(Chocolat.CHOCOLAT_HAUTE_EQUITABLE, new Variable(getNom()+" marge visee chocolat haute equitable", this, 1)) ;
	}
	
	// récupère les attributs notés comme paramètres, utile pour les tests et sûrement appelé par des fonctions externes

	public List<Variable> getIndicateurs() { 
		List<Variable> res=super.getIndicateurs();
		for (PateInterne pate : PateInterne.values()) {
			res.add(MARGE_VISEE_PATE.get(pate));
		}		
		for (Chocolat choc : Chocolat.values()) {
			res.add(MARGE_VISEE_CHOCOLAT.get(choc));
		}
		return res;
	}

	// récupère les attributs notés comme paramètres, utile pour les tests et sûrement appelé par des fonctions externes
	
	public List<Variable> getParametres() { //idem
		List<Variable> res=super.getParametres();
		return res;
	}


/* ACHAT FEVES */

	//pour l'instant il essaie d'acheter tout ce qui passe mais à l'avenir on aura un if lot.quantiteentonne <= quantité_dont_on_a_besoin  
	public double proposerAchat(LotCacaoCriee lot) {
		double prix = this.prixRentableAchatFeve(lot.getFeve());
		double total = lot.getQuantiteEnTonnes()*prix;
		this.journalEq4.ajouter("Propose" + prix + "pour un lot de" + lot.getFeve().name() + "pour un total de" + total);
		if (super.getSolde()*0.5>total) { // ON ACHETE QUE SI LE VALEUR DU LOT EST < 50% DE NOTRE SOLDE (PEUT ETRE MODIFIE
			return prix;
		}
		else {
			return 0;
		}
	}

	public void notifierPropositionRefusee(PropositionCriee proposition) {
		this.journalEq4.ajouter("Apprend que sa proposition de "+Journal.doubleSur(proposition.getPrixPourUneTonne(), 4)+" pour "+Journal.texteColore(proposition.getVendeur(), Journal.doubleSur(proposition.getQuantiteEnTonnes(), 2)+" tonnes de "+proposition.getFeve().name())+Journal.texteColore(Color.red, Color.white, " a ete refusee"));
		this.setMargeVisee(super.creerPateAPartirDeFeve(proposition.getFeve()));
	}

	public Integer getCryptogramme(SuperviseurCacaoCriee superviseur) {
		if (superviseur!=null) { // Personne ne peut creer un second SuperviseurCacaoCriee --> il s'agit bien de l'unique superviseur et on peut lui faire confiance
			return cryptogramme;
		}
		return Integer.valueOf(0);
	}
	
	// une fois que l'offre est acceptée, notifie la vente, met à jour les stocks et leur valeur
	public void notifierVente(PropositionCriee proposition) {
		Feve feve = proposition.getFeve() ;
		super.modifierCoutMoyenFeves(feve, proposition.getQuantiteEnTonnes(), proposition.getPrixPourUneTonne());
		super.setStockFevesValeur(feve, proposition.getQuantiteEnTonnes()+super.getStockFevesValeur(feve));
		this.setMargeVisee(super.creerPateAPartirDeFeve(proposition.getFeve()));
		this.journalEq4.ajouter("Apprend que sa proposition de "+Journal.doubleSur(proposition.getPrixPourUneTonne(), 4)+" pour "+Journal.texteColore(proposition.getVendeur(), Journal.doubleSur(proposition.getQuantiteEnTonnes(), 2)+" tonnes de "+proposition.getFeve().name())+Journal.texteColore(Color.green, Color.black," a ete acceptee"));
		this.journalEq4.ajouter("--> le stock de feve passe a "+Journal.doubleSur(this.stockFeves.get(proposition.getFeve()).getValeur(), 4));
		//System.out.println("ACHETE A : " + proposition.getPrixPourUneTonne());
		//FAIRE AUGMENTER LA MARGE VISEE
	}


	/* CALCUL DES COUTS DE PRODUCTION */
	// à effectuer avant de réaliser les transformations, pour déterminer quelle quantité il faut transformer effectivement
	
	//Renvoie le cout de production d'une tonne de pate 
	// N'EST PLUS UTILISE
	public double getCoutProdPate(PateInterne pate, double quantiteTotaleTransfo) {
		Feve feve = super.creerFeve(pate) ;
		return super.prixApresTFEP(feve, quantiteTotaleTransfo/super.getCoeffTFEP());
	}
	
	//Renvoie le cout de production d'une tonne de chocolat 
	// N'EST PLUS UTILISE
	public double getCoutProdChocolat(Chocolat chocolat, double quantiteTotaleTransfo) {
		PateInterne pate = super.creerPateAPartirDeChocolat(chocolat) ;
		return super.prixApresTPEC(pate, quantiteTotaleTransfo/super.getCoeffTPEC()) ;
	} 

	
	/* Calcule si un prix d'achat des fèves est rentable en fonction des prix de reventes du marché */
	public double prixRentablePourReventeChocolat(Feve feve) {
		if (Filiere.LA_FILIERE.getEtape()>1) {
		double cout_process_product = this.getCoutMoyenChocolatValeur(super.creerChocolat(super.creerPateAPartirDeFeve(feve))) - this.getCoutMoyenFeveValeur(feve)*super.getCoeffTFEP()*super.getCoeffTPEC();
		String indicateur = "BourseChoco cours ";
		if (feve == Feve.FEVE_MOYENNE_EQUITABLE) {
			indicateur += "CHOCOLAT_MOYENNE_EQUITABLE" ;
		}
		else if (feve == Feve.FEVE_HAUTE) {
			indicateur += "CHOCOLAT_HAUTE" ;
		}
		else if (feve == Feve.FEVE_HAUTE_EQUITABLE) {
			indicateur += "CHOCOLAT_HAUTE_EQUITABLE" ;
		}
		else {
			return 0;
		}
		double prix_bourse_choco = Filiere.LA_FILIERE.getIndicateur(indicateur).getHistorique().get(Filiere.LA_FILIERE.getEtape()-1).getValeur();
		return prix_bourse_choco/(1+MARGE_VISEE_CHOCOLAT.get(super.creerChocolat(super.creerPateAPartirDeFeve(feve))).getValeur()) - cout_process_product;
	}
		else {
			return 0;
		}
	}
	
	//AU DEBUT DE LA SIMUL CEST NEGATIF ->> A EVITER
	public double prixRentablePourReventePate(Feve feve) {
		if (super.nbToursAutonomiePateEtFeves(super.creerPateAPartirDeFeve(feve)) >= super.getNombreDeTourDautoMax()) {
			return 0;
		}
		else {
		double cout_process_product = this.getCoutMoyenPateValeur(super.creerPateAPartirDeFeve(feve)) - this.getCoutMoyenFeveValeur(feve)*super.getCoeffTFEP();
		double prix_moy_revente_pate = super.getPrixMoyReventePate(super.creerPateAPartirDeFeve(feve));
		return prix_moy_revente_pate*(1-MARGE_VISEE_PATE.get(super.creerPateAPartirDeFeve(feve)).getValeur()) - cout_process_product;
		}
	}
	
	public double prixRentableAchatFeve(Feve feve ) {
		double prix = ((feve == Feve.FEVE_BASSE||feve == Feve.FEVE_MOYENNE) ? this.prixRentablePourReventePate(feve) : this.prixRentablePourReventeChocolat(feve));
		return prix;
	}
	
	
	/* VENTE CHOCOLAT */
	// On gagne 50% de la marge visee pour vend moitié de notre stock, si on vend + de 100% de la marge visee on vend tout
		public double getOffre(Chocolat chocolat, double cours) {
			double marge = this.margeChoc();
			double quantite = this.getStockChocolatValeur(chocolat) ;
			double cout_prod = this.getCoutMoyenChocolatValeur(chocolat);
			double prix_semi_ideal = cout_prod*(1+marge*0.5);
			double prix_ideal = cout_prod*(1+marge);
			if (cours >= prix_ideal) {
				return quantite ;
			}
			else if (cours >= prix_semi_ideal) {
				return quantite/2;
			}
			else {
				this.MARGE_VISEE_CHOCOLAT.get(chocolat).setValeur(this, Math.max(0.1, marge-0.1)); 
				return 0;
			}
		}

		public void livrer(Chocolat chocolat, double quantite) {
			double valeur = this.getStockChocolatValeur(chocolat) - quantite ;
			if (valeur >= 0) {
				this.setStockChocolatValeur(chocolat, valeur);
				double marge = this.MARGE_VISEE_CHOCOLAT.get(chocolat).getValeur();
				this.MARGE_VISEE_CHOCOLAT.get(chocolat).setValeur(this, Math.min(2, marge+0.1)); 
			}
			else {throw new IllegalArgumentException("stock insuffisant");}
		}
		
		// une fois que la vente est acceptée, notifie la vente, met à jour les stocks
		// fonction à faire
		
		/* ADAPTATION DE LA MARGE */ 
		public void setMargeVisee(PateInterne pate) {
			int nbTourAuto = super.nbToursAutonomiePateEtFeves(pate);
			if (nbTourAuto != 1000) {
				double marge = Math.max(Math.min(nbTourAuto-1-super.getNombreDeTourDautoMin()/super.getNombreDeTourDautoMax(), 2), 0.1);
				MARGE_VISEE_PATE.get(pate).setValeur(this, marge);
			}
		}
		
		public double margeChoc() {
			double rapport = super.coutStocksChoc()/super.getSolde();
			double visee = 0.01/rapport;								//Stock coute 2% de notre solde par tour -> marge esperee de 50%, 1% -> 100%, 5% -> 20%, 10% -> 10%
			if (rapport < 0.005) { 								//visee capee a 200%
				visee = 1;
			}
			if (rapport > 0.2) {
				visee = -10;
			}
			return visee;
		}
		
		
}
