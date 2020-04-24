package abstraction.eq8Romu.cacaoCriee;



import abstraction.eq8Romu.produits.Feve;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;

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
