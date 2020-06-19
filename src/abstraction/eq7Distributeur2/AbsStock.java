package abstraction.eq7Distributeur2;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.ChocolatDeMarque;
import abstraction.eq8Romu.produits.Feve;
import abstraction.eq8Romu.contratsCadres.Echeancier;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;

public class AbsStock {

	// Enregistre les stocks de chaque chocolat de marque
	protected Map<ChocolatDeMarque, Variable> stocksChocolatDeMarque;
	
	// Une table pour tenir compte des quantités achetée à chaque étape (NE PAS TOUCHER, TRAVAIL EN COURS)
	protected Map<Integer, Map<ChocolatDeMarque, Double>> chocoEnStockParEtape;
	protected Map<ChocolatDeMarque, Integer> etapeDuPlusVieuxStock;
	
	// Enregistre les stocks de chaque type de chocolat
	protected Map<Chocolat, Variable> stocksChocolat;
	
	// Enregistre les quantités de chocolat réceptionnés à chaque étape
	protected Map<Chocolat, Variable> chocoReceptionne;
	
	// Journal principal
	protected Journal journal;
	
	// Référence à l'acteur principal
	protected Distributeur2 ac;
	
	// Couleurs d'arrière-plan pour les messages des journaux
	public Color titleColor = Color.BLACK;
	public Color metaColor = Color.CYAN;
	public Color alertColor = Color.RED;
	public Color addStockColor = Color.GREEN;
	public Color removeStockColor = Color.ORANGE;
	public Color descriptionColor = Color.YELLOW;
	public Color peremptionColor = Color.MAGENTA;
	
	protected int datePeremption = 12; // donc une date de péremption de 6 mois car 12 steps
	protected int fraisUnitairesStockage = 1;
	protected double stockLimite = 100.;
	
	public AbsStock(Distributeur2 ac) {
		this.ac = ac;
		stocksChocolatDeMarque=new HashMap<ChocolatDeMarque, Variable>();
		chocoEnStockParEtape=new HashMap<Integer, Map<ChocolatDeMarque, Double>>();
		etapeDuPlusVieuxStock = new HashMap<ChocolatDeMarque, Integer>();
		stocksChocolat=new HashMap<Chocolat, Variable>();
		chocoReceptionne = new HashMap<Chocolat, Variable>();
		initJournaux();
		for (Chocolat choco : ac.nosChoco) {
			stocksChocolat.put(choco, new Variable(getNom() + " : " + choco.name() + " [Stock i]", ac, 0));
			journal.ajouter(Journal.texteColore(metaColor, Color.BLACK,"[CRÉATION] Création d'un stock pour le " + choco + "."));
			chocoReceptionne.put(choco, new Variable(getNom() + " : " + choco.name() + " [Réception i-1]", ac, 0));
		}
	}
	
	// Initialise les journaux
	public void initJournaux() {
		this.journal = new Journal(getNom() + " : Stocks", ac);
		this.journal.ajouter(Journal.texteColore(titleColor, Color.WHITE, this.getNom() + " : Suivi des stocks de chocolat"));
		journal.ajouter(Journal.texteColore(descriptionColor, Color.BLACK, "Les stocks du distributeur sont enregistrés dans ce journal."));
		journal.ajouter(Journal.texteColore(descriptionColor, Color.BLACK, "On y retrouve les créations de stock (correspondant à la première"));
		journal.ajouter(Journal.texteColore(descriptionColor, Color.BLACK, "entrée d'un type de chocolat), les entrées et les sorties de stock."));
	}
	
	// Renvoie les indicateurs
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

	// Renvoie les paramètres
	public List<Variable> getParametres() {
		List<Variable> res=new ArrayList<Variable>();
		return res;
	}

	// Renvoie les journaux
	public List<Journal> getJournaux() { 
		List<Journal> res = new ArrayList<Journal>();
		res.add(journal);
		return res;
	}
	
	// Méthodes renvoyant aux méthodes de l'acteur principal
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
