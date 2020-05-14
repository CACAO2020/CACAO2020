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
	private double prixvente;
	private Variable propalsnonvendues;
	

	
	public eq2Vendeur() {
		super();
		//pour l'instant tous les prix sont initialises a 0
		this.prixTF = new Variable("prixTF",this,0);
		this.prixTT = new Variable("prixTT",this,0);
		this.prixTC = new Variable("prixTC",this,0);
		this.prixvente = 0;
		this.propalsnonvendues = new Variable("propalsnonvendues",this,999999999); //première valeur super haute pour permettre ventes
	}
	/*faudrait rajouter un truc qui set les prix en fonction de ce qu'il s'est passé au cycle d'avant et de notre rentabilité
	 * 
	 */

	/*On vend dès qu'on a du stock
	 * 
	 */
	public LotCacaoCriee getLotEnVente() { //le -500 est une valeur arbitraire
		List<Variable> Stock = this.getVariablesFeve(); 
	    double masseFora = 0;
	    double masseTrini = 0;
	    double masseCrio = 0;
	    double prixfora = 0;
	    double prixtrini = 0;
	    double prixcrio = 0;
	    for (int i = 0; i < Stock.size();i++) { //changer ça vu que c'est plus une liste de couples mais un dictionnaire (en fait y'a peut-être pas besoin de le changer)
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
	    	if (this.getPropal()>prixfora) {
	    		this.setPrixVente(prixfora);
	    		return new LotCacaoCriee(this, Feve.FEVE_BASSE, masseFora, this.getPrixTF().getValeur());
	    	}
	    	else {
	    		this.setPrixVente(this.getPropal()-500);
	    		return new LotCacaoCriee(this, Feve.FEVE_BASSE, (this.getPropal()-500)/this.getPrixTF().getValeur(), this.getPrixTF().getValeur());
	    	}
	    		
	    }
	    else if (prixtrini > prixfora && prixtrini > prixcrio && masseTrini > 0.5) {
	    	if (this.getPropal()>prixtrini) {
	    		this.setPrixVente(prixtrini);
	    		return new LotCacaoCriee(this, Feve.FEVE_MOYENNE, masseTrini, this.getPrixTT().getValeur());
	    	}
	    	else {
	    		this.setPrixVente(this.getPropal()-500);
	    		return new LotCacaoCriee(this, Feve.FEVE_MOYENNE, (this.getPropal()-500)/this.getPrixTT().getValeur(), this.getPrixTT().getValeur());
	    	}
	    		
	    }
	    else if (prixcrio > prixtrini && prixfora < prixcrio && masseCrio > 0.5) {
	    	if (this.getPropal()>prixcrio) {
	    		this.setPrixVente(prixcrio);
	    		return new LotCacaoCriee(this, Feve.FEVE_HAUTE, masseCrio, this.getPrixTC().getValeur());
	    	}
	    	else {
	    		this.setPrixVente(this.getPropal()-500);
	    		return new LotCacaoCriee(this, Feve.FEVE_HAUTE, (this.getPropal()-500)/this.getPrixTC().getValeur(), this.getPrixTC().getValeur());
	    	}
	    		
	    }
	    
	    return null;
	}
	

	@Override
	public void notifierAucuneProposition(LotCacaoCriee lot) { //500 encore arbitraire, surement trop élevée
		this.setPropal(this.getPrixVente()-500);
		
	}

	@Override
	public PropositionCriee choisir(List<PropositionCriee> propositions) { // récupère l'offre d'achat la plus élevée et l'accepte si écart prix <= 5%
		int indice = 0;
		double max_actuel = 0;
		for (int i = 0; i < propositions.size(); i++) {
			if (max_actuel < propositions.get(i).getPrixPourLeLot()) {
				max_actuel = propositions.get(i).getPrixPourLeLot();
				indice = i;
			}
		}
		if (propositions.get(indice).getLot().getFeve()==Feve.FEVE_BASSE) {
			if ((Math.abs(propositions.get(indice).getPrixPourUneTonne() - this.getPrixTF().getValeur()))/100 <= 0.05) {
				return propositions.get(indice);
			}
		}
		else if (propositions.get(indice).getLot().getFeve()==Feve.FEVE_MOYENNE) {
			if ((Math.abs(propositions.get(indice).getPrixPourUneTonne() - this.getPrixTT().getValeur()))/100 <= 0.05) {
				return propositions.get(indice);
			}
		}
		else if (propositions.get(indice).getLot().getFeve()==Feve.FEVE_HAUTE) {
			if ((Math.abs(propositions.get(indice).getPrixPourUneTonne() - this.getPrixTC().getValeur()))/100 <= 0.05) {
				return propositions.get(indice);
			}
		}
		return null;
	}

	@Override
	public void notifierVente(PropositionCriee proposition) { //enlève fèves vendues de notre stock
		removeQtFeve(proposition.getLot().getFeve(), proposition.getLot().getQuantiteEnTonnes());
		
	}
	
	public double getPrixVente() {
		return this.prixvente;
	}
	
	public void setPrixVente(double prix) {
		this.prixvente = prix;
	}
	
	public double getPropal() {
		return this.propalsnonvendues.getValeur();
	}
	
	public void setPropal(double propal) {
		this.propalsnonvendues.setValeur(this, propal);
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


}
	


