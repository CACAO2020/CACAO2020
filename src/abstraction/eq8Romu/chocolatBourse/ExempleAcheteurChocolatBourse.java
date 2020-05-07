package abstraction.eq8Romu.chocolatBourse;

import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.ChocolatDeMarque;
import abstraction.fourni.Filiere;

public class ExempleAcheteurChocolatBourse extends ExempleAbsAcheteurChocolatBourse implements IAcheteurChocolatBourse {

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

	public void receptionner(ChocolatDeMarque chocolat, double quantite) {
		stocksChocolat.get(chocolat).ajouter(this, quantite);
	}
}
