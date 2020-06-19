package abstraction.eq6Distributeur1;

import java.util.LinkedList;
import java.util.List;

import abstraction.eq8Romu.contratsCadres.Echeancier;
import abstraction.eq8Romu.contratsCadres.ExemplaireContratCadre;
import abstraction.eq8Romu.contratsCadres.IAcheteurContratCadre;
import abstraction.eq8Romu.contratsCadres.IVendeurContratCadre;
import abstraction.eq8Romu.produits.ChocolatDeMarque;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;



public class AchatContratCadreEQ6 extends AchatBourseEQ6 implements IAcheteurContratCadre{

	protected ChocolatDeMarque choco;
	protected List<ExemplaireContratCadre> mesContratEnTantQuAcheteur;

	public AchatContratCadreEQ6(double capaciteStockmax,ChocolatDeMarque choco) {
		super(capaciteStockmax);
		this.choco= choco;

		this.mesContratEnTantQuAcheteur=new LinkedList<ExemplaireContratCadre>();

	}


	@Override
	public Echeancier contrePropositionDeLAcheteur(ExemplaireContratCadre contrat) {
		if (Math.random()<0.1) {
			return contrat.getEcheancier(); // on ne cherche pas a negocier sur le previsionnel de livraison
		} else {//dans 90% des cas on fait une contreproposition pour l'echeancier
			Echeancier e = contrat.getEcheancier();
			e.set(e.getStepDebut(), e.getQuantite(e.getStepDebut())*2.5);// on souhaite livrer 2.5 fois plus lors de la 1ere livraison... un choix arbitraire, juste pour l'exemple...
			return e;
		}
	}

	@Override
	public double contrePropositionPrixAcheteur(ExemplaireContratCadre contrat) {
		if (Math.random()<0.1) {
			return contrat.getPrixALaTonne(); // on ne cherche pas a negocier dans 10% des cas
		} else {//dans 90% des cas on fait une contreproposition differente
			return contrat.getPrixALaTonne()*1.025;// 5% de moins., Il fau tmettre une limite pour pas être plus cher que la bourse
		}
	}

	@Override
	public void receptionner(Object produit, double quantite, ExemplaireContratCadre contrat) {
		if ( produit instanceof ChocolatDeMarque) {
			this.stocker((ChocolatDeMarque)produit,  quantite);
		}
		else {
			journalEq6.ajouter("récepetion non prise en compte, pb contrat cadre");
		}
	}
	
	public void nextContrat() { //à mettre dans le next principal
		// On enleve les contrats obsolete (nous pourrions vouloir les conserver pour "archive"...)
		List<ExemplaireContratCadre> contratsObsoletes=new LinkedList<ExemplaireContratCadre>();
		for (ExemplaireContratCadre contrat : this.mesContratEnTantQuAcheteur) {
			
			if (contrat.getQuantiteRestantALivrer()==0.0 && contrat.getMontantRestantARegler()==0.0) {
				contratsObsoletes.add(contrat);
			}
		}
		this.mesContratEnTantQuAcheteur.removeAll(contratsObsoletes);
		
		// Proposition d'un nouveau contrat a tous les vendeurs possibles
		
		for (IActeur acteur : Filiere.LA_FILIERE.getActeurs()) {
			if (acteur!=this && acteur instanceof IVendeurContratCadre && ((IVendeurContratCadre)acteur).vend(this.choco)) {
				
				Filiere.LA_FILIERE.getSuperviseurContratCadre().demande((IAcheteurContratCadre)this, ((IVendeurContratCadre)acteur), this.choco, new Echeancier(Filiere.LA_FILIERE.getEtape()+1, 10, 5.0), cryptogramme);
			}
		}
		
		
	}
   

}
