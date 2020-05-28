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

	/** @author K. GUTIERREZ  */
	public double getOffre(Chocolat chocolat, double cours) {
        if(this.getStockChocolat().containsKey(chocolat)) {
            System.out.println("on peut faire une offre " + cours+" "+ this.getCoutChocolat(chocolat));
            if(cours<this.getCoutChocolat(chocolat)) {
                System.out.println("on n'en fait pas");
                return 0.0;
            }
            if(cours<1.2*this.getCoutChocolat(chocolat)) {
                System.out.println("on en fait une");
                return 0.2*this.getStockChocolat(chocolat);
            }
            if(cours<1.4*this.getCoutChocolat(chocolat)) {
                System.out.println("on en fait une");
                return 0.4*this.getStockChocolat(chocolat);
            }
            if(cours<1.6*this.getCoutChocolat(chocolat)) {
                System.out.println("on en fait une");
                return 0.6*this.getStockChocolat(chocolat);
            }
            if(cours<1.8*this.getCoutChocolat(chocolat)) {
                System.out.println("on en fait une");
                return 0.8*this.getStockChocolat(chocolat);
            }
            return this.getStockChocolat(chocolat);
        }
        return 0.0;
    }
	
	/** @author K. GUTIERREZ  */
	public void livrer(Chocolat chocolat, double quantite) {
		this.setStockChocolat(chocolat,-quantite);
	}

}
