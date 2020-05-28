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
	public Transformateur1() {
		this.stockChocolat.put(Chocolat.CHOCOLAT_BASSE,20.0);
		this.stockFeves.put(Feve.FEVE_HAUTE_EQUITABLE,15.0);
		this.coutChocolat.put(Chocolat.CHOCOLAT_BASSE,10000.0);
		this.coutFeves.put(Feve.FEVE_HAUTE_EQUITABLE, 1.0);
		this.MontantCompte=500000.0;
		this.stockTotalFeves=new Variable("stock total de feves de "+getNom(),this,15.0);
		this.stockTotalChocolat=new Variable("stock total de chocolat de "+getNom(),this,20.0);
		this.stockTotalPateInterne=new Variable("stock total de pate interne de "+getNom(),this,0.0);

	}
	public Color getColor() {
		return new Color(52, 152, 219);
	}
	public void decisionTransformation() {
		for(Chocolat chocolat:this.getStockPateInterne().keySet()) {
			if(this.getStockPateInterne(chocolat)*4000<=this.getMontantCompte()){
				this.transformationPateChocolat(chocolat, this.getStockPateInterne(chocolat));
			}
			else {
				this.transformationPateChocolat(chocolat, this.getMontantCompte()/4000);
			}
		}
		for(Feve feve:this.getStockFeves().keySet()) {
			if(this.getStockFeves(feve)*8000<=this.getMontantCompte()) {
				this.transformationFevePate(feve, this.getStockFeves(feve));
			}
			else {
				this.transformationFevePate(feve, this.getMontantCompte()/8000);
			}
		}
	}
	public void next() {
		this.decisionTransformation();
		System.out.println("cout des feves"+this.getCoutFeves());
		System.out.println("cout de la pate interne"+this.getCoutPateInterne());
		System.out.println("cout des chocolats"+this.getCoutChocolat());
		System.out.println("stock chocolat"+this.getStockChocolat());
		System.out.println("tresorerie "+this.getMontantCompte());
	}
	
	public List<Variable> getIndicateurs() {
		List<Variable> res=new ArrayList<Variable>();
		res.add(this.stockTotalFeves);
		res.add(this.stockTotalChocolat);
		res.add(this.stockTotalPateInterne);
		return res;
	}
	

}
