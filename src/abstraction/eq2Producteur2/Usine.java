package abstraction.eq2Producteur2;

import abstraction.eq8Romu.produits.Pate;

public class Usine {
	public Pate pate;
	public int NbMachine;
	public double Prod;
	public int Age;

	public Usine(Pate pate, int NbMachine) {
		this.pate = pate;
		this.NbMachine =NbMachine;
		this.Prod = 100;
		this.Age =0;
	}
	public Pate getPate() {
		return this.pate;
	}
	public int getAge() {
		return this.Age;
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
		if (this.getAge()<=30) {
			return this.getNbMachine()*this.getProd();
		}
		if (this.getAge()<=50) {
			return this.getNbMachine()*this.getProd()*(1.0-this.getAge()/50.0);
		}
		else return 0.0;
	}
	public double 
}
