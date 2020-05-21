package abstraction.eq6Distributeur1;

import abstraction.eq8Romu.chocolatBourse.ExempleVendeurChocolatBourse;
import abstraction.eq8Romu.chocolatBourse.SuperviseurChocolatBourse;
import abstraction.eq8Romu.clients.ClientFinal;
import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.ChocolatDeMarque;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;

public class FiliereAchatSeul extends Filiere {
	
	public FiliereAchatSeul() {
		super();
		IActeur cf = new ClientFinal();
		this.ajouterActeur(cf);
		IActeur CBASSE = new ExempleVendeurChocolatBourse(Chocolat.CHOCOLAT_BASSE);
		this.ajouterActeur(CBASSE);
		this.ajouterActeur(new ExempleVendeurChocolatBourse(Chocolat.CHOCOLAT_MOYENNE));
		this.ajouterActeur(new ExempleVendeurChocolatBourse(Chocolat.CHOCOLAT_HAUTE_EQUITABLE));
		this.ajouterActeur(new Distributeur1());
		this.ajouterActeur(new SuperviseurChocolatBourse());
		
		System.out.print(this.getIndicateurs(CBASSE));
		
		
		this.getIndicateur("V.ChocoBourse1CHOCOLAT_BASSE stock CHOCOLAT_BASSE").setValeur(cf, 12345);
		
	}
	
	
}
