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
		double prodmaxtrinitario = 7.5;
		double prodmaxforastero = 7.5;
		double prodmaxcriollo = 7.5;
		if (this.type.equals("trinitario")){
			return prodmaxtrinitario*(1-Math.exp(this.age))*this.nbreArbres;
		}
		if (this.type.equals("forastero")){
			return prodmaxforastero*(1-Math.exp(this.age))*this.nbreArbres;}
		else {
			return prodmaxcriollo*(1-Math.exp(this.age))*this.nbreArbres;}
	}
	
}
