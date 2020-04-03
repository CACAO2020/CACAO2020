package abstraction.eq8Romu;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import abstraction.eq8Romu.ventesCacaoAleatoires.FiliereVentesCacaoAleatoires;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;

public class Romu implements IActeur {
	private Variable soldeBancaire;
	@SuppressWarnings("unused")
	private Integer cryptogramme;
	
	public Romu() {
		this.soldeBancaire=new Variable(getNom()+" solde bancaire", this, 100000);
	}

	public String getNom() {
		return "EQ8";
	}

	public String getDescription() {
		return "createur des autres acteurs de la filiere";
	}

	public Color getColor() {
		return new Color(96, 125, 139);
	}

	public void initialiser() {
	}

	public void setCryptogramme(Integer crypto) {
		this.cryptogramme = crypto;
	}
	
	public void next() {
	}

	public List<String> getNomsFilieresProposees() {
		ArrayList<String> filieres = new ArrayList<String>();
		filieres.add("VCA"); // Ventes  Cacao Aleatoires
		return filieres;
	}

	public Filiere getFiliere(String nom) {
		switch (nom) {
		case "VCA" : return new FiliereVentesCacaoAleatoires();
	    default : return null;
		}
	}

	public List<Variable> getIndicateurs() {
		List<Variable> res =  new ArrayList<Variable>();
		res.add(this.soldeBancaire);
		return res;
	}

	public List<Variable> getParametres() {
		return new ArrayList<Variable>();
	}

	public List<Journal> getJournaux() {
		return new ArrayList<Journal>();
	}
	
	public void notificationFaillite(IActeur acteur) {
		if (this==acteur) {
		System.out.println("They killed Romu... ");
		} else {
			System.out.println("Poor "+acteur.getNom()+"... We will miss you. "+this.getNom());
		}
	}
	
	public void notificationOperationBancaire(double montant) {
	}

}
