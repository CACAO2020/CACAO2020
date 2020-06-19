package abstraction.eq1Producteur1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import abstraction.eq8Romu.cacaoCriee.PropositionCriee;
import abstraction.eq8Romu.produits.Feve;
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
 * 
 * void setFonds(double)
 * double addFonds(double)
 * double removeFonds(double)
 * 
 * void setEmployes(ArrayList<Integer>)
 * void addEmployes(int)
 * ArrayList<Integer> initialiserEmployes(int)
 * void actualiserEmployes(boolean)
 * 
 * ArrayList<Double> venduesDernierement(ArrayList<PropositionCriee>)
 * ArrayList<Integer> investissement(double, double, double)
 * ArrayList<Integer> budget_cyclique(double, ArrayList<PropositionCriee>)
 */



public class Budget {
	private double fonds;
	private ArrayList<Integer> employes;
	
	public Budget(double f, int e) {
		this.fonds = f;
		this.employes = initialiserEmployes(e);
	}
	
	public double getFonds() {
		return this.fonds;
	}
	
	public ArrayList<Integer> getEmployes() {
		return this.employes;
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
	
	
	public void actualiserEmployes(boolean b) {
		ArrayList<Integer> l = this.getEmployes();
		boolean vieux = true;
		while (vieux) {
			int employe = l.get(0);
			if (employe==24) {
				l.remove(0);
				if (b) {
					l.add((Integer) 0);
				}
			} else {
				vieux = false;
			}
		}
		this.setEmployes(l);
	}
	
	
	public ArrayList<Double> venduesDernierement(ArrayList<PropositionCriee> l) {
		ArrayList<Double> ForTri = new ArrayList<Double>();
		if (l.isEmpty()) {
			ForTri.add((Double) 0.0);
			ForTri.add((Double) 0.0);
		} else {
			int index = 0;
			while ((index<l.size()) || (index<24)) {
				PropositionCriee lot = l.get(index);
				if (lot.getFeve()==Feve.FEVE_BASSE) {
					ForTri.set(0, ForTri.get(0) + lot.getQuantiteEnTonnes());
				} else if (lot.getFeve()==Feve.FEVE_MOYENNE) {
					ForTri.set(1, ForTri.get(1) + lot.getQuantiteEnTonnes());
				}
				index ++;
			}
		}
		return ForTri;
	}


	public ArrayList<Integer> investissement(double somme, double vendueF, double vendueT) {
		double coutArbresEmploye = 152.18;
		int newEmployes = 0;
		while (somme>(coutArbresEmploye + 50)) {
			newEmployes += 1;
			somme = somme - coutArbresEmploye - 50;
			}
		double quantiteVendue = vendueF + vendueT;
		double proportionT = vendueT/quantiteVendue;
		int newArbresT = (int) proportionT*newEmployes;
		int newArbresF = (int) newEmployes - newArbresT;
		ArrayList<Integer> newPlants = new ArrayList<Integer>();
		newPlants.add(newArbresF*500);
		newPlants.add(newArbresT*500);
		newPlants.add(newEmployes);
		return newPlants;
	}
		
	
	

	public ArrayList<Integer> budget_cyclique(double fonds, ArrayList<PropositionCriee> feves, double coutStockage) {
		this.setFonds(fonds);
		this.removeFonds(this.getEmployes().size()*50);
		boolean reengager = false;
		if (this.getFonds()>(this.getEmployes().size()*50 + coutStockage)*2) {
			reengager = true;
		} 
		this.actualiserEmployes(reengager);
		
		ArrayList<Integer> newPlants = new ArrayList<Integer>();
		if (Filiere.LA_FILIERE.getEtape()!=0) {
			ArrayList<Double> vendues = venduesDernierement(feves);
			if (this.getFonds()>(this.getEmployes().size()*50 + coutStockage)*48) {
				double fondsInvestis = 0.0;
				if (this.getFonds()-this.getEmployes().size()*50*24<=10000.0) {
					fondsInvestis = this.getFonds()-this.getEmployes().size()*50*24;
				} else {
					fondsInvestis = 10000.0;
				}
				newPlants = investissement(fondsInvestis, vendues.get(0), vendues.get(1));
				this.addEmployes(newPlants.get(2));
				this.removeFonds(202.18*newPlants.get(2));
			} else {
				newPlants.add(0);
				newPlants.add(0);
				newPlants.add(0);
			}
		} else {
			newPlants.add(0);
			newPlants.add(0);
			newPlants.add(0);
		}
	newPlants.add((int) ((fonds - this.getFonds())*100));
	return newPlants;
	
	}

}
