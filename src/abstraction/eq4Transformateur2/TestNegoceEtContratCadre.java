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

public class TestNegoceEtContratCadre {
	
	public static void main(String[] args) {
		Transformateur2_contratCadre t = new Transformateur2_contratCadre () ;
		List<Variable> listeIndicateurs = t.getIndicateurs() ;
		for (Variable v : listeIndicateurs) {
			System.out.println(v.getNom());
			System.out.println(v.getValeur());
		}
		List<Variable> listeParametres = t.getParametres() ;
		for (Variable v : listeParametres) {
			System.out.println(v.getNom());
			System.out.println(v.getValeur());
		}
		//t.transformationFeveEnPate (10, Feve.FEVE_BASSE) ;
		//System.out.println(t.getStockFevesValeur(Feve.FEVE_BASSE)) ;
		//t.transformationFeveEnPate (100, Feve.FEVE_BASSE) ;
		//System.out.println(t.getStockFevesValeur(Feve.FEVE_BASSE)) ;
		// renvoie bien l'erreur stock négatif
		//t.transformationFeveEnPate (40, Feve.FEVE_BASSE) ;
		//System.out.println(t.getStockFevesValeur(Feve.FEVE_BASSE)) ;
		//t.transformationFeveEnPate (50, Feve.FEVE_MOYENNE) ;
		//System.out.println(t.getStockFevesValeur(Feve.FEVE_MOYENNE)) ;
		//t.transformationFeveEnPate (20, Feve.FEVE_MOYENNE_EQUITABLE) ;
		//System.out.println(t.getStockFevesValeur(Feve.FEVE_MOYENNE_EQUITABLE)) ;
		// renvoie 40, 0, 0, 30 : marche bien
		
		Map<Feve,Double> quantitesF = new HashMap<Feve,Double>() ;
		quantitesF.put(Feve.FEVE_BASSE,40.0) ;
		quantitesF.put(Feve.FEVE_MOYENNE,40.0) ;
		quantitesF.put(Feve.FEVE_MOYENNE_EQUITABLE,40.0) ;
		t.transformationFeves (quantitesF) ;
		
		for (Variable v : listeIndicateurs) {
			System.out.println(v.getNom());
			System.out.println(v.getValeur());
		}
		
		Map<PateInterne,Double> quantitesP = new HashMap<PateInterne,Double>() ;
		quantitesP.put(PateInterne.PATE_BASSE,70.0) ;
		quantitesP.put(PateInterne.PATE_MOYENNE,70.0) ;
		quantitesP.put(PateInterne.PATE_MOYENNE_EQUITABLE,70.0) ;
		t.transformationPate (quantitesP) ;
		
		for (Variable v : listeIndicateurs) {
			System.out.println(v.getNom());
			System.out.println(v.getValeur());
		}
	}
}
