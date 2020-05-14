package abstraction.eq4Transformateur2;

import java.awt.Color;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import abstraction.eq8Romu.cacaoCriee.IAcheteurCacaoCriee;
import abstraction.eq8Romu.cacaoCriee.LotCacaoCriee;
import abstraction.eq8Romu.cacaoCriee.PropositionCriee;
import abstraction.eq8Romu.cacaoCriee.SuperviseurCacaoCriee;
import abstraction.eq8Romu.chocolatBourse.IVendeurChocolatBourse;
import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.Feve;
import abstraction.eq8Romu.ventesCacaoAleatoires.IAcheteurCacaoAleatoire;
import abstraction.eq8Romu.ventesCacaoAleatoires.IVendeurCacaoAleatoire;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;

public class Transformateur2_negoce extends Transformateur2_e1 implements IAcheteurCacaoCriee, IVendeurChocolatBourse {
	private Map<Feve, Variable> prixMaxAchatFeves;
	private Map<Chocolat, Variable> prixMinVenteChocolat;
	
	public Transformateur2_negoce() {
		super();
		
		this.prixMaxAchatFeves = new HashMap<Feve, Variable>() ;
		this.prixMaxAchatFeves.put(Feve.FEVE_BASSE, new Variable(getNom()+" prix achat feves basses", this, 200)) ;
		this.prixMaxAchatFeves.put(Feve.FEVE_MOYENNE, new Variable(getNom()+" prix achat feves moyennes", this, 200)) ;
		this.prixMaxAchatFeves.put(Feve.FEVE_HAUTE, new Variable(getNom()+" prix achat feves hautes", this, 200)) ;
		this.prixMaxAchatFeves.put(Feve.FEVE_MOYENNE_EQUITABLE,new Variable(getNom()+" prix achat feves moyennes equitables", this, 200)) ;
		this.prixMaxAchatFeves.put(Feve.FEVE_HAUTE_EQUITABLE, new Variable(getNom()+" prix achat feves hautes equitables", this, 200)) ;
		
		this.prixMinVenteChocolat = new HashMap<Chocolat, Variable>() ;
		this.prixMinVenteChocolat.put(Chocolat.CHOCOLAT_BASSE, new Variable(getNom()+" prix min vente chocolat basse", this, 100)) ;
		this.prixMinVenteChocolat.put(Chocolat.CHOCOLAT_MOYENNE, new Variable(getNom()+" prix min vente chocolat moyenne", this, 100)) ;
		this.prixMinVenteChocolat.put(Chocolat.CHOCOLAT_HAUTE, new Variable(getNom()+" prix min vente chocolat haute", this, 0)) ;
		this.prixMinVenteChocolat.put(Chocolat.CHOCOLAT_MOYENNE_EQUITABLE, new Variable(getNom()+" prix min vente chocolat moyenne equitable", this, 100)) ;
		this.prixMinVenteChocolat.put(Chocolat.CHOCOLAT_HAUTE_EQUITABLE, new Variable(getNom()+" prix min vente chocolat haute equitable", this, 0)) ;
	}


/* ACHAT FEVES */

	//pour l'instant il essaie d'acheter tout ce qui passe mais à l'avenir on aura un if lot.quantiteentonne <= quantité_dont_on_a_besoin  
	public double proposerAchat(LotCacaoCriee lot) {
		double prix = 0;
		for (IActeur ac : Filiere.LA_FILIERE.getActeurs()) {
			if (ac instanceof IAcheteurCacaoCriee) {
				double prop = ((IAcheteurCacaoCriee) ac).proposerAchat(lot);
				if (prix<prop) {
					prix = prop;
				}
			}
		}
		if (prix<this.prixMaxAchatFeves.get(lot.getFeve()).getValeur()&&
				super.getSolde()>lot.getQuantiteEnTonnes()*prix) {
			return prix+1;
		}
		else {
			return 1;
		}
	}

	public void notifierPropositionRefusee(PropositionCriee proposition) {
		this.journalEq4.ajouter("Apprend que sa proposition de "+Journal.doubleSur(proposition.getPrixPourUneTonne(), 4)+" pour "+Journal.texteColore(proposition.getVendeur(), Journal.doubleSur(proposition.getQuantiteEnTonnes(), 2)+" tonnes de "+proposition.getFeve().name())+Journal.texteColore(Color.red, Color.white, " a ete refusee"));
	}

	public Integer getCryptogramme(SuperviseurCacaoCriee superviseur) {
		if (superviseur!=null) { // Personne ne peut creer un second SuperviseurCacaoCriee --> il s'agit bien de l'unique superviseur et on peut lui faire confiance
			return cryptogramme;
		}
		return Integer.valueOf(0);
	}

	public void notifierVente(PropositionCriee proposition) {
		this.setStockFevesValeur(proposition.getFeve(), this.getStockFevesValeur(proposition.getFeve())+proposition.getQuantiteEnTonnes());
		this.journalEq4.ajouter("Apprend que sa proposition de "+Journal.doubleSur(proposition.getPrixPourUneTonne(), 4)+" pour "+Journal.texteColore(proposition.getVendeur(), Journal.doubleSur(proposition.getQuantiteEnTonnes(), 2)+" tonnes de "+proposition.getFeve().name())+Journal.texteColore(Color.green, Color.black," a ete acceptee"));
		this.journalEq4.ajouter("--> le stock de feve passe a "+Journal.doubleSur(this.stockFeves.get(proposition.getFeve()).getValeur(), 4));
	}
	
	public double prixMaxAchatFeves() {
		return 1000000;
	}
	
	/* VENTE CHOCOLAT */
	// On vend tout notre stock de chocolat à chaque fois * A MODIFIER POUR CHOISIR QTE A VENDRE *
		public double getOffre(Chocolat chocolat, double cours) {
			if (cours >= this.getPrixMinVenteChoco()) {
				return this.getStockChocolatValeur(chocolat);
			}
			else {
				return 0;
			}
		}

		public void livrer(Chocolat chocolat, double quantite) {
			double valeur = this.getStockChocolatValeur(chocolat) - quantite ;
			if (valeur >= 0) {
				this.setStockChocolatValeur(chocolat, valeur);
			}
			else {throw new IllegalArgumentException("stock insuffisant");}
		}

	/* Fonction qui donnera le prix minimum pour qu'on veuille vendre notre chocolat
	 * Pourra être implémentée une fois qu'on saura calculer le cout de production du chocolat
	 */
		public double getPrixMinVenteChoco() {
			return 0;
		}
		
}
