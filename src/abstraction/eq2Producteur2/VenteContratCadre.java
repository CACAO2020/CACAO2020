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
	}
	
	public void RefreshContrats() {
		for (int i = 0; i < this.getContratsencours().size(); i++) {
			ExemplaireContratCadre contrat = (ExemplaireContratCadre)this.getContratsencours().get(i);
			if (Filiere.LA_FILIERE.getEtape() > contrat.getEcheancier().getStepFin()) {
				this.contratsencours.remove(i);
				this.journal_contrats.ajouter("Le contrat n°"+contrat.getNumero()+" est arrivé à terme. Merci pour la moula");
			}
		}
	}
	@Override
	public boolean vend(Object produit) {
		if (produit.equals(Feve.FEVE_BASSE) || produit.equals(Feve.FEVE_MOYENNE) || produit.equals(Feve.FEVE_MOYENNE_EQUITABLE) || produit.equals(Feve.FEVE_HAUTE) || produit.equals(Feve.FEVE_HAUTE_EQUITABLE))  {
			Feve prod = (Feve)produit; 
		

			this.massedispofora = this.getStockFeve().get(Feve.FEVE_BASSE).getValeur();
			this.massedispotrini = this.getStockFeve().get(Feve.FEVE_MOYENNE).getValeur();
			this.massedispotrinie = this.getStockFeve().get(Feve.FEVE_MOYENNE_EQUITABLE).getValeur();
			this.massedispocrio = this.getStockFeve().get(Feve.FEVE_HAUTE).getValeur();
			this.massedispocrioe = this.getStockFeve().get(Feve.FEVE_HAUTE_EQUITABLE).getValeur();
			for (int i = 0; i < this.getContratsencours().size(); i++) {
				ExemplaireContratCadre contrat = (ExemplaireContratCadre)this.getContratsencours().get(i);
				if ((Feve)contrat.getProduit() == Feve.FEVE_BASSE) {
					massedispofora = massedispofora - contrat.getQuantiteRestantALivrer();
				}
				else if ((Feve)contrat.getProduit() == Feve.FEVE_MOYENNE) {
					massedispotrini = massedispotrini - contrat.getQuantiteRestantALivrer();
				}
				else if ((Feve)contrat.getProduit() == Feve.FEVE_MOYENNE_EQUITABLE) {
					massedispotrinie = massedispotrinie - contrat.getQuantiteRestantALivrer();
				}
				else if ((Feve)contrat.getProduit() == Feve.FEVE_HAUTE) {
					massedispocrio = massedispocrio - contrat.getQuantiteRestantALivrer();
				}
				else if ((Feve)contrat.getProduit() == Feve.FEVE_HAUTE_EQUITABLE) {
					massedispocrioe = massedispocrioe - contrat.getQuantiteRestantALivrer();
				}
			}
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
		else if (produit.equals(Pate.PATE_BASSE) || produit.equals(Pate.PATE_MOYENNE)) {
			Pate pat = (Pate)produit;
			this.patedispofora = this.getpatedispofora();
			this.patedispotrini = this.getpatedispotrini();
			if (pat == Pate.PATE_BASSE && this.patedispofora > 0) {
				this.journal_contrats.ajouter("On est disposé à passer un contrat pour vendre de la pate basse");
				return true;
			}
			else if (pat == Pate.PATE_MOYENNE && this.patedispotrini > 0) {
				this.journal_contrats.ajouter("On est disposé à passer un contrat pour vendre de la pate moyenne");
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
		this.journal_contrats.ajouter("Echéancier refusé");
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
		return 0;
	}

	@Override
	public void notificationNouveauContratCadre(ExemplaireContratCadre contrat) {
		this.contratsencours.add(contrat);
		this.journal_contrats.ajouter("Le contrat n°"+contrat.getNumero()+" a été signé. On devra livrer "+contrat.getQuantiteTotale()+" tonnes de "+(Feve)contrat.getProduit()+" en "+contrat.getEcheancier().getNbEcheances()+" étapes.");
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
			if ((Feve)contrat.getProduit() == Feve.FEVE_BASSE) {
				masse = masse - contrat.getQuantiteRestantALivrer();
			}
		}
		return masse;
	}
	public double getmassedispotrini() {
		double masse = this.getStockFeve().get(Feve.FEVE_MOYENNE).getValeur();
		for (int i = 0; i < this.getContratsencours().size(); i++) {
			ExemplaireContratCadre contrat = (ExemplaireContratCadre)this.getContratsencours().get(i);
			if ((Feve)contrat.getProduit() == Feve.FEVE_MOYENNE) {
				masse = masse - contrat.getQuantiteRestantALivrer();
			}
		}
		return masse;
	}
	public double getmassedispotrinie() {
		double masse = this.getStockFeve().get(Feve.FEVE_MOYENNE_EQUITABLE).getValeur();
		for (int i = 0; i < this.getContratsencours().size(); i++) {
			ExemplaireContratCadre contrat = (ExemplaireContratCadre)this.getContratsencours().get(i);
			if ((Feve)contrat.getProduit() == Feve.FEVE_MOYENNE_EQUITABLE) {
				masse = masse - contrat.getQuantiteRestantALivrer();
			}
		}
		return masse;
	}
	public double getmassedispocrio() {
		double masse = this.getStockFeve().get(Feve.FEVE_HAUTE).getValeur();
		for (int i = 0; i < this.getContratsencours().size(); i++) {
			ExemplaireContratCadre contrat = (ExemplaireContratCadre)this.getContratsencours().get(i);
			if ((Feve)contrat.getProduit() == Feve.FEVE_HAUTE) {
				masse = masse - contrat.getQuantiteRestantALivrer();
			}
		}
		return masse;
	}
	public double getmassedispocrioe() {
		double masse = this.getStockFeve().get(Feve.FEVE_HAUTE_EQUITABLE).getValeur();
		for (int i = 0; i < this.getContratsencours().size(); i++) {
			ExemplaireContratCadre contrat = (ExemplaireContratCadre)this.getContratsencours().get(i);
			if ((Feve)contrat.getProduit() == Feve.FEVE_HAUTE_EQUITABLE) {
				masse = masse - contrat.getQuantiteRestantALivrer();
			}
		}
		return masse;
	}
	public double getpatedispofora() {
		double masse = this.getStockPate().get(Pate.PATE_BASSE).getValeur();
		for (int i = 0; i < this.getContratsencours().size(); i++) {
			ExemplaireContratCadre contrat = (ExemplaireContratCadre)this.getContratsencours().get(i);
			if ((Pate)contrat.getProduit() == Pate.PATE_BASSE) {
				masse = masse - contrat.getQuantiteRestantALivrer();
			}
		}
		return masse;
	}
	public double getpatedispotrini() {
		double masse = this.getStockPate().get(Pate.PATE_MOYENNE).getValeur();
		for (int i = 0; i < this.getContratsencours().size(); i++) {
			ExemplaireContratCadre contrat = (ExemplaireContratCadre)this.getContratsencours().get(i);
			if ((Pate)contrat.getProduit() == Pate.PATE_MOYENNE) {
				masse = masse - contrat.getQuantiteRestantALivrer();
			}
		}
		return masse;
	}
}
