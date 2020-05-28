package abstraction.eq3Transformateur1;
import abstraction.eq8Romu.cacaoCriee.LotCacaoCriee;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import abstraction.eq8Romu.cacaoCriee.PropositionCriee;
import abstraction.eq8Romu.cacaoCriee.SuperviseurCacaoCriee;
import abstraction.eq8Romu.produits.Feve;
import abstraction.eq8Romu.produits.Gamme;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;

public abstract class AcheteurCacao extends Transformation implements abstraction.eq8Romu.cacaoCriee.IAcheteurCacaoCriee{
	private HashMap<String,Double> nb_proposition;


	public AcheteurCacao() {
		HashMap<String,Double> map = new HashMap<String,Double>();
		map.put("Haute", (double) 0);
		map.put("Moyenne_equitable", (double) 0);
		map.put("Haute_equitable",(double) 0);
		map.put("Moyenne", (double) 0);
		this.nb_proposition = map;
	}
	


/** @author Nathan Olborski 
 * On garde en mémoire le nombre de propositions faites pour chaque type de fève qu'on souhaite acheter avec une HashMap,
 * Ensuite on augmente le prix à chaque fois qu'une proposition est refusée en commençant avec le prix minimum ou un prix plus bas

 */

	public double proposerAchat(LotCacaoCriee lot) {
		if ((lot.getFeve().getGamme() == Gamme.HAUTE)&&(lot.getFeve().isEquitable())) {
			double prix = lot.getPrixMinPourUneTonne()*lot.getQuantiteEnTonnes()*(1.00+0.15*nb_proposition.get("Haute_equitable"));
			nb_proposition.replace("Haute_equitable", nb_proposition.get("Haute_equitable") + 1);
			return prix;
		}
		else if ((lot.getFeve().getGamme() == Gamme.HAUTE)&&(!lot.getFeve().isEquitable())) {
			double prix = lot.getPrixMinPourUneTonne()*lot.getQuantiteEnTonnes()*(0.85+0.15*nb_proposition.get("Haute"));
			nb_proposition.replace("Haute", nb_proposition.get("Haute") + 1);
			return prix;
		}
		else if ((lot.getFeve().getGamme() == Gamme.MOYENNE)&&(lot.getFeve().isEquitable())) {
			double prix = lot.getPrixMinPourUneTonne()*lot.getQuantiteEnTonnes()*(0.85+0.15*nb_proposition.get("Moyenne_equitable"));
			nb_proposition.replace("Moyenne_equitable", nb_proposition.get("Moyenne_equitable") + 1);
			return prix;
		}
		else if ((lot.getFeve().getGamme() == Gamme.MOYENNE)&&(!lot.getFeve().isEquitable())) {
			double prix = lot.getPrixMinPourUneTonne()*lot.getQuantiteEnTonnes()*(0.7+0.15*nb_proposition.get("Moyenne"));
			nb_proposition.replace("Moyenne", nb_proposition.get("Moyenne") + 1);
			return prix;
		}
		return 0;
		
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
