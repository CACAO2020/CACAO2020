package abstraction.eq4Transformateur2;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import abstraction.eq8Romu.contratsCadres.ExemplaireContratCadre;
import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.Feve;

public class Transformateur2_next extends Transformateur2_negoce {
	private int tourAvecCapaEnTrop;

	public Transformateur2_next() {
		super();
		this.tourAvecCapaEnTrop = 0;
	}

	public int getTourAvecCapaEnTrop() {
		return tourAvecCapaEnTrop;
	}

	public void setTourAvecCapaEnTrop(int tourAvecCapaEnTrop) {
		this.tourAvecCapaEnTrop = tourAvecCapaEnTrop;
	}

	public void next() {
		List<ExemplaireContratCadre> contratsObsoletes = new LinkedList<ExemplaireContratCadre>();
		for (ExemplaireContratCadre contrat : this.mesContratEnTantQueVendeur) {
			if (contrat.getQuantiteRestantALivrer() == 0.0 && contrat.getMontantRestantARegler() == 0.0) {
				contratsObsoletes.add(contrat);
			}
		}
		this.mesContratEnTantQueVendeur.removeAll(contratsObsoletes);

		super.majQuantitePateCC();

		// Transformer selon priorités de production
		this.transformerSelonPriorites();

		// Gestion des priorites de prod

		// Investissements
		double solde = super.getSolde();
		if (solde >= 0) {
			double investTFEP = solde * INVESTI_MOYPROD;
			super.investirCapaTFEP(investTFEP);
			this.investissementChoco();
		}
	}

	public void transformerSelonPriorites() {
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
					super.getNombreDeTourDautoMin()) - -super.getStockPateValeur(PateInterne.PATE_MOYENNE);
			FevesATransfo.put(Feve.FEVE_MOYENNE, pateMoyManquante / super.getCoeffTFEP());
		}
		// TRANSFO CHOCO
		Map<PateInterne, Double> PateATransfo = new HashMap<PateInterne, Double>();
		List<Chocolat> chocos = new LinkedList<Chocolat>();
		chocos.add(Chocolat.CHOCOLAT_MOYENNE_EQUITABLE);
		chocos.add(Chocolat.CHOCOLAT_HAUTE_EQUITABLE);
		chocos.add(Chocolat.CHOCOLAT_HAUTE);
		for (Chocolat choco : chocos) {
			PateInterne pate = super.creerPateAPartirDeChocolat(choco);
			PateATransfo.put(pate, super.getStockPateValeur(pate));
			super.transformationPate(PateATransfo);
		}
		// PATE POUR CHOCO
		List<PateInterne> patesRestantes = new LinkedList<PateInterne>();
		patesRestantes.add(PateInterne.PATE_MOYENNE_EQUITABLE);
		patesRestantes.add(PateInterne.PATE_HAUTE_EQUITABLE);
		patesRestantes.add(PateInterne.PATE_HAUTE);
		for (PateInterne pate : patesRestantes) {
			Feve feve = super.creerFeve(pate);
			FevesATransfo.put(feve, super.getStockFevesValeur(feve));
		}

		// TRANSFO FEVES
		super.transformationFeves(FevesATransfo);
	}

	public void investissementChoco() {
		double capaPateEnPlus = super.getCapaciteMaxTFEP() - super.getQuantitePateCCTotaleValeur();
		if (capaPateEnPlus > 0) {
			double capaNecessaire = capaPateEnPlus - super.getCapaciteMaxTPEC();
			double rapport = capaNecessaire / capaPateEnPlus;
			if (rapport < 0) {
				boolean noStockPateEnPlus = super.getStockPateValeur(PateInterne.PATE_MOYENNE_EQUITABLE)
						+ super.getStockPateValeur(PateInterne.PATE_HAUTE_EQUITABLE)
						+ super.getStockPateValeur(PateInterne.PATE_HAUTE) == 0;
				if (noStockPateEnPlus) {
					this.setTourAvecCapaEnTrop(this.getTourAvecCapaEnTrop() + 1);

					if (this.getTourAvecCapaEnTrop() > 3) {
						super.setCapaciteMaxTPEC(capaPateEnPlus);
						this.setTourAvecCapaEnTrop(0);
					}
				}
			} else if (rapport > 0.1) {
				double solde = super.getSolde();
				double qteAInvest = super.getCoutPourAugmenterCapaTPEC() * capaNecessaire;
				if (qteAInvest > 0.1 * solde) {
					super.investirCapaTPEC(0.1 * solde);
				} else {
					super.investirCapaTPEC(qteAInvest);
				}
			}
		}
	}
}
