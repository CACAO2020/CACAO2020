package abstraction.eq1Producteur1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import abstraction.eq8Romu.cacaoCriee.PropositionCriee;
import abstraction.fourni.Banque;
import abstraction.fourni.Filiere;


/*
 * Cree par Thalie
 */

/*
 * Donnees :
 * On paie un employe (recolteur, pas de diversification des employes pour l'instant)
 * 65 000 francs CFA par mois, ce qui correspond a 100 euros.
 * Chaque récolteur est embauché pour une durée de un an (24 cycles).
 * Un nouveau plant coute 200 francs CFA (0.3), on compte 500 plants par récolteur.
 * On ne prend pas en compte le terrain lors de la plantation de nouveaux arbres.
 * Pour investir dans de nouvelles plantations, on s'assurera d'avoir suffisamment
 * d'argent pour payer les récolteurs que l'on a déjà pendant un an (choix arbitraire).
 */


/*
 * Programmes disponibles :
 * double getFonds()
 * ArrayList<Integer> getEmployes()
 * double getOldStockF()
 * double getOldSTockT()
 * double getNewStockF()
 * double getNewStockT()
 * 
 * void setFonds(double)
 * void setOldStockF(double)
 * void setOldStockT(double)
 * void setNewStockF(double)
 * void setNewStockT(double)
 * 
 * double addFonds(double)
 * double removeFonds(double)
 * void addEmployes(int)
 * void actualiserEmployes()
 * 
 * ArrayList<Integer> investissement(double, double, double)
 * ArrayList<Integer> budget_cyclique(double, double)
 */



public class Budget {
	private double fonds;
	private ArrayList<Integer> employes;
	private double oldstockF;
	private double newstockF;
	private double oldstockT;
	private double newstockT;
	
	public Budget(double f, int e, double osf, double ost, double nsf, double nst) {
		this.fonds = f;
		this.employes = initialiserEmployes(e);
		this.oldstockF = osf;
		this.oldstockT = ost;
		this.newstockF = nsf;
		this.newstockT = nst;
	}
	
	public double getFonds() {
		return this.fonds;
	}
	
	public ArrayList<Integer> getEmployes() {
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
		this.newstockF=s;
	}
	
	public void setNewStockT(double s) {
		this.newstockF=s;
	}
	
	public void setFonds(double n_fonds) {
		this.fonds = n_fonds;
	}
	
	
	public void addFonds(double ajout) {
		this.setFonds(this.getFonds() + ajout);
	}
	
	public void removeFonds(double retrait) {
		this.setFonds(this.getFonds() - retrait);
	}
	
	public void setEmployes(ArrayList<Integer> l) {
		this.employes = l;
	}
	
	public void addEmployes(int i) {
		for (int k=0; k<i; k++) {
			this.employes.add((Integer) 0);
		}
	}
	
	public void actualiserEmployes() {
		ArrayList<Integer> l = this.getEmployes();
		for (int i=0; i<l.size(); i++) {
			int employe = this.getEmployes().get(0) + 1;
			if (employe==24) {
				this.employes.remove(0);
			}
		}
	}
	
	public ArrayList<Integer> initialiserEmployes(int i) {
		Random rand = new Random();
		ArrayList<Integer> l = new ArrayList<Integer>();
		for (int k=0; k<i; k++) {
			int cycle = (int) rand.nextInt(24);
			l.add((Integer) cycle);
		}
		Collections.sort(l);
		Collections.reverse(l);
		return l;
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
		
	
	

	public ArrayList<Integer> budget_cyclique(double fonds, ArrayList<PropositionCriee> feves) {
		this.setFonds(fonds);
		this.removeFonds(this.getEmployes().size()*50);
		this.actualiserEmployes();
		
		
		ArrayList<Integer> newPlants = new ArrayList<Integer>();
		if (this.getFonds()>this.getEmployes().size()*50*24) {
			newPlants = investissement((this.getFonds()-this.getEmployes().size()*50*24), (this.getNewStockF()-this.getOldStockF()), this.getNewStockT()-this.getOldStockT());
		} else {
			newPlants.add((Integer) 0);
			newPlants.add((Integer) 0);
			newPlants.add((Integer) 0);
		}
		this.addEmployes(newPlants.get(2));
		newPlants.add((int) 202.18*newPlants.get(2));
		return newPlants;
	}

}
