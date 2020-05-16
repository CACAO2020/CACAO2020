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
	protected List<ChocolatDeMarque> produitsCatalogue;
	protected Journal journalCatalogue;
	
	public DistributeurChocolat(Distributeur2 ac) {
		super(ac);
		produitsCatalogue = new ArrayList<ChocolatDeMarque>();
		journalCatalogue = new Journal(ac.getNom() + " : Catalogue du distributeur", ac);
		journalCatalogue.ajouter(Journal.texteColore(titleColor, Color.WHITE, this.getNom() + " : Catalogue du distributeur"));
		journalCatalogue.ajouter(Journal.texteColore(descriptionColor, Color.BLACK, "Ce journal présente le contenu du catalogue du distributeur à chaque étape :"));
		journalCatalogue.ajouter(Journal.texteColore(descriptionColor, Color.BLACK, "types de chocolat en vente, quantités disponibles à la vente et prix à l'unité."));
		journalCatalogue.ajouter(Journal.texteColore(descriptionColor, Color.BLACK, "Il est mis à jour à chaque appel de la commande next() du distributeur,"));
		journalCatalogue.ajouter(Journal.texteColore(descriptionColor, Color.BLACK, "soit APRÈS la vente au client final et AVANT la réception des commandes de l'étape."));
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
		dessinerCatalogue();
	}
	
	public void ajouterProduitAuCatalogue(ChocolatDeMarque choco) {
		if (!produitsCatalogue.contains(choco)) {
			produitsCatalogue.add(choco);
			journalCatalogue.ajouter(Journal.texteColore(positiveColor, Color.BLACK, "[AJOUT] Le chocolat " + choco.name() + " a été ajouté au catalogue."));
		}
	}
	
	private void dessinerCatalogue() {
		journalCatalogue.ajouter(Journal.texteColore(metaColor, Color.BLACK, "[ETAPE " + Filiere.LA_FILIERE.getEtape() + "] Catalogue du distributeur"));
		journalCatalogue.ajouter(Journal.texteSurUneLargeurDe("Chocolat", 40) + Journal.texteSurUneLargeurDe("Quantité (tonnes)", 20) + Journal.texteSurUneLargeurDe("Prix", 20) + Journal.texteSurUneLargeurDe("", 30));
		for (ChocolatDeMarque choco : produitsCatalogue) {
			journalCatalogue.ajouter(Journal.texteSurUneLargeurDe(choco.name(), 40) + Journal.texteSurUneLargeurDe("" + Journal.doubleSur(quantiteEnVente(choco),2), 20) + Journal.texteSurUneLargeurDe("" + Journal.doubleSur(prix(choco),2), 20) + Journal.texteSurUneLargeurDe("", 30));
		}
	}
	
	public List<ChocolatDeMarque> getCatalogue() {
		return produitsCatalogue;
	}

	public double prix(ChocolatDeMarque choco) {
		double cours;
		double pourcentageMarge = 50;
		if (Filiere.LA_FILIERE.getEtape() > 1) {
            cours = Filiere.LA_FILIERE.getIndicateur("BourseChoco cours " + choco.getChocolat().name()).getHistorique().get(Filiere.LA_FILIERE.getEtape()-1).getValeur();
        } else {
        	cours = prixParDefaut.get(choco.getChocolat());
        }
		return (1. + pourcentageMarge/100.) * cours;
	}

	public double quantiteEnVente(ChocolatDeMarque choco) {
		return ac.getStock().getStockChocolatDeMarque(choco);
	}

	public void vendre(ClientFinal client, ChocolatDeMarque choco, double quantite, double montant) {
		if (client!=null) { 
			ac.getStock().retirerStockChocolat(choco, quantite);
			journal.ajouter(Journal.texteColore(positiveColor, Color.BLACK, "[VENTE] Vente de " + quantite + " tonnes de " + choco.name() + " à " + client.getNom() + " pour " + montant + " (" + montant/quantite + "/tonne)."));
		}
	}

	public void notificationRayonVide(ChocolatDeMarque choco) {
		journal.ajouter(Journal.texteColore(warningColor, Color.BLACK, "[RAYON] Le rayon de " + choco.name() + " est vide."));
	}

	public List<ChocolatDeMarque> pubSouhaitee() {
		return null;
	}
	
	public List<Variable> getIndicateurs() {
		List<Variable> res = new ArrayList<Variable>();
		return res;
	}

	public List<Variable> getParametres() {
		List<Variable> res=new ArrayList<Variable>();
		return res;
	}

	public List<Journal> getJournaux() {
		List<Journal> res = new ArrayList<Journal>();
		res.add(journal);
		res.add(journalCatalogue);
		return res;
	}

}
