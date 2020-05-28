package abstraction.eq3Transformateur1;

import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.Feve;
import abstraction.eq8Romu.produits.Gamme;

/** @author AMAURY COUDRAY*/
public abstract class Transformation extends Stock {
	
	/* gestion TRANSFORMATION */
	
	public Chocolat equivalentChocoFeve(Feve feve) {
		if(feve.isEquitable()) {
			if(feve.getGamme()==Gamme.HAUTE) {
				return Chocolat.CHOCOLAT_HAUTE_EQUITABLE;
			}
			return Chocolat.CHOCOLAT_MOYENNE_EQUITABLE;
		}
		if(feve.getGamme()==Gamme.HAUTE) {
			return Chocolat.CHOCOLAT_HAUTE;
		}
		else if (feve.getGamme()==Gamme.MOYENNE) {
			return Chocolat.CHOCOLAT_MOYENNE;
		}
		return Chocolat.CHOCOLAT_BASSE;
	}
	
	public  void transformationFevePate(Feve feve, Double quantite) {
		if((quantite>=0)&&(this.getStockFeves().containsKey(feve))&&(this.getStockFeves(feve)>=quantite)) {
				this.setStockFeves(feve, -quantite);
				this.setStockPateInterne(this.equivalentChocoFeve(feve), quantite);
				
		}
	}
	public  void transformationPateChocolat(Chocolat chocolat, Double quantite) {
		if((quantite>=0)&&(this.getStockPateInterne().containsKey(chocolat))&&(this.getStockPateInterne(chocolat)>=quantite)) {
				this.setStockPateInterne(chocolat, -quantite);
				this.setStockChocolat(chocolat,quantite);
		}
	}
}
