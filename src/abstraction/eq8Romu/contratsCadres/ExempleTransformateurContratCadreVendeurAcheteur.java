package abstraction.eq8Romu.contratsCadres;

import java.util.LinkedList;
import java.util.List;

import abstraction.fourni.Filiere;

public class ExempleTransformateurContratCadreVendeurAcheteur extends ExempleTransformateurContratCadreVendeur implements IAcheteurContratCadre{
	protected List<ExemplaireContratCadre> mesContratEnTantQuAcheteur;

	public ExempleTransformateurContratCadreVendeurAcheteur(Object produit) {
		super(produit);
		this.mesContratEnTantQuAcheteur=new LinkedList<ExemplaireContratCadre>();
	}

	public Echeancier contrePropositionDeLAcheteur(ExemplaireContratCadre contrat) {
		if (Math.random()<0.1) {
			return contrat.getEcheancier(); // on ne cherche pas a negocier sur le previsionnel de livraison
		} else {//dans 90% des cas on fait une contreproposition pour l'echeancier
			Echeancier e = contrat.getEcheancier();
			e.set(e.getStepDebut(), e.getQuantite(e.getStepDebut())*2.5);// on souhaite livrer 2.5 fois plus lors de la 1ere livraison... un choix arbitraire, juste pour l'exemple...
			return e;
		}
	}

	public double contrePropositionPrixAcheteur(ExemplaireContratCadre contrat) {
		if (Math.random()<0.1) {
			return contrat.getPrixALaTonne(); // on ne cherche pas a negocier dans 10% des cas
		} else {//dans 90% des cas on fait une contreproposition differente
			return contrat.getPrixALaTonne()*0.95;// 5% de moins.
		}
	}
	public void next() {
		// On enleve les contrats obsolete (nous pourrions vouloir les conserver pour "archive"...)
		List<ExemplaireContratCadre> contratsObsoletes=new LinkedList<ExemplaireContratCadre>();
		for (ExemplaireContratCadre contrat : this.mesContratEnTantQuAcheteur) {
			if (contrat.getQuantiteRestantALivrer()==0.0 && contrat.getMontantRestantARegler()==0.0) {
				contratsObsoletes.add(contrat);
			}
		}
		this.mesContratEnTantQuAcheteur.removeAll(contratsObsoletes);
		
		// Proposition d'un nouveau contrat a tous les vendeurs possibles
		/*
		for (IActeur acteur : Filiere.LA_FILIERE.getActeurs()) {
			if (acteur!=this && acteur instanceof IVendeurContratCadre && ((IVendeurContratCadre)acteur).vend(produit)) {
				Filiere.LA_FILIERE.getSuperviseurContratCadre().demande((IAcheteurContratCadre)this, ((IVendeurContratCadre)acteur), produit, new Echeancier(Filiere.LA_FILIERE.getEtape()+1, 10, 5.0), cryptogramme);
			}
		}*/
		// OU proposition d'un contrat a un des vendeurs choisi aleatoirement
		List<IVendeurContratCadre> vendeurs = Filiere.LA_FILIERE.getSuperviseurContratCadre().getVendeurs(produit);
		if (vendeurs.contains(this)) {
			vendeurs.remove(this);
		}
		IVendeurContratCadre vendeur = null;
		if (vendeurs.size()==1) {
			vendeur=vendeurs.get(0);
		} else if (vendeurs.size()>1) {
			vendeur = vendeurs.get((int)( Math.random()*vendeurs.size()));
		}
		if (vendeur!=null) {
			Filiere.LA_FILIERE.getSuperviseurContratCadre().demande((IAcheteurContratCadre)this, vendeur, produit, new Echeancier(Filiere.LA_FILIERE.getEtape()+1, 10, 5.0), cryptogramme);
		}
	}

	public void receptionner(Object produit, double quantite, ExemplaireContratCadre contrat) {
		stock.ajouter(this,  quantite);
	}


}
