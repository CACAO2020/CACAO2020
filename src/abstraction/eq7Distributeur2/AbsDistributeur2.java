package abstraction.eq7Distributeur2;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import abstraction.eq8Romu.chocolatBourse.IVendeurChocolatBourse;
import abstraction.eq8Romu.clients.IDistributeurChocolatDeMarque;
import abstraction.eq8Romu.contratsCadres.IVendeurContratCadre;
import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.ChocolatDeMarque;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;

public class AbsDistributeur2 {

	// Liste des types de chocolats achetés et vendus par le distributeur
	public List<Chocolat> nosChoco;
	
	// Couleurs d'arrière-plan pour les messages des journaux
	public Color titleColor = Color.BLACK;
	public Color alertColor = Color.RED;
	public Color descriptionColor = Color.YELLOW;
	public Color positiveColor = Color.GREEN;
	public Color warningColor = Color.ORANGE;
	public Color behaviorColor = Color.BLUE;
	
	//Les chocos que nous vendons
	public AbsDistributeur2() {
		nosChoco = new ArrayList<Chocolat>();
		nosChoco.add(Chocolat.CHOCOLAT_MOYENNE);
		nosChoco.add(Chocolat.CHOCOLAT_MOYENNE_EQUITABLE);
		nosChoco.add(Chocolat.CHOCOLAT_HAUTE);
		nosChoco.add(Chocolat.CHOCOLAT_HAUTE_EQUITABLE);
	}
	
	// Renvoie tous les chocolats de marque possibles parmi ceux commercialisés par le distributeur
	public List<ChocolatDeMarque> tousLesChocolatsDeMarquePossibles() {
		List<ChocolatDeMarque> resultat = new LinkedList<ChocolatDeMarque>();
		for (Chocolat choco : Chocolat.values()) {
			if (nosChoco.contains(choco)) {
				List<IVendeurChocolatBourse> vendeursChocolatBourse=vendeursChocolatBourse();
				for (IVendeurChocolatBourse vendeur : vendeursChocolatBourse) {
					resultat.add( new ChocolatDeMarque(choco, vendeur.getNom()) );
				}
				List<IDistributeurChocolatDeMarque> distributeursChocolatDeMarque=distributeursChocolatDeMarque();
				for (IDistributeurChocolatDeMarque distri : distributeursChocolatDeMarque) {
					resultat.add( new ChocolatDeMarque(choco, distri.getNom()) );
				}
			}
		}
		return resultat;
	}
	
	// Renvoie la liste des vendeurs de chocolat à la bourse
	public List<IVendeurChocolatBourse> vendeursChocolatBourse() {
		List<IVendeurChocolatBourse> res = new LinkedList<IVendeurChocolatBourse>();
		for (IActeur acteur : Filiere.LA_FILIERE.getActeurs()) {
			if (acteur instanceof IVendeurChocolatBourse) {
				res.add((IVendeurChocolatBourse)acteur);
			}
		}
		return res;
	}
	
	// Renvoie la liste des distributeurs de chocolat
	public List<IDistributeurChocolatDeMarque> distributeursChocolatDeMarque() {
		List<IDistributeurChocolatDeMarque> res = new LinkedList<IDistributeurChocolatDeMarque>();
		for (IActeur acteur : Filiere.LA_FILIERE.getActeurs()) {
			if (acteur instanceof IDistributeurChocolatDeMarque) {
				res.add((IDistributeurChocolatDeMarque)acteur);
			}
		}
		return res;
	}
}
