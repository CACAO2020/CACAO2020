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
	private Variable prixTF;
	private Variable prixTT;
	private Variable prixTC;
	private Variable prixTPBG; //il me semble qu'on vendra la pâte en contrat cadre et non pas à la criée, donc les prix des pates à la tonne ne sont peut-être pas utiles ici
	private Variable prixTPHG;
	private Variable propal;
	

	
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
	public LotCacaoCriee getLotEnVente() { //ce que je suis en train de faire : prioriser lots par prix, pour cela, variable prix propositions précédente, propose lot que si prix est inférieur à proposition précédente, dans le cas ou la proposition précédente n'a pas abouti à une vente (on modifie éventuellemnt des trucs à propal dans notifierpasvente)
		List<Variable> Stock = this.getVariables();
	    double masseFora = 0;
	    double masseTrini = 0;
	    double masseCrio = 0;
	    double prixfora = 0;
	    double prixtrini = 0;
	    double prixcrio = 0;
	    for (int i = 0; i < Stock.size();i++) {
	    	if (Stock.get(i).getNom() == "forastero") {
	    		masseFora = masseFora + Stock.get(i).getValeur();
	    	}
	    	if (Stock.get(i).getNom() == "trinitario") {
	    		masseTrini = masseTrini + Stock.get(i).getValeur();
	    	}
	    	if (Stock.get(i).getNom() == "criollo") {
	    		masseCrio = masseCrio + Stock.get(i).getValeur();
	    	}
	    }
	    prixfora = masseFora*this.getPrixTF().getValeur();
	    prixtrini = masseTrini*this.getPrixTT().getValeur();
	    prixcrio = masseCrio*this.getPrixTC().getValeur();
	    if (prixfora > prixtrini && prixfora > prixcrio && masseFora > 0.5) {
	    	if ()
	    		return new LotCacaoCriee(this, Feve.FEVE_BASSE, masseFora, this.getPrixTF().getValeur());
	    }
	    else if (prixtrini > prixfora && prixtrini > prixcrio && masseTrini > 0.5) {
	    	
	    		return new LotCacaoCriee(this, Feve.FEVE_MOYENNE, masseTrini, this.getPrixTT().getValeur());
	    }
	    else if (prixcrio > prixtrini && prixfora < prixcrio && masseCrio > 0.5) {
	    	
	    		return new LotCacaoCriee(this, Feve.FEVE_HAUTE, masseCrio, this.getPrixTC().getValeur());
	    }
	    
	    return null;
	}
	

	@Override
	public void notifierAucuneProposition(LotCacaoCriee lot) {
		// TODO Auto-generated method stub, modifier ici valeur de propal
		
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
	public double getPropal() {
		return this.propal.getValeur();
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
