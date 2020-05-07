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
	protected Map<String, Chocolat> abreviationChocolats;
	protected Map<String, Feve> abreviationFeves;
	
	protected Journal stocksLog;
	
	public AbsStock() {

		this.totalStocksChocolat=new Variable(getNom()+" total stocks chocolat", this, 0);
		
		stocksChocolat=new HashMap<Chocolat, Variable>();
		for (Chocolat choco : Chocolat.values()) {
			stocksChocolat.put(choco, new Variable(getNom() + " : STOCK " + choco.name(), this, 0));
		}
		
		stocksFeves = new HashMap<Feve, Variable>();
		for (Feve feve : Feve.values()) {
				stocksFeves.put(feve, new Variable(getNom() + " : STOCK " + feve.name(), this, 0));
		}
		
		abreviationChocolats = new HashMap<String, Chocolat>();
		abreviationChocolats.put("B", Chocolat.CHOCOLAT_BASSE );
		abreviationChocolats.put("M", Chocolat.CHOCOLAT_MOYENNE );
		abreviationChocolats.put("ME", Chocolat.CHOCOLAT_MOYENNE_EQUITABLE );
		abreviationChocolats.put("H", Chocolat.CHOCOLAT_HAUTE );
		abreviationChocolats.put("HE", Chocolat.CHOCOLAT_HAUTE_EQUITABLE );
		
		abreviationFeves = new HashMap<String, Feve>();
		abreviationFeves.put("B", Feve.FEVE_BASSE );
		abreviationFeves.put("M", Feve.FEVE_MOYENNE );
		abreviationFeves.put("ME", Feve.FEVE_MOYENNE_EQUITABLE );
		abreviationFeves.put("H", Feve.FEVE_HAUTE );
		abreviationFeves.put("HE", Feve.FEVE_HAUTE_EQUITABLE );
		
		stocksLog = new Journal("EQ7 Suivi des stocks", this);
		stocksLog.ajouter("EQ7 : Suivi des stocks de chocolats et de fèves");
		
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
		for (Chocolat choco : Chocolat.values()) {
			res.add(stocksChocolat.get(choco));
		}
		for (Feve feve : Feve.values()) {
			res.add(stocksFeves.get(feve));
		}
		return res;
	}

	@Override
	public List<Variable> getParametres() {
		return null;
	}

	@Override
	public List<Journal> getJournaux() {
		List<Journal> res = new ArrayList<Journal>();
		res.add(stocksLog);
		return res;
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
