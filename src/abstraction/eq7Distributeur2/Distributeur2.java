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
import abstraction.eq8Romu.contratsCadres.Echeancier;
import abstraction.eq8Romu.contratsCadres.ExemplaireContratCadre;
import abstraction.eq8Romu.contratsCadres.IAcheteurContratCadre;
import abstraction.eq8Romu.contratsCadres.SuperviseurVentesContratCadre;
import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.ChocolatDeMarque;
import abstraction.eq8Romu.produits.Feve;
import abstraction.eq8Romu.produits.Gamme;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;
import abstraction.eq1Producteur1.FiliereTestCrieeProd1;
import abstraction.eq7Distributeur2.*;


public class Distributeur2 extends AbsDistributeur2 implements IActeur, IAcheteurChocolatBourse, IAcheteurContratCadre, IDistributeurChocolatDeMarque {
	
	private static int NB_INSTANCES = 0;
	public int numero;
	protected int cryptogramme;
	
	protected double soldeCritique;
	
	private AcheteurBourse acheteurBourse;
	private AcheteurContratCadre acheteurContratCadre;
	private Vendeur vendeur;
	private Stock stock;
	
	private Journal journal;
	private Journal journalTransactions;
	
	protected boolean debutEtape = true;
	
	public Distributeur2() {
		super();
		NB_INSTANCES++;
		numero = NB_INSTANCES;
		soldeCritique = 2.;
		acheteurBourse = new AcheteurBourse(this);
		acheteurContratCadre = new AcheteurContratCadre(this);
		vendeur = new Vendeur(this);
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
		journalTransactions.ajouter(Journal.texteColore(descriptionColor, Color.BLACK, "par l'acteur (débits et crédits) ainsi que les alertes de solde."));
	}
	 
	public int getNumero() {
		return this.numero; 
	}
	
	public AcheteurBourse getAcheteurBourse() {
		return this.acheteurBourse;
	}
	
	public AcheteurContratCadre getAcheteurContratCadre() {
		return this.acheteurContratCadre;
	}
	
	public Vendeur getVendeur() {
		return this.vendeur;
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
		vendeur.initialiser();
		acheteurBourse.initialiser();
		stock.initialiser();
		// AJOUT D'UN STOCK INITIAL POUR OBSERVER LES VENTES
		for (ChocolatDeMarque choco : vendeur.getCatalogue()) {
			stock.ajouterStockChocolat(choco, 100.);
		}
	}
	
	public void next() {
		this.debutEtape = false;
		// Le vendeur met à jour ses indicateurs de vente (dont ont besoin les acheteurs)
		vendeur.majIndicateursDeVente();
		// L'acheteur à la bourse déterminé quelle quantité de chaque type de chocolat commander
		acheteurBourse.majAchatsBourse();
		// L'acheteur par contrats-cadres met à jour ses contrats
		acheteurContratCadre.majAchatsContratCadre();	
		// Le vendeur choisit les campagnes de pub à mener lors de l'étape courante
		vendeur.majPublicites();
		// Le vendeur détermine quelle quantité de chaque chocolat de marque proposer à la vente à l'étape suivante
		vendeur.majQuantitesEnVente();		
		// Le vendeur met à jour les prix de vente de chaque chocolat de marque
		vendeur.majPrixDeVente();
	}
	
	public double getSolde() {
		return Filiere.LA_FILIERE.getBanque().getSolde(Filiere.LA_FILIERE.getActeur(getNom()), this.cryptogramme);
	}

	public List<String> getNomsFilieresProposees() {
		ArrayList<String> filieres = new ArrayList<String>();
		filieres.add("TESTEQ7");
		return(filieres);
	}

	public Filiere getFiliere(String nom) {
		if (nom.equals("TESTEQ7")) {
			return new FiliereTest();
		}
		else {
			return Filiere.LA_FILIERE;
		}
	}

	public List<Variable> getIndicateurs() {
		List<Variable> res = new ArrayList<Variable>();
		res.addAll(stock.getIndicateurs());
		res.addAll(acheteurBourse.getIndicateurs());
		res.addAll(acheteurContratCadre.getIndicateurs());
		res.addAll(vendeur.getIndicateurs());
		return res;
	}

	public List<Variable> getParametres() {
		List<Variable> res=new ArrayList<Variable>();
		res.addAll(stock.getParametres());
		res.addAll(acheteurBourse.getParametres());
		res.addAll(acheteurContratCadre.getParametres());
		res.addAll(vendeur.getParametres());
		return res;
	}

	public List<Journal> getJournaux() {
		List<Journal> res=new ArrayList<Journal>();
		res.add(journal);
		res.add(journalTransactions);
		res.addAll(stock.getJournaux());
		res.addAll(acheteurBourse.getJournaux());
		res.addAll(acheteurContratCadre.getJournaux());
		res.addAll(vendeur.getJournaux());
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

	// Méthodes de l'acheteur à la bourse
	
	public void setCryptogramme(Integer crypto) {
		this.cryptogramme = crypto;
	}

	public double getDemande(Chocolat chocolat, double cours) {
		return acheteurBourse.getDemande(chocolat, cours);
	}

	public Integer getCryptogramme(SuperviseurChocolatBourse superviseur) {
		return this.cryptogramme;
	}

	public void notifierCommande(Chocolat chocolat, double quantiteObtenue, boolean payee) {
		acheteurBourse.notifierCommande(chocolat, quantiteObtenue, payee);
	}

	public void receptionner(ChocolatDeMarque chocolat, double quantite) {
		acheteurBourse.receptionner(chocolat, quantite);
	}

	// Méthodes du vendeur
	
	public List<ChocolatDeMarque> getCatalogue() {
		return vendeur.getCatalogue();
	}

	public double prix(ChocolatDeMarque choco) {
		return vendeur.prix(choco);
	}

	public double quantiteEnVente(ChocolatDeMarque choco) {
		return vendeur.quantiteEnVente(choco);
	}

	public void vendre(ClientFinal client, ChocolatDeMarque choco, double quantite, double montant) {
		vendeur.vendre(client, choco, quantite, montant);
	}

	public void notificationRayonVide(ChocolatDeMarque choco) {
		vendeur.notificationRayonVide(choco);
	}

	public List<ChocolatDeMarque> pubSouhaitee() {
		return vendeur.pubSouhaitee();
	}

	// Méthodes de l'acheteur contrat cadre
	
	public Echeancier contrePropositionDeLAcheteur(ExemplaireContratCadre contrat) {
		return acheteurContratCadre.contrePropositionDeLAcheteur(contrat);
	}

	public double contrePropositionPrixAcheteur(ExemplaireContratCadre contrat) {
		return acheteurContratCadre.contrePropositionPrixAcheteur(contrat);
	}

	public void receptionner(Object produit, double quantite, ExemplaireContratCadre contrat) {
		acheteurContratCadre.receptionner(produit, quantite, contrat);
	}

}
