package abstraction.eq8Romu.cacaoCriee;

import abstraction.eq8Romu.produits.Feve;
import abstraction.fourni.Filiere;

public class FiliereTestVentesCacaoCriee extends Filiere {

	public FiliereTestVentesCacaoCriee() {
		super();
		this.ajouterActeur(new ExempleVendeurCacaoCriee(Feve.FEVE_BASSE, 1.0, 20.0, 0.65));
		this.ajouterActeur(new ExempleVendeurCacaoCriee(Feve.FEVE_BASSE, 1.0, 20.0, 0.75));
		this.ajouterActeur(new ExempleVendeurCacaoCriee(Feve.FEVE_HAUTE, 0.5, 10.0, 0.95));
		this.ajouterActeur(new ExempleAcheteurCacaoCriee());
		this.ajouterActeur(new ExempleAcheteurCacaoCriee());
		this.ajouterActeur(new ExempleAcheteurCacaoCriee());
		SuperviseurCacaoCriee superviseur = new SuperviseurCacaoCriee();
		this.ajouterActeur(superviseur);
	}

}
