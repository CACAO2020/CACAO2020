package abstraction.eq3Transformateur1;
import abstraction.eq8Romu.chocolatBourse.IVendeurChocolatBourse;
import abstraction.eq8Romu.produits.Chocolat;;
public abstract class VendeurChocolatBourse extends Stock implements IVendeurChocolatBourse {

	@Override
	public double getOffre(Chocolat chocolat, double cours) {
		// TODO Auto-generated method stub
		
		return 0;
	}

	@Override
	public void livrer(Chocolat chocolat, double quantite) {
		// TODO Auto-generated method stub
		
	}

}
