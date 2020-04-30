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
		if (feve.equals(Feve.FEVE_BASSE)) {
			this.stockFeves.put(feve, new Variable(acteur.getNom() + " stock feves basse qualité", acteur, this.getStockFeves().get(Feve.FEVE_BASSE).getValeur() + quantite));
		}
		if (feve.equals(Feve.FEVE_MOYENNE)) {
			this.stockFeves.put(feve, new Variable(acteur.getNom() + " stock feves moyenne qualité", acteur, this.getStockFeves().get(Feve.FEVE_MOYENNE).getValeur() + quantite));
		}
		if (feve.equals(Feve.FEVE_HAUTE)) {
			this.stockFeves.put(feve, new Variable(acteur.getNom() + " stock feves haute qualité", acteur, this.getStockFeves().get(Feve.FEVE_HAUTE).getValeur() + quantite));
		}
		if (feve.equals(Feve.FEVE_MOYENNE_EQUITABLE)) {
			this.stockFeves.put(feve, new Variable(acteur.getNom() + " stock feves moyenne qualité équitable", acteur, this.getStockFeves().get(Feve.FEVE_MOYENNE_EQUITABLE).getValeur() + quantite));
		}
		if (feve.equals(Feve.FEVE_HAUTE_EQUITABLE)) {
			this.stockFeves.put(feve, new Variable(acteur.getNom() + " stock feves haute qualité équitable", acteur, this.getStockFeves().get(Feve.FEVE_HAUTE_EQUITABLE).getValeur() + quantite));
		}
	}
	public void retirerFeves(Feve feve, double quantite) {
		if (feve.equals(Feve.FEVE_BASSE)) {
			this.stockFeves.put(feve, new Variable(acteur.getNom() + " stock feves basse qualité", acteur, this.getStockFeves().get(Feve.FEVE_BASSE).getValeur() - quantite));
		}
		if (feve.equals(Feve.FEVE_MOYENNE)) {
			this.stockFeves.put(feve, new Variable(acteur.getNom() + " stock feves moyenne qualité", acteur, this.getStockFeves().get(Feve.FEVE_MOYENNE).getValeur() - quantite));
		}
		if (feve.equals(Feve.FEVE_HAUTE)) {
			this.stockFeves.put(feve, new Variable(acteur.getNom() + " stock feves haute qualité", acteur, this.getStockFeves().get(Feve.FEVE_HAUTE).getValeur() - quantite));
		}
		if (feve.equals(Feve.FEVE_MOYENNE_EQUITABLE)) {
			this.stockFeves.put(feve, new Variable(acteur.getNom() + " stock feves moyenne qualité équitable", acteur, this.getStockFeves().get(Feve.FEVE_MOYENNE_EQUITABLE).getValeur() - quantite));
		}
		if (feve.equals(Feve.FEVE_HAUTE_EQUITABLE)) {
			this.stockFeves.put(feve, new Variable(acteur.getNom() + " stock feves haute qualité équitable", acteur, this.getStockFeves().get(Feve.FEVE_HAUTE_EQUITABLE).getValeur() - quantite));
		}
	}	
	public void ajoutChocolat(Chocolat choco, double quantite) {
		if (choco.equals(Chocolat.CHOCOLAT_BASSE)) {
			this.stockChocolat.put(choco, new Variable(acteur.getNom() + " stock chocolat basse qualité", acteur, this.getStockChocolat().get(Chocolat.CHOCOLAT_BASSE).getValeur() + quantite));
		}
		if (choco.equals(Chocolat.CHOCOLAT_MOYENNE)) {
			this.stockChocolat.put(choco, new Variable(acteur.getNom() + " stock chocolat moyenne qualité", acteur, this.getStockChocolat().get(Chocolat.CHOCOLAT_MOYENNE).getValeur() + quantite));
		}
		if (choco.equals(Chocolat.CHOCOLAT_HAUTE)) {
			this.stockChocolat.put(choco, new Variable(acteur.getNom() + " stock chocolat haute qualité", acteur, this.getStockChocolat().get(Chocolat.CHOCOLAT_HAUTE).getValeur() + quantite));
		}
		if (choco.equals(Chocolat.CHOCOLAT_MOYENNE_EQUITABLE)) {
			this.stockChocolat.put(choco, new Variable(acteur.getNom() + " stock chocolat moyenne qualité équitable", acteur, this.getStockChocolat().get(Chocolat.CHOCOLAT_MOYENNE_EQUITABLE).getValeur() + quantite));
		}
		if (choco.equals(Chocolat.CHOCOLAT_HAUTE_EQUITABLE)) {
			this.stockChocolat.put(choco, new Variable(acteur.getNom() + " stock chocolat haute qualité équitable", acteur, this.getStockChocolat().get(Chocolat.CHOCOLAT_HAUTE_EQUITABLE).getValeur() + quantite));
		}
	}	
	public void retirerChocolat(Chocolat choco, double quantite) {
		if (choco.equals(Chocolat.CHOCOLAT_BASSE)) {
			this.stockChocolat.put(choco, new Variable(acteur.getNom() + " stock chocolat basse qualité", acteur, this.getStockChocolat().get(Chocolat.CHOCOLAT_BASSE).getValeur() - quantite));
		}
		if (choco.equals(Chocolat.CHOCOLAT_MOYENNE)) {
			this.stockChocolat.put(choco, new Variable(acteur.getNom() + " stock chocolat moyenne qualité", acteur, this.getStockChocolat().get(Chocolat.CHOCOLAT_MOYENNE).getValeur() - quantite));
		}
		if (choco.equals(Chocolat.CHOCOLAT_HAUTE)) {
			this.stockChocolat.put(choco, new Variable(acteur.getNom() + " stock chocolat haute qualité", acteur, this.getStockChocolat().get(Chocolat.CHOCOLAT_HAUTE).getValeur() - quantite));
		}
		if (choco.equals(Chocolat.CHOCOLAT_MOYENNE_EQUITABLE)) {
			this.stockChocolat.put(choco, new Variable(acteur.getNom() + " stock chocolat moyenne qualité équitable", acteur, this.getStockChocolat().get(Chocolat.CHOCOLAT_MOYENNE_EQUITABLE).getValeur() - quantite));
		}
		if (choco.equals(Chocolat.CHOCOLAT_HAUTE_EQUITABLE)) {
			this.stockChocolat.put(choco, new Variable(acteur.getNom() + " stock chocolat haute qualité équitable", acteur, this.getStockChocolat().get(Chocolat.CHOCOLAT_HAUTE_EQUITABLE).getValeur() - quantite));
		}
	}	
	public void ajoutPate(Pate pate, double quantite) {
		if (pate.equals(Pate.PATE_BASSE)) {
			this.stockPate.put(pate, new Variable(acteur.getNom() + " stock pate basse qualité", acteur, this.getStockPate().get(Pate.PATE_BASSE).getValeur() + quantite));
		}
		if (pate.equals(Pate.PATE_MOYENNE)) {
			this.stockPate.put(pate, new Variable(acteur.getNom() + " stock pate moyenne qualité", acteur, this.getStockPate().get(Pate.PATE_MOYENNE).getValeur() + quantite));
		}
		if (pate.equals(Pate.PATE_HAUTE)) {
			this.stockPate.put(pate, new Variable(acteur.getNom() + " stock pate haute qualité", acteur, this.getStockPate().get(Pate.PATE_HAUTE).getValeur() + quantite));
		}
		if (pate.equals(Pate.PATE_MOYENNE_EQUITABLE)) {
			this.stockPate.put(pate, new Variable(acteur.getNom() + " stock pate moyenne qualité équitable", acteur, this.getStockPate().get(Pate.PATE_MOYENNE_EQUITABLE).getValeur() + quantite));
		}
		if (pate.equals(Pate.PATE_HAUTE_EQUITABLE)) {
			this.stockPate.put(pate, new Variable(acteur.getNom() + " stock pate haute qualité équitable", acteur, this.getStockPate().get(Pate.PATE_HAUTE_EQUITABLE).getValeur() + quantite));
		}
	}
	public void ajoutPate(Pate pate, double quantite) {
		if (pate.equals(Pate.PATE_BASSE)) {
			this.stockPate.put(pate, new Variable(acteur.getNom() + " stock pate basse qualité", acteur, this.getStockPate().get(Pate.PATE_BASSE).getValeur() - quantite));
		}
		if (pate.equals(Pate.PATE_MOYENNE)) {
			this.stockPate.put(pate, new Variable(acteur.getNom() + " stock pate moyenne qualité", acteur, this.getStockPate().get(Pate.PATE_MOYENNE).getValeur() - quantite));
		}
		if (pate.equals(Pate.PATE_HAUTE)) {
			this.stockPate.put(pate, new Variable(acteur.getNom() + " stock pate haute qualité", acteur, this.getStockPate().get(Pate.PATE_HAUTE).getValeur() - quantite));
		}
		if (pate.equals(Pate.PATE_MOYENNE_EQUITABLE)) {
			this.stockPate.put(pate, new Variable(acteur.getNom() + " stock pate moyenne qualité équitable", acteur, this.getStockPate().get(Pate.PATE_MOYENNE_EQUITABLE).getValeur() - quantite));
		}
		if (pate.equals(Pate.PATE_HAUTE_EQUITABLE)) {
			this.stockPate.put(pate, new Variable(acteur.getNom() + " stock pate haute qualité équitable", acteur, this.getStockPate().get(Pate.PATE_HAUTE_EQUITABLE).getValeur() - quantite));
		}
	}
		
}
	
	
