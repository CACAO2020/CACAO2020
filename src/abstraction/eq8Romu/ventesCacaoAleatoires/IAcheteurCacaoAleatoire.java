package abstraction.eq8Romu.ventesCacaoAleatoires;

public interface IAcheteurCacaoAleatoire {
	/**
	 * @param quantiteEnVente>=0
	 * @param prix>0
	 * @return Retourne la quantite que l'acheteur souhaite acheter compte tenu de la quantite 
	 * en vente et du prix
	 * La valeur retournee est positive ou nulle et forcement inferieure ou egale a quantiteEnVente.
	 * Le vendeur ne peut pas refuser la vente mais l'acheteur ne recoit les feves que si
	 * le montant du a pu etre debite sur son compte (la methode quantiteLivree(double quantite) informe 
	 * l'acheteur d'une livraison de feve)
	 */
	public double quantiteDesiree(double quantiteEnVente, double prix);
	
	/**
	 * Methode appelee par le superviseur afin de notifier l'acheteur de la livraison d'une
	 * quantite de feves.
	 * @param quantiteLivree
	 */
	
	public void quantiteLivree(double quantiteLivree);
	/**
	 * Methode invoquee par le superviseur afin qu'il obtienne le cryptogramme de l'acheteur
	 * (necessaire pour que le transfert d'argent ait lieu).
	 * Le parametre fourni permet de s'assurer que c'est bien le superviseur qui demande 
	 * le cryptogramme (Verifier que le parametre n'est pas null suffit a en etre assure car 
	 * aucun acteur n'a de reference vers l'unique superviseur) 
	 */
	public Integer getCryptogramme(SuperviseurVentesCacaoAleatoires superviseur);
}
