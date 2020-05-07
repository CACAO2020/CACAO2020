package abstraction.eq2Producteur2;

import java.util.ArrayList;
import java.util.List;

import abstraction.eq8Romu.cacaoCriee.IVendeurCacaoCriee;
import abstraction.eq8Romu.cacaoCriee.LotCacaoCriee;
import abstraction.eq8Romu.cacaoCriee.PropositionCriee;
import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;
import abstraction.eq8Romu.produits.Feve;
import abstraction.eq8Romu.produits.Pate;
import abstraction.eq8Romu.produits.Gamme;

public class eq2Vendeur extends eq2Stock implements IVendeurCacaoCriee {
	/*Lucas Y
	 * 
	 */
	private Variable prixTF;
	private Variable prixTT;
	private Variable prixTC;
	private Variable prixTPBG;
	private Variable prixTPHG;
	
	public eq2Vendeur(IActeur createur,List<PaquetArbres> Arbres,Journal journal, double init1, double init2, double init3, double init4, double init5, double init6,double init7, double init8, double init9, double init10,Variable prixTF, Variable prixTT, Variable prixTC, Variable prixTPBG, Variable prixTPHG) {
		super(createur, Arbres , journal,init1, init2, init3, init4, init5,init6,init7,init8,init9,init10);
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
		List<Variable> masses = this.getVariables();
		if (masses.get(0).getValeur() >= 0.5 || masses.get(1).getValeur()>=0.5 || masses.get(2).getValeur()>=0.5) {
			List<Double> m_feves = new ArrayList<Double>();
			for (int i = 0; i < 2; i++) {
				m_feves.add(masses.get(i).getValeur());
			}
		    double max = Math.max(m_feves.get(0),m_feves.get(1));
		    double vraimax = Math.max(max, m_feves.get(2));
		    int indice_max = m_feves.indexOf(vraimax);
		    if (indice_max == 0) {
		    	return new LotCacaoCriee(this, Feve.FEVE_BASSE, m_feves.get(0), this.getPrixTF().getValeur());
		    }
		    else if (indice_max == 1) {
		    	return new LotCacaoCriee(this, Feve.FEVE_MOYENNE, m_feves.get(1), this.getPrixTT().getValeur());
		    }
		    else if (indice_max == 2) {
		    	return new LotCacaoCriee(this, Feve.FEVE_HAUTE, m_feves.get(2), this.getPrixTC().getValeur());
		    }
		}
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

	/**
	 * @return the prixTF
	 */
	public Variable getPrixTF() {
		return prixTF;
	}

	/**
	 * @param prixTF the prixTF to set
	 */
	public void setPrixTF(Variable prixTF) {
		this.prixTF = prixTF;
	}

	/**
	 * @return the prixTT
	 */
	public Variable getPrixTT() {
		return prixTT;
	}

	/**
	 * @param prixTT the prixTT to set
	 */
	public void setPrixTT(Variable prixTT) {
		this.prixTT = prixTT;
	}

	/**
	 * @return the prixTC
	 */
	public Variable getPrixTC() {
		return prixTC;
	}

	/**
	 * @param prixTC the prixTC to set
	 */
	public void setPrixTC(Variable prixTC) {
		this.prixTC = prixTC;
	}

	/**
	 * @return the prixTPBG
	 */
	public Variable getPrixTPBG() {
		return prixTPBG;
	}

	/**
	 * @param prixTPBG the prixTPBG to set
	 */
	public void setPrixTPBG(Variable prixTPBG) {
		this.prixTPBG = prixTPBG;
	}

	/**
	 * @return the prixTPHG
	 */
	public Variable getPrixTPHG() {
		return prixTPHG;
	}

	/**
	 * @param prixTPHG the prixTPHG to set
	 */
	public void setPrixTPHG(Variable prixTPHG) {
		this.prixTPHG = prixTPHG;
	}
	

}
