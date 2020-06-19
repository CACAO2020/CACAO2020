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
		for (ChocolatDeMarque choco : ac.tousLesChocolatsDeMarquePossibles()) {
			ajouterProduitAuCatalogue(choco);
			prixChoco.put(choco, new Variable("Prix du " + choco.name(), ac, prixParDefaut.get(choco.getChocolat())));
			quantitesEnVente.put(choco, new Variable("Quantité en vente de " + choco.name(), ac, 0.));  //!!!!!!!!!!! Forcément 0 en stock initial ?
			quantitesACommander.put(choco, new Variable(getNom() + " : " + choco.name() + " [Commande Contrat-Cadre i-1]", ac, 0.));	
		}
	}
	
	public void next() {
		// Le vendeur met à jour ses indicateurs de vente (dont ont besoin les acheteurs)
		majIndicateursDeVente();
		// Le vendeur détermine quelle quantité de chaque chocolat de marque proposer à la vente à l'étape suivante
		majQuantitesEnVente();
		// Le vendeur met à jour la liste des quantités de chocolat à commander
		majQuantitesACommander();
		// Le vendeur met à jour les prix de vente de chaque chocolat de marque
		majPrixDeVente();
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
		// IA : publicités sur les chocolats équitables uniquement
		for (ChocolatDeMarque choco : produitsCatalogue) {
			if (choco.getChocolat() == Chocolat.CHOCOLAT_MOYENNE_EQUITABLE ||choco.getChocolat() == Chocolat.CHOCOLAT_HAUTE_EQUITABLE) {
				publicites.add(choco);
			}
		}
	}
	
	public void majQuantitesEnVente() {//si solde trop faible on écoule les stocks
		//le solde seuil est calculé à partir du cours actuel
		double quantiteEnVente;
		double stockActuel;
		double soldeActuel;
		double stockLimite = 1000.;
		soldeActuel = ac.getSolde();
		double coursActuel; //dépend du chocolat en vente !
		double moyenneCours = 0;
		double soldeMini; // fonction du cours
		
		// On calcule d'abord le cours moyen de la bourse pour nos chocolats
		for (ChocolatDeMarque choco : produitsCatalogue) {
			coursActuel = Filiere.LA_FILIERE.getIndicateur("BourseChoco cours " + choco.getChocolat().name()).getHistorique().get(Filiere.LA_FILIERE.getEtape()).getValeur();
			moyenneCours += coursActuel;
		}
		moyenneCours /= produitsCatalogue.size();
		soldeMini = coeffCoursMoyen*moyenneCours; //Valeur complétement arbitraire
		
		for (ChocolatDeMarque choco : produitsCatalogue) {
			stockActuel = ac.getStock().getStockChocolatDeMarque(choco);
			if (soldeActuel <= soldeMini){
				//il faut vendre tout !
				quantitesEnVente.get(choco).setValeur(ac, stockActuel);
			} else if (stockActuel < stockLimite) {
				quantitesEnVente.get(choco).setValeur(ac, 0.);
			}else {
				double k = (stockActuel-stockLimite)/stockLimite;
				quantiteEnVente = Double.max(k*stockActuel - stockLimite, stockLimite);
				quantitesEnVente.get(choco).setValeur(ac, quantiteEnVente);
			}
			
		}

	}
	
	public void majQuantitesACommander() { //!!!!!!!!!!!!!!!!!!On n'achète que pour compenser les ventes
		//cette fonction met à jour la quantité de chocolat à commander pour chaque chocolat du catalogue
		// IA : quantité à commander = max(0, quantité en vente - (stockActuel - stockLimite))
		double quantiteACommander;
		double stockActuel;
		double stockLimite = 1000.;
		double coursActuel;
		double moyenneCours = 0;
		double soldeMini;
		double soldeActuel = ac.getSolde();
		
		// On calcule d'abord le cours moyen de la bourse pour nos chocolats
		for (ChocolatDeMarque choco : produitsCatalogue) {
			coursActuel = Filiere.LA_FILIERE.getIndicateur("BourseChoco cours " + choco.getChocolat().name()).getHistorique().get(Filiere.LA_FILIERE.getEtape()).getValeur();
			moyenneCours += coursActuel;
		}
		moyenneCours /= produitsCatalogue.size();
		soldeMini = coeffCoursMoyen*moyenneCours; //Valeur complétement arbitraire	
		
		for (ChocolatDeMarque choco : produitsCatalogue) {
			stockActuel = ac.getStock().getStockChocolatDeMarque(choco);
			if (soldeActuel <= soldeMini) {
				// Il faut tout arrêter !
				quantiteACommander = 0.;
			}
			else if (stockActuel <= stockLimite) {
				quantiteACommander = (stockLimite-stockActuel)*10;
			} else {
				quantiteACommander = Double.max(quantitesEnVente.get(choco).getValeur() - ac.getStock().getStockChocolatDeMarque(choco), 0.);
			}
			quantitesACommander.get(choco).setValeur(ac, quantiteACommander);
		}
	}
	
	public void majPrixDeVente() {
		// met à jour le prix de vente de chaque chocolat du catalogue selon la valeur du cours actuel
		// IA : prix vente = (1 + pourcentageMarge/100) * cours
		double cours;
		double pourcentageMarge;
		for (ChocolatDeMarque choco : produitsCatalogue) {
			if (this.coutUnitaire.containsKey(choco)) {
				double prixLastStep = this.coutUnitaire.get(choco);
			}
			pourcentageMarge = 10.;
			if (Filiere.LA_FILIERE.getEtape() > 1) {
	            cours = Filiere.LA_FILIERE.getIndicateur("BourseChoco cours " + choco.getChocolat().name()).getHistorique().get(Filiere.LA_FILIERE.getEtape()-1).getValeur();
	        } else {
	        	cours = prixParDefaut.get(choco.getChocolat());
	        }
			double prix = (1. + pourcentageMarge/100.) * cours;
			prixChoco.get(choco).setValeur(ac, prix);
		}
	}
	
	public double getQuantiteACommander(ChocolatDeMarque choco) {
		//donne la valeur des quantités à commander pour tel chocolat de marque
		return quantitesACommander.get(choco).getValeur();
	}
	
	public double getQuantiteACommander(Chocolat choco) {
		//donne la quantité à commander de chocolat d'une gamme
		double res = 0.;
		for (ChocolatDeMarque produit : produitsCatalogue) {
			if (produit.getChocolat() == choco) {
				res += quantitesACommander.get(produit).getValeur();
			}
		}
		return res;
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
			initialiserStocksEtape();
			adapterQuantitesEnVente();  // Appelée une seule fois à l'initialisation ?
			dessinerCatalogue();
		}
		if (Filiere.LA_FILIERE.getEtape() == 0) {
			return quantiteAVendreParDefaut;
		} else {
			return quantitesEnVente.get(choco).getValeur();
		}
	}

	public void initialiserStocksEtape() {    // Stocks initiaux nul du coup ?
		int etape = Filiere.LA_FILIERE.getEtape();
		ac.getStock().chocoEnStockParEtape.put(etape, new HashMap<ChocolatDeMarque, Double>());
		for (ChocolatDeMarque chocoDeMarque : ac.tousLesChocolatsDeMarquePossibles()) {
			ac.getStock().chocoEnStockParEtape.get(etape).put(chocoDeMarque, 0.);
		}
	}
	
	public void ajouterProduitAuCatalogue(ChocolatDeMarque choco) {
		//ajoute au catalogue un chocolat (selon sa marque) en indiquant son prix et sa quantité en vente
		if (!produitsCatalogue.contains(choco)) {
			produitsCatalogue.add(choco);
			prixChoco.put(choco, new Variable("Prix du " + choco.name(), ac, prixParDefaut.get(choco.getChocolat())));
			quantitesEnVente.put(choco, new Variable("Quantité de " + choco.name() + " en vente", ac, 0.));
			journalCatalogue.ajouter(Journal.texteColore(positiveColor, Color.BLACK, "[AJOUT] Le chocolat " + choco.name() + " a été ajouté au catalogue."));
		}
	}
	
	public void dessinerCatalogue() {
		journalCatalogue.ajouter(Journal.texteColore(metaColor, Color.BLACK, "[ETAPE " + Filiere.LA_FILIERE.getEtape() + "] Catalogue du distributeur"));
		journalCatalogue.ajouter(Journal.texteSurUneLargeurDe("Chocolat", 40) + Journal.texteSurUneLargeurDe("Quantité (tonnes)", 20) + Journal.texteSurUneLargeurDe("Prix", 20) + Journal.texteSurUneLargeurDe("", 30));
		for (ChocolatDeMarque choco : produitsCatalogue) {
			journalCatalogue.ajouter(Journal.texteSurUneLargeurDe(choco.name(), 40) + Journal.texteSurUneLargeurDe("" + Journal.doubleSur(quantitesEnVente.get(choco).getValeur(),2), 20) + Journal.texteSurUneLargeurDe("" + Journal.doubleSur(prixChoco.get(choco).getValeur(),2), 20) + Journal.texteSurUneLargeurDe("", 30));
		}
	}
	
	public List<ChocolatDeMarque> getCatalogue() {
		//renvoie la liste des chocolats de marque de notre catalogue
		return produitsCatalogue;
	}

	public double prix(ChocolatDeMarque choco) {
		//renvoie le prix d'un chocolat (selon sa marque) 
		return prixChoco.get(choco).getValeur();
	}

	public void adapterQuantitesEnVente() {
		//met à jour les quantité de chocolat de chaque marque en vente selon le stock qu'on a
		for (ChocolatDeMarque produit : produitsCatalogue) {
			double stockLimite = 10.;
			double temp = Double.max(ac.getStock().getStockChocolatDeMarque(produit)-stockLimite, 0.);
			temp = Double.min(quantitesEnVente.get(produit).getValeur(), temp);
			quantitesEnVente.get(produit).setValeur(ac, temp);

		}

			
	}
 
	public void vendre(ClientFinal client, ChocolatDeMarque choco, double quantite, double montant) {
		//vend le chocolat de tel marque à tel prix et telle quantité au client final
		if (client!=null) { 
			if (quantite != 0.) {
				this.coutUnitaire.put(choco, montant/quantite); 
			
			ac.getStock().retirerStockChocolat(choco, quantite);
			journal.ajouter(Journal.texteColore(positiveColor, Color.BLACK, "[VENTE] Vente de " + Journal.doubleSur(quantite,2) + " tonnes de " + choco.name() + " à " + client.getNom() + " pour " + Journal.doubleSur(montant,2) + " (" + Journal.doubleSur(montant/quantite,2) + "/tonne)."));
			ventesEtapeActuelle.put(choco.getChocolat(), ventesEtapeActuelle.get(choco.getChocolat()) + quantite);
			}
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
