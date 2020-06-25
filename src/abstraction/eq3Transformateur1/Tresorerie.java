package abstraction.eq3Transformateur1;

import java.util.ArrayList;
import java.util.List;

import abstraction.fourni.Filiere;
import abstraction.fourni.Variable;

/** @author AMAURY COUDRAY*/
public abstract class Tresorerie extends ActeurEQ3 {
	protected double MontantCompte;
	protected Variable cATotal;
	protected List<Double> cATour;
	public Tresorerie() {
		this.cATotal=new Variable("treso", this);
		this.cATotal.setValeur(this, 0);
		
		this.cATour=new ArrayList<Double>();
		}
	
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
			this.journalTest.ajouter("L'operation d'un montant "+montant+" fait qu'on est dans le rouge" );
			this.journalTest.ajouter("On est à " +Filiere.LA_FILIERE.getBanque().getSolde(this, cryptogramme));
		}
		if(montant>0) {
			cATotal.setValeur(this, cATotal.getValeur()+montant);
			cATour.set(Filiere.LA_FILIERE.getEtape(), cATour.get(Filiere.LA_FILIERE.getEtape())+montant);
		}
		setMontantCompte(montant+this.getMontantCompte());
	}

}
