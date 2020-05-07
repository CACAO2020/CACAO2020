package abstraction.eq1Producteur1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


/*
 * Créé par Thalie
 */

/*
 * Programmes disponibles :
 * ArrayList<Double> getArbresF()
 * ArrayList<Double> getArbresT()
 * double getDerniereRecolteF()
 * double getDerniereRecolteT()
 * double getPourcentageRecolteurs()
 * void setArbresF(ArrayList<Double>)
 * void setArbresT(ArrayListDouble>)
 * void setDerniereRecolte(double, double)
 * 
 * void initialiserArbres(int, int)
 * void setPourcentageRecolteurs()
 * void nouvelArbre(int)
 * void actualiserAge()
 * ArrayList<Double> recolte()
 */



public class Plantations {
	
/*
 * La liste des arbres est stockée comme la liste de leur âge
 * classés dans l'ordre croissant.
 * On retient la dernière récolte car au vu de la phase de traitement
 * nécessaire pour faire sécher les fèves, j'ai implementé un retard
 * entre la récolte et l'ajout au stock des fèves d'un cycle.
 * Le pourcentage de récolteurs est aussi un paramètre nécessaire
 * car il influe sur la capacité de récolte.
 */
	private ArrayList<Double> arbresF;
	private ArrayList<Double> arbresT;
	private double derniereRecolteF;
	private double derniereRecolteT;
	private double pourcentageRecolteurs;
	
	
	public ArrayList<Double> getArbresF() {
		return this.arbresF;
	}
	
	
	public ArrayList<Double> getArbresT() {
		return this.arbresT;
	}
	
	
	public double getDerniereRecolteF() {
		return this.derniereRecolteF;
	}
	
	
	public double getDerniereRecolteT() {
		return this.derniereRecolteT;
	}
	
	
	public double getPourcentageRecolteurs() {
		return this.pourcentageRecolteurs;
	}
	
	
	public void setArbresF(ArrayList<Double> l) {
		this.arbresF = l;
	}
	
	
	public void setArbresT(ArrayList<Double> l) {
		this.arbresT = l;
	}
	
	
	public void setDerniereRecolte(double recolteF, double recolteT) {
		this.derniereRecolteF = recolteF;
		this.derniereRecolteT = recolteT;
	}
	
	
	public void setPourcentageRecolteurs(double p) {
		this.pourcentageRecolteurs = p;
	}
	
	
	public void nouvelArbre(int i) {
		if (i==0) {
			this.arbresF.add(0, (Double) 0.0); 
		} else {
			this.arbresT.add(0, (Double) 0.0);
		}
	}
	
	
	public void actualiserAge() {
		ArrayList<Double> l1 = this.getArbresF();
		ArrayList<Double> l2 = this.getArbresT();
		double pas = 1/24;
		
		for (int i=0; i<l1.size(); i+=1) {
			l1.set(i, (Double) l1.get(i)+pas);
		}
		boolean bool = true;
		while (bool) {
			int i = l1.size()-1;
			double age = (double) l1.get(i);
			if (age>45) {
				l1.remove(i);
			} else {
				bool = false;
			}
		}
		this.setArbresF(l1);
		
		for (int i=0; i<l2.size(); i+=1) {
			l2.set(i, (Double) l2.get(i)+pas);
		}
		bool = true;
		while (bool) {
			int i = l2.size()-1;
			double age = (double) l1.get(i);
			if (age>45) {
				l2.remove(i);
			} else {
				bool = false;
			}
		}
		this.setArbresT(l2);
	}
	
/*
 * Cette fonction est utilisé uniquement au début de la simulation
 * pour créer une plantation déjà existante.
 * J'ai utilisé l'aléatoire pour générer l'âge des arbres.
 */
	public void initialiserArbres(int arbresF, int arbresT) {
		Random rand = new Random();
		ArrayList<Double> lF = new ArrayList<Double>();
		ArrayList<Double> lT = new ArrayList<Double>();
		for (int i=0; i<arbresF; i+= 1) {
			double age = (double) rand.nextInt(45);
			lF.add((Double) age);
		}
		Collections.sort(lF);
		for (int i=0; i<arbresT; i+= 1) {
			double age = (double) rand.nextInt(45);
			lT.add((Double) age);
		}
		Collections.sort(lT);
	}
	
	
/*
 * J'ai pris en compte le pourcentage de récolteurs (parce que cela
 * influe sur le nombre de cacaoyers que l'on peut faire pendant la
 * récolte), de façon un peu simpliste pour l'instant.
 * Comme on ne gèrera pas les employés ici, c'est dans le programme
 * principal qu'il faudra actualiser ce pourcentage.
 */
	public ArrayList<Double> recolte() {
		double pretesAStockerF = this.getDerniereRecolteF();
		double pretesAStockerT = this.getDerniereRecolteT();
		double totalF = 0.0;
		double totalT = 0.0;
		double rendement = 0.0003125;
		ArrayList<Double> lF = this.getArbresF();
		ArrayList<Double> lT = this.getArbresT();
		
		for (int i=0; i<lF.size(); i+=1) {
			double age = (double) lF.get(i);
			if (age==3) {
				totalF += rendement/4;
			} else if (age==4) {
				totalF += rendement/2;
			} else if (age==5) {
				totalF += 3*rendement/4;
			}else if (age<36) {
				totalF += rendement;
			} else if (age<39) {
				totalF += 3*rendement/4;
			} else if (age<42) {
				totalF += rendement/2;
			} else {
				totalF += rendement/4;
			}
		}
		
		for (int i=0; i<lT.size(); i+=1) {
			double age = (double) lT.get(i);
			if (age==3) {
				totalT += rendement/4;
			} else if (age==4) {
				totalT += rendement/2;
			} else if (age==5) {
				totalT += 3*rendement/4;
			}else if (age<36) {
				totalT += rendement;
			} else if (age<39) {
				totalT += 3*rendement/4;
			} else if (age<42) {
				totalT += rendement/2;
			} else {
				totalT += rendement/4;
			}
		}
		
		totalF = totalF*this.getPourcentageRecolteurs();
		totalT = totalT*this.getPourcentageRecolteurs();
		this.setDerniereRecolte(totalF, totalT);
		ArrayList<Double> recolte = new ArrayList<Double>();
		recolte.add((Double) pretesAStockerF);
		recolte.add((Double) pretesAStockerT);
		return recolte;
	}
	
	
	
}
