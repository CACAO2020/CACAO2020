package abstraction.eq7Distributeur2;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.Feve;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;

public class AbsStock implements IActeur {

	private Variable totalStocksChocolat;
	protected Map<Chocolat, Variable> stocksChocolat;
	protected Map<Feve, Variable> stocksFeves;
	
	public AbsStock() {

		this.totalStocksChocolat=new Variable(getNom()+" total stocks chocolat", this, 0);
		
		stocksChocolat=new HashMap<Chocolat, Variable>();
		for (Chocolat choco : Chocolat.values()) {
			stocksChocolat.put(choco, new Variable(getNom() + " : " + choco.name(), this, 0));
		}
		
		stocksFeves = new HashMap<Feve, Variable>();
		for (Feve feve : Feve.values()) {
				stocksFeves.put(feve, new Variable(getNom() + " : " + feve.name(), this, 0));
		}
		
	}
	
	public String getNom() {
		return "Stock EQ7";
	}

	@Override
	public String getDescription() {
		return null;
	}

	@Override
	public Color getColor() {
		return null;
	}

	@Override
	public void initialiser() {
		
	}

	@Override
	public void next() {
		
	}

	@Override
	public List<String> getNomsFilieresProposees() {
		return null;
	}

	@Override
	public Filiere getFiliere(String nom) {
		return null;
	}

	@Override
	public List<Variable> getIndicateurs() {
		List<Variable> res=new ArrayList<Variable>();
		return res;
	}

	@Override
	public List<Variable> getParametres() {
		return null;
	}

	@Override
	public List<Journal> getJournaux() {
		return null;
	}

	@Override
	public void setCryptogramme(Integer crypto) {
		
	}

	@Override
	public void notificationFaillite(IActeur acteur) {
		
	}

	@Override
	public void notificationOperationBancaire(double montant) {
		
	}
}
