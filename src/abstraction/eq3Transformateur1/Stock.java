package abstraction.eq3Transformateur1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.Feve;
import abstraction.fourni.Variable;


public class Stock extends ActeurEQ3 {
	/*
	 * Author AMAURY COUDRAY
	 */

	protected Map<Feve,Double> stockFeves;
	protected Map<Chocolat,Double> stockChocolat;
	protected Variable stockTotalFeves;
	protected Variable stockTotalChocolat;
	public Stock() {
		this.stockFeves=new HashMap<Feve,Double>();
		this.stockFeves.put(Feve.FEVE_MOYENNE,15.0);
		this.stockChocolat=new HashMap<Chocolat,Double>();
		this.stockChocolat.put(Chocolat.CHOCOLAT_MOYENNE,15.0);
		this.stockTotalFeves=new Variable("stock total de feves de "+getNom(),this,15.0);
		this.stockTotalChocolat=new Variable("stock total de chocolat de "+getNom(),this,15.0);
	}
	
	public void setStockFeves(Feve feve,Double quantite) {
		if(this.stockFeves.containsKey(feve)) {
			this.stockFeves.put(feve,quantite+this.stockFeves.get(feve));
		}
		else{
			this.stockFeves.put(feve,quantite);
		}
		this.stockTotalFeves.setValeur(this, quantite);

	}
	public void setStockChocolat(Chocolat chocolat,Double quantite) {
		if(this.stockChocolat.containsKey(chocolat)) {
			this.stockChocolat.put(chocolat,quantite+this.stockChocolat.get(chocolat));
		}
		else {
			this.stockChocolat.put(chocolat,quantite);
		}
		this.stockTotalChocolat.setValeur(this, quantite);
	}
	public Map<Feve,Double> getStockFeves() {
		return this.stockFeves;
	}
	public Map<Chocolat,Double> getStockChocolat() {
		return this.stockChocolat;
	}
	/*
	 * pour tester les methodes de la classe
	public static void main(String[] args) {
		Stock stock=new Stock();
		System.out.println(stock.getStockFeves());
		stock.setStockChocolat(Chocolat.CHOCOLAT_HAUTE, 2.0);
		System.out.println(stock.getStockChocolat());
	}
	*/
}
