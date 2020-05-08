package abstraction.eq7Distributeur2;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import abstraction.eq8Romu.produits.Chocolat;
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
		return stocksChocolat.get(choco).getValeur();
	}

	public double getStockFeves(Feve feve) {
		return stocksFeves.get(feve).getValeur();
	}

	public void ajouterStockChocolat(Chocolat choco, double quantite) {
		stocksChocolat.get(choco).setValeur(ac, stocksChocolat.get(choco).getValeur() + quantite);
		journal.ajouter(quantite + " tonnes de chocolat " + choco + " ont été ajoutées au stock (nouveau stock : " + stocksChocolat.get(choco).getValeur()+ " tonnes)");
	}

	public void retirerStockChocolat(Chocolat choco, double quantite) {
		stocksChocolat.get(choco).setValeur(ac, stocksChocolat.get(choco).getValeur() - quantite);
		journal.ajouter(quantite + " tonnes de chocolat " + choco + " ont été retirées du stock (nouveau stock : " + stocksChocolat.get(choco).getValeur()+ " tonnes)");
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
