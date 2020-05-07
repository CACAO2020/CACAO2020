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

	public eq2Investisseur(IActeur createur, List<PaquetArbres> Arbres,Journal journal,double init1, double init2, double init3, double init4, double init5,double init6,double init7, double init8, double init9, double init10, Variable prixTF, Variable prixTT, Variable prixTC, Variable prixTPBG, Variable prixTPHG, Variable prixArbre) {
		super(createur, Arbres ,journal,init1, init2, init3, init4, init5,init6,init7,init8,init9,init10, prixTF, prixTT,prixTC,prixTPBG, prixTPHG);
		this.prixArbre = prixArbre;
		// TODO Auto-generated constructor stub
	}

	public void AchatArbre(){
		if (Filiere.LA_FILIERE.getBanque().getSolde()) {

		}
	}
	

}
