package abstraction.eq8Romu.ventesCacaoAleatoires;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;

public class ProducteurVentesAleatoires implements IActeur, IVendeurCacaoAleatoire {
	
	private Variable stockFeves;
	private Variable productionMax;
	private Variable prixMin;
	private Variable prixInteressant;
	@SuppressWarnings("unused")
	private Integer cryptogramme;
	private Journal journalPVA1;
	private String nom;
	private Color couleur;

	public ProducteurVentesAleatoires(String nom, Color couleur) {
		this.nom = nom;
		this.couleur = couleur;
		this.stockFeves=new Variable(getNom()+" stock feves", this, 0, 10000, 1000);
		this.productionMax=new Variable(getNom()+" production max par step", this, 0, 1000, 200);
		this.prixMin=new Variable(getNom()+" prix min", this, 0, 5.0, 1.8);
		this.prixInteressant=new Variable(getNom()+" prix interessant", this, 0, 12.0, 2.0);
		this.journalPVA1 = new Journal(this.getNom()+" activites", this);
	}
	
	public void setCryptogramme(Integer crypto) {
		this.cryptogramme = crypto;
	}
	
	public String getNom() {
		return this.nom;
	}

	public String getDescription() {
		return "Producteur Ventes Aleatoires "+this.getNom();
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

	public double quantiteEnVente(double prix) {
		if (prix>=this.prixInteressant.getValeur()) { // Met tout le stock en vente si le prix est interessant
            this.journalPVA1.ajouter("met en vente "+this.stockFeves.getValeur()+" au prix de "+prix);
			return this.stockFeves.getValeur();
		} else if (prix<this.prixMin.getValeur()) { // Ne met rien en vente si le prix est inferieur au prix minimal
            this.journalPVA1.ajouter("met en vente 0 au prix de "+prix);
			return 0;
		} else { // Met la moitie du stock en vente si le prix est correct (superieur au prix min mais inferieur au prix interessant)
            this.journalPVA1.ajouter("met en vente "+(this.stockFeves.getValeur()/2.0)+" au prix de "+prix);
			return this.stockFeves.getValeur()/2.0;
		}
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
		res.add(this.prixMin);
		res.add(this.prixInteressant);
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
}
