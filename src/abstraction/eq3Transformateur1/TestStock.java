package abstraction.eq3Transformateur1;

import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.Feve;

public class TestStock extends Stock{
	public static void main(String[] args) {
		Stock stock=new Stock();
		System.out.println("TEST METHODES GET (a l'etat initial)");
		System.out.println("");
		System.out.println("Map stock Chocolat "+stock.getStockChocolat());
		System.out.println("Stock chocolat moyenne = "+stock.getStockChocolat(Chocolat.CHOCOLAT_MOYENNE));
		System.out.println("Stock chocolat basse = "+stock.getStockChocolat(Chocolat.CHOCOLAT_BASSE));

		System.out.println("Map Stock feves "+stock.getStockFeves());
		System.out.println("Stock feve basse = "+stock.getStockFeves(Feve.FEVE_BASSE));
		System.out.println("Stock feve moyenne = "+stock.getStockFeves(Feve.FEVE_MOYENNE));

		System.out.println("Cout Feves moyenne "+stock.getCoutFeves(Feve.FEVE_MOYENNE));
		stock.setStockChocolat(Chocolat.CHOCOLAT_HAUTE_EQUITABLE,15.0);
		stock.setStockChocolat(Chocolat.CHOCOLAT_MOYENNE,15.0);
		stock.setStockChocolat(Chocolat.CHOCOLAT_MOYENNE,-150.0);
		System.out.println("");
		System.out.println("TEST DES METHODES GET/SET");
		System.out.println("");
		System.out.println("Map stock Chocolat "+stock.getStockChocolat());
		System.out.println("Stock chocolat moyenne = "+stock.getStockChocolat(Chocolat.CHOCOLAT_MOYENNE));
		System.out.println("Stock chocolat basse = "+stock.getStockChocolat(Chocolat.CHOCOLAT_BASSE));

		stock.setStockFeves(Feve.FEVE_MOYENNE,15.0);
		stock.setStockFeves(Feve.FEVE_BASSE,15.0);
		stock.setStockFeves(Feve.FEVE_BASSE,-5.0);
		stock.setStockFeves(Feve.FEVE_BASSE,-150.0);
		System.out.println("Map Stock feves "+stock.getStockFeves());
		System.out.println("Stock feve basse = "+stock.getStockFeves(Feve.FEVE_BASSE));
		System.out.println("Stock feve moyenne = "+stock.getStockFeves(Feve.FEVE_MOYENNE));

			
		System.out.println("");
		System.out.println("TEST DE METHODE equivalent");
		System.out.println("");
		System.out.println(stock.equivalentChocoFeve(Feve.FEVE_HAUTE_EQUITABLE));
		System.out.println(stock.equivalentChocoFeve(Feve.FEVE_HAUTE));
		System.out.println(stock.equivalentChocoFeve(Feve.FEVE_MOYENNE));
		System.out.println(stock.equivalentChocoFeve(Feve.FEVE_MOYENNE_EQUITABLE));
		System.out.println(stock.equivalentChocoFeve(Feve.FEVE_BASSE));
		System.out.println("");
		System.out.println("TEST DE METHODE transformationFevePate");
		System.out.println("");
		System.out.println("Map Stock feves "+stock.getStockFeves());
		System.out.println("Map Stock Pate interne "+stock.getStockPateInterne());
		System.out.println("Map stock Chocolat "+stock.getStockChocolat());
		System.out.println("");
		stock.transformationFevePate(Feve.FEVE_BASSE,5.0);
		stock.transformationFevePate(Feve.FEVE_BASSE,25.0);
		stock.transformationFevePate(Feve.FEVE_BASSE,21.0);
		stock.transformationFevePate(Feve.FEVE_HAUTE,2.0);
		System.out.println("Map Stock feves "+stock.getStockFeves());
		System.out.println("Map Stock Pate interne "+stock.getStockPateInterne());
		System.out.println("Map stock Chocolat "+stock.getStockChocolat());
		System.out.println("");
		stock.transformationPateChocolat(Chocolat.CHOCOLAT_BASSE, 2.0);
		stock.transformationPateChocolat(Chocolat.CHOCOLAT_BASSE, 20.0);
		stock.transformationPateChocolat(Chocolat.CHOCOLAT_BASSE, -20.0);
		stock.transformationPateChocolat(Chocolat.CHOCOLAT_MOYENNE, 20.0);
		stock.transformationPateChocolat(Chocolat.CHOCOLAT_MOYENNE, -20.0);
		System.out.println("Map Stock feves "+stock.getStockFeves());
		System.out.println("Map Stock Pate interne "+stock.getStockPateInterne());
		System.out.println("Map stock Chocolat "+stock.getStockChocolat());
	}
	
	
}
