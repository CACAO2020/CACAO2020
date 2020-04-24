package abstraction.eq5Transformateur3;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import abstraction.eq8Romu.cacaoCriee.IAcheteurCacaoCriee;
import abstraction.eq8Romu.cacaoCriee.LotCacaoCriee;
import abstraction.eq8Romu.cacaoCriee.PropositionCriee;
import abstraction.eq8Romu.cacaoCriee.SuperviseurCacaoCriee;
import abstraction.eq8Romu.chocolatBourse.IVendeurChocolatBourse;
import abstraction.eq8Romu.produits.Chocolat;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;

public class Transformateur3 implements IActeur, IAcheteurCacaoCriee, IVendeurChocolatBourse {

	private Variable stockFeves;
	private Variable stockChocolat;
	private Integer cryptogramme;
	private Journal journalEq5;

	public Transformateur3() {
		this.stockFeves = new Variable(getNom() + " stock feves", this, 50);
		this.stockChocolat = new Variable(getNom() + " stock chocolat", this, 100);
		this.journalEq5 = new Journal("Eq5 activites", this);
	}

	public String getNom() {
		return "EQ5_Whish'ocko";
	}

	public String getDescription() {
		return "Transformateur bla bla bla";
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
	}

	public List<String> getNomsFilieresProposees() {
		return new ArrayList<String>();
	}

	public Filiere getFiliere(String nom) {
		return null;
	}

	public List<Variable> getIndicateurs() {
		List<Variable> res = new ArrayList<Variable>();
		res.add(this.stockFeves);
		res.add(this.stockChocolat);
		return res;
	}

	public List<Variable> getParametres() {
		List<Variable> res = new ArrayList<Variable>();
		return res;
	}

	public List<Journal> getJournaux() {
		List<Journal> res = new ArrayList<Journal>();
		res.add(this.journalEq5);
		return res;
	}

	public void notificationFaillite(IActeur acteur) {
		if (this == acteur) {
			System.out.println("I'll be back... or not... " + this.getNom());
		} else {
			System.out.println("Poor " + acteur.getNom() + "... We will miss you. " + this.getNom());
		}
	}

	public void notificationOperationBancaire(double montant) {
	}

	@Override
	public double getOffre(Chocolat chocolat, double cours) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void livrer(Chocolat chocolat, double quantite) {
		// TODO Auto-generated method stub

	}

	@Override
	public double proposerAchat(LotCacaoCriee lot) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void notifierPropositionRefusee(PropositionCriee proposition) {
		// TODO Auto-generated method stub

	}

	@Override
	public Integer getCryptogramme(SuperviseurCacaoCriee superviseur) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void notifierVente(PropositionCriee proposition) {
		// TODO Auto-generated method stub

	}
}
