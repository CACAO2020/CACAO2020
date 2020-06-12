package abstraction.eq5Transformateur3;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import abstraction.eq4Transformateur2.Transformateur2;
import abstraction.eq8Romu.cacaoCriee.IAcheteurCacaoCriee;
import abstraction.eq8Romu.cacaoCriee.LotCacaoCriee;
import abstraction.eq8Romu.cacaoCriee.PropositionCriee;
import abstraction.eq8Romu.cacaoCriee.SuperviseurCacaoCriee;
import abstraction.eq8Romu.chocolatBourse.IVendeurChocolatBourse;
import abstraction.eq8Romu.contratsCadres.Echeancier;
import abstraction.eq8Romu.contratsCadres.ExemplaireContratCadre;
import abstraction.eq8Romu.contratsCadres.IAcheteurContratCadre;
import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.Feve;
import abstraction.eq8Romu.produits.Pate;
import abstraction.eq8Romu.ventesCacaoAleatoires.IAcheteurCacaoAleatoire;
import abstraction.eq8Romu.ventesCacaoAleatoires.SuperviseurVentesCacaoAleatoires;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;

@SuppressWarnings("unused")
public class Transformateur3 implements IActeur, IAcheteurCacaoCriee, IVendeurChocolatBourse, IAcheteurContratCadre {

	private Integer cryptogramme;
	private Journal journalEq5;
	private AchatCacao acheteurCacao;
	private AchatPate acheteurPate;
	private VenteChocolat vendeurChocolat;
	private Tresorerie tresorier;
	private Stock stock;

	public Transformateur3() {
		this.journalEq5 = new Journal("Eq5 activites", this);
		this.acheteurCacao = new AchatCacao(this); // needs to be filled with parameters this will work for now
		this.acheteurPate = new AchatPate(this);
		this.vendeurChocolat = new VenteChocolat(this);
		this.tresorier = new Tresorerie(this);
		this.stock = new Stock(this);
	}

	public String getNom() {
		return "Whish'oco";
	}

	public String getDescription() {
		return "Transformateur qui aime le chocolat.";
	}

	public Color getColor() {
		return new Color(233, 30, 99);
	}

	public void initialiser() {
	}

	public void setCryptogramme(Integer crypto) {
		this.cryptogramme = crypto;
	}

	public void next() {
		stock.next();
		acheteurPate.commencerNegociations();
	}

	public List<String> getNomsFilieresProposees() {
		List<String> filieresPossibles = new ArrayList<String>();
		filieresPossibles.add("AchatVente");
		return filieresPossibles;
	}

	public Filiere getFiliere(String nom) {
		if (nom.equals("AchatVente")) {
			return new FiliereTestAchatVente();
		}
		else {
			return null;
		}
	}

	public List<Variable> getIndicateurs() {
		List<Variable> res = this.stock.getIndicateurs();
		return res;
	}

	public List<Variable> getParametres() {
		// TODO ici devront être mis les paramètres dont je parlais (finalement ce
		// seront des variables qu'il faudra penser à ajouter ici)
		List<Variable> res = new ArrayList<Variable>();
		res.add(this.stock.getTransformationCostFeve());
		res.add(this.stock.getTransformationCostPate());
		return res;
	}

	public List<Journal> getJournaux() {
		List<Journal> res = new ArrayList<Journal>();
		res.add(this.journalEq5);
		return res;
	}

	public void notificationFaillite(IActeur acteur) {
		if (this == acteur) {
			System.out.println("RIP in pieces" + this.getNom());
		} else {
			System.out.println("Poor " + acteur.getNom() + "... We will miss you. " + this.getNom());
		}
	}

	public void notificationOperationBancaire(double montant) {
		String str = montant > 0 ? "On a gagné de l'argent ! " : "On a perdu de l'argent ! ";
		this.journalEq5.ajouter(str + montant + " Dollars");
	}

	// Vente de Chocolat en bourse IVenteChocolatBourse

	public double getOffre(Chocolat chocolat, double cours) {
		return this.vendeurChocolat.getOffre(chocolat, cours);

	}

	public void livrer(Chocolat chocolat, double quantite) {
		this.vendeurChocolat.livrer(chocolat, quantite);
	}

	// Achat de cacao en criée IAcheteurCacaoCriee

	public double proposerAchat(LotCacaoCriee lot) {
		return this.acheteurCacao.proposerAchat(lot);
	}

	public void notifierPropositionRefusee(PropositionCriee proposition) {
		this.acheteurCacao.notifierPropositionRefusee(proposition);
	}

	public Integer getCryptogramme(SuperviseurCacaoCriee superviseur) {
		return superviseur == null ? Integer.valueOf(0) : this.cryptogramme;
	}

	public void notifierVente(PropositionCriee proposition) {
		this.acheteurCacao.notifierVente(proposition);
	}

	// TODO ajouter les methodes pour acheter de la pate de cacao

	protected AchatCacao getAcheteurCacao() {
		return acheteurCacao;
	}

	protected AchatPate getAcheteurPate() {
		return acheteurPate;
	}

	protected VenteChocolat getVendeurChocolat() {
		return vendeurChocolat;
	}

	protected Tresorerie getTresorier() {
		return tresorier;
	}

	protected Stock getStock() {
		return stock;
	}

	protected int getCryptogramme() {
		return this.cryptogramme;
	}

	
	public Echeancier contrePropositionDeLAcheteur(ExemplaireContratCadre contrat) {
		//errorless commit
		//return this.acheteurPate.contrePropositionDeLAcheteur(contrat);
		return null;
	}

	
	public double contrePropositionPrixAcheteur(ExemplaireContratCadre contrat) {
		//errorless commit
		//return this.acheteurPate.contrePropositionPrixAcheteur(contrat);
		return 0;
	}

	
	public void receptionner(Object produit, double quantite, ExemplaireContratCadre contrat) {
		//errorless commit
		//this.acheteurPate.receptionner(produit, quantite, contrat);
	}
}
