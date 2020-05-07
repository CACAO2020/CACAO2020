package abstraction.eq8Romu.clients;

import abstraction.eq8Romu.produits.Chocolat;
import abstraction.fourni.IActeur;


@Deprecated //Version obsolete du fait de l'introduction des marques pour les chocolats.
public interface IDistributeurChocolat extends IActeur {
	
	/**
	 * @param choco, choco!=null
	 * @return Retourne true si l'acteur commercialise (ce qui ne veut pas dire qu'il en a 
	 * actuellement dans ses rayons) du chocolat de type choco.
	 */
	public boolean commercialise(Chocolat choco);
	
	/**
	 * @param choco, choco!=null
	 * @return Retourne la quantite (en tonnes) de chocolat de type choco que le distributeur
	 * a actuellement disponible a la vente (pour un achat immediat)
	 */
	public double quantiteEnVente(Chocolat choco);
	
	/**
	 * @param choco, choco!=null
	 * @return Le prix actuel d'une tonne de chocolat choco
	 */
	public double prix(Chocolat choco);
	
	/**
	 * Met a jour les donnes du distributeur (dont son stock de chocolat choco) suite
	 * a la vente d'une quantite quantite de chocolat choco.
	 * @param client, le client qui achete (verifier que client!=null suffit a etre assure que c'est bien un client final qui appelle cette methode)
	 * @param choco, choco!=null
	 * @param quantite, quantite>0.0 et quantite<=quantiteEnVente(choco)
	 */
	public void vendre(ClientFinal client, Chocolat choco, double quantite);
}
