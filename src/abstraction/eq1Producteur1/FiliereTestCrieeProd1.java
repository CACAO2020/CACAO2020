package abstraction.eq1Producteur1;


// <-- Melanie 

import java.awt.Color;
import abstraction.eq8Romu.cacaoCriee.ExempleAcheteurCacaoCriee;
import abstraction.eq8Romu.cacaoCriee.SuperviseurCacaoCriee;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;


public class FiliereTestCrieeProd1 extends Filiere {
	
	public FiliereTestCrieeProd1() {
		super();
		this.ajouterActeur(new Producteur1());
		this.ajouterActeur(new ExempleAcheteurCacaoCriee());
		this.ajouterActeur(new ExempleAcheteurCacaoCriee());
		this.ajouterActeur(new ExempleAcheteurCacaoCriee());
		SuperviseurCacaoCriee superviseur = new SuperviseurCacaoCriee();
		this.ajouterActeur(superviseur);
	}
	
	public IActeur getActeur(String nom) {
		if (!nom.equals("Sup.C.Criee")) {
			return super.getActeur(nom); 
		} else {
			return null;
		}
	}
}

// -->