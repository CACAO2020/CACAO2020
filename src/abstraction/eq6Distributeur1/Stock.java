package abstraction.eq6Distributeur1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.ChocolatDeMarque;
import abstraction.fourni.Filiere;

public class Stock  extends Distributeur1abs implements IStock { /** @author Avril Thibault et Tamine Mélissa et Luca Pinguet*/
	protected double capaciteStockmax;
	
		
	
	
	public Stock(double capaciteStockmax) {
		this.capaciteStockmax = capaciteStockmax;

		
	}
	

	@Override
	public double quantiteEnStockTypeChoco(Chocolat choco) {
		double quantite = 0;
		for (Integer etape : this.MapStock.keySet()) {
			for (ChocolatDeMarque chocos : this.MapStock.get(etape).keySet()) {
				if (chocos.getChocolat()==choco) {
				quantite = quantite + MapStock.get(etape).get(chocos);
				}
			}
		}
		return quantite;
	}

	@Override
	public double quantiteEnStockTotale() {
		double quantite = 0;
		for (Integer etape : this.MapStock.keySet()) {
			for (ChocolatDeMarque chocos : this.MapStock.get(etape).keySet()) {
				quantite = quantite + MapStock.get(etape).get(chocos);
			}
		}
		return quantite;
	}

	@Override
	public double espaceRestant(Chocolat choco) {
		return this.quantiteEnStockTypeChoco(choco)-this.capaciteStockmax;
	}

	@Override
	public double coutdeStockage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double valeurdeQuantiteStockee(Chocolat choco) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void stocker(ChocolatDeMarque choco, double quantite) {
		journalEq6Stock.ajouter("stocker");
		System.out.println(Filiere.LA_FILIERE.getEtape()+""+MapStock.get(Filiere.LA_FILIERE.getEtape()));
		if (MapStock.get(Filiere.LA_FILIERE.getEtape()).keySet().contains(choco)) {
			this.MapStock.get(Filiere.LA_FILIERE.getEtape()).put(choco, this.MapStock.get(Filiere.LA_FILIERE.getEtape()).get(choco)+quantite);
		}
		else {
			this.MapStock.get(Filiere.LA_FILIERE.getEtape()).put(choco, quantite);
		}
		journalEq6Stock.ajouter("" + this.MapStock.get(Filiere.LA_FILIERE.getEtape()).keySet());
	}

	@Override
	public void destocker(ChocolatDeMarque choco, double quantite) {
		Double quanti = quantite;
		ArrayList<Integer> listeAEnlever = new ArrayList<Integer>();
		for (Integer etape : this.MapStock.keySet()) {
			if (etape<Filiere.LA_FILIERE.getEtape()-11) {
				listeAEnlever.add(etape);
			}
		}
		for (Integer etape : listeAEnlever) {
			MapStock.remove(etape);
		}
		
		ArrayList<Integer> listeEtape = new ArrayList<Integer>();
		for (Integer etape : this.MapStock.keySet()) {
			if (MapStock.get(etape).containsKey(choco)) {
				listeEtape.add(etape);
			}
		}
		Collections.sort(listeEtape);
		System.out.println(listeEtape+"liste");
		for (int i=0 ; i<listeEtape.size() ; i++) {
			if (quanti!=0) {
				if (MapStock.get(listeEtape.get(i)).get(choco)<=quanti) {
					quanti = quanti - MapStock.get(listeEtape.get(i)).get(choco);
					MapStock.get(listeEtape.get(i)).remove(choco);
				}
				else {
					MapStock.get(listeEtape.get(i)).replace(choco, MapStock.get(listeEtape.get(i)).get(choco)-quanti);
				}
			}
		}
		
	}

	@Override
	public double quantiteEnStockMarqueChoco(ChocolatDeMarque choco) {
		Double quantite = 0.0;
		for (Integer etape : this.MapStock.keySet()) {
			if (MapStock.get(etape).containsKey(choco)) {
				quantite = quantite + MapStock.get(etape).get(choco);
			}
		}
		return quantite;
	}

}
