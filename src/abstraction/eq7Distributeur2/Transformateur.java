package abstraction.eq7Distributeur2;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import abstraction.eq8Romu.cacaoCriee.IAcheteurCacaoCriee;
import abstraction.eq8Romu.cacaoCriee.LotCacaoCriee;
import abstraction.eq8Romu.cacaoCriee.PropositionCriee;
import abstraction.eq8Romu.cacaoCriee.SuperviseurCacaoCriee;
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


// cette classe a ete créee pour simuler un transformateur dans la filiere test 
public class Transformateur implements IAcheteurCacaoCriee, IVendeurChocolatBourse {

	private Variable stockFeves;
	private Variable stockChocolat;
	private Integer cryptogramme;
	private Banque laBanque; 
	private Chocolat choco;
	
	public Transformateur(Chocolat choco) {
		this.choco = choco;
		this.stockFeves = new Variable("Stock fèves " + getNom(), this, 50);
		this.stockChocolat = new Variable("Stock chocolat " + getNom(), this, 0);
	}
	
	public String getNom() {
		return "T " + this.choco.name();
	}

	public String getDescription() {
		return "Transformateur"+this.getNom();
	}

	public Color getColor() {
		return new Color(128, 128, 255);
	}

	public void initialiser() {
		this.laBanque = Filiere.LA_FILIERE.getBanque();
		
	}
// on effectue ici la transformation du cacao (et du sucre) en chocolat. On retire donc une certaine quantité de cacao du stock pour ajouter 
// du chocolat de la qualité correspondante au stock concerné de chocolat
// On ajoute aux couts la main d'oeuvre et les frais en sucre, entretient de machines,...
	public void next() {
		double quantiteTransformee = Math.random()*Math.min(100, this.stockFeves.getValeur()); // on suppose qu'on a un stock infini de sucre
		this.stockFeves.retirer(this, quantiteTransformee);
		this.stockChocolat.ajouter(this, 100*quantiteTransformee);// 50% cacao, 50% sucre
		if (quantiteTransformee != 0) {
			//this.laBanque.virer(this, cryptogramme, this.laBanque, quantiteTransformee*1.0234); // sucre, main d'oeuvre, autres frais
		}
	}

	public List<String> getNomsFilieresProposees() {
		return new ArrayList<String>();
	}

	public Filiere getFiliere(String nom) { 
		return Filiere.LA_FILIERE;
	}

	public List<Variable> getIndicateurs() {
		List<Variable> res=new ArrayList<Variable>();
		res.add(stockFeves);
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

	public void setCryptogramme(Integer crypto) {
		this.cryptogramme = crypto;
		
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

	public Integer getCryptogramme(SuperviseurChocolatBourse superviseur) {
		if (superviseur!=null) {
			return cryptogramme;
		}
		return Integer.valueOf(0);
	}

	public void notifierCommande(Chocolat chocolat, double quantiteObtenue, boolean payee) {
		
	}

	public double getOffre(Chocolat chocolat, double cours) {
		if (chocolat==choco) {
			return this.stockChocolat.getValeur();
		}
		else {
			return 0.0;
		}
	}

	public void livrer(Chocolat chocolat, double quantite) {
		stockChocolat.retirer(this, quantite);
	}


// propose les achats de feves pour la criée en fonction du prix minimal

	public double proposerAchat(LotCacaoCriee lot) {
		if (lot.getFeve().getGamme() == this.choco.getGamme()) {
			return lot.getPrixMinPourUneTonne()*lot.getQuantiteEnTonnes();
		} else {
			return 0;
		}
	}
 
	public void notifierPropositionRefusee(PropositionCriee proposition) {
		
	}

	public Integer getCryptogramme(SuperviseurCacaoCriee superviseur) {
		return this.cryptogramme;
	}

	// envoie une notification de l'achat effectué  
	public void notifierVente(PropositionCriee proposition) {
		this.stockFeves.ajouter(proposition.getVendeur(), proposition.getQuantiteEnTonnes());
	}

}
