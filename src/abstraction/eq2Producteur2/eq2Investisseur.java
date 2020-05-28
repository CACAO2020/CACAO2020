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
	/** décide si on achète des arbres à ce tour ou non, à appeler à chaque next**/
	public void decideAchatArbres() { //si nbre d'arbres arrive en-dessous d'un minimum et qu'on peut en acheter : on rachète ce type d'arbre, ou bien qu'on a assez d'argent, on achète ce qui s'est le mieux vendu sur toutes les ventes depuis x temps.
		int nbrearbresbasse = 0;
		int nbrearbresmoyenne = 0;
		int nbrearbresmoyenneequitable = 0;
		int nbrearbreshaute = 0;
		int nbrearbreshauteequitable = 0;
		List<PaquetArbres> arbres = this.getPaquetsArbres();
		for (int i = 0; i < arbres.size(); i++) {
			if (arbres.get(i).getType() == Feve.FEVE_BASSE) {
				nbrearbresbasse = nbrearbresbasse + arbres.get(i).getNbreArbres();
			}
			else if (arbres.get(i).getType() == Feve.FEVE_MOYENNE){
				nbrearbresmoyenne = nbrearbresmoyenne + arbres.get(i).getNbreArbres();
			}
			else if (arbres.get(i).getType() == Feve.FEVE_MOYENNE_EQUITABLE) {
				nbrearbresmoyenneequitable = nbrearbresmoyenneequitable + arbres.get(i).getNbreArbres();
			}
			else if (arbres.get(i).getType() == Feve.FEVE_HAUTE) {
				nbrearbreshaute = nbrearbreshaute + arbres.get(i).getNbreArbres();
			}
			else if (arbres.get(i).getType() == Feve.FEVE_HAUTE_EQUITABLE) {
				nbrearbreshauteequitable = nbrearbreshauteequitable + arbres.get(i).getNbreArbres();
			}
		}
		
	}
	/*
	 * Paye les employes en fonction du nombre d'arbres
	 */
	public void PayerEmployes() {
		if (this.NbTotalArbres() > 0) {
			double payeEmployes = this.NbTotalArbres()*0.05;
			Filiere.LA_FILIERE.getBanque().virer(this,this.getCrypto(),Filiere.LA_FILIERE.getBanque(),payeEmployes);
		}
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

