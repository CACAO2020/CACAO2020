package abstraction.eq3Transformateur1;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;
import abstraction.eq8Romu.cacaoCriee.ExempleVendeurCacaoCriee;
import abstraction.eq8Romu.cacaoCriee.LotCacaoCriee;
import abstraction.eq8Romu.contratsCadres.Echeancier;
import abstraction.eq8Romu.contratsCadres.ExemplaireContratCadre;
import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.Feve;
import abstraction.fourni.Filiere;
/** @author AMAURY COUDRAY / KARL GUTIERREZ*/
public class Transformateur1 extends VendeurChocolat {

	public Transformateur1() { 
		this.stockFeves.put(Feve.FEVE_BASSE,10.0);
		this.stockFeves.put(Feve.FEVE_MOYENNE,10.0);
		this.stockFeves.put(Feve.FEVE_MOYENNE_EQUITABLE,10.0);
		this.stockFeves.put(Feve.FEVE_HAUTE,10.0);
		this.stockFeves.put(Feve.FEVE_HAUTE_EQUITABLE,10.0);

		this.stockPate.put(Chocolat.CHOCOLAT_BASSE,0.0);
		this.stockPate.put(Chocolat.CHOCOLAT_MOYENNE,0.0);
		this.stockPate.put(Chocolat.CHOCOLAT_MOYENNE_EQUITABLE,0.0);
		this.stockPate.put(Chocolat.CHOCOLAT_HAUTE,0.0);
		this.stockPate.put(Chocolat.CHOCOLAT_HAUTE_EQUITABLE,0.0);

		this.stockChocolat.put(Chocolat.CHOCOLAT_BASSE,5.0);
		this.stockChocolat.put(Chocolat.CHOCOLAT_MOYENNE,5.0);
		this.stockChocolat.put(Chocolat.CHOCOLAT_MOYENNE_EQUITABLE,5.0);
		this.stockChocolat.put(Chocolat.CHOCOLAT_HAUTE,5.0);
		this.stockChocolat.put(Chocolat.CHOCOLAT_HAUTE_EQUITABLE,5.0);

		this.coutFeves.put(Feve.FEVE_BASSE, 10.0);
		this.coutFeves.put(Feve.FEVE_MOYENNE, 10.0);
		this.coutFeves.put(Feve.FEVE_MOYENNE_EQUITABLE, 10.0);
		this.coutFeves.put(Feve.FEVE_HAUTE, 10.0);
		this.coutFeves.put(Feve.FEVE_HAUTE_EQUITABLE, 10.0);

		this.coutPate.put(Chocolat.CHOCOLAT_BASSE,0.0);
		this.coutPate.put(Chocolat.CHOCOLAT_MOYENNE,0.0);
		this.coutPate.put(Chocolat.CHOCOLAT_MOYENNE_EQUITABLE,0.0);
		this.coutPate.put(Chocolat.CHOCOLAT_HAUTE,0.0);
		this.coutPate.put(Chocolat.CHOCOLAT_HAUTE_EQUITABLE,0.0);

		this.coutChocolat.put(Chocolat.CHOCOLAT_BASSE,10010.0);
		this.coutChocolat.put(Chocolat.CHOCOLAT_MOYENNE,10010.0);
		this.coutChocolat.put(Chocolat.CHOCOLAT_MOYENNE_EQUITABLE,10010.0);
		this.coutChocolat.put(Chocolat.CHOCOLAT_HAUTE,10010.0);
		this.coutChocolat.put(Chocolat.CHOCOLAT_HAUTE_EQUITABLE,10010.0);

		this.MontantCompte=500000.0;

		this.stockFevesBasse=new Variable("Feves basse qualité "+getNom(),this,this.stockFeves.get(Feve.FEVE_BASSE));
		this.stockFevesMoyenne=new Variable("Feves moyenne qualité "+getNom(),this,this.stockFeves.get(Feve.FEVE_MOYENNE));
		this.stockFevesMoyenneEquitable=new Variable("Feves moyenne qualité équitable "+getNom(),this,this.stockFeves.get(Feve.FEVE_MOYENNE_EQUITABLE));
		this.stockFevesHaute=new Variable("Feves haute qualité "+getNom(),this,this.stockFeves.get(Feve.FEVE_HAUTE));
		this.stockFevesHauteEquitable=new Variable("Feves haute qualité équitable "+getNom(),this,this.stockFeves.get(Feve.FEVE_HAUTE_EQUITABLE));

		this.stockPateBasse=new Variable("Pate basse qualité "+getNom(),this,this.stockPate.get(Chocolat.CHOCOLAT_BASSE));
		this.stockPateMoyenne=new Variable("Pate moyenne qualité "+getNom(),this,this.stockPate.get(Chocolat.CHOCOLAT_MOYENNE));
		this.stockPateMoyenneEquitable=new Variable("Pate moyenne qualité équitable "+getNom(),this,this.stockPate.get(Chocolat.CHOCOLAT_MOYENNE_EQUITABLE));
		this.stockPateHaute=new Variable("Pate haute qualité "+getNom(),this,this.stockPate.get(Chocolat.CHOCOLAT_HAUTE));
		this.stockPateHauteEquitable=new Variable("Pate haute qualité équitable "+getNom(),this,this.stockPate.get(Chocolat.CHOCOLAT_HAUTE_EQUITABLE));

		this.stockChocolatBasse=new Variable("Chocolat basse qualité "+getNom(),this,this.stockChocolat.get(Chocolat.CHOCOLAT_BASSE));
		this.stockChocolatMoyenne=new Variable("Chocolat moyenne qualité "+getNom(),this,this.stockChocolat.get(Chocolat.CHOCOLAT_MOYENNE));
		this.stockChocolatMoyenneEquitable=new Variable("Chocolat moyenne qualité équitable "+getNom(),this,this.stockChocolat.get(Chocolat.CHOCOLAT_MOYENNE_EQUITABLE));
		this.stockChocolatHaute=new Variable("Chocolat haute qualité "+getNom(),this,this.stockChocolat.get(Chocolat.CHOCOLAT_HAUTE));
		this.stockChocolatHauteEquitable=new Variable("Chocolat haute qualité équitable "+getNom(),this,this.stockChocolat.get(Chocolat.CHOCOLAT_HAUTE_EQUITABLE));

	}
	public Color getColor() {
		return new Color(52, 152, 219);
	}
	public void decisionTransformation() {
		for(Chocolat chocolat:this.getStockPate().keySet()) {
			if(this.getStockPate(chocolat)*2000<Filiere.LA_FILIERE.getBanque().getSolde(this, cryptogramme)){
				this.transformationPateChocolat(chocolat, this.getStockPate(chocolat));
			}
			else if((this.getStockPate(chocolat)*2000)/2<Filiere.LA_FILIERE.getBanque().getSolde(this, cryptogramme)*0.9){
				this.transformationPateChocolat(chocolat, this.getStockPate(chocolat)/2);
			}
		}
		for(Feve feve:this.getStockFeves().keySet()) {
			if(this.getStockFeves(feve)*3500<Filiere.LA_FILIERE.getBanque().getSolde(this, cryptogramme)) {
				this.transformationFevePate(feve, this.getStockFeves(feve));
			}
			else if(this.getStockFeves(feve)*3500/2<Filiere.LA_FILIERE.getBanque().getSolde(this, cryptogramme)*0.9) {
				this.transformationFevePate(feve, this.getStockFeves(feve)/2);
			}
		}
	}
	public void next() {
		this.decisionTransformation();
		this.quantiteFeveAcheteTour.add(0.0);
		this.cATour.add(0.0);
		if(this.finCC>0) {
			this.finCC=this.finCC-1;
		}
		this.descisionCCFeve();
	}
	/** @author KARL GUTIERREZ*/
	public List<Variable> getIndicateurs() {
		List<Variable> res=new ArrayList<Variable>();
		res.add(this.stockFevesBasse);
		res.add(this.stockFevesMoyenne);
		res.add(this.stockFevesMoyenneEquitable);
		res.add(this.stockFevesHaute);
		res.add(this.stockFevesHauteEquitable);

		res.add(this.stockPateBasse);
		res.add(this.stockPateMoyenne);
		res.add(this.stockPateMoyenneEquitable);
		res.add(this.stockPateHaute);
		res.add(this.stockPateHauteEquitable);

		res.add(this.stockChocolatBasse);
		res.add(this.stockChocolatMoyenne);
		res.add(this.stockChocolatMoyenneEquitable);
		res.add(this.stockChocolatHaute);
		res.add(this.stockChocolatHauteEquitable);

		return res;
	}




}
