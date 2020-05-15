package abstraction.eq8Romu.contratsCadres;

import abstraction.eq8Romu.produits.Pate;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;

public class FiliereTestContratCadre extends Filiere {

	private SuperviseurVentesContratCadre superviseurCC;

	public FiliereTestContratCadre() {
		super();
		this.ajouterActeur(new ExempleTransformateurContratCadreVendeurAcheteur(Pate.PATE_BASSE));
		this.ajouterActeur(new ExempleTransformateurContratCadreVendeurAcheteur(Pate.PATE_BASSE));
		this.ajouterActeur(new ExempleTransformateurContratCadreVendeurAcheteur(Pate.PATE_BASSE));
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
