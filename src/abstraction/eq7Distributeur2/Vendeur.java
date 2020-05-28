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
	
	public boolean commercialise(Chocolat choco) {
		return !choco.getGamme().equals(Gamme.BASSE);
	}
	
	public void initialiser() {
		for (ChocolatDeMarque choco : ac.tousLesChocolatsDeMarquePossibles()) {
			ajouterProduitAuCatalogue(choco);
			prixChoco.put(choco, new Variable("Prix du " + choco.name(), ac, prixParDefaut.get(choco.getChocolat())));
			quantitesEnVente.put(choco, new Variable("Quantité en vente de " + choco.name(), ac, 0.));
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
		for (Chocolat choco : ac.nosChoco) {
			chocoVendu.get(choco).setValeur(ac, ventesEtapeActuelle.get(choco));
			ventesEtapeActuelle.put(choco,0.);
		}
	}
	
	public void majPublicites() {
		// IA : publicités sur les chocolats équitables uniquement
		for (ChocolatDeMarque choco : produitsCatalogue) {
			if (choco.getChocolat() == Chocolat.CHOCOLAT_MOYENNE_EQUITABLE ||choco.getChocolat() == Chocolat.CHOCOLAT_HAUTE_EQUITABLE) {
				publicites.add(choco);
			}
		}
	}
	
	public void majQuantitesEnVente() {
		// IA : quantité en vente = alpha * stockActuel + beta
		double quantiteEnVente;
		double stockActuel;
		double alpha = 0.9;
		double beta = 5.;
		for (ChocolatDeMarque choco : produitsCatalogue) {
			stockActuel = ac.getStock().getStockChocolatDeMarque(choco);
			quantiteEnVente = alpha*stockActuel + beta ;
			quantitesEnVente.get(choco).setValeur(ac, quantiteEnVente);
		}
	}
	
	public void majQuantitesACommander() {
		// IA : quantité à commander = max(0, quantité en vente - (stockActuel - stockLimite))
		double quantiteACommander;
		double stockActuel;
		double stockLimite = 10.;
		for (ChocolatDeMarque choco : produitsCatalogue) {
			stockActuel = ac.getStock().getStockChocolatDeMarque(choco);
			quantiteACommander = Double.max(0., quantitesEnVente.get(choco).getValeur() - (stockActuel - stockLimite));
			quantitesACommander.get(choco).setValeur(ac, quantiteACommander);
		}
	}
	
	public void majPrixDeVente() {
		// IA : prix vente = (1 + pourcentageMarge/100) * cours
		double cours;
		double pourcentageMarge;
		for (ChocolatDeMarque choco : produitsCatalogue) {
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
		return quantitesACommander.get(choco).getValeur();
	}
	
	public double getQuantiteACommander(Chocolat choco) {
		double res = 0.;
		for (ChocolatDeMarque produit : produitsCatalogue) {
			if (produit.getChocolat() == choco) {
				res += quantitesACommander.get(produit).getValeur();
			}
		}
		return res;
	}
	
	public double quantiteEnVente(Chocolat choco) {
		double res = 0.;
		for (ChocolatDeMarque produit : produitsCatalogue) {
			if (produit.getChocolat() == choco) {
				res += quantitesEnVente.get(produit).getValeur();
			}
		}
		return res;
	}
	
	public double quantiteEnVente(ChocolatDeMarque choco) {
		if (!ac.debutEtape) {
			ac.debutEtape = true;
			adapterQuantitesEnVente();
			dessinerCatalogue();
		}
		return quantitesEnVente.get(choco).getValeur();
	}

	public void ajouterProduitAuCatalogue(ChocolatDeMarque choco) {
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
		return produitsCatalogue;
	}

	public double prix(ChocolatDeMarque choco) {
		return prixChoco.get(choco).getValeur();
	}

	public void adapterQuantitesEnVente() {
		for (ChocolatDeMarque produit : produitsCatalogue) {
			quantitesEnVente.get(produit).setValeur(ac, Double.min(quantitesEnVente.get(produit).getValeur(), ac.getStock().getStockChocolatDeMarque(produit)));
		}
	}

	public void vendre(ClientFinal client, ChocolatDeMarque choco, double quantite, double montant) {
		if (client!=null) { 
			ac.getStock().retirerStockChocolat(choco, quantite);
			journal.ajouter(Journal.texteColore(positiveColor, Color.BLACK, "[VENTE] Vente de " + Journal.doubleSur(quantite,2) + " tonnes de " + choco.name() + " à " + client.getNom() + " pour " + Journal.doubleSur(montant,2) + " (" + Journal.doubleSur(montant/quantite,2) + "/tonne)."));
			ventesEtapeActuelle.put(choco.getChocolat(), ventesEtapeActuelle.get(choco.getChocolat()) + quantite);
		}
	}

	public void notificationRayonVide(ChocolatDeMarque choco) {
		journal.ajouter(Journal.texteColore(warningColor, Color.BLACK, "[RAYON] Le rayon de " + choco.name() + " est vide."));
	}

	public List<ChocolatDeMarque> pubSouhaitee() {
		return publicites;
	}

}
