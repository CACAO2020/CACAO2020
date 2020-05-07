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

	/** Les couples de variables donne la quantité et le prix à la tonne associé*/
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
		this.stockFeves.get(Feve.FEVE_BASSE)
				.add(new Couple<Variable>(new Variable(acteur.getNom() + "Stock", acteur, 50),
						new Variable(acteur.getNom() + "Prix", acteur, 0)));
		this.stockFeves.put(Feve.FEVE_MOYENNE, new ArrayList<Couple<Variable>>());
		this.stockFeves.put(Feve.FEVE_HAUTE, new ArrayList<Couple<Variable>>());
		this.stockFeves.put(Feve.FEVE_MOYENNE_EQUITABLE, new ArrayList<Couple<Variable>>());
		this.stockFeves.put(Feve.FEVE_HAUTE_EQUITABLE, new ArrayList<Couple<Variable>>());
		this.stockChocolat.put(Chocolat.CHOCOLAT_BASSE, new ArrayList<Couple<Variable>>());
		this.stockChocolat.get(Chocolat.CHOCOLAT_BASSE)
				.add(new Couple<Variable>(new Variable(acteur.getNom() + "Stock", acteur, 50),
						new Variable(acteur.getNom() + "Prix", acteur, 0)));
		this.stockChocolat.put(Chocolat.CHOCOLAT_MOYENNE, new ArrayList<Couple<Variable>>());
		this.stockChocolat.put(Chocolat.CHOCOLAT_HAUTE, new ArrayList<Couple<Variable>>());
		this.stockChocolat.put(Chocolat.CHOCOLAT_MOYENNE_EQUITABLE, new ArrayList<Couple<Variable>>());
		this.stockChocolat.put(Chocolat.CHOCOLAT_HAUTE_EQUITABLE, new ArrayList<Couple<Variable>>());
		this.stockPate.put(Pate.PATE_BASSE, new ArrayList<Couple<Variable>>());
		this.stockPate.put(Pate.PATE_MOYENNE, new ArrayList<Couple<Variable>>());

	}

	public Map<Feve, List<Couple<Variable>>> getStockFeves() {
		return this.stockFeves;
	}

	public Map<Chocolat, List<Couple<Variable>>> getStockChocolat() {
		return this.stockChocolat;
	}

	public Map<Pate, List<Couple<Variable>>> getStockPate() {
		return this.stockPate;
	}

	public double getQuantitéFeves(Feve feve) {
		List<Couple<Variable>> table = this.getStockFeves().get(feve);
		double somme = 0;
		for (int i = 0; i < table.size(); i++) {
			somme = somme + table.get(i).get1().getValeur();
		}
		return somme;
	}

	public double getQuantitéChocolat(Chocolat choco) {
		List<Couple<Variable>> table = this.getStockChocolat().get(choco);
		double somme = 0;
		for (int i = 0; i < table.size(); i++) {
			somme = somme + table.get(i).get1().getValeur();
		}
		return somme;
	}

	public double getQuantitéPate(Pate pate) {
		List<Couple<Variable>> table = this.getStockPate().get(pate);
		double somme = 0;
		for (int i = 0; i < table.size(); i++) {
			somme = somme + table.get(i).get1().getValeur();
		}
		return somme;
	}

	public double getQuantitePrixFeve(Feve feve, double prix) {
		List<Couple<Variable>> table = this.getStockFeves().get(feve);
		double somme = 0;
		for (int i = 0; i < table.size(); i++) {
			if (table.get(i).get2().getValeur() == prix) {
				somme = somme + table.get(i).get1().getValeur();
			}
		}
		return somme;
	}

	public double getQuantitePrixChocolat(Chocolat choco, double prix) {
		List<Couple<Variable>> table = this.getStockChocolat().get(choco);
		double somme = 0;
		for (int i = 0; i < table.size(); i++) {
			if (table.get(i).get2().getValeur() == prix) {
				somme = somme + table.get(i).get1().getValeur();
			}
		}
		return somme;
	}

	public double getQuantitePrixPate(Pate pate, double prix) {
		List<Couple<Variable>> table = this.getStockPate().get(pate);
		double somme = 0;
		for (int i = 0; i < table.size(); i++) {
			if (table.get(i).get2().getValeur() == prix) {
				somme = somme + table.get(i).get1().getValeur();
			}
		}
		return somme;
	}

	public void ajoutFeves(Feve feve, double quantite, double prix) {
		if (quantite > 0) {
			this.stockFeves.get(feve)
					.add(new Couple<Variable>(new Variable(acteur.getNom() + "Stock", acteur, quantite),
							new Variable(acteur.getNom() + "Prix", acteur, prix)));
		} else {
			throw new IllegalArgumentException("Quantité négative");
		}
	}

	public void retirerFevesPrix(Feve feve, double quantite, double prix) {
		List<Couple<Variable>> table = this.stockFeves.get(feve);
		double quantiteAEnlever = quantite;
		int i = 0;
		if (getQuantitePrixFeve(feve, prix) >= quantite) {
			while (quantiteAEnlever != 0) {
				if (table.get(i).get2().getValeur() == prix) {
					if (table.get(i).get1().getValeur() >= quantiteAEnlever) {
						table.get(i).get1().retirer(this.acteur, quantiteAEnlever);
						quantiteAEnlever = 0;
					} else {
						quantiteAEnlever -= table.get(i).get1().getValeur();
						table.get(i).get1().retirer(this.acteur, table.get(i).get1().getValeur());
					}
				}
			}
		} else {
			throw new IllegalArgumentException("Quantite trop importante");
		}

	}

	public void retirerPatePrix(Pate pate, double quantite, double prix) {
		List<Couple<Variable>> table = this.stockPate.get(pate);
		double quantiteAEnlever = quantite;
		int i = 0;
		if (getQuantitePrixPate(pate, prix) >= quantite) {
			while (quantiteAEnlever != 0) {
				if (table.get(i).get2().getValeur() == prix) {
					if (table.get(i).get1().getValeur() >= quantiteAEnlever) {
						table.get(i).get1().retirer(this.acteur, quantiteAEnlever);
						quantiteAEnlever = 0;
					} else {
						quantiteAEnlever -= table.get(i).get1().getValeur();
						table.get(i).get1().retirer(this.acteur, table.get(i).get1().getValeur());
					}
				}
			}
		} else {
			throw new IllegalArgumentException("Quantite trop importante");
		}

	}

	public void retirerChocolatPrix(Chocolat choco, double quantite, double prix) {
		List<Couple<Variable>> table = this.stockChocolat.get(choco);
		double quantiteAEnlever = quantite;
		int i = 0;
		if (getQuantitePrixChocolat(choco, prix) >= quantite) {
			while (quantiteAEnlever != 0) {
				if (table.get(i).get2().getValeur() == prix) {
					if (table.get(i).get1().getValeur() >= quantiteAEnlever) {
						table.get(i).get1().retirer(this.acteur, quantiteAEnlever);
						quantiteAEnlever = 0;
					} else {
						quantiteAEnlever -= table.get(i).get1().getValeur();
						table.get(i).get1().retirer(this.acteur, table.get(i).get1().getValeur());
					}
				}
			}
		} else {
			throw new IllegalArgumentException("Quantite trop importante");
		}

	}

	public void ajoutChocolat(Chocolat choco, double quantite, double prix) {
		if (quantite > 0) {
			this.stockChocolat.get(choco)
					.add(new Couple<Variable>(new Variable(acteur.getNom() + "Stock", acteur, quantite),
							new Variable(acteur.getNom() + "Prix", acteur, prix)));
		} else {
			throw new IllegalArgumentException("Quantité négative");
		}
	}

	public void ajoutPate(Pate pate, double quantite, double prix) {
		if (quantite > 0) {
			this.stockPate.get(pate).add(new Couple<Variable>(new Variable(acteur.getNom() + "Stock", acteur, quantite),
					new Variable(acteur.getNom() + "Prix", acteur, prix)));
		} else {
			throw new IllegalArgumentException("Quantité négative");
		}
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