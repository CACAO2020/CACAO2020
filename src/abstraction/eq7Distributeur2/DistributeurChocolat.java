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
public class DistributeurChocolat extends AbsDistributeurChocolat implements IDistributeurChocolatDeMarque, IActeur {

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
	
	public void initialiser() {
	}

	public void next() {
	}

	public List<ChocolatDeMarque> getCatalogue() {
		List<ChocolatDeMarque> res = new ArrayList<ChocolatDeMarque>();
		return res;
	}

	public double prix(ChocolatDeMarque choco) {
		return 0;
	}

	public double quantiteEnVente(ChocolatDeMarque choco) {
		return 0;
	}

	public void vendre(ClientFinal client, ChocolatDeMarque choco, double quantite, double montant) {
	}

	public void notificationRayonVide(ChocolatDeMarque choco) {
		
	}

	public List<ChocolatDeMarque> pubSouhaitee() {
		return null;
	}
}
