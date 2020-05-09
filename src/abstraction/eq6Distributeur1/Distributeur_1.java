/** package abstraction.eq6Distributeur1;

import java.awt.Color;
import java.util.List;

import abstraction.eq8Romu.chocolatBourse.IAcheteurChocolatBourse;
import abstraction.eq8Romu.chocolatBourse.SuperviseurChocolatBourse;
import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.ChocolatDeMarque;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;

public class Distributeur_1 implements IActeur,IAcheteurChocolatBourse {
	private Journal journal;
	private Stock stock;
	private CompteBancaire soldeBancaire;
	private double marge;
	private Variable variablesolde;
	
	private double coutfixe;
	private double coutsdestockage;
	private double soldeDebutStep;
	private Distributeur_1 nous;
	
	public Distributeur_1(double marge, double soldeInitial) {
		
		this.marge=marge;
		this.coutfixe=0.2;
		this.coutsdestockage=0.01; 
		this.stock=new Stock();
		stock.ajouter(Chocolat.CHOCOLAT_HAUTE_EQUITABLE, 55000.0, this);
		stock.ajouter(Chocolat.CHOCOLAT_MOYENNE, 100000.0, this);
		stock.ajouter(Chocolat.CHOCOLAT_BASSE, 100000.0, this);
		
		this.soldeBancaire = new CompteBancaire(this.getNom(), this, soldeInitial);
		this.variablesolde=new Variable("Solde bancaire IMTermarché", this, soldeBancaire.getCompteBancaire());
		this.soldeDebutStep=this.soldeBancaire.getCompteBancaire();
		Filiere.LA_FILIERE.ajouterIndicateur(variablesolde);
		
		this.journal=new Journal("Journal"+this.getNom(),nous);
		Filiere.LA_FILIERE.ajouterJournal(this.journal);
		
	}
	
	/** @author Mélissa*/ /**
	public String getNom() {
		// TODO Auto-generated method stub
		return "EQ6 : IMTermarché";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void initialiser() {
		/**this.soldeDebutStep=this.soldeBancaire.getCompteBancaire();*/
		/**stock.initialisationDebutStep(stock.getStock());
		
	} */
/**
	@Override
	public void next() {
		this.soldeBancaire.retirer(this, ((this.soldeBancaire.getCompteBancaire()-this.soldeDebutStep)*this.coutfixe));
		this.variablesolde.retirer(this, ((this.soldeBancaire.getCompteBancaire()-this.soldeDebutStep)*this.coutfixe));
		//Prise en compte de la pérénité des stocks
		this.stock.perenniteStock(this.stock.getStock(), this);
		//Prise en compte du coût du stock
		this.soldeBancaire.retirer(this, this.stock.getStockTotal()*this.coutsdestockage);
		this.variablesolde.retirer(this, this.stock.getStockTotal()*this.coutsdestockage);
		
	}
// --------------------------------------------------------------------------------------------------------------------------//	
// ---------------------------------------------- Partie Acheteur en bourse -------------------------------------------------//
// --------------------------------------------------------------------------------------------------------------------------//
	
	@Override
	public double getDemande(Chocolat chocolat, double cours) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Integer getCryptogramme(SuperviseurChocolatBourse superviseur) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void notifierCommande(Chocolat chocolat, double quantiteObtenue, boolean payee) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receptionner(ChocolatDeMarque chocolat, double quantite) {
		// TODO Auto-generated method stub
		
	}

// --------------------------------------------------------------------------------------------------------------------------//	
// ---------------------------------------------- Partie Vendeur client final -----------------------------------------------//
// --------------------------------------------------------------------------------------------------------------------------//

	
		
	
	@Override
	public List<String> getNomsFilieresProposees() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Filiere getFiliere(String nom) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Variable> getIndicateurs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Variable> getParametres() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Journal> getJournaux() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCryptogramme(Integer crypto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notificationFaillite(IActeur acteur) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notificationOperationBancaire(double montant) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receptionner(Chocolat chocolat, double quantite) {
		// TODO Auto-generated method stub
		
	}
	
} */
