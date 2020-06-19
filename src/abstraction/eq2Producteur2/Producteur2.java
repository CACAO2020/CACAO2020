package abstraction.eq2Producteur2;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
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
		//this.VariateurPrix();
		this.PayerEmployes();
		this.setPropal(99999999);
		this.decideAchatArbres();
		this.Maintenance();
		this.BrûlerStock();
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
			if (this.getPaquetsArbres().get(i).getAge() >= 30*24) {
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
		this.setStockPateTourPrecedent2(this.getStockPateTourPrecedent());
		this.setStockFeveTourPrecedent(this.getStockFeve());
		this.setStockPateTourPrecedent(this.getStockPate());
		float facteur_maladies;
		if(this.apparitionMaladies()) {
			facteur_maladies = this.graviteMaladies();
			this.journal_de_production.ajouter("Une maladie a frappé nos plantations ! Notre production est affectée avec un facteur de "+ facteur_maladies);
		}
		else {
			facteur_maladies = 1;
		}
		
		for (int i = 0; i < this.getPaquetsArbres().size(); i++) {
			if (facteur_maladies !=1 && (this.getPaquetsArbres().get(i).getType()==Feve.FEVE_HAUTE_EQUITABLE || this.getPaquetsArbres().get(i).getType()==Feve.FEVE_HAUTE)) {
				
				this.journal_de_production.ajouter("Perte de " + this.getPaquetsArbres().get(i).production()*facteur_maladies + "tonnes de fèves de type: " + this.getPaquetsArbres().get(i).getType() +"à cause de la maladie");
			}
			else {this.addQtFeve(this.getPaquetsArbres().get(i).getType(),this.getPaquetsArbres().get(i).production()*facteur_maladies);
			this.journal_de_production.ajouter("Production de " + this.getPaquetsArbres().get(i).production()*facteur_maladies + "tonnes de fèves de type: " + this.getPaquetsArbres().get(i).getType() );
		}
		}	
		/*for (int i = 0; i < this.getUsines().size(); i++) {
			
			this.addQtPate(this.getUsines().get(i).getPate(),this.getUsines().get(i).Production());
			this.journal_de_production.ajouter("Production de " + this.getUsines().get(i).Production() + "tonnes de pates de type: " + this.getUsines().get(i).getPate() );
		}*/
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
	 * Pour l'instant le facteur d'atténuation varie entre 0.9 et 0.3 (pour le criollo la production est complétement supprimée)
	 */
	public float graviteMaladies() {
		Random rand2 = new Random();
		int experience2 = rand2.nextInt(6);
		return (9 - experience2)/(float) 10;
	}
	/**@author lucas p */
	public void BrûlerStock() { //calcule et compare dérivées de stock et de vente, et décide de brûler une certaine proportion des fèves les moins vendues (s'ils nous en reste) pour diminuer le coût de stockage
		
		if(Filiere.LA_FILIERE.getEtape() ==0){
			this.setVenteTourPrecedent2(this.getVenteVariation());
		}
		if(Filiere.LA_FILIERE.getEtape() ==1){
			this.setVenteTourPrecedent(this.getVenteVariation());
		}
		 if(Filiere.LA_FILIERE.getEtape() >1){
			 for (Feve feve:this.getStockFeve().keySet()) {
				 if(!this.getStockFeveTourPrecedent().containsKey(feve)) {
					 this.getStockFeveTourPrecedent().put(feve,new Variable(this.getStockFeve().get(feve).getNom(),this,0));
				 }
				 if(!this.getStockFeveTourPrecedent2().containsKey(feve)) {
					 this.getStockFeveTourPrecedent2().put(feve,new Variable(this.getStockFeve().get(feve).getNom(),this,0));
				 }
				 if(!this.getVenteTourPrecedent2().containsKey(feve)) {
					 this.getVenteTourPrecedent2().put(feve,new Variable(this.getStockFeve().get(feve).getNom(),this,0));
				 }
				 if(!this.getVenteTourPrecedent().containsKey(feve)) {
					 this.getVenteTourPrecedent().put(feve,new Variable(this.getStockFeve().get(feve).getNom(),this,0));
				 }
				 if(!this.getVenteVariation().containsKey(feve)) {
					 this.getVenteVariation().put(feve,new Variable(this.getStockFeve().get(feve).getNom(),this,0));
				 }
					if((this.getStockFeve().get(feve).getValeur()+this.getStockFeveTourPrecedent().get(feve).getValeur()+this.getStockFeveTourPrecedent2().get(feve).getValeur()/3)*this.getCoutStock().getValeur()>(this.getVenteTourPrecedent().get(feve).getValeur()+this.getVenteTourPrecedent().get(feve).getValeur()+this.getVenteVariation().get(feve).getValeur()/3)&&(this.getStockFeve().get(feve).getValeur()-this.getVenteVariation().get(feve).getValeur()/this.getCoutStock().getValeur()>0)) {
						this.getStockFeve().get(feve).retirer(this, 0.1*(this.getStockFeve().get(feve).getValeur()-this.getVenteVariation().get(feve).getValeur()/this.getCoutStock().getValeur()));
						this.journal_de_production.ajouter("On a brûlé " +0.1*(this.getStockFeve().get(feve).getValeur()-this.getVenteVariation().get(feve).getValeur()/this.getCoutStock().getValeur())+" tonnes de"+this.getStockFeve().get(feve).getNom() +" car leur stockage nous revenait trop cher");
					}
				}
			double stock_cost_variation = 0;
			HashMap<Feve,Variable> Variation = VariationStock(this.getStockFeveTourPrecedent(),this.getStockFeveTourPrecedent2()); // contient des variations de cout de stock !
			HashMap<Feve,Variable> VariationVente =VariationStock(this.getVenteTourPrecedent(),this.getVenteTourPrecedent2()); // contient les variations de PRIX !
			/*for (Feve feve :Variation.keySet()) {			
				if(VariationVente.containsKey(feve)) {
					System.out.println("valeur"+VariationVente.get(feve).getValeur()/(0.0001+Variation.get(feve).getValeur()));//VariationVente.get(feve) n'existe pas
					if(Variation.get(feve).getValeur()>VariationVente.get(feve).getValeur()&&VariationVente.get(feve).getValeur()>0) {
						
						
						System.out.println("on a brulé" +VariationVente.get(feve).getValeur()/Variation.get(feve).getValeur()*0.1+"kg de "+feve);
						this.getStockFeve().get(feve).retirer(this, 0.1*Variation.get(feve).getValeur()/VariationVente.get(feve).getValeur());
				}
					if(Variation.get(feve).getValeur()>VariationVente.get(feve).getValeur()&&Variation.get(feve).getValeur()<0) {
						
					}
					if() {
						
					}
			}}*/
				this.setVenteTourPrecedent2(this.getVenteTourPrecedent());
				this.setVenteTourPrecedent(this.getVenteVariation());
				this.resetDecisionVariable();
		}
	}
	public void fairepâte() { 
		double proportion = 0.2;
		double coût_par_tonne = 6000;
		if (this.getStockPate().get(Pate.PATE_BASSE).getValeur() < proportion*this.getStockFeve().get(Feve.FEVE_BASSE).getValeur()) {
			if ((this.getStockFeve().get(Feve.FEVE_BASSE).getValeur()*proportion-this.getStockPate().get(Pate.PATE_BASSE).getValeur())*coût_par_tonne < Filiere.LA_FILIERE.getBanque().getSolde(this, this.getCrypto())) {
				if (this.getmassedispofora() > this.getStockFeve().get(Feve.FEVE_BASSE).getValeur()*proportion-this.getStockPate().get(Pate.PATE_BASSE).getValeur()) {
					this.removeQtFeve(Feve.FEVE_BASSE, this.getStockFeve().get(Feve.FEVE_BASSE).getValeur()*proportion-this.getStockPate().get(Pate.PATE_BASSE).getValeur());
					this.addQtPate(Pate.PATE_BASSE, this.getStockFeve().get(Feve.FEVE_BASSE).getValeur()*proportion-this.getStockPate().get(Pate.PATE_BASSE).getValeur());
					Filiere.LA_FILIERE.getBanque().virer(this, this.getCrypto(), Filiere.LA_FILIERE.getBanque(), coût_par_tonne*(this.getStockFeve().get(Feve.FEVE_BASSE).getValeur()*proportion-this.getStockPate().get(Pate.PATE_BASSE).getValeur()));
				}
			}
		}
		if (this.getStockPate().get(Pate.PATE_MOYENNE).getValeur() < proportion*this.getStockFeve().get(Feve.FEVE_MOYENNE).getValeur()) {
			if ((this.getStockFeve().get(Feve.FEVE_MOYENNE).getValeur()*proportion-this.getStockPate().get(Pate.PATE_MOYENNE).getValeur())*coût_par_tonne < Filiere.LA_FILIERE.getBanque().getSolde(this, this.getCrypto())) {
				if (this.getmassedispofora() > this.getStockFeve().get(Feve.FEVE_MOYENNE).getValeur()*proportion-this.getStockPate().get(Pate.PATE_MOYENNE).getValeur()) {
					this.removeQtFeve(Feve.FEVE_MOYENNE, this.getStockFeve().get(Feve.FEVE_MOYENNE).getValeur()*proportion-this.getStockPate().get(Pate.PATE_MOYENNE).getValeur());
					this.addQtPate(Pate.PATE_MOYENNE, this.getStockFeve().get(Feve.FEVE_MOYENNE).getValeur()*proportion-this.getStockPate().get(Pate.PATE_MOYENNE).getValeur());
					Filiere.LA_FILIERE.getBanque().virer(this, this.getCrypto(), Filiere.LA_FILIERE.getBanque(), coût_par_tonne*(this.getStockFeve().get(Feve.FEVE_MOYENNE).getValeur()*proportion-this.getStockPate().get(Pate.PATE_MOYENNE).getValeur()));
				}
			}
		}
	}

}