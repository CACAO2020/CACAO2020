

package abstraction.eq2Producteur2;


import abstraction.eq8Romu.cacaoCriee.SuperviseurCacaoCriee;
import abstraction.eq8Romu.produits.Pate;
import abstraction.eq8Romu.produits.Gamme;
import abstraction.eq8Romu.produits.Feve;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;


public class eq2Stock extends eq2Acteur{
/**
 * @author lucas et lucas
 * cette classe gère les StockFeves de fèves et de pâte, les masses sont exprimées en tonnes
 */
	
	private Variable coutStock;
	private HashMap<Feve,Variable> StockFeve;
	private HashMap<Feve,Variable> StockFeveTourPrecedent;
	private HashMap<Feve,Variable> StockFeveTourPrecedent2;
	private HashMap<Pate,Variable> StockPate;
	private double nbemployees;
	private Variable cout_total_Stock;
	
	/**
	 * @author lucas p
	 * 	 */
	public eq2Stock() {
		super();
		this.coutStock = new Variable ("cout",this,1);
		this.StockFeve = new HashMap<Feve,Variable>();
		this.StockPate = new HashMap<Pate,Variable>();
		this.cout_total_Stock= new Variable("cout_total_stock",this,0);
		this.StockFeve.put(Feve.FEVE_BASSE, new Variable("EQ2Feve.FEVE_BASSE",this, 100.0));
		this.StockFeve.put(Feve.FEVE_MOYENNE, new Variable("EQ2Feve.FEVE_MOYENNE",this, 30.0));
		this.StockFeve.put(Feve.FEVE_HAUTE, new Variable("EQ2Feve.FEVE_HAUTE",this, 30.0));
		this.StockFeve.put(Feve.FEVE_MOYENNE_EQUITABLE, new Variable("EQ2Feve.FEVE_MOYENNE_EQUITABLE",this, 30.0));
		this.StockFeve.put(Feve.FEVE_HAUTE_EQUITABLE, new Variable("EQ2Feve.FEVE_HAUTE_EQUITABLE",this, 30.0));
		this.StockFeveTourPrecedent = new HashMap<Feve,Variable>();
		this.StockFeveTourPrecedent2 = new HashMap<Feve,Variable>();
		
		
	}
	
/**
	 * @return the stockFeveTourPrecedent2
	 */
	public HashMap<Feve, Variable> getStockFeveTourPrecedent2() {
		return StockFeveTourPrecedent2;
	}

	/**
	 * @param stockFeveTourPrecedent2 the stockFeveTourPrecedent2 to set
	 */
	public void setStockFeveTourPrecedent2(HashMap<Feve, Variable> stockFeveTourPrecedent2) {
		this.StockFeveTourPrecedent2 =new HashMap<Feve,Variable>();
		for (Feve feve: stockFeveTourPrecedent2.keySet()) {
			this.StockFeveTourPrecedent2.put(feve, new Variable(stockFeveTourPrecedent2.get(feve).getNom(),this,stockFeveTourPrecedent2.get(feve).getValeur()*this.getCoutStock().getValeur()));
	}}

/**
	 * @return the stockFeveTourPrecedent
	 */
	public HashMap<Feve, Variable> getStockFeveTourPrecedent() {
		return StockFeveTourPrecedent;
	}

	/**
	 * @param stockFeveTourPrecedent the stockFeveTourPrecedent to set
	 */
	public void setStockFeveTourPrecedent(HashMap<Feve, Variable> stockFeveTourPrecedent) {
		this.StockFeveTourPrecedent =new HashMap<Feve,Variable>();
		for (Feve feve: stockFeveTourPrecedent.keySet()) {
			this.StockFeveTourPrecedent.put(feve, new Variable(stockFeveTourPrecedent.get(feve).getNom(),this,stockFeveTourPrecedent.get(feve).getValeur()*this.getCoutStock().getValeur()));
	}
	}

public void addStockFeve(Feve feve, double quantité) {
	String type = "EQ2Feve."+feve;
	this.StockFeve.put(feve,new Variable(type,this,quantité));
}
public void addStockPate(Pate pate, double quantité) {
	String type = "EQ2Pate."+pate;
	this.StockPate.put(pate,new Variable(type,this,quantité));
}
	
 public HashMap<Feve,Variable> getStockFeve() {
	 return this.StockFeve;
	 
 }
 public HashMap<Pate,Variable> getStockPate() {
	 return this.StockPate;
	 
 }
 public Variable getCoutTotalStock() {
	 return this.cout_total_Stock;
 }
public void setCoutTotalStock(double new_cout) {
	this.cout_total_Stock.setValeur(this,new_cout);
}
public void setCoutStockFeve(double cout) {
	this.coutStock.setValeur(this, cout);
}
public Variable getCoutStock() {
	return this.coutStock;
}
public void setQtFeve(Feve feve, double quantite) {
	this.getStockFeve().get(feve).setValeur(this, quantite);

}
public void addQtPate(Pate pate, double quantite) {
	if(this.getStockPate().containsKey(pate))
	{this.getStockPate().get(pate).ajouter(this, quantite);}
	else {this.addStockPate(pate, quantite);}

}
public void removeQtPate(Pate pate, double quantite) {
	this.getStockPate().get(pate).retirer(this, quantite);

}
public void setQtPate(Pate pate, double quantite) {
	this.getStockPate().get(pate).setValeur(this, quantite);

}
public void addQtFeve(Feve feve, double quantite) {
	if (this.getStockFeve().containsKey(feve)) {
		this.getStockFeve().get(feve).ajouter(this, quantite);}
	else {
		this.addStockFeve(feve, quantite);
	}	
}
public void removeQtFeve(Feve feve, double quantite) {
	this.getStockFeve().get(feve).retirer(this, quantite);

}
public List<Variable> getVariablesFeve(){
	 List<Variable >variables = new ArrayList<Variable>();
	 for (Feve feve : this.getStockFeve().keySet()) {
		 variables.add(this.getStockFeve().get(feve));
	 }
	return variables;
}
public List<Variable> getVariablesPate(){
	 List<Variable >variables = new ArrayList<Variable>();
	 for (Pate pate : this.getStockPate().keySet()) {
		 variables.add(this.getStockPate().get(pate));
	 }
	return variables;
}
public double getQuantiteFeve(Feve feve) {
	return this.getStockFeve().get(feve).getValeur();
}
public double getQuantitePate(Pate pate) {
	return this.getStockPate().get(pate).getValeur();
}
public void Maintenance() {
	double stock_total = 0;
	for (Feve feve :this.getStockFeve().keySet()) {
		stock_total+=this.getStockFeve().get(feve).getValeur();
	}
	for(Pate pate :this.getStockPate().keySet()) {
		stock_total+=this.getStockPate().get(pate).getValeur();
	}
	this.setCoutTotalStock(stock_total*this.getCoutStock().getValeur()) ;
	Filiere.LA_FILIERE.getBanque().virer(this,this.getCrypto(),Filiere.LA_FILIERE.getBanque(),this.getCoutTotalStock().getValeur());

}




public HashMap<Feve,Variable> VariationStock(HashMap<Feve,Variable> Stock1, HashMap<Feve,Variable> Stock2) {
	HashMap<Feve,Variable> Variation =new HashMap<Feve,Variable>();
	for (Feve feve1 :Stock1.keySet()) {
		for (Feve feve2 :Stock2.keySet()) {
			if(feve1.name().equals(feve2.name())&& Stock2.get(feve2).getValeur()-Stock1.get(feve1).getValeur()!=0) {
				Variation.put(feve1, new Variable(Stock1.get(feve1).getNom(),this,Stock2.get(feve2).getValeur()-Stock1.get(feve1).getValeur()));
			}
			if( !Stock1.containsKey(feve2)) {
				Variation.put(feve2,new Variable(Stock2.get(feve2).getNom(),this,Stock2.get(feve2).getValeur()) );
			}
		}
		if(!Stock2.containsKey(feve1)) {
			Variation.put(feve1, new Variable(Stock1.get(feve1).getNom(),this,-Stock1.get(feve1).getValeur()));
		}
	}
	return Variation;
}

}