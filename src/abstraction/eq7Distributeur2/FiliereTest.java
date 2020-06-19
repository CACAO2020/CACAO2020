package abstraction.eq7Distributeur2;

import java.awt.Color;

import abstraction.eq1Producteur1.Producteur1;
import abstraction.eq2Producteur2.Producteur2;
import abstraction.eq3Transformateur1.Transformateur1;
import abstraction.eq4Transformateur2.Transformateur2;
import abstraction.eq5Transformateur3.Transformateur3;
import abstraction.eq6Distributeur1.Distributeur1;
import abstraction.eq8Romu.Romu;
import abstraction.eq8Romu.cacaoCriee.ExempleAcheteurCacaoCriee;
import abstraction.eq8Romu.cacaoCriee.SuperviseurCacaoCriee;
import abstraction.eq7Distributeur2.Transformateur;
import abstraction.eq8Romu.chocolatBourse.SuperviseurChocolatBourse;
import abstraction.eq8Romu.clients.ClientFinal;
import abstraction.eq8Romu.contratsCadres.ExempleTransformateurContratCadreVendeur;
import abstraction.eq8Romu.contratsCadres.ExempleTransformateurContratCadreVendeurAcheteur;
import abstraction.eq8Romu.contratsCadres.SuperviseurVentesContratCadre;
import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.Feve;
import abstraction.eq8Romu.ventesCacaoAleatoires.ProducteurVentesAleatoires;
import abstraction.eq8Romu.ventesCacaoAleatoires.TransformateurVentesAleatoires;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;


public class FiliereTest extends Filiere {
	
	private ClientFinal cf;
	private SuperviseurVentesContratCadre superviseurCC;
	
	public FiliereTest() {
		super();
		
		// CLIENT FINAL
		cf = new ClientFinal();
		this.ajouterActeur(cf);
		
		// PRODUCTEURS
	//	this.ajouterActeur(new Producteur(Feve.FEVE_MOYENNE));
	//	this.ajouterActeur(new Producteur(Feve.FEVE_MOYENNE_EQUITABLE));
	//	this.ajouterActeur(new Producteur(Feve.FEVE_HAUTE));
	//	this.ajouterActeur(new Producteur(Feve.FEVE_HAUTE_EQUITABLE));
		
		// TRANSFORMATEURS BOURSE
		this.ajouterActeur(new Transformateur(Chocolat.CHOCOLAT_MOYENNE));
	//	this.ajouterActeur(new Transformateur(Chocolat.CHOCOLAT_MOYENNE_EQUITABLE));
	//	this.ajouterActeur(new Transformateur(Chocolat.CHOCOLAT_HAUTE));
	//	this.ajouterActeur(new Transformateur(Chocolat.CHOCOLAT_HAUTE_EQUITABLE));
		
		// TRANSFORMATEURS CONTRAT-CADRE
	//	this.ajouterActeur(new ExempleTransformateurContratCadreVendeurAcheteur(Chocolat.CHOCOLAT_MOYENNE));
	//	this.ajouterActeur(new ExempleTransformateurContratCadreVendeurAcheteur(Chocolat.CHOCOLAT_MOYENNE_EQUITABLE));
	//	this.ajouterActeur(new ExempleTransformateurContratCadreVendeurAcheteur(Chocolat.CHOCOLAT_HAUTE));
	//	this.ajouterActeur(new ExempleTransformateurContratCadreVendeurAcheteur(Chocolat.CHOCOLAT_HAUTE_EQUITABLE));
		
		// DISTRIBUTEUR (NOUS)
		this.ajouterActeur(new Distributeur2());
		
		// ROMU
		this.ajouterActeur(new Romu());
		
		// SUPERVISEURS
		SuperviseurCacaoCriee superviseur = new SuperviseurCacaoCriee();
		this.ajouterActeur(superviseur);
		SuperviseurChocolatBourse superviseurBourse = new SuperviseurChocolatBourse();
		this.ajouterActeur(superviseurBourse);
		this.superviseurCC=new SuperviseurVentesContratCadre();
		this.ajouterActeur(this.superviseurCC);
	}
	
	public SuperviseurVentesContratCadre getSuperviseurContratCadre() {
		return this.superviseurCC;
	}
	
	public IActeur getActeur(String nom) {
		if (!nom.equals("Sup.C.Criee")) {
			return super.getActeur(nom); 
		} else {
			return null;
		}
	}
	
}
