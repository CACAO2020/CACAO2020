package abstraction.eq5Transformateur3;

import abstraction.eq8Romu.cacaoCriee.ExempleVendeurCacaoCriee;
import abstraction.eq8Romu.cacaoCriee.SuperviseurCacaoCriee;
import abstraction.eq8Romu.chocolatBourse.ExempleAcheteurChocolatBourse;
import abstraction.eq8Romu.chocolatBourse.SuperviseurChocolatBourse;
import abstraction.eq8Romu.produits.Feve;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;

/**
 * @author Nicolas PIERRE
 * Filière de teste uniquement pour le haut de gamme, labélisé ou pas.
 * Permet de tester le comportement de AchatFeve et VenteChocolat principalement
 */
public class FiliereTestAchatVente extends Filiere {
	
	public FiliereTestAchatVente() {
		super();
		this.ajouterActeur(new Transformateur3());
		this.ajouterActeur(new ExempleAcheteurChocolatBourse());
		this.ajouterActeur(new ExempleAcheteurChocolatBourse());
        this.ajouterActeur(new ExempleAcheteurChocolatBourse());
        this.ajouterActeur(new ExempleVendeurCacaoCriee(Feve.FEVE_HAUTE, 1,10,500));
		this.ajouterActeur(new ExempleVendeurCacaoCriee(Feve.FEVE_HAUTE_EQUITABLE, 1,10,2000));
        SuperviseurCacaoCriee superviseur = new SuperviseurCacaoCriee();
        SuperviseurChocolatBourse superviseur2 = new SuperviseurChocolatBourse();
        this.ajouterActeur(superviseur);
        this.ajouterActeur(superviseur2);
	}
	
	public IActeur getActeur(String nom) {
		if (!nom.equals("Sup.C.Criee")) {
			return super.getActeur(nom); 
		} else {
			return null;
		}
	}
}