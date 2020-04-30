package abstraction.eq5Transformateur3;

import java.util.Map;

import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.Feve;
import abstraction.fourni.Variable;

/** @author F. GOUJON  */
public class Stock {
	/**
     * Gère les stock de fève, pate et chocolat du Transformateur3
     * Elle est agrégé dans la classe Transformateur3.
     */
	
	private Transformateur3 Acteur;
	private Map<Feve, Variable> stockFeves;
	private Map<Chocolat, Variable> stockChocolat;
	
	public Stock(Transformateur3 Acteur) {
		this.stockFeves.put(Feve.FEVE_BASSE, new Variable(getNom() + " stock feves basse qualité", this, 50));
		this.stockChocolat.put(Chocolat.CHOCOLAT_BASSE, new Variable(getNom() + " stock chocolat basse qualité", this, 50));
	}
	
	public Map<Feve,Variable> getStockFeves() {
		return this.stockFeves;
	}
	public Map<Chocolat, Variable> getStockChocolat() {
		return this.stockChocolat;
	}
}
