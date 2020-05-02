package abstraction.eq2Producteur2;

import java.util.List;

import abstraction.eq8Romu.cacaoCriee.IVendeurCacaoCriee;
import abstraction.eq8Romu.cacaoCriee.LotCacaoCriee;
import abstraction.eq8Romu.cacaoCriee.PropositionCriee;
import abstraction.fourni.IActeur;
import abstraction.fourni.Variable;

public class eq2Vendeur extends eq2Stock implements IVendeurCacaoCriee {
	/*Lucas Y
	 * 
	 */
	private Variable prixTF;
	private Variable prixTT;
	private Variable prixTC;
	private Variable prixTPBG;
	private Variable prixTPHG;
	
	public eq2Vendeur(IActeur createur, double init1, double init2, double init3, double init4, double init5, Variable prixTF, Variable prixTT, Variable prixTC, Variable prixTPBG, Variable prixTPHG) {
		super(createur, init1, init2, init3, init4, init5);
		this.prixTF = prixTF;
		this.prixTT = prixTT;
		this.prixTC = prixTC;
		this.prixTPBG = prixTPBG;
		this.prixTPHG = prixTPHG;
	}

	/*On vend dès qu'on a du stock
	 * 
	 */
	public LotCacaoCriee getLotEnVente() {
		// voir le pdf des protocoles, mais en gros faut prioriser les lots (par masse par ex), je (lucas y) m'en occupe
		return null;
	}

	@Override
	public void notifierAucuneProposition(LotCacaoCriee lot) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PropositionCriee choisir(List<PropositionCriee> propositions) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void notifierVente(PropositionCriee proposition) {
		// TODO Auto-generated method stub
		
	}

}
