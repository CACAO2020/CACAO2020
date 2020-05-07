package abstraction.eq7Distributeur2;

import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.Feve;

public interface IStock {
	
	public double getStockChocolat(Chocolat choco);
	
	public Chocolat stringToChoco(String choco);
	
	public double getStockFeves(Feve feve);
	
	public Feve stringToFeve(String feve);
	
	public void ajouterStockChocolat(Chocolat choco, double quantite);
	
	public void retirerStockChocolat(Chocolat choco, double quantite);
	
	public void ajouterStockFeves(Feve feve, double quantite);
	
	public void retirerStockFeves(Feve feve, double quantite);
	
	//commentaire
	
	

}
