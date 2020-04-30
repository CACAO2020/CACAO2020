package abstraction.eq3Transformateur1;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import abstraction.eq8Romu.produits.Chocolat;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;

public class VendeurChocolatBourse implements IActeur {
	private static int NB_INSTANCES = 0; // Afin d'attribuer un nom different a toutes les instances
	protected int numero;
	protected Variable stockChocolat;
	protected Integer cryptogramme;
	protected Chocolat chocolat;
	protected Journal journal;

	public VendeurChocolatBourse(Chocolat choco) {	
		if (choco==null) {
			throw new IllegalArgumentException("creation d'une instance de ExempleAbsVendeurChocolatBourse avec choco==null");
		}		
		NB_INSTANCES++;
		this.numero=NB_INSTANCES;
		this.chocolat = choco;
		this.stockChocolat=new Variable(getNom()+" stock "+choco.name(), this, 0, 10000, 1000);
		this.journal = new Journal(this.getNom()+" activites", this);
	}
	
	public String getNom() {
		return "V.ChocoBourse"+this.numero+""+chocolat.name();
	}

	public String getDescription() {
		return "Vendeur de chocolat a la bourse "+this.numero+" "+this.chocolat.name();
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
		res.add(this.stockChocolat);
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
