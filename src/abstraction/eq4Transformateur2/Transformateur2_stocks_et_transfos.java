package abstraction.eq4Transformateur2;

import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.Feve;
import abstraction.eq8Romu.produits.Gamme;
import abstraction.eq8Romu.produits.Pate;
import abstraction.fourni.Filiere;

//extension gérant les stocks et la transformation

public class Transformateur2_stocks_et_transfos extends Transformateur2 implements IActeur {
	
	// Notation : TFEP : transformation fève en pâte
	// 			  TPEC : transformation pâte en chocolat
	
	
	//variables
	
	//donnent la limite de la quantité transformable (limitation due aux infrastructures et employés disponibles)
	private Variable capaciteMaxTFEP ; 
	private Variable capaciteMaxTPEC ;
	
	// coût unitaire moyen d'achat de chaque denrée, on ne distingue pas les lots, les stocks sont 
	// considérés comme constitués d'une seule denrée, peu importe quand il a été acheté
		// à mettre à jour à chaque ajout/retrait dans les stocks
	
	private Map<Feve, Variable> coutMoyenFeves ; 
	private Map<PateInterne, Variable> coutMoyenPate ; 
	private Map<Chocolat, Variable> coutMoyenChocolat ;
	
	// donne la valeur totale des stocks
	private Variable valeurDesStocks ;
	
	
	//paramètres
	
	// coûts de transformation d'une unité (tonne), ne dépend que de la gamme et du type de transformation effectuée
	
	private Map<Gamme, Variable> coutsUnitairesTFEP ; 
	private Map<Gamme, Variable> coutsUnitairesTPEC ; 
	
	private Variable coeffTFEP ; //équivalent en pâte d'une unité de fèves
	private Variable coeffTPEC ; //équivalent en chocolat d'une unité de pate
	
	// coût d'entretien des stocks par unité (tonne), ne dépend que du type de denrée
	
	private Variable coutUnitaireStockFeves ; 
	private Variable coutUnitaireStockPate ; 
	private Variable coutUnitaireStockChocolat ; 
	
	//Coût augmentation de la capacité max
	
	private Variable coutAgrandirCapacite ; 
	
	//seuil critique de production, qu'il soit trop bas ou trop haut: compris entre 0 et 1, pourcentage par rapport à capacité MAX
	
	private Variable seuilSupTFEP;
	private Variable seuilInfTFEP;
	private Variable seuilSupTPEC;
	private Variable seuilInfTPEC;
	
	// l'initialisation nécessite de nombreuses variables, qui sont à modifier pour les tests
	// il faut déterminer ces valeurs en essayant d'être réalistes et cohérents avec les autres équipes
	
	public Transformateur2_stocks_et_transfos() {
		
		super () ; 
		
		this.capaciteMaxTFEP = new Variable(getNom()+" limite transformation feve en pate", this, 100) ;
		this.capaciteMaxTPEC = new Variable(getNom()+" limite transformation pate en chocolat", this, 200) ;
		this.coeffTFEP = new Variable(getNom()+" équivalent en pâte d'une unité de fèves", this, 1) ;
		this.coeffTPEC = new Variable(getNom()+" équivalent en chocolat d'une unité de chocolat", this, 1) ;
		this.coutUnitaireStockFeves = new Variable(getNom()+" cout unitaire dû à l'entretien des stocks de feves", this, 1) ;
		this.coutUnitaireStockPate = new Variable(getNom()+" cout unitaire dû à l'entretien des stocks de pate", this, 1) ;
		this.coutUnitaireStockChocolat = new Variable(getNom()+" cout unitaire dû à l'entretien des stocks de chocolat", this, 1) ;
				
		this.coutsUnitairesTFEP = new HashMap<Gamme, Variable>() ;
		this.coutsUnitairesTFEP.put(Gamme.BASSE, new Variable(getNom()+" cout unitaire de transformation de basse qualité fèves vers pâte)", this, 1)) ;
		this.coutsUnitairesTFEP.put(Gamme.MOYENNE, new Variable(getNom()+" cout unitaire de transformation de moyenne qualité fèves vers pâte)", this, 1)) ;
		this.coutsUnitairesTFEP.put(Gamme.HAUTE, new Variable(getNom()+" cout unitaire de transformation de haute qualité fèves vers pâte)", this, 1)) ;
		
		this.coutsUnitairesTPEC = new HashMap<Gamme, Variable>() ;
		this.coutsUnitairesTPEC.put(Gamme.BASSE, new Variable(getNom()+" cout unitaire de transformation de basse qualité pâte vers chocolat)", this, 1)) ;
		this.coutsUnitairesTPEC.put(Gamme.MOYENNE, new Variable(getNom()+" cout unitaire de transformation de moyenne qualité pâte vers chocolat)", this, 1)) ;
		this.coutsUnitairesTPEC.put(Gamme.HAUTE, new Variable(getNom()+" cout unitaire de transformation de haute qualité pâte vers chocolat)", this, 1)) ;
	
		this.coutMoyenFeves = new HashMap<Feve, Variable>() ;
		this.coutMoyenFeves.put(Feve.FEVE_BASSE, new Variable(getNom()+" cout unitaire moyen à l'achat des feves basses", this, 1)) ;
		this.coutMoyenFeves.put(Feve.FEVE_MOYENNE, new Variable(getNom()+" cout unitaire moyen à l'achat des feves moyennes", this, 2)) ;
		this.coutMoyenFeves.put(Feve.FEVE_HAUTE, new Variable(getNom()+" cout unitaire moyen à l'achat des feves hautes", this, 3)) ;
		this.coutMoyenFeves.put(Feve.FEVE_MOYENNE_EQUITABLE,new Variable(getNom()+" cout unitaire moyen à l'achat des feves moyennes equitables", this, 3)) ;
		this.coutMoyenFeves.put(Feve.FEVE_HAUTE_EQUITABLE, new Variable(getNom()+" cout unitaire moyen à l'achat des feves hautes equitables", this, 4)) ;
		
		this.coutMoyenPate = new HashMap<PateInterne, Variable>() ;
		this.coutMoyenPate.put(PateInterne.PATE_BASSE, new Variable(getNom()+" cout unitaire moyen à l'achat de pate basse", this, 1)) ;
		this.coutMoyenPate.put(PateInterne.PATE_MOYENNE, new Variable(getNom()+" cout unitaire moyen à l'achat de pate moyenne", this, 2)) ;
		this.coutMoyenPate.put(PateInterne.PATE_HAUTE, new Variable(getNom()+" cout unitaire moyen à l'achat de pate haute", this, 3)) ;
		this.coutMoyenPate.put(PateInterne.PATE_MOYENNE_EQUITABLE, new Variable(getNom()+" cout unitaire moyen à l'achat de pate moyenne equitable", this, 3));
		this.coutMoyenPate.put(PateInterne.PATE_HAUTE_EQUITABLE, new Variable(getNom()+" cout unitaire moyen à l'achat de pate haute equitable", this, 4));
		
		this.coutMoyenChocolat = new HashMap<Chocolat, Variable>() ;
		this.coutMoyenChocolat.put(Chocolat.CHOCOLAT_BASSE, new Variable(getNom()+" cout unitaire moyen à l'achat de chocolat basse", this, 1)) ;
		this.coutMoyenChocolat.put(Chocolat.CHOCOLAT_MOYENNE, new Variable(getNom()+" cout unitaire moyen à l'achat de chocolat moyenne", this, 2)) ;
		this.coutMoyenChocolat.put(Chocolat.CHOCOLAT_HAUTE, new Variable(getNom()+" cout unitaire moyen à l'achat de chocolat haute", this, 3)) ;
		this.coutMoyenChocolat.put(Chocolat.CHOCOLAT_MOYENNE_EQUITABLE, new Variable(getNom()+" cout unitaire moyen à l'achat de chocolat moyenne equitable", this, 3)) ;
		this.coutMoyenChocolat.put(Chocolat.CHOCOLAT_HAUTE_EQUITABLE, new Variable(getNom()+" cout unitaire moyen à l'achat de chocolat haute equitable", this, 4)) ;
	
		//utilise la fonction juste en dessous pour l'initialisation
		this.valeurDesStocks = new Variable(getNom()+" valeur totale des stocks", this, this.calculeValeurDesStocks()) ;
	
		this.coutAgrandirCapacite = new Variable (getNom()+" cout unitaire pour augmenter la capacité max", this, 200);
		
		this.seuilInfTFEP=new Variable (getNom()+" seuil pour diminuer capacité MAX Feve -> Pate", this, 0);
		this.seuilSupTFEP=new Variable (getNom()+" seuil pour augmenter capacité MAX Feve -> Pate", this, 1);
		this.seuilInfTPEC=new Variable (getNom()+" seuil pour diminuer capacité MAX Pate -> Choco", this, 0);
		this.seuilSupTPEC=new Variable (getNom()+" seuil pour augmenter capacité MAX Pate -> Choco", this, 1);
	
	}
	
	// Permet de calculer la valeur des stocks en additionnant la valeur de chaque stock de denrée, obtenu
	// grâce au coût moyen de la denrée et de la quantité en stock
		// A effectuer à chaque fin de tour
	
	public double calculeValeurDesStocks () {
		double valeur = 0 ;
		for (Feve feve :Feve.values()) {
			valeur += this.getCoutMoyenFeveValeur(feve) * super.getStockFevesValeur(feve) ;
		}
		for (PateInterne pate :PateInterne.values()) {
			valeur += this.getCoutMoyenPateValeur(pate) * super.getStockPateValeur(pate) ;
		}
		for (Chocolat chocolat : Chocolat.values()) {
			valeur += this.getCoutMoyenChocolatValeur(chocolat) * super.getStockChocolatValeur(chocolat) ;
		}
		return valeur ;
	}
	
	// getters : ici, permettent de récupérer la valeur du coût unitaire de transformation de fève en pâte
	// en connaissant la gamme de la fève, puis de même pour l'autre transformation en connaissant la 
	// gamme de pâte
	
	public double getCoutUnitaireTFEP(Gamme gamme) {
		return this.coutsUnitairesTFEP.get(gamme).getValeur() ;
	}
	
	public double getCoutUnitaireTPEC(Gamme gamme) {
		return this.coutsUnitairesTPEC.get(gamme).getValeur();
	}
	
	// getters
	
	public double getCoeffTFEP() {
		return this.coeffTFEP.getValeur();
	}

	public double getCoeffTPEC() {
		return this.coeffTPEC.getValeur();
	}
	
	public double getCapaciteMaxTFEP() {
		return this.capaciteMaxTFEP.getValeur();
	}
	
	public double getCapaciteMaxTPEC() {
		return this.capaciteMaxTPEC.getValeur();
	}
	
	// getters : ici, donne le coût total de transformation pour les deux étapes
	// On considère que le coût est indépendant de la quantité produite. En effet, seule la capacité
	// maximale, ie les installations et les salaires, sont un coût pour l'entreprise.
	
	public double getCoutTFEP (Feve feve) {
		return this.getCoutUnitaireTFEP(feve.getGamme()) * this.capaciteMaxTPEC.getValeur() ;
	}
	
	public double getCoutTPEC (PateInterne pate) {
		return this.getCoutUnitaireTPEC(pate.getGamme())  * this.capaciteMaxTPEC.getValeur() ;
	}
	
	// getters : ici, permettent de récupérer directement la valeur du coût unitaire moyen des stocks,
	// en prenant en argument une denrée précise
	
	public double getCoutMoyenFeveValeur (Feve feve) {
		return this.coutMoyenFeves.get(feve).getValeur() ;
	}
	
	public double getCoutMoyenPateValeur (PateInterne pate) {
		return this.coutMoyenPate.get(pate).getValeur() ;
	}
	
	public double getCoutMoyenChocolatValeur (Chocolat chocolat) {
		return this.coutMoyenChocolat.get(chocolat).getValeur() ;
	}
	
	
	// récupère les attributs notés comme indicateurs, utile pour les tests et sûrement appelé par des fonctions externes
		//Le prof veut-il plus d'indicateurs que les stocks ? En tout cas, utile pour les tests pour le moment
	
	public List<Variable> getIndicateurs() { 
		List<Variable> res=super.getIndicateurs();
		res.add(this.capaciteMaxTFEP) ;
		res.add(this.capaciteMaxTPEC) ;
		for (Feve feve :Feve.values()) {
			res.add(this.coutMoyenFeves.get(feve)) ;
		}
		for (PateInterne pate : PateInterne.values()) {
			res.add(this.coutMoyenPate.get(pate)) ;
		}
		for (Chocolat chocolat : Chocolat.values()) {
			res.add(this.coutMoyenChocolat.get(chocolat)) ;
		}
		return res;
	}

	// récupère les attributs notés comme paramètres, utile pour les tests et sûrement appelé par des fonctions externes
	
	public List<Variable> getParametres() { //idem
		List<Variable> res=super.getParametres();
		for (Gamme gamme : Gamme.values()) {
			res.add(this.coutsUnitairesTFEP.get(gamme)) ;
		}
		for (Gamme gamme : Gamme.values()) {
			res.add(this.coutsUnitairesTPEC.get(gamme)) ;
		}
		return res;
	}
	
// les fonctions suivantes sont utiles pour réaliser les deux transformations
	
	// renvoie la pâte ayant les mêmes caractéristiques que la fève entrée en argument
	
	public PateInterne creerPateAPartirDeFeve (Feve feve) {
		switch (feve.getGamme()) {
		case BASSE : return PateInterne.PATE_BASSE ; 
		case MOYENNE :
			if (feve.isEquitable()) {
				return PateInterne.PATE_MOYENNE_EQUITABLE ;
			} else {
				return PateInterne.PATE_MOYENNE ;
			}
		case HAUTE : 
			if (feve.isEquitable()) {
				return PateInterne.PATE_HAUTE_EQUITABLE ;
			} else {
				return PateInterne.PATE_HAUTE ;
			}
		default : throw new IllegalArgumentException("valeur non trouvée") ;
		}
	}

	// renvoie le chocolat ayant les mêmes caractéristiques que la pâte entrée en argument

	public Chocolat creerChocolat (PateInterne pate) {
		switch (pate.getGamme()) {
		case BASSE : return Chocolat.CHOCOLAT_BASSE ; 
		case MOYENNE :
			if (pate.isEquitable()) {
				return Chocolat.CHOCOLAT_MOYENNE_EQUITABLE ;
			} else {
				return Chocolat.CHOCOLAT_MOYENNE ;
			}
		case HAUTE : 
			if (pate.isEquitable()) {
				return Chocolat.CHOCOLAT_HAUTE_EQUITABLE ;
			} else {
				return Chocolat.CHOCOLAT_HAUTE ;
			}
		default : throw new IllegalArgumentException("valeur non trouvée") ;
		}
	}
	
	// fonctions inverses, utiles dans d'autres fonctions 
	
	public PateInterne creerPateAPartirDeChocolat (Chocolat chocolat) {
		switch (chocolat.getGamme()) {
		case BASSE : return PateInterne.PATE_BASSE ; 
		case MOYENNE :
			if (chocolat.isEquitable()) {
				return PateInterne.PATE_MOYENNE_EQUITABLE ;
			} else {
				return PateInterne.PATE_MOYENNE ;
			}
		case HAUTE : 
			if (chocolat.isEquitable()) {
				return PateInterne.PATE_HAUTE_EQUITABLE ;
			} else {
				return PateInterne.PATE_HAUTE ;
			}
		default : throw new IllegalArgumentException("valeur non trouvée") ;
		}
	}

	public Feve creerFeve (PateInterne pate) {
		switch (pate.getGamme()) {
		case BASSE : return Feve.FEVE_BASSE ; 
		case MOYENNE :
			if (pate.isEquitable()) {
				return Feve.FEVE_MOYENNE_EQUITABLE ;
			} else {
				return Feve.FEVE_MOYENNE ;
			}
		case HAUTE : 
			if (pate.isEquitable()) {
				return Feve.FEVE_HAUTE_EQUITABLE ;
			} else {
				return Feve.FEVE_HAUTE ;
			}
		default : throw new IllegalArgumentException("valeur non trouvée") ;
		}
	}
	
	// renvoie true si la capacité de transformation n'est pas suffisante pour assurer la transformation
	// d'une quantité donnée de denrée
	
	public boolean capaciteInsuffisanteTFEP(double quantiteFeve) {
		return quantiteFeve > this.capaciteMaxTFEP.getValeur() ;
	}
	
	public boolean capaciteInsuffisanteTPEC(double quantitePate) {
		return quantitePate > this.capaciteMaxTPEC.getValeur() ;
	}
	
	// ces fonctions sont utiles aussi autre part que pour la transformation, il faut les utiliser à chaque
	// ajout d'une quantité à un stock 
	// elles permettent de modifier le coût moyen d'une denrée en sachant la quantité ajoutée et son prix
	// unitaire
	
	public void modifierCoutMoyenFeves (Feve feve, double quantite, double prix) { 
		Variable nouveauCout = this.coutMoyenFeves.get(feve);
		double valeur = (nouveauCout.getValeur()*super.getStockFevesValeur(feve) + prix*quantite)/(super.getStockFevesValeur(feve) + quantite) ;
		nouveauCout.setValeur(this , valeur);
		this.coutMoyenFeves.replace(feve, nouveauCout) ;
	}
	
	public void modifierCoutMoyenPate (PateInterne pate, double quantite, double prix) { 
		Variable nouveauCout = this.coutMoyenPate.get(pate);
		double valeur = (nouveauCout.getValeur()*super.getStockPateValeur(pate) + prix*quantite)/(super.getStockPateValeur(pate) + quantite) ;
		nouveauCout.setValeur(this , valeur);
		this.coutMoyenPate.replace(pate, nouveauCout) ;
	}
	
	public void modifierCoutMoyenChocolat (Chocolat chocolat, double quantite, double prix) { 
		Variable nouveauCout = this.coutMoyenChocolat.get(chocolat);
		double valeur = (nouveauCout.getValeur()*super.getStockChocolatValeur(chocolat) + prix*quantite)/(super.getStockChocolatValeur(chocolat) + quantite) ;
		nouveauCout.setValeur(this , valeur);
		this.coutMoyenChocolat.replace(chocolat, nouveauCout) ;
	}
	
	// transformation élémentaire à partir d'une quantité et de la denrée à transformer :
	// enlève la quantité souhaitée au stock de départ si le stock le permet, puis l'ajoute dans les
	// stocks de la denrée correspondante
	// on détermine le prix unitaire de la denrée correspondante, et on modifie le cout moyen des stocks 
	// de cette denrée en conséquence
	
	public void transformationFeveEnPate (double quantiteFeve, Feve feve) { 
			double nouveauStockFeve = super.getStockFevesValeur(feve) - quantiteFeve ;
			if (nouveauStockFeve >= 0) {
				PateInterne pate = this.creerPateAPartirDeFeve(feve) ;
				double quantitePate = this.getCoeffTFEP()*quantiteFeve ;
				super.setStockFevesValeur(feve,nouveauStockFeve) ;
				super.setStockPateValeur(pate, super.getStockPateValeur(pate) + quantitePate ) ;
				if (quantitePate > 0) {
					double prix = this.getCoutMoyenFeveValeur(feve)/this.getCoeffTFEP();
					this.modifierCoutMoyenPate(pate, quantitePate, prix);
				}
			} 
			else {throw new IllegalArgumentException("stock negatif") ;} 
		}
	
	public void transformationPateEnChocolat (double quantitePate, PateInterne pate) {
		double nouveauStockPate = super.getStockPateValeur(pate) - quantitePate ;
		if (nouveauStockPate >= 0) {
			Chocolat chocolat = this.creerChocolat(pate) ;
			double quantiteChocolat = this.getCoeffTPEC()*quantitePate ;
			super.setStockPateValeur(pate,nouveauStockPate) ;
			super.setStockChocolatValeur(chocolat, super.getStockChocolatValeur(chocolat) + quantiteChocolat ) ;
			if (quantiteChocolat > 0) {
				double prix = this.getCoutMoyenPateValeur(pate)/this.getCoeffTPEC();
				this.modifierCoutMoyenPate(pate, quantiteChocolat, prix);
			}
		}
		else {throw new IllegalArgumentException("stock negatif") ;} 
	}

// fonctions de transformation
	
	//argument : dictionnaire donnant la quantité à transformer pour chaque type de denrée
	// on transforme chaque quantité de denrée donnée par le dictionnaire tant que la capacité est
	// suffisante, et si elle ne l'est plus on transforme ce qu'il est encore possible de transformer
	
	// return false si saturation de la capacité de production, true sinon
	
	public boolean transformationFeves (Map<Feve,Double> quantitesFeve) { 
		double quantiteFeveTotale = 0 ;
		for (Feve feve : quantitesFeve.keySet()) {
			if (this.capaciteInsuffisanteTFEP(quantiteFeveTotale + quantitesFeve.get(feve))) {
				double quantiteFeve = this.getCapaciteMaxTFEP() - quantiteFeveTotale ;
				this.transformationFeveEnPate (quantiteFeve, feve) ;
				quantiteFeveTotale = quantiteFeveTotale + quantitesFeve.get(feve) ;
				return false ;
			}
			else {
				quantiteFeveTotale = quantiteFeveTotale + quantitesFeve.get(feve) ;
				this.transformationFeveEnPate (quantitesFeve.get(feve), feve) ; }
		}
		return true ; 
	}
	
	public boolean transformationPate (Map<PateInterne,Double> quantitesPate) { 
		double quantitePateTotale = 0 ;
		for (PateInterne pate : quantitesPate.keySet()) {
			if (this.capaciteInsuffisanteTPEC(quantitePateTotale + quantitesPate.get(pate))) {
				double quantitePate = this.getCapaciteMaxTPEC() - quantitePateTotale ;
				this.transformationPateEnChocolat (quantitePate, pate) ;
				quantitePateTotale = quantitePateTotale + quantitesPate.get(pate) ;
				return false ;
			}
			else {
				quantitePateTotale = quantitePateTotale + quantitesPate.get(pate) ;
				this.transformationPateEnChocolat (quantitesPate.get(pate), pate) ; }
		}
		return true ; 
	}

// calcule l'ensemble des coûts dûs à l'entretien des stocks
	// à exécuter à chaque fin de tour
	
	public double coutStocks () {  
		double cout = 0 ;
		cout += this.coutUnitaireStockFeves.getValeur()*super.getStockTotalFeves() ; 
		cout += this.coutUnitaireStockPate.getValeur()*super.getStockTotalPate() ; 
		cout += this.coutUnitaireStockChocolat.getValeur()*super.getStockTotalChocolat() ; 
		return cout ;
	}
	
}