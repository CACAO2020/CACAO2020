package abstraction.eq3Transformateur1;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;
import abstraction.eq8Romu.produits.Chocolat;
import abstraction.fourni.Filiere;

public class Transformateur1 extends VendeurChocolat {
	
	public Color getColor() {
		return new Color(52, 152, 219);
	}
	public void next() {

	}
	public List<Variable> getIndicateurs() {
		List<Variable> res=new ArrayList<Variable>();
		res.add(this.stockTotalFeves);
		res.add(this.stockTotalChocolat);
		return res;
	}
	

}
