package abstraction.eq4Transformateur2;

import java.util.List;

import abstraction.eq8Romu.contratsCadres.ExemplaireContratCadre;
import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.Feve;
import abstraction.eq8Romu.produits.Gamme;
import abstraction.eq8Romu.produits.Pate;
import abstraction.fourni.Filiere;
import abstraction.fourni.Variable;

public class Transformateur2_gestion_stocks extends Transformateur2_contratCadre {
	
	// donne la valeur totale des stocks
	private Variable valeurDesStocks ;
	
	// coût d'entretien des stocks par unité (tonne), ne dépend que du type de denrée
	
	private Variable coutUnitaireStockFeves ; 
	private Variable coutUnitaireStockPate ; 
	private Variable coutUnitaireStockChocolat ; 
	
	//seuil critique de production, qu'il soit trop bas ou trop haut: compris entre 0 et 1, pourcentage par rapport à capacité MAX
	private Variable seuilSupTFEP;
	private Variable seuilInfTFEP;
	private Variable seuilSupTPEC;
	private Variable seuilInfTPEC;
	
	public Transformateur2_gestion_stocks() {
		
		super () ; 
		
		//utilise la fonction juste en dessous pour l'initialisation
		this.valeurDesStocks = new Variable(getNom()+" valeur totale des stocks", this, this.calculeValeurDesStocks()) ;
		this.seuilInfTFEP=new Variable (getNom()+" seuil pour diminuer capacité MAX Feve -> Pate", this, 0);
		this.seuilSupTFEP=new Variable (getNom()+" seuil pour augmenter capacité MAX Feve -> Pate", this, 1);
		this.seuilInfTPEC=new Variable (getNom()+" seuil pour diminuer capacité MAX Pate -> Choco", this, 0);
		this.seuilSupTPEC=new Variable (getNom()+" seuil pour augmenter capacité MAX Pate -> Choco", this, 1);
		
		this.coutUnitaireStockFeves = new Variable(getNom()+" cout unitaire dû à l'entretien des stocks de feves", this, 0.1) ;
		this.coutUnitaireStockPate = new Variable(getNom()+" cout unitaire dû à l'entretien des stocks de pate", this, 0.1) ;
		this.coutUnitaireStockChocolat = new Variable(getNom()+" cout unitaire dû à l'entretien des stocks de chocolat", this, 0.1) ;
		
	}
	
	// Permet de calculer la valeur des stocks en additionnant la valeur de chaque stock de denrée, obtenu
	// grâce au coût moyen de la denrée et de la quantité en stock
		// A effectuer à chaque fin de tour
	
	public double calculeValeurDesStocks () {
		double valeur = 0 ;
		for (Feve feve :Feve.values()) {
			valeur += super.getCoutMoyenFeveValeur(feve) * super.getStockFevesValeur(feve) ;
		}
		for (PateInterne pate :PateInterne.values()) {
			valeur += super.getCoutMoyenPateValeur(pate) * super.getStockPateValeur(pate) ;
		}
		for (Chocolat chocolat : Chocolat.values()) {
			valeur += super.getCoutMoyenChocolatValeur(chocolat) * super.getStockChocolatValeur(chocolat) ;
		}
		return valeur ;
	}
	
	public List<Variable> getIndicateurs() { 
		List<Variable> res=super.getIndicateurs();
		res.add(this.valeurDesStocks) ;
		return res;
	}
	
	public List<Variable> getParametres() { //idem
		List<Variable> res=super.getParametres();
		res.add(this.seuilSupTFEP) ;
		res.add(this.seuilInfTFEP) ;
		res.add(this.seuilSupTPEC) ;
		res.add(this.seuilInfTPEC) ;
		return res;
	}
	
	// calcule l'ensemble des coûts dûs à l'entretien des stocks
		// à exécuter à chaque fin de tour
		
	public double coutStocksChoc() {
		return this.coutUnitaireStockChocolat.getValeur()*super.getStockTotalChocolat();
	}
	
	
		public double coutStocks () {  
			double cout = 0 ;
			cout += this.coutUnitaireStockFeves.getValeur()*super.getStockTotalFeves() ; 
			cout += this.coutUnitaireStockPate.getValeur()*super.getStockTotalPate() ; 
			cout += this.coutUnitaireStockChocolat.getValeur()*super.getStockTotalChocolat() ; 
			return cout ;
		}
		
	
}
