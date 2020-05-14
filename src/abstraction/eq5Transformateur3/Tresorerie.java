package abstraction.eq5Transformateur3;

import abstraction.fourni.Banque;
import abstraction.fourni.Filiere;
import abstraction.fourni.Variable;

/** @author Simon MORO  
     * Gère l'argent de la société pour la répartir entre les filiaires et voir si toutes les décisions sont cohérentes en terme bugetaires.
     * Elle est agrégé dans la classe Transformateur3.
     * 
     * Infos utiles à récupérer : montant compte en banque ; stock ; historique des ventes / achats ; agios / découverts 
     * ; montant max d'investissement de chaque filière ;
     * 
     *structure d'une Variable = (String nom, IActeur createur,  double min, double max, double valInit)
     */
public class Tresorerie {
    
	private Transformateur3 acteur;
	private Banque banque; //Filiere.LA_FILIERE.getBanque()
	private double MontantCompte;
	private double Decouvert;
	private double FacteurPrioriteGamme; // 100% haute gamme = 1, 100% bas de gamme = 0

	
	private Variable decouvertsConsecutifsAvantFaillite; //parametres fixes à priori
	private Variable decouvertAutorise;
	private Variable agiosDecouvertAutorise;
	private Variable agiosDecouvertAuDela;
	private Variable seuilOperationsRefusees;
	
	public Tresorerie(Transformateur3 acteur, Banque banque, double MontantCompte, double Decouvert, double Facteur, Variable decouvertsConsecutifsAvantFaillite,
			Variable decouvertAutorise, Variable agiosDecouvertAutorise, Variable agiosDecouvertAuDela, Variable seuilOperationsRefusees) {
		this.acteur = acteur;
		this.banque = banque;
		this.MontantCompte=MontantCompte;
		this.Decouvert=Decouvert;
		this.FacteurPrioriteGamme=Facteur;
		
		this.decouvertsConsecutifsAvantFaillite = decouvertsConsecutifsAvantFaillite;
		this.decouvertAutorise = decouvertAutorise;
		this.agiosDecouvertAutorise = agiosDecouvertAutorise;
		this.agiosDecouvertAuDela = agiosDecouvertAuDela;
		this.seuilOperationsRefusees = seuilOperationsRefusees;
	}
	/**
	     * Initialise la trésorerie
	     */
	public Tresorerie(Transformateur3 acteur) {
		
		this(acteur,
				Filiere.LA_FILIERE.getBanque(),
				0,									//montantCompte
				0,									//decouvert actuel
				0,									//FacteurPriorite
				Filiere.LA_FILIERE.getBanque().getParametres().get(0),
				Filiere.LA_FILIERE.getBanque().getParametres().get(1),
				Filiere.LA_FILIERE.getBanque().getParametres().get(2),
				Filiere.LA_FILIERE.getBanque().getParametres().get(3),
				Filiere.LA_FILIERE.getBanque().getParametres().get(4));
	}
	
	public double getMontantCompte() {
		return Filiere.LA_FILIERE.getBanque().getSolde(this.acteur, this.acteur.getCryptogramme());
	}
	
	public double getDecouvert() {
		return this.Decouvert;
	}
	/**
	     * Le facteur qui décrit la priorité que nous mettons sur le bas de gamme par rapport au haut de gamme (évolue au cours de la
	     * simulation)
	     */
	public double getFacteurPrioriteGamme() {
		
		return this.FacteurPrioriteGamme;
	}
	
	public void setMontantCompte (double MontantCompte) {
		this.MontantCompte=MontantCompte;
	}
	
	public void setDecouvert (double Decouvert) {
		this.Decouvert=Decouvert;
	}
	/**
	     * Le facteur qui décrit la priorité que nous mettons sur le bas de gamme par rapport au haut de gamme (évolue au cours de la
	     * simulation)
	     */
	public void setFacteurPrioriteGamme (double Facteur) {
		
		this.FacteurPrioriteGamme=Facteur;
	}
	/**
	     * Renvoie l'investissement maximum possible à faire dans le bas de gamme en fonction du facteur de priorité que l'on s'impose
	     * ainsi que du montant de notre trésorerie
	     */
	public double InvestissementMaxBasDeGamme() {
		
		return this.getMontantCompte();
	}
	/**
	     * Renvoie l'investissement maximum possible à faire dans le haut de gamme en fonction du facteur de priorité que l'on s'impose
	     * ainsi que du montant de notre trésorerie
	     */
	public double InvestissementMaxHautDeGamme() {
		
		return this.getMontantCompte();
	}
	
	public void DiminueTresorerie(double montant) {
		
	}
	
	public void AugmenteTresorerie(double montant) {
		
	}
}