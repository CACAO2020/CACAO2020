package abstraction.eq2Producteur2;

import java.util.List;

import abstraction.eq8Romu.cacaoCriee.IVendeurCacaoCriee;
import abstraction.eq8Romu.cacaoCriee.LotCacaoCriee;
import abstraction.eq8Romu.cacaoCriee.PropositionCriee;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;

public class eq2Investisseur extends eq2Vendeur {

	private Variable prixArbre; 

	public eq2Investisseur( Variable prixArbre) {
		super();
		this.prixArbre = prixArbre;
		// TODO Auto-generated constructor stub
	}

	public void AchatArbre(){
		if (Filiere.LA_FILIERE.getBanque().getSolde()) {

		}
	}
	

}
