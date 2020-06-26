package abstraction.eq4Transformateur2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import abstraction.eq8Romu.contratsCadres.ExemplaireContratCadre;
import abstraction.eq8Romu.contratsCadres.FiliereTestContratCadre;
import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.Feve;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;

public class Transformateur2 extends Transformateur2_negoce {
	private int tourAvecCapaPateEnTrop;
	private int tourAvecCapaChocEnTrop;

	public Transformateur2 () {
		super();
		this.tourAvecCapaPateEnTrop = 0;
		this.tourAvecCapaChocEnTrop = 0;
	}

	public int getTourAvecCapaChocEnTrop() {
		return tourAvecCapaChocEnTrop;
	}

	public int getTourAvecCapaPateEnTrop() {
		return tourAvecCapaPateEnTrop;
	}

	public void setTourAvecCapaPateEnTrop(int tourAvecCapaPateEnTrop) {
		this.tourAvecCapaPateEnTrop = tourAvecCapaPateEnTrop;
	}

	public void setTourAvecCapaChocEnTrop(int tourAvecCapaEnTrop) {
		this.tourAvecCapaChocEnTrop = tourAvecCapaEnTrop;
	}
	
	public List<String> getNomsFilieresProposees() {
		ArrayList<String> liste = new ArrayList<String>();
		liste.add("TestFiliereTransformateur2") ;
		return liste ;
	}

	public Filiere getFiliere(String nom) {
		switch (nom) { 
		case "TestFiliereTransformateur2" : return new TestFiliereTransformateur2() ;
	    default : return null;
		}
	}

	public void next() {
		List<ExemplaireContratCadre> contratsObsoletes = new LinkedList<ExemplaireContratCadre>();
		for (ExemplaireContratCadre contrat : this.mesContratEnTantQueVendeur) {
			if (contrat.getQuantiteRestantALivrer() == 0.0 && contrat.getMontantRestantARegler() == 0.0) {
				contratsObsoletes.add(contrat);
			}
		}
		for (PateInterne pate : PateInterne.values()) {
			super.setMargeVisee(pate);
		}
		this.mesContratEnTantQueVendeur.removeAll(contratsObsoletes);
		super.majQuantitePateCC();
		
		double montant = super.coutStocks() ; //coût des stocks de l'année précédente

		// Transformer selon priorités de production
		montant += this.transformerSelonPriorites();

		// Gestion des priorites de prod
		
		// Coûts de gestion
		montant += super.getCoutFixeValeur() ;
		Filiere.LA_FILIERE.getBanque().virer(this, super.cryptogramme, Filiere.LA_FILIERE.getBanque(), montant) ;
		this.calculeValeurDesStocks() ;
		
		// Investissements
		this.investissementPate();
		this.investissementChoco();
	}

	public double transformerSelonPriorites() {
		
		Map<Feve, Double> FevesATransfo = new HashMap<Feve, Double>();
		// PATE POUR CC EN PREMIER
		int tourAutoBasse = super.nbToursAutonomiePate(PateInterne.PATE_BASSE);
		int tourAutoMoy = super.nbToursAutonomiePate(PateInterne.PATE_MOYENNE);
		if (tourAutoBasse < super.getNombreDeTourDautoMin()) {
			Double pateBasseManquante = super.getQuantiteALivrerPourITour(PateInterne.PATE_BASSE,
					super.getNombreDeTourDautoMin()) - super.getStockPateValeur(PateInterne.PATE_BASSE);
			if (tourAutoMoy < super.getNombreDeTourDautoMin()) {
				Double pateMoyManquante = super.getQuantiteALivrerPourITour(PateInterne.PATE_MOYENNE,
						super.getNombreDeTourDautoMin()) - -super.getStockPateValeur(PateInterne.PATE_MOYENNE);
				if (pateBasseManquante < pateMoyManquante) {
					FevesATransfo.put(Feve.FEVE_BASSE, pateBasseManquante / super.getCoeffTFEP());
					FevesATransfo.put(Feve.FEVE_MOYENNE, pateMoyManquante / super.getCoeffTFEP());
				} else {
					FevesATransfo.put(Feve.FEVE_MOYENNE, pateMoyManquante / super.getCoeffTFEP());
					FevesATransfo.put(Feve.FEVE_BASSE, pateBasseManquante / super.getCoeffTFEP());
				}
			} else {
				FevesATransfo.put(Feve.FEVE_BASSE, pateBasseManquante / super.getCoeffTFEP());
			}
		} else if (tourAutoMoy < super.getNombreDeTourDautoMin()) {
			Double pateMoyManquante = super.getQuantiteALivrerPourITour(PateInterne.PATE_MOYENNE,
					super.getNombreDeTourDautoMin()) -super.getStockPateValeur(PateInterne.PATE_MOYENNE);
			FevesATransfo.put(Feve.FEVE_MOYENNE, pateMoyManquante / super.getCoeffTFEP());
		}
		// LISTE DE PATE POUR CHOCO
		Map<PateInterne, Double> PateATransfo = new HashMap<PateInterne, Double>();
		List<Chocolat> chocos = new LinkedList<Chocolat>();
		chocos.add(Chocolat.CHOCOLAT_MOYENNE_EQUITABLE);
		chocos.add(Chocolat.CHOCOLAT_HAUTE_EQUITABLE);
		chocos.add(Chocolat.CHOCOLAT_HAUTE);
		for (Chocolat choco : chocos) {
			PateInterne pate = super.creerPateAPartirDeChocolat(choco);
			PateATransfo.put(pate, super.getStockPateValeur(pate));
		
		}
		// FEVE POUR CHOCO
		List<PateInterne> patesRestantes = new LinkedList<PateInterne>();
		patesRestantes.add(PateInterne.PATE_MOYENNE_EQUITABLE);
		patesRestantes.add(PateInterne.PATE_HAUTE_EQUITABLE);
		patesRestantes.add(PateInterne.PATE_HAUTE);
		for (PateInterne pate : patesRestantes) {
			Feve feve = super.creerFeve(pate);
			FevesATransfo.put(feve, super.getStockFevesValeur(feve));
		}

		// TRANSFORMATION EFFECTIVE
		double cout = 0 ;
		this.correctionQuantitesTFEP(FevesATransfo) ; // limite avec les stocks disponibles
		this.correctionQuantitesTPEC(PateATransfo) ;
		cout += super.transformationPate(PateATransfo);
		cout += super.transformationFeves(FevesATransfo);
		return cout ;
	}
	
	
	// On augmente la capacité si c'est nécessaire pour subvenir aux contrats cadres ou pour pouvoir transformer nos stocks, sinon elle diminue au bout de 3 tours
	public void investissementPate() {
		double stock_feve = 0;
		stock_feve += super.getStockFevesValeur(Feve.FEVE_MOYENNE_EQUITABLE);
		stock_feve += super.getStockFevesValeur(Feve.FEVE_HAUTE_EQUITABLE);
		stock_feve += super.getStockFevesValeur(Feve.FEVE_HAUTE);
		double capaMinNec = (super.getQuantiteALivrerPourITour(PateInterne.PATE_BASSE, super.getNombreDeTourDautoMin()))/(super.getNombreDeTourDautoMin()-1);
		capaMinNec += (super.getQuantiteALivrerPourITour(PateInterne.PATE_MOYENNE, super.getNombreDeTourDautoMin()))/(super.getNombreDeTourDautoMin()-1);
//capaMinNec est la capacité nécessaire pour transformer la pate nécessaire pour les contrats cadres pour i tour en i-1 tours avec i le nombre min de tours d'auto
		double capapate = super.getCapaciteMaxTFEP();
		double solde = super.getSolde();
		if (capapate - capaMinNec < 0) {
			double qteAInv = (capaMinNec - capapate)*super.getCoutPourAugmenterCapaTFEP();
			if (qteAInv < solde) {
				super.investirCapaTFEP(qteAInv);
			}
			else if (solde*0.1 > 0) {
				super.investirCapaTFEP(solde*0.1);
			}
		}
		else if (capapate - capaMinNec - stock_feve < 0) {
			double qteAInv = (capaMinNec + stock_feve - capapate)*super.getCoutPourAugmenterCapaTFEP();
			if (qteAInv < solde*0.1) {
				super.investirCapaTFEP(qteAInv);
			}
			else if (solde*0.1 > 0) {
				super.investirCapaTFEP(solde*0.1);
			}
		}
		else {
			this.setTourAvecCapaPateEnTrop(this.getTourAvecCapaPateEnTrop()+1);
			if (this.getTourAvecCapaPateEnTrop() > 2) {
				super.setCapaciteMaxTFEP(capaMinNec + stock_feve);
				this.setTourAvecCapaPateEnTrop(0);
			}
		}
	}
	

	//On augmente la capacité pour pourvoir transformer nos stocks, si il n'y en a pas, elle diminue au bout de 3 tours
	public void investissementChoco() {
		double stock_pate = super.getStockPateValeur(PateInterne.PATE_MOYENNE_EQUITABLE)
				+ super.getStockPateValeur(PateInterne.PATE_HAUTE_EQUITABLE)
				+ super.getStockPateValeur(PateInterne.PATE_HAUTE);
		if (stock_pate > 3*super.getCapaciteMaxTPEC()) {
			double new_capa = stock_pate/3;
			double qteAInvest = super.getCoutPourAugmenterCapaTPEC() * new_capa;
			double solde = super.getSolde();
			if (qteAInvest > 0.3 * solde && solde > 0) {
				super.investirCapaTPEC(0.3 * solde);
			}
			else {
				super.investirCapaTPEC(qteAInvest);
			}
		}
		else {
			this.setTourAvecCapaChocEnTrop(this.getTourAvecCapaChocEnTrop() + 1);
			if (this.getTourAvecCapaChocEnTrop() > 3) {
				super.setCapaciteMaxTPEC(super.getCapaciteMaxTPEC()/3);
				this.setTourAvecCapaChocEnTrop(0);
			}
		}
	}
	
	public void correctionTFP (HashMap<Feve,Double> quantitesFeves) {
		for (Feve feve : Feve.values()) {
			if (quantitesFeves.get(feve) > super.getStockFevesValeur(feve)) {
				quantitesFeves.replace(feve, super.getStockFevesValeur(feve)) ;
			}
		}
	}
	
	public void correctionTPEC (HashMap<PateInterne,Double> quantitesPates) {
		for (PateInterne pate : PateInterne.values()) {
			if (quantitesPates.get(pate) > super.getStockPateValeur(pate)) {
				quantitesPates.replace(pate, super.getStockPateValeur(pate)) ;
			}
		}
	}
}
