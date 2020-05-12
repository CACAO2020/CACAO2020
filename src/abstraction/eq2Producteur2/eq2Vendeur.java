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
	 *Kristof S
	 */
	private double masse_en_vente;
	private Variable prixTF;
	private Variable prixTT;
	private Variable prixTC;
	private Variable prixTPBG; //il me semble qu'on vendra la pâte en contrat cadre et non pas à la criée, donc les prix des pates à la tonne ne sont peut-être pas utiles ici
	private Variable prixTPHG;
	private Variable propal_masses;

	
	public eq2Vendeur() {
		super();
		//pour l'instant tous les prix sont initialises a 0
		this.prixTF = new Variable("prixTF",this,0);
		this.prixTT = new Variable("prixTT",this,0);
		this.prixTC = new Variable("prixTC",this,0);
		this.prixTPBG = new Variable("prixTPBG",this,0);
		this.prixTPHG = new Variable("prixTPHG",this,0);
	}

	/*On vend dès qu'on a du stock
	 * 
	 */
	public LotCacaoCriee getLotEnVente() {
		List<Variable> masses = this.getStock();
		if (masses.get(0).getValeur() >= 0.5 || masses.get(1).getValeur()>=0.5 || masses.get(2).getValeur()>=0.5) {
			List<Double> m_feves = new ArrayList<Double>();
			for (int i = 0; i < 2; i++) {
				m_feves.add(masses.get(i).getValeur());
			}
		    double max = Math.max(m_feves.get(0),m_feves.get(1));
		    double vraimax = Math.max(max, m_feves.get(2));
		    int indice_max = m_feves.indexOf(vraimax);
		    if (indice_max == 0) {
		    	this.setMasseEnVente(m_feves.get(0));
		    	this.setPropalMasses(this.getMasseEnVente());
		    	return new LotCacaoCriee(this, Feve.FEVE_BASSE, m_feves.get(0), this.getPrixTF().getValeur());
		    }
		    else if (indice_max == 1) {
		    	this.setMasseEnVente(m_feves.get(1));
		    	this.setPropalMasses(this.getMasseEnVente());
		    	return new LotCacaoCriee(this, Feve.FEVE_MOYENNE, m_feves.get(1), this.getPrixTT().getValeur());
		    }
		    else if (indice_max == 2) {
		    	this.setMasseEnVente(m_feves.get(2));
		    	this.setPropalMasses(this.getMasseEnVente());
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
	public Variable getPropalMasses() {
		return this.propal_masses;
	}
	
	public void setPropalMasses(double masse) {
		propal_masses.setValeur(this, masse);
	}

	public double getMasseEnVente() {
		return this.masse_en_vente;
	}
	public void setMasseEnVente(double masse) {
		this.masse_en_vente = masse;
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
