package abstraction.fourni;

import abstraction.eq1Producteur1.Producteur1;
import abstraction.eq2Producteur2.Producteur2;
import abstraction.eq3Transformateur1.Transformateur1;
import abstraction.eq4Transformateur2.Transformateur2;
import abstraction.eq5Transformateur3.Transformateur3;
import abstraction.eq6Distributeur1.Distributeur1;
import abstraction.eq7Distributeur2.Distributeur2;
import abstraction.eq8Romu.Romu;

public class FiliereParDefaut extends Filiere {
	
	public FiliereParDefaut() {
		super();
		this.ajouterActeur(new Producteur1());
		this.ajouterActeur(new Producteur2());
		this.ajouterActeur(new Transformateur1());
		this.ajouterActeur(new Transformateur2());
		this.ajouterActeur(new Transformateur3());
		this.ajouterActeur(new Distributeur1());
		this.ajouterActeur(new Distributeur2());
		this.ajouterActeur(new Romu());
	}

}
