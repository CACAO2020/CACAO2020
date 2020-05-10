package abstraction.eq6Distributeur1;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.ChocolatDeMarque;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;

public class Distributeur1 implements IActeur {

	private Integer cryptogramme;
	protected Journal journalEq6;
	protected Variable stockHGE;
	protected Variable stockMG;
	protected Variable stockBG;
	protected ChocolatDeMarque chocolatHGE;
	protected ChocolatDeMarque chocolatMG;
	protected ChocolatDeMarque chocolatBG;
	
	public Distributeur1() {
		this.journalEq6 = new Journal("Eq6 activites", this); 
		this.stockHGE=new Variable(getNom()+" stock "+ Chocolat.CHOCOLAT_HAUTE_EQUITABLE.toString(), this, 0, 1000000000, 1000000);
		this.stockMG=new Variable(getNom()+"stock"+ Chocolat.CHOCOLAT_MOYENNE.toString(), this, 0, 1000000000, 1000000);
		this.stockBG=new Variable(getNom()+"stock"+ Chocolat.CHOCOLAT_BASSE.toString(), this, 0, 1000000000, 1000000);
		this.journalEq6=new Journal(this.getNom()+" activites", this);
	}
	
	public String getNom() {
		return "IMTermarché";
	}

	public String getDescription() {
		return "Distributeur bla bla bla";
	}
	
	public Color getColor() {
		return new Color(240, 195, 15);
	}

	public void initialiser() {
	}

	public void setCryptogramme(Integer crypto) {
		this.cryptogramme = crypto;
	}
	
	/** @author Luca Pinguet & Mélissa Tamine */
	public void next() {
		journalEq6.ajouter("Etape="+Filiere.LA_FILIERE.getEtape());
		if (Filiere.LA_FILIERE.getEtape()>=1) {
			journalEq6.ajouter("Le prix moyen du chocolat \""+chocolatHGE.name()+"\" a l'etape precedente etait de "+Filiere.LA_FILIERE.prixMoyenEtapePrecedente(chocolatHGE));
			journalEq6.ajouter("Le prix moyen du chocolat \""+chocolatMG.name()+"\" a l'etape precedente etait de "+Filiere.LA_FILIERE.prixMoyenEtapePrecedente(chocolatMG));
			journalEq6.ajouter("Le prix moyen du chocolat \""+chocolatBG.name()+"\" a l'etape precedente etait de "+Filiere.LA_FILIERE.prixMoyenEtapePrecedente(chocolatBG));
		}
		journalEq6.ajouter("Les ventes de chocolat \""+chocolatHGE.name()+" il y a un an etaient de "+Filiere.LA_FILIERE.getVentes(Filiere.LA_FILIERE.getEtape()-24, chocolatHGE));
		journalEq6.ajouter("Les ventes de chocolat \""+chocolatMG.name()+" il y a un an etaient de "+Filiere.LA_FILIERE.getVentes(Filiere.LA_FILIERE.getEtape()-24, chocolatMG));
		journalEq6.ajouter("Les ventes de chocolat \""+chocolatBG.name()+" il y a un an etaient de "+Filiere.LA_FILIERE.getVentes(Filiere.LA_FILIERE.getEtape()-24, chocolatBG));
		
		
		journalEq6.ajouter("Les ventes de chocolat \""+chocolatHGE.getChocolat().name()+" il y a un an etaient de "+Filiere.LA_FILIERE.getVentes(Filiere.LA_FILIERE.getEtape()-24, chocolatHGE.getChocolat()));
		journalEq6.ajouter("Les ventes de chocolat \""+chocolatMG.getChocolat().name()+" il y a un an etaient de "+Filiere.LA_FILIERE.getVentes(Filiere.LA_FILIERE.getEtape()-24, chocolatMG.getChocolat()));
		journalEq6.ajouter("Les ventes de chocolat \""+chocolatBG.getChocolat().name()+" il y a un an etaient de "+Filiere.LA_FILIERE.getVentes(Filiere.LA_FILIERE.getEtape()-24, chocolatBG.getChocolat()));
	}
	
	public List<String> getNomsFilieresProposees() {
		return new ArrayList<String>();
	}

	public Filiere getFiliere(String nom) {
		return null;
	}
	
	public List<Variable> getIndicateurs() {
		List<Variable> res=new ArrayList<Variable>();
		return res;
	}

	public List<Variable> getParametres() {
		List<Variable> res=new ArrayList<Variable>();
		return res;
	}

	public List<Journal> getJournaux() {
		List<Journal> res=new ArrayList<Journal>();
		res.add(this.journalEq6);
		return res;
	}

	public void notificationFaillite(IActeur acteur) {
		if (this==acteur) {
		System.out.println("I'll be back... or not... "+this.getNom());
		} else {
			System.out.println("Poor "+acteur.getNom()+"... We will miss you. "+this.getNom());
		}
	}
	
	public void notificationOperationBancaire(double montant) {
	}
}

