package abstraction.eq8Romu.clients;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import abstraction.eq8Romu.produits.ChocolatDeMarque;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;

public class ExempleAbsDistributeurChocolatMarque implements IActeur {
	private static int NB_INSTANCES = 0; // Afin d'attribuer un nom different a toutes les instances
	protected int numero;
	protected Variable stockChocolat;
	protected Integer cryptogramme;
	protected ChocolatDeMarque chocolat;
	protected Journal journal;

	public ExempleAbsDistributeurChocolatMarque(ChocolatDeMarque choco) {	
		if (choco==null) {
			throw new IllegalArgumentException("creation d'une instance de ExempleAbsDistributeurChocolatMarqe avec choco==null");
		}		
		NB_INSTANCES++;
		this.numero=NB_INSTANCES;
		this.chocolat = choco;
		this.stockChocolat=new Variable(getNom()+" stock "+choco.name(), this, 0, 1000000000, 1000000);
		this.journal = new Journal(this.getNom()+" activites", this);
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
		journal.ajouter("La consommation annuelle des clients est de "+Filiere.LA_FILIERE.getIndicateur("CLIENTFINAL consommation annuelle").getValeur());
	}

	public void next() {
		journal.ajouter("Etape="+Filiere.LA_FILIERE.getEtape());
		if (Filiere.LA_FILIERE.getEtape()>=1) {
			journal.ajouter("Le prix moyen du chocolat \""+chocolat.name()+"\" a l'etape precedente etait de "+Filiere.LA_FILIERE.prixMoyenEtapePrecedente(chocolat));
		}
		journal.ajouter("Les ventes de chocolat \""+chocolat.name()+" il y a un an etaient de "+Filiere.LA_FILIERE.getVentes(Filiere.LA_FILIERE.getEtape()-24, chocolat));
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