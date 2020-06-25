package abstraction.eq6Distributeur1;

import java.util.HashMap;
import java.util.Map;

import abstraction.eq8Romu.chocolatBourse.IAcheteurChocolatBourse;
import abstraction.eq8Romu.chocolatBourse.SuperviseurChocolatBourse;
import abstraction.eq8Romu.clients.ClientFinal;
import abstraction.eq8Romu.contratsCadres.ExemplaireContratCadre;
import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.ChocolatDeMarque;
import abstraction.fourni.Filiere;

public class AchatBourseEQ6 extends Stock implements IAcheteurChocolatBourse{
    
	public AchatBourseEQ6(double capaciteStockmax) {
		super(capaciteStockmax);
		// TODO Auto-generated constructor stub
	}


	
	protected HashMap<Integer, HashMap<Chocolat, Double>> historiqueBourse;
	
	
	
	
	//mettre à jour EvolutionDemandeChocolat avec VenteSiPasRuptureDeStock à la place de Filiere.LA_FILIERE.getVentes
	public double EvolutionDemandeChocolat(Chocolat chocolat){ // il faut prendre en compte les ruptures de stocks, il faut prendre en compte les contrats cadre
		//avoir acces à cette hash map, puis calculer la demande Chocolat et pas chocolatDE Marque
		
		
		if (Filiere.LA_FILIERE.getEtape()>24) {
		double anneeYa1AN = quantiteVenduTypeChoco(chocolat,24) *1.1; //*1.1 pour avoir un peu de stock
		
		
		
		if (Filiere.LA_FILIERE.getEtape()>48) {
			double anneeYa2AN = quantiteVenduTypeChoco(chocolat,48);//pareil que l'autre, mais pour le chocolat en question, en pourcentage

			//double anneeYa2AN = Filiere.LA_FILIERE.getVentes(Filiere.LA_FILIERE.getEtape()-48+1, chocolat );//pareil que l'autre, mais pour le chocolat en question, en pourcentage
			
		    
			return (anneeYa1AN + (anneeYa1AN-anneeYa2AN)/2)*1.1;//*1.1 pour avoir un peu de stock
			
		}

		if (Filiere.LA_FILIERE.getEtape()<=48) {
			return anneeYa1AN;
			
		}
		return 0;
		
		}
		if (Filiere.LA_FILIERE.getEtape()<=24) {
			double anneeYa1AN = Filiere.LA_FILIERE.getVentes(Filiere.LA_FILIERE.getEtape()-24+1, chocolat );
			
			

		
				return anneeYa1AN/2;
				
			
			
			}
		return 0;

		
		
	}
	
	public double quantiteVenduTypeChoco(Chocolat choco,int CombienDeTour) { //pas fonctionelle, coder pour avoir touts les chocolats vendus
		double quantite = 0;
			for (ChocolatDeMarque chocos :ClientFinal.tousLesChocolatsDeMarquePossibles()) {
				if (chocos.getChocolat()==choco) { // faire une fonction pour 
				if(VenteSiPasRuptureDeStock.get(Filiere.LA_FILIERE.getEtape()- CombienDeTour+2).keySet().contains(chocos)) {
					quantite = quantite + VenteSiPasRuptureDeStock.get(Filiere.LA_FILIERE.getEtape()- CombienDeTour+1).get(chocos);

				}
				}
			
		}
		return quantite;
	}
	
	
	
	public double getDemande(Chocolat chocolat, double cours) {
			double solde = Filiere.LA_FILIERE.getBanque().getSolde(this,  cryptogramme); // retourne l'argent du compte
			double max = solde/cours;
			double stockChoco = this.quantiteEnStockTypeChoco( chocolat);
			double DeamndeChoco = this.EvolutionDemandeChocolat(chocolat);
			evolutionCours.get(Filiere.LA_FILIERE.getEtape()).put(chocolat, cours);
			double quantiteLivreParContratCadre = 0 ;
			for (ExemplaireContratCadre contratDemande : this.mesContratEnTantQuAcheteur) {
				Object produit = contratDemande.getProduit();
				if (produit instanceof ChocolatDeMarque) {
					ChocolatDeMarque cdmobj = (ChocolatDeMarque) produit;
					if (cdmobj.getChocolat() == chocolat) {
						quantiteLivreParContratCadre = quantiteLivreParContratCadre + contratDemande.getQuantiteALivrerAuStep();
					}
				}
				
			}
			
			if (DeamndeChoco - quantiteLivreParContratCadre<stockChoco) {
				journalEq6.ajouter("Demande =" + DeamndeChoco + "stock =" + stockChoco + "return 0");
				return 0;
				
			}
			if (DeamndeChoco - quantiteLivreParContratCadre>=stockChoco) {
				
				double quantitéSouhaité =(DeamndeChoco - quantiteLivreParContratCadre- stockChoco); //on fait l'hypothèse qu'on a la moitié du marché
				if (quantitéSouhaité*cours <solde) {
					journalEq6.ajouter("Demande =" + DeamndeChoco + "stock =" + stockChoco + "return quantiteSouhaite" + quantitéSouhaité);
					return quantitéSouhaité;
					
				}
				
			}
			journalEq6.ajouter("Demande =" + DeamndeChoco + "stock =" + stockChoco + "return 0 fin");
			return 0;
		}
	
		
	
		public void notifierCommande(Chocolat chocolat, double quantiteObtenue, boolean payee) {
			/**if ( payee == true) {                              // on ne l'utilise plus
				HashMap<Chocolat, Double> a = new HashMap<Chocolat, Double>();
				a.put(chocolat,quantiteObtenue);
				this.historiqueBourse.put(Filiere.LA_FILIERE.getEtape(),a );     
			}*/
			/** getChocolat.name().getHistorique().get(int i)*/
		}
	
		
	
		
		public Integer getCryptogramme(SuperviseurChocolatBourse superviseur) { //coder
			if (superviseur !=null) {
				return this.cryptogramme;
			}
			return null;
		}

		@Override
		public void receptionner(ChocolatDeMarque chocolat, double quantite) {
			this.stocker(chocolat,  quantite);
			
		}
}