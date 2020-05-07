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

public class AcheteurCacao extends ActeurEQ3 implements abstraction.eq8Romu.cacaoCriee.IAcheteurCacaoCriee{
	private static int NB_INSTANCES = 0; // Afin d'attribuer un nom different a toutes les instances
	private int numero;
	private Variable totalStocksFeves;
	protected Map<Feve, Double> stocksFeves;
	protected Integer cryptogramme;
	protected Journal journal;

	public AcheteurCacao() {
		NB_INSTANCES++;
		this.numero=NB_INSTANCES;
		this.totalStocksFeves=new Variable(getNom()+" total stocks feves", this, 50);
		stocksFeves=new HashMap<Feve, Double>();
		for (Feve feve : Feve.values()) {
			stocksFeves.put(feve, 0.0);
		}
		this.journal = new Journal(this.getNom()+" activites", this);
}
	
	public void next() {
		double total=0.0;
		for (Feve feve :Feve.values()) {
			if (stocksFeves.get(feve)!=null) {
				total=total+stocksFeves.get(feve);
			}
		}
		this.totalStocksFeves.setValeur(this, total);
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

	public void notifierPropositionRefusee(PropositionCriee proposition) {
			System.out.println("La proposition " + proposition.getLot() + ", de " + proposition.getAcheteur() + ", au prix" + proposition.getPrixPourLeLot()
					+ "a été refusée");
	}

	public Integer getCryptogramme(SuperviseurCacaoCriee superviseur) {
		if (superviseur != null) {
			return this.cryptogramme;}
		else return null;
	}

	public void notifierVente(PropositionCriee proposition) {
		this.			
	}


}
