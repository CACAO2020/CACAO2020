package abstraction.eq7Distributeur2;

import abstraction.eq8Romu.chocolatBourse.IAcheteurChocolatBourse;
import abstraction.eq8Romu.chocolatBourse.SuperviseurChocolatBourse;
import abstraction.eq8Romu.produits.Chocolat;
import abstraction.fourni.Filiere;

public class AcheteurChocolatBourse extends AbsAcheteurChocolatBourse implements IAcheteurChocolatBourse {

	public double getDemande(Chocolat chocolat, double cours) {
		double solde = Filiere.LA_FILIERE.getBanque().getSolde(this,  cryptogramme);
		double max = solde/cours;
		//return 500; // si il retourne 500 sans verifier si il peut se le permettre il va parvenir a un impaye qu'il devra regler pour pouvoir a nouveau acheter a la bourse
		return Math.random()*max;// les cours vont s'effondrer car les acheteurs vont tres vite ne plus avoir assez d'argent pour acheter. Augmentez le solde des acheteurs via l'interface si vous voulez voir les cours repartir à la hausse
	}

	public Integer getCryptogramme(SuperviseurChocolatBourse superviseur) {
		return cryptogramme;
	}

	public void notifierCommande(Chocolat chocolat, double quantiteObtenue, boolean payee) {
		
	}

	public void receptionner(Chocolat chocolat, double quantite) {
		
		
	}
	
	public void next() {
		// L'opération sera effectuée pour CHAQUE type de chocolat que nous vendons
		//D'abord on consulte les stocks
		
		//Ensuite on demande au vendeur quelle quantité lui est demandée
		//On compare la demande du vendeur et les stocks
		//
	}

}
