

package abstraction.eq2Producteur2;
import abstraction.eq8Romu.produits.Feve;
import abstraction.eq8Romu.produits.Pate;
import abstraction.eq8Romu.produits.Gamme;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;


public class eq2Stock extends eq2Acteur{
/**
 * @author lucas et lucas
 * cette classe gère les stocks de fèves et de pâte, les masses sont exprimées en tonnes
 */
	
	private Variable coutStockage;
	private HashMap<Feve,Variable> Stock;

	
	/**
	 * @param masse_feves_trinitario
	 * @param masse_feves_forastero
	 * @param masse_feves_criollo
	 * @param qt_pate_bassegamme
	 * @param qt_pate_hautegamme
	 */
	public eq2Stock() {
		super();
		this.coutStockage = new Variable ("cout",this,100);
		this.Stock = new HashMap<Feve,Variable>();
	}
	
public void addStock(Feve feve, double quantité) {
	String type = ""+feve;
	this.Stock.put(feve,new Variable(type,this,quantité));
}
	
 public HashMap<Feve,Variable> getStock() {
	 return this.Stock;
	 
 }

public void setCoutStockage(double cout) {
	this.coutStockage.setValeur(this, cout);
}
public void setQtFeve(Feve feve, double quantite) {
	this.getStock().get(feve).setValeur(this, quantite);

}
public void addQtFeve(Feve feve, double quantite) {
	this.getStock().get(feve).ajouter(this, quantite);

}
public void removeQtFeve(Feve feve, double quantite) {
	this.getStock().get(feve).retirer(this, quantite);

}
public List<Variable> getVariables(){
	 List<Variable >variables = new ArrayList<Variable>();
	 for (Feve feve : this.getStock().keySet()) {
		 variables.add(this.getStock().get(feve));
	 }
	return variables;
}
}