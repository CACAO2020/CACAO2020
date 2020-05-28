package abstraction.eq3Transformateur1;
import abstraction.eq8Romu.cacaoCriee.LotCacaoCriee;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import abstraction.eq8Romu.cacaoCriee.PropositionCriee;
import abstraction.eq8Romu.cacaoCriee.SuperviseurCacaoCriee;
import abstraction.eq8Romu.chocolatBourse.SuperviseurChocolatBourse;
import abstraction.eq8Romu.produits.Feve;
import abstraction.eq8Romu.produits.Gamme;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;

public abstract class AcheteurCacao extends Transformation implements abstraction.eq8Romu.cacaoCriee.IAcheteurCacaoCriee{
	private HashMap<String,Double> nb_proposition;




	/** @author amaury */
	public double proposerAchat(LotCacaoCriee lot) {
		System.out.println(lot.getFeve());
		if((this.getStockFeves().containsKey((lot.getFeve()))==false)||
				(this.getStockFeves((lot.getFeve()))==0)||
				(this.getStockPateInterne().containsKey(this.equivalentChocoFeve(lot.getFeve()))==false)||
				(this.getStockFeves(lot.getFeve())<=this.getStockPateInterne(this.equivalentChocoFeve(lot.getFeve())))){
			if(lot.getPrixMinPourUneTonne()*lot.getQuantiteEnTonnes()<this.getMontantCompte()&&
					(lot.getPrixMinPourUneTonne()<=(Filiere.LA_FILIERE.getIndicateur("BourseChoco cours "+equivalentChocoFeve(lot.getFeve())).getValeur()-10000)*0.9)) {
				if(((lot.getPrixMinPourUneTonne()+(Filiere.LA_FILIERE.getIndicateur("BourseChoco cours "+equivalentChocoFeve(lot.getFeve())).getValeur()-10000)*0.9))/2<this.getMontantCompte()) {
					return ((lot.getPrixMinPourUneTonne()+(Filiere.LA_FILIERE.getIndicateur("BourseChoco cours "+equivalentChocoFeve(lot.getFeve())).getValeur()-10000)*0.9))/2;
				}
				else {
					return lot.getPrixMinPourUneTonne();	
				}
				
			}

		}
		return 0.0;

	}
	/** @author Nathan Olborski
	 * On fait un simple print de la notification de vente ou de refus de vente, on pourra ajouter cette notification au journal quand l'attribut journal sera mis en protected et qu'on y aura accès dans cette classe
	 */
	public void notifierPropositionRefusee(PropositionCriee proposition) {
		System.out.println("La proposition " + proposition.getLot().toString() + ", de " + proposition.getVendeur().toString() + ", au prix" + proposition.getPrixPourLeLot()
		+ "a été refusée");
	}

	public void notifierVente(PropositionCriee proposition) {
		this.setCoutFeves(proposition.getLot().getFeve(), this.calculCoutFeve(proposition.getLot().getFeve(), proposition.getQuantiteEnTonnes(), proposition.getPrixPourUneTonne()));
		this.setStockFeves(proposition.getLot().getFeve(), proposition.getQuantiteEnTonnes());
		System.out.println("Lot " + proposition.getLot().toString() + ", de " + proposition.getVendeur().toString() + ", au prix" + proposition.getPrixPourLeLot() + " a été effectué");

	}

	/** @author Nathan Olborski
	 * Simple getter, on voit ici l'intérêt d'avoir nos variables en protected 
	 */
	public Integer getCryptogramme(SuperviseurCacaoCriee superviseur) {
		if (superviseur != null) {
			return this.cryptogramme;}
		else return null;
	}




}
