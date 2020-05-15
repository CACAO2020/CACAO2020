package abstraction.eq7Distributeur2;

import java.awt.Color;
import java.util.ArrayList;
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

public class Stock extends AbsStock implements IStock, IActeur {
	
	public Stock(Distributeur2 ac) {
			super(ac);
	}

	public void creerStockChocolatDeMarque(ChocolatDeMarque choco) {
		if (stocksChocolatDeMarque.containsKey(choco)) {
			journal.ajouter("[Echec] Création d'un stock pour le " + choco.name() + " refusée : le stock existe déjà.");
		} else {
			stocksChocolatDeMarque.put(choco, new Variable(ac.getNom() + " : STOCK " + choco.name(), ac, 0.));
			journal.ajouter("Création d'un stock pour le " + choco.name() + ".");
		}
	}
	
	public double getStockChocolat(Chocolat choco) {
		if (stocksChocolat.containsKey(choco)) {
			return stocksChocolat.get(choco).getValeur();
		} else {
			return 0.;
		}
	}
	
	public double getStockChocolatDeMarque(ChocolatDeMarque chocoDeMarque) {
		if (stocksChocolatDeMarque.containsKey(chocoDeMarque)) {
			return stocksChocolatDeMarque.get(chocoDeMarque).getValeur();
		} else {
			return 0.;
		}
	}

	public void ajouterStockChocolat(ChocolatDeMarque chocoDeMarque, double quantite) {
		if (!stocksChocolatDeMarque.containsKey(chocoDeMarque)) {
			creerStockChocolatDeMarque(chocoDeMarque);
		}
		if (!stocksChocolat.containsKey(chocoDeMarque.getChocolat())) {
		}
		stocksChocolatDeMarque.get(chocoDeMarque).setValeur(ac, stocksChocolatDeMarque.get(chocoDeMarque).getValeur() + quantite);
		stocksChocolat.get(chocoDeMarque.getChocolat()).setValeur(ac, stocksChocolat.get(chocoDeMarque.getChocolat()).getValeur() + quantite);
		journal.ajouter(quantite + " tonnes de " + chocoDeMarque.name() + " ont été ajoutées au stock (nouveau stock : " + stocksChocolatDeMarque.get(chocoDeMarque).getValeur()+ " tonnes).");
		
	}

	public void retirerStockChocolat(ChocolatDeMarque chocoDeMarque, double quantite) {
		if (!stocksChocolatDeMarque.containsKey(chocoDeMarque)) {
			creerStockChocolatDeMarque(chocoDeMarque);
		}
		if (!stocksChocolat.containsKey(chocoDeMarque.getChocolat())) {
		}
		if (stocksChocolatDeMarque.get(chocoDeMarque).getValeur() >= quantite) {
			stocksChocolatDeMarque.get(chocoDeMarque).setValeur(ac, stocksChocolatDeMarque.get(chocoDeMarque).getValeur() - quantite);
			stocksChocolat.get(chocoDeMarque.getChocolat()).setValeur(ac, stocksChocolat.get(chocoDeMarque.getChocolat()).getValeur() - quantite);
			journal.ajouter(quantite + " tonnes de " + chocoDeMarque.name() + chocoDeMarque.getMarque() + " ont été retirées du stock (nouveau stock : " + stocksChocolatDeMarque.get(chocoDeMarque).getValeur()+ " tonnes).");
		} else {
				journal.ajouter("[Echec] Tentative de retirer " + quantite + " tonnes de " + chocoDeMarque.name() + " rejetée (stock actuel : " + stocksChocolatDeMarque.get(chocoDeMarque).getValeur() + " tonnes).");
		}
	}

	public List<String> getMarques() {
		List<String> res = new ArrayList<String>();
		for (ChocolatDeMarque choco : stocksChocolatDeMarque.keySet()) {
			res.add(choco.getMarque());
		}
		return res;
	}
	
	public void initialiser() {
		
	}
	
	public void next() {
		
	}
	
}
