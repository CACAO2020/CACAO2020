package abstraction.eq8Romu.chocolatBourse;

import abstraction.eq8Romu.produits.ChocolatDeMarque;

public class CommandeChocolat {
	private ChocolatDeMarque chocolat;
	private double quantite;
	private double montant;
	
	public CommandeChocolat(ChocolatDeMarque chocolat, double quantite, double montant) {
		this.chocolat = chocolat;
		this.montant = montant;
		this.quantite = quantite;
	}

	public ChocolatDeMarque getChocolat() {
		return chocolat;
	}

	public double getMontant() {
		return montant;
	}

	public double getQuantite() {
		return quantite;
	}
	
	public void ajouterInterets() {
		this.montant *= (1.0+SuperviseurChocolatBourse.getTauxInteret());
	}
	

}
