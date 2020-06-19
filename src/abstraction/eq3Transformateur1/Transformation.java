package abstraction.eq3Transformateur1;

import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.Feve;
import abstraction.eq8Romu.produits.Gamme;
import abstraction.fourni.Journal;

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
		if((quantite>0)&&
				(this.getStockFeves().containsKey(feve))&&
				(this.getStockFeves(feve)>=quantite)&&
				(this.depense(quantite*6000))) {
			this.setCoutPate(this.equivalentChocoFeve(feve),this.calculCoutPate(this.equivalentChocoFeve(feve), quantite, quantite*(6000+this.getCoutFeves(feve))));
			this.setStockFeves(feve, -quantite);
			this.setStockPate(this.equivalentChocoFeve(feve), quantite);
			this.journalTransformation.ajouter("transformation de "+quantite +"T de  FEVES"+feve+" en pate");

		}
	}
	public  void transformationPateChocolat(Chocolat chocolat, Double quantite) {
		if((quantite>0)&&
				(this.getStockPate().containsKey(chocolat))&&
				(this.getStockPate(chocolat)>=quantite)&&
				(this.depense(quantite*4000))) {
			this.setCoutChocolat(chocolat, this.calculCoutChocolat(chocolat, quantite, (4000+this.getCoutPate(chocolat))*quantite));
			this.setStockPate(chocolat, -quantite);
			this.setStockChocolat(chocolat,quantite);
			this.journalTransformation.ajouter("transformation de "+quantite +"T de Pate"+chocolat+" en chocolat");
		}
	}
}
