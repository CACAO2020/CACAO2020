package abstraction.eq6Distributeur1;

import java.util.Map;

import abstraction.eq8Romu.produits.ChocolatDeMarque;

public class Distributeur1 extends DistributeurClientFinal {

	public Distributeur1(double capaciteDeVente, double marge, double capaciteStockmax, double pctageHGE, double pctageMG, double pctageBG) {
		super(capaciteDeVente, marge, capaciteStockmax, pctageHGE, pctageMG, pctageBG);
		// TODO Auto-generated constructor stub
	}
	
	public Distributeur1() {
		super(100000, 1.3, 10000000, 0.15, 0.35, 0.50);
	}

}
