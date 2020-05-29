package abstraction.eq1Producteur1;

import java.util.ArrayList;

import abstraction.fourni.Variable;

/*
 * Cree par Thalie
 */

/*
 * Donnees :
 * On paie un employe (recolteur, pas de diversification des employes pour l'instant)
 * 65 000 francs CFA par mois, ce qui correspond a 100 euros.
 * Un nouveau plant coute 200 francs CFA (0.3), on compte 500 plants par récolteur.
 * On ne prend pas en compte le terrain lors de la plantation de nouveaux arbres.
 */


/*
 * Programmes disponibles :
 * double getFonds()
 * int getEmployes()
 * 
 * double setFonds(double)
 * double setEmployes(int)
 * double addFonds(double)
 * double removeFonds(double)
 * int addEmployes(int)
 * int removeEmployes(int)
 * 
 * ArrayList<Integer> investissement(double, int, double, double)
 * ArrayList<Integer> budget_cyclique(int, double, double)
 */



public class Budget {
	private double fonds;
	private int employes;
	private Variable oldstockF;
	private Variable newstockF;
	private Variable oldstockT;
	private Variable newstockT;
	
	
	public double getFonds() {
		return this.fonds;
	}
	
	public int getEmployes() {
		return this.employes;
	}
	
	public Variable getOldStockF() {
		return this.oldstockF;
	}

	public Variable getOldStockT() {
		return this.oldstockT;
	}
	
	public Variable getNewStockF() {
		return this.oldstockF;
	}

	public Variable getNewStockT() {
		return this.oldstockT;
	}
	
	public void setOldStockF(Variable s) {
		this.oldstockF=s;
	}
	
	public void setOldStockT(Variable s) {
		this.oldstockF=s;
	}
	
	public void setNewStockF(Variable s) {
		this.oldstockF=s;
	}
	
	public void setNewStockT(Variable s) {
		this.oldstockF=s;
	}
	
	public void setFonds(double n_fonds) {
		this.fonds = n_fonds;
	}
	
	public void setEmployes(int n_employes) {
		this.employes = n_employes;
	}
	
	public void addFonds(double ajout) {
		this.setFonds(this.getFonds() + ajout);
	}
	
	public void removeFonds(double retrait) {
		this.setFonds(this.getFonds() - retrait);
	}
	
	public void addEmployes(int ajout) {
		this.setEmployes(this.getEmployes() + ajout);
	}
	
	public void removeEmployes(int retrait) {
		this.setEmployes(this.getEmployes() - retrait);
	}


	public ArrayList<Integer> investissement(double somme, int nb_arbres, double quantiteVendueF, double quantiteVendueT) {
		double coutArbresEmploye = 152.18;
		int newHectares = 0;
		while (somme>(coutArbresEmploye + 50)) {
			newHectares += 1;
			somme = somme - coutArbresEmploye - 50;
			}
		double quantiteVendue = quantiteVendueF + quantiteVendueT;
		double proportionT = quantiteVendueT/quantiteVendue;
		int newHectaresT = (int) proportionT*newHectares;
		int newHectaresF = newHectares - newHectaresT;
		ArrayList<Integer> newPlants = new ArrayList<Integer>();
		newPlants.add(newHectaresF);
		newPlants.add(newHectaresT);
		return newPlants;
	}
		
	
	

	public ArrayList<Integer> budget_cyclique(int nb_arbres, Variable stockF, Variable stockT) {
		if (this.getFonds()>this.getEmployes()*50) {
			this.removeFonds(this.getEmployes()*50);
		} else {
			int max_employes = (int) ((int) this.getFonds()/50);
			this.setEmployes(max_employes);
			this.removeFonds(this.getEmployes()*50);
		}
		this.setOldStockF(this.getNewStockF());
		this.setOldStockT(this.getNewStockT());
		this.setNewStockF(stockF);
		this.setNewStockT(stockT);
		double quantiteVendueF = this.getNewStockF().getValeur() - this.getOldStockF().getValeur();
		double quantiteVendueT = this.getNewStockT().getValeur() - this.getOldStockT().getValeur();
		ArrayList<Integer> newPlants = new ArrayList<Integer>();
		if (this.getFonds()>this.getEmployes()*25) {
			newPlants = investissement((this.getFonds()-this.getEmployes()*25), nb_arbres, quantiteVendueF, quantiteVendueT);
		} else {
			newPlants.add((Integer) 0);
			newPlants.add((Integer) 0);
		}
		return newPlants;
	}

}
