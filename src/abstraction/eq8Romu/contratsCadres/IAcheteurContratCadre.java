package abstraction.eq8Romu.contratsCadres;


import abstraction.fourni.IActeur;

public interface IAcheteurContratCadre extends IActeur {

	/**
	 * Methode appelee par le SuperviseurVentesContratCadre lors des negociations
	 * sur l'echeancier afin de connaitre la contreproposition de l'acheteur.
	 * Les precedentes propositions d'echeancier peuvent etre consultees via un
	 * appel a la methode getEcheanciers() sur le contrat passe en parametre. 
	 * @param contrat. Notamment, getEcheancier() appelee sur le contrat retourne
	 * l'echeancier que le vendeur vient de proposer.
	 * @return Retoune null si l'acheteur souhaite mettre fin aux negociation (et abandonner
	 * du coup ce contrat). Retourne le meme echeancier que celui du contrat (contrat.getEcheancier()) 
	 * si l'acheteur est d'accord pour un tel echeancier. Sinon, retourne un nouvel echeancier
	 * que le superviseur soumettra au vendeur.
	 */
	public Echeancier contrePropositionDeLAcheteur(ExemplaireContratCadre contrat) {
		
		//on releve les parametres importants de l'offre du vendeur
		new int stepDeb = contrat.getEcheancier().getStepDebut();
		new double quantite = contrat.getEcheancier().getQuantite(step);
		//notre offre initial
		new int stepDeb0 = contrat.getEcheancier().get(0).getStepDebut();
		new double quantite0 = contrat.getEcheancier().get(0).getQuantite(step);
		
		//si on est d'accord avec la proposition:
		if(stepDeb == stepDeb0 && Math.abs(quantite - quantite0) < 0.25*quantite0) {
			// la date intitile gardee et l'écart est inférieur à 25% de la quantité demandée
			return contrat.getEcheancier();
		}
		
		//sinon, on peut soit arreter les negociations, soit reproproposer
		else if(/*acheteur met fin aux négo*/) {
			return null;
			}
		
		else {
			if(quantite-quantite0<0) {
				return Echeancier(int stepDeb0, int contrat.getEcheancier().getStepFin(), double quantite0*(1-0.25));
			}
			else {
				return Echeancier(int stepDeb0, int contrat.getEcheancier().getStepFin(), double quantite0*(1+0.25));
			}
			}
	};

	/**
	 * Methode appelee par le SuperviseurVentesContratCadre lors des negociations sur
	 * le prix a la tonne afin de connaitre la contreproposition de l'acheteur.
	 * L'acheteur peut consulter les precedentes propositions via un appel a la methode 
	 * getListePrixALaTonne() sur le contrat. En particulier la methode getPrixALaTonne()
	 * appelee sur contrat indique la derniere proposition faite par le vendeur.
	 * @param contrat
	 * @return Retourne un prix negatif ou nul si l'acheteur souhaite mettre fin aux negociations
	 * (en renoncant a ce contrat). Retourne le prix actuel (contrat.getPrixALaTonne()) 
	 * si il est d'accord avec ce prix. Sinon, retourne un autre prix correspondant a sa contreproposition.
	 */
	public double contrePropositionPrixAcheteur(ExemplaireContratCadre contrat);
	
	/**
	 * Methode appelee par le SuperviseurVentesContratCadre afin de notifier l'acheteur de la
	 * livraison de quantite tonnes de produit (dans le cadre du contrat contrat). Il se peut que
	 * la quantitee livre soit inferieure a la quantite prevue par le contrat si le vendeur est
	 * dans l'incapacite de la fournir. Dans ce cas, le vendeur aura une penalite (un pourcentage
	 * de produit a livrer en plus).
	 * L'acheteur doit a minima mettre ce produit dans son stock.
	 * @param produit
	 * @param quantite
	 * @param contrat
	 */
	public void receptionner(Object produit, double quantite, ExemplaireContratCadre contrat);

}
