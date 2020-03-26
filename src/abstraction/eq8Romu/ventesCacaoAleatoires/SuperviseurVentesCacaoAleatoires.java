package abstraction.eq8Romu.ventesCacaoAleatoires;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;
import abstraction.fourni.Filiere;
/**
 *  Le superviseur ne realise pas une transaction a chaque step : 
 *    il y a un delai de 0 a� 2 steps (tire au sort) entre deux echanges
 *  Lorsqu'un echange doit avoir lieu, le superviseur 
 *  - tire au sort un prix entre 1.8 et 2.1
 *  - tire au sort un vendeur parmi les vendeurs 
 *  - tire au sort un acheteur parmi les acheteurs
 *  - il demande au vendeur la quantite qu'il souhaite mettre en vente compte tenu 
 *     du prix via la fonction quantiteEnVente(prix) de IVendeurCacaoAleatoire
 *  - il demande ensuite a l'acheteur la quantite qu'il souhaite acheter connaissant 
 *     le prix de vente et la quantite en vente via la fonction quantiteeDesiree(enVente, prix)
 *     de IAcheteurCacaoAleatoire
 *  - il notifie le vendeur de la quantite qui a ete achetee via la methode 
 *     notificationVente(desiree, prix) de IVenduerCacaoAleatoire
 *  - il tire au sort dans combien de step aura lieu le prochain echange.
 *
 */
public class SuperviseurVentesCacaoAleatoires implements IActeur {
	private static int NB_INSTANCES = 0;

	private List<IVendeurCacaoAleatoire> vendeurs;
	private List<IAcheteurCacaoAleatoire> acheteurs;
	private int nbNextAvantEchange;
	private Journal journal;
	@SuppressWarnings("unused")
	private Integer cryptogramme;
	private Variable prixMin, prixMax;

	public SuperviseurVentesCacaoAleatoires() {
		NB_INSTANCES++;
		if (NB_INSTANCES>1) {
			throw new Error("Tentative de creer une seconde instance du superviseur de ventes de cacao aleatoire");
		}
		this.vendeurs = new ArrayList<IVendeurCacaoAleatoire>();
		this.acheteurs = new ArrayList<IAcheteurCacaoAleatoire>();
		this.journal = new Journal("Ventes aleatoires de cacao", this);
		this.nbNextAvantEchange = 0;
		this.prixMin = new Variable(this.getNom()+" prix min", this, 0.5, 3.5, 1.8);
		this.prixMax = new Variable(this.getNom()+" prix max", this, 0.5, 3.5, 2.1);
		
	}

	public String getNom() {
		return "SVCA";
	}

	public String getDescription() {
		return "Superviseur des ventes de cacao aleatoires";
	}
	
	public Color getColor() {
		return new Color(255, 170, 255);
	}

	public void initialiser() {
		for (IActeur ac : Filiere.LA_FILIERE.getActeurs()) {
			if (ac instanceof IVendeurCacaoAleatoire) {
				vendeurs.add((IVendeurCacaoAleatoire)ac);
			}
			if (ac instanceof IAcheteurCacaoAleatoire) {
				acheteurs.add((IAcheteurCacaoAleatoire)ac);
			}
		}
	}
	
	public void setCryptogramme(Integer crypto) {
		this.cryptogramme = crypto;
	}

	public void next() {
		if (this.vendeurs.size()>0 && this.acheteurs.size()>0) {
			if (this.nbNextAvantEchange==0) {
				this.journal.ajouter(Journal.texteColore(Color.LIGHT_GRAY, Color.BLACK,"-------------------- Echange a l'etape "+Filiere.LA_FILIERE.getEtape()+" --------------------"));
				double prix = prixMin.getValeur() + (Math.random()*(prixMax.getValeur()-prixMin.getValeur()));
				this.journal.ajouter("  prix="+prix);
				IVendeurCacaoAleatoire vendeur = this.vendeurs.get((int)(Math.random()*this.vendeurs.size()));
				this.journal.ajouter(Journal.texteColore(((IActeur)vendeur), "  vendeur="+((IActeur)vendeur).getNom()));
				IAcheteurCacaoAleatoire acheteur = this.acheteurs.get((int)(Math.random()*this.acheteurs.size()));
				this.journal.ajouter(Journal.texteColore(((IActeur)acheteur), "  acheteur="+((IActeur)acheteur).getNom()));
				double enVente = vendeur.quantiteEnVente(prix);
				this.journal.ajouter("  quantite en vente="+enVente);
				if (enVente>0.0) {
					double desiree=acheteur.quantiteDesiree(enVente, prix);
					this.journal.ajouter("   quantitee desiree="+desiree);
					if (desiree>0.0) {
						Integer crypto = acheteur.getCryptogramme(this);
						boolean virementEffectue = Filiere.LA_FILIERE.getBanque().virer((IActeur)acheteur, crypto, (IActeur)vendeur, desiree*prix);
						if (virementEffectue) {
							this.journal.ajouter(Journal.texteColore(Color.green, Color.black, "-------------------- Notification de vente --------------------"));
							acheteur.quantiteLivree(desiree);
							vendeur.notificationVente(desiree,  prix);
						} else {
							this.journal.ajouter(Journal.texteColore(Color.RED, Color.WHITE,"- Le virement n'a pas pu avoir lieu : annulation de la vente -"));
						}
					}
				} 
				this.nbNextAvantEchange=1+(int)(Math.random()*3);
			}
			this.nbNextAvantEchange--;
		}
	}

	public List<String> getNomsFilieresProposees() {
		return new ArrayList<String>();
	}

	public Filiere getFiliere(String nom) {
		return null;
	}
	
	public List<Variable> getIndicateurs() {
		List<Variable> res=new ArrayList<Variable>();
		return res;
	}

	public List<Variable> getParametres() {
		List<Variable> res=new ArrayList<Variable>();
		res.add(prixMin);
		res.add(prixMax);
		return res;
	}

	public List<Journal> getJournaux() {
		List<Journal> res=new ArrayList<Journal>();
		res.add(this.journal);
		return res;
	}
	
	@SuppressWarnings("unlikely-arg-type")
	public void notificationFaillite(IActeur acteur) {
		this.acheteurs.remove(acteur);
		this.vendeurs.remove(acteur);
	}
	
	public void notificationOperationBancaire(double montant) {
	}

}
