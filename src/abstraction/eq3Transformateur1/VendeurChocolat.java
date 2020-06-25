package abstraction.eq3Transformateur1;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import abstraction.eq8Romu.cacaoCriee.LotCacaoCriee;
import abstraction.eq8Romu.cacaoCriee.PropositionCriee;
import abstraction.eq8Romu.chocolatBourse.IVendeurChocolatBourse;
import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.Feve;
import abstraction.eq8Romu.produits.Gamme;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;;
public abstract class VendeurChocolat extends AcheteurCacao implements IVendeurChocolatBourse {

	/** @author K. GUTIERREZ/Theophile  */
	public double getOffre(Chocolat chocolat, double cours) {
        if(this.getStockChocolat().containsKey(chocolat)) {
            
            if(cours<this.getCoutChocolat(chocolat)) {
            	this.journalVente.ajouter("Le cours est inférieur au coût du chocolat : on ne vend pas");
                return 0.0;
            }
            if(cours<1.02*this.getCoutChocolat(chocolat)) {
            	this.journalVente.ajouter("On essaye de vendre 20% de "+chocolat+" alors qu'en stock on a "+this.getStockChocolat(chocolat));
                this.journalVente.ajouter("Ce chocolat nous a coûté "+this.getCoutChocolat(chocolat)+" et le cours est à "+cours);
            	return 0.2*this.getStockChocolat(chocolat);
            }
            if(cours<1.04*this.getCoutChocolat(chocolat)) {
            	this.journalVente.ajouter("On essaye de vendre 40% de "+chocolat+" alors que en stock on a"+this.getStockChocolat(chocolat));
                this.journalVente.ajouter("Ce chocolate nous a coûté "+this.getCoutChocolat(chocolat)+" et le cours est à "+cours);
            	return 0.4*this.getStockChocolat(chocolat);
            }
            if(cours<1.06*this.getCoutChocolat(chocolat)) {
            	this.journalVente.ajouter("On essaye de vendre 60% de "+chocolat+" alors que en stock on a"+this.getStockChocolat(chocolat));
                this.journalVente.ajouter("Ce chocolate nous a coûté "+this.getCoutChocolat(chocolat)+" et le cours est à "+cours);
            	return 0.6*this.getStockChocolat(chocolat);
            }
            if(cours<1.08*this.getCoutChocolat(chocolat)) {
            	this.journalVente.ajouter("On essaye de vendre 80% de "+chocolat+" alors que en stock on a"+this.getStockChocolat(chocolat));
                this.journalVente.ajouter("Ce chocolat nous a coûté "+this.getCoutChocolat(chocolat)+" et le cours est à "+cours);
            	return 0.8*this.getStockChocolat(chocolat);
            }
        	this.journalVente.ajouter("On essaye de vendre 100% de "+chocolat+" alors que en stock on a"+this.getStockChocolat(chocolat));
            this.journalVente.ajouter("Ce chocolat nous a coûté "+this.getCoutChocolat(chocolat)+" et le cours est à "+cours);
        	return this.getStockChocolat(chocolat);
        }
        return 0.0;
    }
	
	/** @author K. GUTIERREZ  */
	public void livrer(Chocolat chocolat, double quantite) {
    	this.journalVente.ajouter("On a vendu "+quantite+" T de "+chocolat);
		this.setStockChocolat(chocolat,-quantite);
	}

}
