package abstraction.eq7Distributeur2;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.ChocolatDeMarque;
import abstraction.eq8Romu.produits.Feve;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;

public class Stock extends AbsStock implements IStock{
	
	public Stock(Distributeur2 ac) {
			super(ac);
	}

	public void creerStockChocolat(ChocolatDeMarque choco) {
		if (stocksChocolat.containsKey(choco)) {
			journal.ajouter("Tentative de créer un stock pour le chocolat " + choco + " refusée : le stock existe déjà.");
		} else {
			stocksChocolat.put(choco, new Variable(getNom() + " : STOCK " + choco.name(), ac, 0.));
			journal.ajouter("Création d'un stock pour le chocolat " + choco + ".");
		}
	}
	
	public void creerStockFeves(Feve feve) {
		if (stocksFeves.containsKey(feve)) {
			journal.ajouter("Tentative de créer un stock pour les fèves " + feve + " refusée : le stock existe déjà.");
		} else {
			stocksFeves.put(feve, new Variable(getNom() + " : STOCK " + feve.name(), ac, 0.));
			journal.ajouter("Création d'un stock pour les fèves " + feve + ".");
		}
	}
	
	public double getStockChocolat(Chocolat choco) {
		double res = 0.;
		for (ChocolatDeMarque chocoDeMarque : stocksChocolat.keySet()) {
			if (chocoDeMarque.getChocolat() == choco) {
				res += stocksChocolat.get(chocoDeMarque).getValeur();
			}
		}
		return res;
	}
	
	public double getStockChocolat(ChocolatDeMarque chocoDeMarque) {
		if (stocksChocolat.containsKey(chocoDeMarque)) {
			return stocksChocolat.get(chocoDeMarque).getValeur();
		} else {
			return 0.;
		}
	}

	public double getStockFeves(Feve feve) {
		if (stocksFeves.containsKey(feve)) {
			return stocksFeves.get(feve).getValeur();
		} else {
			return 0.;
		}
	}

	public void ajouterStockChocolat(ChocolatDeMarque chocoDeMarque, double quantite) {
		if (!stocksChocolat.containsKey(chocoDeMarque)) {
			creerStockChocolat(chocoDeMarque);
		}
		stocksChocolat.get(chocoDeMarque).setValeur(ac, stocksChocolat.get(chocoDeMarque).getValeur() + quantite);
		journal.ajouter(quantite + " tonnes de chocolat " + chocoDeMarque + " ont été ajoutées au stock (nouveau stock : " + stocksChocolat.get(chocoDeMarque).getValeur()+ " tonnes).");
		
	}

	public void retirerStockChocolat(ChocolatDeMarque chocoDeMarque, double quantite) {
		if (!stocksChocolat.containsKey(chocoDeMarque)) {
			creerStockChocolat(chocoDeMarque);
		}
		
		if (stocksChocolat.get(chocoDeMarque).getValeur() >= quantite) {
				stocksChocolat.get(chocoDeMarque).setValeur(ac, stocksChocolat.get(chocoDeMarque).getValeur() - quantite);
				journal.ajouter(quantite + " tonnes de chocolat " + chocoDeMarque + " ont été retirées du stock (nouveau stock : " + stocksChocolat.get(chocoDeMarque).getValeur()+ " tonnes).");
		} else {
				journal.ajouter("Tentative de retirer " + quantite + " tonnes de chocolat " + chocoDeMarque + " rejetée (stock actuel : " + stocksChocolat.get(chocoDeMarque).getValeur() + " tonnes).");
		}
	}

	public void ajouterStockFeves(Feve feve, double quantite) {
		if (!stocksFeves.containsKey(feve)) {
			creerStockFeves(feve);
		}
		stocksFeves.get(feve).setValeur(ac, stocksFeves.get(feve).getValeur() + quantite);
		journal.ajouter(quantite + " tonnes de fèves " + feve + " ont été ajoutées au stock (nouveau stock : " + stocksFeves.get(feve).getValeur() + " tonnes).");
	}

	public void retirerStockFeves(Feve feve, double quantite) {
		if (!stocksFeves.containsKey(feve)) {
			creerStockFeves(feve);
		}
		
		if (stocksFeves.get(feve).getValeur() >= quantite) {
				stocksFeves.get(feve).setValeur(ac, stocksFeves.get(feve).getValeur() - quantite);
				journal.ajouter(quantite + " tonnes de fèves " + feve + " ont été retirées du stock (nouveau stock : " + stocksFeves.get(feve).getValeur() + " tonnes).");
		} else {
				journal.ajouter("Tentative de retirer " + quantite + " tonnes de fèves " + feve + " rejetée (stock actuel : " + stocksFeves.get(feve).getValeur() + " tonnes).");
		}	
	}
	
	
}
