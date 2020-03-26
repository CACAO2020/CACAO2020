package abstraction.eq8Romu.ventesCacaoAleatoires;

public interface IVendeurCacaoAleatoire {
	/**
	 * @param prix > 0
	 * @return Retourne la quantite que le vendeur souhaite mettre en vente 
	 * compte tenu du prix. La valeur retournee doit etre positive ou nulle.
	 *  Le vendeur doit disposer d'un stock de feves au moins egal a la valeur retournee.
	 */
	public double quantiteEnVente(double prix);

	/**
	 * Methode appelee lorsqu'un acheteur vient d'acheter une quantite de feve au prix indique en parametre.
	 * Le vendeur doit doit actualiser son etat (stock, solde, ...) pour tenir compte de cette transaction.
	 * @param quantitee>=0
	 * @param prix>0
	 */
	public void notificationVente(double quantite, double prix);
}
