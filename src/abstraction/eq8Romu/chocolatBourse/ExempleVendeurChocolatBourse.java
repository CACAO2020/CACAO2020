package abstraction.eq8Romu.chocolatBourse;

import abstraction.eq8Romu.produits.Chocolat;

public class ExempleVendeurChocolatBourse extends ExempleAbsVendeurChocolatBourse implements IVendeurChocolatBourse {

	public ExempleVendeurChocolatBourse(Chocolat choco) {
		super(choco);
	}

	public double getOffre(Chocolat chocolat, double cours) {
		if (Math.random()<=0.3333333) {// 33% de faire une offre
			return Math.random()*stockChocolat.getValeur();
		} else {
			return 0.0;
		}
	}

	public void livrer(Chocolat chocolat, double quantite) {
		stockChocolat.retirer(this, quantite);
	}
}
