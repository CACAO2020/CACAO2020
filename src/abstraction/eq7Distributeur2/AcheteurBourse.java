package abstraction.eq7Distributeur2;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import abstraction.eq8Romu.chocolatBourse.CommandeChocolat;
import abstraction.eq8Romu.chocolatBourse.IAcheteurChocolatBourse;
import abstraction.eq8Romu.chocolatBourse.SuperviseurChocolatBourse;
import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.ChocolatDeMarque;
import abstraction.eq8Romu.produits.Feve;
import abstraction.eq8Romu.produits.Gamme;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;

public class AcheteurBourse extends AbsAcheteurBourse implements IAcheteurChocolatBourse, IActeur {
	//Raphaël Caby
	
	public AcheteurBourse(Distributeur2 ac) {
		super(ac);
	}
	
	public void initialiser() {
	}
	
	public void next() {
		// L'acheteur à la bourse récupère les données du distributeur et met à jour les quantités de chaque type de chocolat à commander
		majQuantitesACommander();
	}
	
	public Map<Chocolat, Variable> getDemandes() {
		return demandesChoco;
	}
	
	public double getDemande(Chocolat chocolat, double cours) {
		return demandesChoco.get(chocolat).getValeur();
	}

	public Integer getCryptogramme(SuperviseurChocolatBourse superviseur) {
		return ac.getCryptogramme(superviseur);
	}

	public void notifierCommande(Chocolat chocolat, double quantiteObtenue, boolean payee) {
		if (payee) {
			journal.ajouter(Journal.texteColore(positiveColor, Color.BLACK, "[PAIEMENT] Confirmation d'une commande de " + Journal.doubleSur(quantiteObtenue,2) + " tonnes de " + chocolat.name() + "."));
		} else {
			journal.ajouter(Journal.texteColore(warningColor, Color.BLACK, "[IMPAIEMENT] La commande de " + Journal.doubleSur(quantiteObtenue,2) + " tonnes de " + chocolat.name() + " est impayée."));
			commandeImpayee = new Pair<Chocolat, Double>(chocolat, quantiteObtenue);
		}
		demandesChoco.get(chocolat).setValeur(ac, demandesChoco.get(chocolat).getValeur()-quantiteObtenue);
	}
	
	public void receptionner(ChocolatDeMarque chocolat, double quantite) {
		ac.getStock().ajouterStockChocolat(chocolat, quantite);
		if (commandeImpayee != null) {
			journal.ajouter(Journal.texteColore(warningColor, Color.BLACK, "[PAIEMENT] La commande impayée de " + Journal.doubleSur(quantite,2) + " tonnes de " + chocolat.name() + " a été payée."));
			commandeImpayee = null;

		}
		journal.ajouter(Journal.texteColore(positiveColor, Color.BLACK, "[RÉCEPTION] Réception de " + Journal.doubleSur(quantite,2) + " tonnes de " + chocolat.name() + "."));
		chocoReceptionne.get(chocolat.getChocolat()).setValeur(ac, chocoReceptionne.get(chocolat.getChocolat()).getValeur() + quantite);
	}
	
	public void majQuantitesACommander() {
		double quantiteACommander;
		for (Chocolat choco : ac.nosChoco) {
			chocoReceptionne.get(choco).setValeur(ac, 0.);
			quantiteACommander = ac.getVendeur().quantitesACommanderBourse.get(choco).getValeur();
			demandesChoco.get(choco).setValeur(this, quantiteACommander);
		}
		
		for (Chocolat choco : Chocolat.values()) {
			if (!ac.nosChoco.contains(choco)) {
				demandesChoco.get(choco).setValeur(this, 0.);
			} 
		}
	}

}
