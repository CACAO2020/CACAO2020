package abstraction.eq7Distributeur2;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import abstraction.eq8Romu.chocolatBourse.IVendeurChocolatBourse;
import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.ventesCacaoAleatoires.IVendeurCacaoAleatoire;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;

public class Producteur implements IActeur, IVendeurChocolatBourse {
	
	private Variable stockFeves;
	private Variable productionMax;
	private Integer cryptogramme;
	private Journal journalPVA1;
	private String nom;
	private Color couleur;

	public Producteur(String nom, Color couleur) {
		this.nom = nom;
		this.couleur = couleur;
		this.stockFeves=new Variable(getNom()+" stock feves", this, 0, 10000, 1000);
		this.productionMax=new Variable(getNom()+" production max par step", this, 0, 1000, 200);
		this.journalPVA1 = new Journal(this.getNom()+" activites", this);
	}
	
	public void setCryptogramme(Integer crypto) {
		this.cryptogramme = crypto;
	}
	
	public String getNom() {
		return this.nom;
	}

	public String getDescription() {
		return "Producteur"+this.getNom();
	}

	public Color getColor() {
		return this.couleur;
	}

	public void initialiser() {
	}

	public void next() {
		// production
		double production =  Math.random()*this.productionMax.getValeur();
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
		return null;
	}

	public List<Variable> getIndicateurs() {
		List<Variable> res=new ArrayList<Variable>();
		res.add(this.stockFeves);
		return res;
	}

	public List<Variable> getParametres() {
		List<Variable> res=new ArrayList<Variable>();
		res.add(this.productionMax);
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

	@Override
	public double getOffre(Chocolat chocolat, double cours) {
		return this.stockFeves.getValeur();
	}

	@Override
	public void livrer(Chocolat chocolat, double quantite) {
		stockFeves.retirer(this, quantite);
		
	}
}

