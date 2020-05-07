package abstraction.eq6Distributeur1;

import java.util.ArrayList;
import java.util.Map;

import abstraction.eq8Romu.produits.Chocolat;

public class Stock extends Distributeur1 implements IStock{ /** Classe implémentée par Avril Thibault et Tamine Mélissa*/
	protected double capaciteStockmax;
	protected Map<Chocolat,ArrayList<Double>> MapStock;
	

	@Override
	public double quantiteEnStockTypeChoco(Chocolat choco) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double quantiteEnStockTotale() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double espaceRestant(Chocolat choco) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double CoutdeStockage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double ValeurdeQuantiteStockee(Chocolat choco) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void Stocker(Chocolat choco, double quantite) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Destocker() {
		// TODO Auto-generated method stub
		
	}

}
