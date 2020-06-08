package abstraction.eq7Distributeur2;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.ChocolatDeMarque;
import abstraction.eq8Romu.contratsCadres.Echeancier;
import abstraction.eq8Romu.produits.Feve;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;
//cette classe permet de gerer les mouvement de stock
public class Stock extends AbsStock implements IStock {
	private int datePeremption;
	
	public Stock(Distributeur2 ac) {
			super(ac);
			this.datePeremption = 12; //Donc une date de péremption de 6 mois car 12 steps
			
	}
	
	public double getStockChocolat(Chocolat choco) {
		if (stocksChocolat.containsKey(choco)) {
			return stocksChocolat.get(choco).getValeur();
		} else {
			return 0.;
		}
	}
	
	public double getStockChocolatDeMarque(ChocolatDeMarque chocoDeMarque) {
		if (stocksChocolatDeMarque.containsKey(chocoDeMarque)) {
			return stocksChocolatDeMarque.get(chocoDeMarque).getValeur();
		} else {
			return 0.;
		}
	}
	//ajoute les quantités necessaires de chocolat des stocks correspondant 
	public void ajouterStockChocolat(ChocolatDeMarque chocoDeMarque, double quantite) {
		if (!stocksChocolatDeMarque.containsKey(chocoDeMarque)) {
		}
		if (!stocksChocolat.containsKey(chocoDeMarque.getChocolat())) {
		}
		stocksChocolatDeMarque.get(chocoDeMarque).setValeur(ac, stocksChocolatDeMarque.get(chocoDeMarque).getValeur() + quantite);
		stocksChocolat.get(chocoDeMarque.getChocolat()).setValeur(ac, stocksChocolat.get(chocoDeMarque.getChocolat()).getValeur() + quantite);
		journal.ajouter(Journal.texteColore(addStockColor, Color.BLACK, "[STOCK +] " + Journal.doubleSur(quantite,2) + " tonnes de " + chocoDeMarque.name() + " ont été ajoutées au stock (nouveau stock : " + Journal.doubleSur(stocksChocolatDeMarque.get(chocoDeMarque).getValeur(),2) + " tonnes)."));
		
		int etape = Filiere.LA_FILIERE.getEtape()+this.datePeremption;
		
		if (datesLimites.containsKey(chocoDeMarque)) {
			datesLimites.get(chocoDeMarque).set(etape, quantite);
		} else {
			List<Double> quantites;
			quantites = new ArrayList<Double>();
			quantites.add(quantite);
			datesLimites.put(chocoDeMarque, new Echeancier(etape, quantites));
		}
		
	}
	public void next() {
		//On retire les chocos périmés
		Filiere.LA_FILIERE.getBanque().virer(Filiere.LA_FILIERE.getActeur(getNom()), ac.getCryptogramme(superviseur), Filiere.LA_FILIERE.getActeur("Banque"), ac.masseSalarialeParNext+ this.prixStockageParNext());
		for (Map.Entry<ChocolatDeMarque, Echeancier> mapentry : datesLimites.entrySet()) {
			Echeancier echeancier = mapentry.getValue();
			ChocolatDeMarque chocoDeMarque = mapentry.getKey();
			int stepactuelle = Filiere.LA_FILIERE.getEtape();
			double quantiteASuppr = echeancier.getQuantite(stepactuelle);
			if (quantiteASuppr > 0.) {
				this.retirerStockChocolat(chocoDeMarque, quantiteASuppr);
				// Le message suivant suit un message venant d'une diminution de stocks "normale", il y a double message, ce n'est pas très optimisé...
				journal.ajouter(Journal.texteColore(peremptionColor, Color.BLACK, "[STOCK -] " + Journal.doubleSur(quantiteASuppr,2) + " tonnes de " + chocoDeMarque.name() + " ont périmé et on été retirées du stock (nouveau stock : " + Journal.doubleSur(stocksChocolatDeMarque.get(chocoDeMarque).getValeur(),2) + " tonnes)."));
			}
		}
	}
	public double prixStockageParNext() {
		double prix1tonne = 720.0;
		double prixStockage = 0.0;
		double qtite = 0.0;
		for (Chocolat choco : ac.nosChoco) {
			qtite += this.getStockChocolat(choco);
		}
		prixStockage = qtite*prix1tonne;
		return prixStockage;
	}
	
	public void retirerStockChocolatPerime(ChocolatDeMarque chocoDeMarque, double quantite) {
		Echeancier echeancier = datesLimites.get(chocoDeMarque);
		int stepactuelle = Filiere.LA_FILIERE.getEtape();
		int stepecheance = stepactuelle;
		boolean bool = echeancier.getQuantite(echeancier.getStepFin()) != 0.;
		if (bool) {
			// On a au moins une échéance !
			while (echeancier.getQuantite(stepecheance) == 0.) {
				stepecheance ++;
			}
			double quantite_actuelle = echeancier.getQuantite(stepecheance);
			if (quantite_actuelle >= quantite) {
				// Situation simple, on met simplement à jour la quantité qui périmera à cette échéance
				echeancier.set(stepecheance, quantite_actuelle - quantite);
			}
			else {
				// Là il va falloir trouver une autre échéance à modifier
				echeancier.set(stepecheance, 0);
				this.retirerStockChocolatPerime(chocoDeMarque, quantite - quantite_actuelle);
			}
		}
		else {
			// On ne fait rien, car il n'y a rien à faire
		}
	}
	
	//retire les quantités necessaires de chocolat des stocks correspondant (avec la prise en compte des potentiels echecs de mouvements)
	public void retirerStockChocolat(ChocolatDeMarque chocoDeMarque, double quantite) {
		if (!stocksChocolatDeMarque.containsKey(chocoDeMarque)) {
		}
		if (!stocksChocolat.containsKey(chocoDeMarque.getChocolat())) {
		}
		if (stocksChocolatDeMarque.get(chocoDeMarque).getValeur() >= quantite) {
			stocksChocolatDeMarque.get(chocoDeMarque).setValeur(ac, stocksChocolatDeMarque.get(chocoDeMarque).getValeur() - quantite);
			stocksChocolat.get(chocoDeMarque.getChocolat()).setValeur(ac, stocksChocolat.get(chocoDeMarque.getChocolat()).getValeur() - quantite);
			journal.ajouter(Journal.texteColore(removeStockColor, Color.BLACK, "[STOCK -] " + Journal.doubleSur(quantite,2) + " tonnes de " + chocoDeMarque.name() + " ont été retirées du stock (nouveau stock : " + Journal.doubleSur(stocksChocolatDeMarque.get(chocoDeMarque).getValeur(),2) + " tonnes)."));

			this.retirerStockChocolatPerime(chocoDeMarque, quantite);
						
		} else {
				journal.ajouter(Journal.texteColore(alertColor, Color.BLACK,"[ÉCHEC STOCK -] Tentative de retirer " + Journal.doubleSur(quantite,2) + " tonnes de " + chocoDeMarque.name() + " rejetée (stock actuel : " + Journal.doubleSur(stocksChocolatDeMarque.get(chocoDeMarque).getValeur(),2) + " tonnes)."));
		}
	}

	public void initialiser() {		
		for (ChocolatDeMarque choco : ac.tousLesChocolatsDeMarquePossibles()) {
			stocksChocolatDeMarque.put(choco, new Variable(ac.getNom() + " : STOCK " + choco.name(), ac, 0.));
			journal.ajouter(Journal.texteColore(metaColor, Color.BLACK,"[CRÉATION] Création d'un stock pour le " + choco.name() + "."));
		}
	}
	
}
