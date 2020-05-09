package abstraction.eq6Distributeur1;

import abstraction.fourni.IActeur;
import abstraction.fourni.Variable;

public class CompteBancaire extends Variable {
	private double compte;
	
	//Cette classe sert à gérer notre compte bancaire pour y imposer des règles propres au compte
	
	
	public CompteBancaire(String nom , IActeur createur, double compte) {
		super(nom, createur, compte);
	}
	
	/**  */
	public CompteBancaire(String nom, IActeur createur) {
		super(nom, createur);
	}
	
	/**  */
	public double getCompteBancaire() {
		return this.getValeur();
	}
	
	/**
	 */
	public double Payer (IActeur auteur, double paiement) {
		double nouveausolde = this.getValeur() - paiement;
		if (paiement<0.0){
			throw new IllegalArgumentException("Appel de Payer(compte, paiement) de CompteBancaire avec paiement<0.0 (=="+paiement+")");
		}	else if (nouveausolde <0.0) {
			this.setValeur(auteur, 0);
			return paiement + nouveausolde;
		} else {
			this.setValeur(auteur, nouveausolde);
			return paiement;
		}
	}
	
	/**  
	 * */
	public void RecevoirPaiement (IActeur auteur, double paiement) {
		double nouveausolde = this.getValeur() + paiement*0.8;
		if (paiement <0.0) {
			throw new IllegalArgumentException("Appel de RecevoirPaiement(compte, paiement) de CompteBancaire avec paiement<0.0 (=="+paiement+")");
		} else {
			this.setValeur(auteur, nouveausolde);
		}
	}
}
