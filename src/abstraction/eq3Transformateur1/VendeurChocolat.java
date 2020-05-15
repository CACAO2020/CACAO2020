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
	public VendeurChocolat(ActeurEQ3 acteur) {
		super(acteur);
	}

	/** @author K. GUTIERREZ  */
	public double getOffre(Chocolat chocolat, double cours) {
		if(cours<this.getCoutChocolat(chocolat)) {
			return 0.0;
		}
		return this.getStockChocolat(chocolat);
	}
	
	/** @author K. GUTIERREZ  */
	public void livrer(Chocolat chocolat, double quantite) {
		this.setStockChocolat(chocolat, quantite);
	}

}
