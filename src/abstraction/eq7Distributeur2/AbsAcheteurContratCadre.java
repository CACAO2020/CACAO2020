package abstraction.eq7Distributeur2;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import abstraction.eq8Romu.contratsCadres.ExemplaireContratCadre;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;
import abstraction.eq8Romu.produits.Chocolat;

public class AbsAcheteurContratCadre {

	// Stocke la liste des exemplaires des contrats en cours
	protected List<ExemplaireContratCadre> nosContrats;
	
	protected Map<Chocolat, Double> quantitesARecevoirParContrats;
	
	// Référence à l'acteur principal
	protected Distributeur2 ac;
	
	// Journal principal
	public Journal journal;
	
	// Journal des contrats
	public Journal journalContrats;
	
	// Couleurs d'arrière-plan pour les messages des journaux
	public Color titleColor = Color.BLACK;
	public Color alertColor = Color.RED;
	public Color warningColor = Color.ORANGE;
	public Color positiveColor = Color.GREEN;
	public Color descriptionColor = Color.YELLOW;
	public Color metaColor = Color.CYAN;
	
	public AbsAcheteurContratCadre(Distributeur2 ac) {
		this.quantitesARecevoirParContrats = new HashMap<Chocolat, Double>();
		for (Chocolat choco : Chocolat.values()) {
			this.quantitesARecevoirParContrats.put(choco, 0.);
		}
		this.ac = ac;
		this.nosContrats = new ArrayList<ExemplaireContratCadre>();
		initJournaux();
	}

	// Initialise les journaux
	public void initJournaux() {
		this.journal = new Journal(this.getNom() + " : Acheteur Contrat Cadre", ac);
		journal.ajouter(Journal.texteColore(titleColor, Color.WHITE, this.getNom() + " : Acheteur Contrat Cadre"));
		journal.ajouter(Journal.texteColore(descriptionColor, Color.BLACK, "Ce journal suit les activités de l'acheteur de chocolat par contrats-cadres"));
		journal.ajouter(Journal.texteColore(descriptionColor, Color.BLACK, "associé à ce distributeur."));
		this.journalContrats = new Journal(this.getNom() + " : Journal Contrats", ac);
		journalContrats.ajouter(Journal.texteColore(titleColor, Color.WHITE, this.getNom() + " : Journal Contrats"));
		journalContrats.ajouter(Journal.texteColore(descriptionColor, Color.BLACK, "Ce journal suit les contrats de l'acheteur par contrat-cadre."));
	}
	
	// Renvoie les indicateurs
	public List<Variable> getIndicateurs() {
		List<Variable> res=new ArrayList<Variable>();
		return res;
	}

	// Renvoie les paramètres
	public List<Variable> getParametres() {
		List<Variable> res=new ArrayList<Variable>();
		return res;
	}

	// Renvoie les journaux
	public List<Journal> getJournaux() {
		List<Journal> res = new ArrayList<Journal>();
		res.add(journal);
		res.add(journalContrats);
		return res;
	}

	// Méthodes renvoyant aux méthodes de l'acteur principal
	public String getNom() {
		return ac.getNom();
	}

	public String getDescription() {
		return ac.getDescription();
	}

	public Color getColor() {
		return ac.getColor();
	}

	public List<String> getNomsFilieresProposees() {
		return ac.getNomsFilieresProposees();
	}

	public Filiere getFiliere(String nom) {
		return ac.getFiliere(nom);
	}

	public void setCryptogramme(Integer crypto) {
		ac.setCryptogramme(crypto);
	}

	public void notificationFaillite(IActeur acteur) {
		ac.notificationFaillite(acteur);
	}

	public void notificationOperationBancaire(double montant) {
		ac.notificationOperationBancaire(montant);
	}
}
