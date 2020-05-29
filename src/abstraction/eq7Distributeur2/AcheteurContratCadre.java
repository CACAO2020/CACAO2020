package abstraction.eq7Distributeur2;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import abstraction.eq8Romu.contratsCadres.Echeancier;
import abstraction.eq8Romu.contratsCadres.ExemplaireContratCadre;
import abstraction.eq8Romu.contratsCadres.IAcheteurContratCadre;
import abstraction.eq8Romu.contratsCadres.IVendeurContratCadre;
import abstraction.eq8Romu.contratsCadres.SuperviseurVentesContratCadre;
import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.ChocolatDeMarque;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;

public class AcheteurContratCadre extends AbsAcheteurContratCadre implements IAcheteurContratCadre, IActeur {

	public AcheteurContratCadre(Distributeur2 ac) {
		super(ac);
	}
	
	public void initialiser() {
	}
	
	public void next() {
		// On supprime les contrats obsolètes
		supprimerContratsObsoletes();
		// Mise à jour du journal des contrats
		majJournalContrats();
		// Proposition de nouveaux contrats pour les chocolats de marque
		proposerContratsChocoDeMarque();
		// Proposition de nouveaux contrats pour les types de chocolat
		proposerContratsChoco();	
	}

	// Supprime de la liste nosContrats tous les contrats devenus obsolètes
	public void supprimerContratsObsoletes() {
		List<ExemplaireContratCadre> contratsObsoletes=new LinkedList<ExemplaireContratCadre>();
		for (ExemplaireContratCadre contrat : nosContrats) {
			if (contrat.getQuantiteRestantALivrer() == 0.0 && contrat.getMontantRestantARegler() == 0.0) {
				contratsObsoletes.add(contrat);
				journal.ajouter(Journal.texteColore(warningColor, Color.BLACK, "[OBSOLESCENCE] Le contrat avec " + contrat.getVendeur().getNom() + " pour le " + contrat.getProduit() + " a été retiré."));
			}
		}
		this.nosContrats.removeAll(contratsObsoletes);
	}
	
	// Propose un contrat à chaque vendeur pour chaque chocolat de marque
	public void proposerContratsChocoDeMarque() {
		// IA : l'acheteur propose un contrat à chaque vendeur et pour chaque chocolat de marque à chaque étape
		// Les durées des échéanciers et les quantités livrées à chaque étape sont fixées (resp. 10 et 1.0)
		SuperviseurVentesContratCadre superviseur = Filiere.LA_FILIERE.getSuperviseurContratCadre();
		int etape = Filiere.LA_FILIERE.getEtape();	
		for (ChocolatDeMarque choco : ac.tousLesChocolatsDeMarquePossibles()) {
			List<IVendeurContratCadre> vendeurs = superviseur.getVendeurs(choco);
			ExemplaireContratCadre contrat;
			for (IVendeurContratCadre vendeur : vendeurs) {
				contrat = superviseur.demande(ac, vendeur, choco, new Echeancier(etape, 10, 1.0), ac.cryptogramme);
				if (contrat != null) {
					nosContrats.add(contrat);
					notifierNouveauContrat(contrat);
				}
			}
		}	
	}
	
	// Propose un contrat à chaque vendeur pour chaque type de chocolat
	public void proposerContratsChoco() {
		// IA : l'acheteur propose un contrat à chaque vendeur et pour chaque type de chocolat à chaque étape
		// Les durées des échéanciers et les quantités livrées à chaque étape sont fixées (resp. 10 et 1.0)
		SuperviseurVentesContratCadre superviseur = Filiere.LA_FILIERE.getSuperviseurContratCadre();
		int etape = Filiere.LA_FILIERE.getEtape();	
		for (Chocolat choco : ac.nosChoco) {
			List<IVendeurContratCadre> vendeurs = superviseur.getVendeurs(choco);
			ExemplaireContratCadre contrat;
			for (IVendeurContratCadre vendeur : vendeurs) {
				contrat = superviseur.demande(ac, vendeur, choco, new Echeancier(etape+1, 10, 1.0), ac.cryptogramme);
				if (contrat != null) {
					nosContrats.add(contrat);
					notifierNouveauContrat(contrat); 
				}
			}
		}	
	}
	
	// Informe de la signature d'un contrat via le journal principal
	public void notifierNouveauContrat(ExemplaireContratCadre contrat) {
		journal.ajouter(Journal.texteColore(positiveColor, Color.BLACK, "[NOUVEAU CONTRAT] Vendeur : " + contrat.getVendeur().getNom() + "; Produit : " + produitToString(contrat.getProduit()) + "; Quantité  : " + Journal.doubleSur(contrat.getQuantiteTotale(),2) + "; Echéancier : " + echeancierToString(contrat.getEcheancier()) + "."));
	}
	
	// Transforme un produit (Chocolat ou ChocolatDeMarque) en son String équivalent
	public String produitToString(Object produit) {
		if (produit instanceof Chocolat) {
			return ((Chocolat)produit).name();
		} else if (produit instanceof ChocolatDeMarque) {
			return ((ChocolatDeMarque)produit).name();
		} else {
			return null;
		}
	}
	
	// Transforme un échéancier en String sous la forme [etape:quantité, ...]
	public String echeancierToString(Echeancier e) {
		int etape = Filiere.LA_FILIERE.getEtape();
		String res = "[";
		for (int i = etape; i <= e.getStepFin(); i++) {
			if (i == e.getStepFin()) {
				res += i + ":" + Journal.doubleSur(e.getQuantite(i),2) + "]";
			} else {
				res += i + ":" + Journal.doubleSur(e.getQuantite(i),2) + "; ";
			}
		}
		return res;
	}
	
	// Ajoute au journal des contrats des détails sur les contrats actuels
	public void majJournalContrats() {
		journalContrats.ajouter(Journal.texteColore(metaColor, Color.BLACK, "[ETAPE " + Filiere.LA_FILIERE.getEtape() + "] Contrats en cours"));
		journalContrats.ajouter(Journal.texteSurUneLargeurDe("Vendeur", 30) + Journal.texteSurUneLargeurDe("Chocolat", 30) + Journal.texteSurUneLargeurDe("Quantité", 10) + Journal.texteSurUneLargeurDe("Echéancier", 100) + Journal.texteSurUneLargeurDe("", 30));
		for (ExemplaireContratCadre contrat : nosContrats) {
			journalContrats.ajouter(Journal.texteSurUneLargeurDe(contrat.getVendeur().getNom(), 30) + Journal.texteSurUneLargeurDe(produitToString(contrat.getProduit()),30) + Journal.texteSurUneLargeurDe(Journal.doubleSur(contrat.getQuantiteTotale(), 2),10) + Journal.texteSurUneLargeurDe(echeancierToString(contrat.getEcheancier()), 100) + Journal.texteSurUneLargeurDe("", 30));
		}
	}

	public Echeancier contrePropositionDeLAcheteur(ExemplaireContratCadre contrat) {
		// IA : on tente la négociation de l'échéancier dans 90% des cas en multipliant la quantité à livrer lors de la première livraison (par 2.5)
		if (Math.random()<0.1) {
			return contrat.getEcheancier();
		} else {
			Echeancier e = contrat.getEcheancier();
			e.set(e.getStepDebut(), e.getQuantite(e.getStepDebut())*2.5);
			return e;
		}
	}

	public double contrePropositionPrixAcheteur(ExemplaireContratCadre contrat) {
		// IA : on tente la négociation du prix dans 90% des cas en le diminuant de 5%
		if (Math.random()<0.1) {
			return contrat.getPrixALaTonne(); 
		} else {
			return contrat.getPrixALaTonne()*0.95;
		}
	}

	public void receptionner(Object produit, double quantite, ExemplaireContratCadre contrat) {
		if (produit instanceof ChocolatDeMarque) {
			journal.ajouter(Journal.texteColore(positiveColor, Color.BLACK, "[RÉCEPTION] Réception de " + Journal.doubleSur(quantite,2) + " tonnes de " + ((ChocolatDeMarque)produit).name() + " par " + contrat.getVendeur().getNom() + "."));
			ac.getStock().ajouterStockChocolat((ChocolatDeMarque)produit, quantite);
			ac.getStock().chocoReceptionne.get(((ChocolatDeMarque)produit).getChocolat()).setValeur(ac, ac.getStock().chocoReceptionne.get(((ChocolatDeMarque)produit).getChocolat()).getValeur() + quantite);
		} else if (produit instanceof Chocolat)  {
			journal.ajouter(Journal.texteColore(positiveColor, Color.BLACK, "[RÉCEPTION] Réception de " + Journal.doubleSur(quantite,2) + " tonnes de " + ((Chocolat)produit).name() + " par " + contrat.getVendeur().getNom() + "."));
			ac.getStock().ajouterStockChocolat(new ChocolatDeMarque((Chocolat)produit, getNom()), quantite);
			ac.getStock().chocoReceptionne.get((Chocolat)produit).setValeur(ac, ac.getStock().chocoReceptionne.get((Chocolat)produit).getValeur() + quantite);
		}
	}

}
