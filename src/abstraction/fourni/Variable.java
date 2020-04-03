package abstraction.fourni;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;


/**
 * Classe modelisant un indicateur. 
 * Un indicateur est une variable numerique dont la valeur 
 * pourra etre historisee et visualisee.
 * 
 * Vous aurez a creer des instances de cette classe (potentiellement 
 * partagees entre plusieurs acteurs) et a faire evoluer leurs valeurs.
 *
 * @author Romuald Debruyne
 */
public class Variable implements Comparable<Variable>{
	private String nom;           // nom designant l'indicateur
	private IActeur createur;      // acteur a l'origine de la creation de l'indicateur
	private Courbe courbe;        // ensemble des couples (etape, valeur). Exploite par la classe Graphique
	// min et max sont des seuils d'alerte, mais ne sont pas bloquants : la valeur peut depasser ces seuils
	private double min;           // si la valeur est inferieure a min alors elle sera en rouge dans l'interface
	private double max;           // si la valeur est superieure a max alors elle sera en rose dans l'interface
	private Historique historique;// memorise l'historique des changements de valeur depuis la creation (premier element)
	// a la valeur actuelle (dernier element), en precisant pour chaque changement
	// le nom de l'acteur a l'origine de la modification, l'etape a laquelle le 
	// changement intervient et bien sur la nouvelle valeur.
	private PropertyChangeSupport pcs; // Pour notifier les observers (les graphiques notamment) des changements 

	/**
	 * 
	 * @param nom identifiant designant l'indicateur
	 * @param createur l'acteur qui est a l'origine de la creation de l'indicateur
	 * @param valInit la valeur initiale de l'indicateur
	 */
	public Variable(String nom, IActeur createur,  double min, double max, double valInit) {
		this.nom = nom;
		this.createur = createur;
		this.min = min;
		this.max = max;
		this.historique = new Historique();
		this.historique.ajouter(createur, Filiere.LA_FILIERE==null ? 0 : Filiere.LA_FILIERE.getEtape(), valInit);
		this.courbe = new Courbe(this.nom);
		this.courbe.ajouter(Filiere.LA_FILIERE==null ? 0 : Filiere.LA_FILIERE.getEtape(), valInit);
		this.pcs = new  PropertyChangeSupport(this);
	}
	public Variable(String nom, IActeur createur, double valInit) {
		this(nom, createur, -Double.MAX_VALUE, Double.MAX_VALUE, valInit);
	}
	/**
	 * Par defaut, la valeur initiale est fixee a 0.0
	 */
	public Variable(String nom, IActeur createur) {
		this(nom, createur, 0.0);
	}
	/**
	 * @return Retourne le nom de l'indicateur
	 */
	public String getNom() {
		return this.nom;
	}
	/**
	 * @return Retourne l'acteur a l'origine de la creation de l'indicateur
	 */
	public IActeur getCreateur() {
		return this.createur;
	}
	/**
	 * @return Retourne l'historique des changements de valeur
	 */
	public Historique getHistorique() {
		return this.historique;
	}
	/**
	 * @return Retourne la Courbe (utilise principalement pour l'affichage du graphique correspondant)
	 */
	public Courbe getCourbe() {
		return this.courbe;
	}
	/**
	 * @return Retourne la valeur actuelle de l'indicateur (donc la derniere valeur indiquee dans l'historique)
	 */
	public double getValeur() {
		return this.historique.getValeur();
	}
	/**
	 * Affecte la valeur valeur a l'indicateur en precisant
	 * que c'est auteur qui est a l'origine de ce changement.
	 * @param auteur Acteur a l'origine de la modification de valeur
	 * @param valeur La nouvelle valeur
	 */
	public void setValeur(IActeur auteur, double valeur) {
		double old = getValeur();
		int etape = Filiere.LA_FILIERE==null ? 0 : Filiere.LA_FILIERE.getEtape();
		this.historique.ajouter(auteur, etape, valeur);
		this.courbe.ajouter(etape, this.getValeur());
		pcs.firePropertyChange("Value",old,valeur);
	}

	public double getMin() {
		return this.min;
	}
	
	public void setMin(double min) {
		double oldMin=this.min;
		this.min = min;
		pcs.firePropertyChange("min",oldMin,min);
	}
	
	public double getMax() {
		return this.max;
	}

	public void setMax(double max) {
		double oldMax=this.max;
		this.max = max;
		pcs.firePropertyChange("max",oldMax,max);
	}

	public boolean equals(Object o) {
		return (o instanceof Variable) && (this.getNom().equals(((Variable)o).getNom()));
	}
	/**
	 * Ajoute montant a la valeur de l'indicateur
	 * @param auteur
	 * @param delta>0
	 */
	public void ajouter(IActeur auteur, double delta) {
		this.setValeur(auteur, this.getValeur()+delta);
	}

	/**
	 * Retire montant a la valeur de l'indicateur
	 * @param auteur
	 * @param delta>0
	 */
	public void retirer(IActeur auteur, double delta) {
		this.setValeur(auteur, this.getValeur()-delta);
	}
	public void addObserver(PropertyChangeListener obs) {
		pcs.addPropertyChangeListener(obs);
	}
	public int compareTo(Variable o) {
		return this.getNom().compareTo(o.getNom());
	}


}
