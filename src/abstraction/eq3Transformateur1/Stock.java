package abstraction.eq3Transformateur1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.Feve;
import abstraction.eq8Romu.produits.Gamme;
import abstraction.fourni.Variable;

public class Stock extends ActeurEQ3 {
	/*
	 * Author Amaury Coudray
	 */

	private Map<Feve,Double> StockFeves;
	private Map<Chocolat,Double> StockChocolat;
	public Stock() {
		StockFeves=new HashMap<Feve,Double>();
		StockChocolat=new HashMap<Chocolat,Double>();
	}
	
	public void setStockFeves(Feve feve,Double quantite) {
		StockFeves.put(feve,quantite+StockFeves.get(feve));
	}
	public void setStockChocolat(Chocolat chocolat,Double quantite) {
		StockChocolat.put(chocolat,quantite+StockChocolat.get(chocolat));
	}
	

}
