package abstraction.eq7Distributeur2;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import abstraction.eq8Romu.chocolatBourse.IAcheteurChocolatBourse;
import abstraction.eq8Romu.chocolatBourse.SuperviseurChocolatBourse;
import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.ChocolatDeMarque;
import abstraction.eq8Romu.produits.Feve;
import abstraction.eq8Romu.produits.Gamme;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;

public class AcheteurChocolat extends AbsAcheteurChocolat implements IAcheteurChocolatBourse, IActeur {
	//Raphaël Caby
	
	protected Map<Chocolat, Variable> demandesChoco;
	
	public AcheteurChocolat(Distributeur2 ac) {
		super(ac);
		demandesChoco = new HashMap<Chocolat, Variable>();
		for (Chocolat choco : Chocolat.values()) {
			demandesChoco.put(choco, new Variable("Demande en " + choco.name(), ac, 0));
		}
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
			journal.ajouter(Journal.texteColore(warningColor, Color.BLACK, "[IMPAIEMENT] Confirmation d'une commande de " + Journal.doubleSur(quantiteObtenue,2) + " tonnes de " + chocolat.name() + "."));
		}
		demandesChoco.get(chocolat).setValeur(ac, demandesChoco.get(chocolat).getValeur()-quantiteObtenue);
	}
	
	public void receptionner(ChocolatDeMarque chocolat, double quantite) {
		ac.getStock().ajouterStockChocolat(chocolat, quantite);
		journal.ajouter(Journal.texteColore(positiveColor, Color.BLACK, "[RÉCEPTION] Réception de " + Journal.doubleSur(quantite,2) + " tonnes de " + chocolat.name() + "."));
		ac.getDistributeurChocolat().ajouterProduitAuCatalogue(chocolat);
	}

	public void initialiser() {
	}
	
	public void next() {
		// L'opération sera effectuée pour CHAQUE type de chocolat que nous vendons
		for (Chocolat choco : Chocolat.values()) {
			// D'abord on consulte les stocks
			double stockChoco = ac.getStock().getStockChocolat(choco);
			// Ensuite on demande au vendeur quelle quantité lui est demandée
			double demandeVendeur = 10.;   //Le temps de progresser dans le fichier vendeur
			// On compare la demande du vendeur et les stocks
			double achatsAFaire;
			if (choco.getGamme() == Gamme.BASSE) {
				achatsAFaire = 0;
			} else {
				achatsAFaire = max(demandeVendeur - stockChoco, 0.);
			}	
			demandesChoco.get(choco).setValeur(this, achatsAFaire);
		}
	}

	public double max(double d1, double d2) {
		if (d1 < d2) {
			return d2;
		}
		return d1;
	}

}
