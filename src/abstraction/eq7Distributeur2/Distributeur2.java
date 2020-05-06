package abstraction.eq7Distributeur2;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;

public class Distributeur2 implements IActeur {
	
	private Integer cryptogramme;
	private Journal journalEq7;
	private Variable stockChocolat;
	private Variable stockFeves;

	public Distributeur2() {
		this.journalEq7 = new Journal("Eq7 activites", this);
		this.stockChocolat = new Variable(getNom()+" stock chocolat", this, 0, 10000, 1000);
		this.stockFeves = new Variable(getNom()+" stock feves", this, 0, 10000, 1000);
	}

	public String getNom() {
		return "EQ7";
	}

	public String getDescription() {
		return "Ecocoa de Liège";
	}

	public Color getColor() {
		return new Color(240, 195, 15);
	}

	public void initialiser() {
	}

	public void setCryptogramme(Integer crypto) {
		this.cryptogramme = crypto;
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
		List<Variable> res=new ArrayList<Variable>();
		res.add(this.stockChocolat);
		res.add(this.stockFeves);
		return res;
	}

	public List<Variable> getParametres() {
		List<Variable> res=new ArrayList<Variable>();
		return res;
	}

	public List<Journal> getJournaux() {
		List<Journal> res=new ArrayList<Journal>();
		res.add(journalEq7);
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
