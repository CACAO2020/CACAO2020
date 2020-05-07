package abstraction.fourni;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import abstraction.eq8Romu.clients.ClientFinal;
import abstraction.eq8Romu.produits.ChocolatDeMarque;
import presentation.FenetrePrincipale;

import java.awt.Color;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Classe modelisant une filiere vue comme un regroupement d'acteurs, 
 * d'indicateurs, de parametres et de journaux. 
 * 
 * Les acteurs/indicateurs/parametres/journaux que vous creerez devront etre
 * ajoutes a l'unique instance de cette classe designee par la 
 * variable LA_FILIERE. 
 *
 * @author Romuald Debruyne
 */
public class Filiere {
	public static Filiere LA_FILIERE; // Filiere.LA_FILIERE reference l'unique instance de Filiere

	private int etape;                   // Le numero d'etape en cours
	private Banque laBanque;             // L'unique banque. Tous les acteurs ont un compte a cette banque
	private List<IActeur> acteurs;       // La liste des acteurs
	private List<IActeur> acteursSolvables;// La liste des acteurs n'ayant pas fait faillite
	private HashMap<String, Variable> indicateurs;// Table associant a chaque nom d'indicateur la variable qui la represente
	private HashMap<IActeur, List<Variable>> indicateursParActeur; // Table associant a chaque acteur sa liste d'indicateurs
	private HashMap<String, Variable> parametres;// Table associant a chaque nom de parametre la variable qui la represente
	private HashMap<IActeur, List<Variable>> parametresParActeur; // Table associant a chaque acteur sa liste de parametres
	private HashMap<String, Journal> journaux;      // La liste des journaux
	private HashMap<IActeur, List<Journal>> journauxParActeur; // Table associant a chaque acteur sa liste de journaux
	private PropertyChangeSupport pcs;        // Pour notifier les observers des changements de step 
	private Journal journalFiliere;

	/**
	 * Initialise la filiere de sorte que le numero d'etape soit 0, 
	 * et qu'il n'y ait pour l'heure que la Banque pour unique acteur. 
	 * Les constructeurs des sous-classes de Filiere devront ajouter les autres acteurs
	 */
	public Filiere() {
		this.etape=0;
		this.acteurs=new ArrayList<IActeur>();
		this.acteursSolvables=new ArrayList<IActeur>();
		this.indicateurs=new HashMap<String, Variable>();
		this.parametres=new HashMap<String, Variable>();
		this.indicateursParActeur=new HashMap<IActeur, List<Variable>>();
		this.parametresParActeur=new HashMap<IActeur, List<Variable>>();
		this.journaux=new HashMap<String, Journal>();
		this.journauxParActeur=new HashMap<IActeur, List<Journal>>();
		this.pcs = new  PropertyChangeSupport(this);
		this.laBanque = new Banque();
		this.journalFiliere = this.laBanque.getJournaux().get(0);
		this.ajouterActeur(this.laBanque);
	}

	public void initialiser() {
		this.journalFiliere.ajouter("Initialiser()");
		for (IActeur a : this.acteurs) {
			a.initialiser();
		}
	}

	/**
	 * @return Retourne le numero de l'etape en cours.
	 */
	public int getEtape() {
		return this.etape;
	}

	public Banque getBanque() {
		return this.laBanque;
	}

	private void incEtape() {
		int old = this.etape;
		this.etape++;
		pcs.firePropertyChange("Etape",old,this.etape);
	}

	public int getAnnee() {
		return 2020+this.etape/24;
	}

	public int getNumeroMois() {
		return 1+(this.etape%24)/2;
	}

	public int getJour() {
		return (this.etape%2==0 ? 1 : 15);
	}

	public String getMois() {
		String[] mois= {"janvier", "fevrier", "mars", "avril", "mai", "juin", "juillet", "aout", "septembre", "octobre", "novembre", "decembre"};
		return mois[getNumeroMois()-1];
	}

	public String getDate() {
		return this.getJour()+" "+this.getMois()+" "+this.getAnnee();
	}
	
	public double prixMoyenEtapePrecedente(ChocolatDeMarque choco) {
		for (IActeur acteur : this.acteurs) {
			if (acteur instanceof ClientFinal) {
				if (getEtape()<1) {
					throw new IllegalArgumentException("Il n'est pas possible d'appeler prixMoyenEtapePrecedente a la premiere etape");
				}
				return ((ClientFinal)acteur).prixMoyenEtapePrecedente(choco);
			}
		}
		throw new IllegalArgumentException("il n'est possible d'appeler prixMoyenEtapePrecedente que pour une filiere comportant une instance de ClientFinal");
	}
	
	public double getVentes(int etape, ChocolatDeMarque choco) {
		for (IActeur acteur : this.acteurs) {
			if (acteur instanceof ClientFinal) {
				return ((ClientFinal)acteur).getVentes(etape, choco);
			}
		}
		throw new IllegalArgumentException("il n'est possible d'appeler getVentes que pour une filiere comportant une instance de ClientFinal");
	}

	/**
	 * Ajoute l'acteur ac a la filiere si il n'existe pas deja un acteur portant le meme nom
	 * Leve une erreur si le parametre est null ou est le nom d'un acteur deja dans la filiere
	 * @param ac, l'acteur a ajouter
	 */
	public void ajouterActeur(IActeur ac) {
		this.journalFiliere.ajouter(Journal.texteColore(ac==null ? Color.white : ac.getColor(), Color.black,"ajouterActeur("+(ac==null?"null":ac.getNom())+")"));
		if (ac==null) {
			erreur("Appel a ajouterActeur de Filiere avec un parametre null");
		} else if (this.getActeur(ac.getNom())==null) {
			this.acteurs.add(ac);
			this.acteursSolvables.add(ac);
		} else {
			erreur("Appel a ajouterActeur de Filiere avec pour parametre le nom d'un acteur deja present dans la filiere");
		}
		this.getBanque().creerCompte(ac);
		this.journalFiliere.ajouter(Journal.texteColore(ac, "- creation du compte bancaire de "+ac.getNom()));
		this.initIndicateurs(ac);
		List<Variable> indicateursAAjouter = ac.getIndicateurs();
		for (Variable v : indicateursAAjouter) {
			this.ajouterIndicateur(v);
			this.journalFiliere.ajouter(Journal.texteColore(ac,"- ajout de l'indicateur "+v.getNom()));
		}
		this.initParametres(ac);
		List<Variable> parametresAAjouter = ac.getParametres();
		for (Variable v : parametresAAjouter) {
			this.ajouterParametre(v);
			this.journalFiliere.ajouter(Journal.texteColore(ac,"- ajout du parametre "+v.getNom()));
		}
		this.initJournaux(ac);
		List<Journal> journauxAAjouter = ac.getJournaux();
		for (Journal j : journauxAAjouter) {
			this.ajouterJournal(j);
			this.journalFiliere.ajouter(Journal.texteColore(ac,"- ajout du journal "+j.getNom()));
		}
	}

	private void initIndicateurs(IActeur acteur) {
		if (this.indicateursParActeur.get(acteur)==null) {
			this.indicateursParActeur.put(acteur, new ArrayList<Variable>());
		}
	}
	/**
	 * Ajoute l'indicateur i a la filiere
	 * @param i l'idicateur a ajouter
	 */
	public void ajouterIndicateur(Variable i) {
		if (i==null) {
			erreur("Appel a ajouterIndicateur de Filiere avec null pour parametre");
		} else if (this.indicateurs.get(i.getNom())!=null) {
			erreur("Appel a ajouterIndicateur(v) de Filiere alors qu'il existe deja dans la filiere un indicateur portant le meme nom que v (\""+i.getNom()+"\")");
		} else {
			this.indicateurs.put(i.getNom(), i);
			List<Variable> indicateursActuels = this.indicateursParActeur.get(i.getCreateur());
			if (indicateursActuels==null) {
				indicateursActuels=new ArrayList<Variable>();
			}
			indicateursActuels.add(i);
			this.indicateursParActeur.put(i.getCreateur(), indicateursActuels);
		}
	}

	private void initParametres(IActeur acteur) {
		if (this.parametresParActeur.get(acteur)==null) {
			this.parametresParActeur.put(acteur, new ArrayList<Variable>());
		}
	}
	/**
	 * Ajoute le parametre i a la filiere
	 * @param i le parametre a ajouter
	 */
	public void ajouterParametre(Variable i) {
		if (i==null) {
			erreur("Appel a ajouterParametre de Filiere avec null pour parametre");
		} else if (this.parametres.get(i.getNom())!=null) {
			erreur("Appel a ajouterParametre(v) de Filiere alors qu'il existe deja dans la filiere un parametre portant le meme nom que v (\""+i.getNom()+"\")");
		} else {
			this.parametres.put(i.getNom(), i);
			List<Variable> parametresActuels = this.parametresParActeur.get(i.getCreateur());
			if (parametresActuels==null) {
				parametresActuels=new ArrayList<Variable>();
			}
			parametresActuels.add(i);
			this.parametresParActeur.put(i.getCreateur(), parametresActuels);
		}
	}
	
	private void initJournaux(IActeur acteur) {
		if (this.journauxParActeur.get(acteur)==null) {
			this.journauxParActeur.put(acteur, new ArrayList<Journal>());
		}
	}
	
	/**
	 * Ajoute le journal j au monde
	 * @param j le journal a ajouter
	 */
	public void ajouterJournal(Journal j) {
		if (j==null) {
			erreur("Appel a ajouterJournal de Filiere avec null pour parametre");
		} else if (this.journaux.get(j.getNom())!=null) {
			erreur("Appel a ajouterJournal(j) de Filiere alors qu'il existe deja dans la filiere un journal portant le meme nom que j (\""+j.getNom()+"\")");
		} else {
			this.journaux.put(j.getNom(), j);
			List<Journal> journauxActuels = this.journauxParActeur.get(j.getCreateur());
			if (journauxActuels==null) {
				journauxActuels=new ArrayList<Journal>();
			}
			journauxActuels.add(j);
			this.journauxParActeur.put(j.getCreateur(), journauxActuels);
		}
	}

	/**
	 * @return Retourne une copie de la liste des acteurs du monde
	 */
	public List<IActeur> getActeurs() {
		return new ArrayList<IActeur>(this.acteurs);
	}

	/**
	 * @return Retourne une copie de la liste des acteurs de la filiere n'ayant pas fait faillite
	 */
	public List<IActeur> getActeursSolvables() {
		return new ArrayList<IActeur>(this.acteursSolvables);
	}

	/**
	 * @param nom Le nom de l'acteur a retourner
	 * @return Si il existe dans la filiere un acteur de nom nom, retourne cet acteur.
	 * Sinon, returne null. 
	 */
	public IActeur getActeur(String nom) {
		int i=0; 
		while (i<this.acteurs.size() && !this.acteurs.get(i).getNom().equals(nom)) {
			i++;
		}
		if (i<this.acteurs.size()) {
			return this.acteurs.get(i);
		} else {
			return null;
		}
	}

	/** 
	 * @return Retourne une copie de la liste des indicateurs de l'acteur
	 */
	public List<Variable> getIndicateurs(IActeur acteur) {
		if (acteur==null) {
			erreur("Appel de getIndicateurs de Filiere avec null pour parametre");
		} else if (!this.indicateursParActeur.keySet().contains(acteur)){
			erreur("Appel de getIndicateurs de Filiere avec pour parametre un acteur non present ");
		} 
		return new ArrayList<Variable>(this.indicateursParActeur.get(acteur));
	}

	/** 
	 * @return Retourne une copie de la liste des parametres de l'acteur
	 */
	public List<Variable> getParametres(IActeur acteur) {
		if (acteur==null) {
			erreur("Appel de getParametres de Filiere avec null pour parametre");
		} else if (!this.parametresParActeur.keySet().contains(acteur)){
			erreur("Appel de getParametres de Filiere avec pour parametre un acteur non present ");
		} 
		return new ArrayList<Variable>(this.parametresParActeur.get(acteur));
	}

	/**
	 * @param nom le nom de l'indicateur a retourner
	 * @return Si il existe dans le Monde un indicateur de nom nom
	 * retourne cet indicateur. Sinon, affiche un message d'alerte 
	 * et retourne null.
	 */
	public Variable getIndicateur(String nomIndicateur) {
		if (nomIndicateur==null) {
			erreur("Appel de getIndicateur de Filiere avec null pour parametre");
		}
		Variable res = this.indicateurs.get(nomIndicateur);
		if (res==null) {
			System.out.println("  Aie... recherche d'un indicateur en utilisant un nom incorrect : \""+nomIndicateur+"\" n'est pas dans la liste :"+indicateurs.keySet());
			System.out.println("  la variable que vous recherchez est peut etre un parametre plutot qu'un indicateur ?");
		}
		return res;
	}

	/**
	 * @param nom le nom de l'indicateur a retourner
	 * @return Si il existe dans le Monde un indicateur de nom nom
	 * retourne cet indicateur. Sinon, affiche un message d'alerte 
	 * et retourne null.
	 */
	public Variable getParametre(String nomParametre) {
		if (nomParametre==null) {
			erreur("Appel de getParametre de Filiere avec null pour parametre");
		} 
		Variable res = this.parametres.get(nomParametre);
		if (res==null) {
			System.out.println("  Aie... recherche d'un parametre en utilisant un nom incorrect : \""+nomParametre+"\" n'est pas dans la liste :"+parametres.keySet());
			System.out.println("  la variable que vous recherchez est peut etre un indicateur plutot qu'un parametre ?");
		}
		return res;
	}
	
	/**
	 * @return Retourne la liste des journaux de l'acteur specifie
	 */
	public List<Journal> getJournaux(IActeur acteur) {
		if (acteur==null) {
			erreur("Appel de getJournaux de Filiere avec null pour parametre");
		} else if (!this.journauxParActeur.keySet().contains(acteur)){
			erreur("Appel de getJournaux de Filiere avec pour parametre un acteur non present ");
		} 
		return new ArrayList<Journal>(this.journauxParActeur.get(acteur));
	}

	public void erreur(String s) {
		this.journalFiliere.ajouter(Journal.texteColore(Color.RED, Color.WHITE,s));
		throw new Error(s);
	}
	
	/**
	 * Methode appelee lorsque l'utilisateur clique sur le bouton NEXT de l'interface graphique.
	 * Cette methode incremente le numero d'etape puis appelle la methode next() de chaque acteur du monde.
	 */
	public void next() {
		this.journalFiliere.ajouter("Next() : Passage a l'etape suivante====================== ");
		for (IActeur a : this.acteurs) {
			if (!this.laBanque.aFaitFaillite(a)) {
				this.journalFiliere.ajouter(Journal.texteColore(a, "- "+a.getNom()+".next()"));
				this.journalFiliere.notifyObservers();
				a.next();
				for (Journal j : journauxParActeur.get(a)) {
					j.notifyObservers();
				}
			}
		}
		this.incEtape();
	}

	public void addObserver(PropertyChangeListener obs) {
		pcs.addPropertyChangeListener(obs);
	}

	public void notificationFaillite(IActeur acteur) {
		this.acteursSolvables.remove(acteur);
		this.journalFiliere.ajouter(Journal.texteColore(acteur,"Faillite de "+acteur.getNom()));
		this.journalFiliere.notifyObservers();
		FenetrePrincipale.LA_FENETRE_PRINCIPALE.notificationFaillite(acteur);
	}
}
