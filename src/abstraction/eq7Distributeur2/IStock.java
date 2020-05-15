package abstraction.eq7Distributeur2;

import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.ChocolatDeMarque;
import abstraction.eq8Romu.produits.Feve;

public interface IStock {
	
	public void creerStockChocolatDeMarque(ChocolatDeMarque choco);
	
	public void creerStockChocolat(Chocolat choco);
	
	public void creerStockFeves(Feve feve);
	
	public double getStockChocolat(Chocolat choco);
	
	public double getStockChocolatDeMarque(ChocolatDeMarque chocoDeMarque);
	
	public double getStockFeves(Feve feve);
	
	public java.util.List<String> getMarques();
	
	public void ajouterStockChocolat(ChocolatDeMarque chocoDeMarque, double quantite);
	
	public void retirerStockChocolat(ChocolatDeMarque chocoDeMarque, double quantite);
	
	public void ajouterStockFeves(Feve feve, double quantite);
	
	public void retirerStockFeves(Feve feve, double quantite);

}
