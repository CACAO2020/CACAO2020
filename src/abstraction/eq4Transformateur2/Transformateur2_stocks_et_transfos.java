package abstraction.eq4Transformateur2; 

import abstraction.fourni.Filiere;
import abstraction.fourni.Variable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.Feve;
import abstraction.eq8Romu.produits.Gamme;

//extension gérant les stocks et la transformation

public class Transformateur2_stocks_et_transfos extends Transformateur2_acteur {
	
	// Notation : TFEP : transformation fève en pâte
	// 			  TPEC : transformation pâte en chocolat
	
	
	//variables
	
	//donnent la limite de la quantité de materiaux transformable par tour (limitation due aux infrastructures et employés disponibles)
	private Variable capaciteMaxTFEP ; 
	private Variable capaciteMaxTPEC ;
	
	private double coutPourAugmenterCapaTFEP = 1;
	private double coutPourAugmenterCapaTPEC = 1;
	
	
	// coût unitaire moyen d'achat de chaque denrée, on ne distingue pas les lots, les stocks sont 
	// considérés comme constitués d'une seule denrée, peu importe quand il a été acheté
		// à mettre à jour à chaque ajout/retrait dans les stocks
	
	private Map<Feve, Variable> coutMoyenFeves ; 
	private Map<PateInterne, Variable> coutMoyenPate ; 
	private Map<Chocolat, Variable> coutMoyenChocolat ;
	
	
	//paramètres
	
	// coûts de transformation d'une unité (tonne), ne dépend que de la gamme et du type de transformation effectuée
	
	private Map<Gamme, Variable> coutsUnitairesTFEP ; 
	private Map<Gamme, Variable> coutsUnitairesTPEC ; 
	
	// coûts d'entretien d'une unité (capacité en tonne) de la capacité max (infrastructures et salaires)
	
	private Variable coutUnitaireEntretienTFEP ; 
	private Variable coutUnitaireEntretienTPEC ; 
	
	private Variable coeffTFEP ; //équivalent en pâte d'une unité de fèves
	private Variable coeffTPEC ; //équivalent en chocolat d'une unité de pate
	
	// l'initialisation nécessite de nombreuses variables, qui sont à modifier pour les tests
	// il faut déterminer ces valeurs en essayant d'être réalistes et cohérents avec les autres équipes
	
	public Transformateur2_stocks_et_transfos() {
		
		super () ; 
				

		this.capaciteMaxTFEP = new Variable(getNom()+" limite transformation feve en pate", this, 500) ;
		this.capaciteMaxTPEC = new Variable(getNom()+" limite transformation pate en chocolat", this, 500) ;
		this.coeffTFEP = new Variable(getNom()+" équivalent en pâte d'une unité de fèves", this, 1) ;
		this.coeffTPEC = new Variable(getNom()+" équivalent en chocolat d'une unité de chocolat", this, 1) ;
				
		this.coutsUnitairesTFEP = new HashMap<Gamme, Variable>() ;
		this.coutsUnitairesTFEP.put(Gamme.BASSE, new Variable(getNom()+" cout unitaire de transformation de basse qualité fèves vers pâte)", this, 10)) ;
		this.coutsUnitairesTFEP.put(Gamme.MOYENNE, new Variable(getNom()+" cout unitaire de transformation de moyenne qualité fèves vers pâte)", this, 12)) ;
		this.coutsUnitairesTFEP.put(Gamme.HAUTE, new Variable(getNom()+" cout unitaire de transformation de haute qualité fèves vers pâte)", this, 15)) ;
		
		this.coutsUnitairesTPEC = new HashMap<Gamme, Variable>() ;
		this.coutsUnitairesTPEC.put(Gamme.BASSE, new Variable(getNom()+" cout unitaire de transformation de basse qualité pâte vers chocolat)", this, 10)) ;
		this.coutsUnitairesTPEC.put(Gamme.MOYENNE, new Variable(getNom()+" cout unitaire de transformation de moyenne qualité pâte vers chocolat)", this, 12)) ;
		this.coutsUnitairesTPEC.put(Gamme.HAUTE, new Variable(getNom()+" cout unitaire de transformation de haute qualité pâte vers chocolat)", this, 15)) ;
		
		this.coutUnitaireEntretienTFEP  = new Variable(getNom()+" cout unitaire d'entretien de la capacité de transformation de fève en pâte", this, 1) ;
		this.coutUnitaireEntretienTPEC  = new Variable(getNom()+" cout unitaire d'entretien de la capacité de transformation de pâte en chocolat", this, 1) ;
		
		this.coutMoyenFeves = new HashMap<Feve, Variable>() ;

		this.coutMoyenFeves.put(Feve.FEVE_BASSE, new Variable(getNom()+" cout unitaire moyen à l'achat des feves basses", this, 100)) ;
		this.coutMoyenFeves.put(Feve.FEVE_MOYENNE, new Variable(getNom()+" cout unitaire moyen à l'achat des feves moyennes", this, 120)) ;
		this.coutMoyenFeves.put(Feve.FEVE_HAUTE, new Variable(getNom()+" cout unitaire moyen à l'achat des feves hautes", this, 150)) ;
		this.coutMoyenFeves.put(Feve.FEVE_MOYENNE_EQUITABLE,new Variable(getNom()+" cout unitaire moyen à l'achat des feves moyennes equitables", this, 150)) ;
		this.coutMoyenFeves.put(Feve.FEVE_HAUTE_EQUITABLE, new Variable(getNom()+" cout unitaire moyen à l'achat des feves hautes equitables", this, 200)) ;
		
		this.coutMoyenPate = new HashMap<PateInterne, Variable>() ;
		this.coutMoyenPate.put(PateInterne.PATE_BASSE, new Variable(getNom()+" cout unitaire moyen à l'achat de pate basse", this, 150)) ;
		this.coutMoyenPate.put(PateInterne.PATE_MOYENNE, new Variable(getNom()+" cout unitaire moyen à l'achat de pate moyenne", this, 170)) ;
		this.coutMoyenPate.put(PateInterne.PATE_HAUTE, new Variable(getNom()+" cout unitaire moyen à l'achat de pate haute", this, 200)) ;
		this.coutMoyenPate.put(PateInterne.PATE_MOYENNE_EQUITABLE, new Variable(getNom()+" cout unitaire moyen à l'achat de pate moyenne equitable", this, 200));
		this.coutMoyenPate.put(PateInterne.PATE_HAUTE_EQUITABLE, new Variable(getNom()+" cout unitaire moyen à l'achat de pate haute equitable", this, 250));
		
		this.coutMoyenChocolat = new HashMap<Chocolat, Variable>() ;
		this.coutMoyenChocolat.put(Chocolat.CHOCOLAT_BASSE, new Variable(getNom()+" cout unitaire moyen à l'achat de chocolat basse", this, 250)) ;
		this.coutMoyenChocolat.put(Chocolat.CHOCOLAT_MOYENNE, new Variable(getNom()+" cout unitaire moyen à l'achat de chocolat moyenne", this, 270)) ;
		this.coutMoyenChocolat.put(Chocolat.CHOCOLAT_HAUTE, new Variable(getNom()+" cout unitaire moyen à l'achat de chocolat haute", this, 300)) ;
		this.coutMoyenChocolat.put(Chocolat.CHOCOLAT_MOYENNE_EQUITABLE, new Variable(getNom()+" cout unitaire moyen à l'achat de chocolat moyenne equitable", this, 300)) ;
		this.coutMoyenChocolat.put(Chocolat.CHOCOLAT_HAUTE_EQUITABLE, new Variable(getNom()+" cout unitaire moyen à l'achat de chocolat haute equitable", this, 350)) ;
	
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
	
	public double getCoutUnitaireEntretienTFEP() {
		return this.coutUnitaireEntretienTFEP.getValeur();
	}

	public double getCoutUnitaireEntretienTPEC() {
		return this.coutUnitaireEntretienTPEC.getValeur();
	}
	
	public double getCapaciteMaxTFEP() {
		return this.capaciteMaxTFEP.getValeur();
	}
	
	public double getCapaciteMaxTPEC() {
		return this.capaciteMaxTPEC.getValeur();
	}
	
	
	public void setCapaciteMaxTFEP(double capaciteMaxTFEP) {
		this.capaciteMaxTFEP.setValeur(this, capaciteMaxTFEP);
	}

	public void setCapaciteMaxTPEC(double capaciteMaxTPEC) {
		this.capaciteMaxTPEC.setValeur(this, capaciteMaxTPEC);
	}

	public double getCoutPourAugmenterCapaTFEP() {
		return coutPourAugmenterCapaTFEP;
	}

	public double getCoutPourAugmenterCapaTPEC() {
		return coutPourAugmenterCapaTPEC;
	}
	
	// getters : ici, donne le coût unitaire d'entretien de la capacité de transformation pour les deux transformation
	// A chaque étape, la quantité totale transformée change, et donc le coût unitaire dû à l'entretien des salaires
	//  et infrastructures peut varier
	
	public double getCoutUnitaireEntretienTFEPauStep (double quantiteTotaleFevesATransfo) {
		if (quantiteTotaleFevesATransfo != 0) {
			return this.getCoutUnitaireEntretienTFEP() * this.capaciteMaxTFEP.getValeur() / quantiteTotaleFevesATransfo ;
		}
		else { throw new IllegalArgumentException ("quantite totale nulle") ; }
	}
	
	public double getCoutUnitaireEntretienTPECauStep (double quantiteTotalePateATransfo) {
		if (quantiteTotalePateATransfo != 0) {
			return this.getCoutUnitaireEntretienTPEC() * this.capaciteMaxTPEC.getValeur() / quantiteTotalePateATransfo ;
		}
		else { throw new IllegalArgumentException ("quantite totale nulle") ; }
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
	
	// ATTENTION : la transformation doit être faite en une fois, sinon le coût de transformation n'est pas fiable
	
	public double prixApresTFEP (Feve feve, double quantiteTotaleFeveTransfo) {
		if (quantiteTotaleFeveTransfo == 0) {
			throw new IllegalArgumentException ("Quantité à transformer nulle") ;
		}
		double prix = this.getCoutMoyenFeveValeur(feve) ;
		prix += this.getCoutUnitaireEntretienTFEPauStep(quantiteTotaleFeveTransfo) ;
		prix += this.getCoutUnitaireTFEP(feve.getGamme()) ;
		prix = prix / this.getCoeffTFEP() ;
		return prix ;
	}
	
	public double transformationFeveEnPate (double quantiteFeve, Feve feve, double quantiteTotaleFeveTransfo) { 
			double nouveauStockFeve = super.getStockFevesValeur(feve) - quantiteFeve ;
			if (nouveauStockFeve >= 0) {
				double cout = 0 ;
				PateInterne pate = this.creerPateAPartirDeFeve(feve) ;
				double quantitePate = this.getCoeffTFEP()*quantiteFeve ;
				if (quantitePate > 0) {
					cout = quantitePate*this.getCoutUnitaireTFEP(feve.getGamme()) ;
					cout += this.getCapaciteMaxTFEP()*this.getCoutUnitaireEntretienTFEP() ;
					double prix = this.prixApresTFEP(feve, quantiteTotaleFeveTransfo);
					/*System.out.println("prix");
					System.out.println(prix);*/
					this.modifierCoutMoyenPate(pate, quantitePate, prix);
				}
				super.setStockFevesValeur(feve,nouveauStockFeve) ;
				super.setStockPateValeur(pate, super.getStockPateValeur(pate) + quantitePate ) ;
				return cout ;
			} 
			else {throw new IllegalArgumentException("stock negatif") ;} 
		}
	
	public double prixApresTPEC (PateInterne pate, double quantiteTotalePateTransfo) {
		if (quantiteTotalePateTransfo == 0) {
			throw new IllegalArgumentException ("Quantité à transformer nulle") ;
		}
		double prix = this.getCoutMoyenPateValeur(pate) ;
		prix += this.getCoutUnitaireEntretienTPECauStep(quantiteTotalePateTransfo) ;
			prix += this.getCoutUnitaireTPEC(pate.getGamme()) ;
			prix = prix / this.getCoeffTPEC() ;
			return prix ;
		}
	
	public double transformationPateEnChocolat (double quantitePate, PateInterne pate, double quantiteTotalePateTransfo) {
		double nouveauStockPate = super.getStockPateValeur(pate) - quantitePate ;
		if (nouveauStockPate >= 0) {
			double cout = 0 ;
			Chocolat chocolat = this.creerChocolat(pate) ;
			double quantiteChocolat = this.getCoeffTPEC()*quantitePate ;
			if (quantiteChocolat > 0) {
				cout = quantitePate*this.getCoutUnitaireTPEC(pate.getGamme()) ;
				cout += this.getCapaciteMaxTPEC()*this.getCoutUnitaireEntretienTPEC() ;
				double prix = this.prixApresTPEC(pate, quantiteTotalePateTransfo );
				/*System.out.println("prix");
				System.out.println(prix);*/
				this.modifierCoutMoyenChocolat(chocolat, quantiteChocolat, prix);
			}
			super.setStockPateValeur(pate,nouveauStockPate) ;
			super.setStockChocolatValeur(chocolat, super.getStockChocolatValeur(chocolat) + quantiteChocolat ) ;
			return cout ;
		}
		else {throw new IllegalArgumentException("stock negatif") ;} 
	}
	
	public double getQuantiteTFEP (Map<Feve, Double> quantites) {
		double quantiteTotale = 0 ;
		for (Feve feve : quantites.keySet()) {
			quantiteTotale += quantites.get(feve) ;
		}
		return quantiteTotale ;
	}
	
	public double getQuantiteTPEC (Map<PateInterne, Double> quantites) {
		double quantiteTotale = 0 ;
		for (PateInterne pate : quantites.keySet()) {
			quantiteTotale += quantites.get(pate) ;
		}
		return quantiteTotale ;
	}
	
	// on corrige les quantités de denrée données par le dictionnaire. Tant que la capacité est
		// suffisante, on garde les quantites prévues, et si elle ne l'est plus on limite avec la capacité max
	// return false si saturation de la capacité de production, true sinon
	
	//il pourrait être intéressant de mettre un ordre de priorité dans les gammes
	
	public boolean correctionQuantitesTFEP (Map<Feve,Double> quantitesFeve) { 
		double quantiteFeveTotale = 0 ;
		boolean b = true ;
		Map<Feve,Double> quantitesCorrige = quantitesFeve ;
		for (Feve feve : quantitesFeve.keySet()) {
			if (super.getStockFevesValeur(feve) < quantitesFeve.get(feve)) {
				quantitesFeve.replace(feve, super.getStockFevesValeur(feve)) ;
			}
			if (this.capaciteInsuffisanteTFEP(quantiteFeveTotale + quantitesFeve.get(feve))) {
				double quantiteFeve = this.getCapaciteMaxTFEP() - quantiteFeveTotale ;
				quantitesCorrige.replace(feve, quantiteFeve) ;
				quantiteFeveTotale += quantiteFeve ;
				b = false ;
			}
			else { quantiteFeveTotale += quantitesFeve.get(feve) ; }
		}
		return b ; 
	} 
	
	public boolean correctionQuantitesTPEC (Map<PateInterne,Double> quantitesPate) { 
		double quantitePateTotale = 0 ;
		Map<PateInterne,Double> quantitesCorrige = quantitesPate ;
		for (PateInterne pate : quantitesPate.keySet()) {
			if (super.getStockPateValeur(pate) < quantitesPate.get(pate)) {
				quantitesPate.replace(pate, super.getStockPateValeur(pate)) ;
			}
			if (this.capaciteInsuffisanteTPEC(quantitePateTotale + quantitesPate.get(pate))) {
				double quantiteFeve = this.getCapaciteMaxTPEC() - quantitePateTotale ;
				quantitesCorrige.replace(pate, quantiteFeve) ;
				quantitePateTotale += quantitesPate.get(pate) ;
				return false ;
			}
			else { quantitePateTotale += quantitesPate.get(pate) ; }
		}
		return true ; 
	} 

// fonctions de transformation
	
	//argument : dictionnaire donnant la quantité à transformer pour chaque type de denrée
	// on le corrige, puis on effectue la transformation pour chaque denrée 
			
	public double transformationFeves (Map<Feve,Double> quantitesFeve) { 
		this.correctionQuantitesTFEP(quantitesFeve) ;
		double cout = 0 ;
		for (Feve feve : quantitesFeve.keySet()) {
			cout += this.transformationFeveEnPate (quantitesFeve.get(feve), feve, this.getQuantiteTFEP(quantitesFeve)) ;
		} 
		return cout ;
	}
	
	public double transformationPate (Map<PateInterne,Double> quantitesPate) { 
		this.correctionQuantitesTPEC(quantitesPate) ;
		double cout = 0 ;
		for (PateInterne pate : quantitesPate.keySet()) {
			cout += this.transformationPateEnChocolat (quantitesPate.get(pate), pate, this.getQuantiteTPEC(quantitesPate)) ;
		} 
		return cout ;
	}
		
/* AUGMENTER LA CAPACITE DE TRANSFORMATION */
	public void investirCapaTFEP(double qteAInvestir) {
			this.capaciteMaxTFEP.setValeur(this, this.getCapaciteMaxTFEP()+ qteAInvestir/this.getCoutPourAugmenterCapaTFEP());
			Filiere.LA_FILIERE.getBanque().virer(this, this.cryptogramme, Filiere.LA_FILIERE.getBanque(), qteAInvestir);
	}
	
	public void investirCapaTPEC(double qteAInvestir) {
		this.capaciteMaxTPEC.setValeur(this, this.getCapaciteMaxTPEC()+ qteAInvestir/this.getCoutPourAugmenterCapaTPEC());
		Filiere.LA_FILIERE.getBanque().virer(this, this.cryptogramme, Filiere.LA_FILIERE.getBanque(), qteAInvestir);
	}
}