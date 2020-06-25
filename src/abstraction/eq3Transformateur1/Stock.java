package abstraction.eq3Transformateur1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.Feve;
import abstraction.eq8Romu.produits.Gamme;
import abstraction.fourni.Filiere;
import abstraction.fourni.Variable;

/** @author AMAURY COUDRAY / KARL GUTIERREZ*/
public abstract class  Stock extends Tresorerie { 


	/* quantite de chaque type de FEVE/CHocolat/PATE */ 
	protected Map<Feve,Double> stockFeves;
	protected Map<Chocolat,Double> stockChocolat; 
	protected Map<Chocolat,Double> stockPate;
	/* cout pour chaque unité de chaque type de FEVE/CHocolat */
	protected Map<Feve,Double> coutFeves;
	protected Map<Chocolat,Double> coutChocolat;
	protected Map<Chocolat,Double> coutPate;

	protected Variable stockFevesBasse;
	protected Variable stockFevesMoyenne;
	protected Variable stockFevesMoyenneEquitable;
	protected Variable stockFevesHaute;
	protected Variable stockFevesHauteEquitable;

	protected Variable stockPateBasse;
	protected Variable stockPateMoyenne;
	protected Variable stockPateMoyenneEquitable;
	protected Variable stockPateHaute;
	protected Variable stockPateHauteEquitable;

	protected Variable stockChocolatBasse;
	protected Variable stockChocolatMoyenne;
	protected Variable stockChocolatMoyenneEquitable;
	protected Variable stockChocolatHaute;
	protected Variable stockChocolatHauteEquitable;

	protected Variable quantiteFeveAchete;
	protected List<Double> quantiteFeveAcheteTour;
	public Stock() {
		this.stockFeves=new HashMap<Feve,Double>();
		this.stockChocolat=new HashMap<Chocolat,Double>();
		this.stockPate=new HashMap<Chocolat,Double>();
		this.coutChocolat=new HashMap<Chocolat,Double>();;
		this.coutPate=new HashMap<Chocolat,Double>();;
		this.coutFeves=new HashMap<Feve,Double>();
		this.quantiteFeveAchete= new Variable("Quantité de fèves achetées en moyenne ", this);
		this.quantiteFeveAchete.setValeur(this,0);
		this.quantiteFeveAcheteTour=new ArrayList<Double>();
		/*
		 * FAKE VALEUR pour le moment
		 * this.stockChocolat.put(Chocolat.CHOCOLAT_MOYENNE,15.0);
		 * 	this.coutChocolat.put(Chocolat.CHOCOLAT_MOYENNE,2002.0);
		 */

	}

	/* gestion FEVE */

	public void setStockFeves(Feve feve,Double quantite) {
		if(quantite+this.stockFeves.get(feve)>=0) {
			if(this.stockFeves.containsKey(feve)) {
				this.stockFeves.put(feve,quantite+this.stockFeves.get(feve));
			}
			else{
				this.stockFeves.put(feve,quantite);
			}
			switch(feve.getGamme()) {
			case BASSE:
				this.stockFevesBasse.setValeur(this,this.stockFevesBasse.getValeur()+ quantite);
				break;
			case MOYENNE:
				if (!feve.isEquitable()) {
					this.stockFevesMoyenne.setValeur(this,this.stockFevesMoyenne.getValeur()+ quantite);
				}
				else {
					this.stockFevesMoyenneEquitable.setValeur(this,this.stockFevesMoyenneEquitable.getValeur()+ quantite);
				}
				break;
			case HAUTE:
				if (!feve.isEquitable()) {
					this.stockFevesHaute.setValeur(this,this.stockFevesHaute.getValeur()+ quantite);
				}
				else {
					this.stockFevesHauteEquitable.setValeur(this,this.stockFevesHauteEquitable.getValeur()+ quantite);
				}
				break;
			}
		}
		if(quantite>0) {
			quantiteFeveAchete.setValeur(this, (quantiteFeveAchete.getValeur()+quantite));
			quantiteFeveAcheteTour.set(Filiere.LA_FILIERE.getEtape(), 
					quantiteFeveAcheteTour.get(Filiere.LA_FILIERE.getEtape())+quantite);
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
		if(quantite+this.stockChocolat.get(chocolat)>=0) {
			if(this.stockChocolat.containsKey(chocolat)) {
				this.stockChocolat.put(chocolat,quantite+this.stockChocolat.get(chocolat));
			}
			else {
				this.stockChocolat.put(chocolat,quantite);
			}
			switch(chocolat.getGamme()) {
			case BASSE:
				this.stockChocolatBasse.setValeur(this,this.stockChocolatBasse.getValeur()+ quantite);
				break;
			case MOYENNE:
				if (!chocolat.isEquitable()) {
					this.stockChocolatMoyenne.setValeur(this,this.stockChocolatMoyenne.getValeur()+ quantite);
				}
				else {
					this.stockChocolatMoyenneEquitable.setValeur(this,this.stockChocolatMoyenneEquitable.getValeur()+ quantite);
				}
				break;
			case HAUTE:
				if (!chocolat.isEquitable()) {
					this.stockChocolatHaute.setValeur(this,this.stockChocolatHaute.getValeur()+ quantite);
				}
				else {
					this.stockChocolatHauteEquitable.setValeur(this,this.stockChocolatHauteEquitable.getValeur()+ quantite);
				}
				break;
			}
		}
	}
	public Map<Chocolat,Double> getStockChocolat() {
		return this.stockChocolat;
	}
	public Double getStockChocolat(Chocolat chocolat) {
		return this.stockChocolat.get(chocolat);
	}

	/* gestion PATE INERNE */

	public void setStockPate(Chocolat chocolat,Double quantite) {

		if(quantite+this.stockPate.get(chocolat)>=0) {
			if(this.stockPate.containsKey(chocolat)) {
				this.stockPate.put(chocolat,quantite+this.stockPate.get(chocolat));
			}
			else {
				this.stockPate.put(chocolat,quantite);
			}
			switch(chocolat.getGamme()) {
			case BASSE:
				this.stockPateBasse.setValeur(this,this.stockPateBasse.getValeur()+ quantite);
				break;
			case MOYENNE:
				if (!chocolat.isEquitable()) {
					this.stockPateMoyenne.setValeur(this,this.stockPateMoyenne.getValeur()+ quantite);
				}
				else {
					this.stockPateMoyenneEquitable.setValeur(this,this.stockPateMoyenneEquitable.getValeur()+ quantite);
				}
				break;
			case HAUTE:
				if (!chocolat.isEquitable()) {
					this.stockPateHaute.setValeur(this,this.stockPateHaute.getValeur()+ quantite);
				}
				else {
					this.stockPateHauteEquitable.setValeur(this,this.stockPateHauteEquitable.getValeur()+ quantite);
				}
				break;
			}
		}
	}
	public Map<Chocolat,Double> getStockPate(){
		return this.stockPate;
	}
	public Double getStockPate(Chocolat chocolat){
		return this.stockPate.get(chocolat);
	}	

	/* getters COUT */

	public void setCoutFeves(Feve feve,Double cout) {
		this.coutFeves.put(feve,cout);
	}
	public void setCoutChocolat(Chocolat chocolat,Double cout) {
		this.coutChocolat.put(chocolat,cout);
	}
	public void setCoutPate(Chocolat chocolat,Double cout) {
		this.coutPate.put(chocolat,cout);
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
	public Map<Chocolat, Double> getCoutPate() {
		return coutPate;
	}
	public Double getCoutPate(Chocolat chocolat) {
		return coutPate.get(chocolat);
	}
	/*calcul COUT */

	/*methode a appeler AVANT d'ajouter la quantite de feve au stock*/ 

	public Double calculCoutFeve(Feve feve, Double quantiteAjoute, Double prixQuantite ) {
		if(this.coutFeves.containsKey(feve)) {
			return (prixQuantite*quantiteAjoute+this.coutFeves.get(feve)*this.stockFeves.get(feve))/(quantiteAjoute+this.stockFeves.get(feve));
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
	public Double calculCoutPate(Chocolat chocolat,Double quantiteAjoute, Double prixQuantite) {
		if(this.coutPate.containsKey(chocolat)) {
			return (prixQuantite+this.coutPate.get(chocolat)*this.stockPate.get(chocolat))/(quantiteAjoute+this.stockPate.get(chocolat));
		}
		else {
			return prixQuantite/quantiteAjoute;
		}
	}



}
