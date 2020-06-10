package abstraction.eq2Producteur2;

import java.util.ArrayList;

import abstraction.eq8Romu.contratsCadres.Echeancier;
import abstraction.eq8Romu.contratsCadres.ExemplaireContratCadre;
import abstraction.eq8Romu.contratsCadres.IVendeurContratCadre;
import abstraction.eq8Romu.produits.Feve;
import abstraction.fourni.Filiere;
import abstraction.fourni.Journal;

public class VenteContratCadre extends eq2Vendeur implements IVendeurContratCadre {
	
	private ArrayList<ExemplaireContratCadre> contratsencours;
	private Journal journal_contrats;
	
	public VenteContratCadre() {
		super();
		this.contratsencours = new ArrayList<ExemplaireContratCadre>();
		this.journal_contrats = new Journal("Journal Contrats", this);
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
		

			double massedispofora = this.getStockFeve().get(Feve.FEVE_BASSE).getValeur();
			double massedispotrini = this.getStockFeve().get(Feve.FEVE_MOYENNE).getValeur();
			double massedispotrinie = this.getStockFeve().get(Feve.FEVE_MOYENNE_EQUITABLE).getValeur();
			double massedispocrio = this.getStockFeve().get(Feve.FEVE_HAUTE).getValeur();
			double massedispocrioe = this.getStockFeve().get(Feve.FEVE_HAUTE_EQUITABLE).getValeur();
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
			if (prod == Feve.FEVE_BASSE && massedispofora > 0.5) {
				return true;
			}
			else if (prod == Feve.FEVE_MOYENNE && massedispotrini > 0.5) {
				return true;
			}
			else if (prod == Feve.FEVE_MOYENNE_EQUITABLE && massedispotrinie > 0.5) {
				return true;
			}
			else if (prod == Feve.FEVE_HAUTE && massedispocrio > 0.5) {
				return true;
			}
			else if (prod == Feve.FEVE_HAUTE_EQUITABLE && massedispocrioe > 0.5) {
				return true;
			}
			else {
				return false;
			}
		}
		
		return false;
	}

	public Echeancier contrePropositionDuVendeur(ExemplaireContratCadre contrat) {
		this.journal_contrats.ajouter("Echeancier accepté");
		return contrat.getEcheancier();
	}

	@Override //pas en-dessous de notre prix min, si au-dessus on prend
	public double propositionPrix(ExemplaireContratCadre contrat) {
		if (contrat.getProduit().equals(Feve.FEVE_BASSE)) {
			this.journal_contrats.ajouter("Proposition d'un prix de "+this.getPrixTF().getValeur() + 100);
			return this.getPrixTF().getValeur() + 100;
		}
		else if (contrat.getProduit().equals(Feve.FEVE_MOYENNE)) {
			this.journal_contrats.ajouter("Proposition d'un prix de "+this.getPrixTT().getValeur() + 100);
			return this.getPrixTT().getValeur() + 100;
		}
		else if (contrat.getProduit().equals(Feve.FEVE_MOYENNE_EQUITABLE)) {
			this.journal_contrats.ajouter("Proposition d'un prix de "+this.getPrixTTE().getValeur() + 100);
			return this.getPrixTTE().getValeur() + 100;
		}
		else if (contrat.getProduit().equals(Feve.FEVE_HAUTE)) {
			this.journal_contrats.ajouter("Proposition d'un prix de "+this.getPrixTC().getValeur() + 100);
			return this.getPrixTC().getValeur() + 100;
		}
		else if (contrat.getProduit().equals(Feve.FEVE_HAUTE_EQUITABLE)) {
			this.journal_contrats.ajouter("Proposition d'un prix de "+this.getPrixTCE().getValeur() + 100);
			return this.getPrixTCE().getValeur() + 100;
		}
		return 0;
	}

	@Override
	public double contrePropositionPrixVendeur(ExemplaireContratCadre contrat) { //pb : si jamais leur prix est trop bas, je leur dit d'aller se faire voir au lieu de négocier. Pour y remédier, mettre les conditions && et || dans un autre if en dessous, et rajouter un elif si leur prix est trop bas
		if (contrat.getProduit().equals(Feve.FEVE_BASSE) && (contrat.getPrixALaTonne() > this.getPrixTF().getValeur() || (Math.abs(this.getPrixTF().getValeur()-contrat.getPrixALaTonne())/100 <= 0.05))) {
			this.journal_contrats.ajouter("Acceptation du prix de "+ contrat.getPrixALaTonne());
			return contrat.getPrixALaTonne();
		}
		else if (contrat.getProduit().equals(Feve.FEVE_MOYENNE) && (contrat.getPrixALaTonne() > this.getPrixTT().getValeur() || (Math.abs(this.getPrixTT().getValeur()-contrat.getPrixALaTonne())/100 <= 0.05))) {
			this.journal_contrats.ajouter("Acceptation du prix de "+contrat.getPrixALaTonne());
			return contrat.getPrixALaTonne();
		}
		else if (contrat.getProduit().equals(Feve.FEVE_MOYENNE_EQUITABLE) && (contrat.getPrixALaTonne() > this.getPrixTTE().getValeur() || (Math.abs(this.getPrixTTE().getValeur()-contrat.getPrixALaTonne())/100 <= 0.05))) {
			this.journal_contrats.ajouter("Acceptation du prix de "+contrat.getPrixALaTonne());
			return contrat.getPrixALaTonne();
		}
		else if (contrat.getProduit().equals(Feve.FEVE_HAUTE) && (contrat.getPrixALaTonne() > this.getPrixTC().getValeur() || (Math.abs(this.getPrixTC().getValeur()-contrat.getPrixALaTonne())/100 <= 0.05))) {
			this.journal_contrats.ajouter("Acceptation du prix de "+contrat.getPrixALaTonne());
			return contrat.getPrixALaTonne();
		}
		else if (contrat.getProduit().equals(Feve.FEVE_HAUTE_EQUITABLE) && (contrat.getPrixALaTonne() > this.getPrixTCE().getValeur() || (Math.abs(this.getPrixTCE().getValeur()-contrat.getPrixALaTonne())/100 <= 0.05))) {
			this.journal_contrats.ajouter("Acceptation du prix de "+this.getPrixTCE().getValeur() + 100);
			return contrat.getPrixALaTonne();
		}
		return 0;
	}

	@Override
	public void notificationNouveauContratCadre(ExemplaireContratCadre contrat) {
		this.contratsencours.add(contrat);
		this.journal_contrats.ajouter("Le contrat n°"+contrat.getNumero()+" a été signé.");
	}

	@Override
	public double livrer(Object produit, double quantite, ExemplaireContratCadre contrat) {
		// TODO Auto-generated method stub
		return 0;
	}
	public ArrayList getContratsencours() {
		return this.contratsencours;
	}
}
