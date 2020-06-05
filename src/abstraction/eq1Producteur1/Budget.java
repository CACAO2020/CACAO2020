package abstraction.eq1Producteur1;

import java.util.ArrayList;

import abstraction.fourni.Banque;
import abstraction.fourni.Filiere;


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
 * double getOldStockF()
 * double getOldSTockT()
 * double getNewStockF()
 * double getNewStockT()
 * 
 * void setFonds(double)
 * void setEmployes(int)
 * void setOldStockF(double)
 * void setOldStockT(double)
 * void setNewStockF(double)
 * void setNewStockT(double)
 * 
 * double addFonds(double)
 * double removeFonds(double)
 * int addEmployes(int)
 * int removeEmployes(int)
 * 
 * ArrayList<Integer> investissement(double, double, double)
 * ArrayList<Integer> budget_cyclique(double, double)
 */



public class Budget {
	private double fonds;
	private int employes;
	private double oldstockF;
	private double newstockF;
	private double oldstockT;
	private double newstockT;
	
	public Budget(double f, int e, double osf, double ost, double nsf, double nst) {
		this.fonds = f;
		this.employes = e;
		this.oldstockF = osf;
		this.oldstockT = ost;
		this.newstockF = nsf;
		this.newstockT = nst;
	}
	
	public double getFonds() {
		return this.getFonds();
	}
	
	public int getEmployes() {
		return this.employes;
	}
	
	public double getOldStockF() {
		return this.oldstockF;
	}

	public double getOldStockT() {
		return this.oldstockT;
	}
	
	public double getNewStockF() {
		return this.oldstockF;
	}

	public double getNewStockT() {
		return this.oldstockT;
	}
	
	public void setOldStockF(double s) {
		this.oldstockF=s;
	}
	
	public void setOldStockT(double s) {
		this.oldstockF=s;
	}
	
	public void setNewStockF(double s) {
		this.oldstockF=s;
	}
	
	public void setNewStockT(double s) {
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


	public ArrayList<Integer> investissement(double somme, double quantiteVendueF, double quantiteVendueT) {
		double coutArbresEmploye = 152.18;
		int newEmployes = 0;
		while (somme>(coutArbresEmploye + 50)) {
			newEmployes += 1;
			somme = somme - coutArbresEmploye - 50;
			}
		double quantiteVendue = quantiteVendueF + quantiteVendueT;
		double proportionT = quantiteVendueT/quantiteVendue;
		double proportionF = quantiteVendueF/quantiteVendue;
		if (proportionF<0) {
			proportionF = 0;
		}
		if (proportionT<0) {
			proportionT = 0;
		}
		int newArbresT = (int) proportionT*newEmployes;
		int newArbresF = (int) proportionF*newEmployes;
		if (newArbresT+newArbresF == 0) {
			newEmployes = 0;
		}
		ArrayList<Integer> newPlants = new ArrayList<Integer>();
		newPlants.add(newArbresF*500);
		newPlants.add(newArbresT*500);
		newPlants.add(newEmployes);
		return newPlants;
	}
		
	
	

	public ArrayList<Integer> budget_cyclique(double fonds, double stockF, double stockT) {
		this.setFonds(fonds);
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
		ArrayList<Integer> newPlants = new ArrayList<Integer>();
		if (this.getFonds()>this.getEmployes()*50) {
			newPlants = investissement((this.getFonds()-this.getEmployes()*50), (this.getNewStockF()-this.getOldStockF()), this.getNewStockT()-this.getOldStockT());
		} else {
			newPlants.add((Integer) 0);
			newPlants.add((Integer) 0);
			newPlants.add((Integer) 0);
		}
		this.setEmployes(this.getEmployes()+newPlants.get(2));
		this.removeFonds(202.18*newPlants.get(2));;
		newPlants.add((int) 202.18*newPlants.get(2));
		return newPlants;
	}

}
