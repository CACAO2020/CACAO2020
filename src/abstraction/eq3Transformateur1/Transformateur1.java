package abstraction.eq3Transformateur1;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;
import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.Feve;
import abstraction.fourni.Filiere;
/** @author AMAURY COUDRAY*/
public class Transformateur1 extends VendeurChocolat {
	
	public Color getColor() {
		return new Color(52, 152, 219);
	}
	public void next() {
		for(Chocolat chocolat:this.getStockPateInterne().keySet()) {
			this.transformationPateChocolat(chocolat, this.getStockPateInterne(chocolat));
		}
		for(Feve feve:this.getStockFeves().keySet()) {
			this.transformationFevePate(feve, this.getStockFeves(feve));
		}
		
	}
	public List<Variable> getIndicateurs() {
		List<Variable> res=new ArrayList<Variable>();
		res.add(this.stockTotalFeves);
		res.add(this.stockTotalChocolat);
		res.add(this.stockTotalPateInterne);
		return res;
	}
	

}
