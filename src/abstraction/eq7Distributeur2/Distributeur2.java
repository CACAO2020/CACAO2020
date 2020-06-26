package abstraction.eq7Distributeur2;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

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
	
	// Solde seuil pour le mode panik
	protected double soldeMini = 100000.;
	
	// Solde seuil pour le mode kalm
	protected double soldeMaxi = 10000000.;
	
	// Instances des sous-acteurs associés au distributeur
	private AcheteurBourse acheteurBourse;
	private AcheteurContratCadre acheteurContratCadre;
	private Vendeur vendeur;
	private Stock stock;
	
	// Frais par étape
	protected double coutMasseSalariale = 80000;
	protected double coutPub = 1000;
	
	// Stock initial
	protected double stockInitial = 10000;
	
	private Journal journal;
	private Journal journalTransactions;
	
	protected boolean debutEtape = true;
	
	public Distributeur2() {
		super();
		NB_INSTANCES++;
		numero = NB_INSTANCES;
		acheteurBourse = new AcheteurBourse(this);
		acheteurContratCadre = new AcheteurContratCadre(this);
		vendeur = new Vendeur(this);
		stock = new Stock(this);
		initJournaux();
	}
	
	// Initialise les journaux
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
	
	// Lance les procédures d'initialisation des acteurs
	public void initialiser() {
		vendeur.initialiser();
		acheteurBourse.initialiser();
		stock.initialiser();
		// Ajout d'un stock initial
		stock.chocoEnStockParEtape.put(0, new HashMap<ChocolatDeMarque, Double>());
		for (ChocolatDeMarque choco : tousLesChocolatsDeMarquePossibles()) {
			vendeur.quantitesACommanderParContrats.put(choco, new Variable(getNom() + " : " + choco.name() + " [CONTRATS]", this, 0.));
			stock.chocoEnStockParEtape.get(0).put(choco, 0.);
			stock.stocksChocolatDeMarque.put(choco, new Variable(getNom() + " : STOCK " + choco.name(), this, 0.));
			stock.journal.ajouter(Journal.texteColore(stock.metaColor, Color.BLACK,"[CRÉATION] Création d'un stock pour le " + choco.name() + "."));	
		}
		for (Chocolat choco : Chocolat.values()) {
			if (choco.getGamme() != Gamme.BASSE) {
				vendeur.quantitesACommanderEnBourse.put(choco, new Variable(getNom() + " : " + choco.name() + " [BOURSES]", this, 0.));
				stock.ajouterStockChocolat(new ChocolatDeMarque(choco, this.getNom()), stockInitial);
				vendeur.prixChoco.put(choco, new Variable("Prix du " + choco.name(), this, vendeur.prixParDefaut.get(choco)));
			}
		}
	}
	
	// La méthode next, qui lance les appels des fonctions next de chaque sous-acteur
	// Le vendeur est appelé en premier pour évaluer la quantité de chocolat que les acheteurs doivent commander
	public void next() {
		this.debutEtape = false;
		// Paiement des frais (masse salariale et coûts de stockage)
		payerFrais();
		if (Filiere.LA_FILIERE.getEtape() > 0) {
			gestionPanik();
			if (!vendeur.panik) {
				gestionKalm();
			}
		}
		stock.next();
		vendeur.next();
		acheteurContratCadre.next();
		acheteurBourse.next();
	}
	
	// Renvoie le total des frais de l'étape : frais de stockage, salaires, auxquels sont retirés les bénéfices de livraison
	public double getFrais() {
		double coutMasseSalariale = this.coutMasseSalariale;
		double fraisStockage = this.getStock().fraisStockage();
		double beneficesLivraisons = 0.;
		double distanceMin = 5.;
		double distanceMax = 20.;
		double fraisTotaux = 0.;
		double coutFixeLivraison = 5;
		double coutLivraisonVariable = 2;
		double proportionDeLivraisons = 10; // pourcentage de livraisons
		for (Double quantite : vendeur.quantitesVendues) {
			Random testLivraison = new Random();
			boolean estUnelivraison = (testLivraison.nextInt(100) < proportionDeLivraisons); 
			if (estUnelivraison) {
				Random distanceLivraison = new Random();
				double distance = distanceMin + (distanceMax - distanceMin) * distanceLivraison.nextDouble();
				beneficesLivraisons += coutFixeLivraison + coutLivraisonVariable*distance*quantite;
			}
		}
		if (vendeur.pubLastStep) {
			fraisTotaux += coutPub;
			vendeur.pubLastStep = false;
		}
		if (coutMasseSalariale + fraisStockage < beneficesLivraisons) {
			fraisTotaux = 0;
		} else {
			fraisTotaux = coutMasseSalariale + fraisStockage - beneficesLivraisons;
		}
		return fraisTotaux;
	}
	
	public void payerFrais() {
		double fraisTotaux = this.getFrais();
		if (fraisTotaux > 0) {
			journalTransactions.ajouter(Journal.texteColore(warningColor, Color.BLACK, "[FRAIS] Paiement de " + Journal.doubleSur(fraisTotaux,2) + " de frais."));
			Filiere.LA_FILIERE.getBanque().virer(this, this.cryptogramme, Filiere.LA_FILIERE.getActeur("Banque"), fraisTotaux);
		}
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
	
	// Renvoie le solde actuel de l'acteur
	public double getSolde() {
		return Filiere.LA_FILIERE.getBanque().getSolde(Filiere.LA_FILIERE.getActeur(getNom()), this.cryptogramme);
	}
	
	// Informe de la faillite d'un acteur dans le journal principal
	public void notificationFaillite(IActeur acteur) {
		if (this==acteur) {
			System.out.println("I'll be back... or not... "+this.getNom());
			journal.ajouter(Journal.texteColore(alertColor, Color.BLACK, "[FAILLITE] Cet acteur a fait faillite !"));
			for (ChocolatDeMarque choco : this.tousLesChocolatsDeMarquePossibles()) {
				this.vendeur.quantitesEnVente.get(choco).setValeur(this, 0.);;
			}
		} else {
			System.out.println("Poor "+acteur.getNom()+"... We will miss you. "+this.getNom());
			journal.ajouter(Journal.texteColore(alertColor, Color.BLACK, "[FAILLITE] " + acteur.getNom() + " a fait faillite !"));
		}
	}	

	// Affiche les détails d'une opération bancaire dans le journal des transactions
	public void notificationOperationBancaire(double montant) {
		if (montant > 0) {
			journalTransactions.ajouter(Journal.texteColore(positiveColor, Color.BLACK, "[CRÉDIT] Crédit d'une valeur de : " + Journal.doubleSur(montant,2) + "."));
		} else {
			journalTransactions.ajouter(Journal.texteColore(warningColor, Color.BLACK, "[DÉBIT] Débit d'une valeur de : " + Journal.doubleSur(-montant,2) + "."));
		}
	}
	
	// Affiche le solde dans le journal principal
	public void notificationSolde() {
		double solde = getSolde();
		if (solde > soldeMini) {
			journal.ajouter(Journal.texteColore(positiveColor, Color.BLACK, "[SOLDE] Solde après vente : " + Journal.doubleSur(solde,2) + "."));
		} else {
			if (solde > 0) {
				journal.ajouter(Journal.texteColore(warningColor, Color.BLACK, "[SOLDE] Solde actuel : " + Journal.doubleSur(solde,2) + "."));
			} else {
				journal.ajouter(Journal.texteColore(alertColor, Color.BLACK, "[SOLDE] Solde actuel : " + Journal.doubleSur(solde,2) + "."));
			}
		}
	}
	
	// L'acteur devient en panik si son solde est inférieur au soldeMini
	public boolean estEnPanik() {
		return (this.getSolde() <= this.soldeMini);
	}
		
	// Gère la panik de l'acteur
	public void gestionPanik() {
		// Le mode panique est-il actif ?
		boolean estEnPanik = estEnPanik(); 
		if (estEnPanik) {
			if (!vendeur.wasPanik) {
				// Le mode panik vient de s'activer !
				vendeur.wasPanik = true;
				vendeur.panik = true;
				vendeur.modeActuel = "panik";
				// Ajout au journal le début du mode panik
				journal.ajouter(Journal.texteColore(behaviorColor, Color.BLACK, "[PANIK ON] Mode PANIK activé !"));
			} else {
				
				// Le mode panik est actif mais ce n'est pas le premier tour de panik
				vendeur.wasPanik = true;
				vendeur.panik = true; // On sait jamais
				// Ajout au journal la poursuite de la panik
			}
		} else if (!estEnPanik && vendeur.wasPanik) {
			// La panik vient de se terminer (et nous sommes toujours là)
			vendeur.wasPanik = false;
			vendeur.panik = false;
			vendeur.modeActuel = "normal";
			//Ajouter au journal la fin de la panik
			journal.ajouter(Journal.texteColore(behaviorColor, Color.BLACK, "[PANIK OFF] Mode PANIK désactivé ! Ouf !"));
		} else {
			//Pas de panik en vue, rien à afficher
			vendeur.wasPanik = false;
		}
	}
	
	// L'acteur devient kalm si son solde est supérieur au soldeMaxi
	public boolean estKalm() {
		double soldeActuel = this.getSolde();
		return soldeActuel > this.soldeMaxi;		
	}
	// Gère le kalm de l'acteur
	public void gestionKalm() {
		//Le mode kalm est-il actif ?
		boolean estKalm = estKalm(); 
		if (estKalm) {
			if (!vendeur.wasKalm) {
				//Mode Kalm vient de s'activer !
				vendeur.wasKalm = true;
				vendeur.kalm = true;
				vendeur.modeActuel = "kalm";
				//Ajout au journal le début du mode Kalm
				journal.ajouter(Journal.texteColore(behaviorColor, Color.BLACK, "[KALM ON] Mode KALM activé !"));
			} else {
				// Le mode Kalm est actif mais ce n'est pas le premier tour de Kalm
				vendeur.wasKalm = true;
				vendeur.kalm = true; // On sait jamais
				// Ajout au journal la poursuite du Kalm
				//journal.ajouter(Journal.texteColore(behaviorColor, Color.BLACK, "[KALM] Mode KALM toujours actif !"));
			}
		} else if (!estKalm && vendeur.wasKalm) {
			// Le Kalm vient de se terminer (aie)
			vendeur.wasKalm = false;
			vendeur.kalm = false;
			vendeur.modeActuel = "normal";
			//Ajouter au journal la fin du Kalm
			//journal.ajouter(Journal.texteColore(behaviorColor, Color.BLACK, "[KALM OFF] Mode KALM désactivé ! Aie !"));
		} else {
			//Pas de Kalm en vue, rien à afficher
			vendeur.wasKalm = false;
		}
	}
	
	
	// Renvoie la liste des filières proposées par l'acteur
	public List<String> getNomsFilieresProposees() {
		ArrayList<String> filieres = new ArrayList<String>();
		filieres.add("TESTEQ7");
		return(filieres);
	}

	// Renvoie une instance d'une filière d'après son nom
	public Filiere getFiliere(String nom) {
		if (nom.equals("TESTEQ7")) {
			return new FiliereTest();
		}
		else {
			return Filiere.LA_FILIERE;
		}
	}

	// Renvoie les indicateurs
	public List<Variable> getIndicateurs() {
		List<Variable> res = new ArrayList<Variable>();
		res.addAll(stock.getIndicateurs());
		res.addAll(acheteurBourse.getIndicateurs());
		res.addAll(acheteurContratCadre.getIndicateurs());
		res.addAll(vendeur.getIndicateurs());
		return res;
	}

	// Renvoie les paramètres
	public List<Variable> getParametres() {
		List<Variable> res=new ArrayList<Variable>();
		res.addAll(stock.getParametres());
		res.addAll(acheteurBourse.getParametres());
		res.addAll(acheteurContratCadre.getParametres());
		res.addAll(vendeur.getParametres());
		return res;
	}

	// Renvoie les journaux
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

	// Renvoie les instances des acteurs associés à ce distributeur
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

	// Méthodes renvoyant aux méthodes de l'acheteur à la bourse
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

	// Méthodes renvoyant aux méthodes du vendeur
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

	// Méthodes renvoyant aux méthodes de l'acheteur contrat cadre
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
