package abstraction.eq6Distributeur1;

import abstraction.eq8Romu.chocolatBourse.ExempleVendeurChocolatBourse;
import abstraction.eq8Romu.chocolatBourse.SuperviseurChocolatBourse;
import abstraction.eq8Romu.clients.ClientFinal;
import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.ChocolatDeMarque;
import abstraction.fourni.Filiere;

public class FiliereAchatSeul extends Filiere {
	
	public FiliereAchatSeul() {
		super();
		this.ajouterActeur(new ClientFinal());
		this.ajouterActeur(new ExempleVendeurChocolatBourse(Chocolat.CHOCOLAT_BASSE));
		this.ajouterActeur(new ExempleVendeurChocolatBourse(Chocolat.CHOCOLAT_MOYENNE));
		this.ajouterActeur(new ExempleVendeurChocolatBourse(Chocolat.CHOCOLAT_HAUTE_EQUITABLE));
		this.ajouterActeur(new Distributeur1());
		this.ajouterActeur(new SuperviseurChocolatBourse());
		
	}
	
	
}
