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

public class AbsAcheteurCacaoCriee implements IActeur {
	private static int NB_INSTANCES = 0; // Afin d'attribuer un nom different a toutes les instances
	private int numero;
	protected Integer cryptogramme;
	protected Journal journal;
	protected Stock stock;

	public AbsAcheteurCacaoCriee(Stock stock) {
		NB_INSTANCES++;
		this.numero=NB_INSTANCES;
		this.stock = stock;
		this.journal = new Journal(this.getNom()+" activites3"+ this.numero, this);
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
		this.stock.stocksChocolat.get(stock.stringToChoco("H")).setValeur(this, 10.);
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