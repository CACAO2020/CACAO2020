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
import abstraction.eq8Romu.chocolatBourse.IVendeurChocolatBourse;
import abstraction.fourni.Filiere;

public class Transformateur2 implements IActeur {
	
	//variables : ce sont les stocks, sous la forme de dictionnaires. A chaque type de denrée correspond
	//une Variable, dont la valeur est un double. Cela nous donne les quantités de stocks pour chaque type
	//de denrée.
	
	protected Map<Feve, Variable> stockFeves;
	
	//PateInterne est une classe alternative pour Pate permettant de faciliter toutes les opérations internes
	//Il faut cependant vérifier que lors des échanges avec les codes extérieurs, ce qui est envoyé est du
	//type Pate. On l'utilise ici pour classifier les stocks.
	protected Map<PateInterne, Variable> stockPate ; 
	protected Map<Chocolat, Variable> stockChocolat;


	
	//paramètres
	private Variable coutFixe ; //coûts de fonctionnement, marketing etc
	protected Integer cryptogramme;
	protected Journal journalEq4;

	
	

/* ******LES QUANTITES SONT EN TONNES****** */

	// l'initialisation nécessite de nombreuses variables, qui sont à modifier pour les tests
	// il faut déterminer ces valeurs en essayant d'être réalistes et cohérents avec les autres équipes

	
	public Transformateur2() {
		
		this.stockFeves = new HashMap<Feve, Variable>() ;
		this.stockFeves.put(Feve.FEVE_BASSE, new Variable(getNom()+" stock feves basses", this, 50)) ;
		this.stockFeves.put(Feve.FEVE_MOYENNE, new Variable(getNom()+" stock feves moyennes", this, 50)) ;
		this.stockFeves.put(Feve.FEVE_HAUTE, new Variable(getNom()+" stock feves hautes", this, 0)) ;
		this.stockFeves.put(Feve.FEVE_MOYENNE_EQUITABLE,new Variable(getNom()+" stock feves moyennes equitables", this, 50)) ;
		this.stockFeves.put(Feve.FEVE_HAUTE_EQUITABLE, new Variable(getNom()+" stock feves hautes equitables", this, 0)) ;
		
		this.stockPate = new HashMap<PateInterne, Variable>() ;
		this.stockPate.put(PateInterne.PATE_BASSE, new Variable(getNom()+" stock pate basse", this, 50)) ;
		this.stockPate.put(PateInterne.PATE_MOYENNE, new Variable(getNom()+" stock pate moyenne", this, 50)) ;
		this.stockPate.put(PateInterne.PATE_HAUTE, new Variable(getNom()+" stock pate haute", this, 0)) ;
		this.stockPate.put(PateInterne.PATE_MOYENNE_EQUITABLE, new Variable(getNom()+" stock pate moyenne equitable", this, 50));
		this.stockPate.put(PateInterne.PATE_HAUTE_EQUITABLE, new Variable(getNom()+" stock pate haute equitable", this, 0));
		
		this.stockChocolat = new HashMap<Chocolat, Variable>() ;
		this.stockChocolat.put(Chocolat.CHOCOLAT_BASSE, new Variable(getNom()+" stock chocolat basse", this, 100)) ;
		this.stockChocolat.put(Chocolat.CHOCOLAT_MOYENNE, new Variable(getNom()+" stock chocolat moyenne", this, 100)) ;
		this.stockChocolat.put(Chocolat.CHOCOLAT_HAUTE, new Variable(getNom()+" stock chocolat haute", this, 0)) ;
		this.stockChocolat.put(Chocolat.CHOCOLAT_MOYENNE_EQUITABLE, new Variable(getNom()+" stock chocolat moyenne equitable", this, 100)) ;
		this.stockChocolat.put(Chocolat.CHOCOLAT_HAUTE_EQUITABLE, new Variable(getNom()+" stock chocolat haute equitable", this, 0)) ;
		
		this.cryptogramme = 4 ;
		this.journalEq4 = new Journal("Eq4 activites", this);
		
		this.coutFixe = new Variable(getNom()+" cout fixe (marketing, R&D, fonctionnement...)", this, 200) ;
	}
	
	public String getNom() {
		return "EQ4";
	}

	public String getDescription() {
		return "Chocoptimization";
	}

	public Color getColor() {
		return new Color(155, 89, 182);
	}
	
	//getters, ici permettant de récupérer directement la quantité de stock correspondant à une denrée particulière
	
	public double getStockFevesValeur(Feve feve) {
		return this.stockFeves.get(feve).getValeur() ;
	}
	
	public double getStockPateValeur(PateInterne pate) {
		return this.stockPate.get(pate).getValeur();
	}
	
	public double getStockChocolatValeur(Chocolat chocolat) {
		return this.stockChocolat.get(chocolat).getValeur() ;
	}
	
	//setters, ici permettant de modifier directement la quantité de stock correspondant à une denrée particulière
	
	public void setStockFevesValeur(Feve feve, double valeur) {
		this.stockFeves.get(feve).setValeur(this, valeur) ;
	}
	
	public void setStockPateValeur(PateInterne pate, double valeur) {
		this.stockPate.get(pate).setValeur(this, valeur) ;
	}
	
	public void setStockChocolatValeur(Chocolat chocolat, double valeur) {
		this.stockChocolat.get(chocolat).setValeur(this, valeur) ;
	}

	public void initialiser() {
	}

	public void setCryptogramme(Integer crypto) {
		this.cryptogramme = crypto;
	}
	public void next() {
	}
	
	public List<String> getNomsFilieresProposees() {
		return new ArrayList<String>();
	}

	public Filiere getFiliere(String nom) {
		return null;
	}
	
	// récupère les attributs notés comme indicateurs, utile pour les tests et sûrement appelé par des fonctions externes
	
	public List<Variable> getIndicateurs() {
		List<Variable> res=new ArrayList<Variable>();
		for (Feve feve :Feve.values()) {
			res.add(this.stockFeves.get(feve)) ;
		}
		for (PateInterne pate :PateInterne.values()) {
			// à décommenter si getIndicateurs ne doit pas renvoyer de variables internes, utile pour les tests pour le moment
			//if (pate == PateInterne.PATE_BASSE || pate == PateInterne.PATE_MOYENNE) {
				res.add(this.stockPate.get(pate)) ;
			//}
		}
		for (Chocolat chocolat : Chocolat.values()) {
			res.add(this.stockChocolat.get(chocolat)) ;
		}
		return res;
	}

	// récupère les attributs notés comme paramètres, utile pour les tests et sûrement appelé par des fonctions externes
	
	public List<Variable> getParametres() {
		List<Variable> res=new ArrayList<Variable>();
		res.add(this.coutFixe) ;
		return res; 
	}

	public List<Journal> getJournaux() {
		List<Journal> res=new ArrayList<Journal>();
		res.add(this.journalEq4);
		return res;
	}

	public void notificationFaillite(IActeur acteur) {
		if (this==acteur) {
		System.out.println("I'll be back... or not... "+this.getNom());
		} else {
			System.out.println("Poor "+acteur.getNom()+"... We will miss you. "+this.getNom());
		}
	}
	
	public void notificationOperationBancaire(double montant) {
	}
	
//Connaitre notre solde
	public double getSolde() {
		return Filiere.LA_FILIERE.getBanque().getSolde(this, this.cryptogramme);
	}

	
	// permettent de récupérer les stocks totaux, pourraient servir d'indicateur mais sont surtout utiles
	// pour alléger les codes calculant les coûts d'entretien des stocks
	
	public double getStockTotalFeves () {
		double quantite = 0 ;
		for (Feve feve : Feve.values()) {
			quantite += this.getStockFevesValeur(feve) ;
		}
		return quantite ;
	}
	
	public double getStockTotalPate () {
		double quantite = 0 ;
		for (PateInterne pate : PateInterne.values()) {
			quantite += this.getStockPateValeur(pate) ;
		}
		return quantite ;
	}
	
	public double getStockTotalChocolat () {
		double quantite = 0 ;
		for (Chocolat chocolat : Chocolat.values()) {
			quantite += this.getStockChocolatValeur(chocolat) ;
		}
		return quantite ;
	}
}
