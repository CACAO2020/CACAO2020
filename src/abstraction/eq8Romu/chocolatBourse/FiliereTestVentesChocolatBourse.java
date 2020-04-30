package abstraction.eq8Romu.chocolatBourse;

import abstraction.eq8Romu.produits.Chocolat;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;

public class FiliereTestVentesChocolatBourse  extends Filiere {

	public FiliereTestVentesChocolatBourse() {
		super();
		this.ajouterActeur(new ExempleVendeurChocolatBourse(Chocolat.CHOCOLAT_BASSE));
		this.ajouterActeur(new ExempleVendeurChocolatBourse(Chocolat.CHOCOLAT_BASSE));
		this.ajouterActeur(new ExempleVendeurChocolatBourse(Chocolat.CHOCOLAT_HAUTE));
		this.ajouterActeur(new ExempleVendeurChocolatBourse(Chocolat.CHOCOLAT_MOYENNE_EQUITABLE));
		this.ajouterActeur(new ExempleAcheteurChocolatBourse());
		this.ajouterActeur(new ExempleAcheteurChocolatBourse());
		this.ajouterActeur(new ExempleAcheteurChocolatBourse());
		SuperviseurChocolatBourse superviseur = new SuperviseurChocolatBourse();
		this.ajouterActeur(superviseur);
		this.getIndicateur("BourseChoco cours CHOCOLAT_BASSE").setValeur(superviseur, 4000.0);
		this.getIndicateur("BourseChoco cours CHOCOLAT_MOYENNE").setValeur(superviseur, 10000.0);
		this.getIndicateur("BourseChoco cours CHOCOLAT_HAUTE").setValeur(superviseur, 15000.0);
		this.getIndicateur("BourseChoco cours CHOCOLAT_MOYENNE_EQUITABLE").setValeur(superviseur, 14000.0);
		this.getIndicateur("BourseChoco cours CHOCOLAT_HAUTE_EQUITABLE").setValeur(superviseur, 20000.0);
		getBanque().initCompte(this, "A.ChocoBourse1", 100000);
		getBanque().initCompte(this, "A.ChocoBourse2", 100000);
		getBanque().initCompte(this, "A.ChocoBourse3", 100000);
	}
	/**
	 * Redefinition afin d'interdire l'acces direct au superviseur.
	 * Sans cela, il serait possible de contourner l'autentification
	 */
	public IActeur getActeur(String nom) {
		if (!nom.equals("BourseChoco")) {
			return super.getActeur(nom); 
		} else {
			return null;
		}
	}
}
