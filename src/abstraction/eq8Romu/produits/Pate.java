package abstraction.eq8Romu.produits;

public enum Pate {
	PATE_BASSE(Gamme.BASSE),
	FEVE_MOYENNE(Gamme.MOYENNE);

	private Gamme gamme;
	
	Pate(Gamme gamme) {
		this.gamme = gamme;
	}
	public Gamme getGamme() {
		return this.gamme;
	}
	public boolean isEquitable() {
		return false; // aucune pate n'est equitable
	}
	public boolean isBio() {
		return false; // aucune pate n'est bio
	}
	public static void main(String[] args) {
		for (Pate p : Pate.values()) {
			System.out.println(p);
		}
	}
}
