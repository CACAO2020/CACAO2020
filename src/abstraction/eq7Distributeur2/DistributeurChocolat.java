package abstraction.eq7Distributeur2;

import abstraction.eq8Romu.clients.ClientFinal;
import abstraction.eq8Romu.clients.IDistributeurChocolat;
import abstraction.eq8Romu.produits.Chocolat;
//import abstraction.eq8Romu.produits.Gamme;
//import abstraction.fourni.Filiere;

/**
 * 
 * @author R. Debryune
 *
 * Un exemple simpliste d'un distributeur qui ne commercialise qu'une variete de chocolat, 
 * qui peut au plus mettre capaciteDeVente tonnes de chocolat en vente par step, et qui a
 * un prix fixe pour ce chocolat 
 */
public class DistributeurChocolat extends AbsDistributeurChocolat implements IDistributeurChocolat {

	private double capaciteDeVente;
	private double prix;

	public DistributeurChocolat() {

	}

	public boolean commercialise(Chocolat choco) {
		return false;
	}

	public double quantiteEnVente(Chocolat choco) {
		return 0.;
	}

	public double prix(Chocolat choco) {
		return 0.;
	}

	public void vendre(ClientFinal client, Chocolat choco, double quantite) {
	}
	
	public void next() {
		//Filiere.LA_FILIERE.getVentes(-24, new Chocolat(Gamme.HAUTE, false, false));
	}
}
