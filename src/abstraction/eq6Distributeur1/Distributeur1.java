package abstraction.eq6Distributeur1;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import abstraction.eq8Romu.chocolatBourse.IAcheteurChocolatBourse;
import abstraction.eq8Romu.chocolatBourse.SuperviseurChocolatBourse;
import abstraction.eq8Romu.clients.ClientFinal;
import abstraction.eq8Romu.clients.IDistributeurChocolat;
import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.Gamme;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;

public class Distributeur1 implements IActeur, IAcheteurChocolatBourse {

	private Variable margeMin;
	protected Integer cryptogramme;
	private Journal journalEq6;
	private HashMap<Chocolat, Double> quantitevendueparstep;
	private Distributeur1 EQ6;
	private Variable soldeBancaire;
	private ArrayList<Double> historiqueMG;
	private ArrayList<Double> historiqueBG;
	private ArrayList<Double> historiqueHGE;
	private Variable stockMG;
	private Variable stockHGE;
	private Variable stockBG;
	

	public Distributeur1() { /** @author Mélissa Tamine */
		this.margeMin=new Variable(getNom()+" marge minimale", this, 0.0, 100.0, 10.0);
		this.journalEq6 = new Journal("Eq6 activites", this);
		this.soldeBancaire = new Variable("EQ6 Solde Bancaire", this, 1000000);
		this.stockHGE = new Variable("Stock HGE", this, 0);
		this.stockMG = new Variable("Stock MG", this, 0);
		this.stockBG = new Variable ("Stock BG", this, 0);
	
	}

	public String getNom() {
		return "EQ6 : IMTermarché";
	}

	public String getDescription() {
		return "Grande distribution vendant notamment du chocolat";
	}

	public Color getColor() {
		return new Color(230, 126, 34);
	}

	public void initialiser() {
	}

	public void setCryptogramme(Integer crypto) {
		this.cryptogramme = crypto;
	}
	
	public Variable getSoldeBancaire() {
		return this.soldeBancaire;
	
	}
	
	public ArrayList<Double> getHistoriqueMG() {
		return this.historiqueMG;
	}
	
	public ArrayList<Double> getHistoriqueBG() {
		return this.historiqueBG;
	}
	
	public ArrayList<Double> getHistoriqueHGE() {
		return this.historiqueHGE;
	}
	
	public Variable getStockHGE() {
		return this.stockHGE;
	}
	
	public Variable getStockMG() {
		return this.stockMG;
	}
	
	public Variable getStockBG() {
		return this.stockBG;
	}
	
	private double fraisStockage() {
		double cout =0;
		cout+= 0.0001*this.getStockHGE().getValeur();
		cout+= 0.0001*this.getStockMG().getValeur();
		cout+= 0.0001*this.getStockBG().getValeur();
		return cout;
	}
	
	private HashMap<Chocolat, Double> derniereVente() {

			HashMap<Chocolat, Double> vente_produit= new HashMap<Chocolat, Double>();
			double vente_stockMG = 0;
			double vente_stockHGE = 0;
			double vente_stockBG = 0;
			

			if (Filiere.LA_FILIERE.getEtape()>0) {
				
			if ( stockMG.getHistorique().getTaille()  > 2 ) {
				vente_stockMG += stockMG.getHistorique().get(stockMG.getHistorique().getTaille() -2).getValeur() - stockMG.getValeur();

			} else {
				vente_stockMG=0; }

			if ( stockHGE.getHistorique().getTaille()  > 2 ) {
				vente_stockHGE+= stockHGE.getHistorique().get(stockHGE.getHistorique().getTaille() -2).getValeur() - stockHGE.getValeur();

			} else {
				vente_stockHGE=0;}

			if ( stockBG.getHistorique().getTaille()  > 2 ) {
				vente_stockBG += stockBG.getHistorique().get(stockBG.getHistorique().getTaille() -2).getValeur() - stockBG.getValeur();

			} else {
				vente_stockBG=0;}


			vente_produit.put(Chocolat.CHOCOLAT_MOYENNE, vente_stockMG);
			vente_produit.put(Chocolat.CHOCOLAT_BASSE, vente_stockBG);
			vente_produit.put(Chocolat.CHOCOLAT_HAUTE_EQUITABLE, vente_stockHGE);

			
		}
			return vente_produit;
	}
	

	public List<String> getNomsFilieresProposees() {
		return new ArrayList<String>();
	}

	public Filiere getFiliere(String nom) {
		return null;
	}

	public List<Variable> getIndicateurs() {
		List<Variable> res=new ArrayList<Variable>();
		res.add(soldeBancaire);
		return res;
	}

	public List<Variable> getParametres() {
		List<Variable> res=new ArrayList<Variable>();
		res.add(this.margeMin);
		return res;
	}

	public List<Journal> getJournaux() {
		List<Journal> res=new ArrayList<Journal>();
		res.add(journalEq6);
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
	public void receptionner(Chocolat chocolat, double quantite) {
		// TODO Auto-generated method stub
		
	}


	public boolean commercialise(Chocolat choco) {
		// TODO Auto-generated method stub
		return false;
	}

	public double quantiteEnVente(Chocolat choco) {
		// TODO Auto-generated method stub
		return 0;
	}

	public double prix(Chocolat choco) {
		// TODO Auto-generated method stub
		return 0;
	}


	public void vendre(ClientFinal client, Chocolat choco, double quantite) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void next() {
		// TODO Auto-generated method stub
		 /** @author Mélissa Tamine */
		int step = Filiere.LA_FILIERE.getEtape();
        double solde = this.getSoldeBancaire().getValeur();
        this.journalEq6.ajouter("Solde : "+solde);
        this.getSoldeBancaire().retirer(this, this.fraisStockage());
       

		// On met les historiques à jour
        
        HashMap<Chocolat, Double> derniereVente = derniereVente();
        for (Chocolat c : derniereVente.keySet()) {
                if (c.getGamme()==Gamme.MOYENNE && !(c.isEquitable())) {
                        getHistoriqueMG().add(derniereVente.get(c));
                        quantitevendueparstep.put(c, 0.0);
                }
                if (c.getGamme()==Gamme.BASSE && !(c.isEquitable())) {
                        getHistoriqueBG().add(derniereVente.get(c));
                        quantitevendueparstep.put(c, 0.0);
                }
               
                if (c.getGamme()==Gamme.HAUTE && (c.isEquitable())){
                        getHistoriqueHGE().add(derniereVente.get(c));
                        quantitevendueparstep.put(c, 0.0);
                
                }
        }
	}

	
		
	
}
