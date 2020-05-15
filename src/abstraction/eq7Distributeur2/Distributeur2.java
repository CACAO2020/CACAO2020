package abstraction.eq7Distributeur2;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import abstraction.eq8Romu.chocolatBourse.IAcheteurChocolatBourse;
import abstraction.eq8Romu.chocolatBourse.SuperviseurChocolatBourse;
import abstraction.eq8Romu.clients.ClientFinal;
import abstraction.eq8Romu.clients.IDistributeurChocolatDeMarque;
import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.ChocolatDeMarque;
import abstraction.eq8Romu.produits.Feve;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;
import abstraction.eq7Distributeur2.*;


public class Distributeur2 implements IActeur, IAcheteurChocolatBourse, IDistributeurChocolatDeMarque {
	
	private static int NB_INSTANCES = 0;
	public int numero;
	
	private int cryptogramme;
	
	private AcheteurChocolat acheteurChocolat;
	private DistributeurChocolat distributeurChocolat;
	private Stock stock;

	public Color titleColor = Color.BLACK;
	private Journal journal;
	
	public Distributeur2() {
		NB_INSTANCES++;
		numero = NB_INSTANCES;
		acheteurChocolat = new AcheteurChocolat(this);
		distributeurChocolat = new DistributeurChocolat(this);
		stock = new Stock(this);
		journal = new Journal(getNom() + " : Activités", this);
	}
	 
	public int getNumero() {
		return this.numero; 
	}
	
	public AcheteurChocolat getAcheteurChocolat() {
		return this.acheteurChocolat;
	}
	
	public DistributeurChocolat getDistributeurChocolat() {
		return this.distributeurChocolat;
	}
	
	public Stock getStock() {
		return this.stock;
	}
		
	public String getNom() {
		return "EQ7";
	}

	public String getDescription() {
		return "Distributeur Ecocoa de Liège, chocolatier responsable";
	}

	public Color getColor() {
		return new Color(240, 195, 15);
	}

	public void initialiser() {
		journal.ajouter(Journal.texteColore(titleColor, Color.WHITE, "EQ7 : Journal d'activités"));
		acheteurChocolat.initialiser();
		distributeurChocolat.initialiser();
		stock.initialiser();
	}

	public void next() {
		acheteurChocolat.next();
		distributeurChocolat.next();
		stock.next();
	}

	public List<String> getNomsFilieresProposees() {
		return new ArrayList<String>();
	}

	public Filiere getFiliere(String nom) {
		return Filiere.LA_FILIERE;
	}

	public List<Variable> getIndicateurs() {
		List<Variable> res = new ArrayList<Variable>();
		res.addAll(stock.getIndicateurs());
		res.addAll(acheteurChocolat.getIndicateurs());
		res.addAll(distributeurChocolat.getIndicateurs());
		return res;
	}

	public List<Variable> getParametres() {
		List<Variable> res=new ArrayList<Variable>();
		res.addAll(stock.getParametres());
		res.addAll(acheteurChocolat.getParametres());
		res.addAll(distributeurChocolat.getParametres());
		return res;
	}

	public List<Journal> getJournaux() {
		List<Journal> res=new ArrayList<Journal>();
		res.add(journal);
		res.addAll(stock.getJournaux());
		res.addAll(acheteurChocolat.getJournaux());
		res.addAll(distributeurChocolat.getJournaux());
		return res;
	}

	public void notificationFaillite(IActeur acteur) {
		if (this==acteur) {
			System.out.println("I'll be back... or not... "+this.getNom());
		} else {
			System.out.println("Poor "+acteur.getNom()+"... We will miss you. "+this.getNom());

		}
	}	

	public void notificationOperationBancaire(double montant) {
	}

	// Méthodes de l'acheteur de chocolat à la bourse
	
	public void setCryptogramme(Integer crypto) {
		this.cryptogramme = crypto;
	}

	public double getDemande(Chocolat chocolat, double cours) {
		return acheteurChocolat.getDemande(chocolat, cours);
	}

	public Integer getCryptogramme(SuperviseurChocolatBourse superviseur) {
		return this.cryptogramme;
	}

	public void notifierCommande(Chocolat chocolat, double quantiteObtenue, boolean payee) {
		acheteurChocolat.notifierCommande(chocolat, quantiteObtenue, payee);
	}

	public void receptionner(ChocolatDeMarque chocolat, double quantite) {
		acheteurChocolat.receptionner(chocolat, quantite);
	}

	// Méthodes du distributeur de chocolat de marque
	
	public List<ChocolatDeMarque> getCatalogue() {
		return distributeurChocolat.getCatalogue();
	}

	public double prix(ChocolatDeMarque choco) {
		return distributeurChocolat.prix(choco);
	}

	public double quantiteEnVente(ChocolatDeMarque choco) {
		return distributeurChocolat.quantiteEnVente(choco);
	}

	public void vendre(ClientFinal client, ChocolatDeMarque choco, double quantite, double montant) {
		distributeurChocolat.vendre(client, choco, quantite, montant);
	}

	public void notificationRayonVide(ChocolatDeMarque choco) {
		distributeurChocolat.notificationRayonVide(choco);
	}

	public List<ChocolatDeMarque> pubSouhaitee() {
		return distributeurChocolat.pubSouhaitee();
	}

}
