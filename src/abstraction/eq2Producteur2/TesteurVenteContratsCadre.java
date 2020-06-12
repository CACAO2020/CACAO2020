package abstraction.eq2Producteur2;


import abstraction.eq8Romu.contratsCadres.ExempleTransformateurContratCadreVendeurAcheteur;
import abstraction.eq8Romu.contratsCadres.SuperviseurVentesContratCadre;
import abstraction.eq8Romu.produits.Feve;
import abstraction.eq8Romu.produits.Pate;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;

public class TesteurVenteContratsCadre extends Filiere {

	private SuperviseurVentesContratCadre superviseurCC;

	public TesteurVenteContratsCadre() {
		super();
		this.ajouterActeur(new ExempleTransformateurContratCadreVendeurAcheteur(Feve.FEVE_BASSE));
		this.ajouterActeur(new ExempleTransformateurContratCadreVendeurAcheteur(Feve.FEVE_MOYENNE));
		this.ajouterActeur(new ExempleTransformateurContratCadreVendeurAcheteur(Feve.FEVE_MOYENNE_EQUITABLE));
		this.ajouterActeur(new ExempleTransformateurContratCadreVendeurAcheteur(Feve.FEVE_HAUTE));
		this.ajouterActeur(new ExempleTransformateurContratCadreVendeurAcheteur(Feve.FEVE_HAUTE_EQUITABLE));
		this.ajouterActeur(new VenteContratCadre());
		this.superviseurCC=new SuperviseurVentesContratCadre();
		this.ajouterActeur(this.superviseurCC);
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
	public SuperviseurVentesContratCadre getSuperviseurContratCadre() {
		return this.superviseurCC;
	}
}
