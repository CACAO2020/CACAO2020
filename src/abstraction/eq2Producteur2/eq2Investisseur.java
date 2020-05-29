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
	private double prixUsine;
	private Journal journal_achats;

	public eq2Investisseur() {
		super();
		this.prixUsine=100;
		this.prixArbre = 3;
		this.journal_achats = new Journal("Investissements",this);
	}

	public void AchatArbres(int nbrArbres, Feve feve){
		if (Filiere.LA_FILIERE.getBanque().getSolde(this,this.getCrypto())> nbrArbres*this.prixArbre && nbrArbres > 0) {
			this.ajoutPaquetArbres(new PaquetArbres(nbrArbres, feve));
			Filiere.LA_FILIERE.getBanque().virer(this,this.getCrypto(),Filiere.LA_FILIERE.getBanque(),nbrArbres*this.prixArbre);
			
			this.journal_achats.ajouter("Achat de " + nbrArbres + " arbres de type: " + feve.getGamme());
		}
	}
	/** décide si on achète des arbres à ce tour ou non, à appeler à chaque next**/
	public void decideAchatArbres() { //si nbre d'arbres arrive en-dessous d'un minimum et qu'on peut en acheter : on rachète ce type d'arbre, ou bien qu'on a assez d'argent, on achète ce qui s'est le mieux vendu sur toutes les ventes depuis x temps.
		                              //mettons qu'on commence avec 10 hectares (propriété de taille très raisonnable) --> 11 110 arbres, entre 5 et 6 employés
		                              //on détermine les proportions de fèves qui se sont le mieux vendues les tours précédents pour savoir quoi planter
		int nbrearbres = 0;
		List<PaquetArbres> arbres = this.getPaquetsArbres();
		for (int i = 0; i < arbres.size(); i++) {
			nbrearbres = nbrearbres + arbres.get(i).getNbreArbres();
		}
		double totalventesréalisées = this.getcompteurcrio()+this.getcompteurcrioe()+this.getcompteurfora()+this.getcompteurtrini()+this.getcompteurtrinie();
		if(totalventesréalisées !=0) {
		double proportionfora = this.getcompteurfora()/totalventesréalisées;
		double proportiontrini = this.getcompteurtrini()/totalventesréalisées;
		double proportioncrio = this.getcompteurcrio()/totalventesréalisées;
		double proportiontrinie = this.getcompteurtrinie()/totalventesréalisées;
		double proportioncrioe = this.getcompteurcrioe()/totalventesréalisées;
		
		if (nbrearbres < 11100 && Filiere.LA_FILIERE.getBanque().getSolde(this,this.getCrypto()) > 0) {
			double ecart = 11100 - nbrearbres;
			this.AchatArbres((int)Math.floor(ecart*proportionfora), Feve.FEVE_BASSE);
			this.AchatArbres((int)Math.floor(ecart*proportiontrini), Feve.FEVE_MOYENNE);
			this.AchatArbres((int)Math.floor(ecart*proportiontrinie), Feve.FEVE_MOYENNE_EQUITABLE);
			this.AchatArbres((int)Math.floor(ecart*proportioncrio), Feve.FEVE_HAUTE);
			this.AchatArbres((int)Math.floor(ecart*proportioncrioe), Feve.FEVE_HAUTE_EQUITABLE);
			this.journal_achats.ajouter("on a acheté des arbres car trop peu d'arbres");
		}
		else if ((Filiere.LA_FILIERE.getBanque().getSolde(this,this.getCrypto())) > (this.NbTotalArbres()*0.05)*500) { //pb : achète trop d'arbres qui produisent pas avant 6 ans, faillite
			double investissement_max = Filiere.LA_FILIERE.getBanque().getSolde(this,this.getCrypto())*0.005;
			double nbre_arbresmax = Math.floor(investissement_max/this.getprixArbre());
			this.AchatArbres((int)Math.floor(nbre_arbresmax*proportionfora), Feve.FEVE_BASSE);
			this.AchatArbres((int)Math.floor(nbre_arbresmax*proportiontrini), Feve.FEVE_MOYENNE);
			this.AchatArbres((int)Math.floor(nbre_arbresmax*proportiontrinie), Feve.FEVE_MOYENNE_EQUITABLE);
			this.AchatArbres((int)Math.floor(nbre_arbresmax*proportioncrio), Feve.FEVE_HAUTE);
			this.AchatArbres((int)Math.floor(nbre_arbresmax*proportioncrioe), Feve.FEVE_HAUTE_EQUITABLE);
			this.journal_achats.ajouter("on a acheté des arbres parce qu'on a de la maille");
		}}
		
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

