package abstraction.eq8Romu.clients;

import java.util.List;

import abstraction.eq8Romu.produits.ChocolatDeMarque;
import abstraction.fourni.IActeur;

public interface IDistributeurChocolatDeMarque extends IActeur {
	
	/**
	 * @return La liste des chocolats de marque que le distributeur commercialise 
	 * (ce qui ne veut pas dire qu'il en a actuellement dans ses rayons).
	 */
	public List<ChocolatDeMarque> getCatalogue();
	
	/**
	 * @param choco, choco!=null
	 * @return Le prix actuel d'une tonne de chocolat choco
	 * IMPORTANT : durant une meme etape, la fonction doit toujours retourner la meme valeur pour un chocolat donne.
	 */
	public double prix(ChocolatDeMarque choco);
	
	/**
	 * @param choco, choco!=null
	 * @return Retourne la quantite (en tonnes) de chocolat de type choco 
	 * actuellement disponible a la vente (pour un achat immediat --> le distributeur a 
	 * au moins cette quantite en stock)
	 */
	public double quantiteEnVente(ChocolatDeMarque choco);
	
	
	/**
	 * Met a jour les donnees du distributeur (dont son stock de chocolat choco) suite
	 * a la vente d'une quantite quantite de chocolat choco.
	 * Lorsque le client appelle cette methode il a deja verse la somme montant correspondant a l'achat
	 * sur le compte du distributeur : le distributeur a recu une notification de la banque ce qui lui permet 
	 * de verifier que la transaction est bien effective.
	 * @param client, le client qui achete 
	 * @param choco, choco!=null
	 * @param quantite, quantite>0.0 et quantite<=quantiteEnVente(choco)
	 * @param montant, le montant correspondant a la transaction que le client a deja verse sur le compte du distributeur
	 */
	public void vendre(ClientFinal client, ChocolatDeMarque choco, double quantite, double montant);
	
	/**
	 * Methode appelee par le client final lorsque la quantite en rayon de chocolat choco 
	 * est inferieure a la quantite desiree
	 * @param choco, le chocolat de marque dont la quantite en rayon a ete integralement achetee
	 */
	public void notificationRayonVide(ChocolatDeMarque choco);
	
	/**
	 * Methode appelee par ClientFinal des qu'une campgane de publicite est autorisee. 
	 * Le distributeur retourne null ou une liste vide si il ne souhaite pas realiser de 
	 * campagne depub a cette etape. Sinon, il retourne la liste des produits sur lesquels portera 
	 * la publicite.
	 * @return
	 */
	public List<ChocolatDeMarque> pubSouhaitee();
}
