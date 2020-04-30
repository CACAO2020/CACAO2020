package abstraction.eq3Transformateur1;

import abstraction.eq8Romu.cacaoCriee.IVendeurCacaoCriee;
import abstraction.eq8Romu.produits.Feve;
import abstraction.fourni.Journal;

/** @author R. DEBRUYNE  */

public class LotCacaoCriee {
	public static final double QUANTITE_MIN = 0.5; //Pas de lot de moins de 500 Kg
	private IVendeurCacaoCriee vendeur;
	private Feve feve;
	private double quantiteEnTonnes;
	private double prixMinPourUneTonne; // ce prix est INDICATIF (il n'engage a rien) :
	                                    // le vendeur n'est pas tenu d'accepter une offre dont le prix est 
	                                    // superieur, et peut eventuellement accepter une offre dont le prix 
	                                    // est inferieur
	/**
	 * @param vendeur, vendeur!=null
	 * @param quantiteEnTonnes, quantite>=QUANTITE_MIN
	 * @param prixMinPourUneTonne, prixMinPourUneTonne>=0.0
	 */
	public LotCacaoCriee(IVendeurCacaoCriee vendeur, Feve feve, double quantiteEnTonnes, double prixMinPourUneTonne) {
		if (quantiteEnTonnes<=QUANTITE_MIN) {
			throw new IllegalArgumentException("Tentative de creer un lot avec une quantite "+quantiteEnTonnes+" inferieure a QUANTITE_MIN (=="+QUANTITE_MIN+")");
		}
		if (prixMinPourUneTonne<0.0) {
			throw new IllegalArgumentException("Tentative de creer un lot avec un prix minimum negatif (=="+prixMinPourUneTonne+")");
		}
		if (vendeur==null) {
			throw new IllegalArgumentException("Tentative de creer un lot avec null pour vendeur");
		}
		if (feve==null) {
			throw new IllegalArgumentException("Tentative de creer un lot avec null pour feve");
		}
		this.vendeur = vendeur;
		this.feve = feve;
		this.quantiteEnTonnes = quantiteEnTonnes;
		this.prixMinPourUneTonne = prixMinPourUneTonne;
	}
	public IVendeurCacaoCriee getVendeur() {
		return this.vendeur;
	}
	public Feve getFeve() {
		return this.feve;
	}
	public double getQuantiteEnTonnes() {
		return this.quantiteEnTonnes;
	}
	public double getPrixMinPourUneTonne() {
		return this.prixMinPourUneTonne;
	}
	public String toString() {
		return "[V="+this.getVendeur().getNom()+",F="+this.getFeve()+",Q="+Journal.doubleSur(this.getQuantiteEnTonnes(), 2)+",P="+Journal.doubleSur(this.getPrixMinPourUneTonne(),4)+"]";
	}
}
