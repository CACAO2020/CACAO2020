package abstraction.eq8Romu.cacaoCriee;

import abstraction.fourni.IActeur;

/** @author R. DEBRUYNE  */
public interface IAcheteurCacaoCriee extends IActeur {
	
	/**
	 * Methode appelee par le superviseur des ventes de feves a la criee
	 * afin de connaitre la proposition d'achat de this suite a la 
	 * mise en vente du lot lot par le vendeur a la criee lot.getVendeur().
	 * @param lot, lot!=null, le lot qu'un vendeur vient de proposer a la vente
	 * @return Retourne le prix a la tonne auquel l'acheteur this souhaite
	 * acheter le lot (0.0 si l'acheteur this ne souhaite pas acheter ce lot)
	 */
	public double proposerAchat(LotCacaoCriee lot);

	/**
	 * Methode appelee par le superviseur des ventes de feves a la criee
	 * lorsque l'offre d'achat a la ciee proposition emise par this 
	 * n'a pas ete retenue.
	 * @param proposition, proposition!=null, la proposition que this avait faite et qui a ete refusee
	 */
	public void notifierPropositionRefusee(PropositionCriee proposition);

	/**
	 * Methode invoquee par le SuperviseurCacaoCriee afin qu'il obtienne le cryptogramme de l'acheteur
	 * (necessaire pour que le transfert d'argent ait lieu).
	 * Le parametre fourni permet de s'assurer que c'est bien le superviseur qui demande 
	 * le cryptogramme (Verifier que le parametre n'est pas null suffit a en etre assure car 
	 * aucun acteur n'a de reference vers l'unique superviseur) 
	 */
	public Integer getCryptogramme(SuperviseurCacaoCriee superviseur);

	/**
	 * Methode appelee par le superviseur des ventes de feves a la criee
	 * suite au paiement par le vendeur (this) de la proposition.
	 * La methode doit a minima ajouter le lot de feve au stock de feves de this. 
	 * @param proposition, la proposition qui vient d'etre honoree par this (et livree par le vendeur)
	 */
	public void notifierVente(PropositionCriee proposition);

}
