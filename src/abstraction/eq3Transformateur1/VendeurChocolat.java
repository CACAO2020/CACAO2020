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
            
            if(cours<this.getCoutChocolat(chocolat)) {

                return 0.0;
            }
            if(cours<1.02*this.getCoutChocolat(chocolat)) {

                return 0.2*this.getStockChocolat(chocolat);
            }
            if(cours<1.04*this.getCoutChocolat(chocolat)) {

                return 0.4*this.getStockChocolat(chocolat);
            }
            if(cours<1.06*this.getCoutChocolat(chocolat)) {

                return 0.06*this.getStockChocolat(chocolat);
            }
            if(cours<1.08*this.getCoutChocolat(chocolat)) {

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
