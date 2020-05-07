package abstraction.eq3Transformateur1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.Feve;
import abstraction.fourni.Variable;


public class Stock extends ActeurEQ3 {
	/*
	 * Author Amaury Coudray
	 */

	protected Map<Feve,Double> stockFeves;
	protected Map<Chocolat,Double> stockChocolat;
	protected Variable stockTotalFeves;
	protected Variable stockTotalChocolat;
	public Stock() {
		this.stockFeves=new HashMap<Feve,Double>();
		this.stockChocolat=new HashMap<Chocolat,Double>();
		this.stockTotalFeves=new Variable("stock total de feves de "+getNom(),this,0);
		
	}
	
	public void setStockFeves(Feve feve,Double quantite) {
		this.stockFeves.put(feve,quantite+this.stockFeves.get(feve));
	}
	public void setStockChocolat(Chocolat chocolat,Double quantite) {
		this.stockChocolat.put(chocolat,quantite+this.stockChocolat.get(chocolat));
	}
	public Map<Feve,Double> getStockFeves() {
		return this.stockFeves;
	}
	public Map<Chocolat,Double> getStockChocolat() {
		return this.stockChocolat;
	}
}
