package abstraction.eq7Distributeur2;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import abstraction.eq8Romu.produits.Chocolat;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;

public class DistributeurChocolatAbs implements IActeur {
	private static int NB_INSTANCES = 0; // Afin d'attribuer un nom different a toutes les instances
	protected int numero;
	protected Variable stockChocolat;
	protected Variable stockFeves;
	protected Integer cryptogramme;
	protected Chocolat chocolat;
	protected Journal journal;
	
	public DistributeurChocolatAbs(Chocolat choco) {	
		if (choco==null) {
			throw new IllegalArgumentException("creation d'une instance de ExempleAbsDistributeurChocolat avec choco==null");
		}		
		NB_INSTANCES++;
		this.numero=NB_INSTANCES;
		this.chocolat = choco;
		this.stockFeves = new Variable(getNom()+" stock feves", this, 0);
		this.stockChocolat=new Variable(getNom()+" stock "+choco.name(), this, 0, 10000, 1000);
		this.journal = new Journal("Eq7 activitesS", this);
	}
	
	public String getNom() {
		return "D.Choco"+this.numero+""+chocolat.name();
	}

	public String getDescription() {
		return "Distributeur de chocolat "+this.numero+" "+this.chocolat.name();
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
		res.add(this.stockFeves);
		res.add(this.stockChocolat);
		return res;
	}

	public List<Variable> getParametres() {
		List<Variable> res=new ArrayList<Variable>();
		return res;
	}

	public List<Journal> getJournaux() {
		List<Journal> res=new ArrayList<Journal>();
		res.add(this.journal);
		return res;
	}

	public void setCryptogramme(Integer crypto) {
		this.cryptogramme = crypto;
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