package abstraction.eq2Producteur2;
import abstraction.eq8Romu.produits.Feve;
import abstraction.eq8Romu.produits.Pate;
import abstraction.eq8Romu.produits.Gamme;
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
	private Feve type;
	
	
	public PaquetArbres(int nbreA, Feve type) {
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
	
	public Feve getType() {
		return this.type;
	}
	/**
	 * 
	 * @author lucas P
	 */
	public double production() {
		double prodmaxTrinitario = 7.5;
		double prodmaxForastero = 7.5;
		double prodmaxCriollo = 7.5;
		double prodmaxTrinitario_bio = 6;
		double prodmaxForastero_bio = 6;
		double prodmaxCriollo_bio = 6;
		if (this.type.equals(Feve.FEVE_BASSE)){
			return prodmaxTrinitario*(1-Math.exp(this.age))*this.nbreArbres;
		}
		if (this.type.equals(Feve.FEVE_MOYENNE)){
			return prodmaxForastero*(1-Math.exp(this.age))*this.nbreArbres;}
		else 
			{return prodmaxCriollo*(1-Math.exp(this.age))*this.nbreArbres;}
		
	}
	
}
