package abstraction.eq6Distributeur1;

import java.util.LinkedList;
import java.util.List;

import abstraction.eq8Romu.clients.ClientFinal;
import abstraction.eq8Romu.contratsCadres.Echeancier;
import abstraction.eq8Romu.contratsCadres.ExemplaireContratCadre;
import abstraction.eq8Romu.contratsCadres.IAcheteurContratCadre;
import abstraction.eq8Romu.contratsCadres.IVendeurContratCadre;
import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.ChocolatDeMarque;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;



public class AchatContratCadreEQ6 extends DistributeurClientFinal implements IAcheteurContratCadre{

	protected ChocolatDeMarque choco;
	 
	
	
	public AchatContratCadreEQ6(double capaciteDeVente, double marge, double capaciteStockmax, double pctageHGE,
			double pctageMG, double pctageBG) {
		super(capaciteDeVente, marge, capaciteStockmax, pctageHGE, pctageMG, pctageBG);
		// TODO Auto-generated constructor stub
	}

	
	


	@Override
	public Echeancier contrePropositionDeLAcheteur(ExemplaireContratCadre contrat) {
		/*
		if (Math.random()<0.5) {
			return contrat.getEcheancier(); // on ne cherche pas a negocier sur le previsionnel de livraison
		} else {
			Echeancier e = contrat.getEcheancier();
			Echeancier nouveau = new Echeancier(e.getStepDebut(),e.getStepFin()-e.getStepDebut(),e.getQuantite(e.getStepDebut())*1.05); // on leur commande un plus gros stock

		
			return nouveau;
		}
		*/
		return null;

		
		
	}

	@Override
	public double contrePropositionPrixAcheteur(ExemplaireContratCadre contrat) {
		if (Math.random()<0.5) {
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
		double moyennePrixBourse = 0;
		
		
		for (ChocolatDeMarque chocolat : ClientFinal.tousLesChocolatsDeMarquePossibles() ) {
				
		this.choco = chocolat;
		boolean OnFaitUnCOntratCadre = true;
		for (ExemplaireContratCadre contrat : this.mesContratEnTantQuAcheteur) { 		//si il n'y a pas de contrat cadre en cours

			
				if (contrat.getProduit().equals(choco)) {
					OnFaitUnCOntratCadre = false;
			}
		}
		
		
		
		//pour la durer du contrat, faire en fonction de la quantité écoulé
		int duréeContrat = 10;
		
		for (IActeur acteur : Filiere.LA_FILIERE.getActeurs()) {
			if (acteur!=this && acteur instanceof IVendeurContratCadre && ((IVendeurContratCadre)acteur).vend(this.choco)) { //vérifier qu'il vend bien du chocolat
				double valeurMin = 10E10;
				for(int i=0 ; i<duréeContrat; i++ ) {
					double newValeur = this.VenteSiPasRuptureDeStock.get(Filiere.LA_FILIERE.getEtape()+1+i-24).get(this.choco);
					if(newValeur<valeurMin) {
						valeurMin=newValeur;
						
						
					}
					moyennePrixBourse = this.evolutionCours.get(Filiere.LA_FILIERE.getEtape()+1+i-24).get(this.choco.getChocolat());
				}//comment on fixe le prix??, comment on crée notre marque distributrice
				if (this.choco.getChocolat() == Chocolat.CHOCOLAT_BASSE) //
				moyennePrixBourse = moyennePrixBourse /duréeContrat;
				moyennePrixBourse = moyennePrixBourse*0.9; //car on veut un rabais
				Filiere.LA_FILIERE.getSuperviseurContratCadre().demande((IAcheteurContratCadre)this, ((IVendeurContratCadre)acteur), this.choco, new Echeancier(Filiere.LA_FILIERE.getEtape()+1, duréeContrat, valeurMin), cryptogramme);
			}
		}
		}
		
		
	}
		
   

}
