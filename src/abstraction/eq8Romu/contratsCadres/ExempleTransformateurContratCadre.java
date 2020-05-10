package abstraction.eq8Romu.contratsCadres;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;

public class ExempleTransformateurContratCadre implements IActeur {
	private static int NB_INSTANCES = 0; // Afin d'attribuer un nom different a toutes les instances
	protected int numero;
	protected Variable stock;
	protected Integer cryptogramme;
	protected Object produit;
	protected Journal journal;

	public ExempleTransformateurContratCadre(Object produit) {	
		if (produit==null) {
			throw new IllegalArgumentException("creation d'une instance de ExempleTransformateurContratCadre avec produit==null");
		}		
		NB_INSTANCES++;
		this.numero=NB_INSTANCES;
		this.produit=produit;
		this.stock=new Variable(getNom()+" stock ", this, 0, 10000, 1000);
		this.journal = new Journal(this.getNom()+" activites", this);
	}
	
	public String getNom() {
		return "TCC"+this.numero+""+produit.toString();
	}

	public String getDescription() {
		return "TransformateurContratCadre "+this.numero+" "+this.produit.toString();
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
		List<Variable> res=new ArrayList<Variable>();
		res.add(this.stock);
		return res;
	}

	public List<Variable> getParametres() {
		List<Variable> res=new ArrayList<Variable>();
		return res;
	}

	public List<Journal> getJournaux() {
		List<Journal> j= new ArrayList<Journal>();
		j.add(this.journal);
		return j;
	}

	public void setCryptogramme(Integer crypto) {
		this.cryptogramme = crypto;
	}
	
	public void notificationFaillite(IActeur acteur) {
	}
	
	public void notificationOperationBancaire(double montant) {
	}
}
