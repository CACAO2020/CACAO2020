package abstraction.eq4Transformateur2;

import abstraction.eq8Romu.cacaoCriee.ExempleAcheteurCacaoCriee;
import abstraction.eq8Romu.cacaoCriee.ExempleVendeurCacaoCriee;
import abstraction.eq8Romu.cacaoCriee.SuperviseurCacaoCriee;
import abstraction.eq8Romu.chocolatBourse.ExempleAcheteurChocolatBourse;
import abstraction.eq8Romu.chocolatBourse.ExempleVendeurChocolatBourse;
import abstraction.eq8Romu.chocolatBourse.SuperviseurChocolatBourse;
import abstraction.eq8Romu.contratsCadres.ExempleTransformateurContratCadreVendeurAcheteur;
import abstraction.eq8Romu.contratsCadres.SuperviseurVentesContratCadre;
import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.Feve;
import abstraction.eq8Romu.produits.Pate;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;

public class TestFiliereTransformateur2 extends Filiere {
	
	private SuperviseurVentesContratCadre superviseurCC ;
	private SuperviseurChocolatBourse superviseurChocolatBourse ;
	private SuperviseurCacaoCriee superviseurCacaoCriee ;

	public TestFiliereTransformateur2 () {
		
		super();
		this.ajouterActeur(new Transformateur2());
		this.ajouterActeur(new ExempleTransformateurContratCadreVendeurAcheteur(Pate.PATE_BASSE));
		this.ajouterActeur(new ExempleTransformateurContratCadreVendeurAcheteur(Pate.PATE_BASSE));
		this.ajouterActeur(new ExempleTransformateurContratCadreVendeurAcheteur(Pate.PATE_BASSE));
		this.ajouterActeur(new ExempleTransformateurContratCadreVendeurAcheteur(Pate.PATE_MOYENNE));
		this.ajouterActeur(new ExempleTransformateurContratCadreVendeurAcheteur(Pate.PATE_MOYENNE));
		this.superviseurCC=new SuperviseurVentesContratCadre();
		this.ajouterActeur(this.superviseurCC);
		this.superviseurCacaoCriee=new SuperviseurCacaoCriee();
		this.ajouterActeur(this.superviseurCacaoCriee);
		this.ajouterActeur(new ExempleVendeurCacaoCriee(Feve.FEVE_BASSE, 50, 200, 50));
		this.ajouterActeur(new ExempleVendeurCacaoCriee(Feve.FEVE_BASSE, 100, 200, 60));
		this.ajouterActeur(new ExempleVendeurCacaoCriee(Feve.FEVE_MOYENNE, 50, 200, 80));
		this.ajouterActeur(new ExempleVendeurCacaoCriee(Feve.FEVE_MOYENNE, 100, 200, 100));
		this.ajouterActeur(new ExempleVendeurCacaoCriee(Feve.FEVE_HAUTE, 100, 200, 140));
		this.ajouterActeur(new ExempleVendeurCacaoCriee(Feve.FEVE_HAUTE, 100, 200, 170));
		this.ajouterActeur(new ExempleVendeurCacaoCriee(Feve.FEVE_HAUTE_EQUITABLE, 50, 150, 240));
		this.ajouterActeur(new ExempleVendeurCacaoCriee(Feve.FEVE_MOYENNE_EQUITABLE, 50, 150, 180));
		this.ajouterActeur(new ExempleAcheteurCacaoCriee());
		this.superviseurChocolatBourse =new SuperviseurChocolatBourse();
		this.ajouterActeur(this.superviseurChocolatBourse);
		this.ajouterActeur(new ExempleAcheteurChocolatBourse());
		this.ajouterActeur(new ExempleAcheteurChocolatBourse());
//		for (Chocolat chocolat : Chocolat.values()) {
//			this.ajouterActeur(new ExempleVendeurChocolatBourse(chocolat)) ;
//		}
	}
	
	
	public void initialiser() {
		super.initialiser();
	}

	
	/**
	 * Redefinition afin d'interdire l'acces direct au client final
	 */
	public IActeur getActeur(String nom) {
		if (!nom.equals("CLIENTFINAL")) {
			return super.getActeur(nom); 
		} else {
			return null;
		}
	}
	
	public SuperviseurCacaoCriee getSuperviseurCacaoCriee () {
		return this.superviseurCacaoCriee ;
	}
	
	public SuperviseurVentesContratCadre getSuperviseurContratCadre() {
		return this.superviseurCC ;
	}
	
	public SuperviseurChocolatBourse getSuperviseurChocolatBourse() {
		return this.superviseurChocolatBourse ;
	}
	
}
