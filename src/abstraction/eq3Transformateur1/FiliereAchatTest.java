package abstraction.eq3Transformateur1;

import abstraction.eq8Romu.cacaoCriee.ExempleAcheteurCacaoCriee;
import abstraction.eq8Romu.cacaoCriee.ExempleVendeurCacaoCriee;
import abstraction.eq8Romu.cacaoCriee.SuperviseurCacaoCriee;
import abstraction.eq8Romu.chocolatBourse.ExempleAcheteurChocolatBourse;
import abstraction.eq8Romu.chocolatBourse.ExempleVendeurChocolatBourse;
import abstraction.eq8Romu.chocolatBourse.SuperviseurChocolatBourse;
import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.Feve;
import abstraction.fourni.Filiere;

public class FiliereAchatTest extends Filiere{
	public FiliereAchatTest() {
		super();
		this.ajouterActeur(new Transformateur1());
		this.ajouterActeur(new ExempleAcheteurCacaoCriee());
		this.ajouterActeur(new ExempleVendeurCacaoCriee(Feve.FEVE_MOYENNE_EQUITABLE, 1,20, 0.75));
		this.ajouterActeur(new ExempleVendeurCacaoCriee(Feve.FEVE_HAUTE, 0.5,10, 0.95));
		SuperviseurCacaoCriee superviseur = new SuperviseurCacaoCriee();
		this.ajouterActeur(superviseur);
		this.ajouterActeur(new ExempleVendeurChocolatBourse(Chocolat.CHOCOLAT_MOYENNE));
		this.ajouterActeur(new ExempleAcheteurChocolatBourse());
		this.ajouterActeur(new ExempleAcheteurChocolatBourse());
		this.ajouterActeur(new ExempleAcheteurChocolatBourse());
		SuperviseurChocolatBourse superviseurBourse = new SuperviseurChocolatBourse();
		this.ajouterActeur(superviseurBourse);
	}
}
