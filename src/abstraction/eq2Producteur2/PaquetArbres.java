package abstraction.eq2Producteur2;
import abstraction.eq8Romu.produits.Feve;
import abstraction.eq8Romu.produits.Pate;
import abstraction.eq8Romu.produits.Gamme;

public class PaquetArbres {
	/**
	 * @author Kristof Szentes
	 */
	
	private int nbreArbres;
	private int age;
	private Feve type;
	private double prodmax;
	
	public PaquetArbres(int nbreA, Feve type) {
		this.nbreArbres = nbreA;
		this.age = 0;
		this.type = type;
		this.prodmax = 7.5;
	}
	public PaquetArbres(int nbreA, Feve type, int age) {
		this.nbreArbres = nbreA;
		this.age = age;
		this.type = type;
		this.prodmax = 7.5;
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
	
	public int getAge() {
		return this.age;
	}
	
	public void setAge(int newAge) {
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
		if (this.getAge() < 3) {
			return 0.0;
		}
		else if(this.getAge() < 6) {
			return ((this.getAge() - 2)*0.25*this.getProdmax());
		}
		else if(this.getAge() < 31) {
			return this.getProdmax();
		}
		else {
			return(this.getProdmax()*(1-(this.getAge() - 30)/15));
		}
		
	}
	
}
