package abstraction.eq3Transformateur1;


import abstraction.eq8Romu.chocolatBourse.ExempleAcheteurChocolatBourse;
import abstraction.eq8Romu.chocolatBourse.ExempleVendeurChocolatBourse;
import abstraction.eq8Romu.chocolatBourse.SuperviseurChocolatBourse;
import abstraction.eq8Romu.produits.Chocolat;
import abstraction.fourni.Filiere;

public class FiliereVenteTest extends Filiere{
public FiliereVenteTest() {
	this.ajouterActeur(new Transformateur1());
	this.ajouterActeur(new ExempleVendeurChocolatBourse(Chocolat.CHOCOLAT_MOYENNE));
	this.ajouterActeur(new ExempleAcheteurChocolatBourse());
	this.ajouterActeur(new ExempleAcheteurChocolatBourse());
	this.ajouterActeur(new ExempleAcheteurChocolatBourse());
	SuperviseurChocolatBourse superviseurBourse = new SuperviseurChocolatBourse();
	this.ajouterActeur(superviseurBourse);
}
}
