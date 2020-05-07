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

	protected Map<Feve,Double> StockFeves;
	protected Map<Chocolat,Double> StockChocolat;
	public Stock() {
		this.StockFeves=new HashMap<Feve,Double>();
		this.StockChocolat=new HashMap<Chocolat,Double>();
	}
	
	public void setStockFeves(Feve feve,Double quantite) {
		this.StockFeves.put(feve,quantite+this.StockFeves.get(feve));
	}
	public void setStockChocolat(Chocolat chocolat,Double quantite) {
		this.StockChocolat.put(chocolat,quantite+this.StockChocolat.get(chocolat));
	}
	public Map<Feve,Double> getStockFeves() {
		return this.StockFeves;
	}
	public Map<Chocolat,Double> getStockChocolat() {
		return this.StockChocolat;
	}
}
