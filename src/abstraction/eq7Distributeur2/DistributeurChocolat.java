package abstraction.eq7Distributeur2;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

public class DistributeurChocolat extends AbsDistributeurChocolat implements IDistributeurChocolatDeMarque, IActeur {

	protected Map<Chocolat, Double> prixParDefaut;
	protected Map<ChocolatDeMarque, Variable> prixChoco;
	protected List<ChocolatDeMarque> produitsCatalogue;
	protected Map<ChocolatDeMarque, Variable> quantitesEnVente;
	
	public DistributeurChocolat(Distributeur2 ac) {
		super(ac);
		produitsCatalogue = new ArrayList<ChocolatDeMarque>();
		quantitesEnVente = new HashMap<ChocolatDeMarque, Variable>();
		prixChoco = new HashMap<ChocolatDeMarque, Variable>();
		prixParDefaut = new HashMap<Chocolat, Double>();
		prixParDefaut.put(Chocolat.CHOCOLAT_MOYENNE, 10000.);
		prixParDefaut.put(Chocolat.CHOCOLAT_MOYENNE_EQUITABLE, 14000.);
		prixParDefaut.put(Chocolat.CHOCOLAT_HAUTE, 15000.);
		prixParDefaut.put(Chocolat.CHOCOLAT_HAUTE_EQUITABLE, 20000.);
	}
	
	public boolean commercialise(Chocolat choco) {
		return !choco.getGamme().equals(Gamme.BASSE);
	}
	
	public void initialiser() {
	}

	public void next() {
		int etape = Filiere.LA_FILIERE.getEtape();
		for (Chocolat choco : ac.nosChoco) {
			if (totalChocoVendu.get(choco).getHistorique().getTaille() != etape + 1) {
				totalChocoVendu.get(choco).setValeur(ac, totalChocoVendu.get(choco).getHistorique().get(etape-1).getValeur());
			}
			if (etape > 0) {
				chocoVendu.get(choco).setValeur(ac, totalChocoVendu.get(choco).getHistorique().get(etape).getValeur() - totalChocoVendu.get(choco).getHistorique().get(etape-1).getValeur());
			}
		}
		dessinerCatalogue();
	}
	
	public void ajouterProduitAuCatalogue(ChocolatDeMarque choco) {
		if (!produitsCatalogue.contains(choco)) {
			produitsCatalogue.add(choco);
			prixChoco.put(choco, new Variable("Prix du " + choco.name(), ac, prixParDefaut.get(choco.getChocolat())));
			quantitesEnVente.put(choco, new Variable("Quantité de " + choco.name() + " en vente", ac, 0.));
			journalCatalogue.ajouter(Journal.texteColore(positiveColor, Color.BLACK, "[AJOUT] Le chocolat " + choco.name() + " a été ajouté au catalogue."));
		}
	}
	
	private void dessinerCatalogue() {
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
		double cours;
		double pourcentageMarge = 5;
		if (Filiere.LA_FILIERE.getEtape() > 1) {
            cours = Filiere.LA_FILIERE.getIndicateur("BourseChoco cours " + choco.getChocolat().name()).getHistorique().get(Filiere.LA_FILIERE.getEtape()-1).getValeur();
        } else {
        	cours = prixParDefaut.get(choco.getChocolat());
        }
		double prix = (1. + pourcentageMarge/100.) * cours;
		prixChoco.get(choco).setValeur(ac, prix);
		return prix ;
	}

	public double quantiteEnVente(ChocolatDeMarque choco) {
		double quantite = ac.getStock().getStockChocolatDeMarque(choco) - 1.;
		quantitesEnVente.get(choco).setValeur(ac, quantite);
		return quantitesEnVente.get(choco).getValeur();
	}

	public void vendre(ClientFinal client, ChocolatDeMarque choco, double quantite, double montant) {
		if (client!=null) { 
			ac.getStock().retirerStockChocolat(choco, quantite);
			journal.ajouter(Journal.texteColore(positiveColor, Color.BLACK, "[VENTE] Vente de " + Journal.doubleSur(quantite,2) + " tonnes de " + choco.name() + " à " + client.getNom() + " pour " + Journal.doubleSur(montant,2) + " (" + Journal.doubleSur(montant/quantite,2) + "/tonne)."));
			totalChocoVendu.get(choco.getChocolat()).setValeur(ac, totalChocoVendu.get(choco.getChocolat()).getValeur() + quantite);
		}
	}

	public void notificationRayonVide(ChocolatDeMarque choco) {
		journal.ajouter(Journal.texteColore(warningColor, Color.BLACK, "[RAYON] Le rayon de " + choco.name() + " est vide."));
	}

	public List<ChocolatDeMarque> pubSouhaitee() {
		return null;
	}

}
