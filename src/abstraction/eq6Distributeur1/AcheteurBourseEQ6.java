package abstraction.eq6Distributeur1;

import abstraction.eq8Romu.chocolatBourse.IAcheteurChocolatBourse;

public class AcheteurBourseEQ6 extends Stock implements IAcheteurChocolatBourse{
    
	public double EvolutionDemandeTotal (){
		//anakyse dernière deux dernière année évolution = année-1 + (année-1 -année-2)/2
		
		return 0.0;
	}
	
	public double EvolutionDemandeChocolatPourcentage(){
		//pareil que l'autre, mais pour le chocolat en question, en pourcentage
		return 0;
	}
	
	public double getDemande(Chocolat chocolat, double cours) {
			double solde = Filiere.LA_FILIERE.getBanque().getSolde(this,  cryptogramme); // retourne l'argent du compte
			double max = solde/cours;
			double a = this.quantiteEnstockTypeChoco( chocolat);
			double b = this.quantiteEnStockTotal();
			double DemandeTotal = this.EvolutionDemandeTotal ();
			double DeamndeChoco = this.DemandeChocolatPourcentage(chocolat);
			if (DeamndeChoco<a) {
				return 0;
				
			}
			if (DeamndeChoco>=a) {
				return (DeamndeChoco*DemandeTotal - a);//calcul quantité en stock souhaite- quantité en stock actuel
				
			}
			// les cours vont s'effondrer car les acheteurs vont tres vite ne plus avoir assez d'argent pour acheter. Augmentez le solde des acheteurs via l'interface si vous voulez voir les cours repartir à la hausse
		}
	
		public Integer getCryptogramme(SuperviseurChocolatBourse superviseur) {
			return cryptogramme;
		}
	
		public void notifierCommande(Chocolat chocolat, double quantiteObtenue, boolean payee) {
			
		}
	
		public void receptionner(Chocolat chocolat, double quantite) {
			this.stocksChocolat.get(chocolat).ajouter(this, quantite);
		}
	
    
}
