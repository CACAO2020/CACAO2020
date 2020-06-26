package abstraction.eq2Producteur2;

import java.util.ArrayList;
import java.util.List;

import abstraction.eq8Romu.contratsCadres.Echeancier;
import abstraction.eq8Romu.contratsCadres.ExemplaireContratCadre;
import abstraction.eq8Romu.contratsCadres.IVendeurContratCadre;
import abstraction.eq8Romu.produits.Feve;
import abstraction.eq8Romu.produits.Pate;
import abstraction.fourni.Filiere;
import abstraction.fourni.Journal;

public class VenteContratCadre extends eq2Vendeur implements IVendeurContratCadre {
	
	private ArrayList<ExemplaireContratCadre> contratsencours;
	private Journal journal_contrats;
	private double PrixMoyen; //prix a la tonne
	private double prixventecontrat;
	private double massedispofora; 
	private double massedispotrini;
	private double massedispotrinie;
	private double massedispocrio;
	private double massedispocrioe;
	private double patedispofora;
	private double patedispotrini;
	
	public VenteContratCadre() {
		super();
		this.contratsencours = new ArrayList<ExemplaireContratCadre>();
		this.journal_contrats = new Journal("Journal Contrats", this);
		this.massedispofora = 0;
		this.massedispotrini = 0;
		this.massedispotrinie = 0;
		this.massedispocrio = 0;
		this.massedispocrioe = 0;
		this.patedispofora = 0;
		this.patedispotrini = 0;
		this.PrixMoyen=0;
	}
	
	public void RefreshContrats() {
		int i = 0;
		while ( i < this.getContratsencours().size()) {
			if (this.getContratsencours().size()>0) {
				ExemplaireContratCadre contrat = (ExemplaireContratCadre)this.getContratsencours().get(i);
				if (Filiere.LA_FILIERE.getEtape() > contrat.getEcheancier().getStepFin()) {
					this.PrixMoyen = (this.PrixMoyen +this.contratsencours.get(i).getPrixALaTonne())/2;
					this.contratsencours.remove(i);
					--i;
					this.journal_contrats.ajouter("Le contrat n°"+contrat.getNumero()+" est arrivé à terme. Merci pour la moula");
				}
			}
			i++;
		}
	}
	@Override
	public boolean vend(Object produit) {
		if (produit instanceof Feve)  {
			Feve prod = (Feve)produit; 
		

			this.massedispofora = this.getmassedispofora();
			this.massedispotrini = this.getmassedispotrini();
			this.massedispotrinie = this.getmassedispotrinie();
			this.massedispocrio = this.getmassedispocrio();
			this.massedispocrioe = this.getmassedispocrioe();
			//this.journal_contrats.ajouter(""+massedispofora+massedispotrini+massedispotrinie+massedispocrio+massedispocrioe);
			if (prod == Feve.FEVE_BASSE && massedispofora > 0.5) {
				this.journal_contrats.ajouter("On est disposé à passer un contrat pour vendre des fèves forastero");
				return true;
			}
			else if (prod == Feve.FEVE_MOYENNE && massedispotrini > 0.5) {
				this.journal_contrats.ajouter("On est disposé à passer un contrat pour vendre des fèves trinitario");
				return true;
			}
			else if (prod == Feve.FEVE_MOYENNE_EQUITABLE && massedispotrinie > 0.5) {
				this.journal_contrats.ajouter("On est disposé à passer un contrat pour vendre des fèves trinitario équitables");
				return true;
			}
			else if (prod == Feve.FEVE_HAUTE && massedispocrio > 0.5) {
				this.journal_contrats.ajouter("On est disposé à passer un contrat pour vendre des fèves criollo");
				return true;
			}
			else if (prod == Feve.FEVE_HAUTE_EQUITABLE && massedispocrioe > 0.5) {
				this.journal_contrats.ajouter("On est disposé à passer un contrat pour vendre des fèves criollo équitables");
				return true;
			}
			else {
				return false;
			}
		}
		else if (produit instanceof Pate) {
			Pate pat = (Pate)produit;
			this.patedispofora = this.getpatedispofora();
			this.patedispotrini = this.getpatedispotrini();
			if (pat == Pate.PATE_BASSE && this.patedispofora > 0) {
				this.journal_contrats.ajouter("On est disposé à passer un contrat pour vendre de la pâte basse");
				return true;
			}
			else if (pat == Pate.PATE_MOYENNE && this.patedispotrini > 0) {
				this.journal_contrats.ajouter("On est disposé à passer un contrat pour vendre de la pâte moyenne");
				return true;
			}
		}
		
		return false;
	}

	public Echeancier contrePropositionDuVendeur(ExemplaireContratCadre contrat) {
		if (contrat.getProduit().equals(Feve.FEVE_BASSE) || contrat.getProduit().equals(Feve.FEVE_MOYENNE) || contrat.getProduit().equals(Feve.FEVE_MOYENNE_EQUITABLE) || contrat.getProduit().equals(Feve.FEVE_HAUTE) || contrat.getProduit().equals(Feve.FEVE_HAUTE_EQUITABLE))  {
			Feve prod = (Feve)contrat.getProduit(); 
			if (prod == Feve.FEVE_BASSE && this.massedispofora >= contrat.getQuantiteTotale()) {
				this.journal_contrats.ajouter("Echéancier accepté");
				return contrat.getEcheancier();
			}
			if (prod == Feve.FEVE_MOYENNE && this.massedispotrini >= contrat.getQuantiteTotale()) {
				this.journal_contrats.ajouter("Echéancier accepté");
				return contrat.getEcheancier();
			}
			if (prod == Feve.FEVE_MOYENNE_EQUITABLE && this.massedispotrinie >= contrat.getQuantiteTotale()) {
				this.journal_contrats.ajouter("Echéancier accepté");
				return contrat.getEcheancier();
			}
			if (prod == Feve.FEVE_HAUTE && this.massedispocrio >= contrat.getQuantiteTotale()) {
				this.journal_contrats.ajouter("Echéancier accepté");
				return contrat.getEcheancier();
			}
			if (prod == Feve.FEVE_HAUTE_EQUITABLE && this.massedispocrioe >= contrat.getQuantiteTotale()) {
				this.journal_contrats.ajouter("Echéancier accepté");
				return contrat.getEcheancier();
			}
		}
		else if (contrat.getProduit().equals(Pate.PATE_BASSE) || contrat.getProduit().equals(Pate.PATE_MOYENNE)) {
			Pate pat = (Pate)contrat.getProduit();
			if (pat == Pate.PATE_BASSE && this.patedispofora >= contrat.getQuantiteTotale()) {
				this.journal_contrats.ajouter("Echéancier accepté");
				return contrat.getEcheancier();
			}
			if (pat == Pate.PATE_MOYENNE && this.patedispotrini >= contrat.getQuantiteTotale()) {
				this.journal_contrats.ajouter("Echéancier accepté");
				return contrat.getEcheancier();
			}
		}
		this.journal_contrats.ajouter("Echéancier refusé, ils demandent "+contrat.getQuantiteTotale()+" et on a "+this.getmassedispocrio()+" "+this.getmassedispocrioe()+" "+this.getmassedispofora()+" "+this.getmassedispotrini()+" "+this.getmassedispotrinie());
		return null;
	}

	@Override //pas en-dessous de notre prix min, si au-dessus on prend
	public double propositionPrix(ExemplaireContratCadre contrat) {
		if (contrat.getProduit().equals(Feve.FEVE_BASSE)) {
			this.setPrixVenteContrat(this.getPrixTF().getValeur()*1.07);
			this.journal_contrats.ajouter("Proposition d'un prix de "+this.getPrixVenteContrat()+"$");
			return this.getPrixVenteContrat();
		}
		else if (contrat.getProduit().equals(Feve.FEVE_MOYENNE)) {
			this.setPrixVenteContrat(this.getPrixTT().getValeur()*1.07);
			this.journal_contrats.ajouter("Proposition d'un prix de "+this.getPrixVenteContrat()+"$");
			return this.getPrixVenteContrat();
		}
		else if (contrat.getProduit().equals(Feve.FEVE_MOYENNE_EQUITABLE)) {
			this.setPrixVenteContrat(this.getPrixTTE().getValeur()*1.07);
			this.journal_contrats.ajouter("Proposition d'un prix de "+this.getPrixVenteContrat()+"$");
			return this.getPrixVenteContrat();
		}
		else if (contrat.getProduit().equals(Feve.FEVE_HAUTE)) {
			this.setPrixVenteContrat(this.getPrixTC().getValeur()*1.07);
			this.journal_contrats.ajouter("Proposition d'un prix de "+this.getPrixVenteContrat()+"$");
			return this.getPrixVenteContrat();
		}
		else if (contrat.getProduit().equals(Feve.FEVE_HAUTE_EQUITABLE)) {
			this.setPrixVenteContrat(this.getPrixTCE().getValeur()*1.07);
			this.journal_contrats.ajouter("Proposition d'un prix de "+this.getPrixVenteContrat()+"$");
			return this.getPrixVenteContrat();
		}
		else if (contrat.getProduit().equals(Pate.PATE_BASSE)) {
			this.setPrixVenteContrat(this.getPrixTPF().getValeur()*1.07);
			this.journal_contrats.ajouter("Proposition d'un prix de "+this.getPrixVenteContrat()+"$");
			return this.getPrixVenteContrat();
		}
		else if (contrat.getProduit().equals(Pate.PATE_MOYENNE)) {
			this.setPrixVenteContrat(this.getPrixTPT().getValeur()*1.07);
			this.journal_contrats.ajouter("Proposition d'un prix de "+this.getPrixVenteContrat()+"$");
			return this.getPrixVenteContrat();
		}
		return 0;
	}

	@Override
	public double contrePropositionPrixVendeur(ExemplaireContratCadre contrat) { //pb : si jamais leur prix est trop bas, je leur dit d'aller se faire voir au lieu de négocier. Pour y remédier, mettre les conditions && et || dans un autre if en dessous, et rajouter un elif si leur prix est trop bas
		if (contrat.getProduit().equals(Feve.FEVE_BASSE)) {
			if((contrat.getPrixALaTonne() > this.getPrixTF().getValeur() || (Math.abs(this.getPrixTF().getValeur()-contrat.getPrixALaTonne())/100 <= 0.05))) {
				this.setPrixVenteContrat(contrat.getPrixALaTonne());
				this.journal_contrats.ajouter("Acceptation du prix de "+ this.getPrixVenteContrat()+"$");
				return this.getPrixVenteContrat();
			}
			else {
				this.setPrixVenteContrat(this.getPrixTF().getValeur()*1.05);
				this.journal_contrats.ajouter("On négocie ardemment les forasteros pour "+this.getPrixVenteContrat()+"$");
				return this.getPrixVenteContrat();
			}
		}
		else if (contrat.getProduit().equals(Feve.FEVE_MOYENNE))  {
			if (contrat.getPrixALaTonne() > this.getPrixTT().getValeur() || (Math.abs(this.getPrixTT().getValeur()-contrat.getPrixALaTonne())/100 <= 0.05)) {
				this.setPrixVenteContrat(contrat.getPrixALaTonne());
				this.journal_contrats.ajouter("Acceptation du prix de "+this.getPrixVenteContrat()+"$");
				return this.getPrixVenteContrat();
			}
			else {
				this.setPrixVenteContrat(this.getPrixTT().getValeur()*1.05);
				this.journal_contrats.ajouter("On négocie ardemment les trinitarios pour "+this.getPrixVenteContrat()+"$");
				return this.getPrixVenteContrat();
			}
		}
		else if (contrat.getProduit().equals(Feve.FEVE_MOYENNE_EQUITABLE))  {
			if (contrat.getPrixALaTonne() > this.getPrixTTE().getValeur() || (Math.abs(this.getPrixTTE().getValeur()-contrat.getPrixALaTonne())/100 <= 0.05)) {
				this.setPrixVenteContrat(contrat.getPrixALaTonne());
				this.journal_contrats.ajouter("Acceptation du prix de "+this.getPrixVenteContrat()+"$");
				return this.getPrixVenteContrat();
			}
			else {
				this.setPrixVenteContrat(this.getPrixTTE().getValeur()*1.05);
				this.journal_contrats.ajouter("On négocie ardemment les trinitarios équitables pour "+this.getPrixVenteContrat()+"$");
				return this.getPrixVenteContrat();
			}
		}
		else if (contrat.getProduit().equals(Feve.FEVE_HAUTE))  {
			if (contrat.getPrixALaTonne() > this.getPrixTC().getValeur() || (Math.abs(this.getPrixTC().getValeur()-contrat.getPrixALaTonne())/100 <= 0.05)) {
				this.setPrixVenteContrat(contrat.getPrixALaTonne());
				this.journal_contrats.ajouter("Acceptation du prix de "+this.getPrixVenteContrat()+"$");
				return this.getPrixVenteContrat();
			}
			else {
				this.setPrixVenteContrat(this.getPrixTC().getValeur()*1.05);
				this.journal_contrats.ajouter("On négocie ardemment les criollos pour "+this.getPrixVenteContrat()+"$");
				return this.getPrixVenteContrat();
			}
		}
		else if (contrat.getProduit().equals(Feve.FEVE_HAUTE_EQUITABLE))  {
			if (contrat.getPrixALaTonne() > this.getPrixTCE().getValeur() || (Math.abs(this.getPrixTCE().getValeur()-contrat.getPrixALaTonne())/100 <= 0.05)) {
				this.setPrixVenteContrat(contrat.getPrixALaTonne());
				this.journal_contrats.ajouter("Acceptation du prix de "+this.getPrixVenteContrat()+"$");
				return this.getPrixVenteContrat();
			}
			else {
				this.setPrixVenteContrat(this.getPrixTCE().getValeur()*1.05);
				this.journal_contrats.ajouter("On négocie ardemment les criollos équitables pour "+this.getPrixVenteContrat()+"$");
				return this.getPrixVenteContrat();
			}
		}
		else if (contrat.getProduit().equals(Pate.PATE_BASSE))  {
			if (contrat.getPrixALaTonne() > this.getPrixTPF().getValeur() || (Math.abs(this.getPrixTPF().getValeur()-contrat.getPrixALaTonne())/100 <= 0.05)) {
				this.setPrixVenteContrat(contrat.getPrixALaTonne());
				this.journal_contrats.ajouter("Acceptation du prix de "+this.getPrixVenteContrat()+"$");
				return this.getPrixVenteContrat();
			}
			else {
				this.setPrixVenteContrat(this.getPrixTPF().getValeur()*1.05);
				this.journal_contrats.ajouter("On négocie ardemment la pâte basse pour "+this.getPrixVenteContrat()+"$");
				return this.getPrixVenteContrat();
			}
		}
		else if (contrat.getProduit().equals(Pate.PATE_MOYENNE))  {
			if (contrat.getPrixALaTonne() > this.getPrixTPT().getValeur() || (Math.abs(this.getPrixTPT().getValeur()-contrat.getPrixALaTonne())/100 <= 0.05)) {
				this.setPrixVenteContrat(contrat.getPrixALaTonne());
				this.journal_contrats.ajouter("Acceptation du prix de "+this.getPrixVenteContrat()+"$");
				return this.getPrixVenteContrat();
			}
			else {
				this.setPrixVenteContrat(this.getPrixTPT().getValeur()*1.05);
				this.journal_contrats.ajouter("On négocie ardemment la pâte moyenne pour "+this.getPrixVenteContrat()+"$");
				return this.getPrixVenteContrat();
			}
		}
		return 0;
	}

	@Override
	public void notificationNouveauContratCadre(ExemplaireContratCadre contrat) {
		this.contratsencours.add(contrat);
		this.journal_contrats.ajouter("Le contrat n°"+contrat.getNumero()+" a été signé. On devra livrer "+contrat.getQuantiteTotale()+" tonnes de "+contrat.getProduit()+" en "+contrat.getEcheancier().getNbEcheances()+" étapes.");
		if (contrat.getProduit().equals(Feve.FEVE_BASSE)||contrat.getProduit().equals(Pate.PATE_BASSE)) {
			this.incrementercompteurfora();
		}
		else if (contrat.getProduit().equals(Feve.FEVE_MOYENNE)||contrat.getProduit().equals(Pate.PATE_MOYENNE)) {
			this.incrementercompteurtrini();
		}
		else if (contrat.getProduit().equals(Feve.FEVE_MOYENNE_EQUITABLE)) {
			this.incrementercompteurtrinie();
		}
		else if (contrat.getProduit().equals(Feve.FEVE_HAUTE)) {
			this.incrementercompteurcrio();
		}
		else if (contrat.getProduit().equals(Feve.FEVE_HAUTE_EQUITABLE)) {
			this.incrementercompteurcrioe();
		}
	}

	@Override
	public double livrer(Object produit, double quantite, ExemplaireContratCadre contrat) {
		if (produit.equals(Feve.FEVE_BASSE) || produit.equals(Feve.FEVE_MOYENNE) || produit.equals(Feve.FEVE_MOYENNE_EQUITABLE) || produit.equals(Feve.FEVE_HAUTE) || produit.equals(Feve.FEVE_HAUTE_EQUITABLE))  {
			Feve prod = (Feve)produit; 
			if (prod == Feve.FEVE_BASSE) {
				if (this.getStockFeve().get(Feve.FEVE_BASSE).getValeur() >= quantite) {
					this.removeQtFeve(Feve.FEVE_BASSE, quantite);
					this.journal_contrats.ajouter("Nous avons livré toute la commande du contrat n°"+contrat.getNumero()+", reste "+(contrat.getQuantiteRestantALivrer()-quantite)+"tonnes à livrer.");
					return quantite;
				}
				else {
					this.removeQtFeve(Feve.FEVE_BASSE, this.getStockFeve().get(Feve.FEVE_BASSE).getValeur());
					this.journal_contrats.ajouter("On est cours sur la livraison pour le contrat n°"+contrat.getNumero()+", on va être pénalisés");
					return this.getStockFeve().get(Feve.FEVE_BASSE).getValeur();
				}
			}
			else if (prod == Feve.FEVE_MOYENNE) {
				if (this.getStockFeve().get(Feve.FEVE_MOYENNE).getValeur() >= quantite) {
					this.removeQtFeve(Feve.FEVE_MOYENNE, quantite);
					this.journal_contrats.ajouter("Nous avons livré toute la commande du contrat n°"+contrat.getNumero()+", reste "+(contrat.getQuantiteRestantALivrer()-quantite)+"tonnes à livrer.");
					return quantite;
				}
				else {
					this.removeQtFeve(Feve.FEVE_MOYENNE, this.getStockFeve().get(Feve.FEVE_MOYENNE).getValeur());
					this.journal_contrats.ajouter("On est cours sur la livraison pour le contrat n°"+contrat.getNumero()+", on va être pénalisés");
					return this.getStockFeve().get(Feve.FEVE_MOYENNE).getValeur();
				}
			}
			else if (prod == Feve.FEVE_MOYENNE_EQUITABLE) {
				if (this.getStockFeve().get(Feve.FEVE_MOYENNE_EQUITABLE).getValeur() >= quantite) {
					this.removeQtFeve(Feve.FEVE_MOYENNE_EQUITABLE, quantite);
					this.journal_contrats.ajouter("Nous avons livré toute la commande du contrat n°"+contrat.getNumero()+", reste "+(contrat.getQuantiteRestantALivrer()-quantite)+"tonnes à livrer.");
					return quantite;
				}
				else {
					this.removeQtFeve(Feve.FEVE_MOYENNE_EQUITABLE, this.getStockFeve().get(Feve.FEVE_MOYENNE_EQUITABLE).getValeur());
					this.journal_contrats.ajouter("On est cours sur la livraison pour le contrat n°"+contrat.getNumero()+", on va être pénalisés");
					return this.getStockFeve().get(Feve.FEVE_MOYENNE_EQUITABLE).getValeur();
				}
			}
			else if (prod == Feve.FEVE_HAUTE) {
				if (this.getStockFeve().get(Feve.FEVE_HAUTE).getValeur() >= quantite) {
					this.removeQtFeve(Feve.FEVE_HAUTE, quantite);
					this.journal_contrats.ajouter("Nous avons livré toute la commande du contrat n°"+contrat.getNumero()+", reste "+(contrat.getQuantiteRestantALivrer()-quantite)+"tonnes à livrer.");
					return quantite;
				}
				else {
					this.removeQtFeve(Feve.FEVE_HAUTE, this.getStockFeve().get(Feve.FEVE_HAUTE).getValeur());
					this.journal_contrats.ajouter("On est cours sur la livraison pour le contrat n°"+contrat.getNumero()+", on va être pénalisés");
					return this.getStockFeve().get(Feve.FEVE_HAUTE).getValeur();
				}
			}
			else if (prod == Feve.FEVE_HAUTE_EQUITABLE) {
				if (this.getStockFeve().get(Feve.FEVE_HAUTE_EQUITABLE).getValeur() >= quantite) {
					this.removeQtFeve(Feve.FEVE_HAUTE_EQUITABLE, quantite);
					this.journal_contrats.ajouter("Nous avons livré toute la commande du contrat n°"+contrat.getNumero()+", reste "+(contrat.getQuantiteRestantALivrer()-quantite)+"tonnes à livrer.");
					return quantite;
				}
				else {
					this.removeQtFeve(Feve.FEVE_HAUTE_EQUITABLE, this.getStockFeve().get(Feve.FEVE_HAUTE_EQUITABLE).getValeur());
					this.journal_contrats.ajouter("On est cours sur la livraison pour le contrat n°"+contrat.getNumero()+", on va être pénalisés");
					return this.getStockFeve().get(Feve.FEVE_HAUTE_EQUITABLE).getValeur();
				}
			}
		}
		else if (contrat.getProduit().equals(Pate.PATE_BASSE) || contrat.getProduit().equals(Pate.PATE_MOYENNE)) {
			Pate pat = (Pate)contrat.getProduit();
			if (pat == Pate.PATE_BASSE) {
				if (this.getStockPate().get(Pate.PATE_BASSE).getValeur() >= quantite) {
					this.removeQtPate(Pate.PATE_BASSE, quantite);
					this.journal_contrats.ajouter("Nous avons livré toute la commande du contrat n°"+contrat.getNumero()+", reste "+(contrat.getQuantiteRestantALivrer()-quantite)+"tonnes à livrer.");
					return quantite;
				}
				else {
					this.removeQtPate(Pate.PATE_BASSE, this.getStockPate().get(Pate.PATE_BASSE).getValeur());
					this.journal_contrats.ajouter("On est cours sur la livraison pour le contrat n°"+contrat.getNumero()+", on va être pénalisés");
					return this.getStockPate().get(Pate.PATE_BASSE).getValeur();
				}
			}
			else if (pat == Pate.PATE_MOYENNE) {
				if (this.getStockPate().get(Pate.PATE_MOYENNE).getValeur() >= quantite) {
					this.removeQtPate(Pate.PATE_MOYENNE, quantite);
					this.journal_contrats.ajouter("Nous avons livré toute la commande du contrat n°"+contrat.getNumero()+", reste "+(contrat.getQuantiteRestantALivrer()-quantite)+"tonnes à livrer.");
					return quantite;
				}
				else {
					this.removeQtPate(Pate.PATE_MOYENNE, this.getStockPate().get(Pate.PATE_MOYENNE).getValeur());
					this.journal_contrats.ajouter("On est cours sur la livraison pour le contrat n°"+contrat.getNumero()+", on va être pénalisés");
					return this.getStockPate().get(Pate.PATE_MOYENNE).getValeur();
				}
			}
		}
		return 0;
	}
	public ArrayList getContratsencours() {
		return this.contratsencours;
	}
	public List<Journal> getJournaux() {
		List<Journal> res=new ArrayList<Journal>();
		res.addAll(super.getJournaux());
		res.add(this.journal_contrats);
		return res;
	}
	public void setPrixVenteContrat(double prix) {
		this.prixventecontrat = prix;
	}
	public double getPrixVenteContrat() {
		return this.prixventecontrat;
	}
	public double getmassedispofora() {
		double masse = this.getStockFeve().get(Feve.FEVE_BASSE).getValeur();
		for (int i = 0; i < this.getContratsencours().size(); i++) {
			ExemplaireContratCadre contrat = (ExemplaireContratCadre)this.getContratsencours().get(i);
			if (contrat.getProduit() instanceof Feve) {
				if ((Feve)contrat.getProduit() == Feve.FEVE_BASSE) {
					masse = masse - contrat.getQuantiteRestantALivrer();
				}
			}
		}
		return masse;
	}
	public double getmassedispotrini() {
		double masse = this.getStockFeve().get(Feve.FEVE_MOYENNE).getValeur();
		for (int i = 0; i < this.getContratsencours().size(); i++) {
			ExemplaireContratCadre contrat = (ExemplaireContratCadre)this.getContratsencours().get(i);
			if (contrat.getProduit() instanceof Feve) {
				if ((Feve)contrat.getProduit() == Feve.FEVE_MOYENNE) {
					masse = masse - contrat.getQuantiteRestantALivrer();
				}
			}
		}
		return masse;
	}
	public double getmassedispotrinie() {
		double masse = this.getStockFeve().get(Feve.FEVE_MOYENNE_EQUITABLE).getValeur();
		for (int i = 0; i < this.getContratsencours().size(); i++) {
			ExemplaireContratCadre contrat = (ExemplaireContratCadre)this.getContratsencours().get(i);
			if (contrat.getProduit() instanceof Feve) {
				if ((Feve)contrat.getProduit() == Feve.FEVE_MOYENNE_EQUITABLE) {
					masse = masse - contrat.getQuantiteRestantALivrer();
				}
			}
		}
		return masse;
	}
	public double getmassedispocrio() {
		double masse = this.getStockFeve().get(Feve.FEVE_HAUTE).getValeur();
		for (int i = 0; i < this.getContratsencours().size(); i++) {
			ExemplaireContratCadre contrat = (ExemplaireContratCadre)this.getContratsencours().get(i);
			if (contrat.getProduit() instanceof Feve) {
				if ((Feve)contrat.getProduit() == Feve.FEVE_HAUTE) {
					masse = masse - contrat.getQuantiteRestantALivrer();
				}
			}
		}
		return masse;
	}
	public double getmassedispocrioe() {
		double masse = this.getStockFeve().get(Feve.FEVE_HAUTE_EQUITABLE).getValeur();
		for (int i = 0; i < this.getContratsencours().size(); i++) {
			ExemplaireContratCadre contrat = (ExemplaireContratCadre)this.getContratsencours().get(i);
			if (contrat.getProduit() instanceof Feve) {
				if ((Feve)contrat.getProduit() == Feve.FEVE_HAUTE_EQUITABLE) {
					masse = masse - contrat.getQuantiteRestantALivrer();
				}
			}
		}
		return masse;
	}
	public double getpatedispofora() {
		double masse = this.getStockPate().get(Pate.PATE_BASSE).getValeur();
		for (int i = 0; i < this.getContratsencours().size(); i++) {
			ExemplaireContratCadre contrat = (ExemplaireContratCadre)this.getContratsencours().get(i);
			if (contrat.getProduit() instanceof Pate) {
				if ((Pate)contrat.getProduit() == Pate.PATE_BASSE) {
					masse = masse - contrat.getQuantiteRestantALivrer();
				}
			}
		}
		return masse;
	}
	public double getpatedispotrini() {
		double masse = this.getStockPate().get(Pate.PATE_MOYENNE).getValeur();
		for (int i = 0; i < this.getContratsencours().size(); i++) {
			ExemplaireContratCadre contrat = (ExemplaireContratCadre)this.getContratsencours().get(i);
			if (contrat.getProduit() instanceof Pate) {
				if ((Pate)contrat.getProduit() == Pate.PATE_MOYENNE) {
					masse = masse - contrat.getQuantiteRestantALivrer();
				}
			}
		}
		return masse;
	}
	/**
	 * @return the prixMoyen
	 */
	public double getPrixMoyen() {
		return PrixMoyen;
	}

	/**
	 * @param prixMoyen the prixMoyen to set
	 */
	public void setPrixMoyen(double prixMoyen) {
		PrixMoyen = prixMoyen;
	}
}
