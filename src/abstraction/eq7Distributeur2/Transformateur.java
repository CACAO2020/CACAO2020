package abstraction.eq7Distributeur2;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import abstraction.eq8Romu.cacaoCriee.IAcheteurCacaoCriee;
import abstraction.eq8Romu.cacaoCriee.LotCacaoCriee;
import abstraction.eq8Romu.cacaoCriee.PropositionCriee;
import abstraction.eq8Romu.cacaoCriee.SuperviseurCacaoCriee;
import abstraction.eq8Romu.chocolatBourse.IAcheteurChocolatBourse;
import abstraction.eq8Romu.chocolatBourse.IVendeurChocolatBourse;
import abstraction.eq8Romu.chocolatBourse.SuperviseurChocolatBourse;
import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.ChocolatDeMarque;
import abstraction.fourni.Banque;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;

public class Transformateur implements IAcheteurCacaoCriee, IVendeurChocolatBourse {

	private Variable stockFeves;
	private Variable stockChocolat;
	private Integer cryptogramme;
	private Banque laBanque; 
	private Chocolat choco;
	
	public Transformateur(Chocolat choco) {
		this.choco = choco;
		this.stockFeves = new Variable("Stock fèves " + getNom(), this, 50);
		this.stockChocolat = new Variable("Stock chocolat " + getNom(), this, 0);
	}
	
	public String getNom() {
		return "T " + this.choco.name();
	}

	public String getDescription() {
		return "Transformateur"+this.getNom();
	}

	public Color getColor() {
		return new Color(128, 128, 255);
	}

	public void initialiser() {
		this.laBanque = Filiere.LA_FILIERE.getBanque();
		
	}

	public void next() {
		double quantiteTransformee = Math.random()*Math.min(100, this.stockFeves.getValeur()); // on suppose qu'on a un stock infini de sucre
		this.stockFeves.retirer(this, quantiteTransformee);
		this.stockChocolat.ajouter(this, 2*quantiteTransformee);// 50% cacao, 50% sucre
		if (quantiteTransformee != 0) {
			this.laBanque.virer(this, cryptogramme, this.laBanque, quantiteTransformee*1.0234); // sucre, main d'oeuvre, autres frais
		}
	}

	public List<String> getNomsFilieresProposees() {
		return new ArrayList<String>();
	}

	public Filiere getFiliere(String nom) {
		return Filiere.LA_FILIERE;
	}

	public List<Variable> getIndicateurs() {
		List<Variable> res=new ArrayList<Variable>();
		res.add(stockFeves);
		res.add(stockChocolat);
		return res;
	}

	public List<Variable> getParametres() {
		List<Variable> res=new ArrayList<Variable>();
		return res;
	}

	public List<Journal> getJournaux() {
		List<Journal> res=new ArrayList<Journal>();
		return res;
	}

	public void setCryptogramme(Integer crypto) {
		this.cryptogramme = crypto;
		
	}

	public void notificationFaillite(IActeur acteur) {
		if (this==acteur) {
			System.out.println("I'll be back... or not... "+this.getNom());
			} else {
				System.out.println("Poor "+acteur.getNom()+"... We will miss you. "+this.getNom());
			}
	}

	public void notificationOperationBancaire(double montant) {
		
	}

	public double getDemande(Chocolat chocolat, double cours) {
		double solde = Filiere.LA_FILIERE.getBanque().getSolde(this,  cryptogramme);
		double max = solde/cours;
		//return 500; // si il retourne 500 sans verifier si il peut se le permettre il va parvenir a un impaye qu'il devra regler pour pouvoir a nouveau acheter a la bourse
		return Math.random()*max;// les cours vont s'effondrer car les acheteurs vont tres vite ne plus avoir assez d'argent pour acheter. Augmentez le solde des acheteurs via l'interface si vous voulez voir les cours repartir à la hausse
	}

	public Integer getCryptogramme(SuperviseurChocolatBourse superviseur) {
		if (superviseur!=null) {
			return cryptogramme;
		}
		return Integer.valueOf(0);
	}

	public void notifierCommande(Chocolat chocolat, double quantiteObtenue, boolean payee) {
		
	}

	public double getOffre(Chocolat chocolat, double cours) {
		if (chocolat==choco) {
			return this.stockChocolat.getValeur();
		}
		else {
			return 0.0;
		}
	}

	public void livrer(Chocolat chocolat, double quantite) {
		stockChocolat.retirer(this, quantite);
	}


	public double proposerAchat(LotCacaoCriee lot) {
		return lot.getPrixMinPourUneTonne()*lot.getQuantiteEnTonnes();
	}


	public void notifierPropositionRefusee(PropositionCriee proposition) {
		
	}


	public Integer getCryptogramme(SuperviseurCacaoCriee superviseur) {
		return this.cryptogramme;
	}

	public void notifierVente(PropositionCriee proposition) {
		this.stockFeves.ajouter(proposition.getVendeur(), proposition.getQuantiteEnTonnes());
	}

}
