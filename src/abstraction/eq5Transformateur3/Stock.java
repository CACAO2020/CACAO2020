package abstraction.eq5Transformateur3;

import java.util.HashMap;

import java.util.Map;

import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.Feve;
import abstraction.eq8Romu.produits.Pate;
import abstraction.fourni.Filiere;
import abstraction.fourni.Variable;
import java.util.List;
import java.util.ArrayList;

/** @author F. GOUJON
 * Gère les stock de fève, pate et chocolat du Transformateur3
* Elle est agrégé dans la classe Transformateur3.
*/
public class Stock {
	// Les couples de variables donne la quantité et le prix à la tonne associé
	private Transformateur3 acteur;
	private Map<Feve, List<Couple<Variable>>> stockFeves;
	private Map<Chocolat, List<Couple<Variable>>> stockChocolat;
	private Map<Pate, List<Couple<Variable>>> stockPate;
	private Variable transformationCostFeve;
	private Variable transformationCostPate;
	private Variable stockCostFixe;
	private Variable stockCostVar;
	private List<Variable> indicateurs;

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
		this.transformationCostFeve = new Variable(acteur.getNom() + "Cout transformation feve à chocolat", acteur, 10000);
		this.transformationCostPate = new Variable(acteur.getNom() + "Cout transformation pate à chocolat", acteur, 4000);
		this.stockCostFixe = new Variable(acteur.getNom() + "Cout fixe du stockage", acteur, 1000);
		this.stockCostVar = new Variable(acteur.getNom() + "Cout Variable du stockage", acteur, 100);
		this.indicateurs = new ArrayList<Variable>();
		
		this.indicateurs.add(0,new Variable(acteur.getNom() + "Stock fève basse qualité", acteur, 50));
		this.indicateurs.add(1,new Variable(acteur.getNom() + "Stock fève moyenne qualité", acteur, 0));
		this.indicateurs.add(2,new Variable(acteur.getNom() + "Stock fève haute qualité", acteur, 0));
		this.indicateurs.add(3,new Variable(acteur.getNom() + "Stock fève moyenne qualité equitable", acteur, 0));
		this.indicateurs.add(4,new Variable(acteur.getNom() + "Stock fève haute qualité équitable", acteur, 0));
		this.indicateurs.add(5,new Variable(acteur.getNom() + "Stock chocolat basse qualité", acteur, 50));
		this.indicateurs.add(6,new Variable(acteur.getNom() + "Stock chocolat moyenne qualité", acteur, 0));
		this.indicateurs.add(7,new Variable(acteur.getNom() + "Stock chocolat haute qualité", acteur, 0));
		this.indicateurs.add(8,new Variable(acteur.getNom() + "Stock chocolat moyenne qualité equitable", acteur, 0));
		this.indicateurs.add(9,new Variable(acteur.getNom() + "Stock chocolat haute qualité équitable", acteur, 0));
		this.indicateurs.add(10,new Variable(acteur.getNom() + "Stock pate basse qualité", acteur, 0));
		this.indicateurs.add(11,new Variable(acteur.getNom() + "Stock pate moyenne qualité", acteur, 0));


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
	public Variable getTransformationCostFeve() {
		return this.transformationCostFeve;
	}
	public Variable getTransformationCostPate() {
		return this.transformationCostPate;
	}

	public double getQuantiteFeves(Feve feve) {
		List<Couple<Variable>> table = this.getStockFeves().get(feve);
		double somme = 0;
		for (int i = 0; i < table.size(); i++) {
			somme = somme + table.get(i).get1().getValeur();
		}
		return somme;
	}

	public double getQuantiteChocolat(Chocolat choco) {
		List<Couple<Variable>> table = this.getStockChocolat().get(choco);
		double somme = 0;
		for (int i = 0; i < table.size(); i++) {
			somme = somme + table.get(i).get1().getValeur();
		}
		return somme;
	}

	/** 
	 * Overload to support Chocolat with a Feve in argument
	 */
	public double getQuantiteChocolat(Feve feve) {
		return this.getQuantiteChocolat(Stock.getProduct(feve));
	}
	
	public double getQuantitePate(Pate pate) {
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
			while (quantiteAEnlever != 0 && i<table.size()) {
				if (table.get(i).get2().getValeur() == prix) {
					if (table.get(i).get1().getValeur() >= quantiteAEnlever) {
						table.get(i).get1().retirer(this.acteur, quantiteAEnlever);
						quantiteAEnlever = 0;
					} else {
						quantiteAEnlever -= table.get(i).get1().getValeur();
						table.get(i).get1().retirer(this.acteur, table.get(i).get1().getValeur());
					}
				}
				this.majStockFeves(feve);
				i += 1;
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
			while (quantiteAEnlever != 0 && i<table.size()) {
				if (table.get(i).get2().getValeur() == prix) {
					if (table.get(i).get1().getValeur() >= quantiteAEnlever) {
						table.get(i).get1().retirer(this.acteur, quantiteAEnlever);
						quantiteAEnlever = 0;
					} else {
						quantiteAEnlever -= table.get(i).get1().getValeur();
						table.get(i).get1().retirer(this.acteur, table.get(i).get1().getValeur());
					}
				}
				this.majStockPate(pate);
				i += 1;
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
			while (quantiteAEnlever != 0 && i<table.size()) {
				if (table.get(i).get2().getValeur() == prix) {
					if (table.get(i).get1().getValeur() >= quantiteAEnlever) {
						table.get(i).get1().retirer(this.acteur, quantiteAEnlever);
						quantiteAEnlever = 0;
					} else {
						quantiteAEnlever -= table.get(i).get1().getValeur();
						table.get(i).get1().retirer(this.acteur, table.get(i).get1().getValeur());
					}
				}
				this.majStockChocolat(choco);
				i += 1;
			}
		} else {
			throw new IllegalArgumentException("Quantite trop importante");
		}
	
	}
	public double prixMaxStock(List<Couple<Variable>> table) {
		if (table.isEmpty()) {
			return 0;
		} else {
			double m = 0;
			for (Couple<Variable> infos : table) {
				if (infos.get2().getValeur() > m ) {
					m = infos.get2().getValeur();
				}
			}
			return m;
		}
	}
	public void retirerChocolat(Chocolat choco, double quantite) {
		this.majStockChocolat(choco);
		List<Couple<Variable>> table = this.stockChocolat.get(choco);
		double quantiteAEnlever = quantite;
		if (getQuantiteChocolat(choco) >= quantite) {
			while (quantiteAEnlever != 0) {
				double prix = this.prixMaxStock(table);
				double quantiteprix = this.getQuantitePrixChocolat(choco, prix);
				if (quantiteprix >= quantiteAEnlever) {
					this.retirerChocolatPrix(choco, quantiteAEnlever, prix);
				} else {
					this.retirerChocolatPrix(choco, quantiteprix, prix);
					quantiteAEnlever -= quantiteprix;
				}
				this.majStockChocolat(choco);
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
	public void majStockFeves(Feve feve) {
		List<Couple<Variable>> table = this.getStockFeves().get(feve);
		table.removeIf(c -> c.get1().getValeur()==0);
	}
	public void majStockPate(Pate pate) {
		List<Couple<Variable>> table = this.getStockPate().get(pate);
		table.removeIf(c -> c.get1().getValeur()==0);
	}
	
	public void majStockChocolat(Chocolat choco) {
		List<Couple<Variable>> table = this.getStockChocolat().get(choco);
		table.removeIf(c -> c.get1().getValeur() == 0);
	}
	
	/**
	 * Fonction appelé lorsque le prochain tour arrive
	 * Elle transforme les fèves et la pate automatiquement en chocolat
	 * On notera que pour l'instant tout le stock est instantanement transformer
	 * Paramètres transformationCost
	 */
	public void next() {
		for (Feve feve : Feve.values()) {
			for (Couple<Variable> feveInfos : this.stockFeves.get(feve)) {
				this.stockChocolat.get(Stock.getProduct(feve)).add(feveInfos);
				feveInfos.get2().ajouter(acteur, this.transformationCostFeve.getValeur());
				this.acteur.getTresorier().diminueTresorerie(this.transformationCostFeve.getValeur() * feveInfos.get1().getValeur());
			}
			//le stock est transformé donc la matière première est supprimée
			this.stockFeves.get(feve).clear();
		}
		for (Pate pate : Pate.values()) {
			for (Couple<Variable> pateInfos : this.stockPate.get(pate)) {
				this.stockChocolat.get(Stock.getProduct(pate)).add(pateInfos);
				pateInfos.get2().ajouter(acteur, this.transformationCostPate.getValeur());
				this.acteur.getTresorier()
						.diminueTresorerie(this.transformationCostPate.getValeur() * pateInfos.get1().getValeur());
			}
			this.stockPate.get(pate).clear();
		}
		this.updateIndicateurs();
		this.stockCosts();
	}

	/**
	 * permet de calculer le prix du stock et de perdre l'argent correspondant
	 */
	private void stockCosts() {
		double tailleDuStock = 0;
		for(Feve feve : Feve.values()){
			tailleDuStock += this.getQuantiteChocolat(feve) + this.getQuantiteFeves(feve);
		}
		for(Pate pate : Pate.values()){
			tailleDuStock += this.getQuantitePate(pate);
		}
		double prixDuStock = tailleDuStock * this.getStockCostVar().getValeur() + this.getStockCostFixe().getValeur();
		Filiere.LA_FILIERE.getBanque().virer(this.acteur, this.acteur.getCryptogramme(), Filiere.LA_FILIERE.getBanque(), prixDuStock);
	}

	// Indicateurs : quantité de stock pour chaque chocolat et fèves
	public List<Variable> getIndicateurs() {
		return this.indicateurs;
	}

	private void updateIndicateurs() {
		this.getIndicateurs().get(0).setValeur(acteur, this.getQuantiteFeves(Feve.FEVE_BASSE));
		this.getIndicateurs().get(1).setValeur(acteur, this.getQuantiteFeves(Feve.FEVE_MOYENNE));
		this.getIndicateurs().get(2).setValeur(acteur, this.getQuantiteFeves(Feve.FEVE_HAUTE));
		this.getIndicateurs().get(3).setValeur(acteur, this.getQuantiteFeves(Feve.FEVE_MOYENNE_EQUITABLE));
		this.getIndicateurs().get(4).setValeur(acteur, this.getQuantiteFeves(Feve.FEVE_HAUTE_EQUITABLE));
		this.getIndicateurs().get(5).setValeur(acteur, this.getQuantiteChocolat(Chocolat.CHOCOLAT_BASSE));
		this.getIndicateurs().get(6).setValeur(acteur, this.getQuantiteChocolat(Chocolat.CHOCOLAT_MOYENNE));
		this.getIndicateurs().get(7).setValeur(acteur, this.getQuantiteChocolat(Chocolat.CHOCOLAT_HAUTE));
		this.getIndicateurs().get(8).setValeur(acteur, this.getQuantiteChocolat(Chocolat.CHOCOLAT_MOYENNE_EQUITABLE));
		this.getIndicateurs().get(10).setValeur(acteur, this.getQuantitePate(Pate.PATE_BASSE));
		this.getIndicateurs().get(11).setValeur(acteur, this.getQuantitePate(Pate.PATE_MOYENNE));
		
	}

	/**
	 * Fonction pour faire l'association entre fève et chocolat produit de même pour
	 * pate en surcharge
	 * 
	 * @return Chocolat produit par la fève associée
	 */
	public static Chocolat getProduct(Feve feve) {
		switch (feve) {
			case FEVE_BASSE:
				return Chocolat.CHOCOLAT_BASSE;
			case FEVE_MOYENNE:
				return Chocolat.CHOCOLAT_MOYENNE;
			case FEVE_MOYENNE_EQUITABLE:
				return Chocolat.CHOCOLAT_MOYENNE_EQUITABLE;
			case FEVE_HAUTE:
				return Chocolat.CHOCOLAT_HAUTE;
			default:
				return Chocolat.CHOCOLAT_HAUTE_EQUITABLE;
		}
	}

	public static Chocolat getProduct(Pate pate) {
		switch (pate) {
			case PATE_BASSE:
				return Chocolat.CHOCOLAT_BASSE;
			default:
				return Chocolat.CHOCOLAT_MOYENNE;
		}
	}

	public Variable getStockCostFixe() {
		return stockCostFixe;
	}

	public Variable getStockCostVar() {
		return stockCostVar;
	}

}




