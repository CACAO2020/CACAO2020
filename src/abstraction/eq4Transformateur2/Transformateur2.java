package abstraction.eq4Transformateur2;

import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import abstraction.eq8Romu.chocolatBourse.IVendeurChocolatBourse;
import abstraction.eq8Romu.produits.Chocolat;
import abstraction.fourni.Filiere;

public class Transformateur2 implements IActeur, IVendeurChocolatBourse {
	
	private Variable stockFeves;
	private Variable stockChocolat;
	private Integer cryptogramme;
	private Journal journalEq4;

	public Transformateur2() {
		this.stockFeves=new Variable(getNom()+" stock feves", this, 50);
		this.stockChocolat=new Variable(getNom()+" stock chocolat", this, 100);
		this.journalEq4 = new Journal("Eq4 activites", this);
	}
	
	public String getNom() {
		return "EQ4";
	}

	public String getDescription() {
		return "Transformateur bla bla bla";
	}

	public Color getColor() {
		return new Color(155, 89, 182);
	}

	public void initialiser() {
	}

	public void setCryptogramme(Integer crypto) {
		this.cryptogramme = crypto;
	}
	public void next() {
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
		res.add(this.stockChocolat);
		return res;
	}

	public List<Variable> getParametres() {
		List<Variable> res=new ArrayList<Variable>();
		return res;
	}

	public List<Journal> getJournaux() {
		List<Journal> res=new ArrayList<Journal>();
		res.add(this.journalEq4);
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

// Vente de chocololat
	public double getOffre(Chocolat chocolat, double cours) {
		if (cours >= this.getPrixMinVenteChoco()) {
			return stockChocolat.getValeur();
		}
		else {
			return 0;
		}
	}

	public void livrer(Chocolat chocolat, double quantite) {
		stockChocolat.retirer(this, quantite);
	}

/* Fonction qui donnera le prix minimum pour qu'on veuille vendre notre chocolat
 * Pourra être implémentée une fois qu'on saura calculer le cout de production du chocolat
 */
	public double getPrixMinVenteChoco() {
		return 0;
	}
	
}
