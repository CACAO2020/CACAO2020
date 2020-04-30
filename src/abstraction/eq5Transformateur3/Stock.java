package abstraction.eq5Transformateur3;

import java.util.HashMap;
import java.util.Map;

import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.Feve;
import abstraction.eq8Romu.produits.Pate;
import abstraction.fourni.Variable;

/** @author F. GOUJON  */
public class Stock {
	/**
     * Gère les stock de fève, pate et chocolat du Transformateur3
     * Elle est agrégé dans la classe Transformateur3.
     */
	
	private Transformateur3 acteur;
	private Map<Feve, Variable> stockFeves;
	private Map<Chocolat, Variable> stockChocolat;
	private Map<Pate, Variable> stockPate;
	
	public Stock(Transformateur3 acteur) {
		this.acteur = acteur;
		this.stockChocolat = new HashMap<Chocolat, Variable>();
		this.stockFeves = new HashMap<Feve, Variable>();
		this.stockPate = new HashMap<Pate, Variable>();
		this.stockFeves.put(Feve.FEVE_BASSE, new Variable(acteur.getNom() + " stock feves basse qualité", acteur, 50));
		this.stockChocolat.put(Chocolat.CHOCOLAT_BASSE, new Variable(acteur.getNom() + " stock chocolat basse qualité", acteur, 50));
	}
	
	public Map<Feve,Variable> getStockFeves() {
		return this.stockFeves;
	}
	public Map<Chocolat, Variable> getStockChocolat() {
		return this.stockChocolat;
	}
	public Map<Pate, Variable> getStockPate() {
		return this.stockPate;
	}

	public void ajoutFeves(Feve feve, double quantite) {
		this.stockFeves.get(feve).ajouter(acteur, quantite);
	}

	public void retirerFeves(Feve feve, double quantite) {
		this.stockFeves.get(feve).retirer(acteur, quantite);
	}
	
	public void ajoutChocolat(Chocolat choco, double quantite) {
		this.stockChocolat.get(choco).ajouter(acteur, quantite);
	}	

	public void retirerChocolat(Chocolat choco, double quantite) {
		this.stockChocolat.get(choco).ajouter(acteur, quantite);
	}
	
	
	public void ajoutPate(Pate pate, double quantite) {
		this.stockPate.get(pate).ajouter(acteur, quantite);
	}	

	public void retirerPate(Pate pate, double quantite) {
		this.stockPate.get(pate).ajouter(acteur, quantite);
	}
			
}
	
	
