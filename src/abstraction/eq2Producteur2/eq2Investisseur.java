package abstraction.eq2Producteur2;

import java.util.List;

import abstraction.eq8Romu.cacaoCriee.IVendeurCacaoCriee;
import abstraction.eq8Romu.cacaoCriee.LotCacaoCriee;
import abstraction.eq8Romu.cacaoCriee.PropositionCriee;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;
import abstraction.fourni.Variable;

public class eq2Investisseur extends eq2Vendeur {

	private int prixArbre; 

	public eq2Investisseur(IActeur createur, double init1, double init2, double init3, double init4, double init5, Variable prixTF, Variable prixTT, Variable prixTC, Variable prixTPBG, Variable prixTPHG, Variable prixArbre) {
		super(createur, init1, init2, init3, init4, init5, prixTF, prixTT, prixTC, prixTPBG, prixTPHG);
		this.prixArbre = 3;
		// TODO Auto-generated constructor stub
	}

	public void AchatArbres(int nbrArbres, String type){
		if (Filiere.LA_FILIERE.getBanque().getSolde(this,this.getCrypto())> nbrArbres*this.prixArbre) {
			this.ajoutPaquetArbres(new PaquetArbres(nbrArbres, type));
			Filiere.LA_FILIERE.getBanque().virer(this,this.getCrypto(),Filiere.LA_FILIERE.getBanque(),nbrArbres*this.prixArbre)
		}
	}
	

}

