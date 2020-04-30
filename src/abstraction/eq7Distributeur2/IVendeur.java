package abstraction.eq7Distributeur2;

public interface IVendeur {
	
	public double demandeClient();
	
	public double consulterStocks();
	
	public double vente();
	
	public double envoyerClients();

	public void notificationVente(double quantite, double prix);
	
	
}
