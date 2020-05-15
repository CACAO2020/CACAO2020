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

public class DistributeurChocolat extends AbsDistributeurChocolat implements IDistributeurChocolatDeMarque, IActeur {

	public DistributeurChocolat(Distributeur2 ac) {
		super(ac);
	}

	public boolean commercialise(Chocolat choco) {
		return ac.getAcheteurChocolat().gammesChocolat.contains(choco);
	}
	
	public void initialiser() {
	}

	public void next() {
	}

	public List<ChocolatDeMarque> getCatalogue() {
		List<ChocolatDeMarque> res = new ArrayList<ChocolatDeMarque>();
		for (ChocolatDeMarque choco : ac.getStock().stocksChocolatDeMarque.keySet()) {
			res.add(choco);
		}
		return res;
	}

	public double prix(ChocolatDeMarque choco) {
		return 5.;
	}

	public double quantiteEnVente(ChocolatDeMarque choco) {
		return ac.getStock().getStockChocolatDeMarque(choco);
	}

	public void vendre(ClientFinal client, ChocolatDeMarque choco, double quantite, double montant) {
		if (client!=null) { 
			ac.getStock().retirerStockChocolat(choco, quantite);
			journal.ajouter("Vente de " + quantite + " tonnes de " + choco.name() + " à " + client + " pour " + montant + ".");
		}
	}

	public void notificationRayonVide(ChocolatDeMarque choco) {
		
	}

	public List<ChocolatDeMarque> pubSouhaitee() {
		return null;
	}

}
