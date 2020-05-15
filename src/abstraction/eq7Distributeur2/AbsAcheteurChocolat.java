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

public class AbsAcheteurChocolat implements IActeur {
	protected Map<Chocolat, Variable> demandesChoco;
	protected List<Chocolat> gammesChocolat;
	protected Integer cryptogramme;
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
		return "EQ7";
	}

	public String getDescription() {
		return "Acheteur de chocolat a la bourse "+this.ac.getNumero();
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
