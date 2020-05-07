package abstraction.eq7Distributeur2;

import java.util.Map;

import abstraction.eq8Romu.chocolatBourse.IAcheteurChocolatBourse;
import abstraction.eq8Romu.chocolatBourse.SuperviseurChocolatBourse;
import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.Feve;
import abstraction.fourni.Filiere;
import abstraction.fourni.Variable;

public class AcheteurChocolatBourse extends AbsAcheteurChocolatBourse implements IAcheteurChocolatBourse {
	//Raphaël Caby
	public double getDemande(Chocolat chocolat, double cours) {
		return this.getDemande_choco().get(chocolat).getValeur();
	}

	public Integer getCryptogramme(SuperviseurChocolatBourse superviseur) {
		return cryptogramme;
	}

	public void notifierCommande(Chocolat chocolat, double quantiteObtenue, boolean payee) {
		
	}

	public void receptionner(Chocolat chocolat, double quantite) {
		
		
	}
	
	public void next() {
		for (Chocolat choco : Chocolat.values()) {
		// L'opération sera effectuée pour CHAQUE type de chocolat que nous vendons
		//D'abord on consulte les stocks
			double stock_choco = stocksChocolat.get(choco).getValeur();
		//Ensuite on demande au vendeur quelle quantité lui est demandée
			double demande_vendeur = 15.;   //Le temps de progresser dans le fichier vendeur
		//On compare la demande du vendeur et les stocks
			double achats_a_faire = stock_choco - demande_vendeur;
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

}
