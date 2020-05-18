package abstraction.eq7Distributeur2;

import java.util.Map;

import abstraction.eq8Romu.chocolatBourse.IAcheteurChocolatBourse;
import abstraction.eq8Romu.chocolatBourse.SuperviseurChocolatBourse;
import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.ChocolatDeMarque;
import abstraction.eq8Romu.produits.Feve;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;
import abstraction.fourni.Variable;


public class AcheteurChocolat extends AbsAcheteurChocolat implements IAcheteurChocolatBourse {
	//Raphaël Caby
	
	public AcheteurChocolat(Distributeur2 ac) {
		super(ac);
	}
	
	public double getDemande(ChocolatDeMarque chocolat, double cours) {
		return this.getDemande_choco().get(chocolat).getValeur();
	}

	public Integer getCryptogramme(SuperviseurChocolatBourse superviseur) {
		return cryptogramme;
	}

	public void notifierCommande(ChocolatDeMarque chocolat, double quantiteObtenue, boolean payee) {
		int i = this.getJournaux().size();
		String s = "";
		if (payee) {s = "Commande payée";}
		else {s = "Commande non payée";}
		s = s + "de " + chocolat.getMarque();
		getJournaux().get(i - 1).ajouter(s);;
		
	}
	
	public void receptionner(ChocolatDeMarque chocolat, double quantite) {
		ac.getStock().ajouterStockChocolat(chocolat, quantite);
	}

	public void next() {
		for (Chocolat choco : ) {
		// L'opération sera effectuée pour CHAQUE gamme de chocolat que nous vendons
		//D'abord on consulte les stocks
			double stock_choco = ac.getStock().stocksChocolat.get(choco).getValeur();
		//Ensuite on demande au vendeur quelle quantité lui est demandée
			double demande_vendeur = 15.;   //Le temps de progresser dans le fichier vendeur
		//On compare la demande du vendeur et les stocks
			double achats_a_faire = demande_vendeur - stock_choco;
			if (achats_a_faire <= 0.) {
				//Si achats_a_faire < 0 alors on n'achete rien
				this.getDemande_choco().get(choco).setValeur(this, 0.);
			}
			else {
				//Sinon on positionne la demande sur achats_a_faire
				this.getDemande_choco().get(choco).setValeur(this, achats_a_faire);
			}
			
		
		}
	}

	// NE PAS UTILISER
	public double getDemande(Chocolat chocolat, double cours) {
		return 0;
	}

	// NE PAS UTILISER
	public void notifierCommande(Chocolat chocolat, double quantiteObtenue, boolean payee) {
		
	}

}
