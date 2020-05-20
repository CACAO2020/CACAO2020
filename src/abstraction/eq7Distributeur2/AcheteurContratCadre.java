package abstraction.eq7Distributeur2;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import abstraction.eq8Romu.contratsCadres.Echeancier;
import abstraction.eq8Romu.contratsCadres.ExemplaireContratCadre;
import abstraction.eq8Romu.contratsCadres.IAcheteurContratCadre;
import abstraction.eq8Romu.contratsCadres.IVendeurContratCadre;
import abstraction.eq8Romu.contratsCadres.SuperviseurVentesContratCadre;
import abstraction.eq8Romu.produits.ChocolatDeMarque;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;

public class AcheteurContratCadre extends AbsAcheteurContratCadre implements IAcheteurContratCadre, IActeur{

	public AcheteurContratCadre(Distributeur2 ac) {
		super(ac);
	}
	
	public void initialiser() {
	}

	public void next() {
		// On enleve les contrats obsolete (nous pourrions vouloir les conserver pour "archive"...)
		List<ExemplaireContratCadre> contratsObsoletes=new LinkedList<ExemplaireContratCadre>();
		for (ExemplaireContratCadre contrat : mesContrats) {
			if (contrat.getQuantiteRestantALivrer() == 0.0 && contrat.getMontantRestantARegler() == 0.0) {
				contratsObsoletes.add(contrat);
			}
		}
		this.mesContrats.removeAll(contratsObsoletes);
		
		// Proposition d'un nouveau contrat a tous les vendeurs possibles
		SuperviseurVentesContratCadre superviseur = Filiere.LA_FILIERE.getSuperviseurContratCadre();
		int etape = Filiere.LA_FILIERE.getEtape();		
		for (ChocolatDeMarque choco : ac.getVendeur().produitsCatalogue) {
			List<IVendeurContratCadre> vendeurs = superviseur.getVendeurs(choco);
			ExemplaireContratCadre contrat;
			for (IVendeurContratCadre vendeur : vendeurs) {
				contrat = superviseur.demande(ac, vendeur, choco, new Echeancier(etape+1, 10, 5.0), ac.cryptogramme);
				if (contrat != null) {
					mesContrats.add(contrat);
				}
			}
		}	
		System.out.println(mesContrats.size());
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

	public void receptionner(Object produit, double quantite, ExemplaireContratCadre contrat) {
		ac.getStock().ajouterStockChocolat((ChocolatDeMarque)produit, quantite);
	}

}
