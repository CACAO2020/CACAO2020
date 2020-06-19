package abstraction.eq3Transformateur1;
import java.util.List;

import abstraction.eq8Romu.contratsCadres.IVendeurContratCadre;
import abstraction.eq8Romu.produits.Feve;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;
public abstract class AchatFeveCC extends Transformation {
	public void descisionCCFeve(){
		List <IVendeurContratCadre> Vendeurs= Filiere.LA_FILIERE.getSuperviseurContratCadre().getVendeurs(Feve.FEVE_HAUTE_EQUITABLE);
		Vendeurs.addAll(Filiere.LA_FILIERE.getSuperviseurContratCadre().getVendeurs(Feve.FEVE_HAUTE));
		
	}
	
}
