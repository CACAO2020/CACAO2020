package abstraction.eq7Distributeur2;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import abstraction.eq8Romu.chocolatBourse.CommandeChocolat;
import abstraction.eq8Romu.chocolatBourse.IAcheteurChocolatBourse;
import abstraction.eq8Romu.chocolatBourse.SuperviseurChocolatBourse;
import abstraction.eq8Romu.contratsCadres.ExemplaireContratCadre;
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
	
	public void next() {
		// L'acheteur à la bourse détermine quelle quantité de chaque type de chocolat commander
		majAchatsBourse();
	}
	
	public AcheteurBourse(Distributeur2 ac) {
		super(ac);
	}
	
	public void initialiser() {
	}
	
	// Création d'une table des demandes (du vendeur) qui tiendra à jour quelle est la demande pour chaque type de chocolat
	public Map<Chocolat, Variable> getDemandes() {
		return quantitesACommander;
	}
	
	// Renvoie la demande actuelle pour un type de chocolat donné
	public double getDemande(Chocolat chocolat, double cours) {
		return quantitesACommander.get(chocolat).getValeur();
	}

	public Integer getCryptogramme(SuperviseurChocolatBourse superviseur) {
		return ac.getCryptogramme(superviseur);
	}
	
	// Méthode qui notifie les commandes lorsqu'elles ont lieu : elle inscrit les commandes dans les journaux et met à jour la table des demandes du vendeur
	public void notifierCommande(Chocolat chocolat, double quantiteObtenue, boolean payee) {
		if (payee) {
			journal.ajouter(Journal.texteColore(positiveColor, Color.BLACK, "[PAIEMENT] Confirmation d'une commande de " + Journal.doubleSur(quantiteObtenue,2) + " tonnes de " + chocolat.name() + "."));
		} else {
			journal.ajouter(Journal.texteColore(warningColor, Color.BLACK, "[IMPAIEMENT] La commande de " + Journal.doubleSur(quantiteObtenue,2) + " tonnes de " + chocolat.name() + " est impayée."));
			commandeImpayee = new Pair<Chocolat, Double>(chocolat, quantiteObtenue);
		}
		quantitesACommander.get(chocolat).setValeur(ac, quantitesACommander.get(chocolat).getValeur()-quantiteObtenue);
	}
	
	// Méthode qui, une fois un achat validé, met à jour les stocks, paye la commande si besoin et inscrit le tout dans le journal correspondant
	public void receptionner(ChocolatDeMarque chocolat, double quantite) {
		ac.getStock().ajouterStockChocolat(chocolat, quantite);
		if (commandeImpayee != null) {
			journal.ajouter(Journal.texteColore(warningColor, Color.BLACK, "[PAIEMENT] La commande impayée de " + Journal.doubleSur(quantite,2) + " tonnes de " + chocolat.name() + " a été payée."));
			commandeImpayee = null;

		}
		journal.ajouter(Journal.texteColore(positiveColor, Color.BLACK, "[RÉCEPTION] Réception de " + Journal.doubleSur(quantite,2) + " tonnes de " + chocolat.name() + "."));
		ac.getStock().chocoReceptionne.get(chocolat.getChocolat()).setValeur(ac, ac.getStock().chocoReceptionne.get(chocolat.getChocolat()).getValeur() + quantite);
	}
	
	// Méthode qui calcule la quantité qui doit être achetée en bourse, en tenant compte des contrats, pour chaque gamme de chocolat. La table des demandes est tenue à jour.
	public void majAchatsBourse() {
		// IA : quantité à commander = quantité demandée par le vendeur MOINS les quantités reçues à l'étape suivante grâce aux contrats actuels
		double quantiteTotaleACommander;
		double quantiteACommander;
		double quantiteARecevoirParContrats = 0.;
		for (Chocolat choco : ac.nosChoco) {
			ac.getStock().chocoReceptionne.get(choco).setValeur(ac, 0.);
			Chocolat typeChoco;
			for (ExemplaireContratCadre contrat : ac.getAcheteurContratCadre().nosContrats) {
				if (contrat.getProduit() instanceof Chocolat) {
					typeChoco = ((Chocolat)contrat.getProduit());
				} else {
					typeChoco = ((ChocolatDeMarque)contrat.getProduit()).getChocolat();
				}
				if (choco == typeChoco) {
					quantiteARecevoirParContrats += contrat.getQuantiteALivrerAuStep();
				}
			}
			quantiteTotaleACommander = ac.getVendeur().getQuantiteACommander(choco);
			quantiteACommander = quantiteTotaleACommander - quantiteARecevoirParContrats; 
			quantitesACommander.get(choco).setValeur(ac, quantiteACommander);
		}
	}
}
