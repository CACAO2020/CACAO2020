package abstraction.eq7Distributeur2;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import abstraction.eq8Romu.contratsCadres.ExemplaireContratCadre;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;

public class AbsAcheteurContratCadre {

	protected List<ExemplaireContratCadre> mesContrats;
	
	protected Distributeur2 ac;
	
	public Journal journal;
	public Journal journalContrats;
	
	public Color titleColor = Color.BLACK;
	public Color alertColor = Color.RED;
	public Color warningColor = Color.ORANGE;
	public Color positiveColor = Color.GREEN;
	public Color descriptionColor = Color.YELLOW;
	public Color metaColor = Color.CYAN;
	
	public AbsAcheteurContratCadre(Distributeur2 ac) {
		this.ac = ac;
		this.mesContrats = new ArrayList<ExemplaireContratCadre>();
		initJournaux();
	}

	public void initJournaux() {
		this.journal = new Journal(this.getNom() + " : Acheteur Contrat Cadre", ac);
		journal.ajouter(Journal.texteColore(titleColor, Color.WHITE, this.getNom() + " : Acheteur Contrat Cadre"));
		journal.ajouter(Journal.texteColore(descriptionColor, Color.BLACK, "Ce journal suit les activités de l'acheteur de chocolat par contrats-cadres"));
		journal.ajouter(Journal.texteColore(descriptionColor, Color.BLACK, "associé à ce distributeur."));
		this.journalContrats = new Journal(this.getNom() + " : Journal Contrats", ac);
		journalContrats.ajouter(Journal.texteColore(titleColor, Color.WHITE, this.getNom() + " : Journal Contrats"));
		journalContrats.ajouter(Journal.texteColore(descriptionColor, Color.BLACK, "Ce journal suit les contrats de l'acheteur par contrat-cadre."));
	}
	
	public List<Variable> getIndicateurs() {
		List<Variable> res=new ArrayList<Variable>();
		return res;
	}

	public List<Variable> getParametres() {
		List<Variable> res=new ArrayList<Variable>();
		return res;
	}

	public List<Journal> getJournaux() {
		List<Journal> res = new ArrayList<Journal>();
		res.add(journal);
		res.add(journalContrats);
		return res;
	}

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
