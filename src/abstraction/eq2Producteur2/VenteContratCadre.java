package abstraction.eq2Producteur2;

import java.util.ArrayList;

import abstraction.eq8Romu.contratsCadres.Echeancier;
import abstraction.eq8Romu.contratsCadres.ExemplaireContratCadre;
import abstraction.eq8Romu.contratsCadres.IVendeurContratCadre;
import abstraction.eq8Romu.produits.Feve;
import abstraction.fourni.Filiere;
import abstraction.fourni.Journal;

public class VenteContratCadre extends eq2Vendeur implements IVendeurContratCadre {
 //on va se faire des contrats cadres de 100 tonnes à chaque fois: seul pb est de trouver moyen de compter les contrats cadres déjà en cours par type de fève pour savoir quelle masse on a vraiment de dispo.
	
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
		double massedispofora = 0;
		double massedispotrini = 0;
		double massedispotrinie = 0;
		double massedispocrio = 0;
		double massedispocrioe = 0;
		
		
		return false;
	}

	@Override //avec les contrats de 100 tonnes fixes on peut accepter à peu près n'importe quel échéancier
	public Echeancier contrePropositionDuVendeur(ExemplaireContratCadre contrat) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override //pas en-dessous de notre prix min, si au-dessus on prend
	public double propositionPrix(ExemplaireContratCadre contrat) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double contrePropositionPrixVendeur(ExemplaireContratCadre contrat) {
		// TODO Auto-generated method stub
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
