package abstraction.eq7Distributeur2;

import java.awt.Color;
import java.util.List;

import abstraction.eq8Romu.contratsCadres.Echeancier;
import abstraction.eq8Romu.contratsCadres.ExemplaireContratCadre;
import abstraction.eq8Romu.contratsCadres.IAcheteurContratCadre;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;

public class AcheteurContratCadre extends AbsAcheteurContratCadre implements IAcheteurContratCadre, IActeur{

	public AcheteurContratCadre(Distributeur2 ac) {
		super(ac);
	}
	
	public void initialiser() {
		
	}

	public void next() {
		
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
