package abstraction.eq1Producteur1;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;
import abstraction.eq8Romu.cacaoCriee.IVendeurCacaoCriee;
import abstraction.eq8Romu.cacaoCriee.LotCacaoCriee;
import abstraction.eq8Romu.cacaoCriee.PropositionCriee;
import abstraction.eq8Romu.produits.Feve;
import abstraction.fourni.Filiere;

/*
 * Précision sur la méthode de répartition des fichiers sources
 * La méthode pour l'instant retenu est l'agrégation de classes annexes (vente de cacao, gestion de stock...)
 * qui permetteront d'organiser le code dans plusieurs fichiers
 */

public class Producteur1 implements IActeur, IVendeurCacaoCriee {

	private Variable stockFeves;
	private Integer cryptogramme;
	private Journal journalEq1;

	public Producteur1() {
		this.stockFeves=new Variable(getNom()+" stock feves", this, 0, 10000, 1000);
		this.journalEq1 = new Journal("Eq1 activites", this);
	}

	public void setCryptogramme(Integer crypto) {
		this.cryptogramme = crypto;
	}
	
	private Integer getCryptogramme()
	{
		return this.cryptogramme;
	}

	public String getNom() {
		return "EQ1";
	}

	public String getDescription() {
		return "Producteur 1 : Producteur de feves Forastero et Trinitario";
	}

	public Color getColor() {
		return new Color(26, 188, 156);
	}

	public void initialiser() {
	}
	
	public double getStock()
	{
		return this.stockFeves.getValeur();
	}

	public void next() {
		// Ecriture de l'état dans les logs.
		this.journalEq1.ajouter("Quantité de stock : " + this.getStock());
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
		return res;
	}

	public List<Variable> getParametres() {
		List<Variable> res=new ArrayList<Variable>();
		return res;
	}

	public List<Journal> getJournaux() {
		List<Journal> res=new ArrayList<Journal>();
		res.add(this.journalEq1);
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

	
	public LotCacaoCriee getLotEnVente() 
	{
		// A compléter
		return new LotCacaoCriee(this, Feve.FEVE_BASSE, this.stockFeves.getValeur(), 10);
	}

	@Override
	public void notifierAucuneProposition(LotCacaoCriee lot) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PropositionCriee choisir(List<PropositionCriee> propositions) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void notifierVente(PropositionCriee proposition) {
		// TODO Auto-generated method stub
		
	}
}
