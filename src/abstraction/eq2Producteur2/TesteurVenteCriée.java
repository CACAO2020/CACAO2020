package abstraction.eq2Producteur2;



import abstraction.eq8Romu.produits.Feve;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;
import abstraction.eq8Romu.cacaoCriee.*;

public class TesteurVenteCriée extends Filiere {

	public TesteurVenteCriée() {
		super();
		this.ajouterActeur(new ExempleVendeurCacaoCriee(Feve.FEVE_BASSE, 1.0, 20.0, 0.65*1000));
		this.ajouterActeur(new ExempleVendeurCacaoCriee(Feve.FEVE_BASSE, 1.0, 20.0, 0.75*1000));
		this.ajouterActeur(new ExempleVendeurCacaoCriee(Feve.FEVE_HAUTE, 0.5, 10.0, 0.95*1000));
		this.ajouterActeur(new eq2Vendeur());
		this.ajouterActeur(new ExempleAcheteurCacaoCriee());
		this.ajouterActeur(new ExempleAcheteurCacaoCriee());
		this.ajouterActeur(new ExempleAcheteurCacaoCriee());
		SuperviseurCacaoCriee superviseur = new SuperviseurCacaoCriee();
		this.ajouterActeur(superviseur);
	}
	/**
	 * Redefinition afin d'interdire l'acces direct au superviseur.
	 * Sans cela, il serait possible de contourner l'autentification
	 */
	public IActeur getActeur(String nom) {
		if (!nom.equals("Sup.C.Criee")) {
			return super.getActeur(nom); 
		} else {
			return null;
		}
	}


}
