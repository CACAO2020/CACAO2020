package abstraction.eq8Romu.cacaoCriee;

import java.util.List;

import abstraction.fourni.IActeur;

/** @author R. DEBRUYNE  */
public interface IVendeurCacaoCriee extends IActeur {
	/**
	 * Methode appelee par le superviseur des ventes de feves a la criee afin de connaitre le lot
	 * que le vendeur souhaite mettre en vente.
	 * @return Retourne le lot de feve que le vendeur souhaite proposer a la vente (null si le vendeur ne
	 * souhaite pas mettre de lot en vente)
	 */
	public LotCacaoCriee getLotEnVente();
	
	/**
	 * Methode appelee par le superviseur des ventes de feves a la criee lorsque qu'aucune proposition
	 * d'achat n'a ete formulee par les vendeurs pour le lot propose a la vente
	 * @param lot, le lot que le vendeur a propose a la vente et qui n'a fait l'objet d'une proposition 
	 * d'achat de la part des vendeurs.
	 * Remarque : cette methode a pour but de notifier le vendeur que les vendeurs n'ont pas fait d'offres
	 * d'achat pour le lot. Le code de cette methode peut eventuellement etre vide si le vendeur ne 
	 * souhaite pas tirer parti de cette information
	 */
	public void notifierAucuneProposition(LotCacaoCriee lot);
	
	/**
	 * Methode appelee par le superviseur des ventes de feves afin de connaitre la proposition que
	 * retient le vendeur.
	 * @param propositions, propositions!=null, propositions.size()>=1, l'ensemble des propositions 
	 * d'achat du lot mis en vente par this
	 * @return Retourne la proposition figurant dans propositions que le vendeur this retient 
	 * (retourne null si le vendeur ne rejette toutes ces propositions)
	 */
	public PropositionCriee choisir(List<PropositionCriee> propositions);
	
	/**
	 * Methode appelee par le superviseur des ventes de feves 
	 * suite au paiement par l'acheteur de la proposition.
	 * La methode doit donc a minima retirer du stock le lot de feve qui vient d'etre achete 
	 * @param proposition, la proposition que le vendeur vient d'honorer en payant la somme due.
	 */
	public void notifierVente(PropositionCriee proposition);
}
