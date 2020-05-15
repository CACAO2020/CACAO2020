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

	protected Map<ChocolatDeMarque, Variable> stocksChocolat;
	protected Map<Feve, Variable> stocksFeves;
	
	protected Journal journal;
	
	protected Distributeur2 ac;
	
	public AbsStock(Distributeur2 ac) {

		this.ac = ac;
		
		stocksChocolat=new HashMap<ChocolatDeMarque, Variable>();
		//for (ChocolatDeMarque choco : Chocolat.values()) {
		//	stocksChocolat.put(choco, new Variable(getNom() + " : STOCK " + choco.name(), ac, 0));
		//}
		
		stocksFeves = new HashMap<Feve, Variable>();
		for (Feve feve : Feve.values()) {
				stocksFeves.put(feve, new Variable(getNom() + " : STOCK " + feve.name(), ac, 0));
		}
		
		this.journal = new Journal(getNom() + " Stocks " + ac.getNumero(), ac);
		this.journal.ajouter("EQ7 : Suivi des stocks de chocolats et de fèves");
		
	}
	
	public String getNom() {
		return "EQ7";
	}
	public List<Variable> getIndicateurs() {
		List<Variable> res=new ArrayList<Variable>();
		for (ChocolatDeMarque choco : stocksChocolat.keySet()) {
			res.add(stocksChocolat.get(choco));
		}
		for (Feve feve : Feve.values()) {
			res.add(stocksFeves.get(feve));
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

	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	public Color getColor() {
		// TODO Auto-generated method stub
		return null;
	}

	public void initialiser() {
		// TODO Auto-generated method stub
		
	}

	public void next() {
		// TODO Auto-generated method stub
		
	}

	public List<String> getNomsFilieresProposees() {
		// TODO Auto-generated method stub
		return null;
	}

	public Filiere getFiliere(String nom) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setCryptogramme(Integer crypto) {
		// TODO Auto-generated method stub
		
	}

	public void notificationFaillite(IActeur acteur) {
		// TODO Auto-generated method stub
		
	}

	public void notificationOperationBancaire(double montant) {
		// TODO Auto-generated method stub
		
	}

}
