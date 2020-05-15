package abstraction.eq7Distributeur2;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.ChocolatDeMarque;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;

public class AbsAcheteurChocolat {
	protected Map<Chocolat, Variable> demandesChoco;
	protected List<Chocolat> gammesChocolat;

	protected Journal journal;

	protected Distributeur2 ac;
	
	public AbsAcheteurChocolat() {
		
	}

	public AbsAcheteurChocolat(Distributeur2 ac) {
		this.ac = ac;
		demandesChoco=new HashMap<Chocolat, Variable>();
		
		gammesChocolat = new ArrayList<Chocolat>();
		gammesChocolat.add(Chocolat.CHOCOLAT_MOYENNE);
		gammesChocolat.add(Chocolat.CHOCOLAT_HAUTE);
		gammesChocolat.add(Chocolat.CHOCOLAT_MOYENNE_EQUITABLE);
		gammesChocolat.add(Chocolat.CHOCOLAT_HAUTE_EQUITABLE);
		
		for (Chocolat choco : Chocolat.values()) {
			demandesChoco.put(choco, new Variable("Demande en : " + choco.name(), ac, 0));
		}
		this.journal = new Journal(this.getNom() + " Acheteur Chocolat Bourse " + ac.getNumero(), ac);
	}

	public Map<Chocolat, Variable> getDemandesChoco() {
		return demandesChoco;
	}

	public String getNom() {
		return ac.getNom();
	}


	public String getDescription() {
		return ac.getDescription();
	}


	public Color getColor() {
		return ac.getColor();
	}


	public List<String> getNomsFilieresProposees() {
		return ac.getNomsFilieresProposees();
	}


	public Filiere getFiliere(String nom) {
		return ac.getFiliere(nom);
	}


	public void setCryptogramme(Integer crypto) {
		ac.setCryptogramme(crypto);
	}


	public void notificationFaillite(IActeur acteur) {
		ac.notificationFaillite(acteur);
	}


	public void notificationOperationBancaire(double montant) {
		ac.notificationOperationBancaire(montant);
	}

	public void initialiser() {
	}
	
	public void next() {
		
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
	
} 
