package abstraction.eq2Producteur2;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;
import abstraction.fourni.Filiere;

public class Producteur2 implements IActeur {
	
	private eq2Stock stock;
	private Integer cryptogramme;
	private Journal journalEq2;
	private List<PaquetArbres> PaquetsArbres; // la liste des paquets d'arbres de notre acteur

	public Producteur2() {
		this.stock=new eq2Stock(this, 0,0,0,0,0);
		this.journalEq2 = new Journal("Eq2 activites", this);
		this.PaquetsArbres = new ArrayList<PaquetArbres>();
	}
	
	public String getNom() {
		return "Moulacao";
	}

	public String getDescription() {

		return "Producteur kris";

	}

	public Color getColor() {
		return new Color(46, 204, 113);
	}

	public void initialiser() {
	}
	
	public void setCryptogramme(Integer crypto) {
		this.cryptogramme = crypto;
	}
	public List<PaquetArbres> getPaquetsArbres(){
		return this.PaquetsArbres;
	}
	/**
	 * Cette methode est appellee a chaque nouveau tour
	 * Pour l'instant elle avance l'age de chaque paquet d'arbre de 1
	 * @author Kristof Szentes
	 */
	public void next() {
		for (int i = 0; i < this.getPaquetsArbres().size(); i++) {
			this.getPaquetsArbres().get(i).setAge(this.getPaquetsArbres().get(i).getAge() + 1);
		}
	}

	public List<String> getNomsFilieresProposees() {
		return new ArrayList<String>();
	}

	public Filiere getFiliere(String nom) {
		return null;
	}
	public List<Variable> getIndicateurs() {
		List<Variable> res=new ArrayList<Variable>();
		res.addAll(this.stock.getVariables());
		return res;
	}

	public List<Variable> getParametres() {
		List<Variable> res=new ArrayList<Variable>();
		return res;
	}

	public List<Journal> getJournaux() {
		List<Journal> res=new ArrayList<Journal>();
		res.add(this.journalEq2);
		return res;
	}

	public void notificationFaillite(IActeur acteur) {
		if (this==acteur) {
		System.out.println("I'll be back... or not... "+this.getNom());
		} else {
			System.out.println("Poor "+acteur.getNom()+"... We will miss you. "+this.getNom());
		}
	}
	
	public void notificationOperationBancaire(double montant) {
	}
}
