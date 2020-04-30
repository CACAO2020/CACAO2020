package abstraction.eq3Transformateur1;

import abstraction.eq8Romu.produits.Chocolat;

public interface IVendeurChocolatBourse extends IActeur {

	public double getOffre(Chocolat chocolat, double cours);
	
	/**
	 * Methode appelee par le SuperviseurChocolatBourse lorsqu'apres une offre le vendeur
	 * vend quantite tonnes de chocolat. Le montant de l'achat a deja ete verse sur le compte
	 * bancaire. Le vendeur doit donc deduire de son stock quantite tonnes de son stock.
	 * @param chocolat
	 * @param quantite, la quantite vendue, inferieure ou egale a la quantite que le vendeur
	 * avait mis en vente.
	 */
	public void livrer(Chocolat chocolat, double quantite);
}
