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
	
	public Stock() {
			super();
	}

	@Override
	public double getStockChocolat(Chocolat choco) {
		return stocksChocolat.get(choco).getValeur();
	}

	@Override
	public Chocolat stringToChoco(String abr) {
		return abreviationChocolats.get(abr);
	}

	@Override
	public double getStockFeves(Feve feve) {
		return stocksFeves.get(feve).getValeur();
	}

	@Override
	public Feve stringToFeve(String abr) {
		return abreviationFeves.get(abr);
	}

	@Override
	public void ajouterStockChocolat(Chocolat choco, double quantite) {
	}

	@Override
	public void retirerStockChocolat(Chocolat choco, double quantite) {
	}

	@Override
	public void ajouterStockFeves(Feve feve, double quantite) {
	}

	@Override
	public void retirerStockFeves(Feve feve, double quantite) {
	}
	
	
}
