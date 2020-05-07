package abstraction.eq7Distributeur2;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import abstraction.eq8Romu.produits.Chocolat;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;

public class AbsAcheteurChocolatBourse implements IActeur {
	private static int NB_INSTANCES = 0; // Afin d'attribuer un nom different a toutes les instances
	private int numero;
	private Map<Chocolat, Variable> demande_choco;
	protected Integer cryptogramme;
	protected Journal journal;

	protected Distributeur2 ac;
	
	public AbsAcheteurChocolatBourse() {
		
	}
	
	public AbsAcheteurChocolatBourse(Distributeur2 ac) {
		this.ac = ac;
		NB_INSTANCES++;
		this.numero=NB_INSTANCES;
		demande_choco=new HashMap<Chocolat, Variable>();
		for (Chocolat choco : Chocolat.values()) {
			demande_choco.put(choco, new Variable("Demande en : " + choco.name(), ac, 0));
		}
		this.journal = new Journal(this.getNom()+" activites", ac);
	}

	public Map<Chocolat, Variable> getDemande_choco() {
		return demande_choco;
	}

	public String getNom() {
		return "A.ChocoBourse"+numero;
	}

	public String getDescription() {
		return "Acheteur de chocolat a la bourse "+this.numero;
	}
	
	public Color getColor() {
		return new Color(((numero)*(128/NB_INSTANCES)), ((numero)*(255/NB_INSTANCES)), 128+(numero)*(127/NB_INSTANCES));
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
		return null;
	}

	public List<Variable> getParametres() {
		List<Variable> res=new ArrayList<Variable>();
		return res;
	}

	public List<Journal> getJournaux() {
		return null;
	}
	
	public void notificationFaillite(IActeur acteur) {
	}
	
	public void notificationOperationBancaire(double montant) {
	}
} 
