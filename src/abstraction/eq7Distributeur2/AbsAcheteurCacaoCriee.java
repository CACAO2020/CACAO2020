package abstraction.eq7Distributeur2;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import abstraction.eq8Romu.produits.Feve;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;

public class AbsAcheteurCacaoCriee extends DistributeurChocolat implements IActeur {
	private static int NB_INSTANCES = 0; // Afin d'attribuer un nom different a toutes les instances
	private int numero;
	private Variable totalStocksFeves;
	protected Map<Feve, Double> stocksFeves;
	protected Integer cryptogramme;
	protected Journal journal;

	public AbsAcheteurCacaoCriee() {
		NB_INSTANCES++;
		this.numero=NB_INSTANCES;
		this.totalStocksFeves=new Variable(getNom()+" total stocks feves", this, 50);
		stocksFeves=new HashMap<Feve, Double>();
		for (Feve feve : Feve.values()) {
			stocksFeves.put(feve, 0.0);
		}
		this.journal = new Journal(this.getNom()+" activites", this);
	}
	
	public String getNom() {
		return "A.CacaoCriee"+numero;
	}

	public String getDescription() {
		return "Acheteur de cacao a la criee "+this.numero;
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
		for (Feve feve :Feve.values()) {
			if (stocksFeves.get(feve)!=null) {
				total=total+stocksFeves.get(feve);
			}
		}
		this.totalStocksFeves.setValeur(this, total);
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