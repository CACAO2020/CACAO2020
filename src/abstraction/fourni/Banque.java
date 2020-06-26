package abstraction.fourni;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import control.CtrlDecouvertAutorise;

public class Banque implements IActeur {
	public static final double SOLDE_MAX = 1000000000;
	public static final double SOLDE_INITIAL = 500000;
	private HashMap<IActeur, Variable> comptes; // Memorise le solde bancaire de chaque acteur
	private HashMap<IActeur, Integer> decouvertsConsecutifs; // Memorise le nombre d'etapes consecutives a decouvert. Revient a 0 des que le solde redevient positif.  
	private HashMap<IActeur, Integer> cryptogramme; // Nombre personnel a chaque acteur, a communiquer pour avoir la permission d'effectuer une operation sur le compte 
	private HashMap<IActeur, Boolean> faillites; // A true pour les acteurs ayant fait faillite

	private Journal journalBanque;
	private Journal journalFiliere;

	private Variable decouvertsConsecutifsAvantFaillite; // Un acteur fait faillite si il atteint un nombre de decouverts consecutifs egal a cette valeur 
	private Variable decouvertAutorise; 
	private Variable agiosDecouvertAutorise; // Agios applicables pour une solde debiteur de [decouvertAutorise, 0]
	private Variable agiosDecouvertAuDela; // Agios applicables pour un solde debiteur allant au dela du decouvert autorise
	private Variable seuilOperationsRefusees; // Aucune operation amenant le solde a un niveau inferieur n'est autorisee mis a part l'ajout d'agios 

	public Banque() {
		this.comptes = new HashMap<IActeur, Variable>();
		this.decouvertsConsecutifs=new HashMap<IActeur, Integer>();
		this.cryptogramme = new HashMap<IActeur, Integer>();
		this.decouvertsConsecutifsAvantFaillite = new Variable(this.getNom()+" decouverts consecutifs avant faillite", this, 1, 24, 12);
		this.decouvertAutorise = new Variable(this.getNom()+" decouvert autorise", this, -1000000, 0, -100000);
		this.agiosDecouvertAutorise = new Variable(this.getNom()+" agios decouvert autorise", this, 0.0, 0.17, 0.08);
		this.agiosDecouvertAuDela = new Variable(this.getNom()+" agios decouvert au dela", this, 0.08, 0.25, 0.16);
		this.seuilOperationsRefusees = new Variable(this.getNom()+" seuil operations refusees", this, -500000, 0.0, -200000);
		this.journalBanque = new Journal("Activites Bancaires", this);
		this.journalFiliere = new Journal("Activites de la filiere", this);
		this.faillites = new HashMap<IActeur, Boolean>();
	}

	public String getNom() {
		return "Banque";
	}

	public Color getColor() {
		return new Color(96, 125, 139);
	}

	public void initialiser() {
	}

	public void setCryptogramme(Integer crypto) {
	}

	public void next() {
		Set<IActeur> acteurs = comptes.keySet();
		for (IActeur a : acteurs) {
			if (!aFaitFaillite(a)) {
				if (comptes.get(a).getValeur()<0) {
					int nbDecouverts = decouvertsConsecutifs.get(a)+1;
					if (nbDecouverts>=decouvertsConsecutifsAvantFaillite.getValeur()) {
						faireFaillite(a);
					} else {
						decouvertsConsecutifs.put(a,nbDecouverts);
						agios(a);
					}
				} else {
					decouvertsConsecutifs.put(a,0);
				}
			}
		}
	}

	public void agios(IActeur a) {
		double decouvertAutorise = -comptes.get(a).getValeur();
		double auDela = 0.0;
		if (comptes.get(a).getValeur()<this.getDecouvertAutorise()) {
			decouvertAutorise = - this.getDecouvertAutorise();
			auDela = -comptes.get(a).getValeur()+this.getDecouvertAutorise();
		}
		double montantAgiosAutorises = decouvertAutorise*this.agiosDecouvertAutorise.getValeur()/24.0;
		comptes.get(a).retirer(this, montantAgiosAutorises);
		this.journalBanque.ajouter("agios \"autorises\" de "+Journal.texteColore(a.getColor(), Color.BLACK, Journal.texteSurUneLargeurDe(a.getNom(),10))+" d'un mondant de "+Journal.doubleSur(montantAgiosAutorises, 15,3));
		if (auDela>0.0) {
			double montantAgiosAuDela = auDela*this.agiosDecouvertAuDela.getValeur()/24.0;
			comptes.get(a).retirer(this, montantAgiosAuDela);
			this.journalBanque.ajouter("agios au dela du decouvert autorise de "+Journal.texteColore(a.getColor(), Color.BLACK, Journal.texteSurUneLargeurDe(a.getNom(),10))+" d'un mondant de "+Journal.doubleSur(montantAgiosAuDela, 15,3));
		}
	}

	public void erreur(String s) {
		this.journalFiliere.ajouter(Journal.texteColore(Color.RED, Color.WHITE,s));
		throw new Error(s);
	}

	public void creerCompte(IActeur acteur) {
		if (Filiere.LA_FILIERE==null || Filiere.LA_FILIERE.getEtape()==0) {
			if (acteur==null) {
				erreur("Appel de creerCompte de Banque avec null pour parametre");
			} else if (this.comptes.get(acteur)!=null) {
				erreur("Appel de creerCompte(acteur) avec acteur ayant deja un compte");
			} else {
				this.journalBanque.ajouter(Journal.texteColore(acteur, "Creation du compte de "+acteur.getNom()));
				Variable compte = new Variable(acteur.getNom()+" solde", acteur, this.decouvertAutorise.getValeur(), SOLDE_MAX,(acteur.getNom().contentEquals("CLIENTFINAL")?SOLDE_MAX:SOLDE_INITIAL));
				comptes.put(acteur, compte);
				Integer crypto = genereCryptogramme();
				this.cryptogramme.put(acteur,crypto);
				acteur.setCryptogramme(crypto);
				this.decouvertAutorise.addObserver(new CtrlDecouvertAutorise(this.decouvertAutorise, compte));
				this.decouvertsConsecutifs.put(acteur,0);
				this.faillites.put(acteur,false);
			}
		} else {
			erreur("Appel de creerCompte a l'etape "+ Filiere.LA_FILIERE.getEtape()+" : la creation de compte ne peut avoir lieu qu'a l'etape 0");
		}
	}

	public List<String> getNomsFilieresProposees() {
		return new ArrayList<String>();
	}

	public Filiere getFiliere(String nom) {
		return null;
	}

	public List<Variable> getIndicateurs() {
		boolean appelantOk=false;
		try { 
			Exception e = new Exception();
			StackTraceElement[] trace = e.getStackTrace();
			for (StackTraceElement el : trace) {
				if (el.getClassName().contains("FenetrePrincipale")) {
					appelantOk=true;
				}
			}
		} catch(Exception e2) {}

		if ((Filiere.LA_FILIERE==null || Filiere.LA_FILIERE.getEtape()==0) && (appelantOk)) {
			ArrayList<Variable> listeComptes = new ArrayList<Variable>();
			for (Variable v : this.comptes.values()) {
				if (v.getCreateur()!=this && !v.getCreateur().getNom().equals("EQ8") && faillites.get(v.getCreateur()).equals(false)) {// on ne met pas le compte bancaire de la banque et du createur des autres acteurs de la filiere (Eq7=romu)
					listeComptes.add(v);
				}
			}
			Collections.sort(listeComptes);
			return listeComptes;
		} else {
			this.journalBanque.ajouter(Journal.texteColore(Color.RED,  Color.WHITE,"Methode getIndicateurs() de Banque : Tentative d'acces non autorise aux comptes"));
			return new ArrayList<Variable>();
		}
	}

	public List<Variable> getParametres() {
		List<Variable> res = new ArrayList<Variable>();
		res.add(this.decouvertsConsecutifsAvantFaillite);
		res.add(this.decouvertAutorise);
		res.add(this.agiosDecouvertAutorise);
		res.add(this.agiosDecouvertAuDela);
		res.add(this.seuilOperationsRefusees);
		return res;
	}

	public List<Journal> getJournaux() {
		ArrayList<Journal> res = new ArrayList<Journal>();
		res.add(this.journalFiliere);
		res.add(this.journalBanque);
		return res;
	}

	public String getDescription() {
		return "LA banque";
	}

	public Integer genereCryptogramme() {
		Integer crypto=100000000+((int)(Math.random()*899999999)); 
		while (cryptogramme.values().contains(crypto)) {
			crypto=100000000+((int)(Math.random()*899999999)); 
		}
		return crypto;
	}
	
	public boolean verifier(IActeur acteur, long cryptogramme) {
		return this.cryptogramme.get(acteur)==cryptogramme;
	}

	public void faireFaillite(IActeur acteur) {
		this.journalBanque.ajouter(Journal.texteColore(acteur, "Faillite de "+acteur.getNom()));
		this.faillites.put(acteur, true);
		Filiere.LA_FILIERE.notificationFaillite(acteur);
		for (IActeur a : this.comptes.keySet()) {
			this.journalBanque.ajouter(Journal.texteColore(acteur, "- notification de Faillite de "+acteur.getNom())+Journal.texteColore(a, " a "+a.getNom()));
			a.notificationFaillite(acteur);
		}
		this.journalBanque.notifyObservers();

	}

	public boolean aFaitFaillite(IActeur acteur) {
		if (acteur==null) {
			erreur("Appel de aFaitFaillite de Banque avec null pour parametre");
		} else if (this.comptes.get(acteur)==null) {
			erreur("Appel de aFaitFaillite(acteur) avec acteur n'ayant aucun compte");
		} 
		return this.faillites.get(acteur);
	}

	public void notificationFaillite(IActeur acteur) {
		if (this==acteur) {
			System.out.println("OMG !!! They killed the banker !");
		} else {
			System.out.println("Poor "+acteur.getNom()+"... We will miss you. The Banker");
		}
	}

	public double getDecouvertAutorise() {
		return this.decouvertAutorise.getValeur();
	}

	public double getSeuilOperationsRefusees() {
		return this.seuilOperationsRefusees.getValeur();
	}

	public double getAgiosDecouvertAutorise() {
		return this.agiosDecouvertAutorise.getValeur();
	}

	public double getAgiosDecouvertAuDela() {
		return this.agiosDecouvertAuDela.getValeur();
	}

	public double getSolde(IActeur acteur, int cryptogramme) {
		if (acteur==null) {
			erreur(" Appel de getSolde de Banque avec un parametre null");
		} else if (this.cryptogramme.get(acteur)!=cryptogramme) {
			erreur(" Appel de getSolde de Banque avec un cryptogramme qui n'est pas celui du compte");
		}
		return comptes.get(acteur).getValeur();
	}

	public void initCompte(Filiere f, String nomActeur, double soldeInit) {
		if (f!=null && (Filiere.LA_FILIERE==null || Filiere.LA_FILIERE.getEtape()==0)) {
			for (IActeur a : comptes.keySet()) {
				if (a.getNom().equals(nomActeur)) {
					comptes.get(a).setValeur(this, soldeInit);
				}
			}
		}
	}

	public boolean virer(IActeur acteurADebiter, int cryptogrammeActeurADebiter, IActeur acteurACrediter, double montant) {
		if (acteurADebiter==null || acteurACrediter==null) {
			erreur(" Appel de virer de Banque avec un parametre null");
		} else if (!comptes.containsKey(acteurADebiter) || !comptes.containsKey(acteurACrediter) ) {
			erreur(" Appel de virer de Banque avec un acteur qui n'a pas de compte bancaire");
		} else if (acteurADebiter==acteurACrediter) {
			erreur(" Appel de virer de Banque avec un acteur identique pour acteur a debiter et acteur a crediter");
		} else if (montant<=0) {
			erreur(" Appel de virer de Banque avec un montant egal a "+montant+" au lieu d'un montant strictement positif");
		} else if (this.cryptogramme.get(acteurADebiter)!=cryptogrammeActeurADebiter) {
			erreur(" Appel de virer de Banque avec un cryptogramme qui n'est pas celui du compte a debiter");
		} else if (getSolde(acteurADebiter,cryptogrammeActeurADebiter)-montant<this.getSeuilOperationsRefusees()) {
			this.journalBanque.ajouter(Color.RED, Color.WHITE," Virement d'un montant "+Journal.doubleSur(montant, 15,3)+" impossible car cela amenerait le solde du compte de "+Journal.texteColore(acteurADebiter.getColor(), Color.BLACK, acteurADebiter.getNom())+Journal.texteColore(Color.red, Color.white, " en dessous du decouvert autorise"));
			return false;
		} else if (this.aFaitFaillite(acteurADebiter)) {
			this.journalBanque.ajouter(Color.RED, Color.WHITE," Appel de virer de Banque avec l'acteur dont le compte est a debiter qui a fait faillite : "+Journal.texteColore(acteurADebiter.getColor(), Color.BLACK, acteurADebiter.getNom()));
			return false;
		} else if (this.aFaitFaillite(acteurACrediter)) {
			this.journalBanque.ajouter(Color.RED, Color.WHITE," Appel de virer de Banque avec l'acteur dont le compte est a crediter qui a fait faillite : "+Journal.texteColore(acteurACrediter.getColor(), Color.BLACK, acteurACrediter.getNom()));
			return false;
		} else{
			if (!acteurADebiter.getNom().equals("CLIENTFINAL") && !acteurADebiter.getNom().equals("BourseChoco")) {
				comptes.get(acteurADebiter).retirer(this, montant);
				acteurADebiter.notificationOperationBancaire(-montant);
			}
			if (!acteurACrediter.getNom().equals("BourseChoco")) {
			comptes.get(acteurACrediter).ajouter(this,  montant);
			acteurACrediter.notificationOperationBancaire(montant);
			}
			this.journalBanque.ajouter("virement de "+Journal.texteColore(acteurADebiter.getColor(), Color.BLACK, Journal.texteSurUneLargeurDe(acteurADebiter.getNom(),10))+" vers "+Journal.texteColore(acteurACrediter.getColor(), Color.BLACK, Journal.texteSurUneLargeurDe(acteurACrediter.getNom(),10))+" d'un mondant de "+Journal.doubleSur(montant, 15,3));
			return true;
		}
		return false;
	}

	public void notificationOperationBancaire(double montant) {
	}

}
