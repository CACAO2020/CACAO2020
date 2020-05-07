package abstraction.eq7Distributeur2;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.Feve;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;


public class Distributeur2 implements IActeur {
	
	private Integer cryptogramme;
	private Journal journal;
	
	public AcheteurCacaoCriee acheteurCacaoCriee;
	private AcheteurChocolatBourse acheteurChocolatBourse;
	private DistributeurChocolat distributeurChocolat;
	
	protected Stock stock;

	public Distributeur2() {
		this.journal = new Journal(this.getNom() + " activites", this);
		stock = new Stock();
		acheteurCacaoCriee = new AcheteurCacaoCriee(stock);
		acheteurChocolatBourse = new AcheteurChocolatBourse(stock);
		
	}

	public String getNom() {
		return "EQ7";
	}

	public String getDescription() {
		return "Ecocoa de Liège, chocolatier responsable";
	}

	public Color getColor() {
		return new Color(240, 195, 15);
	}

	public void initialiser() {
	}

	public void setCryptogramme(Integer crypto) {
		this.cryptogramme = crypto;
	}

	public void next() {
		System.out.println(this.stock.stocksChocolat.get(stock.stringToChoco("H")).getValeur());
		this.stock.stocksChocolat.get(Chocolat.CHOCOLAT_HAUTE).setValeur(this, this.stock.stocksChocolat.get(stock.stringToChoco("H")).getValeur() + 5.);
		System.out.println(this.stock.stocksChocolat.get(stock.stringToChoco("H")).getValeur());
		acheteurCacaoCriee.next();
	}

	public List<String> getNomsFilieresProposees() {
		return new ArrayList<String>();
	}

	public Filiere getFiliere(String nom) {
		return Filiere.LA_FILIERE;
	}

	public List<Variable> getIndicateurs() {
		return stock.getIndicateurs();
	}

	public List<Variable> getParametres() {
		List<Variable> res=new ArrayList<Variable>();
		return res;
	}

	public List<Journal> getJournaux() {
		List<Journal> res=new ArrayList<Journal>();
		res.add(journal);
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
