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
	
	public Stock(Distributeur2 ac) {
			super(ac);			
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
		
		int etape = Filiere.LA_FILIERE.getEtape();

		if (chocoEnStockParEtape.containsKey(etape)) {
			chocoEnStockParEtape.get(etape).put(chocoDeMarque,chocoEnStockParEtape.get(etape).get(chocoDeMarque) + quantite);
		}
		
	}
	
	public void next() {
		// Paiement masse salariale et coûts de stockage
		payerFrais();
		// On jette les chocos périmés
		jeterChocoPerimes();
	}
	
	public void payerFrais() {
		Filiere.LA_FILIERE.getBanque().virer(Filiere.LA_FILIERE.getActeur(getNom()), ac.cryptogramme, Filiere.LA_FILIERE.getActeur("Banque"), ac.coutMasseSalariale + this.coutStockage());
	}
	
	public void jeterChocoPerimes() {
		int etape = Filiere.LA_FILIERE.getEtape();
		if (etape >= this.datePeremption) {
			for (Map.Entry<ChocolatDeMarque, Double> mapEntry : chocoEnStockParEtape.get(etape-this.datePeremption).entrySet()) {
				ChocolatDeMarque choco = mapEntry.getKey();
				double quantiteAJeter = mapEntry.getValue();
				if (quantiteAJeter > 0.) {
					this.retirerStockChocolat(choco, quantiteAJeter);
					// Le message suivant suit un message venant d'une diminution de stocks "normale", il y a double message, ce n'est pas très optimisé...
					journal.ajouter(Journal.texteColore(peremptionColor, Color.BLACK, "[STOCK -] " + Journal.doubleSur(quantiteAJeter,2) + " tonnes de " + choco.name() + " ont périmé et ont été retirées du stock (nouveau stock : " + Journal.doubleSur(stocksChocolatDeMarque.get(choco).getValeur(),2) + " tonnes)."));
				}
			}
		}
	}
	
	public double coutStockage() {
		double prix1tonne = 720.0;
		double prixStockage = 0.0;
		double qtite = 0.0;
		for (Chocolat choco : ac.nosChoco) {
			qtite += this.getStockChocolat(choco);
		}
		prixStockage = qtite*prix1tonne;
		return prixStockage;
	}
	
	public void majStocksParEtape(ChocolatDeMarque chocoDeMarque, double quantite) {

		int etapeActuelle = Filiere.LA_FILIERE.getEtape();
		if (this.chocoEnStockParEtape.containsKey(etapeActuelle)) {
			int etapeDuPlusVieuxStock = this.etapeDuPlusVieuxStock.get(chocoDeMarque);
			
			int etape = etapeDuPlusVieuxStock;
			double quantiteRestanteARetirer = quantite;
			double quantiteEtapeARetirer;
			
			// On retire successivement les quantités de chocolat stockés par ordre chronologique jusqu'à en avoir retiré assez
			while (quantiteRestanteARetirer != 0.) {
				quantiteEtapeARetirer = Double.min(quantiteRestanteARetirer, this.chocoEnStockParEtape.get(etape).get(chocoDeMarque));
				this.chocoEnStockParEtape.get(etape).put(chocoDeMarque, this.chocoEnStockParEtape.get(etape).get(chocoDeMarque) - quantiteEtapeARetirer);
				quantiteRestanteARetirer -= quantiteEtapeARetirer;
				etape += 1;
			}
			
			// On repart de la dernière étape à laquelle on a enlevé du chocolat et on recherche la nouvelle étape du plus vieux stock
			etape -= 1;
			while (etape <= etapeActuelle) {
				if (this.chocoEnStockParEtape.get(etape).get(chocoDeMarque) == 0.) {
					etape += 1;
				} else {
					this.etapeDuPlusVieuxStock.put(chocoDeMarque, etape);
					etape = etapeActuelle + 2;
				}
			}
			
			// Si on n'a plus de stock du tout, on dit que la nouvelle étape du plus vieux stock est l'étape actuelle
			if (etape == etapeActuelle + 1) {
				this.etapeDuPlusVieuxStock.put(chocoDeMarque, etapeActuelle);
			}
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
			this.majStocksParEtape(chocoDeMarque, quantite);
						
		} else {
				journal.ajouter(Journal.texteColore(alertColor, Color.BLACK,"[ÉCHEC STOCK -] Tentative de retirer " + Journal.doubleSur(quantite,2) + " tonnes de " + chocoDeMarque.name() + " rejetée (stock actuel : " + Journal.doubleSur(stocksChocolatDeMarque.get(chocoDeMarque).getValeur(),2) + " tonnes)."));
		}
	}

	public void initialiser() {		
		for (ChocolatDeMarque choco : ac.tousLesChocolatsDeMarquePossibles()) {
			etapeDuPlusVieuxStock.put(choco, 0);
			stocksChocolatDeMarque.put(choco, new Variable(ac.getNom() + " : STOCK " + choco.name(), ac, 0.));
			journal.ajouter(Journal.texteColore(metaColor, Color.BLACK,"[CRÉATION] Création d'un stock pour le " + choco.name() + "."));
		}
	}
	
}
