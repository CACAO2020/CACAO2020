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
//cette classe permet de gerer les mouvement de stock
public class Stock extends AbsStock implements IStock {
	
	public Stock(Distributeur2 ac) {
			super(ac);
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
	//ajoute les quantités necessaires de chocolat des stocks correspondant
	public void ajouterStockChocolat(ChocolatDeMarque chocoDeMarque, double quantite) {
		if (!stocksChocolatDeMarque.containsKey(chocoDeMarque)) {
		}
		if (!stocksChocolat.containsKey(chocoDeMarque.getChocolat())) {
		}
		stocksChocolatDeMarque.get(chocoDeMarque).setValeur(ac, stocksChocolatDeMarque.get(chocoDeMarque).getValeur() + quantite);
		stocksChocolat.get(chocoDeMarque.getChocolat()).setValeur(ac, stocksChocolat.get(chocoDeMarque.getChocolat()).getValeur() + quantite);
		journal.ajouter(Journal.texteColore(addStockColor, Color.BLACK, "[STOCK +] " + Journal.doubleSur(quantite,2) + " tonnes de " + chocoDeMarque.name() + " ont été ajoutées au stock (nouveau stock : " + Journal.doubleSur(stocksChocolatDeMarque.get(chocoDeMarque).getValeur(),2) + " tonnes)."));
		
	}
//retire les quantités necessaires de chocolat des stocks correspondant (avec la prise en compte des potentiels echecs de mouvements)
	public void retirerStockChocolat(ChocolatDeMarque chocoDeMarque, double quantite) {
		if (!stocksChocolatDeMarque.containsKey(chocoDeMarque)) {
		}
		if (!stocksChocolat.containsKey(chocoDeMarque.getChocolat())) {
		}
		if (stocksChocolatDeMarque.get(chocoDeMarque).getValeur() >= quantite) {
			stocksChocolatDeMarque.get(chocoDeMarque).setValeur(ac, stocksChocolatDeMarque.get(chocoDeMarque).getValeur() - quantite);
			stocksChocolat.get(chocoDeMarque.getChocolat()).setValeur(ac, stocksChocolat.get(chocoDeMarque.getChocolat()).getValeur() - quantite);
			journal.ajouter(Journal.texteColore(removeStockColor, Color.BLACK, "[STOCK -] " + Journal.doubleSur(quantite,2) + " tonnes de " + chocoDeMarque.name() + " ont été retirées du stock (nouveau stock : " + Journal.doubleSur(stocksChocolatDeMarque.get(chocoDeMarque).getValeur(),2) + " tonnes)."));
		} else {
				journal.ajouter(Journal.texteColore(alertColor, Color.BLACK,"[ÉCHEC STOCK -] Tentative de retirer " + Journal.doubleSur(quantite,2) + " tonnes de " + chocoDeMarque.name() + " rejetée (stock actuel : " + Journal.doubleSur(stocksChocolatDeMarque.get(chocoDeMarque).getValeur(),2) + " tonnes)."));
		}
	}
	//ajoute une valeur a un stock en cas d'achat fructueux auprès d'un transformateur
	public void initialiser() {		
		for (ChocolatDeMarque choco : ac.tousLesChocolatsDeMarquePossibles()) {
			stocksChocolatDeMarque.put(choco, new Variable(ac.getNom() + " : STOCK " + choco.name(), ac, 0.));
			journal.ajouter(Journal.texteColore(metaColor, Color.BLACK,"[CRÉATION] Création d'un stock pour le " + choco.name() + "."));
		}
	}
	
}
