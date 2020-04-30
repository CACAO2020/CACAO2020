package abstraction.eq2Producteur2;

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
	
}
