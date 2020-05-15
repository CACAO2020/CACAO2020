package abstraction.eq2Producteur2;

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

	private int prixArbre; 

	public eq2Investisseur() {
		super();
		this.prixArbre = 3;
		// TODO Auto-generated constructor stub
	}

	public void AchatArbres(int nbrArbres, Feve feve){
		if (Filiere.LA_FILIERE.getBanque().getSolde(this,this.getCrypto())> nbrArbres*this.prixArbre) {
			this.ajoutPaquetArbres(new PaquetArbres(nbrArbres, feve));
			Filiere.LA_FILIERE.getBanque().virer(this,this.getCrypto(),Filiere.LA_FILIERE.getBanque(),nbrArbres*this.prixArbre);
		}
	}
	

}

