package abstraction.eq6Distributeur1;

import java.util.Map;

import abstraction.eq8Romu.produits.ChocolatDeMarque;

public class Distributeur1 extends DistributeurClientFinal {

	public Distributeur1(double capaciteDeVente, double margeHGE, double margeMG, double margeBG,
			double capaciteStockmax, double pctageHGE, double pctageMG, double pctageBG) {
		super(capaciteDeVente, margeHGE, margeMG, margeBG, capaciteStockmax, pctageHGE, pctageMG, pctageBG);
		// TODO Auto-generated constructor stub
	}
	
	public Distributeur1() {
		super(100000, 0.5, 0.4, 0.2, 10000000, 0.15, 0.35, 0.50);
	}

}
