package abstraction.eq7Distributeur2;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.ChocolatDeMarque;
import abstraction.eq8Romu.produits.Feve;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;

public class AbsStock implements IActeur {

	protected Map<ChocolatDeMarque, Variable> stocksChocolatDeMarque;
	protected Map<Chocolat, Variable> stocksChocolat;
	
	protected Journal journal;
	
	protected Distributeur2 ac;
	
	public AbsStock(Distributeur2 ac) {

		this.ac = ac;
		
		stocksChocolatDeMarque=new HashMap<ChocolatDeMarque, Variable>();
		stocksChocolat=new HashMap<Chocolat, Variable>();

		for (Chocolat choco : Chocolat.values()) {
			stocksChocolat.put(choco, new Variable(ac.getNom() + " : STOCK " + choco.name(), ac, 0));
		}
		
		this.journal = new Journal(ac.getNom() + " Stocks " + ac.getNumero(), ac);
		this.journal.ajouter("EQ7 : Suivi des stocks de chocolats et de fèves");
		
	}
		
	public List<Variable> getIndicateurs() {
		List<Variable> res=new ArrayList<Variable>();
		for (Chocolat choco : Chocolat.values()) {
			res.add(stocksChocolat.get(choco));
		}
		for (ChocolatDeMarque choco : stocksChocolatDeMarque.keySet()) {
			res.add(stocksChocolatDeMarque.get(choco));
		}
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

	public void initialiser() {
		
	}

	public void next() {
		
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

}
