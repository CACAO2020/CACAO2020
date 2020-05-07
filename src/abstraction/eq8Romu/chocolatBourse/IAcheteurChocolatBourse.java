package abstraction.eq8Romu.chocolatBourse;

import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.ChocolatDeMarque;
import abstraction.fourni.IActeur;

public interface IAcheteurChocolatBourse extends IActeur {
	
	/**
	 * Methode invoquee par le SuperviseurChocolatBourse pour connaitre la
	 * quantite de chocolat chocolat que l'IAcheteurChocolatBourse souhaite
	 * acheter sachant que son prix actuel en bourse est cours
	 * @param chocolat
	 * @param cours, le prix actuel en bourse du chocolat chocolat.
	 * @return La quantite desiree (retourne une quantite inferieure 
	 * a SuperviseurChocolatBourse.QUANTITE_MIN s'il ne souhaite pas
	 * en acheter actuellement) 
	 */
	public double getDemande(Chocolat chocolat, double cours);

	/**
	 * Methode invoquee par le SuperviseurChocolatBourse afin qu'il obtienne le cryptogramme de l'acheteur
	 * (necessaire pour que le transfert d'argent ait lieu).
	 * Le parametre fourni permet de s'assurer que c'est bien le superviseur qui demande 
	 * le cryptogramme (Verifier que le parametre n'est pas null suffit a en etre assure car 
	 * aucun acteur n'a de reference vers l'unique superviseurChocolatBourse) 
	 */
	public Integer getCryptogramme(SuperviseurChocolatBourse superviseur);
	
	/**
	 * Methode invoquee par le SuperviseurChocolatBourse afin d'informer l'IAcheteurChocolatBourse
	 * de la quantite qu'il va pouvoir obtenir (il ne sera livre que lorsque la methode receptionner
	 * sera appelee). payee vaut false si l'etat du compte bancaire de l'acheteur n'a pas permis de 
	 * payer la commande : dans ce cas le superviseur garde trace de la commande que l'acheteur devra
	 * payer (avec les interets) avant de pouvoir a nouveau acheter en bourse
	 * @param chocolat
	 * @param quantiteObtenue, la quantite qu'a pu obtenir l'acheteur (il avait potentiellement commande davantage)
	 * @param payee, true si le paiement a pu etre effectue (false si l'etat du compte bancaire de l'acheteur n'a pas permis de payer la commande)
	 */
	public void notifierCommande(Chocolat chocolat, double quantiteObtenue, boolean payee);
	
	/**
	 * Methode invoquee par le SuperviseurChocolatBourse afin de livrer la quantite de
	 * chocolat achetee (--> le stock de chocolat de l'IAcheteurChocolatBourse augmente).
	 * @param chocolat
	 * @param quantite
	 */
	public void receptionner(ChocolatDeMarque chocolat, double quantite);
	

}
