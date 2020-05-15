package abstraction.eq1Producteur1;

public class Budget {
	private double fonds;
	private int employes;
	
	public double getFonds() {
		return this.fonds;
	}
	
	public int getEmployes() {
		return this.employes;
	}
	
	public void setFonds(double n_fonds) {
		this.fonds = n_fonds;
	}
	
	public void setEmployes(int n_employes) {
		this.employes = n_employes;
	}
	
	public void addFonds(double ajout) {
		this.setFonds(this.getFonds() + ajout);
	}
	
	public void removeFonds(double retrait) {
		this.setFonds(this.getFonds() - retrait);
	}
	
	public void addEmployes(int ajout) {
		this.setEmployes(this.getEmployes() + ajout);
	}
	
	public void removeEmployes(int retrait) {
		this.setEmployes(this.getEmployes() - retrait);
	}
	
	
/*
 * Fonction à fair tourner à chaque cycle dans le programme principal.
 * Paie des employés (récolteurs, voir après si on diversifie les employés) :
 * 60 000 francs CFA/an, donc 2 500 francs CFA/cycle (3,81 euros).
 */
	public void cyclique() {
		if (this.getFonds()>this.getEmployes()*3.81) {
			this.removeFonds(this.getEmployes()*3.81);
		} else {
			int max_employes = (int) ((int) this.getFonds()/3.81);
			this.setEmployes(max_employes);
			this.removeFonds(this.getEmployes()*3.81);
		}
	}

}
