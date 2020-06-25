package abstraction.eq5Transformateur3;

import java.util.ArrayList;
import java.util.List;

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
	//private double MontantCompte;				//Montant réel du compte au début du tour
	private double MontantCompteALaFinDuTour;	//Montant théorique après les achats de quelqu'un
	//private double Decouvert;
	private double FacteurPrioriteGamme; // 100% haute gamme => 1, 100% bas de gamme => 0
	private double investissementBasACeTour;	//valeur mise à jour en achetant dans les autres classes
	private double investissementSecondaireACeTour;
	private double venteBasACeTour;				//valeur mise à jour en vendant dans les autres classes
	private double venteSecondaireACeTour;
	private List<Double> investissementBasGlobal;  //l'historique des investissements dans la filière bas de gamme
	private List<Double> investissementSecondaireGlobal;
	private List<Double> venteBasGlobal;			//l'historique des achats dans la filière bas de gamme
	private List<Double> venteSecondaireGlobal;
	private int tour;
	
	
	//private Variable decouvertsConsecutifsAvantFaillite; //parametres fixes à priori
	//private Variable decouvertAutorise;
	//private Variable agiosDecouvertAutorise;
	//private Variable agiosDecouvertAuDela;
	//private Variable seuilOperationsRefusees;
	
	/**public Tresorerie(Transformateur3 acteur, double MontantCompte, double MontantCompteALaFinDuTour, double Decouvert, double Facteur, Variable decouvertsConsecutifsAvantFaillite,
			Variable decouvertAutorise, Variable agiosDecouvertAutorise, Variable agiosDecouvertAuDela, Variable seuilOperationsRefusees) {
		this.acteur = acteur;
		this.MontantCompte=MontantCompte;
		this.MontantCompteALaFinDuTour=MontantCompteALaFinDuTour;
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
	//public Tresorerie(Transformateur3 acteur) {
		/**
		 * La trésorerie est initialisée comme vide, elle est mise à jour en début de tour idéalement
		 */
		/**this(acteur,
				0,									//montantCompte
				0,									//MontantCompteALaFinDuTour
				0,									//decouvert actuel
				0,									//FacteurPriorite
				Filiere.LA_FILIERE.getBanque().getParametres().get(0),
				Filiere.LA_FILIERE.getBanque().getParametres().get(1),
				Filiere.LA_FILIERE.getBanque().getParametres().get(2),
				Filiere.LA_FILIERE.getBanque().getParametres().get(3),
				Filiere.LA_FILIERE.getBanque().getParametres().get(4));
	}*/
	
	public Tresorerie(Transformateur3 acteur, double MontantCompteALaFinDuTour, double Facteur,double investissementBasACeTour,double investissementSecondaireACeTour,
			double venteBasACeTour,double venteSecondaireACeTour,List<Double> investissementBasGlobal, List<Double> investissementSecondaireGlobal,	List<Double> venteBasGlobal, 
			List<Double> venteSecondaireGlobal, int tour) {
		this.acteur = acteur;
		this.MontantCompteALaFinDuTour=MontantCompteALaFinDuTour;
		this.FacteurPrioriteGamme=Facteur;
		this.investissementBasACeTour=investissementBasACeTour;
		this.investissementSecondaireACeTour=investissementSecondaireACeTour;
		this.venteBasACeTour=venteBasACeTour;
		this.venteSecondaireACeTour=venteSecondaireACeTour;
		this.tour=tour;
	}

	public Tresorerie(Transformateur3 acteur) {
		this(acteur,
				0,
				0.8,
				0,
				0,
				0,
				0,
				new ArrayList<Double>(),
				new ArrayList<Double>(),
				new ArrayList<Double>(),
				new ArrayList<Double>(),
				0);
	}
	
	
	public Transformateur3 getActeur() {
		return acteur;
	}

	public double getMontantCompte() {
		//le découvert = montant compte si il est négatif
		//V1 : le montant total est utilisé indifférement des filières
		//V2 : on sépare les montants max accordés aux filières haute/basse avec le facteur de priorité
		return Filiere.LA_FILIERE.getBanque().getSolde(this.acteur, this.acteur.getCryptogramme());
	}
	
	public double getMontantCompteALaFinDuTour() {
		return this.MontantCompteALaFinDuTour;
	}
		
	/**
	     * Le facteur qui décrit la priorité que nous mettons sur le bas de gamme par rapport au haut de gamme (évolue au cours de la
	     * simulation)
	     */
	public double getFacteurPrioriteGamme() {
		
		return this.FacteurPrioriteGamme;
	}
	
	
		
	public double getInvestissementBasACeTour() {
		return investissementBasACeTour;
	}

	public void setInvestissementBasACeTour(double investissementBasACeTour) {
		this.investissementBasACeTour = investissementBasACeTour;
	}
	


	public List<Double> getInvestissementBasGlobal() {
		return investissementBasGlobal;
	}

	public List<Double> getInvestissementSecondaireGlobal() {
		return investissementSecondaireGlobal;
	}

	public List<Double> getVenteBasGlobal() {
		return venteBasGlobal;
	}

	public List<Double> getVenteSecondaireGlobal() {
		return venteSecondaireGlobal;
	}

	public double getVenteBasACeTour() {
		return venteBasACeTour;
	}

	public void setVenteBasACeTour(double venteBasACeTour) {
		this.venteBasACeTour = venteBasACeTour;
	}

	public double getVenteSecondaireACeTour() {
		return venteSecondaireACeTour;
	}

	public void setVenteSecondaireACeTour(double venteSecondaireACeTour) {
		this.venteSecondaireACeTour = venteSecondaireACeTour;
	}

	public double getInvestissementSecondaireACeTour() {
		return investissementSecondaireACeTour;
	}

	public void setInvestissementSecondaireACeTour(double investissementSecondaireACeTour) {
		this.investissementSecondaireACeTour = investissementSecondaireACeTour;
	}
	
	

	public int getTour() {
		return tour;
	}

	public void setTour(int tour) {
		this.tour = tour;
	}

	public Variable getDecouvertsConsecutifsAvantFaillite() {
		return Filiere.LA_FILIERE.getBanque().getParametres().get(0);
	}
	
	public Variable getDecouvertAutorise() {
		return Filiere.LA_FILIERE.getBanque().getParametres().get(1);
	}
	
	public Variable getAgiosDecouvertAutorise() {
		return Filiere.LA_FILIERE.getBanque().getParametres().get(2);
	}
	
	public Variable getAgiosDecouvertAuDela() {
		return Filiere.LA_FILIERE.getBanque().getParametres().get(3);
	}
	
	public Variable getSeuilOperationsRefusees() {
		return Filiere.LA_FILIERE.getBanque().getParametres().get(4);
	}
	
	public void setMontantCompteALaFinDuTour (double MontantCompteALaFinDuTour) {
		this.MontantCompteALaFinDuTour=MontantCompteALaFinDuTour;
	}

	/**
	     * Le facteur qui décrit la priorité que nous mettons sur le bas de gamme par rapport au haut de gamme (évolue au cours de la
	     * simulation)
	     */
	public void setFacteurPrioriteGamme (double Facteur) {
		
		this.FacteurPrioriteGamme=Facteur;
	}
	/**
	     * Renvoie l'investissement maximum possible à faire sur la fillière principale (bas de gamme) par rapport à la filière secondaire
	     */
	public void updateFacteurPrioriteGamme () {
		if (this.calculRentabiliteBas()!=-1 && this.calculRentabiliteSecondaire()!=-1) { //-1 signifie qu'il n'y avait pas assez d'infos d'achats et de ventes pour le calcul de la rentabilité
		
		
		if (this.calculRentabiliteBas()>this.calculRentabiliteSecondaire()) {   //si 1 filière est + rentable, on modifie le facteur de priorité (proportionnellement à l'écart relatif des 2 rentabilités
			double facteur_chgt=(this.calculRentabiliteBas()/this.calculRentabiliteSecondaire());
			if (this.getFacteurPrioriteGamme()+0.05*facteur_chgt>=0.9) {
				this.setFacteurPrioriteGamme(0.9);
			}					//on ne dépasse pas un trop gros seuil de priorité pour garder un minimum de variété
			else {
				this.setFacteurPrioriteGamme(this.getFacteurPrioriteGamme()+0.05);
			}
		}
		else { if (this.calculRentabiliteBas()<this.calculRentabiliteSecondaire()) {
			double facteur_chgt=(this.calculRentabiliteSecondaire()/this.calculRentabiliteBas());
			if (this.getFacteurPrioriteGamme()-facteur_chgt*0.05<=0.1) {
				this.setFacteurPrioriteGamme(0.1);
			}	
			else {
				this.setFacteurPrioriteGamme(this.getFacteurPrioriteGamme()-0.05);
			}
		}
		else {}
		}
		}
	}
	
	public double calculRentabiliteBas() {				//on cherche les 10 derniers investissements et les 10 dernieres ventes non nulles les plus récentes, on calcule la rentabilité dessus
		List<Double> V=this.getVenteBasGlobal();
		List<Double> I=this.getInvestissementBasGlobal();
		double venteTot=0;
		double investTot=0;
		int nbr_ventes_non_nulles=0;
		int nbr_investissements_non_nuls=0;
		int t_actuel=this.getTour();
		int t=t_actuel+1;
		while ((nbr_ventes_non_nulles<10 | nbr_investissements_non_nuls<10) && t>0) {
			t=t-1;
			if (I.get(t_actuel-t)!=0 && nbr_investissements_non_nuls<10) {
				nbr_investissements_non_nuls=nbr_investissements_non_nuls+1;
				investTot=investTot+I.get(t);
			}
			if (V.get(t_actuel-t)!=0 && nbr_ventes_non_nulles<10) {
				nbr_ventes_non_nulles=nbr_ventes_non_nulles+1;
				venteTot=venteTot+V.get(t);
			}
		}
			
		
		if (nbr_investissements_non_nuls!=10 | nbr_ventes_non_nulles!=10) {
			return (-1);	//-1 signifie qu'il n'y avait pas assez d'infos d'achats et de ventes pour le calcul de la rentabilité
		} else {
			return (venteTot/investTot)  ;
		}
		
	}
	
	public double calculRentabiliteSecondaire() {
		List<Double> V=this.getVenteSecondaireGlobal();
		List<Double> I=this.getInvestissementSecondaireGlobal();
		double venteTot=0;
		double investTot=0;
		int nbr_ventes_non_nulles=0;
		int nbr_investissements_non_nuls=0;
		int t_actuel=this.getTour();
		int t=t_actuel+1;
		while ((nbr_ventes_non_nulles<10 | nbr_investissements_non_nuls<10) && t>0) {
			t=t-1;
			if (I.get(t_actuel-t)!=0 && nbr_investissements_non_nuls<10) {
				nbr_investissements_non_nuls=nbr_investissements_non_nuls+1;
				investTot=investTot+I.get(t);
			}
			if (V.get(t_actuel-t)!=0 && nbr_ventes_non_nulles<10) {
				nbr_ventes_non_nulles=nbr_ventes_non_nulles+1;
				venteTot=venteTot+V.get(t);
			}
		}
			
		
		if (nbr_investissements_non_nuls!=10 | nbr_ventes_non_nulles!=10) {
			return (-1);	//-1 signifie qu'il n'y avait pas assez d'infos d'achats et de ventes pour le calcul de la rentabilité
		} else {
			return (venteTot/investTot)  ;
		}
	}

	public void next() {
		/**
		 * Met à jour toute la trésorerie
		 */
		
		this.investissementBasGlobal.add(this.getInvestissementBasACeTour());
		this.investissementSecondaireGlobal.add(this.getInvestissementSecondaireACeTour());
		this.venteBasGlobal.add(this.getVenteBasACeTour());
		this.venteSecondaireGlobal.add(this.getVenteSecondaireACeTour());
		this.updateFacteurPrioriteGamme();
		this.setMontantCompteALaFinDuTour(0); 						
		this.setInvestissementBasACeTour(0);
		this.setInvestissementSecondaireACeTour(0);
		this.setVenteBasACeTour(0);
		this.setVenteSecondaireACeTour(0);
		this.setTour(this.getTour()+1);
	}
	
	public void jaiAchetePrincipale(double montant) {
		this.setInvestissementBasACeTour(this.getInvestissementBasACeTour()+montant);
	}
	
	public void jaiAcheteSecondaire(double montant) {
		this.setInvestissementSecondaireACeTour(this.getInvestissementSecondaireACeTour()+montant);
	}
	
	public void jaiVenduPrincipale(double montant) {
		this.setVenteBasACeTour(this.getVenteBasACeTour()+montant);
	}
	
	public void jaiVenduSecondaire(double montant) {
		this.setVenteSecondaireACeTour(this.getVenteSecondaireACeTour()+montant);
	}
	
	public double investissementMaxBasDeGamme() {
		
		return this.getMontantCompte()*this.getFacteurPrioriteGamme();
	}
	
	/**
	     * Renvoie l'investissement maximum possible à faire dans le haut de gamme en fonction du facteur de priorité que l'on s'impose
	     * ainsi que du montant de notre trésorerie
	     */
	
	public double investissementMaxCompteSecondaire() {
		
		return this.getMontantCompte()*(1-this.getFacteurPrioriteGamme());
	}
	
	
	
	public void diminueTresorerie(double montant) {
		Filiere.LA_FILIERE.getBanque().virer(this.getActeur(), this.getActeur().getCryptogramme(), Filiere.LA_FILIERE.getBanque(), montant);
	}
	
	
	
}