package abstraction.eq7Distributeur2;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.ChocolatDeMarque;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;

public class AbsVendeur {

	// Référence à l'acteur principal
	protected Distributeur2 ac;

	// Journal principal
	protected Journal journal;
	
	// Journal du catalogue
	protected Journal journalCatalogue;
	
	// Enregistre les quantités de chaque chocolat de marque à commander
	protected Map<ChocolatDeMarque, Variable> quantitesACommanderParContrats;
	protected Map<Chocolat, Variable> quantitesACommanderEnBourse;	
	
	// Enregistre les quantités de chaque chocolat de marque mis en vente
	protected Map<ChocolatDeMarque, Variable> quantitesEnVente;
	
	protected Map<Chocolat, Variable> quantitesEnVenteChoco;
	
	// Enregistre les prix de vente de chaque chocolat de marque
	protected Map<Chocolat, Variable> prixChoco;
	
	// Enregistre les quantités de chaque type de chocolat vendues
	protected Map<Chocolat, Variable> chocoVendu;
	
	// Prix par défaut à appliquer à chaque type de chocolat
	protected Map<Chocolat, Double> prixParDefaut;
	
	// Quantité par défaut à vendre pour chaque chocolat de marque
	protected double quantiteAVendreParDefaut;
	
	// Enregistre les quantités de chaque type de chocolat vendues à l'étape courante
	protected Map<Chocolat, Double> ventesEtapeActuelle;
	
	// Liste des chocolats de marque proposés au vendeur
	protected List<ChocolatDeMarque> produitsCatalogue;
	
	// Liste des chocolats de marque pour lesquels on souhaite réaliser une campagne de publicité
	protected List<ChocolatDeMarque> publicites;
	
	// Liste des prix de ventes unitaires pour chaque marque, à chaque tours
	protected Map<ChocolatDeMarque, Double> coutUnitaire;
	
	// Marqueur de la panique, lorsqu'il est activé le vendeur panique et essaie d'écouler tous ses stocks à prix élevés
	protected boolean panik;
	
	// Le mode panik était actif au tour précédent !
	protected boolean wasPanik;
	
	// Marqueur du calme, lorsqu'il est activé le vendeur reste calme
	protected boolean kalm;
	
	// Le mode kalm était actif au tour précédent !
	protected boolean wasKalm;
	
	// Nombre de pubs en cours
	protected int compteurPub;
	
	// Stocke s'il l'on a fait de la pub au dernier step
	protected boolean pubLastStep;
	
	// Stocke les quantites vendues à l'étape précédente
	protected List<Double> quantitesVendues;
	
	// Mode actuel de l'acteur (normal, panik ou kalm)
	protected String modeActuel = "normal";
	
	// Pourcentages de marge en fonction du mode de l'acteur
	protected Map<String, Map<Chocolat, Double>> pourcentagesMarge; 
		
	// Couleurs d'arrière-plan pour les messages des journaux
	public Color titleColor = Color.BLACK;
	public Color metaColor = Color.CYAN;
	public Color alertColor = Color.RED;
	public Color warningColor = Color.ORANGE;
	public Color positiveColor = Color.GREEN;
	public Color descriptionColor = Color.YELLOW;
	
	public AbsVendeur(Distributeur2 ac) {	
		this.wasPanik = false;
		this.panik = false;
		pourcentagesMarge = new HashMap<String, Map<Chocolat, Double>>();
		Map<Chocolat, Double> pourcentagesMargeKalm = new HashMap<Chocolat, Double>();
		Map<Chocolat, Double> pourcentagesMargeNormal = new HashMap<Chocolat, Double>();
		Map<Chocolat, Double> pourcentagesMargePanik = new HashMap<Chocolat, Double>();
		
		pourcentagesMargeKalm.put(Chocolat.CHOCOLAT_MOYENNE, 10.);
		pourcentagesMargeKalm.put(Chocolat.CHOCOLAT_MOYENNE_EQUITABLE, 5.);
		pourcentagesMargeKalm.put(Chocolat.CHOCOLAT_HAUTE, 20.);
		pourcentagesMargeKalm.put(Chocolat.CHOCOLAT_HAUTE_EQUITABLE, 15.);
		
		pourcentagesMargeNormal.put(Chocolat.CHOCOLAT_MOYENNE, 20.);
		pourcentagesMargeNormal.put(Chocolat.CHOCOLAT_MOYENNE_EQUITABLE, 10.);
		pourcentagesMargeNormal.put(Chocolat.CHOCOLAT_HAUTE, 40.);
		pourcentagesMargeNormal.put(Chocolat.CHOCOLAT_HAUTE_EQUITABLE, 30.);
		
		pourcentagesMargePanik.put(Chocolat.CHOCOLAT_MOYENNE, 40.);
		pourcentagesMargePanik.put(Chocolat.CHOCOLAT_MOYENNE_EQUITABLE, 20.);
		pourcentagesMargePanik.put(Chocolat.CHOCOLAT_HAUTE, 80.);
		pourcentagesMargePanik.put(Chocolat.CHOCOLAT_HAUTE_EQUITABLE, 60.);
		
		pourcentagesMarge.put("panik", pourcentagesMargePanik);
		pourcentagesMarge.put("kalm", pourcentagesMargeKalm);
		pourcentagesMarge.put("normal", pourcentagesMargeNormal);
		
		quantitesVendues = new ArrayList<Double>();
		this.ac = ac;
		produitsCatalogue = new ArrayList<ChocolatDeMarque>();
		publicites = new ArrayList<ChocolatDeMarque>();
		quantitesEnVente = new HashMap<ChocolatDeMarque, Variable>();
		prixChoco = new HashMap<Chocolat, Variable>();
		quantitesACommanderParContrats = new HashMap<ChocolatDeMarque, Variable>();
		quantitesACommanderEnBourse = new HashMap<Chocolat, Variable>();
		ventesEtapeActuelle = new HashMap<Chocolat, Double>();
		prixParDefaut = new HashMap<Chocolat, Double>();
		prixParDefaut.put(Chocolat.CHOCOLAT_MOYENNE, 10000.);
		prixParDefaut.put(Chocolat.CHOCOLAT_MOYENNE_EQUITABLE, 14000.);
		prixParDefaut.put(Chocolat.CHOCOLAT_HAUTE, 15000.);
		prixParDefaut.put(Chocolat.CHOCOLAT_HAUTE_EQUITABLE, 20000.);
		chocoVendu = new HashMap<Chocolat, Variable>();
		for (Chocolat choco : ac.nosChoco) {
			ventesEtapeActuelle.put(choco, 0.);
			chocoVendu.put(choco, new Variable(getNom() + " : " + choco.name() + " [Ventes i-1]", ac, 0.));	
		}
		initJournaux();
	}
	
	// Initialise les journaux
	public void initJournaux() {
		journal = new Journal(this.getNom() + " : Distributeur Chocolat", ac);
		journal.ajouter(Journal.texteColore(titleColor, Color.WHITE, this.getNom() + " : Distributeur Chocolat"));
		journal.ajouter(Journal.texteColore(descriptionColor, Color.BLACK, "Ce journal mémorise les activités du distributeur, vendeur de chocolat au client"));
		journal.ajouter(Journal.texteColore(descriptionColor, Color.BLACK, "final. Il affiche les ventes effectuées et les alertes lorsqu'un rayon est vide."));
		
		journalCatalogue = new Journal(ac.getNom() + " : Catalogue du distributeur", ac);
		journalCatalogue.ajouter(Journal.texteColore(titleColor, Color.WHITE, this.getNom() + " : Catalogue du distributeur"));
		journalCatalogue.ajouter(Journal.texteColore(descriptionColor, Color.BLACK, "Ce journal présente le contenu du catalogue du distributeur à chaque étape :"));
		journalCatalogue.ajouter(Journal.texteColore(descriptionColor, Color.BLACK, "types de chocolat mis en vente, quantités disponibles à la vente et prix à l'unité."));
		journalCatalogue.ajouter(Journal.texteColore(descriptionColor, Color.BLACK, "Il est mis à jour à chaque appel de la commande next() du distributeur, c'est pourquoi"));
		journalCatalogue.ajouter(Journal.texteColore(descriptionColor, Color.BLACK, "le catalogue à une étape donnée n'est visible qu'à partir du passage à l'étape suivante."));
		journalCatalogue.ajouter(Journal.texteColore(descriptionColor, Color.BLACK, "L'état du catalogue à une étape correspond à celui proposé au client lors de cette étape."));
	}
	
	// Renvoie les indicateurs
	public List<Variable> getIndicateurs() {
		List<Variable> res = new ArrayList<Variable>();
		for (Chocolat choco : ac.nosChoco) {
			res.add(chocoVendu.get(choco));
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
		res.add(journalCatalogue);
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

}