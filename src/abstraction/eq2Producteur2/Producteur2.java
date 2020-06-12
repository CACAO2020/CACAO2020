package abstraction.eq2Producteur2;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;
import abstraction.eq4Transformateur2.PateInterne;
import abstraction.eq8Romu.produits.Feve;
import abstraction.eq8Romu.produits.Pate;
import abstraction.fourni.Filiere;

public class Producteur2 extends eq2Investisseur implements IActeur {

	private Journal journal_de_production;
	
	public Producteur2() {
		super();
		this.journal_de_production = new Journal("Journal de Production",this);
	}

	/**
	 * Cette methode est appellee a chaque nouveau tour
	 */
	public void next() {

		this.RefreshContrats();
		this.resetbooleans();
		this.RefreshAge();
		this.RefreshStocks();
		this.PayerEmployes();
		this.setPropal(99999999);
		this.decideAchatArbres();
		this.Maintenance();
		this.BrûlerStock();
		this.resetDecisionVariable();
	}
	/**
	 * Cette méthode avance l'age de chaque paquet d'arbre de 1 et enleve les arbres qui ont atteint les 45 ans
	 */
	public void RefreshAge() {
		List<Integer> deathlist = new ArrayList<Integer>();
		
		for (int i = 0; i < this.getPaquetsArbres().size(); i++) {
			this.getPaquetsArbres().get(i).setAge(this.getPaquetsArbres().get(i).getAge() + 1);
			if (this.getPaquetsArbres().get(i).getAge() >= 45*24) {
				deathlist.add(i);
			}
		}
		for (int i = 0; i < deathlist.size(); i++) {
			this.getPaquetsArbres().remove(this.getPaquetsArbres().get(i));
		}
		List<Integer> terminator = new ArrayList<Integer>();
		
		for (int i = 0; i < this.getUsines().size(); i++) {
			this.getUsines().get(i).setAge(this.getUsines().get(i).getAge() + 1);
			if (this.getPaquetsArbres().get(i).getAge() >= 50*24) {
				terminator.add(i);
			}
		}
		for (int i = 0; i < terminator.size(); i++) {
			this.getUsines().remove(this.getUsines().get(i));
		}
		
	}
	
	/**
	 * 
	 * @author lucas P
	 * cette fonction calcule la production d'un cycle et la rajoute au stock
	 */
	public void RefreshStocks() {
		this.setStockFeveTourPrecedent2(this.getStockFeveTourPrecedent());
		this.setStockFeveTourPrecedent(this.getStockFeve());
		float facteur_maladies;
		if(this.apparitionMaladies()) {
			facteur_maladies = this.graviteMaladies();
			this.journal_de_production.ajouter("Une maladie a frappé nos plantations ! Notre production est affectée avec un facteur de "+ facteur_maladies);
		}
		else {
			facteur_maladies = 1;
		}
		
		for (int i = 0; i < this.getPaquetsArbres().size(); i++) {
			this.addQtFeve(this.getPaquetsArbres().get(i).getType(),this.getPaquetsArbres().get(i).production()*facteur_maladies);
			this.journal_de_production.ajouter("Production de " + this.getPaquetsArbres().get(i).production()*facteur_maladies + "tonnes de fèves de type: " + this.getPaquetsArbres().get(i).getType() );
		}
		for (int i = 0; i < this.getUsines().size(); i++) {
			this.addQtPate(this.getUsines().get(i).getPate(),this.getUsines().get(i).Production()*facteur_maladies);
			this.journal_de_production.ajouter("Production de " + this.getUsines().get(i).Production()*facteur_maladies + "tonnes de pates de type: " + this.getUsines().get(i).getPate() );
		}
	}
	//cette fonction va essayer de calculer la valeur de notre stock a partir des prix de la criée precedente (pour le moment), il pourra etre amelioré.(lucas p)
	public double EstimationVenteStock() {
		return this.getPrixTC().getValeur()*this.getQuantiteFeve(Feve.FEVE_HAUTE)+this.getPrixTT().getValeur()*this.getQuantiteFeve(Feve.FEVE_MOYENNE)+this.getPrixTF().getValeur()*this.getQuantiteFeve(Feve.FEVE_BASSE);
	}
	public List<Variable> getIndicateurs() {
		List<Variable> res = new ArrayList<Variable>();
		for (Feve feve: this.getStockFeve().keySet()) {
			res.add(this.getStockFeve().get(feve));
		}
		for (Pate pate: this.getStockPate().keySet()) {
			res.add(this.getStockPate().get(pate));
		}
		res.add(this.getCoutTotalStock());
		return res;
	}
	

	public List<Variable> getParametres() {
		List<Variable> res=new ArrayList<Variable>();
		res.add(new Variable("cout_arbre",this,this.getprixArbre()));
		res.add(this.getCoutStock());
		return res;
	}
		
	public List<Journal> getJournaux() {
		List<Journal> res=new ArrayList<Journal>();
		res.addAll(super.getJournaux());
		res.add(this.journal_de_production);
		return res;
	}
	
	/*
	 *  Cette méthode détermine si une maladie est apparue ou non
	 *  Nous avons une chance sur 20 actuellement d'avoir une maladie à un tour
	 */
	public boolean apparitionMaladies() {
		Random rand = new Random();
		int experience = rand.nextInt(20);
		if(experience == 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/*
	 * Cette méthode calcule la gravité de la maladie si une maladie est apparue
	 * Pour l'instant le facteur d'atténuation varie entre 0.9 et 0.3
	 */
	public float graviteMaladies() {
		Random rand2 = new Random();
		int experience2 = rand2.nextInt(6);
		return (9 - experience2)/(float) 10;
	}


}