package abstraction.eq2Producteur2.produits;

public enum Feve {

	FEVE_BASSE(Gamme.BASSE, false, false),
	FEVE_MOYENNE(Gamme.MOYENNE, false, false),
	FEVE_HAUTE(Gamme.HAUTE, false, false),
	FEVE_MOYENNE_EQUITABLE(Gamme.MOYENNE, true, false),
	FEVE_HAUTE_EQUITABLE(Gamme.HAUTE, true, false);

	private Gamme gamme;
	private boolean equitable;
	private boolean bio;
	
	Feve(Gamme gamme, boolean equitable, boolean bio) {
		this.gamme = gamme;
		this.equitable = equitable;
		this.bio = bio;
	}
	public Gamme getGamme() {
		return this.gamme;
	}
	public boolean isEquitable() {
		return this.equitable;
	}
	public boolean isBio() {
		return this.bio;
	}

	public static void main(String[] args) {
		for (Feve f : Feve.values()) {
			System.out.println(f);
		}
	}
}
