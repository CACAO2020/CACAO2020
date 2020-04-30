package abstraction.eq7Distributeur2;

public interface IStock {
	//raphael G

	public double getStocks();   //informe sur les 6 stocks différents
	
	public double quantitesRecues(); //Indique quantitées recues par le protocole d'achat
	
	public double quantitesPrelevees();  //Indique quantitées prélevées (cad retirées du stock) par le protocole de vente 
	
	public double coutStockage();  //Indique les couts du stockage actuel 
	
	public double estimationProchStock(); //Estime les couts de stockage lors de la prochaine step 
	                                      // (ie calcule la différence entre achat et vente lors de cette step et l'ajoute au stock)
	
	public double stockAnneePrec();    //Informe stock 1 an auparavant
	
	
}
