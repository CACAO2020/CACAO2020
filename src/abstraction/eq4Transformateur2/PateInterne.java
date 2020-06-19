package abstraction.eq4Transformateur2;

import abstraction.eq8Romu.contratsCadres.FiliereTestContratCadre;
import abstraction.eq8Romu.produits.Gamme;
import abstraction.eq8Romu.produits.Pate;

//PateInterne est une classe alternative pour Pate permettant de faciliter toutes les opérations internes
	//Il faut cependant vérifier que lors des échanges avec les codes extérieurs, ce qui est envoyé est du
	//type Pate. On l'utilise ici pour classifier les stocks.

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
	
	public String toString () {
		switch (this) { 
		case PATE_BASSE : return "pate basse" ;
		case PATE_MOYENNE : return "pate moyenne" ;
		case PATE_HAUTE : return "pate haute" ;
		case PATE_MOYENNE_EQUITABLE : return "pate moyenne équitable" ;
		case PATE_HAUTE_EQUITABLE : return "pate haute équitable" ;
	    default : return "pate nulle";
		}
	}
	
	// Permet de renvoyer une pâte de type Pate à partir d'une pâte de type PateInterne 
		// Attention retourne null si c'est une pâte qui n'a pas d'équivalent dans Pate, ie une pâte
		// qui n'est utile qu'en interne
	
	public Pate conversionPateInterne () { 
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
