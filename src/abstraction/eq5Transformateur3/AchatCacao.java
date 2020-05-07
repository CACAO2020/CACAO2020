package abstraction.eq5Transformateur3;

import abstraction.eq8Romu.cacaoCriee.LotCacaoCriee;
import abstraction.eq8Romu.cacaoCriee.PropositionCriee;
import abstraction.eq8Romu.produits.Gamme;
/** @author Eva DUPUY  */
public class AchatCacao {
    /**
     * Classe qui régit tous les achats de fèves, à la criée et aléatoire
     * Elle est agrégé dans la classe Transformateur3.
     */
	
	//Ces nombres influencent le prix proposé dans proposer achat
		private double NB_propositions_refusees;
		private double NB_precedent;
		private double prix; 
		private Transformateur3 acteur;
		
		public AchatCacao(Transformateur3 acteur) {
			this.acteur = acteur;
			this.NB_propositions_refusees = 0;
			this.NB_precedent = 0;
	}
	
	//achète le lot au prix minimum si les feves sont haut de gamme et equitables
		//Il faut ajouter une condition sur les stocks et la trésorerie
	//
	
	public double proposerAchat(LotCacaoCriee lot) {
		if (lot.getFeve().isEquitable() && lot.getFeve().getGamme()== Gamme.HAUTE) {
			prix = lot.getPrixMinPourUneTonne()*lot.getQuantiteEnTonnes();
			
			if (NB_propositions_refusees == NB_precedent && acteur.getTresorier().InvestissementMaxHautDeGamme()<= prix) {
				return prix ; //vrai que pour l'initialisation
			}
			
			else if (NB_propositions_refusees > NB_precedent && acteur.getTresorier().InvestissementMaxHautDeGamme()<= prix) {  //derniere proposition acceptee
				return prix;
			}
			
			//(NB_propositions_refusees < NB_precedent)  //la dernière proposition a été refusée
			else { 
				prix = prix*(1 + NB_propositions_refusees)*1.5;
				if ( acteur.getTresorier().InvestissementMaxHautDeGamme()<= prix) {
				return prix ; //plus on a de prop refusees, plus on augmente le prix
				// 1.5 est un coefficient choisi au hasard pour debuter
				}
				else {
					return 0.0;
				}
			}	
		}
		else {
			return 0.0;
		}
	}
	
	
	public void notifierPropositionRefusee(PropositionCriee proposition) {
		NB_precedent = NB_propositions_refusees;
		NB_propositions_refusees = +1;
	}

	
	
//getCryptogramme renvoyé par transformateur donc pas besoin de l'implémenter ici
	
	
	
	//Ne pas oblier les conséquences sur la trésorerie
	
	//diminue le nombre de propositions refusées donc on peut diminuer le prix de proposition
	//ajoute les feves du lot au stock de feves de l'entprise
	
	public void notifierVente(PropositionCriee proposition, Stock stockFeves) {
		NB_precedent = NB_propositions_refusees;
		NB_propositions_refusees = NB_propositions_refusees - 1;
		stockFeves.ajoutFeves(proposition.getFeve(), proposition.getQuantiteEnTonnes()) ;
		//Tresorerie
	}
	
	
	
	
	
	
	
}