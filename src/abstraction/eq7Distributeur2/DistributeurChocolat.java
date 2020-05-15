package abstraction.eq7Distributeur2;

import java.util.ArrayList;
import java.util.List;

import abstraction.eq8Romu.clients.ClientFinal;
import abstraction.eq8Romu.clients.IDistributeurChocolat;
import abstraction.eq8Romu.clients.IDistributeurChocolatDeMarque;
import abstraction.eq8Romu.produits.Chocolat;
//import abstraction.eq8Romu.produits.Gamme;
//import abstraction.fourni.Filiere;
import abstraction.eq8Romu.produits.ChocolatDeMarque;
import abstraction.fourni.IActeur;

/**
 * 
 * @author R. Debryune
 *
 * Un exemple simpliste d'un distributeur qui ne commercialise qu'une variete de chocolat, 
 * qui peut au plus mettre capaciteDeVente tonnes de chocolat en vente par step, et qui a
 * un prix fixe pour ce chocolat 
 */
public class DistributeurChocolat extends AbsDistributeurChocolat implements IDistributeurChocolatDeMarque {

	private double capaciteDeVente;
	private double prix;

	public DistributeurChocolat(Distributeur2 ac) {
		super(ac);
	}

	public boolean commercialise(Chocolat choco) {
		return false;
	}

	public double quantiteEnVente(Chocolat choco) {
		return 0.;
	}

	public double prix(Chocolat choco) {
		return 0.;
	}

	public void vendre(ClientFinal client, Chocolat choco, double quantite) {
	}
	
	public void next() {
		//Filiere.LA_FILIERE.getVentes(-24, new Chocolat(Gamme.HAUTE, false, false));
	}

	public List<ChocolatDeMarque> getCatalogue() {
		List<ChocolatDeMarque> res = new ArrayList<ChocolatDeMarque>();
		return res;
	}

	public double prix(ChocolatDeMarque choco) {
		// TODO Auto-generated method stub
		return 0;
	}

	public double quantiteEnVente(ChocolatDeMarque choco) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void vendre(ClientFinal client, ChocolatDeMarque choco, double quantite, double montant) {
		// TODO Auto-generated method stub
		
	}

	public void notificationRayonVide(ChocolatDeMarque choco) {
		// TODO Auto-generated method stub
		
	}

	public List<ChocolatDeMarque> pubSouhaitee() {
		// TODO Auto-generated method stub
		return null;
	}
}
