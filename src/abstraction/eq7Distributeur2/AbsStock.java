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

public class AbsStock {

	protected Map<ChocolatDeMarque, Variable> stocksChocolatDeMarque;
	protected Map<Chocolat, Variable> stocksChocolat;
	protected Map<Chocolat, Variable> chocoReceptionne;
	
	protected Journal journal;
	
	protected Distributeur2 ac;
	
	public Color titleColor = Color.BLACK;
	public Color metaColor = Color.CYAN;
	public Color alertColor = Color.RED;
	public Color addStockColor = Color.GREEN;
	public Color removeStockColor = Color.ORANGE;
	public Color descriptionColor = Color.YELLOW;
	
	public AbsStock(Distributeur2 ac) {

		this.ac = ac;
		stocksChocolatDeMarque=new HashMap<ChocolatDeMarque, Variable>();
		stocksChocolat=new HashMap<Chocolat, Variable>();
		chocoReceptionne = new HashMap<Chocolat, Variable>();
		initJournaux();

		for (Chocolat choco : ac.nosChoco) {
			stocksChocolat.put(choco, new Variable(getNom() + " : " + choco.name() + " [Stock i]", ac, 0));
			journal.ajouter(Journal.texteColore(metaColor, Color.BLACK,"[CRÉATION] Création d'un stock pour le " + choco + "."));
			chocoReceptionne.put(choco, new Variable(getNom() + " : " + choco.name() + " [Réception i-1]", ac, 0));
		}
	}
		
	public void initJournaux() {
		this.journal = new Journal(getNom() + " : Stocks", ac);
		this.journal.ajouter(Journal.texteColore(titleColor, Color.WHITE, this.getNom() + " : Suivi des stocks de chocolat"));
		journal.ajouter(Journal.texteColore(descriptionColor, Color.BLACK, "Les stocks du distributeur sont enregistrés dans ce journal."));
		journal.ajouter(Journal.texteColore(descriptionColor, Color.BLACK, "On y retrouve les créations de stock (correspondant à la première"));
		journal.ajouter(Journal.texteColore(descriptionColor, Color.BLACK, "entrée d'un type de chocolat), les entrées et les sorties de stock."));
	}
	
	public List<Variable> getIndicateurs() {
		List<Variable> res=new ArrayList<Variable>();
		for (Chocolat choco : ac.nosChoco) {
			res.add(stocksChocolat.get(choco));
		}
		for (Chocolat choco : ac.nosChoco) {
			res.add(chocoReceptionne.get(choco));
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
	
	public int getNumero() {
		return ac.getNumero();
	}

}
