package abstraction.eq2Producteur2;

import java.util.ArrayList;
import java.util.List;

import abstraction.eq8Romu.cacaoCriee.IVendeurCacaoCriee;
import abstraction.eq8Romu.cacaoCriee.LotCacaoCriee;
import abstraction.eq8Romu.cacaoCriee.PropositionCriee;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;
import abstraction.eq8Romu.produits.Feve;

public class eq2Investisseur extends eq2Vendeur {

	private double prixArbre;
	private Journal journal_achats;

	public eq2Investisseur() {
		super();
		this.prixArbre = 3;
		this.journal_achats = new Journal("Investissements",this);
	}

	public void AchatArbres(int nbrArbres, Feve feve){
		if (Filiere.LA_FILIERE.getBanque().getSolde(this,this.getCrypto())> nbrArbres*this.prixArbre) {
			this.ajoutPaquetArbres(new PaquetArbres(nbrArbres, feve));
			Filiere.LA_FILIERE.getBanque().virer(this,this.getCrypto(),Filiere.LA_FILIERE.getBanque(),nbrArbres*this.prixArbre);
			
			this.journal_achats.ajouter("Achat de " + nbrArbres + " arbres de type: " + feve.getGamme());
		}
	}
	/*
	 * Paye les employes en fonction du nombre d'arbres
	 */
	public void PayerEmployes() {
		double payeEmployes = this.NbTotalArbres()*0.05;
		Filiere.LA_FILIERE.getBanque().virer(this,this.getCrypto(),Filiere.LA_FILIERE.getBanque(),payeEmployes);
	}
	public double getprixArbre() {
		return this.prixArbre;
	}
	public List<Journal> getJournaux() {
		List<Journal> res=new ArrayList<Journal>();
		res.addAll(super.getJournaux());
		res.add(this.journal_achats);
		return res;
	}
}

