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
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;

public class AcheteurCacao extends Stock implements abstraction.eq8Romu.cacaoCriee.IAcheteurCacaoCriee{
	private static int NB_INSTANCES = 0; // Afin d'attribuer un nom different a toutes les instances
	private int numero;

	public AcheteurCacao() {
		NB_INSTANCES++;
		this.numero=NB_INSTANCES;
	}
	
	public void next() {
		double total=0.0;
		for (Feve feve :Feve.values()) {
			if (stockFeves.get(feve)!=null) {
				total=total+stockFeves.get(feve);
			}
		}
		this.stockTotalFeves.setValeur(this, total);
	}

/** On ne souhaite qu'acheter des fèves haut de gamme, haut de gamme équitable ou moyenne gamme équitable, on utilise des prix arbitraire pour l'instant
* Nathan Olborski **/

	public double proposerAchat(LotCacaoCriee lot) {
		if ((lot.getFeve() == Feve.FEVE_HAUTE)||(lot.getFeve() == Feve.FEVE_MOYENNE_EQUITABLE)) {
			return 10.0;}
		if (lot.getFeve() == Feve.FEVE_HAUTE_EQUITABLE) {
			return 15.0;}
		return 0.0;
	}
/** On fait un simple print de la notification de vente ou de refus de vente, on pourra ajouter cette notification au journal quand l'attribut journal sera mis en protected et qu'on y aura accès dans cette classe
 Nathan Olborski
 */
	public void notifierPropositionRefusee(PropositionCriee proposition) {
			System.out.println("La proposition " + proposition.getLot().toString() + ", de " + proposition.getVendeur().toString() + ", au prix" + proposition.getPrixPourLeLot()
					+ "a été refusée");
	}
	
	public void notifierVente(PropositionCriee proposition) {
		this.setStockFeves(proposition.getLot().getFeve(), proposition.getQuantiteEnTonnes());
		System.out.println("Lot " + proposition.getLot().toString() + ", de " + proposition.getVendeur().toString() + ", au prix" + proposition.getPrixPourLeLot() + " a été effectué");
		
	}

/** Simple getter, on voit ici l'intérêt d'avoir nos variables en protected 
 Nathan Olborski
 */
	public Integer getCryptogramme(SuperviseurCacaoCriee superviseur) {
		if (superviseur != null) {
			return this.cryptogramme;}
		else return null;
	}




}
