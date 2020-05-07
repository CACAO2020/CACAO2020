package abstraction.eq5Transformateur3;

import java.util.HashMap;

import java.util.Map;

import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.Feve;
import abstraction.eq8Romu.produits.Pate;
import abstraction.fourni.Variable;
import java.util.List;
import java.util.ArrayList;

/** @author F. GOUJON  */
public class Stock {
	/**
     * Gère les stock de fève, pate et chocolat du Transformateur3
     * Elle est agrégé dans la classe Transformateur3.
     */
	
	private Transformateur3 acteur;
	private Map<Feve, List<Couple<Variable>>> stockFeves;
	private Map<Chocolat, List<Couple<Variable>>> stockChocolat;
	private Map<Pate, List<Couple<Variable>>> stockPate;
	
	public Stock(Transformateur3 acteur) {
		this.acteur = acteur;
		this.stockChocolat = new HashMap<Chocolat, List<Couple<Variable>>>();
		this.stockFeves = new HashMap<Feve, List<Couple<Variable>>>();
		this.stockPate = new HashMap<Pate, List<Couple<Variable>>>();
		this.stockFeves.put(Feve.FEVE_BASSE, new ArrayList<Couple<Variable>>());
		this.stockFeves.get(Feve.FEVE_BASSE).add(new Couple<Variable>(new Variable(acteur.getNom() + "Stock", acteur, 50),
			new Variable(acteur.getNom() + "Prix", acteur, 0)));
		this.stockFeves.put(Feve.FEVE_MOYENNE, new ArrayList<Couple<Variable>>());
		this.stockFeves.get(Feve.FEVE_MOYENNE).add(new Couple<Variable>(new Variable(acteur.getNom() + "Stock", acteur, 0),
				new Variable(acteur.getNom() + "Prix", acteur, 0)));
		this.stockFeves.put(Feve.FEVE_HAUTE, new ArrayList<Couple<Variable>>());
		this.stockFeves.get(Feve.FEVE_HAUTE).add(new Couple<Variable>(new Variable(acteur.getNom() + "Stock", acteur, 0),
				new Variable(acteur.getNom() + "Prix", acteur, 0)));
		this.stockFeves.put(Feve.FEVE_MOYENNE_EQUITABLE, new ArrayList<Couple<Variable>>());
		this.stockFeves.get(Feve.FEVE_MOYENNE_EQUITABLE).add(new Couple<Variable>(new Variable(acteur.getNom() + "Stock", acteur, 0),
				new Variable(acteur.getNom() + "Prix", acteur, 0)));
		this.stockFeves.put(Feve.FEVE_HAUTE_EQUITABLE, new ArrayList<Couple<Variable>>());
		this.stockFeves.get(Feve.FEVE_HAUTE_EQUITABLE).add(new Couple<Variable>(new Variable(acteur.getNom() + "Stock", acteur, 0),
				new Variable(acteur.getNom() + "Prix", acteur, 0)));
		this.stockChocolat.put(Chocolat.CHOCOLAT_BASSE, new ArrayList<Couple<Variable>>());
		this.stockChocolat.put(Chocolat.CHOCOLAT_MOYENNE, new ArrayList<Couple<Variable>>());
		this.stockChocolat.put(Chocolat.CHOCOLAT_HAUTE, new ArrayList<Couple<Variable>>());
		this.stockChocolat.put(Chocolat.CHOCOLAT_MOYENNE_EQUITABLE, new ArrayList<Couple<Variable>>());
		this.stockChocolat.put(Chocolat.CHOCOLAT_HAUTE_EQUITABLE, new ArrayList<Couple<Variable>>());
		this.stockPate.put(Pate.PATE_BASSE, new ArrayList<Couple<Variable>>());
		this.stockPate.put(Pate.PATE_MOYENNE, new ArrayList<Couple<Variable>>());
		this.stockPate.put(Pate.PATE_HAUTE, new ArrayList<Couple<Variable>>());
		this.stockPate.put(Pate.PATE_MOYENNE_EQUITABLE, new ArrayList<Couple<Variable>>());
		this.stockPate.put(Pate.PATE_HAUTE_EQUITABLE, new ArrayList<Couple<Variable>>());
		
		
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
		this.stockChocolat.get(choco).retirer(acteur, quantite);
	}
	
	public void ajoutPate(Pate pate, double quantite) {
		this.stockPate.get(pate).ajouter(acteur, quantite);
	}	

	public void retirerPate(Pate pate, double quantite) {
		this.stockPate.get(pate).retirer(acteur, quantite);
	}
			
}
/*
this.stockFeves.put(Feve.FEVE_BASSE, new Couple((new Variable(acteur.getNom() + " stock feves basse qualité", acteur, 50)));
this.stockFeves.put(Feve.FEVE_MOYENNE, new Variable(acteur.getNom() + " stock feves moyenne qualité", acteur, 0));
this.stockFeves.put(Feve.FEVE_HAUTE, new Variable(acteur.getNom() + " stock feves haute qualité", acteur, 0));
this.stockFeves.put(Feve.FEVE_MOYENNE_EQUITABLE, new Variable(acteur.getNom() + " stock feves moyenne qualité équitable", acteur, 0));
this.stockFeves.put(Feve.FEVE_HAUTE_EQUITABLE, new Variable(acteur.getNom() + " stock feves haute qualité équitable", acteur, 0));
this.stockChocolat.put(Chocolat.CHOCOLAT_BASSE, new Variable(acteur.getNom() + " stock chocolat basse qualité", acteur, 50));
this.stockChocolat.put(Chocolat.CHOCOLAT_MOYENNE, new Variable(acteur.getNom() + " stock chocolat moyenne qualité", acteur, 0));
this.stockChocolat.put(Chocolat.CHOCOLAT_HAUTE, new Variable(acteur.getNom() + " stock chocolat haute qualité", acteur, 0));
this.stockChocolat.put(Chocolat.CHOCOLAT_MOYENNE_EQUITABLE, new Variable(acteur.getNom() + " stock chocolat moyenne qualité équitable", acteur, 0));
this.stockChocolat.put(Chocolat.CHOCOLAT_HAUTE_EQUITABLE, new Variable(acteur.getNom() + " stock chocolat haute qualité équitable", acteur, 0));
this.stockPate.put(Pate.PATE_BASSE, new Variable(acteur.getNom() + "stock pate basse qualité", acteur, 0));
this.stockPate.put(Pate.PATE_MOYENNE, new Variable(acteur.getNom() + "stock pate moyenne qualité", acteur, 0));
this.stockPate.put(Pate.PATE_HAUTE, new Variable(acteur.getNom() + "stock pate haute qualité", acteur, 0));
this.stockPate.put(Pate.PATE_MOYENNE_EQUITABLE, new Variable(acteur.getNom() + "stock pate moyenne qualité équitable", acteur, 0));
this.stockPate.put(Pate.PATE_HAUTE_EQUITABLE, new Variable(acteur.getNom() + "stock pate haute qualité équitable", acteur, 0));
	
*/