package abstraction.eq7Distributeur2;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import abstraction.eq8Romu.chocolatBourse.IAcheteurChocolatBourse;
import abstraction.eq8Romu.chocolatBourse.IVendeurChocolatBourse;
import abstraction.eq8Romu.chocolatBourse.SuperviseurChocolatBourse;
import abstraction.eq8Romu.clients.ClientFinal;
import abstraction.eq8Romu.clients.IDistributeurChocolatDeMarque;
import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.ChocolatDeMarque;
import abstraction.eq8Romu.produits.Feve;
import abstraction.eq8Romu.produits.Gamme;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;
import abstraction.eq7Distributeur2.*;


public class Distributeur2 extends AbsDistributeur2 implements IActeur, IAcheteurChocolatBourse, IDistributeurChocolatDeMarque {
	
	private static int NB_INSTANCES = 0;
	public int numero;
	private int cryptogramme;
	
	protected double soldeCritique;
	
	private AcheteurChocolat acheteurChocolat;
	private DistributeurChocolat distributeurChocolat;
	private Stock stock;
	
	private Journal journal;
	private Journal journalTransactions;
	
	public Distributeur2() {
		super();
		NB_INSTANCES++;
		numero = NB_INSTANCES;
		soldeCritique = 2.;
		acheteurChocolat = new AcheteurChocolat(this);
		distributeurChocolat = new DistributeurChocolat(this);
		stock = new Stock(this);
		initJournaux();
	}
	
	public void initJournaux() {
		journal = new Journal(getNom() + " : Informations générales", this);
		journal.ajouter(Journal.texteColore(titleColor, Color.WHITE, "EQ7 : Journal d'activités"));
		journal.ajouter(Journal.texteColore(descriptionColor, Color.BLACK, "Ce journal rapporte les informations majeures concernant"));
		journal.ajouter(Journal.texteColore(descriptionColor, Color.BLACK, "l'acteur (changement de stratégie, faillite, ...)."));
		journalTransactions = new Journal(getNom() + " : Transactions bancaires", this);
		journalTransactions.ajouter(Journal.texteColore(titleColor, Color.WHITE, "EQ7 : Transactions bancaires"));
		journalTransactions.ajouter(Journal.texteColore(descriptionColor, Color.BLACK, "Ce journal regroupe toutes les opérations bancaires effectuées"));
		journalTransactions.ajouter(Journal.texteColore(descriptionColor, Color.BLACK, "par l'acteur (débits et crédits)."));
	}
	 
	public int getNumero() {
		return this.numero; 
	}
	
	public AcheteurChocolat getAcheteurChocolat() {
		return this.acheteurChocolat;
	}
	
	public DistributeurChocolat getDistributeurChocolat() {
		return this.distributeurChocolat;
	}
	
	public Stock getStock() {
		return this.stock;
	}
		
	public String getNom() {
		return "EQ7";
	}

	public String getDescription() {
		return "Distributeur Ecocoa de Liège, chocolatier responsable";
	}

	public Color getColor() {
		return new Color(240, 195, 15);
	}

	public void initialiser() {
		acheteurChocolat.initialiser();
		distributeurChocolat.initialiser();
		stock.initialiser();
	}
	
	public void next() {
		distributeurChocolat.next();
		acheteurChocolat.next();
		stock.next();
	}
	
	public double getSolde() {
		return Filiere.LA_FILIERE.getBanque().getSolde(Filiere.LA_FILIERE.getActeur(getNom()), this.cryptogramme);
	}

	public List<String> getNomsFilieresProposees() {
		return new ArrayList<String>();
	}

	public Filiere getFiliere(String nom) {
		return Filiere.LA_FILIERE;
	}

	public List<Variable> getIndicateurs() {
		List<Variable> res = new ArrayList<Variable>();
		res.addAll(stock.getIndicateurs());
		res.addAll(acheteurChocolat.getIndicateurs());
		res.addAll(distributeurChocolat.getIndicateurs());
		return res;
	}

	public List<Variable> getParametres() {
		List<Variable> res=new ArrayList<Variable>();
		res.addAll(stock.getParametres());
		res.addAll(acheteurChocolat.getParametres());
		res.addAll(distributeurChocolat.getParametres());
		return res;
	}

	public List<Journal> getJournaux() {
		List<Journal> res=new ArrayList<Journal>();
		res.add(journal);
		res.add(journalTransactions);
		res.addAll(stock.getJournaux());
		res.addAll(acheteurChocolat.getJournaux());
		res.addAll(distributeurChocolat.getJournaux());
		return res;
	}

	public void notificationFaillite(IActeur acteur) {
		if (this==acteur) {
			System.out.println("I'll be back... or not... "+this.getNom());
			journal.ajouter(Journal.texteColore(alertColor, Color.BLACK, "[FAILLITE] Cet acteur a fait faillite !"));
		} else {
			System.out.println("Poor "+acteur.getNom()+"... We will miss you. "+this.getNom());
			journal.ajouter(Journal.texteColore(alertColor, Color.BLACK, "[FAILLITE] " + acteur.getNom() + " a fait faillite !"));
		}
	}	

	public void notificationOperationBancaire(double montant) {
		if (montant > 0) {
			journalTransactions.ajouter(Journal.texteColore(positiveColor, Color.BLACK, "[CRÉDIT] Crédit d'une valeur de : " + Journal.doubleSur(montant,2) + "."));
		} else {
			journalTransactions.ajouter(Journal.texteColore(warningColor, Color.BLACK, "[DÉBIT] Débit d'une valeur de : " + Journal.doubleSur(-montant,2) + "."));
		}
	}
	
	public void notificationSolde() {
		double solde = getSolde();
		if (solde > soldeCritique) {
			journal.ajouter(Journal.texteColore(positiveColor, Color.BLACK, "[SOLDE] Solde après vente : " + Journal.doubleSur(solde,2) + "."));
		} else {
			if (solde > 0) {
				journal.ajouter(Journal.texteColore(warningColor, Color.BLACK, "[SOLDE] Solde actuel : " + Journal.doubleSur(solde,2) + "."));
			} else {
				journal.ajouter(Journal.texteColore(alertColor, Color.BLACK, "[SOLDE] Solde actuel : " + Journal.doubleSur(solde,2) + "."));
			}
		}
	}

	// Méthodes de l'acheteur de chocolat à la bourse
	
	public void setCryptogramme(Integer crypto) {
		this.cryptogramme = crypto;
	}

	public double getDemande(Chocolat chocolat, double cours) {
		return acheteurChocolat.getDemande(chocolat, cours);
	}

	public Integer getCryptogramme(SuperviseurChocolatBourse superviseur) {
		return this.cryptogramme;
	}

	public void notifierCommande(Chocolat chocolat, double quantiteObtenue, boolean payee) {
		acheteurChocolat.notifierCommande(chocolat, quantiteObtenue, payee);
	}

	public void receptionner(ChocolatDeMarque chocolat, double quantite) {
		acheteurChocolat.receptionner(chocolat, quantite);
	}

	// Méthodes du distributeur de chocolat de marque
	
	public List<ChocolatDeMarque> getCatalogue() {
		return distributeurChocolat.getCatalogue();
	}

	public double prix(ChocolatDeMarque choco) {
		return distributeurChocolat.prix(choco);
	}

	public double quantiteEnVente(ChocolatDeMarque choco) {
		return distributeurChocolat.quantiteEnVente(choco);
	}

	public void vendre(ClientFinal client, ChocolatDeMarque choco, double quantite, double montant) {
		distributeurChocolat.vendre(client, choco, quantite, montant);
	}

	public void notificationRayonVide(ChocolatDeMarque choco) {
		distributeurChocolat.notificationRayonVide(choco);
	}

	public List<ChocolatDeMarque> pubSouhaitee() {
		return distributeurChocolat.pubSouhaitee();
	}

}
