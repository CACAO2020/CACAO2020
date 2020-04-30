package abstraction.eq5Transformateur3;

import abstraction.eq8Romu.cacaoCriee.LotCacaoCriee;
import abstraction.eq8Romu.cacaoCriee.PropositionCriee;
import abstraction.eq8Romu.cacaoCriee.SuperviseurCacaoCriee;
import abstraction.eq8Romu.produits.Gamme;

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
	
	public double proposerAchat(LotCacaoCriee lot) {
		if (lot.getFeve().isEquitable() && lot.getFeve().getGamme()== Gamme.HAUTE) {
			return lot.getPrixMinPourUneTonne() ;
		}
		return 0;
	}
//achète le lot au prix minimum si les feves sont haut de gamme et equitables
	//Il faut ajouter une condition sur les stocks et la trésorerie
	
	public void notifierPropositionRefusee(PropositionCriee proposition) {
		

	}

	
	public Integer getCryptogramme(SuperviseurCacaoCriee superviseur) {
		
		return null;
	}

	
	public void notifierVente(PropositionCriee proposition) {
		

	}
}