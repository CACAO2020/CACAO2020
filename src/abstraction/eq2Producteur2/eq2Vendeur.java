package abstraction.eq2Producteur2;

import java.util.List;

import abstraction.eq8Romu.cacaoCriee.IVendeurCacaoCriee;
import abstraction.eq8Romu.cacaoCriee.LotCacaoCriee;
import abstraction.eq8Romu.cacaoCriee.PropositionCriee;
import abstraction.fourni.IActeur;

public class eq2Vendeur extends eq2Stock implements IVendeurCacaoCriee {
	/*Lucas Y
	 * 
	 */
	public eq2Vendeur(IActeur createur, double init1, double init2, double init3, double init4, double init5) {
		super(createur, init1, init2, init3, init4, init5);
	}

	@Override
	public LotCacaoCriee getLotEnVente() {
		// TODO Auto-generated method stub
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
