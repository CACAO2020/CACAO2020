package abstraction.eq7Distributeur2;

import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.ChocolatDeMarque;
import abstraction.eq8Romu.produits.Feve;

public interface IStock {
	
	public double getStockChocolat(Chocolat choco);
	
	public double getStockChocolatDeMarque(ChocolatDeMarque chocoDeMarque);
	
	public void ajouterStockChocolat(ChocolatDeMarque chocoDeMarque, double quantite);
	
	public void retirerStockChocolat(ChocolatDeMarque chocoDeMarque, double quantite);

}
