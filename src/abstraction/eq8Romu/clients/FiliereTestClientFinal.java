package abstraction.eq8Romu.clients;

import abstraction.eq8Romu.produits.Chocolat;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;

public class FiliereTestClientFinal extends Filiere {

	private ClientFinal cf ;
	public FiliereTestClientFinal() {
		super();
		this.ajouterActeur(new ExempleDistributeurChocolat(Chocolat.CHOCOLAT_BASSE, 30000.0, 4000.0));
		this.ajouterActeur(new ExempleDistributeurChocolat(Chocolat.CHOCOLAT_BASSE, 50000.0, 3750.0));
		this.ajouterActeur(new ExempleDistributeurChocolat(Chocolat.CHOCOLAT_MOYENNE, 40000.0, 10000.0));
		this.ajouterActeur(new ExempleDistributeurChocolat(Chocolat.CHOCOLAT_MOYENNE, 35000.0, 9000.0));
		cf = new ClientFinal();
		this.ajouterActeur(cf);
		
		this.getIndicateur("D.Choco1CHOCOLAT_BASSE stock CHOCOLAT_BASSE").setValeur(cf, 1000000);
		this.getIndicateur("D.Choco2CHOCOLAT_BASSE stock CHOCOLAT_BASSE").setValeur(cf, 1000000);
		this.getIndicateur("D.Choco3CHOCOLAT_MOYENNE stock CHOCOLAT_MOYENNE").setValeur(cf, 1000000);
		this.getIndicateur("D.Choco4CHOCOLAT_MOYENNE stock CHOCOLAT_MOYENNE").setValeur(cf, 1000000);
	}
	
	
	public void initialiser() {
		super.initialiser();
		cf.initAttractiviteChoco(Chocolat.CHOCOLAT_BASSE, 1.0);
		cf.initAttractiviteChoco(Chocolat.CHOCOLAT_MOYENNE, 2.0);
	}

	
	/**
	 * Redefinition afin d'interdire l'acces direct au client final
	 */
	public IActeur getActeur(String nom) {
		if (!nom.equals("CLIENTFINAL")) {
			return super.getActeur(nom); 
		} else {
			return null;
		}
	}
}
