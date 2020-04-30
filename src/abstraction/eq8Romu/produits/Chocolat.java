package abstraction.eq8Romu.produits;

public enum Chocolat {
	CHOCOLAT_BASSE(Gamme.BASSE, false, false),
	CHOCOLAT_MOYENNE(Gamme.MOYENNE, false, false),
	CHOCOLAT_HAUTE(Gamme.HAUTE, false, false),
	CHOCOLAT_MOYENNE_EQUITABLE(Gamme.MOYENNE, true, false),
	CHOCOLAT_HAUTE_EQUITABLE(Gamme.HAUTE, true, false);
	
	private Gamme gamme;
	private boolean equitable;
	private boolean bio;
	
	Chocolat(Gamme gamme, Boolean equitable, Boolean bio) {
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
		for (Chocolat c : Chocolat.values()) {
			System.out.println(c);
		}
	}

}
