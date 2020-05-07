package abstraction.eq7Distributeur2;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;

public class Distributeur2 extends AcheteurChocolatBourse implements IActeur {
	
	private Integer cryptogramme;
	private Journal journalEq7;
	private Variable stockChocolatHG;
	private Variable stockChocolatHGE;
	private Variable stockChocolatMG;
	private Variable stockChocolatMGE;
	private Variable stockFevesHG;
	private Variable stockFevesHGE;

	public Distributeur2() {
		this.journalEq7 = new Journal("Eq7 activites", this);
		this.stockChocolatHG = new Variable(getNom()+" stock chocolat haut de gamme", this, 0, 10000, 1000);
		this.stockChocolatHGE = new Variable(getNom()+" stock chocolat haut de gamme équitable", this, 0, 10000, 1000);
		this.stockChocolatMG = new Variable(getNom()+" stock chocolat moyenne gamme", this, 0, 10000, 1000);
		this.stockChocolatMGE = new Variable(getNom()+" stock chocolat moyenne gamme équitable", this, 0, 10000, 1000);
		this.stockFevesHG = new Variable(getNom()+" stock feves haut de gamme", this, 0, 10000, 1000);
		this.stockFevesHGE = new Variable(getNom()+" stock feves haut de gamme équitables", this, 0, 10000, 1000);
	}

	public String getNom() {
		return "EQ7 : Ecocoa de Liège";
	}

	public String getDescription() {
		return "Ecocoa de Liège, chocolatier responsable";
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
		res.add(this.stockChocolatHG);
		res.add(this.stockChocolatHGE);
		res.add(this.stockChocolatMG);
		res.add(this.stockChocolatMGE);
		res.add(this.stockFevesHG);
		res.add(this.stockFevesHGE);
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
