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


	public AcheteurCacao() {
		HashMap<String,Double> map = new HashMap<String,Double>();
		map.put("Haute", (double) 0);
		map.put("Moyenne_equitable", (double) 0);
		map.put("Haute_equitable",(double) 0);
		map.put("Moyenne", (double) 0);
		this.nb_proposition = map;
	}
	
	/** @author K. GUTIERREZ  */
	public double coefficient(Feve feve) {
		if (feve.getGamme() == Gamme.HAUTE) {
			if (feve.isEquitable()) {
				return 1;
			}
			else {
				return 0.85;
			}
		}
		else if (feve.getGamme() == Gamme.MOYENNE) {
			if (feve.isEquitable()) {
				return 0.85;
			}
			else {
				return 0.7;
			}
		}
		return 0;
	}
	
	/** @author K. GUTIERREZ  */
	public String gammeString(Feve feve) {
		if (feve.getGamme() == Gamme.HAUTE) {
			if (feve.isEquitable()) {
				return "Haute_equitable";
			}
			else {
				return "Haute";
			}
		}
		else if (feve.getGamme() == Gamme.MOYENNE) {
			if (feve.isEquitable()) {
				return "Moyenne_equitable";
			}
			else {
				return "Moyenne";
			}
		}
		return "";
	}
	


/** @author Nathan Olborski 
 * On garde en mémoire le nombre de propositions faites pour chaque type de fève qu'on souhaite acheter avec une HashMap,
 * Ensuite on augmente le prix à chaque fois qu'une proposition est refusée en commençant avec le prix minimum ou un prix plus bas

 */

	public double proposerAchat(LotCacaoCriee lot, double cours) {
		if (lot.getPrixMinPourUneTonne()*lot.getQuantiteEnTonnes()<=(cours*lot.getQuantiteEnTonnes() - 10000)*0.9) {
			double prix = lot.getPrixMinPourUneTonne()*lot.getQuantiteEnTonnes()*(coefficient(lot.getFeve())+0.15*nb_proposition.get(gammeString(lot.getFeve())));
			nb_proposition.replace(gammeString(lot.getFeve()), nb_proposition.get(gammeString(lot.getFeve())) + 1);
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
