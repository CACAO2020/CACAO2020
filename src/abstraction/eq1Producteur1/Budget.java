package abstraction.eq1Producteur1;

import java.util.ArrayList;

/*
 * Cree par Thalie
 */

/*
 * Donnees :
 * On paie un employe (recolteur, pas de diversification des employes pour l'instant)
 * 65 000 francs CFA par mois, ce qui correspond a 100 euros.
 * Un nouveau plant coute 200 francs CFA (0.3), on compte 1 111 plants par hectares
 * et chaque recolteur s'occupe de 2 hectares.
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
 * ArrayList<Integer> cyclique(int, double, double)
 */



public class Budget {
	private double fonds;
	private int employes;
	
	public double getFonds() {
		return this.fonds;
	}
	
	public int getEmployes() {
		return this.employes;
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
		int nb_hectares = (int) nb_arbres/1111;
		int parite = nb_hectares - ((int) nb_hectares/2);
		double coutArbresHectare = 333.33;
		int newHectares = 0;
		while (somme>(coutArbresHectare + (1-parite)*50)) {
			newHectares += 1;
			if (parite==0) {
				somme = somme - coutArbresHectare - 50;
				parite = 1;
			} else {
				somme = somme - coutArbresHectare;
				parite = 0;
			}
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
		
	
	

	public ArrayList<Integer> cyclique(int nb_arbres, double quantiteVendueF, double quantiteVendueT) {
		if (this.getFonds()>this.getEmployes()*50) {
			this.removeFonds(this.getEmployes()*50);
		} else {
			int max_employes = (int) ((int) this.getFonds()/50);
			this.setEmployes(max_employes);
			this.removeFonds(this.getEmployes()*50);
		}
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
