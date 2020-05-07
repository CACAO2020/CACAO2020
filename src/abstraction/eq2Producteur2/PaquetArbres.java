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
		double prodmaxTrinitario = 7.5;
		double prodmaxForastero = 7.5;
		double prodmaxCriollo = 7.5;
		double prodmaxTrinitario_bio = 6;
		double prodmaxForastero_bio = 6;
		double prodmaxCriollo_bio = 6;
		if (this.type.equals("trinitario")){
			return prodmaxTrinitario*(1-Math.exp(this.age))*this.nbreArbres;
		}
		if (this.type.equals("forastero")){
			return prodmaxForastero*(1-Math.exp(this.age))*this.nbreArbres;}
		if (this.type.contentEquals("criollo"))
			{return prodmaxCriollo*(1-Math.exp(this.age))*this.nbreArbres;}
		if (this.type.equals("trinitario_bio")){
			return prodmaxTrinitario_bio*(1-Math.exp(this.age))*this.nbreArbres;
		}
		if (this.type.equals("forastero_bio")){
			return prodmaxForastero_bio*(1-Math.exp(this.age))*this.nbreArbres;}
		else {
			return prodmaxCriollo_bio*(1-Math.exp(this.age))*this.nbreArbres;}
	}
	
}
