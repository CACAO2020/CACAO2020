package abstraction.eq7Distributeur2;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import abstraction.eq8Romu.cacaoCriee.IAcheteurCacaoCriee;
import abstraction.eq8Romu.cacaoCriee.LotCacaoCriee;
import abstraction.eq8Romu.cacaoCriee.PropositionCriee;
import abstraction.eq8Romu.cacaoCriee.SuperviseurCacaoCriee;
import abstraction.eq8Romu.produits.Feve;
import abstraction.fourni.Filiere;
import abstraction.fourni.Journal;

public class AcheteurCacaoCriee extends AcheteurCacaoCrieeAbs implements IAcheteurCacaoCriee {
	private Map<Feve, Double> prixCourant;

	public AcheteurCacaoCriee() {
		super();
		prixCourant=new HashMap<Feve, Double>();
		for (Feve feve : Feve.values()) {
			prixCourant.put(feve, 0.6);
		}
	}

	public Integer getCryptogramme(SuperviseurCacaoCriee superviseur) {
		if (superviseur!=null) { // Personne ne peut creer un second SuperviseurCacaoCriee --> il s'agit bien de l'unique superviseur et on peut lui faire confiance
			return cryptogramme;
		}
		return Integer.valueOf(0);
	}

	public double proposerAchat(LotCacaoCriee lot) {
		double prix = this.prixCourant.get(lot.getFeve())*0.95; // on tente de payer un peu moins
		double solde = Filiere.LA_FILIERE.getBanque().getSolde(this, cryptogramme);
		if (solde>lot.getQuantiteEnTonnes()*prix) {
			this.journal.ajouter("Propose "+Journal.doubleSur(prix,  4)+" pour "+Journal.texteColore(lot.getVendeur(), Journal.doubleSur(lot.getQuantiteEnTonnes(), 2)+" tonnes de "+lot.getFeve().name()));
			return prix;
		} else {
			this.journal.ajouter("Ne souhaite pas acheter un lot de "+Journal.texteColore(lot.getVendeur(), Journal.doubleSur(lot.getQuantiteEnTonnes(), 2)+" tonnes de "+lot.getFeve().name()));
			return 0.0;
		}
	}

	public void notifierPropositionRefusee(PropositionCriee proposition) {
		double nouveauPrix = Math.max(0.01,  this.prixCourant.get(proposition.getLot().getFeve())*1.02);
		this.prixCourant.put(proposition.getLot().getFeve(), nouveauPrix);
		this.journal.ajouter("Apprend que sa proposition de "+Journal.doubleSur(proposition.getPrixPourUneTonne(), 4)+" pour "+Journal.texteColore(proposition.getVendeur(), Journal.doubleSur(proposition.getQuantiteEnTonnes(), 2)+" tonnes de "+proposition.getFeve().name())+Journal.texteColore(Color.red, Color.white, " a ete refusee"));
		this.journal.ajouter("--> augmente le prix de 2% --> "+Journal.doubleSur(nouveauPrix, 4));
	}

	public void notifierVente(PropositionCriee proposition) {
		this.stocksFeves.put(proposition.getFeve(), this.stocksFeves.get(proposition.getFeve())+proposition.getQuantiteEnTonnes());
		this.journal.ajouter("Apprend que sa proposition de "+Journal.doubleSur(proposition.getPrixPourUneTonne(), 4)+" pour "+Journal.texteColore(proposition.getVendeur(), Journal.doubleSur(proposition.getQuantiteEnTonnes(), 2)+" tonnes de "+proposition.getFeve().name())+Journal.texteColore(Color.green, Color.black," a ete acceptee"));
		this.journal.ajouter("--> le stock de feve passe a "+Journal.doubleSur(this.stocksFeves.get(proposition.getFeve()), 4));
	}
}