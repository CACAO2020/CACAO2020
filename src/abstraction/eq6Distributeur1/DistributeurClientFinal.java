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
	private double margeHGE;
	private double margeMG;
	private double margeBG;

	public DistributeurClientFinal(double capaciteDeVente, double margeHGE, double margeMG, double margeBG,double capaciteStockmax, Map<ChocolatDeMarque, Double> MapStock) {
		super(capaciteStockmax, MapStock);
		this.catalogueVente = new HashMap<ChocolatDeMarque, Double>();
		this.capaciteDeVente = capaciteDeVente;
		this.margeHGE=margeHGE;
		this.margeMG=margeMG;
		this.margeBG=margeBG;
		
	}

	/** @author Luca Pinguet & Mélissa Tamine */
	
	public List<ChocolatDeMarque> getCatalogue() {
		List<ChocolatDeMarque> produits = new ArrayList<ChocolatDeMarque>();
		for (ChocolatDeMarque chocos : this.MapStock.keySet()) {
			produits.add(chocos);
		}
		return produits;
	}

	/** @author Luca Pinguet & Mélissa Tamine */
	
	public double prix(ChocolatDeMarque choco) {
		double cours = this.evolutionCours.get(Filiere.LA_FILIERE.getEtape()).get(choco.getChocolat());
		if (choco.getChocolat()==Chocolat.CHOCOLAT_HAUTE_EQUITABLE) {
			return cours+margeHGE*cours;}
		else if (choco.getChocolat()==Chocolat.CHOCOLAT_MOYENNE) {
			return cours+margeMG*cours;}
		else if (choco.getChocolat()==Chocolat.CHOCOLAT_BASSE) {
			return cours+margeBG*cours;}
		else {
			return 0 ;}
		}
		

	/** @author Luca Pinguet & Mélissa Tamine */
	
	public double quantiteEnVente(ChocolatDeMarque choco) {
		if (choco.getChocolat()==Chocolat.CHOCOLAT_HAUTE_EQUITABLE) {
			return Math.min(capaciteDeVente, stockHGE.getValeur());
		}
		else if (choco.getChocolat()==Chocolat.CHOCOLAT_MOYENNE) {
			return Math.min(capaciteDeVente, stockMG.getValeur());
		}
		else if (choco.getChocolat()==Chocolat.CHOCOLAT_BASSE) {
			return Math.min(capaciteDeVente, stockBG.getValeur());
		}
		else {
			return 0;
		}
	
	}
	
	/** @author Luca Pinguet & Mélissa Tamine */
	
	public void vendre(ClientFinal client, ChocolatDeMarque choco, double quantite, double montant) {
		if (client!=null) { 
			if (choco.getChocolat()==Chocolat.CHOCOLAT_HAUTE_EQUITABLE) {
				stockHGE.retirer(client, quantite);
			}
			else if (choco.getChocolat()==Chocolat.CHOCOLAT_MOYENNE) {
				stockMG.retirer(client, quantite);
			}
			else if (choco.getChocolat()==Chocolat.CHOCOLAT_BASSE) {
				stockBG.retirer(client, quantite);
			}
		
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

}
