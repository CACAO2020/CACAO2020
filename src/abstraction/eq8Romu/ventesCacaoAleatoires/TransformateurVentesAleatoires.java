package abstraction.eq8Romu.ventesCacaoAleatoires;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import abstraction.fourni.Banque;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;

public class TransformateurVentesAleatoires  implements IActeur, IAcheteurCacaoAleatoire {
	
	private Variable stockFeves;
	private Variable stockChocolat;
	private Integer cryptogramme;
	private String nom;
	private Banque laBanque; 

	public TransformateurVentesAleatoires(String nom) {
		this.nom = nom;

		this.stockFeves=new Variable(getNom()+" stock feves", this, 50);
		this.stockChocolat=new Variable(getNom()+" stock chocolat", this, 100);
	}
	
	public String getNom() {
		return this.nom;
	}

	public String getDescription() {
		return "Transformateur ventes aleatoires "+this.getNom();
	}
	
	public Color getColor() {
		return new Color(128, 128, 255);
	}

	public void initialiser() {
		this.laBanque = Filiere.LA_FILIERE.getBanque();
	}

	public void setCryptogramme(Integer crypto) {
		this.cryptogramme = crypto;
	}
	public void next() {
		// transformation
		double quantiteTransformee = Math.random()*Math.min(100, this.stockFeves.getValeur()); // on suppose qu'on a un stock infini de sucre
		this.stockFeves.retirer(this, quantiteTransformee);
		this.stockChocolat.ajouter(this, (2*quantiteTransformee));// 50% cacao, 50% sucre
		this.laBanque.virer(this, cryptogramme, this.laBanque, quantiteTransformee*1.0234); // sucre, main d'oeuvre, autres frais
	}

	public double quantiteDesiree(double quantiteEnVente, double prix) {
		double desiree= quantiteEnVente; // achete le plus possible, meme si il n'a pas l'argent pour l'acheter
		return desiree;
	}
	
	public void quantiteLivree(double quantiteLivree) {
		this.stockFeves.ajouter(this, quantiteLivree);
	}
	
	public List<String> getNomsFilieresProposees() {
		return new ArrayList<String>();
	}

	public Filiere getFiliere(String nom) {
		return null;
	}
	public List<Variable> getIndicateurs() {
		List<Variable> res=new ArrayList<Variable>();
		res.add(this.stockFeves);
		res.add(stockChocolat);
		return res;
	}

	public List<Variable> getParametres() {
		List<Variable> res=new ArrayList<Variable>();
		return res;
	}

	public List<Journal> getJournaux() {
		List<Journal> res=new ArrayList<Journal>();
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

	public Integer getCryptogramme(SuperviseurVentesCacaoAleatoires superviseur) {
		if (superviseur!=null) {
			return cryptogramme;
		}
		return Integer.valueOf(0);
	}
}
