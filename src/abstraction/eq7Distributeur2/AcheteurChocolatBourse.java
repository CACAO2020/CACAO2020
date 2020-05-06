package abstraction.eq7Distributeur2;

import abstraction.eq8Romu.chocolatBourse.IAcheteurChocolatBourse;
import abstraction.eq8Romu.chocolatBourse.SuperviseurChocolatBourse;
import abstraction.eq8Romu.produits.Chocolat;
import abstraction.fourni.Filiere;

public class AcheteurChocolatBourse extends AbsAcheteurChocolatBourse implements IAcheteurChocolatBourse {

	public double getDemande(Chocolat chocolat, double cours) {
		double solde = Filiere.LA_FILIERE.getBanque().getSolde(this,  cryptogramme);
		double max = solde/cours; // représente la quantité maximale en tonnes de chocolat achetable au prix cours sans se mettre à découvert  
		return Math.random()*max; 
		// les cours vont s'effondrer car les acheteurs vont tres vite ne plus avoir assez d'argent pour acheter. Augmentez le solde des acheteurs via l'interface si vous voulez voir les cours repartir à la hausse
	
	}

	public Integer getCryptogramme(SuperviseurChocolatBourse superviseur) {
		return cryptogramme;
	}

	public void notifierCommande(Chocolat chocolat, double quantiteObtenue, boolean payee) {
	}

	public void receptionner(Chocolat chocolat, double quantite) {
		stocksChocolat.get(chocolat).ajouter(this, quantite);
	}
}
