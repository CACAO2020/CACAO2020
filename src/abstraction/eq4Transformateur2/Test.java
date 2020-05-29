package abstraction.eq4Transformateur2;

import java.util.LinkedList;

import abstraction.eq5Transformateur3.Transformateur3;
import abstraction.eq8Romu.cacaoCriee.ExempleVendeurCacaoCriee;
import abstraction.eq8Romu.cacaoCriee.LotCacaoCriee;
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
import abstraction.eq8Romu.produits.Pate;
import abstraction.fourni.Filiere;

public class Test {

	public static void main(String[] args) {
		Transformateur2_next t = new Transformateur2_next();

		Filiere f = new Filiere();
		f.ajouterActeur(new ExempleAcheteurChocolatBourse());
		f.ajouterActeur(new ExempleAcheteurChocolatBourse());
		f.ajouterActeur(t);
        f.ajouterActeur(new ExempleVendeurCacaoCriee(Feve.FEVE_HAUTE, 1,10,500));
		f.ajouterActeur(new ExempleVendeurCacaoCriee(Feve.FEVE_HAUTE_EQUITABLE, 1,10,2000));
        SuperviseurCacaoCriee superviseur = new SuperviseurCacaoCriee();
        SuperviseurChocolatBourse superviseur2 = new SuperviseurChocolatBourse();
        SuperviseurVentesContratCadre superviseur3 = new SuperviseurVentesContratCadre();
        f.ajouterActeur(superviseur);
        f.ajouterActeur(superviseur2);
        f.ajouterActeur(superviseur3);
        
        
        Filiere.LA_FILIERE = f;
        
//		LinkedList<Double> li = new LinkedList<Double>();
//		for (int i = 0; i < 100; i++) {
//			li.add(Double.valueOf(5));
//		} 
//		Echeancier e = new Echeancier(Filiere.LA_FILIERE.getEtape(), li);
//		ContratCadre c = new ContratCadre(new ExempleTransformateurContratCadreVendeurAcheteur(Pate.PATE_BASSE), new ExempleTransformateurContratCadreVendeurAcheteur(Pate.PATE_BASSE), Pate.PATE_BASSE, e, t.cryptogramme);
//		ExemplaireContratCadre ex = new ExemplaireContratCadre(c);
//		c.signer();
//		t.getMesContratEnTantQueVendeur().add(ex);
//		
//		System.out.println("nb tour auto :" + t.nbToursAutonomiePate(PateInterne.PATE_BASSE)); //NBtour auto OP
//		t.majQuantitePateCC();
//		LotCacaoCriee lot = new LotCacaoCriee(new ExempleVendeurCacaoCriee(Feve.FEVE_BASSE,1,1,1), Feve.FEVE_MOYENNE_EQUITABLE, 10, 1);
//		System.out.println(t.proposerAchat(lot));
//		
//		String indicateur = "prix bourse haute";
//		System.out.println(indicateur + " " + Filiere.LA_FILIERE.getIndicateur("BourseChoco cours CHOCOLAT_HAUTE").getHistorique().get(Filiere.LA_FILIERE.getEtape()-1).getValeur());
//		
//		t.next();
        f.initialiser();
        for (int i = 0; i < 10; i++) {
            System.out.println("SOLDE : " + t.getSolde());
		//System.out.println("Feve Basse " + t.getStockFevesValeur(Feve.FEVE_BASSE));
		//System.out.println("Feve Moyenne " + t.getStockFevesValeur(Feve.FEVE_MOYENNE));
		//System.out.println("Feve Moyenne equi " + t.getStockFevesValeur(Feve.FEVE_MOYENNE_EQUITABLE));
		
        //System.out.println("Feve Haute " + t.getStockFevesValeur(Feve.FEVE_HAUTE));
		//System.out.println("Feve Haute equi " + t.getStockFevesValeur(Feve.FEVE_HAUTE_EQUITABLE));
		
		//System.out.println("Pate Basse " + t.getStockPateValeur(PateInterne.PATE_BASSE));
		//System.out.println("Pate haute " + t.getStockPateValeur(PateInterne.PATE_HAUTE));
		//System.out.println("Chocolat Moyenne equi " + t.getStockChocolatValeur(Chocolat.CHOCOLAT_MOYENNE_EQUITABLE));
		
        //System.out.println("Chocolat Haute " + t.getStockChocolatValeur(Chocolat.CHOCOLAT_HAUTE));
		//System.out.println("Chocolat Haute equi " + t.getStockChocolatValeur(Chocolat.CHOCOLAT_HAUTE_EQUITABLE));


        f.next();

        }


	}
}

