package abstraction.eq3Transformateur1;

import java.util.HashMap;

import java.util.Map;

import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.Feve;
import abstraction.eq8Romu.produits.Gamme;
import abstraction.fourni.Variable;

/** @author AMAURY COUDRAY*/
public abstract class Stock extends ActeurEQ3 {


	/* quantite de chaque type de FEVE/CHocolat/PATE */
	protected Map<Feve,Double> stockFeves;
	protected Map<Chocolat,Double> stockChocolat;
	protected Map<Chocolat,Double> stockPateInterne;
	/* cout pour chaque unité de chaque type de FEVE/CHocolat */
	protected Map<Feve,Double> coutFeves;
	protected Map<Chocolat,Double> coutChocolat;
	protected Map<Chocolat,Double> coutPateInterne;
	
	protected Variable stockTotalFeves;
	protected Variable stockTotalChocolat;
	protected Variable stockTotalPateInterne;
	
	public Stock() {
		this.stockFeves=new HashMap<Feve,Double>();
		this.stockChocolat=new HashMap<Chocolat,Double>();
		this.stockPateInterne=new HashMap<Chocolat,Double>();
		this.coutChocolat=new HashMap<Chocolat,Double>();;
		this.coutPateInterne=new HashMap<Chocolat,Double>();;
		this.coutFeves=new HashMap<Feve,Double>();
		/*
		 * FAKE VALEUR pour le moment
		 */
		this.stockFeves.put(Feve.FEVE_MOYENNE,15.0);
		this.stockChocolat.put(Chocolat.CHOCOLAT_MOYENNE,15.0);
		this.coutFeves.put(Feve.FEVE_MOYENNE, 10.0);
		this.coutChocolat.put(Chocolat.CHOCOLAT_MOYENNE,2002.0);
		this.stockTotalFeves=new Variable("stock total de feves de "+getNom(),this,15.0);
		this.stockTotalChocolat=new Variable("stock total de chocolat de "+getNom(),this,15.0);
		this.stockTotalPateInterne=new Variable("stock total de pate interne de "+getNom(),this,0.0);

	}
	
	/* gestion FEVE */
	
	public void setStockFeves(Feve feve,Double quantite) {
		if(quantite>=0) {
			if(this.stockFeves.containsKey(feve)) {
				this.stockFeves.put(feve,quantite+this.stockFeves.get(feve));
			}
			else{
				this.stockFeves.put(feve,quantite);
			}
			this.stockTotalFeves.setValeur(this, quantite);
		}
		else if((this.stockFeves.containsKey(feve))&&(quantite+this.stockFeves.get(feve)>=0)) {
			this.stockFeves.put(feve,quantite+this.stockFeves.get(feve));
			this.stockTotalFeves.setValeur(this, quantite);
		}
	}
	public Map<Feve,Double> getStockFeves() {
		return this.stockFeves;
	}
	public Double getStockFeves(Feve feve) {
		return this.stockFeves.get(feve);
	}
	
	/* gestion CHOCOLAT */
	
	public void setStockChocolat(Chocolat chocolat,Double quantite) {
		if(quantite>=0) {
			if(this.stockChocolat.containsKey(chocolat)) {
				this.stockChocolat.put(chocolat,quantite+this.stockChocolat.get(chocolat));
			}
			else {
				this.stockChocolat.put(chocolat,quantite);
			}
			this.stockTotalChocolat.setValeur(this, quantite);
		}
		else if((this.stockChocolat.containsKey(chocolat))&&(quantite+this.stockChocolat.get(chocolat)>=0)) {
			this.stockChocolat.put(chocolat,quantite+this.stockChocolat.get(chocolat));
			this.stockTotalChocolat.setValeur(this, quantite);
		}
	}
	public Map<Chocolat,Double> getStockChocolat() {
		return this.stockChocolat;
	}
	public Double getStockChocolat(Chocolat chocolat) {
		return this.stockChocolat.get(chocolat);
	}
	
	/* gestion PATE INERNE */
	
	public void setStockPateInterne(Chocolat chocolat,Double quantite) {
		
		if(quantite>=0) {
			if(this.stockPateInterne.containsKey(chocolat)) {
				this.stockPateInterne.put(chocolat,quantite+this.stockPateInterne.get(chocolat));
			}
			else {
				this.stockPateInterne.put(chocolat,quantite);
			}
			this.stockTotalPateInterne.setValeur(this, quantite);
		}
		else if((this.stockPateInterne.containsKey(chocolat))&&(quantite+this.stockPateInterne.get(chocolat)>=0)) {
			this.stockPateInterne.put(chocolat,quantite+this.stockPateInterne.get(chocolat));
			this.stockTotalPateInterne.setValeur(this, quantite);
		}
	}
	public Map<Chocolat,Double> getStockPateInterne(){
		return this.stockPateInterne;
	}
	public Double getStockPateInterne(Chocolat chocolat){
		return this.stockPateInterne.get(chocolat);
	}	
	
	/* getters COUT */
	
	public void setCoutFeves(Feve feve,Double cout) {
		this.coutFeves.put(feve,cout);
	}
	public void setCoutChocolat(Chocolat chocolat,Double cout) {
		this.coutChocolat.put(chocolat,cout);
	}
	public void setCoutPateInterne(Chocolat chocolat,Double cout) {
		this.coutPateInterne.put(chocolat,cout);
	}
	public Map<Feve,Double> getCoutFeves() {
		return this.coutFeves;
	}
	public Double getCoutFeves(Feve feve) {
		return this.coutFeves.get(feve);
	}
	public Map<Chocolat,Double> getCoutChocolat() {
		return this.coutChocolat;
	}
	public Double getCoutChocolat(Chocolat chocolat) {
		return this.coutChocolat.get(chocolat);
	}
	public Map<Chocolat, Double> getCoutPateInterne() {
		return coutPateInterne;
	}
	public Double getCoutPateInterne(Chocolat chocolat) {
		return coutPateInterne.get(chocolat);
	}
	/*calcul COUT */
	
	/*methode a appeler AVANT d'ajouter la quantite de feve au stock*/ 
	
	public Double calculCoutFeve(Feve feve, Double quantiteAjoute, Double prixQuantite ) {
		if(this.coutFeves.containsKey(feve)) {
			return (prixQuantite+this.coutFeves.get(feve)*this.stockFeves.get(feve))/(quantiteAjoute+this.stockFeves.get(feve));
		}
		return prixQuantite/quantiteAjoute;
	}
	public Double calculCoutChocolat(Chocolat chocolat,Double quantiteAjoute, Double prixQuantite) {
		if(this.coutChocolat.containsKey(chocolat)) {
			return (prixQuantite+this.coutChocolat.get(chocolat)*this.stockChocolat.get(chocolat))/(quantiteAjoute+this.stockChocolat.get(chocolat));
		}
		else {
			return prixQuantite/quantiteAjoute;
		}
	}
	public Double calculCoutPateInterne(Chocolat chocolat,Double quantiteAjoute, Double prixQuantite) {
		if(this.coutPateInterne.containsKey(chocolat)) {
			return (prixQuantite+this.coutPateInterne.get(chocolat)*this.stockPateInterne.get(chocolat))/(quantiteAjoute+this.stockPateInterne.get(chocolat));
		}
		else {
			return prixQuantite/quantiteAjoute;
		}
	}
	
	/* gestion TRANSFORMATION */
	
	public Chocolat equivalentChocoFeve(Feve feve) {
		if(feve.isEquitable()) {
			if(feve.getGamme()==Gamme.HAUTE) {
				return Chocolat.CHOCOLAT_HAUTE_EQUITABLE;
			}
			return Chocolat.CHOCOLAT_MOYENNE_EQUITABLE;
		}
		if(feve.getGamme()==Gamme.HAUTE) {
			return Chocolat.CHOCOLAT_HAUTE;
		}
		else if (feve.getGamme()==Gamme.MOYENNE) {
			return Chocolat.CHOCOLAT_MOYENNE;
		}
		return Chocolat.CHOCOLAT_BASSE;
	}
	
	public  void transformationFevePate(Feve feve, Double quantite) {
		if((quantite>=0)&&(this.getStockFeves().containsKey(feve))&&(this.getStockFeves(feve)>=quantite)) {
				this.setStockFeves(feve, -quantite);
				this.setStockPateInterne(this.equivalentChocoFeve(feve), quantite);
		}
	}
	public  void transformationPateChocolat(Chocolat chocolat, Double quantite) {
		if((quantite>=0)&&(this.getStockPateInterne().containsKey(chocolat))&&(this.getStockPateInterne(chocolat)>=quantite)) {
				this.setStockPateInterne(chocolat, -quantite);
				this.setStockChocolat(chocolat,quantite);
		}
	}
}
