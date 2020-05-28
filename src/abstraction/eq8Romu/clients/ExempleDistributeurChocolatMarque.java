package abstraction.eq8Romu.clients;

import java.util.LinkedList;
import java.util.List;

import abstraction.eq8Romu.produits.ChocolatDeMarque;
/**
 * @author R. Debryune
 *
 * Un exemple simpliste d'un distributeur qui ne commercialise qu'une variete de chocolat, 
 * qui peut au plus mettre capaciteDeVente tonnes de chocolat en vente par step, et qui a
 * un prix fixe pour ce chocolat 
 */
public class ExempleDistributeurChocolatMarque extends ExempleAbsDistributeurChocolatMarque implements IDistributeurChocolatDeMarque {

	private double capaciteDeVente;
	private double prix;

	public ExempleDistributeurChocolatMarque(ChocolatDeMarque choco, double capaciteDeVente, double prix) {
		super(choco);
		this.capaciteDeVente = capaciteDeVente;
		this.prix = prix;
	}

	public List<ChocolatDeMarque> getCatalogue() {
		List<ChocolatDeMarque> produits=new LinkedList<ChocolatDeMarque>();
		produits.add(chocolat);
		return produits;
	}

	public double prix(ChocolatDeMarque choco) {
		return this.prix;
	}

	public double quantiteEnVente(ChocolatDeMarque choco) {
		return Math.min(capaciteDeVente, stockChocolat.getValeur());
	}

	public void vendre(ClientFinal client, ChocolatDeMarque choco, double quantite, double montant) {
		if (client!=null) { // permet de verifier que c'est bien un client final qui appelle cette methode car les autres acteurs n'ont aucun moyen de connaitre la reference vers l'unique instance de ClientFinal
			// verifier qu'il y a eu une notification d'un virement de montant sur le compte serait une assurrance supplementaire
			stockChocolat.retirer(client, quantite);
		}
	}

	public void notificationRayonVide(ChocolatDeMarque choco) {
		journal.ajouter(" Aie... j'aurais du mettre davantage de "+choco.name()+" en vente");
	}

	/**
	 * Ici on fait une campagne de pub des qu'on le peut, ce qui n'est pas forcement judicieux
	 */
	public List<ChocolatDeMarque> pubSouhaitee() {
		List<ChocolatDeMarque> res = new LinkedList<ChocolatDeMarque>();
		res.add(chocolat);
		return res;
	}
}
