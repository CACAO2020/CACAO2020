package abstraction.eq6Distributeur1;

import java.util.HashMap;

import abstraction.eq8Romu.chocolatBourse.IAcheteurChocolatBourse;
import abstraction.eq8Romu.chocolatBourse.SuperviseurChocolatBourse;
import abstraction.eq8Romu.produits.Chocolat;
/**
public class AchatBourseEQ6 extends Stock implements IAcheteurChocolatBourse{
    
	private HashMap<Integer, Integer> historiqueBrouse;
	
	public double EvolutionDemandeTotal (){
		//anakyse dernière deuxD dernière année évolution = année-1 + (année-1 -année-2)/2
		demandethis.gethistorique()
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
	
		
	
		public void notifierCommande(Chocolat chocolat, double quantiteObtenue, boolean payee) {
			if (quantiteObtenue * chocolat.getMontant() )
		}
	
		public void receptioner(Chocolat chocolat, double quantite) {
			this.Stocker(chocolat,  quantite);
		}

		
		
		public Integer getCryptogramme(SuperviseurChocolatBourse superviseur) {
			if (superviseur !=null) {
				return this.cryptogramme;
			}
			return null;
		}

		@Override
		public void notifierCommande(Chocolat chocolat, double quantiteObtenue, boolean payee) {
			// TODO Auto-generated method stub
			
		}

		
	
    
}
*/
