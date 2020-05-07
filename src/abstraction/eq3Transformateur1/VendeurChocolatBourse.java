package abstraction.eq3Transformateur1;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import abstraction.eq8Romu.chocolatBourse.IVendeurChocolatBourse;
import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.Gamme;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;;
public abstract class VendeurChocolatBourse extends Stock implements IVendeurChocolatBourse {

	private static int NB_INSTANCES = 0;
	private int numeroVendeur;
	private Chocolat chocolat;
	
	/** @author K. GUTIERREZ  */
	public VendeurChocolatBourse(Chocolat chocolat) {	
		if (chocolat==null) {
			throw new IllegalArgumentException("chocolat==null");
		}		
		NB_INSTANCES++;
		this.numeroVendeur = NB_INSTANCES;
		this.chocolat = chocolat;
	}
	
	/** @author K. GUTIERREZ  */
	public double getOffre(Chocolat chocolat, double cours) {
		return cours*((Variable) StockChocolat).getValeur();
	}

	/** @author K. GUTIERREZ  */
	public void livrer(Chocolat chocolat, double quantite) {
		((Variable) StockChocolat).retirer(this, quantite);
	}

}
