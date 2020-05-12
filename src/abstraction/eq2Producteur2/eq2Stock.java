

package abstraction.eq2Producteur2;
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
	private List<Variable> Stock;

	
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
	}
	
public void addStock(String type, double quantité) {
	
	this.Stock.add(new Variable(type,this,quantité));
}
	
 public List<Variable> getStock() {
	 return this.Stock;
	 
 }

public void setCoutStockage(double cout) {
	this.coutStockage.setValeur(this, cout);
}
public void setQtFeve(String type, double quantite) {
	for (int i=0;i<this.Stock.size()+1;i++) {
		if (this.Stock.get(i).getNom().equals(type)) {
			this.Stock.get(i).setValeur(this,quantite);
		}
	}
}
public void addQtFeve(String type, double quantite) {
	for (int i=0;i<this.Stock.size()+1;i++) {
		if (this.Stock.get(i).getNom().equals(type)) {
			this.Stock.get(i).ajouter(this,quantite);
		}
	}
}
public void removeQtFeve(String type, double quantite) {
	for (int i=0;i<this.Stock.size()+1;i++) {
		if (this.Stock.get(i).getNom().equals(type)) {
			this.Stock.get(i).retirer(this,quantite);
		}
	}
}
}