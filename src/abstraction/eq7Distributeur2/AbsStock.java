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

	//idée : enlever stocksChocolat pour ne garder que stocksChocolatDeMarque
	//initialiser stocksChocolatDeMarque avec des stocks de chaque couple (Chocolat, Marque (=String)) à 0
	//faut-il afficher tous les indicateurs correspondants ? (on achète du chocolat aux équipes 1, 2, 3, 4 et 5, 
	//reste à faire la liste des types de chocolat qu'ils vendent)
	//si on veut la quantité total de chocolat HGE p.ex., on fait appel à une fonction getStockChocolat qui calcule la somme
	//des stocks de ce type de chocolat pour chaque marque.
	
	protected Map<Chocolat, Variable> stocksChocolat; 
	protected Map<ChocolatDeMarque, Variable> stocksChocolatDeMarque;
	protected Map<Feve, Variable> stocksFeves;
	protected Map<String, Chocolat> abreviationChocolats;
	protected Map<String, Feve> abreviationFeves;
	
	protected Journal journal;
	
	protected Distributeur2 ac;
	
	public AbsStock(Distributeur2 ac) {

		this.ac = ac;
		
		stocksChocolat=new HashMap<Chocolat, Variable>();
		for (Chocolat choco : Chocolat.values()) {
			stocksChocolat.put(choco, new Variable(getNom() + " : STOCK " + choco.name(), ac, 0));
		}
		
		stocksFeves = new HashMap<Feve, Variable>();
		for (Feve feve : Feve.values()) {
				stocksFeves.put(feve, new Variable(getNom() + " : STOCK " + feve.name(), ac, 0));
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
		
		this.journal = new Journal(getNom() + " Stocks " + ac.numero, ac);
		this.journal.ajouter("EQ7 : Suivi des stocks de chocolats et de fèves");
		
	}
	
	public String getNom() {
		return "EQ7";
	}
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

	public List<Variable> getParametres() {
		return null;
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
