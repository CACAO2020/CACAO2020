package abstraction.eq6Distributeur1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import abstraction.eq8Romu.clients.ClientFinal;
import abstraction.eq8Romu.clients.IDistributeurChocolatDeMarque;
import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.ChocolatDeMarque;
import abstraction.fourni.Filiere;

public class DistributeurClientFinal extends AchatBourseEQ6 implements IDistributeurChocolatDeMarque{
	private double capaciteDeVente;
	private Map<ChocolatDeMarque,Double> catalogueVente;
	protected Map<ChocolatDeMarque,Double> margeChocolat;
	private double marge;
	private double pctageHGE;
	private double pctageBG;
	private double pctageMG;
	
	 

	public DistributeurClientFinal(double capaciteDeVente, double marge, double capaciteStockmax, double pctageHGE, double pctageMG, double pctageBG) {
		super(capaciteStockmax);
		this.catalogueVente = new HashMap<ChocolatDeMarque,Double>();
		this.margeChocolat = new HashMap<ChocolatDeMarque,Double>();
		this.capaciteDeVente = capaciteDeVente;
		this.marge=marge;
		this.pctageBG=pctageBG;
		this.pctageHGE=pctageHGE;
		this.pctageMG=pctageMG;
		
		
	}

	/** @author Luca Pinguet & Mélissa Tamine */
	
	public List<ChocolatDeMarque> getCatalogue() {
		List<ChocolatDeMarque> produits = new ArrayList<ChocolatDeMarque>();
		for (Integer etape : this.MapStock.keySet()) {
			for (ChocolatDeMarque chocos : MapStock.get(etape).keySet()) {
				if (!produits.contains(chocos)) {
					produits.add(chocos);
				}
			}
		}
		return produits;
		
	}

	/** @author Luca Pinguet & Mélissa Tamine */
	
	public double prix(ChocolatDeMarque choco) {
		double cours = this.evolutionCours.get(Filiere.LA_FILIERE.getEtape()).get(choco.getChocolat());
		if (choco.getChocolat()==Chocolat.CHOCOLAT_HAUTE_EQUITABLE) {
			return cours*this.margeChocolat.get(choco);}
		else if (choco.getChocolat()==Chocolat.CHOCOLAT_MOYENNE) {
			return cours*this.margeChocolat.get(choco);}
		else if (choco.getChocolat()==Chocolat.CHOCOLAT_BASSE) {
			return cours*this.margeChocolat.get(choco);}
		else {
			return 0 ;}
		}
		

	/** @author Luca Pinguet & Mélissa Tamine */
	
	public double quantiteEnVente(ChocolatDeMarque choco) {
		double capaciteHGE=this.capaciteDeVente*this.pctageHGE;
        double capaciteMG=this.capaciteDeVente*this.pctageMG;
        double capaciteBG=this.capaciteDeVente*this.pctageBG;

        double nbmarques=0;
        
        ArrayList<ChocolatDeMarque> dejaExplore = new ArrayList<ChocolatDeMarque>();
        for (Integer etape : MapStock.keySet() ) {
        	 for (ChocolatDeMarque chocos : MapStock.get(etape).keySet()) {
        		 if (chocos.getChocolat()==choco.getChocolat() && !dejaExplore.contains(chocos)) {
        			 nbmarques = nbmarques + 1;
        			 dejaExplore.add(chocos);
        		 }
        	 }
        }

        if (choco.getChocolat()==Chocolat.CHOCOLAT_HAUTE_EQUITABLE) {
            return quantiteEnStockMarqueChoco(choco)!=0 ? Math.min(capaciteHGE/nbmarques, quantiteEnStockMarqueChoco(choco)) : 0;
        }
        else if (choco.getChocolat()==Chocolat.CHOCOLAT_MOYENNE) {
            return quantiteEnStockMarqueChoco(choco)!=0 ? Math.min(capaciteMG/nbmarques, quantiteEnStockMarqueChoco(choco)) : 0;
        }
        else if (choco.getChocolat()==Chocolat.CHOCOLAT_BASSE) {
            return quantiteEnStockMarqueChoco(choco)!=0 ? Math.min(capaciteBG/nbmarques, quantiteEnStockMarqueChoco(choco)) : 0;
        }
        else {
            return 0;
        }

    }
	
	
	/** @author Luca Pinguet & Mélissa Tamine */
	
	public void vendre(ClientFinal client, ChocolatDeMarque choco, double quantite, double montant) {
		if (client!=null) { 
			destocker(choco,quantite);
			this.evolutionVentes.get(Filiere.LA_FILIERE.getEtape()).put(choco, quantite);
			}
		}
		

	/** @author Luca Pinguet & Mélissa Tamine */
	public void notificationRayonVide(ChocolatDeMarque choco) {
		journalEq6.ajouter(" Aie... j'aurais du mettre davantage de "+choco.name()+" en vente");
		
	}

	@Override
	public List<ChocolatDeMarque> pubSouhaitee() {
		// TODO Auto-generated method stub
		return null;
	}
	//Début V2
	
	public void evolutionMarge(ChocolatDeMarque choco) {
		double vente2 = Filiere.LA_FILIERE.getVentes(Filiere.LA_FILIERE.getEtape()-2, choco)>0 ? this.evolutionVentes.get(Filiere.LA_FILIERE.getEtape()-2).get(choco)/Filiere.LA_FILIERE.getVentes(Filiere.LA_FILIERE.getEtape()-2, choco):0;
		double vente1 = Filiere.LA_FILIERE.getVentes(Filiere.LA_FILIERE.getEtape()-1, choco)>0 ? this.evolutionVentes.get(Filiere.LA_FILIERE.getEtape()-1).get(choco)/Filiere.LA_FILIERE.getVentes(Filiere.LA_FILIERE.getEtape()-1, choco):0;
		if (vente1!=0 && vente2!=0) {
			if (this.margeChocolat.containsKey(choco)) {
				double newmarge = this.margeChocolat.get(choco)+((vente1-vente2)/vente2)*0.2;  // faudra faire des tests sur l'évolution des marges
				if (newmarge<1.5 && newmarge>1.1){
					this.margeChocolat.replace(choco, newmarge);
				}
				if (newmarge>1.5){
					this.margeChocolat.replace(choco, 1.5);
				}
				if (newmarge<1.1) {
					this.margeChocolat.replace(choco, 1.1);
				}
			}
			else {
				this.margeChocolat.put(choco, marge);
			}
		}
	}

}
