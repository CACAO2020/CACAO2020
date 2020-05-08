package abstraction.eq7Distributeur2;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.Feve;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;

public class AbsAcheteurCacaoCriee implements IActeur {
	protected Integer cryptogramme;
	protected Journal journal;
	protected Distributeur2 ac;

	public AbsAcheteurCacaoCriee(Distributeur2 ac) {
		this.ac = ac;
		this.journal = new Journal(this.getNom()+ " Acheteur Cacao Criée "+ this.ac.getNumero(), ac);
	}
	
	public String getNom() {
		return "EQ7";
	}

	public String getDescription() {
		return "Acheteur de cacao a la criee "+this.ac.getNumero();
	}
	
	public Color getColor() {
		return ac.getColor();
	}

	public void initialiser() {
	}

	public void setCryptogramme(Integer crypto) {
		this.cryptogramme = crypto;
	}
	public void next() {
		ac.getStock().ajouterStockChocolat(ac.stringToChoco("H"), 10);
	}

	public List<String> getNomsFilieresProposees() {
		return new ArrayList<String>();
	}

	public Filiere getFiliere(String nom) {
		return null;
	}
	
	public List<Variable> getIndicateurs() {
		List<Variable> res = new ArrayList<Variable>();
		return res;
	}

	public List<Variable> getParametres() {
		List<Variable> res=new ArrayList<Variable>();
		return res;
	}

	public List<Journal> getJournaux() {
		List<Journal> res = new ArrayList<Journal>();
		res.add(journal);
		return res;
	}
	
	public void notificationFaillite(IActeur acteur) {
	}
	
	public void notificationOperationBancaire(double montant) {
	}
}