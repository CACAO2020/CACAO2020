package abstraction.eq8Romu.chocolatBourse;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;
import abstraction.eq8Romu.cacaoCriee.SuperviseurCacaoCriee;
import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.Feve;
import abstraction.fourni.Banque;
import abstraction.fourni.Filiere;
/** @author R. DEBRUYNE 

 * Bourse du chocolat

 */
public class SuperviseurChocolatBourse implements IActeur {
	private static int NB_INSTANCES = 0; // Afin de verifier qu'il y a au plus UN superviseur des ventes de chocolat a la bourse
	public static final double QUANTITE_MIN = 0.1; //Pas d'offre/demande de moins de 100 Kg
	private static Variable TAUX_INTERET; // Si un acheteur a effectue une commande qu'il n'a pas su payer, 
	// un interet de TAUX_INTERET.getValeur() est ajoute a chaque step.
	private static Variable DELTA_MAX; // A l'issue d'une session le cours peut evoluer d'au plus DELTA_MAX pourcents
	private Journal journal;
	private Map<Chocolat,Variable> cours;
	private Map<IAcheteurChocolatBourse, CommandeChocolat> commandesNonPayee;// Un acheteur qui commande
	// du chocolat sans pouvoir payer sa commande devra payer (avec les interets) cette commande
	// avant de pouvoir effectuer d'autres achats en bourse
	private Integer crypto;

	public SuperviseurChocolatBourse() {
		NB_INSTANCES++;
		if (NB_INSTANCES>1) {
			throw new Error("Tentative de creer une seconde instance du superviseur de la bourse du chocolat");
		}
		TAUX_INTERET=new Variable(this.getNom()+" taux interet", this, 0.0, 0.15, 0.05);
		DELTA_MAX=new Variable(this.getNom()+" delta max", this, 0.001, 0.05, 0.01);
		this.journal = new Journal("Ventes de chocolat a la bourse", this);
		this.cours = new HashMap<Chocolat, Variable>();
		for (Chocolat choco : Chocolat.values()) {
			this.cours.put(choco, new Variable(this.getNom()+" cours "+choco.name(), this, 3000.0, 40000.0, 10000.0));
		}
		this.commandesNonPayee = new HashMap<IAcheteurChocolatBourse, CommandeChocolat>();
	}
	public static double getTauxInteret() {
		return TAUX_INTERET.getValeur();
	}

	public String getNom() {
		return "BourseChoco";
	}

	public String getDescription() {
		return "Superviseur des ventes a la bourse du chocolat";
	}

	public Color getColor() {
		return new Color(96, 125, 139);
	}

	public void initialiser() {
	}

	public void setCryptogramme(Integer crypto) {
		this.crypto = crypto;
	}
	public void next() {
		reclamerCreances();
		uneSession();
		uneSession();
	}
	public void reclamerCreances() {
		List<IAcheteurChocolatBourse> acheteursDetteReglee = new ArrayList<IAcheteurChocolatBourse>();
		journal.ajouter(this.commandesNonPayee.keySet().size()+" commandes non payees ");
		if (!this.commandesNonPayee.keySet().isEmpty()) {
			for (IAcheteurChocolatBourse acheteur : this.commandesNonPayee.keySet()) {
				CommandeChocolat commande = commandesNonPayee.get(acheteur);
				commande.ajouterInterets();
				Integer crypto = acheteur.getCryptogramme(this);
				this.journal.ajouter(Journal.texteColore(acheteur, acheteur.getNom()+" doit payer sa commande de "+commande.getQuantite()+" tonnes de "+commande.getChocolat().name()+" pour un montant de "+commande.getMontant()));
				boolean virementEffectue = Filiere.LA_FILIERE.getBanque().virer(acheteur, crypto, this, commande.getMontant());
				if (virementEffectue) {
					acheteur.receptionner(commande.getChocolat(), commande.getQuantite());
					acheteursDetteReglee.add(acheteur);
					journal.ajouter(Journal.texteColore(Color.GREEN,  Color.BLACK, " --> paiement effectue"));
				} else {
					journal.ajouter(Journal.texteColore(Color.RED,  Color.BLACK, " --> paiement impossible"));
				}
			}
		}
		for (IAcheteurChocolatBourse acheteur : acheteursDetteReglee) {
			this.commandesNonPayee.remove(acheteur);
		}
	}
	public void uneSession() {
		// On recree les listes de vendeurs et acheteurs a chaque next car :
		// (1) dans la simulation la creation de nouveaux acteurs en cours
		// de simulation est autorisee et
		// (2) certains acteurs ont pu faire faillite
		//		List<PropositionCriee> transactions = new ArrayList<PropositionCriee>(); // les propositions qui ont abouti a une transaction durant l'etape courante
		List<IVendeurChocolatBourse> vendeurs;
		List<IAcheteurChocolatBourse> acheteurs;
		Banque laBanque = Filiere.LA_FILIERE.getBanque();
		vendeurs = new ArrayList<IVendeurChocolatBourse>();
		acheteurs = new ArrayList<IAcheteurChocolatBourse>();
		for (IActeur ac : Filiere.LA_FILIERE.getActeurs()) {
			if (ac instanceof IVendeurChocolatBourse && !laBanque.aFaitFaillite(ac) ) {
				vendeurs.add((IVendeurChocolatBourse)ac);
			}
			if (ac instanceof IAcheteurChocolatBourse && !laBanque.aFaitFaillite(ac) && !commandesNonPayee.keySet().contains(ac)) {
				acheteurs.add((IAcheteurChocolatBourse)ac);
			}
		}
		HashMap<IVendeurChocolatBourse, Double> offres = new HashMap<IVendeurChocolatBourse, Double>();
		HashMap<IAcheteurChocolatBourse, Double> demandes = new HashMap<IAcheteurChocolatBourse, Double>();
		for (Chocolat choco : Chocolat.values()) {
			journal.ajouter(" Echanges de "+choco.name()+" au prix de "+cours.get(choco).getValeur());

			offres.clear();
			demandes.clear();
			double totalOffres=0.0;
			double totalDemandes=0.0;
			for (IVendeurChocolatBourse v : vendeurs) {
				double offre = v.getOffre(choco, cours.get(choco).getValeur());
				journal.ajouter(Journal.texteColore(v, v.getNom()+" offre "+Journal.doubleSur(offre,4)+" tonnes"));
				if (offre>=QUANTITE_MIN) {
					offres.put(v, offre);
					totalOffres+=offre;
				} else {
					journal.ajouter("offre trop faible");
				}
			}
			if (totalOffres>0.0) {
				for (IAcheteurChocolatBourse a : acheteurs) {
					double demande = a.getDemande(choco, cours.get(choco).getValeur());
					journal.ajouter(Journal.texteColore(a, a.getNom()+" demande "+Journal.doubleSur(demande,4)+" tonnes"));
					if (demande>=QUANTITE_MIN) {
						demandes.put(a, demande);
						totalDemandes+=demande;
					} else {
						journal.ajouter("demande trop faible");
					}
				} 
				if (totalDemandes>0.0) {
					double ratioDemandes = Math.min(1.0, totalOffres/totalDemandes);// Au plus le demandeur aura la quantite demande, mais si la demande est plus forte que l'offre il obtiendra une quantite proportionnelle a sa demande
					for (IAcheteurChocolatBourse acheteur : demandes.keySet()) {
						Integer crypto = acheteur.getCryptogramme(this);
						double quantiteAchetee = demandes.get(acheteur)*ratioDemandes;
						double montant = quantiteAchetee*cours.get(choco).getValeur();
						boolean virementEffectue = Filiere.LA_FILIERE.getBanque().virer(acheteur, crypto, this, montant);
						acheteur.notifierCommande(choco, quantiteAchetee, virementEffectue);
						if (virementEffectue) {
							journal.ajouter(Journal.texteColore(acheteur,"l'acheteur "+acheteur.getNom()+" obtient "+Journal.doubleSur(quantiteAchetee, 2)));
							acheteur.receptionner(choco, quantiteAchetee);
						} else {
							journal.ajouter(Journal.texteColore(acheteur,"l'acheteur "+acheteur.getNom()+" obtient "+Journal.doubleSur(quantiteAchetee, 2)+" mais n'a pas les moyens de payer"));
							commandesNonPayee.put(acheteur, new CommandeChocolat(choco, quantiteAchetee, montant));
						}
					}
					double ratioOffres = Math.min(1.0, totalDemandes/totalOffres);// Vendra au plus la quantite correspondant a son offre, mais si la demande est faible vendra une quantite proportionnelle a son offre
					for (IVendeurChocolatBourse vendeur : offres.keySet()) {
						double quantiteVendue = offres.get(vendeur)*ratioOffres;
						journal.ajouter(Journal.texteColore(vendeur,"le vendeur "+vendeur.getNom()+" vend "+Journal.doubleSur(quantiteVendue, 2)));
						double montant = quantiteVendue*cours.get(choco).getValeur();
						Filiere.LA_FILIERE.getBanque().virer(this, this.crypto, vendeur, montant);
						vendeur.livrer(choco, quantiteVendue);
					}
					double nouveauCours = cours.get(choco).getValeur() *(1.0+ ( DELTA_MAX.getValeur()* (totalDemandes-totalOffres)/(totalDemandes+totalOffres)));
					cours.get(choco).setValeur(this, nouveauCours);
				} else { // de l'offre mais pas de demande
					double nouveauCours = cours.get(choco).getValeur() *(1.0- ( DELTA_MAX.getValeur()/10.0));
					cours.get(choco).setValeur(this, nouveauCours);
				}
			} else { // pas d'offres
				double nouveauCours = cours.get(choco).getValeur() *(1.0+ ( DELTA_MAX.getValeur()/10.0));
				cours.get(choco).setValeur(this, nouveauCours);
			}
			journal.ajouter("le cours passe a "+Journal.doubleSur(cours.get(choco).getValeur(),4));
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
		for (Chocolat choco : Chocolat.values()) {
			res.add(this.cours.get(choco));
		}
		return res;
	}

	public List<Variable> getParametres() {
		List<Variable> res=new ArrayList<Variable>();
		res.add(TAUX_INTERET);
		res.add(DELTA_MAX);
		return res;
	}

	public List<Journal> getJournaux() {
		List<Journal> res=new ArrayList<Journal>();
		res.add(this.journal);
		return res;
	}

	public void notificationFaillite(IActeur acteur) {
		if (commandesNonPayee.keySet().contains(acteur)) {
			journal.ajouter(Journal.texteColore(acteur, acteur.getNom()+" vient de faire faillite : on efface sa dette vis a vis de la bourse"));
			commandesNonPayee.remove(acteur);
		}
	}

	public void notificationOperationBancaire(double montant) {
	}
}
