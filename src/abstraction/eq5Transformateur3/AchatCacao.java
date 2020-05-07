package abstraction.eq5Transformateur3;

import abstraction.eq8Romu.cacaoCriee.LotCacaoCriee;
import abstraction.eq8Romu.cacaoCriee.PropositionCriee;
import abstraction.eq8Romu.cacaoCriee.SuperviseurCacaoCriee;
import abstraction.eq8Romu.produits.Gamme;
/** @author Eva DUPUY  */
public class AchatCacao {
    /**
     * Classe qui régit tous les achats de fèves, à la criée et aléatoire
     * Elle est agrégé dans la classe Transformateur3.
     */
    //aléatoire	
	public double quantiteDesiree(double quantiteEnVente, double prix) {	
		return 0;
	}
	public void quantiteLivree(double quantiteLivree) {
	}
	
    //en criée
	
	
	//achète le lot au prix minimum si les feves sont haut de gamme et equitables
		//Il faut ajouter une condition sur les stocks et la trésorerie
	public double proposerAchat(LotCacaoCriee lot) {
		if (lot.getFeve().isEquitable() && lot.getFeve().getGamme()== Gamme.HAUTE) {
			return lot.getPrixMinPourUneTonne()*lot.getQuantiteEnTonnes() ;
		}
		else {
			return 0;
		}
	}
	
	//Ce nombre devra influencer le prix proposé dans proposer achat
	private double NB_propositions_refusees;
	
	public void notifierPropositionRefusee(PropositionCriee proposition) {
		NB_propositions_refusees = +1;
	}

	
	// PAS COMPRIS - REPRENDRE
	public Integer getCryptogramme(SuperviseurCacaoCriee superviseur) {
		if (superviseur != null) {
			return this.getCryptogramme(superviseur);
		}
		return null;
	}

	//Ne pas oblier les conséquences sur la trésorerie
	
	//diminue le nombre de propositions refusées donc on peut diminuer le prix de proposition
	//ajoute les feves du lot au stock de feves de l'entprise
	
	public void notifierVente(PropositionCriee proposition, Stock stockFeves) {
		NB_propositions_refusees = NB_propositions_refusees - 1;
		stockFeves.ajoutFeves(proposition.getFeve(), proposition.getQuantiteEnTonnes()) ;
		//Tresorerie
	}
	
	
	
	
	
	
	
}