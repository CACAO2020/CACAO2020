package abstraction.eq3Transformateur1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.Feve;
import abstraction.fourni.Variable;

/** @author AMAURY COUDRAY*/
public class Stock extends ActeurEQ3 {
	
	
	/* quantite de chaque type de FEVE/CHocolat */
	protected Map<Feve,Double> stockFeves;
	protected Map<Chocolat,Double> stockChocolat;
	
	/* cout pour chaque unité de chaque type de FEVE/CHocolat */
	protected Map<Feve,Double> coutFeves;
	protected Map<Chocolat,Double> coutChocolat;
	
	protected Variable stockTotalFeves;
	protected Variable stockTotalChocolat;
	public Stock() {
		this.stockFeves=new HashMap<Feve,Double>();
		this.stockChocolat=new HashMap<Chocolat,Double>();
		this.coutChocolat=new HashMap<Chocolat,Double>();;
		this.coutFeves=new HashMap<Feve,Double>();
		/*
		 * FAKE VALEUR pour le moment
		 */
		this.stockFeves.put(Feve.FEVE_MOYENNE,15.0);
		this.stockChocolat.put(Chocolat.CHOCOLAT_MOYENNE,15.0);
		this.stockTotalFeves=new Variable("stock total de feves de "+getNom(),this,15.0);
		this.stockTotalChocolat=new Variable("stock total de chocolat de "+getNom(),this,15.0);
	}
	/* PRIX correpond au prix de la quantité TOTAL (prix du lot)*/
	public void setCoutFeves(Feve feve,Double cout) {
		this.coutFeves.put(feve,cout);
	}
	public void setCoutChocolat(Chocolat chocolat,Double cout) {
		this.coutChocolat.put(chocolat,cout);
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
	public Double getStockFeves(Feve feve) {
		return this.stockFeves.get(feve);
	}
	public Map<Chocolat,Double> getStockChocolat() {
		return this.stockChocolat;
	}
	public Double getStockChocolat(Chocolat chocolat) {
		return this.stockChocolat.get(chocolat);
	}
	public Map<Feve,Double> getCoutFeves() {
		return this.coutFeves;
	}
	public Double getCoutFeves(Feve feve) {
		return this.coutFeves.get(feve);
	}
	public Map<Chocolat,Double> getCoutChocolat() {
		return this.coutChocolat;
	}
	public Double getCoutChocolat(Chocolat chocolat) {
		return this.coutChocolat.get(chocolat);
	}
	
	/*
	  pour tester les methodes de la classe
	*/
	
	public static void main(String[] args) {
		Stock stock=new Stock();
		System.out.println("");
		System.out.println("TEST METHODES GET (a l'etat initial)");
		System.out.println("");
		System.out.println("Map stock Chocolat "+stock.getStockChocolat());
		System.out.println("Stock chocolat moyenne = "+stock.getStockChocolat(Chocolat.CHOCOLAT_MOYENNE));
		System.out.println("Stock chocolat basse = "+stock.getStockChocolat(Chocolat.CHOCOLAT_BASSE));
		System.out.println("Map cout Chocolat "+stock.getCoutChocolat());
		System.out.println("Cout chocolat moyenne = "+stock.getCoutChocolat(Chocolat.CHOCOLAT_MOYENNE));
		System.out.println("Map Stock feves "+stock.getStockFeves());
		System.out.println("Stock feve basse = "+stock.getStockFeves(Feve.FEVE_BASSE));
		System.out.println("Stock feve moyenne = "+stock.getStockFeves(Feve.FEVE_MOYENNE));
		System.out.println("Map cout Feves "+stock.getCoutFeves());
		System.out.println("Cout Feves moyenne "+stock.getCoutFeves(Feve.FEVE_MOYENNE));
		stock.setStockChocolat(Chocolat.CHOCOLAT_HAUTE_EQUITABLE,15.0);
		stock.setStockChocolat(Chocolat.CHOCOLAT_MOYENNE,15.0);
		System.out.println("");
		System.out.println("TEST DES METHODES GET/SET CHOCOLAT");
		System.out.println("");
		System.out.println("Map stock Chocolat "+stock.getStockChocolat());
		System.out.println("Map cout Chocolat "+stock.getCoutChocolat());
		System.out.println("Cout Chocolat moyenne "+stock.getCoutChocolat(Chocolat.CHOCOLAT_MOYENNE));
		System.out.println("Cout Chocolat haute equitable "+stock.getCoutChocolat(Chocolat.CHOCOLAT_HAUTE_EQUITABLE));
		System.out.println("");
		stock.setStockFeves(Feve.FEVE_MOYENNE,15.0);
		stock.setStockFeves(Feve.FEVE_BASSE,15.0);
		System.out.println("TEST DES METHODES GET/SET FEVES ");
		System.out.println("");
		System.out.println("Map Stock feves "+stock.getStockFeves());
		System.out.println("Stock feve basse = "+stock.getStockFeves(Feve.FEVE_BASSE));
		System.out.println("Stock feve moyenne = "+stock.getStockFeves(Feve.FEVE_MOYENNE));
		System.out.println("Map cout Feves "+stock.getCoutFeves());
		System.out.println("Cout Feves moyenne "+stock.getCoutFeves(Feve.FEVE_MOYENNE));


	}
	/*
	 * if(this.coutFeves.containsKey(feve)) {
			this.setCoutFeves(feve,(prix+this.coutFeves.get(feve)*this.stockFeves.get(feve))/(quantite+this.stockFeves.get(feve)));
		}
		else {
			this.setCoutFeves(feve,prix/quantite);
		}
	 		if(this.coutChocolat.containsKey(chocolat)) {
			this.coutChocolat.put(chocolat,(prix+this.coutChocolat.get(chocolat)*this.stockChocolat.get(chocolat))/(quantite+this.stockChocolat.get(chocolat)));
		}
		else {
			this.coutChocolat.put(chocolat,prix/quantite);
		}
	 */
	
}
