package abstraction.eq2Producteur2;
import abstraction.eq8Romu.produits.Feve;
import abstraction.eq8Romu.produits.Pate;
import abstraction.eq8Romu.produits.Gamme;

public class PaquetArbres {
	/**
	 * @author Kristof Szentes
	 */
	
	private int nbreArbres;
	private double age;
	private Feve type;
	private double prodmax;
	
	public PaquetArbres(int nbreA, Feve type) {
		this.nbreArbres = nbreA;
		this.age = 0;
		this.type = type;
		this.prodmax = 0.0003125;
	}
	public PaquetArbres(int nbreA, Feve type, double age) {
		this.nbreArbres = nbreA;
		this.age = age;
		this.type = type;
		this.prodmax = 0.0003125;
	}
	
	public double getProdmax() {
		return this.prodmax;
	}
	public void setProdmax(double prodmax) {
		this.prodmax = prodmax;
	}
	
	public int getNbreArbres() {
		return this.nbreArbres;
	}
	
	public double getAge() {
		return this.age;
	}
	
	public void setAge(double newAge) {
		this.age = newAge;
	}
	
	public Feve getType() {
		return this.type;
	}
	/**
	 * 
	 * @author lucas P
	 * @author Kristof
	 */
	public double production() {
		double production_max = this.getProdmax()*this.getNbreArbres(); 
		if (this.getAge() < 3*24) {
			return 0.0;
		}
		else if(this.getAge() < 6*24) {
			return ((this.getAge() - 2)*0.25*production_max);
		}
		else if(this.getAge() < 31*24) {
			return production_max;
		}
		else if(this.getAge() > 45*24) {
			return 0;
		}
		else {
			return(production_max*(1.0-(this.getAge() - 30.0*24.0)/360.0));
		}
		
	}
	
}
