package abstraction.eq5Transformateur3;
/** @author Simon MORO  */
public class Tresorerie {
    /**
     * Gère l'argent de la société pour la répartir entre les filiaires et voir si toutes les décisions sont cohérentes en terme bugetaires.
     * Elle est agrégé dans la classe Transformateur3.
     * 
     * Infos utiles à récupérer : montant compte en banque ; stock ; historique des ventes / achats ; agios / découverts 
     * ; montant max d'investissement de chaque filière ;
     * 
     *
     */
	private Transformateur3 Acteur;
	
	private double MontantCompte;
	private double Agios;
	private double FacteurPrioriteGamme;
	
	public Tresorerie(double MontantCompte, double Agios, double Facteur) {
		this.MontantCompte=MontantCompte;
		this.Agios=Agios;
		this.FacteurPrioriteGamme=Facteur;
	}
	
	public double getMontantCompte() {
		return this.MontantCompte;
	}
	
	public double getAgios() {
		return this.Agios;
	}
	
	public double getFacteurPrioriteGamme() {
		/**
	     * Le facteur qui décrit la priorité que nous mettons sur le bas de gamme par rapport au haut de gamme (évolue au cours de la
	     * simulation)
	     */
		return this.FacteurPrioriteGamme;
	}
	
	public void setMontantCompte (double MontantCompte) {
		this.MontantCompte=MontantCompte;
	}
	
	public void setAgios (double Agios) {
		this.Agios=Agios;
	}
	
	public void setFacteurPrioriteGamme (double Facteur) {
		/**
	     * Le facteur qui décrit la priorité que nous mettons sur le bas de gamme par rapport au haut de gamme (évolue au cours de la
	     * simulation)
	     */
		this.FacteurPrioriteGamme=Facteur;
	}
	
	public double InvestissementMaxBasDeGamme() {
		/**
	     * Renvoie l'investissement maximum possible à faire dans le bas de gamme en fonction du facteur de priorité que l'on s'impose
	     * ainsi que du montant de notre trésorerie
	     */
		return 0;
	}
	
	public double InvestissementMaxHautDeGamme() {
		/**
	     * Renvoie l'investissement maximum possible à faire dans le haut de gamme en fonction du facteur de priorité que l'on s'impose
	     * ainsi que du montant de notre trésorerie
	     */
		return 0;
	}
}