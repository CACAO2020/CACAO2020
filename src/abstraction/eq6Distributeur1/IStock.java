package abstraction.eq6Distributeur1;

import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.ChocolatDeMarque;

public interface IStock { /** Interface implémentée par Avril Thibault et Tamine Mélissa. 
	/**
	 * @param choco, choco!=null
	 * @return Retourne la quantite (en tonnes) de chocolat de type choco que le distributeur
	 * a actuellement disponible en stock.
	 */
	public double quantiteEnStockTypeChoco(Chocolat choco); 
	
	
	/**
	 * @param choco, choco!=null
	 * @return Retourne la quantite (en tonnes) de chocolat de marque choco que le distributeur
	 * a actuellement disponible en stock.
	 */
	public double quantiteEnStockMarqueChoco (ChocolatDeMarque choco);
	
	
	/**
	 * @return Retourne la quantite totale (en tonnes) que le distributeur
	 * a actuellement disponible en stock.
	 */
	public double quantiteEnStockTotale();
	
	
	/**
	 * @param choco, choco!=null
	 * @return Retourne la quantite (en tonnes) de chocolat de type choco que le distributeur
	 * peut encore stockée.
	 */
	public double espaceRestant(Chocolat choco);
	
	
	/**
	 * @return Retourne le coût actuel de stockage.
	 */
	public double coutdeStockage();
	
	
	/**
	 * @param choco, choco!=null
	 * @return Retourne la valeur de la quantite (en tonnes) stockée de chocolat de type choco que le distributeur
	 * a actuellement disponible en stock.
	 */
	public double valeurdeQuantiteStockee(Chocolat choco);
	
	
	
	/**
	 * Methode invoquee afin de stocker une certaine quantite de
	 * chocolat de type choco achetée.
	 * @param chocolat
	 * @param quantite
	 */
	public void stocker(ChocolatDeMarque choco, double quantite);
	
	
	/**
	 * Methode invoquee afin de déstocker une certaine quantite de
	 * chocolat de type choco achetée.
	 * @param chocolat
	 * @param quantite
	 */
	public void destocker();


}
