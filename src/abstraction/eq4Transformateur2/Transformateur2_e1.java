package abstraction.eq4Transformateur2;

import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.Feve;
import abstraction.eq8Romu.produits.Gamme;
import abstraction.eq8Romu.produits.Pate;
import abstraction.fourni.Filiere;

public class Transformateur2_e1 extends Transformateur2 implements IActeur {
	
	//variables
	private Variable capaciteMaxTFEP ; //donnent la limite de la quantité transformable (comprend infrastructures et employés)
	private Variable capaciteMaxTPEC ;
	
	//paramètres
	private Map<Gamme, Variable> coutsUnitairesTFEP ; // coûts de transformation par unité
	private Map<Gamme, Variable> coutsUnitairesTPEC ; 
	private Variable coeffTFEP ; //équivalent en pâte d'une unité de fèves
	private Variable coeffTPEC ; //équivalent en chocolat d'une unité de chocolat
	
	public Transformateur2_e1() {
		
		super () ; 
		
		this.capaciteMaxTFEP = new Variable(getNom()+" limite transformation feve en pate", this, 100) ;
		this.capaciteMaxTPEC = new Variable(getNom()+" limite transformation pate en chocolat", this, 200) ;
		this.coeffTFEP = new Variable(getNom()+" équivalent en pâte d'une unité de fèves", this, 1) ;
		this.coeffTPEC = new Variable(getNom()+" équivalent en chocolat d'une unité de chocolat", this, 1) ;
				
		this.coutsUnitairesTFEP = new HashMap<Gamme, Variable>() ;
		this.coutsUnitairesTFEP.put(Gamme.BASSE, new Variable(getNom()+" cout unitaire de transformation de basse qualité fèves vers pâte)", this, 1)) ;
		this.coutsUnitairesTFEP.put(Gamme.MOYENNE, new Variable(getNom()+" cout unitaire de transformation de moyenne qualité fèves vers pâte)", this, 1)) ;
		this.coutsUnitairesTFEP.put(Gamme.HAUTE, new Variable(getNom()+" cout unitaire de transformation de haute qualité fèves vers pâte)", this, 1)) ;
		
		this.coutsUnitairesTPEC = new HashMap<Gamme, Variable>() ;
		this.coutsUnitairesTPEC.put(Gamme.BASSE, new Variable(getNom()+" cout unitaire de transformation de basse qualité pâte vers chocolat)", this, 1)) ;
		this.coutsUnitairesTPEC.put(Gamme.MOYENNE, new Variable(getNom()+" cout unitaire de transformation de moyenne qualité pâte vers chocolat)", this, 1)) ;
		this.coutsUnitairesTPEC.put(Gamme.HAUTE, new Variable(getNom()+" cout unitaire de transformation de haute qualité pâte vers chocolat)", this, 1)) ;
	}
	
	public double getCoutUnitaireTFEP(Gamme gamme) {
		return this.coutsUnitairesTFEP.get(gamme).getValeur() ;
	}
	
	public double getCoutUnitaireTPEC(Gamme gamme) {
		return this.coutsUnitairesTPEC.get(gamme).getValeur();
	}
	
	public double getCoeffTFEP() {
		return this.coeffTFEP.getValeur();
	}

	public double getCoeffTPEC() {
		return this.coeffTPEC.getValeur();
	}
	
	public double getCapaciteMaxTFEP() {
		return this.capaciteMaxTFEP.getValeur();
	}
	
	public double getCapaciteMaxTPEC() {
		return this.capaciteMaxTPEC.getValeur();
	}
	
	public List<Variable> getIndicateurs() { //Le prof veut-il plus d'indicateurs que les stocks ?
		List<Variable> res=super.getIndicateurs();
		res.add(this.capaciteMaxTFEP) ;
		res.add(this.capaciteMaxTPEC) ;
		return res;
	}

	public List<Variable> getParametres() { //idem
		List<Variable> res=super.getParametres();
		for (Gamme gamme : Gamme.values()) {
			res.add(this.coutsUnitairesTFEP.get(gamme)) ;
		}
		for (Gamme gamme : Gamme.values()) {
			res.add(this.coutsUnitairesTPEC.get(gamme)) ;
		}
		return res;
	}
	
	public PateInterne creerPate (Feve feve) {
		switch (feve.getGamme()) {
		case BASSE : return PateInterne.PATE_BASSE ; 
		case MOYENNE :
			if (feve.isEquitable()) {
				return PateInterne.PATE_MOYENNE_EQUITABLE ;
			} else {
				return PateInterne.PATE_MOYENNE ;
			}
		case HAUTE : 
			if (feve.isEquitable()) {
				return PateInterne.PATE_HAUTE_EQUITABLE ;
			} else {
				return PateInterne.PATE_HAUTE ;
			}
		default : throw new IllegalArgumentException("valeur non trouvée") ;
		}
	}

	public Chocolat creerChocolat (PateInterne pate) {
		switch (pate.getGamme()) {
		case BASSE : return Chocolat.CHOCOLAT_BASSE ; 
		case MOYENNE :
			if (pate.isEquitable()) {
				return Chocolat.CHOCOLAT_MOYENNE_EQUITABLE ;
			} else {
				return Chocolat.CHOCOLAT_MOYENNE ;
			}
		case HAUTE : 
			if (pate.isEquitable()) {
				return Chocolat.CHOCOLAT_HAUTE_EQUITABLE ;
			} else {
				return Chocolat.CHOCOLAT_HAUTE ;
			}
		default : throw new IllegalArgumentException("valeur non trouvée") ;
		}
	}
	
	public double coutTFEP (Feve feve) {
		return this.getCoutUnitaireTFEP(feve.getGamme()) * this.capaciteMaxTPEC.getValeur() ;
	}
	
	public double coutTPEC (Pate pate, double quantitePate) {
		return this.getCoutUnitaireTPEC(pate.getGamme())  * this.capaciteMaxTPEC.getValeur() ;
	}
	
	public boolean capaciteInsuffisanteTFEP(double quantiteFeve) {
		return quantiteFeve > this.capaciteMaxTFEP.getValeur() ;
	}
	
	public boolean capaciteInsuffisanteTPEC(double quantitePate) {
		return quantitePate > this.capaciteMaxTPEC.getValeur() ;
	}
	
	public void transformationFeveEnPate (double quantiteFeve, Feve feve) { 
			double nouveauStockFeve = super.getStockFevesValeur(feve) - quantiteFeve ;
			if (nouveauStockFeve >= 0) {
				PateInterne pate = this.creerPate(feve) ;
				double nouveauStockPate = super.getStockPateValeur(pate) + this.getCoeffTFEP()*quantiteFeve ;
				super.setStockFevesValeur(feve,nouveauStockFeve) ;
				super.setStockPateValeur(pate,nouveauStockPate) ;
			} 
			else {throw new IllegalArgumentException("stock negatif") ;} 
		}
	
	public void transformationPateEnChocolat (double quantitePate, PateInterne pate) {
		double nouveauStockPate = super.getStockPateValeur(pate) - quantitePate ;
		if (nouveauStockPate >= 0) {
			Chocolat chocolat = this.creerChocolat(pate) ;
			double nouveauStockChocolat = super.getStockChocolatValeur(chocolat) + this.getCoeffTPEC()*quantitePate ;
			super.setStockPateValeur(pate,nouveauStockPate) ;
			super.setStockChocolatValeur(chocolat,nouveauStockChocolat) ;
		}
		else {throw new IllegalArgumentException("stock negatif") ;} 
	}
	
	public boolean transformationFeves (Map<Feve,Double> quantitesFeve) { //argument : dictionnaire donnant la quantité à transformer pour chaque type de fève
		double quantiteFeveTotale = 0 ;
		for (Feve feve : quantitesFeve.keySet()) {
			if (this.capaciteInsuffisanteTFEP(quantiteFeveTotale + quantitesFeve.get(feve))) {
				double quantiteFeve = this.getCapaciteMaxTFEP() - quantiteFeveTotale ;
				this.transformationFeveEnPate (quantiteFeve, feve) ;
				quantiteFeveTotale = quantiteFeveTotale + quantitesFeve.get(feve) ;
				return false ;
			}
			else {
				quantiteFeveTotale = quantiteFeveTotale + quantitesFeve.get(feve) ;
				this.transformationFeveEnPate (quantitesFeve.get(feve), feve) ; }
		}
		return true ; // return false si saturation de la capacité de production
	}
	
	public boolean transformationPate (Map<PateInterne,Double> quantitesPate) { //argument : dictionnaire donnant la quantité à transformer pour chaque type de pate
		double quantitePateTotale = 0 ;
		for (PateInterne pate : quantitesPate.keySet()) {
			if (this.capaciteInsuffisanteTPEC(quantitePateTotale + quantitesPate.get(pate))) {
				double quantitePate = this.getCapaciteMaxTPEC() - quantitePateTotale ;
				this.transformationPateEnChocolat (quantitePate, pate) ;
				quantitePateTotale = quantitePateTotale + quantitesPate.get(pate) ;
				return false ;
			}
			else {
				quantitePateTotale = quantitePateTotale + quantitesPate.get(pate) ;
				this.transformationPateEnChocolat (quantitesPate.get(pate), pate) ; }
		}
		return true ; // return false si saturation de la capacité de production
	}
}