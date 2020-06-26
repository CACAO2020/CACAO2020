package abstraction.eq4Transformateur2;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import abstraction.eq8Romu.contratsCadres.Echeancier;
import abstraction.eq8Romu.contratsCadres.ExemplaireContratCadre;
import abstraction.eq8Romu.contratsCadres.IVendeurContratCadre;
import abstraction.eq8Romu.produits.Feve;
import abstraction.eq8Romu.produits.Pate;
import abstraction.fourni.Filiere;
import abstraction.fourni.Variable;

public class Transformateur2_contratCadre extends Transformateur2_stocks_et_transfos implements IVendeurContratCadre {
	
	//Variables
	protected List<ExemplaireContratCadre> mesContratEnTantQueVendeur; //ne peut pas être ajouté dans les fonctions d'affichage
	private Map<PateInterne,Variable> quantitePateCC ;
	
	//Paramètres
	private Variable margePate ;
	private Variable facteurQuantiteLimite ;
	private Variable stockPateMin ;
	
	public Transformateur2_contratCadre() {
		super();
		this.mesContratEnTantQueVendeur=new LinkedList<ExemplaireContratCadre>();
		
		this.quantitePateCC = new HashMap<PateInterne, Variable>() ;
		this.quantitePateCC.put(PateInterne.PATE_BASSE, new Variable(getNom()+" quantité totale de pate basse à fournir pour les contrats cadres", this, 0)) ;
		this.quantitePateCC.put(PateInterne.PATE_MOYENNE, new Variable(getNom()+" quantité totale de pate moyenne à fournir pour les contrats cadres", this, 0)) ;
		this.quantitePateCC.put(PateInterne.PATE_HAUTE, new Variable(getNom()+" quantité totale de pate haute à fournir pour les contrats cadres", this, 0)) ;
		this.quantitePateCC.put(PateInterne.PATE_MOYENNE_EQUITABLE, new Variable(getNom()+" quantité totale de pate moyenne équitable à fournir pour les contrats cadres", this, 0)) ;
		this.quantitePateCC.put(PateInterne.PATE_HAUTE_EQUITABLE, new Variable(getNom()+" quantité totale de pate haute équitable à fournir pour les contrats cadres", this, 0)) ;
		
		this.margePate = new Variable(getNom()+" marge sur le prix de revente de la pâte", this, 1.5) ; 
		this.facteurQuantiteLimite = new Variable(getNom()+" pourcentage de la capacité de production à utiliser sur les contrats cadres", this, 0.9) ;
		this.stockPateMin = new Variable(getNom()+" quantité de stock minimale pour pouvoir envisager de vendre la pâte", this, 0) ; 
	}
	
	// récupère les attributs notés comme paramètres, utile pour les tests et sûrement appelé par des fonctions externes

	public List<Variable> getIndicateurs() { 
		List<Variable> res=super.getIndicateurs();
		for (PateInterne pate : PateInterne.values()) {
			res.add(this.quantitePateCC.get(pate)) ;
		}
		res.add(this.margePate) ;
		res.add(this.facteurQuantiteLimite) ;
		res.add(this.stockPateMin) ;
		return res;
	}

	// récupère les attributs notés comme paramètres, utile pour les tests et sûrement appelé par des fonctions externes
	
	public List<Variable> getParametres() { //idem
		List<Variable> res=super.getParametres();
		return res;
	}
	
	// getters
	
	public double getQuantitePateCCValeur(PateInterne pate) {
		return this.quantitePateCC.get(pate).getValeur();
	}
	
	public double getMargePate() {
		return this.margePate.getValeur();
	}
	
	public double getFacteurQuantiteLimite() {
		return this.facteurQuantiteLimite.getValeur();
	}
	
	public double getStockPateMin() {
		return this.stockPateMin.getValeur();
	}
	
	public List<ExemplaireContratCadre> getMesContratEnTantQueVendeur() {
		return mesContratEnTantQueVendeur;
	}

	public List<ExemplaireContratCadre> getContratDeCeType(PateInterne pateI) {
		Pate pate = pateI.conversionPateInterne();
		List<ExemplaireContratCadre> resu = new LinkedList<ExemplaireContratCadre>();
		for (ExemplaireContratCadre ex : mesContratEnTantQueVendeur) {
			if (ex.getProduit() == pate) {
				resu.add(ex);
			}
		}
		return resu;
	}
	
	public double getQuantiteALivrerAuStep(ExemplaireContratCadre e, int i) {
		return e.getEcheancier().getQuantite(i);
	}
	
	public double getQuantiteALivrerPourITour(PateInterne pate, int i) {
		List<ExemplaireContratCadre> li = this.getContratDeCeType(pate);
		double resu = 0;
		int step = Filiere.LA_FILIERE.getEtape();
		for (ExemplaireContratCadre ex : li) {
			for (int k = 0; k < i; k++) {
				resu += this.getQuantiteALivrerAuStep(ex, step + k);
			}
		}
		return resu;
	}
	
	// renvoie la valeur de la quantite totale de pate à fournir
	public double getQuantitePateCCTotaleValeur() {
		double valeur = 0 ;
		for (PateInterne pate : PateInterne.values()) {
			valeur += this.getQuantitePateCCValeur(pate) ;
		}
		return valeur ;
	}
	
	//Renvoie la moyenne pondérée des prix de revente de notre pate pondérée par la qte a vendre
	public double getPrixMoyReventePate(PateInterne pate) {
		if (this.getQuantitePateCCValeur(pate) == 0) {
			return PRIX_MOYEN_SUPPOSE_PATE;
		}
		else {
		double resu = 0;
		double quantite = 0;
		for (ExemplaireContratCadre exemplaireContratCadre : mesContratEnTantQueVendeur) {
			if ((Pate)exemplaireContratCadre.getProduit() == pate.conversionPateInterne()) {
				resu += exemplaireContratCadre.getMontantRestantARegler();
				quantite += exemplaireContratCadre.getQuantiteRestantALivrer();
			}
		}
		if (resu == 0||quantite == 0) {
			return PRIX_MOYEN_SUPPOSE_PATE;									//UNE CONSTANTE IMPORTANTE -> AU DEBUT DE LA SIMUL ON SUPPORT LE PRIX DE REVENTE DE LA PATE DE 1000
																					//CA PERMET DE LANCER LES NEGO
		}
		else {
			return resu/quantite;
		}
		}
	}
	// permet de mettre à jour quantitePateCC grâce à la liste des contrats actuels
	// il faut mettre à jour à chaque début de tour, le contrats ayant été négociés à la fin du tour précédent
	
	public void majQuantitePateCC () {
		
		for (PateInterne pate : PateInterne.values()) {
			double valeur = 0 ;
			for (ExemplaireContratCadre contrat : this.mesContratEnTantQueVendeur) {
				if (contrat.getProduit() == pate.conversionPateInterne()) {
					valeur += this.getQuantiteALivrerAuStep(contrat, Filiere.LA_FILIERE.getEtape()+1) ; }
			}
			Variable nouvelleQuantite = this.quantitePateCC.get(pate) ;
			nouvelleQuantite.setValeur(this, valeur);
			this.journalEq4.ajouter("valeur maj" + valeur);
			this.quantitePateCC.replace(pate, nouvelleQuantite) ;
		}
	}
	
	// permet d'obtenir la pate de type PateInterne correspondant au produit mis en argument
	// retourne null si ça ne fait pas partie des deux types de pates vendables
	
	public PateInterne conversionPate (Object produit) {
		if (produit != null) {
			if (produit == Pate.PATE_BASSE) {
				return PateInterne.PATE_BASSE ;
			}
			else {
				if (produit == Pate.PATE_MOYENNE) {
					return PateInterne.PATE_MOYENNE ;
				}
				else { return null ; }
			}
		} else { return null ; }
	}
	
	// calcule l'écart relatif entre deux doubles (servira de norme pour mesurer la différence entre 
	// deux échéanciers en première approche)
	
	public double ecartRelatif (Echeancier e1, Echeancier e2) {
		double a = e1.getQuantiteTotale() ;
		double b = e2.getQuantiteTotale() ;
		if (b == 0) {
			return a ;
		}
		else {
			return Math.abs(a-b)/b ;
		}
	}
	
	// modifie l'échéancier, avec la capacité actuelle (ou prévue, par la suite) permettant d'assurer la 
	// quantité voulue par l'acheteur pour chaque étape
	// si parfait == true, retourne l'échéancier idéal pour notre production
	// on prend une marge et on fixe le seuil plus bas, à 80%, dans le cas d'une rupture d'approvisionnement
	// on prend une marge sur la première quantité, histoire d'assurer le coup
	
	public void echeancierLimite (Echeancier e, PateInterne pate, boolean parfait) {
//		if (this.nbToursAutonomiePateEtFeves(pate) <= super.getNombreDeTourDautoMax()) {
		double quantiteLimite = 50;//this.getFacteurQuantiteLimite()*super.getCapaciteMaxTFEP() ;	//On suppose qu'il n'y aura pas plus de 50 feves de ce type dispo par contrat, c'est empirique
		quantiteLimite -= this.getQuantitePateCCTotaleValeur() ;
		if (quantiteLimite < 0) {
			this.journalEq4.ajouter("quantite limite" + quantiteLimite);
		}
		
		
		for (int i = e.getStepDebut() ; i < e.getNbEcheances() ; i++) {
			if (parfait || e.getQuantite(i)>quantiteLimite) {
				e.set(i, quantiteLimite);
			}
		}
		if (super.getStockPateValeur(pate) - e.getQuantite(e.getStepDebut()) < this.getStockPateMin()) {
			e.set(e.getStepDebut(), super.getStockPateValeur(pate) - this.getStockPateMin()) ;
		}
//		}
	}
	
	// renvoie un échéancier dont les quantités sont la moyenne des quantités des deux échéanciers en argument
	// pour l'instant on ne l'utilise pas
	
/*	public Echeancier echeancierMoyen (Echeancier e1, Echeancier e2) {
			
			Echeancier e = e1 ;
			for (int i = e.getStepDebut() ; i < e.getNbEcheances() ; i++) {
				e.set(i, (e1.getQuantite(i)+e2.getQuantite(i))/2);
			}
			return e ;
		} */

	public Echeancier contrePropositionDuVendeur(ExemplaireContratCadre contrat) {
		
		PateInterne pate = this.conversionPate(contrat.getProduit()) ;
		Echeancier echeancier = contrat.getEcheancier() ;
		List<Echeancier> liste = contrat.getEcheanciers() ;
		
		if (contrat.getQuantiteTotale() == 0) { return null ;}
		
		if (!this.vend(contrat.getProduit())) { return null ; } // on ne vend pas cette pâte 
		
		if (liste.size()<2) { //aucune proposition n'a été faite, on envoie notre échéancier idéal
			 this.echeancierLimite(echeancier, pate, true) ;
			 return echeancier ;
		}
		else {
			/* Echeancier echeancierPrecedent = liste.get(liste.size()-2) ;
			double norme = this.ecartRelatif(echeancier,echeancierPrecedent) ; */
			
			//on accepte après trop d'itérations, on ajustera notre production en conséquence mais on garde 
			// un écart relatif correct pour pas avoir de valeur aberrante si la demande est trop grande
			if (liste.size()>1000) { 
				return null ;
			}
		/*	// si l'écart relatif est suffisamment faible, on regarde si la répartition respecte nos limitations
			if (norme < 0.1) {
				Echeancier e = this.echeancierLimite(echeancier, pate, false) ;
				if (this.ecartRelatif(echeancier, e) < 0.1) {
					return echeancier ;
				} }
			// sinon, on envoie l'échéancier moyen entre notre précédent échéancier et celui qu'on reçoit, et on
			// le corrige de manière à respecter nos contraintes de production
			Echeancier e = this.echeancierMoyen(echeancier, echeancierPrecedent) ;
			return this.echeancierLimite(e, pate, false) ;
			} */
			
			
			//Si l'échancier ne dépasse pas les limites, on accepte. Sinon, on retourne l'échéancier tronqué
			this.echeancierLimite(echeancier, pate, false) ;
			return echeancier ;
		}
	}
	
	// proposition iniatiale pour le prix, pour l'instant simple marge par rapport au coût de production 
	// il faudrait diminuer la marge en fonction de la quantité, que ça soit plus rentable d'acheter en gros
	
	public double propositionPrix(ExemplaireContratCadre contrat) {
		PateInterne pate = this.conversionPate(contrat.getProduit()) ;
		this.journalEq4.ajouter(""+(this.getMargePate()*super.getCoutMoyenPateValeur(pate)));
		return this.getMargePate()*super.getCoutMoyenPateValeur(pate) ;
			
				//5000.0 + (1000.0/contrat.getQuantiteTotale());}// plus la quantite est elevee, plus le prix est interessant
	}

	// la contre-proposition fait la moyenne de notre dernière proposition et de celle qui arrive, sauf si celle-ci
	// est acceptable au vu de notre marge
	
	public double contrePropositionPrixVendeur(ExemplaireContratCadre contrat) {
		double prixVoulu = 0 ;
		PateInterne pate = this.conversionPate(contrat.getProduit()) ;
		List<Double> liste = contrat.getListePrixALaTonne() ;
		int n = liste.size() ;
		
		this.journalEq4.ajouter("prix courant "+contrat.getPrixALaTonne());
		this.journalEq4.ajouter("prix voulu "+ this.propositionPrix(contrat));
		
		if (contrat.getPrixALaTonne() >= this.propositionPrix(contrat)) { // si le contrat est plus intéressant que notre contrat maximal, on accepte
			return contrat.getPrixALaTonne() ;
		}
		
		if (liste.size() >= 1000) { // s'il y a eu trop d'itérations, on refuse le prix de l'acheteur
			return 0 ;
		}
		
		if (liste.size() >= 2) { //on prend notre prix précédent et on fait la moyenne avec le prix de l'acheteur s'il excède une marge minimum
			
			if (liste.get(n-2) < liste.get(n-1)) {
				return contrat.getPrixALaTonne() ;
			}
			
			prixVoulu = (liste.get(n-2) + liste.get(n-1))/2 ;
			double ecartRelatif = Math.abs(liste.get(n-2) - liste.get(n-1))/liste.get(n-1) ;
			
			if (prixVoulu < 1.1*super.getCoutMoyenPateValeur(pate)) { //marge minimum à 10%, on reste sur nos positions
				return liste.get(n-2) ;
			} else { 
				if (ecartRelatif < 0.04) { // si l'écart relatif est inférieur à 4% (arbitraire), on ne chipote plus
					return contrat.getPrixALaTonne() ;
			}
			else { return prixVoulu ; }} // on propose notre prix
		}
		else { // on a jamais proposé de prix
			return propositionPrix(contrat) ;
		}
	}
	
	//

	public void notificationNouveauContratCadre(ExemplaireContratCadre contrat) {
		this.mesContratEnTantQueVendeur.add(contrat);
		this.majQuantitePateCC(); 
		this.journalEq4.ajouter("quantite pate cc totale" + this.getQuantitePateCCTotaleValeur());
	}
	
	
	// renvoie true si on vend le produit : si on a pas un stock minimal, on ne vend pas (ou alors on permet
	// d'avoir un contrat avec une première année à vide si on arrive à le négocier ?)
	// dans nos contraintes d'acteur, on ne vend que des pates basse et moyenne
	
	public boolean vend(Object produit) {
		PateInterne pate = this.conversionPate(produit) ;
		if (pate != null && ((pate == PateInterne.PATE_BASSE) || (pate == PateInterne.PATE_MOYENNE))) {
			return super.getStockPateValeur(pate) - this.getQuantitePateCCValeur(pate) > this.getStockPateMin() ; // quantité de stock minimale
		}
		else { 	
			return false ; }
	}
	
	// met à jour le stock et renvoie la quantité livrée effectivement

	public double livrer(Object produit, double quantite, ExemplaireContratCadre contrat) {
		
		PateInterne pate = this.conversionPate(contrat.getProduit()) ;
		double livre = Math.min(super.getStockPateValeur(pate), quantite);
		if (livre>0.0) {
			super.setStockPateValeur(pate, super.getStockPateValeur(pate)-livre);
		}
		return livre;
	}
	
//  Nombre de tours d'autonmies restants pour couvrir les echeanciers de nos contrats cadres	
		public int nbToursAutonomiePate(PateInterne pate) {
			int resu = -1;
			double pate_echeancier = 0;
			int step = Filiere.LA_FILIERE.getEtape();
			List<ExemplaireContratCadre> contratsDeCeType = this.getContratDeCeType(pate);
			while (pate_echeancier < super.getStockPateValeur(pate)) {
				resu++;
				double qte_tour = 0;
				for (ExemplaireContratCadre ex : contratsDeCeType) {
					qte_tour += this.getQuantiteALivrerAuStep(ex, step + resu);
				}
				if (qte_tour == 0) {
					return 1000;			// On a assez pour subvenir à toutes le echeances
				}
				else {
					pate_echeancier += qte_tour;
				}
			}
			return resu;
		}
		
		public int nbToursAutonomiePateEtFeves(PateInterne pate) {
			int resu = -1;
			double pate_echeancier = 0;
			int step = Filiere.LA_FILIERE.getEtape(); //RETIRE POUR TEST
			//int step = 1; //pour test
			List<ExemplaireContratCadre> contratsDeCeType = this.getContratDeCeType(pate);
			while (pate_echeancier < super.getStockPateValeur(pate)) {
				resu++;
				double qte_tour = 0;
				for (ExemplaireContratCadre ex : contratsDeCeType) {
					qte_tour += this.getQuantiteALivrerAuStep(ex, step + resu);
				}
				if (qte_tour == 0) {
					return 1000;			// On a assez pour subvenir à toutes le echeances
				}
				else {
					pate_echeancier += qte_tour;
				}
			}
			double equivalent_feves = (pate_echeancier - super.getStockPateValeur(pate))/super.getCoeffTFEP();
			Feve feve = super.creerFeve(pate);
			while (equivalent_feves < super.getStockFevesValeur(feve)) {
				resu++;
				double qte_tour = 0;
				for (ExemplaireContratCadre ex : contratsDeCeType) {
					qte_tour += this.getQuantiteALivrerAuStep(ex, step + resu);
				}
				if (qte_tour == 0) {
					return 1000;			// On a assez pour subvenir à toutes le echeances
				}
				else {
					equivalent_feves += qte_tour/super.getCoeffTFEP();
				}
			}
			return resu;
		}

}
