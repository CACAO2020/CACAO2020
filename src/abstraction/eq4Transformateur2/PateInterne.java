package abstraction.eq4Transformateur2;

import abstraction.eq8Romu.produits.Gamme;
import abstraction.eq8Romu.produits.Pate;

public enum PateInterne {
	PATE_BASSE(Gamme.BASSE, false, false),
	PATE_MOYENNE(Gamme.MOYENNE, false, false),
	PATE_HAUTE(Gamme.HAUTE, false, false),
	PATE_MOYENNE_EQUITABLE(Gamme.MOYENNE, true, false),
	PATE_HAUTE_EQUITABLE(Gamme.HAUTE, true, false);

	private Gamme gamme;
	private boolean equitable;
	private boolean bio;
	
	PateInterne(Gamme gamme, boolean equitable, boolean bio) {
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
		for (PateInterne p : PateInterne.values()) {
			System.out.println(p);
		}
	}
	public Pate conversionPateInterne () { // Attention retourne null si ça ne doit pas sortir du code interne
		if (this == PateInterne.PATE_BASSE) {
			return Pate.PATE_BASSE ;
		}
		else {
			if (this == PateInterne.PATE_MOYENNE) {
				return Pate.PATE_MOYENNE ;
			}
			else {
				return null ;
			}
		}
	}
}
