

package abstraction.eq2Producteur2;


import abstraction.eq8Romu.cacaoCriee.SuperviseurCacaoCriee;
import abstraction.eq8Romu.produits.Pate;
import abstraction.eq8Romu.produits.Gamme;
import abstraction.eq8Romu.produits.Feve;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;


public class eq2Stock extends eq2Acteur{
/**
 * @author lucas et lucas
 * cette classe gère les StockFeves de fèves et de pâte, les masses sont exprimées en tonnes
 */
	
	private Variable coutStockFeveage;
	private HashMap<Feve,Variable> StockFeve;
	private HashMap<Pate,Variable> StockPate;
	private double nbemployees;
	
	
	/**
	 * @author lucas p
	 * 	 */
	public eq2Stock() {
		super();
		this.coutStockFeveage = new Variable ("cout",this,100);
		this.StockFeve = new HashMap<Feve,Variable>();
		this.StockPate = new HashMap<Pate,Variable>();
		this.nbemployees = 10;
	}
	
public void addStockFeve(Feve feve, double quantité) {
	String type = ""+feve;
	this.StockFeve.put(feve,new Variable(type,this,quantité));
}
public void addStockPate(Pate pate, double quantité) {
	String type = ""+pate;
	this.StockPate.put(pate,new Variable(type,this,quantité));
}
	
 public HashMap<Feve,Variable> getStockFeve() {
	 return this.StockFeve;
	 
 }
 public HashMap<Pate,Variable> getStockPate() {
	 return this.StockPate;
	 
 }

public void setCoutStockFeveage(double cout) {
	this.coutStockFeveage.setValeur(this, cout);
}
public void setQtFeve(Feve feve, double quantite) {
	this.getStockFeve().get(feve).setValeur(this, quantite);

}
public void addQtPate(Pate pate, double quantite) {
	this.getStockPate().get(pate).ajouter(this, quantite);

}
public void removeQtPate(Pate pate, double quantite) {
	this.getStockPate().get(pate).retirer(this, quantite);

}
public void setQtPate(Pate pate, double quantite) {
	this.getStockPate().get(pate).setValeur(this, quantite);

}
public void addQtFeve(Feve feve, double quantite) {
	this.getStockFeve().get(feve).ajouter(this, quantite);

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
/**
 * @author lucas p
 */
public void seuil_employees() {
	double ratio = this.nbemployees/this.NbTotalArbres();
	double ratio_optimal = 1/500;
	for (int i=0; i<this.getPaquetsArbres().size()+1;i++) {this.getPaquetsArbres().get(i).setProdmax(7.5*ratio*1/ratio_optimal);} 
	
	
}
}