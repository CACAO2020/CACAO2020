package abstraction.eq8Romu.cacaoCriee;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;
import abstraction.eq8Romu.produits.Feve;
import abstraction.fourni.Banque;
import abstraction.fourni.Filiere;
/** @author R. DEBRUYNE 

 * Variante de la vente a la criee (un exemple : https://www.maisondelamer.org/ressources/la-vente-en-criee/ )
 * dans laquelle ce n'est pas necessairement la meilleur offre qui l'emporte : c'est
 * le vendeur qui en connaissance de toutes les offres decide quelle offre l'emporte.
 *
 */
public class SuperviseurCacaoCriee implements IActeur {
	private static int NB_INSTANCES = 0; // Afin de verifier qu'il y a au plus UN superviseur des ventes de feves a la criee
	private Variable maxLotsInvendus; // Si au cours d'une meme etape un vendeur atteint maxLotsInvendus 
	// lots proposes non vendus il est retire des vendeurs pour cette etape (il devra attendre la prochaine
	// etape avant de pouvoir proposer d'autres lots).
	private Journal journal;
	private Map<Integer, List<PropositionCriee>> historique;
	private Map<Feve,Variable> prix;

	public SuperviseurCacaoCriee() {
		NB_INSTANCES++;
		if (NB_INSTANCES>1) {
			throw new Error("Tentative de creer une seconde instance du superviseur de ventes de cacao a la criee");
		}
		this.maxLotsInvendus = new Variable(this.getNom()+" max propositions sans vente", this, 5.0, 30.0, 15.0);
		this.journal = new Journal("Ventes de feves a la criee", this);
		this.historique = new HashMap<Integer, List<PropositionCriee>>();
		this.prix = new HashMap<Feve, Variable>();
		for (Feve f : Feve.values()) {
			this.prix.put(f, new Variable(this.getNom()+" prix vente "+f.name(), this, 0.0, 5.0, 0.0));
		}
	}

	public String getNom() {
		return "Sup.C.Criee";
	}

	public String getDescription() {
		return "Superviseur des ventes de feves de cacao a la criee";
	}

	public List<PropositionCriee> getHistorique(int etape) {
		if (!historique.keySet().contains(etape)) {
			return new ArrayList<PropositionCriee>();
		} else {
			return new ArrayList<PropositionCriee>(historique.get(etape));
		}
	}

	public Color getColor() {
		return new Color(96, 125, 139);
	}

	public void initialiser() {
	}

	public void setCryptogramme(Integer crypto) {
	}

	public void next() {
		// On recree les listes de vendeurs et acheteurs a chaque next car :
		// (1) dans la simulation la creation de nouveaux acteurs en cours
		// de simulation est autorisee et
		// (2) certains acteurs ont pu faire faillite
		List<PropositionCriee> transactions = new ArrayList<PropositionCriee>(); // les propositions qui ont abouti a une transaction durant l'etape courante
		List<IVendeurCacaoCriee> vendeurs;
		List<IAcheteurCacaoCriee> acheteurs;
		Banque laBanque = Filiere.LA_FILIERE.getBanque();
		vendeurs = new ArrayList<IVendeurCacaoCriee>();
		acheteurs = new ArrayList<IAcheteurCacaoCriee>();
		for (IActeur ac : Filiere.LA_FILIERE.getActeurs()) {
			if (ac instanceof IVendeurCacaoCriee && !laBanque.aFaitFaillite(ac)) {
				vendeurs.add((IVendeurCacaoCriee)ac);
			}
			if (ac instanceof IAcheteurCacaoCriee && !laBanque.aFaitFaillite(ac)) {
				acheteurs.add((IAcheteurCacaoCriee)ac);
			}
		}
		Collections.shuffle(vendeurs);
		Collections.shuffle(acheteurs);

		int vendeurCourant = 0;

		this.journal.ajouter(Journal.texteColore(Color.LIGHT_GRAY, Color.BLACK,"-------------------- Echange a l'etape "+Filiere.LA_FILIERE.getEtape()+" --------------------"));
		if (vendeurs.size()==0) {
			this.journal.ajouter(Journal.texteColore(Color.LIGHT_GRAY, Color.BLACK,"Aucun vendeur --> Aucune transaction"));
		} else if (acheteurs.size()==0) {
			this.journal.ajouter(Journal.texteColore(Color.LIGHT_GRAY, Color.BLACK,"Aucun acheteur --> Aucune transaction"));
		}
		String texteVendeurs="Vendeurs : ";
		for (IVendeurCacaoCriee v : vendeurs) {
			texteVendeurs+=Journal.texteColore(v,  v.getNom());
		}
		this.journal.ajouter(texteVendeurs);
		String texteAcheteurs="Acheteurs : ";
		for (IAcheteurCacaoCriee a : acheteurs) {
			texteAcheteurs+=Journal.texteColore(a,  a.getNom());
		}
		this.journal.ajouter(texteAcheteurs);

		Map<IVendeurCacaoCriee, Integer> lotsInvendus = new HashMap<IVendeurCacaoCriee, Integer>();
		if (vendeurs.size()>0) {
			for (IVendeurCacaoCriee v : vendeurs) {
				lotsInvendus.put(v,0);
			}
		}

		while (vendeurs.size()>0 
		    && acheteurs.size()>0 ) { // Il faut au moins un vendeur et un acheteur pour qu'il y ait des echanges

			LotCacaoCriee lot = vendeurs.get(vendeurCourant).getLotEnVente();
			if (lot==null) { // Le vendeur courant n'a plus de lot a mettre en vente a cette etape
				this.journal.ajouter(Journal.texteColore(vendeurs.get(vendeurCourant), vendeurs.get(vendeurCourant).getNom()+" ne met pas de lot en vente --> retrait de la liste des vendeurs"));
				vendeurs.remove(vendeurCourant);
				if (vendeurCourant>=vendeurs.size()) {
					vendeurCourant=0; // le vendeur courant etait le dernier de la liste --> on repasse au premier
				}
			} else {
				this.journal.ajouter(Journal.texteColore(vendeurs.get(vendeurCourant), vendeurs.get(vendeurCourant).getNom()+" met en vente le lot "+lot));
				List<PropositionCriee> propositions = new ArrayList<PropositionCriee>();
				for (IAcheteurCacaoCriee acheteur : acheteurs) {
					double prix = acheteur.proposerAchat(lot);
					if (prix>0.0) {
						this.journal.ajouter(Journal.texteColore(acheteur, acheteur.getNom()+" propose un prix a la tonne de "+Journal.doubleSur(prix, 4)));
						propositions.add(new PropositionCriee(lot, acheteur, prix));
					}
				}
				if (propositions.size()==0) {
					this.journal.ajouter(Journal.texteColore(Color.RED, Color.WHITE,"   Aucune proposition d'achat"));
					vendeurs.get(vendeurCourant).notifierAucuneProposition(lot);
					if (lotsInvendus.get(vendeurs.get(vendeurCourant))+1==maxLotsInvendus.getValeur()) {
						this.journal.ajouter(Journal.texteColore(Color.RED, Color.WHITE,"   Le vendeur a mis en vente "+maxLotsInvendus.getValeur()+" lots qui n'ont pas ete achetes --> le vendeur est retire de la liste des vendeurs"));
						vendeurs.remove(vendeurCourant);
						if (vendeurCourant>=vendeurs.size()) {
							vendeurCourant=0; // le vendeur courant etait le dernier de la liste --> on repasse au premier
						}
					} else {
						lotsInvendus.put(vendeurs.get(vendeurCourant), lotsInvendus.get(vendeurs.get(vendeurCourant))+1);
						vendeurCourant=(vendeurCourant+1)%vendeurs.size();
					}
				} else {
					PropositionCriee choisie = vendeurs.get(vendeurCourant).choisir(new ArrayList<PropositionCriee>(propositions));
					if (choisie==null) {
						this.journal.ajouter(Journal.texteColore(Color.RED, Color.WHITE,"   Le vendeur ne retient aucune proposition d'achat"));
						if (lotsInvendus.get(vendeurs.get(vendeurCourant))+1==maxLotsInvendus.getValeur()) {
							this.journal.ajouter(Journal.texteColore(Color.RED, Color.WHITE,"   Le vendeur a mis en vente "+maxLotsInvendus.getValeur()+" lots qui n'ont pas ete achetes --> le vendeur est retire de la liste des vendeurs"));
							vendeurs.remove(vendeurCourant);
							if (vendeurCourant>=vendeurs.size()) {
								vendeurCourant=0; // le vendeur courant etait le dernier de la liste --> on repasse au premier
							}
						} else {
							lotsInvendus.put(vendeurs.get(vendeurCourant), lotsInvendus.get(vendeurs.get(vendeurCourant))+1);
							vendeurCourant=(vendeurCourant+1)%vendeurs.size();
						}
						for (PropositionCriee p : propositions) {
							p.getAcheteur().notifierPropositionRefusee(p);
						}
					} else {
						if (!propositions.contains(choisie)) {
							throw new Error("Le vendeur de feves "+vendeurs.get(vendeurCourant).getNom()+" a une implementation de choisir(propositions) qui retourne une proposition ne figurant pas dans propositions");
						}
						this.journal.ajouter(Journal.texteColore(choisie.getAcheteur()," Le vendeur choisit la proposition de "+ choisie.getAcheteur().getNom()));
						// on notifie les autres acheteurs que leur proposition n'a pas ete retenue
						for (PropositionCriee p : propositions) {
							if (p!=choisie) {
								p.getAcheteur().notifierPropositionRefusee(p);
							}
						}
						// On procede si possible au paiement
						Integer crypto = choisie.getAcheteur().getCryptogramme(this);
						boolean virementEffectue = Filiere.LA_FILIERE.getBanque().virer(choisie.getAcheteur(), crypto, vendeurs.get(vendeurCourant), choisie.getPrixPourLeLot());
						if (virementEffectue) {
							this.journal.ajouter(Journal.texteColore(Color.green, Color.black, "-------------------- Notification de vente --------------------"));
							choisie.getVendeur().notifierVente(choisie);
							choisie.getAcheteur().notifierVente(choisie);
							transactions.add(choisie );
							this.prix.get(choisie.getFeve()).setValeur(choisie.getAcheteur(), choisie.getPrixPourUneTonne());
						} else {
							this.journal.ajouter(Journal.texteColore(Color.RED, Color.WHITE,"- Le virement n'a pas pu avoir lieu : annulation de la vente et retrait de l'acheteur \""+choisie.getAcheteur().getNom()+"\" de la liste des acheteurs"));
							acheteurs.remove(choisie.getAcheteur());
						}
						vendeurCourant=(vendeurCourant+1)%vendeurs.size();
					}
				}
			}
		}
		if (transactions.size()==0) {
			this.journal.ajouter(Journal.texteColore(Color.LIGHT_GRAY, Color.BLACK,"Aucune transaction effectuee a l'etape "+Filiere.LA_FILIERE.getEtape()+" --------------------"));
		} else {
			historique.put(Filiere.LA_FILIERE.getEtape(), transactions);
			this.journal.ajouter(Journal.texteColore(Color.LIGHT_GRAY, Color.BLACK,"Recapitulatif des transaction effectuees a l'etape "+Filiere.LA_FILIERE.getEtape()+" --------------------"));
			for (PropositionCriee p : transactions) {
				this.journal.ajouter(
						Journal.texteColore(Color.LIGHT_GRAY, Color.BLACK,Journal.doubleSur(p.getQuantiteEnTonnes(), 2)+"Tonnes de "+p.getLot().getFeve().name()
								+" au prix de "+Journal.doubleSur(p.getPrixPourUneTonne(), 4)+" de ")
						+Journal.texteColore(p.getLot().getVendeur(), p.getLot().getVendeur().getNom())
						+Journal.texteColore(Color.LIGHT_GRAY, Color.BLACK," a ")
						+Journal.texteColore(p.getAcheteur(), p.getAcheteur().getNom()));
			}
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
		for (Feve f : Feve.values()) {
			res.add(this.prix.get(f));
		}
		return res;
	}

	public List<Variable> getParametres() {
		List<Variable> res=new ArrayList<Variable>();
		res.add(this.maxLotsInvendus);
		return res;
	}

	public List<Journal> getJournaux() {
		List<Journal> res=new ArrayList<Journal>();
		res.add(this.journal);
		return res;
	}

	public void notificationFaillite(IActeur acteur) {
	}

	public void notificationOperationBancaire(double montant) {
	}
}
