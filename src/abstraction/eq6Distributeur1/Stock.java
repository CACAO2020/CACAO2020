package abstraction.eq6Distributeur1;

import java.util.HashMap;
import java.util.Map;

import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.ChocolatDeMarque;

public class Stock extends Distributeur1 implements IStock{ /** @author Avril Thibault et Tamine Mélissa*/
	protected double capaciteStockmax;
	protected Map<ChocolatDeMarque,Double> MapStock;
	
	public Stock (double capaciteStockmax, Map<ChocolatDeMarque,Double> MapStock) {
		this.capaciteStockmax = capaciteStockmax;
		this.MapStock = new HashMap<ChocolatDeMarque,Double>();
	}

	@Override
	public double quantiteEnStockTypeChoco(Chocolat choco) {
		double quantite = 0;
		for (ChocolatDeMarque chocos : this.MapStock.keySet()) {
			if (chocos.getChocolat()==choco) {
				quantite = quantite + MapStock.get(chocos);
			}
		}
		return quantite;
	}

	@Override
	public double quantiteEnStockTotale() {
		double quantite = 0;
		for (ChocolatDeMarque chocos : this.MapStock.keySet()) {
			quantite = quantite + MapStock.get(chocos);
		}
		return quantite;
	}

	@Override
	public double espaceRestant(Chocolat choco) {
		return this.quantiteEnStockTypeChoco(choco)-this.capaciteStockmax;
	}

	@Override
	public double coutdeStockage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double valeurdeQuantiteStockee(Chocolat choco) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void stocker(ChocolatDeMarque choco, double quantite) {
		if (MapStock.keySet().contains(choco)) {
			this.MapStock.put(choco, this.MapStock.get(choco)+quantite);
		}
		else {
			this.MapStock.put(choco, quantite);
		}
		
	}

	@Override
	public void destocker() {
		
		
	}

	@Override
	public double quantiteEnStockMarqueChoco(ChocolatDeMarque choco) {
		return this.MapStock.get(choco);
	}

}
