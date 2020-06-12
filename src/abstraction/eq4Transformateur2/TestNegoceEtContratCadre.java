package abstraction.eq4Transformateur2;

import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import abstraction.eq8Romu.cacaoCriee.ExempleVendeurCacaoCriee;
import abstraction.eq8Romu.cacaoCriee.SuperviseurCacaoCriee;
import abstraction.eq8Romu.chocolatBourse.ExempleAcheteurChocolatBourse;
import abstraction.eq8Romu.chocolatBourse.SuperviseurChocolatBourse;
import abstraction.eq8Romu.contratsCadres.ContratCadre;
import abstraction.eq8Romu.contratsCadres.Echeancier;
import abstraction.eq8Romu.contratsCadres.ExemplaireContratCadre;
import abstraction.eq8Romu.contratsCadres.ExempleTransformateurContratCadreVendeurAcheteur;
import abstraction.eq8Romu.contratsCadres.SuperviseurVentesContratCadre;
import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.Feve;
import abstraction.eq8Romu.produits.Gamme;
import abstraction.eq8Romu.produits.Pate;
import abstraction.fourni.Filiere;

public class TestNegoceEtContratCadre {
	
	public static void main(String[] args) {
		
		Transformateur2 t = new Transformateur2();

		Filiere f = new Filiere();
		f.ajouterActeur(t);
		f.ajouterActeur(new ExempleTransformateurContratCadreVendeurAcheteur(Pate.PATE_BASSE));
		f.ajouterActeur(new ExempleTransformateurContratCadreVendeurAcheteur(Pate.PATE_BASSE));
		f.ajouterActeur(new ExempleTransformateurContratCadreVendeurAcheteur(Pate.PATE_MOYENNE));
        SuperviseurVentesContratCadre superviseur3 = new SuperviseurVentesContratCadre();
        f.ajouterActeur(superviseur3);
        f.initialiser();
        Filiere.LA_FILIERE = f;
		
		/*LinkedList<Double> li = new LinkedList<Double>();
		for (int i = 0; i < 10; i++) {
			li.add(10.0);
		} 
		/*Echeancier parfait = new Echeancier(Filiere.LA_FILIERE.getEtape(), li);
		t.echeancierLimite(parfait, PateInterne.PATE_BASSE, true);
		for (int i = parfait.getStepDebut() ; i < parfait.getStepFin() ; i++) {
			System.out.println(parfait.getQuantite(i));
		} l'échéancier parfait fonctionne bien */
		
		/*li.add(500.0);
		for (int i = 1; i < 10; i++) {
			li.add(i*100.0);
		} 
		Echeancier limite = new Echeancier(Filiere.LA_FILIERE.getEtape(), li);
		t.echeancierLimite(limite, PateInterne.PATE_BASSE, false);
		for (int i = limite.getStepDebut() ; i < limite.getStepFin() ; i++) {
			System.out.println(limite.getQuantite(i));
		} l'échéancier limite fonctionne bien */
		
		/* Echeancier e = new Echeancier(Filiere.LA_FILIERE.getEtape(), li);
		ContratCadre c = new ContratCadre(new ExempleTransformateurContratCadreVendeurAcheteur(Pate.PATE_BASSE), new ExempleTransformateurContratCadreVendeurAcheteur(Pate.PATE_BASSE), Pate.PATE_BASSE, e, t.cryptogramme);
		c.signer();
		ExemplaireContratCadre ex = new ExemplaireContratCadre(c);
		t.notificationNouveauContratCadre(ex) ;
		
		System.out.println(t.getQuantitePateCCValeur(PateInterne.PATE_BASSE));
		System.out.println(t.getQuantitePateCCValeur(PateInterne.PATE_MOYENNE)); */
		
		LinkedList<Double> li2 = new LinkedList<Double>();
		for (int i = 0; i < 10; i++) {
			li2.add(100.0);
		}
		
		/* Echeancier e2 = new Echeancier(Filiere.LA_FILIERE.getEtape(), li2);
		ContratCadre c2 = new ContratCadre(new ExempleTransformateurContratCadreVendeurAcheteur(Pate.PATE_MOYENNE), new ExempleTransformateurContratCadreVendeurAcheteur(Pate.PATE_MOYENNE), Pate.PATE_MOYENNE, e2, t.cryptogramme);
		c2.signer();
		ExemplaireContratCadre ex2 = new ExemplaireContratCadre(c2);
		t.notificationNouveauContratCadre(ex2) ;
			
		System.out.println(t.getQuantitePateCCValeur(PateInterne.PATE_BASSE));
		System.out.println(t.getQuantitePateCCValeur(PateInterne.PATE_MOYENNE));	
		
		/* System.out.println(t.vend(Pate.PATE_BASSE));
		System.out.println(t.vend(Pate.PATE_MOYENNE)); vend semble marcher */
		
		/*t.livrer(Pate.PATE_BASSE, 200.0, ex) ;
		System.out.println(t.getStockPateValeur(PateInterne.PATE_BASSE)); livrer semble marcher */
        
        /*Echeancier e3 = new Echeancier(Filiere.LA_FILIERE.getEtape(), li2);
		ContratCadre c3 = new ContratCadre(new ExempleTransformateurContratCadreVendeurAcheteur(Pate.PATE_MOYENNE), new ExempleTransformateurContratCadreVendeurAcheteur(Pate.PATE_MOYENNE), Pate.PATE_MOYENNE, e3, t.cryptogramme);
		c3.ajouterPrixALaTonne(1.0);
		c3.ajouterPrixALaTonne(520.0);
		c3.ajouterPrixALaTonne(400.0);
		c3.ajouterPrixALaTonne(460.0);
		c3.ajouterPrixALaTonne(440.0);
		c3.signer();
		ExemplaireContratCadre ex3 = new ExemplaireContratCadre(c3);
		System.out.println(t.getCoutMoyenPateValeur(PateInterne.PATE_MOYENNE)) ;
		System.out.println(t.contrePropositionPrixVendeur(ex3)) ;
		t.notificationNouveauContratCadre(ex3) ; la contre proposition pour les prix marche */
		
	}
}
