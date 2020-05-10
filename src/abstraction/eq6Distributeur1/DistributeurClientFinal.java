package abstraction.eq6Distributeur1;

import java.util.LinkedList;
import java.util.List;

import abstraction.eq8Romu.clients.ClientFinal;
import abstraction.eq8Romu.clients.IDistributeurChocolatDeMarque;
import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.ChocolatDeMarque;

public class DistributeurClientFinal extends Distributeur1 implements IDistributeurChocolatDeMarque{
	private double capaciteDeVente;
	private double prixHGE;
	private double prixMG;
	private double prixBG;

	public DistributeurClientFinal(double capaciteDeVente, double prixHGE, double prixMG, double prixBG) {
		this.capaciteDeVente = capaciteDeVente;
		this.prixHGE=prixHGE;
		this.prixMG=prixMG;
		this.prixBG=prixBG;
		
	}

	/** @author Luca Pinguet & Mélissa Tamine */
	
	public List<ChocolatDeMarque> getCatalogue() {
		List<ChocolatDeMarque> produits=new LinkedList<ChocolatDeMarque>();
		produits.add(chocolatHGE);
		produits.add(chocolatMG);
		produits.add(chocolatBG);
		return produits;
	}

	/** @author Luca Pinguet & Mélissa Tamine */
	
	public double prix(ChocolatDeMarque choco) {
		if (choco.getChocolat()==Chocolat.CHOCOLAT_HAUTE_EQUITABLE) {
			return this.prixHGE;}
		else if (choco.getChocolat()==Chocolat.CHOCOLAT_MOYENNE) {
			return this.prixMG;}
		else if (choco.getChocolat()==Chocolat.CHOCOLAT_BASSE) {
			return this.prixBG;}
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
