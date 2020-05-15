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
		return stocksChocolat.get(chocoDeMarque).getValeur();
	}

	public double getStockFeves(Feve feve) {
		return stocksFeves.get(feve).getValeur();
	}

	public void ajouterStockChocolat(ChocolatDeMarque chocoDeMarque, double quantite) {
		stocksChocolat.get(chocoDeMarque).setValeur(ac, stocksChocolat.get(chocoDeMarque).getValeur() + quantite);
		journal.ajouter(quantite + " tonnes de chocolat " + chocoDeMarque + " ont été ajoutées au stock (nouveau stock : " + stocksChocolat.get(chocoDeMarque).getValeur()+ " tonnes)");
	}

	public void retirerStockChocolat(ChocolatDeMarque chocoDeMarque, double quantite) {
		stocksChocolat.get(chocoDeMarque).setValeur(ac, stocksChocolat.get(chocoDeMarque).getValeur() - quantite);
		journal.ajouter(quantite + " tonnes de chocolat " + chocoDeMarque + " ont été retirées du stock (nouveau stock : " + stocksChocolat.get(chocoDeMarque).getValeur()+ " tonnes)");
	}

	public void ajouterStockFeves(Feve feve, double quantite) {
		stocksFeves.get(feve).setValeur(ac, stocksFeves.get(feve).getValeur() + quantite);
		journal.ajouter(quantite + " tonnes de fèves " + feve + " ont été ajoutées au stock (nouveau stock : " + stocksFeves.get(feve).getValeur() + " tonnes)");
	}

	public void retirerStockFeves(Feve feve, double quantite) {
		stocksFeves.get(feve).setValeur(ac, stocksFeves.get(feve).getValeur() - quantite);
		journal.ajouter(quantite + " tonnes de fèves " + feve + " ont été retirées du stock (nouveau stock : " + stocksFeves.get(feve).getValeur() + " tonnes)");
	}
	
	
}
