package abstraction.eq8Romu.clients;

import abstraction.eq8Romu.produits.Chocolat;

/**
 * 
 * @author R. Debryune
 *
 * Un exemple simpliste d'un distributeur qui ne commercialise qu'une variete de chocolat, 
 * qui peut au plus mettre capaciteDeVente tonnes de chocolat en vente par step, et qui a
 * un prix fixe pour ce chocolat 
 */
public class ExempleDistributeurChocolat extends ExempleAbsDistributeurChocolat implements IDistributeurChocolat {

	private double capaciteDeVente;
	private double prix;

	public ExempleDistributeurChocolat(Chocolat choco, double capaciteDeVente, double prix) {
		super(choco);
		this.capaciteDeVente = capaciteDeVente;
		this.prix = prix;
	}

	public boolean commercialise(Chocolat choco) {
		return (this.chocolat.equals(choco)); // ne commercialise qu'un seul chocolat
	}

	public double quantiteEnVente(Chocolat choco) {
		return Math.min(capaciteDeVente, stockChocolat.getValeur());
	}

	public double prix(Chocolat choco) {
		return this.prix;
	}

	public void vendre(ClientFinal client, Chocolat choco, double quantite) {
		if (client!=null) { // permet de verifier que c'est bien un client final qui appelle cette methode car les autres acteurs n'ont aucun moyen de connaitre la reference vers l'unique instance de ClientFinal
			stockChocolat.retirer(client, quantite);
		}
	}
}
