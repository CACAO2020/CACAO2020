package abstraction.eq7Distributeur2;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import abstraction.eq8Romu.cacaoCriee.IVendeurCacaoCriee;
import abstraction.eq8Romu.cacaoCriee.LotCacaoCriee;
import abstraction.eq8Romu.cacaoCriee.PropositionCriee;
import abstraction.eq8Romu.chocolatBourse.IVendeurChocolatBourse;
import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.Feve;
import abstraction.eq8Romu.ventesCacaoAleatoires.IVendeurCacaoAleatoire;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;

public class Producteur implements IActeur, IVendeurCacaoCriee {
	
	private Variable stockFeves;
	private Variable production;
	private Integer cryptogramme;
	private Journal journalPVA1;
	private Feve feve;

	public Producteur(Feve feve) {
		this.feve = feve;
		this.stockFeves=new Variable(getNom()+" stock fèves", this, 0);
		this.production=new Variable(getNom()+" production par step", this, 100, 500, 0);
		this.journalPVA1 = new Journal(this.getNom()+" activites", this);
	}
	
	public void setCryptogramme(Integer crypto) {
		this.cryptogramme = crypto;
	}
	
	public String getNom() {
		return "P " + this.feve.name();
	}

	public String getDescription() {
		return "Producteur"+this.getNom();
	}

	public Color getColor() {
		return new Color(128, 128, 255);
	}

	public void initialiser() {
	}

	public void next() {
		// production
		double production =  Math.random()*(this.production.getMax()-this.production.getMin())+this.production.getMin();
		this.production.setValeur(this, production);
		this.stockFeves.ajouter(this, production);
		this.journalPVA1.ajouter("Production de "+production);

	}

	public void notificationVente(double quantite, double prix) {
		this.stockFeves.retirer(this, quantite);// Le superviseur nous informe de la quantite qu'on vient de vendre --> on la retire du stock
        this.journalPVA1.ajouter("vente de "+quantite+" au prix de "+prix+" le stock devient "+this.stockFeves.getValeur());
	}

	public List<String> getNomsFilieresProposees() {
		return new ArrayList<String>();
	}

	public Filiere getFiliere(String nom) {
		return Filiere.LA_FILIERE;
	}

	public List<Variable> getIndicateurs() {
		List<Variable> res=new ArrayList<Variable>();
		res.add(this.stockFeves);
		return res;
	}

	public List<Variable> getParametres() {
		List<Variable> res=new ArrayList<Variable>();
		res.add(this.production);
		return res;
	}

	public List<Journal> getJournaux() {
		List<Journal> res=new ArrayList<Journal>();
		res.add(this.journalPVA1);
		return res;
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

	public double getOffre(Chocolat chocolat, double cours) {
		return this.stockFeves.getValeur();
	}

	public void livrer(Chocolat chocolat, double quantite) {
		stockFeves.retirer(this, quantite);
	}


	public LotCacaoCriee getLotEnVente() {
		if (this.stockFeves.getValeur()/2 <= LotCacaoCriee.QUANTITE_MIN) {
			return null;
		} else {
			LotCacaoCriee lot = new LotCacaoCriee(this, this.feve, 0.8*this.stockFeves.getValeur(), 1);
			return lot;
		}
	}

	public void notifierAucuneProposition(LotCacaoCriee lot) {
		
	}

	public PropositionCriee choisir(List<PropositionCriee> propositions) {
		if (propositions == null) {
			return null;
		} else {
			return propositions.get(0);
		}
	}

	public void notifierVente(PropositionCriee proposition) {
		this.stockFeves.retirer(proposition.getAcheteur(), proposition.getQuantiteEnTonnes());
	}
}

