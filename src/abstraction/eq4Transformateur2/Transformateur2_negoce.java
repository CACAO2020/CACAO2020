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
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;

public class Transformateur2_negoce extends Transformateur2_contratCadre implements IAcheteurCacaoCriee, IVendeurChocolatBourse {
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
	
	// récupère les attributs notés comme paramètres, utile pour les tests et sûrement appelé par des fonctions externes

	public List<Variable> getIndicateurs() { 
		List<Variable> res=super.getIndicateurs();
		for (Feve feve :Feve.values()) {
			res.add(this.prixMaxAchatFeves.get(feve)) ;
		}
		for (Chocolat chocolat : Chocolat.values()) {
			res.add(this.prixMinVenteChocolat.get(chocolat)) ;
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
		Class classeAppelante = null;
        try { 
            Exception e = new Exception();
            String name = ((e.getStackTrace())[1]).getClassName();
            classeAppelante = Class.forName( name );
        } catch(Exception e2) {
            classeAppelante = null; 
        }
if (!classeAppelante.equals(SuperviseurCacaoCriee.class)) { throw new Error("la concurrence tente de nous arnaquer"); }
		if (super.getSolde()*0.5>lot.getQuantiteEnTonnes()*this.prixMaxAchatFeves.get(lot.getFeve()).getValeur()) { // ON ACHETE QUE SI LE VALEUR DU LOT EST < 50% DE NOTRE SOLDE (PEUT ETRE MODIFIE
			return this.prixMaxAchatFeves.get(lot.getFeve()).getValeur();
		}
		else {
			return 3;
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
	
	// une fois que l'offre est acceptée, notifie la vente, met à jour les stocks et leur valeur
	public void notifierVente(PropositionCriee proposition) {
		Feve feve = proposition.getFeve() ;
		super.modifierCoutMoyenFeves(feve, proposition.getQuantiteEnTonnes(), proposition.getPrixPourLeLot());
		super.setStockFevesValeur(feve, proposition.getQuantiteEnTonnes()+super.getStockFevesValeur(feve));
		this.journalEq4.ajouter("Apprend que sa proposition de "+Journal.doubleSur(proposition.getPrixPourUneTonne(), 4)+" pour "+Journal.texteColore(proposition.getVendeur(), Journal.doubleSur(proposition.getQuantiteEnTonnes(), 2)+" tonnes de "+proposition.getFeve().name())+Journal.texteColore(Color.green, Color.black," a ete acceptee"));
		this.journalEq4.ajouter("--> le stock de feve passe a "+Journal.doubleSur(this.stockFeves.get(proposition.getFeve()).getValeur(), 4));
		
	}

	
	/* CALCUL DES COUTS DE PRODUCTION */
	// à effectuer avant de réaliser les transformations, pour déterminer quelle quantité il faut transformer effectivement
	
	//Renvoie le cout de production d'une tonne de pate
	public double getCoutProdPate(PateInterne pate, double quantiteTransfo) {
		Feve feve = super.creerFeve(pate) ;
		return super.prixApresTFEP(feve, quantiteTransfo/super.getCoeffTFEP());
	}
	
	//Renvoie le cout de production d'une tonne de chocolat 
	public double getCoutProdChocolat(Chocolat chocolat, double quantiteTransfo) {
		PateInterne pate = super.creerPateAPartirDeChocolat(chocolat) ;
		return super.prixApresTPEC(pate, quantiteTransfo/super.getCoeffTPEC()) ;
	}
	
	/* VENTE CHOCOLAT */
	// On vend tout notre stock de chocolat à chaque fois * A MODIFIER POUR CHOISIR QTE A VENDRE *
		public double getOffre(Chocolat chocolat, double cours) {
			double quantite = this.getStockChocolatValeur(chocolat) ;
			if (cours >= this.getCoutProdChocolat(chocolat, quantite)*1.2) { //J'AI CHOISI UNE MARGE ABITRAIRE DE 20%, DEVRA VARIER EN FONCTION DU STOCK
				return quantite ;
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
		
		// une fois que la vente est acceptée, notifie la vente, met à jour les stocks
		// fonction à faire
		
}
