package abstraction.eq5Transformateur3;

import abstraction.eq8Romu.contratsCadres.Echeancier;
import abstraction.eq8Romu.contratsCadres.ExemplaireContratCadre;

public class AchatPate {
    /**
     * Gère les achats de pates avec le contrat cadre et négocie les prix.
     * Elle est agrégé dans la classe Transformateur3.
     */
    //TODO ajouter les methodes pour acheter de la pate de cacao

    private Transformateur3 acteur;
		
    public AchatPate(Transformateur3 acteur) {
        this.acteur = acteur;
    }

	public Echeancier contrePropositionDeLAcheteur(ExemplaireContratCadre contrat) {
		return null;
	}

	public double contrePropositionPrixAcheteur(ExemplaireContratCadre contrat) {
		return 0;
	}

	public void receptionner(Object produit, double quantite, ExemplaireContratCadre contrat) {
	}
}