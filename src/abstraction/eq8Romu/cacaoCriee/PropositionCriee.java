package abstraction.eq8Romu.cacaoCriee;

import abstraction.eq8Romu.produits.Feve;

public class PropositionCriee {
	private LotCacaoCriee lot;
	private IAcheteurCacaoCriee acheteur;
	private double prixPourUneTonne;
	
	public PropositionCriee(LotCacaoCriee lot, IAcheteurCacaoCriee acheteur, double prix) {
		this.lot = lot;
		this.acheteur = acheteur;
		this.prixPourUneTonne = prix;
	}
	public LotCacaoCriee getLot() {
		return lot;
	}
	public IAcheteurCacaoCriee getAcheteur() {
		return acheteur;
	}
	public IVendeurCacaoCriee getVendeur() {
		return lot.getVendeur();
	}
	public double getQuantiteEnTonnes() {
		return this.getLot().getQuantiteEnTonnes();
	}
	public double getPrixPourUneTonne() {
		return prixPourUneTonne;
	}
	public double getPrixPourLeLot() {
		return lot.getQuantiteEnTonnes()*this.getPrixPourUneTonne();
	}
	public Feve getFeve() {
		return lot.getFeve();
	}
	
}
