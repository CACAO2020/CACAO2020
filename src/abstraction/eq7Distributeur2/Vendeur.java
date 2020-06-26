package abstraction.eq7Distributeur2;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import abstraction.eq8Romu.clients.ClientFinal;
import abstraction.eq8Romu.clients.IDistributeurChocolat;
import abstraction.eq8Romu.clients.IDistributeurChocolatDeMarque;
import abstraction.eq8Romu.produits.Chocolat;
//import abstraction.eq8Romu.produits.Gamme;
//import abstraction.fourni.Filiere;
import abstraction.eq8Romu.produits.ChocolatDeMarque;
import abstraction.eq8Romu.produits.Gamme;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;

public class Vendeur extends AbsVendeur implements IDistributeurChocolatDeMarque, IActeur {

	public Vendeur(Distributeur2 ac) {
		super(ac);
		
	}
	
	public void initialiser() {
		this.coutUnitaire = new HashMap<ChocolatDeMarque, Double>();
		quantiteAVendreParDefaut = 0.;
		
		for (ChocolatDeMarque choco : ac.tousLesChocolatsDeMarquePossibles()) {
			ajouterProduitAuCatalogue(choco);
			quantitesEnVente.put(choco, new Variable("Quantité en vente de " + choco.name(), ac, 0.));  //!!!!!!!!!!! Forcément 0 en stock initial ?
		}
	}
	
	public void next() {
		// Le vendeur met à jour ses indicateurs de vente
		majIndicateursDeVente();
		// Le vendeur détermine les quantités à vendre et à commander
		majAchatsEtVentes();
		// Le vendeur met à jour les prix de vente de chaque chocolat de marque
		//majPrixDeVente();
		// Le vendeur choisit les campagnes de pub à mener lors de l'étape courante
		majPublicites();
	}
	
	public void majIndicateursDeVente() {
		//cette fonction met à jour les données de vente de la step actuelle
		for (Chocolat choco : ac.nosChoco) {
			chocoVendu.get(choco).setValeur(ac, ventesEtapeActuelle.get(choco));
			ventesEtapeActuelle.put(choco,0.);
		}
	}
	
	public void majPublicites() {
		//cette fonction actualise la liste des chocolats (selon gamme) dont le distributeur fait la publicité
		int etape = Filiere.LA_FILIERE.getEtape();
		if (etape % 22 == 0 || etape % 7 == 0 || (panik && compteurPub < 3)) {
			// On est à la période de Noël, Paques ou en panik en ayant fait peu de pubs ! Pub sur tous nos chocolats équitables !
			for (ChocolatDeMarque choco : produitsCatalogue) {
				if (choco.getChocolat().isEquitable()) {
					publicites.add(choco);
				}
			}
			pubLastStep = true;
		} else if (etape % 24 == 0) {
			//Remise à 0 du compteur
			compteurPub = 0;
		}
	}
	
	// La fonction principale de cet acteur
	public void majAchatsEtVentes() {
		int etapeActuelle = Filiere.LA_FILIERE.getEtape();
		double quantiteAVendre = 0.;
		double quantiteACommanderEnBourse = 0.;
		double quantiteACommanderParContrats = 0.;
		double stockActuel;
		double stockQuiVaPerimer;
		double stockQuiNeVaPasPerimer;
		double cours;
		double pourcentageMarge;
		double beneficePartiel;
		double beneficeTotal = 0.;
		double prixDeVente = 0.;
		double margeSolde = 90.;
		double soldeAlloueAuxAchats;
		double soldeActuel = Filiere.LA_FILIERE.getBanque().getSolde(ac, ac.cryptogramme);
		double coutContratsEtape = ac.getAcheteurContratCadre().coutContratsActuels();
		double soldeDisponible = soldeActuel - coutContratsEtape;
		
		if (etapeActuelle != 0) {
			beneficeTotal = 0;
			for (Chocolat choco : Chocolat.values()) {
				if (choco.getGamme() != Gamme.BASSE) { // Seulement sur nos chocolats par GAMME
					
					stockActuel = ac.getStock().getStockChocolat(choco);
					stockQuiVaPerimer = ac.getStock().stockQuiVaPerimer(choco);
					stockQuiNeVaPasPerimer = stockActuel - stockQuiVaPerimer;
					pourcentageMarge = this.pourcentagesMarge.get(this.modeActuel).get(choco); // Les marges par produit sont définies dans l'absvendeur
					cours = Filiere.LA_FILIERE.getIndicateur("BourseChoco cours " + choco.name()).getHistorique().get(Filiere.LA_FILIERE.getEtape()-1).getValeur();
					prixDeVente = cours*(1+pourcentageMarge/100);
					this.prixChoco.get(choco).setValeur(ac, prixDeVente);
					
					if (panik) {
						quantiteAVendre = stockActuel;
					} else if (kalm) {
						quantiteAVendre = stockQuiVaPerimer + stockQuiNeVaPasPerimer*0.4;		
					} else {
						quantiteAVendre = stockQuiVaPerimer + stockQuiNeVaPasPerimer*0.5;					
					}
				
					beneficePartiel = quantiteAVendre*prixDeVente;
					beneficeTotal += beneficePartiel;

					for (ChocolatDeMarque chocoDeMarque : ac.tousLesChocolatsDeMarquePossibles()) { // Sur nos chocolats par MARQUE
						if (chocoDeMarque.getChocolat() == choco) {
							stockActuel = ac.getStock().getStockChocolatDeMarque(chocoDeMarque);
							stockQuiVaPerimer = ac.getStock().stockQuiVaPerimer(chocoDeMarque);
							stockQuiNeVaPasPerimer = stockActuel - stockQuiVaPerimer;
							// On définit les quantités à vendre
							if (panik) {
								quantiteAVendre = stockActuel;							
							} else if (kalm) {
								quantiteAVendre = stockQuiVaPerimer + stockQuiNeVaPasPerimer*0.4;		
							} else {
								quantiteAVendre = stockQuiVaPerimer + stockQuiNeVaPasPerimer*0.5;					
							}
							this.quantitesEnVente.get(chocoDeMarque).setValeur(ac, quantiteAVendre);
						}
					}
				}
			}
			soldeAlloueAuxAchats = Double.max(0., (soldeDisponible + beneficeTotal)*(1-margeSolde/100));
			
			double soldeRestant = soldeAlloueAuxAchats;
			int nombreTypesChoco = 0;
			for (Chocolat choco : Chocolat.values()) {
				if (choco.getGamme() != Gamme.BASSE) {
					nombreTypesChoco += 1; // On s'en servira pour répartir le solde alloué aux achats entre tous les chocolats
				}
			}
			for (Chocolat choco : Chocolat.values()) {
				if (choco.getGamme() != Gamme.BASSE) {
					cours = Filiere.LA_FILIERE.getIndicateur("BourseChoco cours " + choco.name()).getHistorique().get(Filiere.LA_FILIERE.getEtape()-1).getValeur();
					double quantite = soldeAlloueAuxAchats/(nombreTypesChoco*cours);
					
					if (panik) {
						quantiteACommanderEnBourse = quantite*0.6; // On achète moins en mode panik
					} else if (kalm) {
						quantiteACommanderEnBourse = quantite*0.3; //Normalement les valeurs seraient 0.6; 0.3; 0.5 si la filière utilisait les contrats -> ce n'est pas le cas donc changement de stratégie
					} else {
						quantiteACommanderEnBourse = quantite*0.5;
					}
				soldeRestant -= quantiteACommanderEnBourse;
				this.quantitesACommanderEnBourse.get(choco).setValeur(ac, quantiteACommanderEnBourse);
				}
			}
			
			for (ChocolatDeMarque choco : ac.tousLesChocolatsDeMarquePossibles()) {
				double nombreProduitsChoco = 0;
				for (ChocolatDeMarque chocoDeMarque : ac.tousLesChocolatsDeMarquePossibles()) {
					if (chocoDeMarque.getChocolat() == choco.getChocolat()) {
						nombreProduitsChoco += 1;
					}
				}
				cours = Filiere.LA_FILIERE.getIndicateur("BourseChoco cours " + choco.getChocolat().name()).getHistorique().get(Filiere.LA_FILIERE.getEtape()-1).getValeur();
				if (panik) {
					quantiteACommanderParContrats = 0.;
				} else if (kalm) {
					quantiteACommanderParContrats = soldeRestant/(nombreProduitsChoco*cours); //valeur inutile car les contrats ne sont pas utilisés
				} else {
					quantiteACommanderParContrats = soldeRestant/(nombreProduitsChoco*cours); //valeur inutile car les contrats ne sont pas utilisés
				}
				this.quantitesACommanderParContrats.get(choco).setValeur(ac, quantiteACommanderParContrats);
			}
			
			
		}
	}
	
	public double sommeCours() {
		double res = 0;
		for (Chocolat choco : Chocolat.values()) {
			if (choco.getGamme() != Gamme.BASSE) {
				res += Filiere.LA_FILIERE.getIndicateur("BourseChoco cours " + choco.name()).getHistorique().get(Filiere.LA_FILIERE.getEtape()-1).getValeur();
			}
		}
		return res;
	}
	
	
	public double getQuantiteACommanderParContrats(ChocolatDeMarque choco) {
		//donne la valeur des quantités à commander pour tel chocolat de marque
		return quantitesACommanderParContrats.get(choco).getValeur();
	}
	
	public double getQuantiteACommanderEnBourse(Chocolat choco) {
		//donne la valeur des quantités à commander pour tel chocolat de marque
		return quantitesACommanderEnBourse.get(choco).getValeur();
	}
	
	public double quantiteEnVente(Chocolat choco) {
		//donne quantité en vente de chocolat selon une gamme
		double res = 0.;
		for (ChocolatDeMarque produit : produitsCatalogue) {
			if (produit.getChocolat() == choco) {
				res += quantitesEnVente.get(produit).getValeur();
			}
		}
		return res;
	}
	
	public double quantiteEnVente(ChocolatDeMarque choco) {
		//donne quantité en vente de chocolat selon la marque
		if (!ac.debutEtape) { 
			ac.debutEtape = true;
			ac.getStock().initialiserStocksEtape();
			adapterQuantitesEnVente();
			dessinerCatalogue();
		}
		if (Filiere.LA_FILIERE.getEtape() == 0) {
			return quantiteAVendreParDefaut;
		} else {
			return quantitesEnVente.get(choco).getValeur();
		}
	}
	
	public void ajouterProduitAuCatalogue(ChocolatDeMarque choco) {
		//ajoute au catalogue un chocolat (selon sa marque) en indiquant son prix et sa quantité en vente
		if (!produitsCatalogue.contains(choco)) {
			produitsCatalogue.add(choco);
			quantitesEnVente.put(choco, new Variable("Quantité de " + choco.name() + " en vente", ac, 0.));
			journalCatalogue.ajouter(Journal.texteColore(positiveColor, Color.BLACK, "[AJOUT] Le chocolat " + choco.name() + " a été ajouté au catalogue."));
		}
	}
	
	public void dessinerCatalogue() {
		journalCatalogue.ajouter(Journal.texteColore(metaColor, Color.BLACK, "[ETAPE " + Filiere.LA_FILIERE.getEtape() + "] Catalogue du distributeur"));
		journalCatalogue.ajouter(Journal.texteSurUneLargeurDe("Chocolat", 40) + Journal.texteSurUneLargeurDe("Quantité (tonnes)", 20) + Journal.texteSurUneLargeurDe("Prix", 20) + Journal.texteSurUneLargeurDe("", 30));
		for (ChocolatDeMarque choco : produitsCatalogue) {
			journalCatalogue.ajouter(Journal.texteSurUneLargeurDe(choco.name(), 40) + Journal.texteSurUneLargeurDe("" + Journal.doubleSur(quantitesEnVente.get(choco).getValeur(),2), 20) + Journal.texteSurUneLargeurDe("" + Journal.doubleSur(prixChoco.get(choco.getChocolat()).getValeur(),2), 20) + Journal.texteSurUneLargeurDe("", 30));
		}
	}
	
	public List<ChocolatDeMarque> getCatalogue() {
		//renvoie la liste des chocolats de marque de notre catalogue
		return produitsCatalogue;
	}

	public double prix(ChocolatDeMarque choco) {
		//renvoie le prix d'un chocolat (selon sa marque) 
		return prixChoco.get(choco.getChocolat()).getValeur();
	}

	public void adapterQuantitesEnVente() {
		//met à jour les quantité de chocolat de chaque marque en vente selon le stock qu'on a
		
		for (ChocolatDeMarque produit : produitsCatalogue) {
			double stockActuel = ac.getStock().getStockChocolatDeMarque(produit);
			double quantitesEnVentePrevue = quantitesEnVente.get(produit).getValeur();
			quantitesEnVente.get(produit).setValeur(ac, Double.min(stockActuel, quantitesEnVentePrevue));
			quantitesVendues = new ArrayList<Double>();
		}
		
	}
 
	public void vendre(ClientFinal client, ChocolatDeMarque choco, double quantite, double montant) {
		//vend le chocolat de telle marque à tel prix et telle quantité au client final
		if (client!=null) {
			this.coutUnitaire.put(choco, montant/quantite); 
			ac.getStock().retirerStockChocolat(choco, quantite);
			journal.ajouter(Journal.texteColore(positiveColor, Color.BLACK, "[VENTE] Vente de " + Journal.doubleSur(quantite,2) + " tonnes de " + choco.name() + " à " + client.getNom() + " pour " + Journal.doubleSur(montant,2) + " (" + Journal.doubleSur(montant/quantite,2) + "/tonne)."));
			ventesEtapeActuelle.put(choco.getChocolat(), ventesEtapeActuelle.get(choco.getChocolat()) + quantite);
			quantitesVendues.add(quantite);
		}
	}

	public void notificationRayonVide(ChocolatDeMarque choco) {
		//notifie quand le rayon est vide
		journal.ajouter(Journal.texteColore(warningColor, Color.BLACK, "[RAYON] Le rayon de " + choco.name() + " est vide."));
	}

	public List<ChocolatDeMarque> pubSouhaitee() {
		return publicites;
	}
	

}
