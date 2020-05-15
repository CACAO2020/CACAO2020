package abstraction.eq7Distributeur2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import abstraction.eq8Romu.clients.ClientFinal;
import abstraction.eq8Romu.clients.IDistributeurChocolat;
import abstraction.eq8Romu.clients.IDistributeurChocolatDeMarque;
import abstraction.eq8Romu.produits.Chocolat;
//import abstraction.eq8Romu.produits.Gamme;
//import abstraction.fourni.Filiere;
import abstraction.eq8Romu.produits.ChocolatDeMarque;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;

public class DistributeurChocolat extends AbsDistributeurChocolat implements IDistributeurChocolatDeMarque, IActeur {

	protected Map<Chocolat, Double> prixParDefaut;
	
	public DistributeurChocolat(Distributeur2 ac) {
		super(ac);
		prixParDefaut = new HashMap<Chocolat, Double>();
		prixParDefaut.put(Chocolat.CHOCOLAT_MOYENNE, 10000.);
		prixParDefaut.put(Chocolat.CHOCOLAT_MOYENNE_EQUITABLE, 14000.);
		prixParDefaut.put(Chocolat.CHOCOLAT_HAUTE, 15000.);
		prixParDefaut.put(Chocolat.CHOCOLAT_HAUTE_EQUITABLE, 20000.);
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
		double cours;
		double pourcentageMarge = 5;
		if (Filiere.LA_FILIERE.getEtape() > 1) {
            cours = Filiere.LA_FILIERE.getIndicateur("BourseChoco cours " + choco.getChocolat().name()).getHistorique().get(Filiere.LA_FILIERE.getEtape()-1).getValeur();
        } else {
        	cours = prixParDefaut.get(choco.getChocolat());
        }
		return (1. + pourcentageMarge/100.) * cours;
	}

	public double quantiteEnVente(ChocolatDeMarque choco) {
		return ac.getStock().getStockChocolatDeMarque(choco);
	}

	public void vendre(ClientFinal client, ChocolatDeMarque choco, double quantite, double montant) {
		if (client!=null) { 
			ac.getStock().retirerStockChocolat(choco, quantite);
			journal.ajouter("Vente de " + quantite + " tonnes de " + choco.name() + " à " + client.getNom() + " pour " + montant + ".");
		}
	}

	public void notificationRayonVide(ChocolatDeMarque choco) {
		journal.ajouter("Le rayon de " + choco.name() + " est vide.");
		
	}

	public List<ChocolatDeMarque> pubSouhaitee() {
		return null;
	}

}
