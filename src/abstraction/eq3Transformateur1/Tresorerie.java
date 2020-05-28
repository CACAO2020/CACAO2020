package abstraction.eq3Transformateur1;

import abstraction.fourni.Filiere;

/** @author AMAURY COUDRAY*/
public abstract class Tresorerie extends ActeurEQ3 {
	protected double MontantCompte;
	public Tresorerie() {
		this.MontantCompte=500000.0;
	}
	
	public double getMontantCompte() {
		return MontantCompte;
	}

	public void setMontantCompte(double nouveauMontant) {
		MontantCompte = nouveauMontant;
	}
	public void depense(double montant) {
		Filiere.LA_FILIERE.getBanque().virer(this, this.cryptogramme, Filiere.LA_FILIERE.getBanque(), montant);
	}
	public void notificationOperationBancaire(double montant) {
		setMontantCompte(montant+this.getMontantCompte());
	}

}
