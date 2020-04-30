package abstraction.eq2Producteur2;

/**
 * 
 * @author lucas
 *type peut prendre 3 valeurs : trinitario, forastero, criollo (par ordre croissant de qualité)
 */
public class PaquetArbres {
	/**
	 * @author Kristof Szentes
	 */
	
	private int nbreArbres;
	private int age;
	private String type;
	
	public PaquetArbres(int nbreA, String type) {
		this.nbreArbres = nbreA;
		this.age = 0;
		this.type = type;
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
	
	public String getType() {
		return this.type;
	}
	
	public double production() {
		return prodmax*(1-Math.exp(this.age));
	}
	
	public double recolte() {
		if (this.type.equals("trinitario")){
			return production;
		}
		if (this.type.equals("forastero")){
			return production;}
		if (this.type.equals("criollo")){
			return production;}
	}
}
