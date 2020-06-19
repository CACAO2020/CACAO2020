package abstraction.eq3Transformateur1;

import abstraction.fourni.Filiere;

/** @author AMAURY COUDRAY*/
public abstract class Tresorerie extends ActeurEQ3 {
	protected double MontantCompte;

	
	public double getMontantCompte() {
		return MontantCompte;
	}

	public void setMontantCompte(double nouveauMontant) {
		MontantCompte = nouveauMontant;
	}
	public boolean depense(double montant) {
		return Filiere.LA_FILIERE.getBanque().virer(this, this.cryptogramme, Filiere.LA_FILIERE.getBanque(), montant);
	}
	public void notificationOperationBancaire(double montant) {
		if(Filiere.LA_FILIERE.getBanque().getSolde(this, cryptogramme)<0) {
			this.journalTest.ajouter("l'operation d'un montant"+montant+"fait qu'on est dans le rouge" );
			this.journalTest.ajouter("on est a" +Filiere.LA_FILIERE.getBanque().getSolde(this, cryptogramme));
		}
		setMontantCompte(montant+this.getMontantCompte());
	}

}
