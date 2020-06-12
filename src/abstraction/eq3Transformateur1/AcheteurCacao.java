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

public abstract class AcheteurCacao extends AchatPateCC implements abstraction.eq8Romu.cacaoCriee.IAcheteurCacaoCriee{
	private HashMap<Feve, Double> nb_proposition;
	
	public AcheteurCacao() {
		nb_proposition = new HashMap<Feve,Double>();
		nb_proposition.put(Feve.FEVE_HAUTE_EQUITABLE,0.0);
		nb_proposition.put(Feve.FEVE_MOYENNE_EQUITABLE,0.0);
		nb_proposition.put(Feve.FEVE_HAUTE,0.0);
		nb_proposition.put(Feve.FEVE_MOYENNE,0.0);
		nb_proposition.put(Feve.FEVE_BASSE,0.0);
	}
	
	/** @author Amaury & Nathan 
	 * On propose un prix qui augmente au fur et à mesure des propositions et en fonction de notre besoin en fève (ie si notre stock est vide)*/
	public double proposerAchat(LotCacaoCriee lot) {
		double prix_moyen = ((lot.getPrixMinPourUneTonne()+(Filiere.LA_FILIERE.getIndicateur("BourseChoco cours "+equivalentChocoFeve(lot.getFeve())).getValeur()-10000)*0.9))/2;
		if((this.getStockFeves().containsKey((lot.getFeve()))==false)||
				(this.getStockFeves((lot.getFeve()))==0)||
				((this.getStockPate().containsKey(this.equivalentChocoFeve(lot.getFeve()))==false)&&
				(this.getStockFeves(lot.getFeve())<=this.getStockPate(this.equivalentChocoFeve(lot.getFeve()))))){
					if(lot.getPrixMinPourUneTonne()*lot.getQuantiteEnTonnes()<this.getMontantCompte()&&
					(nb_proposition.get(lot.getFeve())<=3)) {
						if( lot.getPrixMinPourUneTonne() * lot.getQuantiteEnTonnes() * (10 + nb_proposition.get(lot.getFeve())/10) < this.getMontantCompte()) {
							this.journalAchat.ajouter("Proposition d'achat du lot" + lot.toString() + ", Nombre de propositions = " + nb_proposition.get(lot.getFeve()) + ", au prix :" + lot.getPrixMinPourUneTonne() * lot.getQuantiteEnTonnes() * (10 + nb_proposition.get(lot.getFeve())/10));
							nb_proposition.put(lot.getFeve(),nb_proposition.get(lot.getFeve())+1);
							return lot.getPrixMinPourUneTonne() * lot.getQuantiteEnTonnes() * (10 + nb_proposition.get(lot.getFeve())/10) ; 
						}
					}
					else {
						if (prix_moyen * (6 + nb_proposition.get(lot.getFeve()))/10 < this.getMontantCompte()) {
							this.journalAchat.ajouter("Proposition d'achat du lot" + lot.toString() + ", Nombre de propositions = " + nb_proposition.get(lot.getFeve()) + ", au prix :" + prix_moyen * (6 + nb_proposition.get(lot.getFeve()))/10);
							nb_proposition.put(lot.getFeve(),nb_proposition.get(lot.getFeve())+1);
							return prix_moyen * (6 + nb_proposition.get(lot.getFeve()))/10;	
						}
					}
				}
		this.journalAchat.ajouter("Pas de proposition d'achat pour le lot" + lot.toString() + ", Nombre de propositions = " + nb_proposition.get(lot.getFeve()));
		
		return 0.0;
	}
	
	public void reset_propositions() {
		nb_proposition.clear();
		nb_proposition.put(Feve.FEVE_HAUTE_EQUITABLE,0.0);
		nb_proposition.put(Feve.FEVE_MOYENNE_EQUITABLE,0.0);
		nb_proposition.put(Feve.FEVE_HAUTE,0.0);
		nb_proposition.put(Feve.FEVE_MOYENNE,0.0);
		nb_proposition.put(Feve.FEVE_BASSE,0.0);
	}
	/** @author Nathan Olborski
	 * On fait un simple print de la notification de vente ou de refus de vente, on pourra ajouter cette notification au journal quand l'attribut journal sera mis en protected et qu'on y aura accès dans cette classe
	 */
	public void notifierPropositionRefusee(PropositionCriee proposition) {
		this.journalAchat.ajouter("La proposition " + proposition.getLot().toString() + ", de " + proposition.getVendeur().toString() + ", au prix" + proposition.getPrixPourLeLot()
		+ "a été refusée");
	}

	public void notifierVente(PropositionCriee proposition) {
		//this.reset_propositions();
		this.setCoutFeves(proposition.getLot().getFeve(), this.calculCoutFeve(proposition.getLot().getFeve(), proposition.getQuantiteEnTonnes(), proposition.getPrixPourUneTonne()));
		this.setStockFeves(proposition.getLot().getFeve(), proposition.getQuantiteEnTonnes());
		this.journalAchat.ajouter("Lot " + proposition.getLot().toString() + ", de " + proposition.getVendeur().toString() + ", au prix" + proposition.getPrixPourLeLot() + " a été effectué");

	}

	/** @author Nathan Olborski
	 * Simple getter	*/
	public Integer getCryptogramme(SuperviseurCacaoCriee superviseur) {
		if (superviseur != null) {
			return this.cryptogramme;}
		else return null;
	}




}
