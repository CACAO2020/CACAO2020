package abstraction.eq7Distributeur2;

import java.util.List;


public interface IStock {
	//raphael G

	public List<Integer> getStocks();   //informe sur les 6 stocks différents
	
	public List<Integer> quantitesRecues(); //Indique quantitées recues par le protocole d'achat
	
	public List<Integer> quantitesPrelevees();  //Indique quantitées prélevées (cad retirées du stock) par le protocole de vente 
	
	public double coutStockage();  //Indique les couts du stockage actuel 
	
	public List<Integer> estimationProchStock(); //Estime les prochains stocks lors de la prochaine step 
	                                      // (ie calcule la différence entre achat et vente lors de cette step et l'ajoute au stock)
	
	public List<Integer> stockAnneePrec();    //Informe stock 1 an auparavant

	
}
