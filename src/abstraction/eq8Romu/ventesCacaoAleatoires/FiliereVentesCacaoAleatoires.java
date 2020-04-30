package abstraction.eq8Romu.ventesCacaoAleatoires;

import java.awt.Color;

import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;

public class FiliereVentesCacaoAleatoires extends Filiere {

	public FiliereVentesCacaoAleatoires() {
		super();
		this.ajouterActeur(new ProducteurVentesAleatoires("PVA1", new Color(128,255,128)));
		this.ajouterActeur(new ProducteurVentesAleatoires("PVA2", new Color(128,255,255)));
		this.ajouterActeur(new TransformateurVentesAleatoires("TVA"));
		SuperviseurVentesCacaoAleatoires superviseur = new SuperviseurVentesCacaoAleatoires();
		this.ajouterActeur(superviseur);
		this.getParametre("PVA1 prix min").setValeur(superviseur, 1.5);
		this.getParametre("PVA1 prix interessant").setValeur(superviseur, 1.9);
		this.getParametre("PVA2 prix min").setValeur(superviseur, 1.8);
		this.getParametre("PVA2 prix interessant").setValeur(superviseur, 2.2);
		this.getParametre("SVCA prix min").setValeur(superviseur, 1.4);
		this.getParametre("SVCA prix max").setValeur(superviseur, 1.5);
		getBanque().initCompte(this, "TVA", 50000);
	}
	/**
	 * Redefinition afin d'interdire l'acces direct au superviseur.
	 * Sans cela, il serait possible de contourner l'autentification avec un code similaire a : 
	 * 		IActeur superviseur = Filiere.LA_FILIERE.getActeur("SVCA");
	 * 		IActeur acheteur = Filiere.LA_FILIERE.getActeur("TVA");
	 *		TransformateurVentesAleatoires t = (TransformateurVentesAleatoires)acheteur;
	 *		Integer crypto = t.getCryptogramme((SuperviseurVentesCacaoAleatoires)superviseur);
	 *		Filiere.LA_FILIERE.getBanque().virer(t, crypto, this, 123456);
	 */
	public IActeur getActeur(String nom) {
		if (!nom.equals("SVCA")) {
			return super.getActeur(nom); 
		} else {
			return null;
		}
	}
}
