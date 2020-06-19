package abstraction.eq2Producteur2;

import abstraction.eq8Romu.produits.Pate;

public class Usine {
	public int NbMachine;
	public double Prod;
	public int Age;
	public int rendement;

	public Usine(int NbMachine) {
	
		this.NbMachine =NbMachine;
		this.Prod = 10;
		this.Age =0;
		this.rendement=1;
	}

	public int getAge() {
		return this.Age;
	}
	public void setAge( int newAge) {
		this.Age= newAge;
	}
	public int getNbMachine() {
		return this.NbMachine;
	}
	public double getProd() {
		return this.Prod;
	}
	public void setProd(double newProd) {
		this.Prod = newProd;
	}
	public double Production() {
		if (this.getAge()<=15) {
			return this.getNbMachine()*this.getProd();
		}
		if (this.getAge()<=30) {
			return this.getNbMachine()*this.getProd()*(1.0-this.getAge()/30.0);
		}
		else return 0.0;
	}

	public double Amortissement() {
		if (this.getAge()<=15) {
			return 100; //a remplacer par un pourcentage du cout d'achat
		}
		else {return 0;}
	}
}
