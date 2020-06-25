package abstraction.eq3Transformateur1;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import abstraction.eq8Romu.contratsCadres.Echeancier;
import abstraction.eq8Romu.contratsCadres.ExemplaireContratCadre;
import abstraction.eq8Romu.contratsCadres.IAcheteurContratCadre;
import abstraction.eq8Romu.contratsCadres.IVendeurContratCadre;
import abstraction.eq8Romu.produits.Feve;
import abstraction.fourni.Filiere;
public abstract class AchatFeveCC extends Transformation implements IAcheteurContratCadre {
	protected int finCC;
	public AchatFeveCC() {
		finCC=0;
		}
	@Override
	public Echeancier contrePropositionDeLAcheteur(ExemplaireContratCadre contrat) {
		
		if(contrat.getEcheanciers().get(0).getNbEcheances()==contrat.getEcheancier().getNbEcheances()) {
			return contrat.getEcheancier();
			
		}
		return null;
	}
	@Override
	public double contrePropositionPrixAcheteur(ExemplaireContratCadre contrat) {
		if(contrat.getPrixALaTonne()+5000<=(Filiere.LA_FILIERE.getIndicateur("BourseChoco cours CHOCOLAT_HAUTE_EQUITABLE").getValeur())*0.85) {
			this.finCC=contrat.getEcheancier().getNbEcheances();
			this.journalCC.ajouter("conculsion du CC de "+contrat.getQuantiteTotale()+"T"+" pour un prix à la tonne de"+contrat.getPrixALaTonne() );
			return contrat.getPrixALaTonne();
			
		}
		return 0;
	}
	@Override
	public void receptionner(Object produit, double quantite, ExemplaireContratCadre contrat) {
		this.setCoutFeves((Feve) produit, this.calculCoutFeve((Feve) produit, quantite,contrat.getPrixALaTonne() ));
		this.setStockFeves((Feve) produit,quantite);
		this.journalCC.ajouter("reception de"+quantite);
	}
	public void descisionCCFeve(){
		List <IVendeurContratCadre> Vendeurs= Filiere.LA_FILIERE.getSuperviseurContratCadre().getVendeurs(Feve.FEVE_HAUTE_EQUITABLE);
		/*Vendeurs.addAll(Filiere.LA_FILIERE.getSuperviseurContratCadre().getVendeurs(Feve.FEVE_HAUTE));*/
		if((Filiere.LA_FILIERE.getEtape()>26) &&(finCC==0)&& (Vendeurs.size()>0)) {
			Double cAAnneeMoy=0.0;	
			for(int i =Filiere.LA_FILIERE.getEtape()-24;i<Filiere.LA_FILIERE.getEtape()+1;i++) {
				cAAnneeMoy=cAAnneeMoy+cATour.get(i)*1/12;
			}
			Double feveAcheteAnneeMoye=0.0;	
			for(int i =Filiere.LA_FILIERE.getEtape()-24;i<Filiere.LA_FILIERE.getEtape()+1;i++) {
				feveAcheteAnneeMoye=feveAcheteAnneeMoye+quantiteFeveAcheteTour.get(i)*1/12;
			}
			Double quantiteFeveCC=feveAcheteAnneeMoye*0.15;
			if((quantiteFeveCC*(Filiere.LA_FILIERE.getIndicateur("Sup.C.Criee prix vente FEVE_HAUTE_EQUITABLE").getValeur()+5000)<cAAnneeMoy*0.20)&&(quantiteFeveCC>1)) {
				Echeancier EchancierCC=new Echeancier(Filiere.LA_FILIERE.getEtape()+1, 12, quantiteFeveCC);
				 Filiere.LA_FILIERE.getSuperviseurContratCadre().demande(this, Vendeurs.get(0), Feve.FEVE_HAUTE_EQUITABLE, EchancierCC, this.cryptogramme);
			}
			


		}
			}
	
	public Echeancier descisionEcheancier(Feve feve ) {
		return new Echeancier();
	}
	
	
}
