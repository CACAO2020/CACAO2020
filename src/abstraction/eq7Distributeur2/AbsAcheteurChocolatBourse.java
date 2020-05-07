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

public class AbsAcheteurChocolatBourse extends AcheteurCacaoCriee implements IActeur {
	private static int NB_INSTANCES = 0; // Afin d'attribuer un nom different a toutes les instances
	private int numero;
	private Variable totalStocksChocolat;
	protected Map<Chocolat, Variable> stocksChocolat;
	protected Integer cryptogramme;
	protected Journal journal;

	public AbsAcheteurChocolatBourse() {
		NB_INSTANCES++;
		this.numero=NB_INSTANCES;
		this.totalStocksChocolat=new Variable(getNom()+" total stocks chocolat", this, 0);
		stocksChocolat=new HashMap<Chocolat, Variable>();
		for (Chocolat choco : Chocolat.values()) {
			stocksChocolat.put(choco, new Variable(getNom()+" stock "+choco.name(), this, 0));
		}
		this.journal = new Journal(this.getNom()+" activites", this);
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
		double total=0.0;
		for (Chocolat choco :Chocolat.values()) {
			if (stocksChocolat.get(choco)!=null) {
				total=total+stocksChocolat.get(choco).getValeur();
			}
		}
		this.totalStocksChocolat.setValeur(this, total);
	}

	public List<String> getNomsFilieresProposees() {
		return new ArrayList<String>();
	}

	public Filiere getFiliere(String nom) {
		return null;
	}
	
	public List<Variable> getIndicateurs() {
		List<Variable> res = super.getIndicateurs();
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
	
	public void notificationFaillite(IActeur acteur) {
	}
	
	public void notificationOperationBancaire(double montant) {
	}
}
