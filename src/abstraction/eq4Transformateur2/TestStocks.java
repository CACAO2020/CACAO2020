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

public class TestStocks {
	
	public static void main(String[] args) {
		Transformateur2_stocks_et_transfos t = new Transformateur2_stocks_et_transfos () ;
		/*List<Variable> listeIndicateurs = t.getIndicateurs() ;
		for (Variable v : listeIndicateurs) {
			System.out.println(v.getNom());
			System.out.println(v.getValeur());
		}
		*/List<Variable> listeParametres = t.getParametres() ;
		for (Variable v : listeParametres) {
			System.out.println(v.getNom());
			System.out.println(v.getValeur());
		}/*
		t.transformationFeveEnPate (10, Feve.FEVE_BASSE) ;
		System.out.println(t.getStockFevesValeur(Feve.FEVE_BASSE)) ;
		t.transformationFeveEnPate (100, Feve.FEVE_BASSE) ;
		System.out.println(t.getStockFevesValeur(Feve.FEVE_BASSE)) ;
		 renvoie bien l'erreur stock négatif
		t.transformationFeveEnPate (40, Feve.FEVE_BASSE) ;
		System.out.println(t.getStockFevesValeur(Feve.FEVE_BASSE)) ;
		t.transformationFeveEnPate (50, Feve.FEVE_MOYENNE) ;
		System.out.println(t.getStockFevesValeur(Feve.FEVE_MOYENNE)) ;
		t.transformationFeveEnPate (20, Feve.FEVE_MOYENNE_EQUITABLE) ;
		System.out.println(t.getStockFevesValeur(Feve.FEVE_MOYENNE_EQUITABLE)) ;
		 renvoie 40, 0, 0, 30 : marche bien
		 */
		
		System.out.println(t.getCoutMoyenFeveValeur(Feve.FEVE_MOYENNE));
		System.out.println(t.getCoutMoyenPateValeur(PateInterne.PATE_MOYENNE));
		System.out.println(t.getCoutMoyenChocolatValeur(Chocolat.CHOCOLAT_MOYENNE));
		
		Map<Feve,Double> quantitesF = new HashMap<Feve,Double>() ;
		quantitesF.put(Feve.FEVE_BASSE,50.0) ;
		quantitesF.put(Feve.FEVE_MOYENNE,50.0) ;
		quantitesF.put(Feve.FEVE_MOYENNE_EQUITABLE,50.0) ;
		quantitesF.put(Feve.FEVE_HAUTE,50.0) ;
		quantitesF.put(Feve.FEVE_HAUTE_EQUITABLE,50.0) ;
		t.transformationFeves (quantitesF) ;
		
		/*List<Variable> listeIndicateurs = t.getIndicateurs() ;
		for (Variable v : listeIndicateurs) {
			System.out.println(v.getNom());
			System.out.println(v.getValeur());
		}*/
		
		Map<PateInterne,Double> quantitesP = new HashMap<PateInterne,Double>() ;
		quantitesP.put(PateInterne.PATE_BASSE,50.0) ;
		quantitesP.put(PateInterne.PATE_MOYENNE,50.0) ;
		quantitesP.put(PateInterne.PATE_MOYENNE_EQUITABLE,50.0) ;
		quantitesP.put(PateInterne.PATE_HAUTE,50.0) ;
		quantitesP.put(PateInterne.PATE_HAUTE_EQUITABLE,50.0) ;
		t.transformationPate (quantitesP) ;
		
		/*for (Variable v : listeIndicateurs) {
			System.out.println(v.getNom());
			System.out.println(v.getValeur());
		}*/
		
		/*System.out.println(t.getStockFevesValeur(Feve.FEVE_MOYENNE));
		System.out.println(t.getStockPateValeur(PateInterne.PATE_MOYENNE));
		System.out.println(t.getStockChocolatValeur(Chocolat.CHOCOLAT_MOYENNE));
		System.out.println(t.getCoutMoyenFeveValeur(Feve.FEVE_MOYENNE));
		System.out.println(t.getCoutMoyenPateValeur(PateInterne.PATE_MOYENNE));
		System.out.println(t.getCoutMoyenChocolatValeur(Chocolat.CHOCOLAT_MOYENNE));*/
		//fonctionne pour toutes les denrées
		
		System.out.println(t.getStockFevesValeur(Feve.FEVE_HAUTE_EQUITABLE));
		System.out.println(t.getStockPateValeur(PateInterne.PATE_HAUTE_EQUITABLE));
		System.out.println(t.getStockChocolatValeur(Chocolat.CHOCOLAT_HAUTE_EQUITABLE));
		System.out.println(t.getCoutMoyenFeveValeur(Feve.FEVE_HAUTE_EQUITABLE));
		System.out.println(t.getCoutMoyenPateValeur(PateInterne.PATE_HAUTE_EQUITABLE));
		System.out.println(t.getCoutMoyenChocolatValeur(Chocolat.CHOCOLAT_HAUTE_EQUITABLE));
		
		t.transformationPate (quantitesP) ;
		
		System.out.println(t.getStockFevesValeur(Feve.FEVE_HAUTE_EQUITABLE));
		System.out.println(t.getStockPateValeur(PateInterne.PATE_HAUTE_EQUITABLE));
		System.out.println(t.getStockChocolatValeur(Chocolat.CHOCOLAT_HAUTE_EQUITABLE));
		System.out.println(t.getCoutMoyenFeveValeur(Feve.FEVE_HAUTE_EQUITABLE));
		System.out.println(t.getCoutMoyenPateValeur(PateInterne.PATE_HAUTE_EQUITABLE));
		System.out.println(t.getCoutMoyenChocolatValeur(Chocolat.CHOCOLAT_HAUTE_EQUITABLE));
		
		/*for (Feve feve : Feve.values()) {
			System.out.println(t.getStockFevesValeur(feve)) ;
			System.out.println(t.getStockValeur(feve)) ; }
		for (PateInterne pate : PateInterne.values()) {
			System.out.println(t.getStockPateValeur(pate)) ;
			System.out.println(t.getStockValeur(pate)) ; }
		System.out.println(t.getStockValeur(null)) ; renvoie bien une erreur
		 getStockValeur(produit) marche bien
		
		for (Feve feve : Feve.values()) {
			t.setStockValeur(feve, 10) ; }
		for (PateInterne pate : PateInterne.values()) {
			t.setStockValeur(pate, 15) ; }
		for (Feve feve : Feve.values()) {
			System.out.println(t.getStockFevesValeur(feve)) ;
			System.out.println(t.getStockValeur(feve)) ; }
		for (PateInterne pate : PateInterne.values()) {
			System.out.println(t.getStockPateValeur(pate)) ;
			System.out.println(t.getStockValeur(pate)) ; }
		t.setStockValeur(null, 10);
		t.setStockValeur(Pate.PATE_BASSE, 10) ; renvoie erreur : produit non utilisé
		 marche bien
		*/
		
		// l'ensemble de la transformation marche bien
		
		
		
	}
}
