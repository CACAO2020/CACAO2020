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
	private double pctageHGE;
	private double pctageBG;
	private double pctageMG;

	public DistributeurClientFinal(double capaciteDeVente, double margeHGE, double margeMG, double margeBG,double capaciteStockmax, Map<ChocolatDeMarque, Double> MapStock, double pctageHGE, double pctageMG, double pctageBG) {
		super(capaciteStockmax, MapStock);
		this.catalogueVente = new HashMap<ChocolatDeMarque, Double>();
		this.capaciteDeVente = capaciteDeVente;
		this.margeHGE=margeHGE;
		this.margeMG=margeMG;
		this.margeBG=margeBG;
		this.pctageBG=pctageBG;
		this.pctageHGE=pctageHGE;
		this.pctageMG=pctageMG;
		
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
		double capaciteHGE=this.capaciteDeVente*this.pctageHGE;
		double capaciteMG=this.capaciteDeVente*this.pctageMG;
		double capaciteBG=this.capaciteDeVente*this.pctageBG;
		
		double nbmarques=0;
		
		for (ChocolatDeMarque chocos : MapStock.keySet()) {
			if (chocos.getChocolat()==choco.getChocolat()) {
				nbmarques = nbmarques + 1;
			}
			
		}
		
		
		if (choco.getChocolat()==Chocolat.CHOCOLAT_HAUTE_EQUITABLE) {
			return Math.min(capaciteHGE/nbmarques, this.MapStock.get(choco));
		}
		else if (choco.getChocolat()==Chocolat.CHOCOLAT_MOYENNE) {
			return Math.min(capaciteMG/nbmarques, this.MapStock.get(choco));
		}
		else if (choco.getChocolat()==Chocolat.CHOCOLAT_BASSE) {
			return Math.min(capaciteBG/nbmarques, this.MapStock.get(choco));
		}
		else {
			return 0;
		}
	
	}
	
	/** @author Luca Pinguet & Mélissa Tamine */
	
	public void vendre(ClientFinal client, ChocolatDeMarque choco, double quantite, double montant) {
		if (client!=null) { 
			destocker(choco,quantite);
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
