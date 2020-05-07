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
	
	private static int NB_INSTANCES = 0; // Afin d'attribuer un nom different a toutes les instances
	public int numero;
	
	private Integer cryptogramme;
	private Journal journal;
	
	protected AcheteurCacaoCriee acheteurCacaoCriee;
	protected AcheteurChocolatBourse acheteurChocolatBourse;
	protected DistributeurChocolat distributeurChocolat;
	protected Stock stock;


	public Distributeur2() {
		NB_INSTANCES++;
		numero = NB_INSTANCES;
		stock = new Stock(this);		
		acheteurCacaoCriee = new AcheteurCacaoCriee(this);
		acheteurChocolatBourse = new AcheteurChocolatBourse(this);
		distributeurChocolat = new DistributeurChocolat(this);
		this.journal = new Journal(this.getNom() + " Activités " + numero, this);
	}
	
	// Renvoie l'unique instance de la classe Stock associée au distributeur
	protected Stock getStock() {
		return this.stock;
	}
	
	// Renvoie l'unique instance de la classe AcheteurCacaoCriee associée au distributeur
	protected AcheteurCacaoCriee getAcheteurCacaoCriee() {
		return this.acheteurCacaoCriee;
	}
	
	// Renvoie l'unique instance de la classe AcheteurChocolatBourse associée au distributeur
	protected AcheteurChocolatBourse getAcheteurChocolatBourse() {
		return this.acheteurChocolatBourse;
	}
	
	// Renvoie l'unique instance de la classe DistributeurChocolat associée au distributeur
	protected DistributeurChocolat getDistributeurChocolat() {
		return this.distributeurChocolat;
	}
	
	// Renvoie le nom de l'acteur (par défaut : "EQ7")
	public String getNom() {
		return "EQ7";
	}

	// Renvoie la description de l'acteur
	public String getDescription() {
		return "Distributeur Ecocoa de Liège, chocolatier responsable";
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
		this.stock.stocksChocolat.get(Chocolat.CHOCOLAT_HAUTE).setValeur(this, this.stock.stocksChocolat.get(stock.stringToChoco("H")).getValeur() + 5.);
		acheteurCacaoCriee.next(); //ajoute 10 au stock "H" par l'intermédiaire de l'acheteur cacao criée
	}

	public List<String> getNomsFilieresProposees() {
		return new ArrayList<String>();
	}

	public Filiere getFiliere(String nom) {
		return Filiere.LA_FILIERE;
	}

	public List<Variable> getIndicateurs() {
		List<Variable> res = new ArrayList<Variable>();
		res.addAll(stock.getIndicateurs());
		res.addAll(acheteurCacaoCriee.getIndicateurs());
		res.addAll(acheteurChocolatBourse.getIndicateurs());
		res.addAll(distributeurChocolat.getIndicateurs());
		return res;
	}

	public List<Variable> getParametres() {
		List<Variable> res=new ArrayList<Variable>();
		return res;
	}

	public List<Journal> getJournaux() {
		List<Journal> res=new ArrayList<Journal>();
		res.add(journal);
		res.addAll(stock.getJournaux());
		res.addAll(acheteurCacaoCriee.getJournaux());
		res.addAll(acheteurChocolatBourse.getJournaux());
		res.addAll(distributeurChocolat.getJournaux());
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
