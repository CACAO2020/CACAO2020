package abstraction.eq7Distributeur2;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import abstraction.eq8Romu.produits.Chocolat;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;

public class AbsDistributeurChocolat {
	private static int NB_INSTANCES = 0; // Afin d'attribuer un nom different a toutes les instances
	protected int numero;
	protected Integer cryptogramme;
	protected Journal journal;

	protected Distributeur2 ac;
	
	public AbsDistributeurChocolat(Distributeur2 ac) {	
		this.ac = ac;
		NB_INSTANCES++;
		this.numero=NB_INSTANCES;
		this.journal = new Journal(this.getNom()+" activites4", ac);
	}
	
	public String getNom() {
		return "D.Choco"+this.numero;
	}

	public String getDescription() {
		return "Distributeur de chocolat "+this.numero;
	}

	public Color getColor() {
		return new Color(128+((numero)*(127/NB_INSTANCES)), 64+((numero)*(191/NB_INSTANCES)), 0);
	}

	public void initialiser() {
	}

	public void next() {
	}

	public List<String> getNomsFilieresProposees() {
		return new ArrayList<String>();
	}

	public Filiere getFiliere(String nom) {
		return null;
	}

	public List<Variable> getIndicateurs() {
		return null;
	}

	public List<Variable> getParametres() {
		List<Variable> res=new ArrayList<Variable>();
		return res;
	}

	public List<Journal> getJournaux() {
		return null;
	}

	public void setCryptogramme(Integer crypto) {
		this.cryptogramme = crypto;
	}
	
	public void notificationFaillite(IActeur acteur) {
	}
	
	public void notificationOperationBancaire(double montant) {
	}
}