package abstraction.eq7Distributeur2;

import java.awt.Color;
import java.util.List;

import abstraction.eq8Romu.chocolatBourse.IAcheteurChocolatBourse;
import abstraction.eq8Romu.chocolatBourse.IVendeurChocolatBourse;
import abstraction.eq8Romu.chocolatBourse.SuperviseurChocolatBourse;
import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.ChocolatDeMarque;
import abstraction.fourni.Banque;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;

public class Transformateur implements IAcheteurChocolatBourse, IVendeurChocolatBourse {

	private Variable stockFeves;
	private Variable stockChocolat;
	private Integer cryptogramme;
	private String nom;
	private Banque laBanque; 
	
	@Override
	public String getNom() {
		// TODO Auto-generated method stub
		return this.nom;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Transformateur"+this.getNom();
	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void initialiser() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void next() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String> getNomsFilieresProposees() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Filiere getFiliere(String nom) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Variable> getIndicateurs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Variable> getParametres() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Journal> getJournaux() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCryptogramme(Integer crypto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notificationFaillite(IActeur acteur) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notificationOperationBancaire(double montant) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getDemande(Chocolat chocolat, double cours) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Integer getCryptogramme(SuperviseurChocolatBourse superviseur) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void notifierCommande(Chocolat chocolat, double quantiteObtenue, boolean payee) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receptionner(ChocolatDeMarque chocolat, double quantite) {
		// TODO Auto-generated method stub
		
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

}
