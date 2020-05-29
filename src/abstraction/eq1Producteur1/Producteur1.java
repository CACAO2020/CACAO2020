package abstraction.eq1Producteur1;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;
import abstraction.eq8Romu.cacaoCriee.IVendeurCacaoCriee;
import abstraction.eq8Romu.cacaoCriee.LotCacaoCriee;
import abstraction.eq8Romu.cacaoCriee.PropositionCriee;
import abstraction.eq8Romu.produits.Feve;
import abstraction.fourni.Filiere;

/*
 * Précision sur la méthode de répartition des fichiers sources
 * La méthode pour l'instant retenu est l'agrégation de classes annexes (vente de cacao, gestion de stock...)
 * qui permetteront d'organiser le code dans plusieurs fichiers
 */

public class Producteur1 implements IActeur, IVendeurCacaoCriee {

	private Variable stockFevesForastero;
	private Variable stockFevesTrinitario;
	private Integer cryptogramme;
	private Journal journalEq1;
	private GestionCriee venteCriee;
	private Plantations plantation;

	public Producteur1() {
		this.stockFevesForastero=new Variable(getNom()+" stock feves Forastero", this, 0, 10000, 1000);
		this.stockFevesTrinitario=new Variable(getNom()+" stock feves Trinitario", this, 0, 10000, 1000);
		this.journalEq1 = new Journal("Eq1 activites", this);
		this.venteCriee = new GestionCriee(this);
		this.plantation = new Plantations();
	}

	public void setCryptogramme(Integer crypto) {
		this.cryptogramme = crypto;
	}
	
	private Integer getCryptogramme()
	{
		return this.cryptogramme;
	}

	public String getNom() {
		return "EQ1";
	}

	public String getDescription() {
		return "Producteur 1 : Producteur de feves Forastero et Trinitario";
	}

	public Color getColor() {
		return new Color(26, 188, 156);
	}

	public void initialiser() {
		this.plantation.initialiserArbres(10000, 2000);
	}
	
	// Modifiee par Melanie pour l'ajout des differents stocks de feves
	public double getStock(Feve typeFeve)
	{	
		if(typeFeve == Feve.FEVE_BASSE)
		{
			return this.stockFevesForastero.getValeur();
		}
		if(typeFeve == Feve.FEVE_BASSE)
		{
			return this.stockFevesTrinitario.getValeur();
		}
		return 0;
	}
	
	
	// Modifiee par Melanie pour l'ajout des differents stocks de feves
	public void next() {
		// Ecriture de l'état dans les logs.
		this.journalEq1.ajouter("Quantité de stock de Trinitario : " + this.getStock(Feve.FEVE_MOYENNE));
		this.journalEq1.ajouter("Quantité de stock de Forastero : " + this.getStock(Feve.FEVE_BASSE));
	}

	// Modification pour ajout de la filiere TestCrieeProd1
	// <-- Melanie
	
	public List<String> getNomsFilieresProposees() {
		ArrayList<String> filieres = new ArrayList<String>();
		filieres.add("TESTCRIEEPROD1");
		return(filieres);
		
	}

	public Filiere getFiliere(String nom) {
		if (nom.equals("TESTCRIEEPROD1")) {
			return new FiliereTestCrieeProd1();
		}
		else {
			return null;
		}
	}
	
	// -->
	
	// Melanie = ajout des stocks de chaque feves

	public List<Variable> getIndicateurs() {
		List<Variable> res=new ArrayList<Variable>();
		res.add(this.stockFevesForastero);
		res.add(this.stockFevesTrinitario);
		return res;
	}

	public List<Variable> getParametres() {
		List<Variable> res=new ArrayList<Variable>();
		return res;
	}

	public List<Journal> getJournaux() {
		List<Journal> res=new ArrayList<Journal>();
		res.add(this.journalEq1);
		return res;
	}

	public void notificationFaillite(IActeur acteur) {
		if (this==acteur) {
			System.out.println("I'll be back... or not... "+this.getNom());
		} else {
			System.out.println("Poor "+acteur.getNom()+"... We will miss you. "+this.getNom());
		}
	}

	public void notificationOperationBancaire(double montant) {
	}


	// Fonctions de vente de cacao a la criee.
	//[+] < Clément
	public LotCacaoCriee getLotEnVente() 
	{
		return venteCriee.getLotEnVente();
	}

	@Override
	public void notifierAucuneProposition(LotCacaoCriee lot) {
		venteCriee.notifierAucuneProposition(lot);
		
	}

	@Override
	public PropositionCriee choisir(List<PropositionCriee> propositions) {
		return venteCriee.choisir(propositions);
	}

	@Override
	public void notifierVente(PropositionCriee proposition) {
		venteCriee.notifierVente(proposition);
	}

	//Fonction pour les classes agréger pour ajouter des entrées au journaux
	public void ajouterJournaux(String notification)
	{
		this.journalEq1.ajouter(notification);
	}

	//[-] Clément >


		//Fonctions pour la gestion du stock
	// --< Melanie
	
	public void setStock(double valeur, Feve typeFeve) {
		this.setStock(valeur, typeFeve);
	}
	
	/**
	 * Ajoute la quantité de stock augmentation à la valeur
	 * deja existante
	 */
	
	public void addStock(double augmentation, Feve typeFeve) {
		
		if(typeFeve == Feve.FEVE_BASSE)
		{
			stockFevesForastero.setValeur(this, this.stockFevesForastero.getValeur() + augmentation);
		}
		if(typeFeve == Feve.FEVE_MOYENNE)
		{
			stockFevesTrinitario.setValeur(this, this.stockFevesTrinitario.getValeur() + augmentation);
		}
		
		
	}
	
	/**
	 * Enleve la quantité de stock diminution à la valeur
	 * deja existante
	 */
	
	public void removeStock(double diminution, Feve typeFeve) {
		this.addStock(-1*diminution, typeFeve);		
	}
	
	// -->
}
