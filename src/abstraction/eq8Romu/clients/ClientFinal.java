package abstraction.eq8Romu.clients;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import abstraction.eq8Romu.produits.Chocolat;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;

public class ClientFinal implements IActeur {
	private static final double DISTRIBUTIONS_ANNUELLES[][] = 
		{
				//				 Jan1 Jan2 Fev1 Fev2 Mar1 Mar2 Avr1 Avr2 Mai1 Mai2 Jui1 Jui2 Jul1 Jul2 Aou1 Aou2 Sep1 Sep2 Oct1 Oct2 Nov1 Nov2 Dec1 Dec2
				{ 3.5, 3.5, 6.0, 3.5, 3.5, 3.5, 3.5, 3.5, 9.0, 3.5, 3.0, 3.0, 3.0, 3.0, 3.0, 3.0, 3.5, 3.5, 3.5, 3.5, 3.5, 3.5, 9.0, 9.0, },			
				{ 3.0, 3.0, 6.0, 3.0, 3.0, 3.0, 3.0, 3.0, 9.0, 3.0, 3.0, 2.0, 2.0, 2.0, 2.0, 2.0, 3.0, 3.0, 3.0, 3.0, 3.0, 3.0,15.0,15.0, },			
				{ 3.0, 3.0, 7.0, 3.0, 3.0, 3.0, 3.0, 3.0,10.0, 3.0, 3.0, 2.0, 2.0, 2.0, 2.0, 2.0, 3.0, 3.0, 3.0,10.0, 3.0, 3.0,11.0,10.0, },			
				{ 3.0, 3.0,10.0, 3.0, 3.0, 3.0, 3.0, 3.0,12.0, 3.0, 2.0, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 3.0, 3.0, 3.0, 3.0, 3.0,15.0,15.0, },			
				{ 3.0, 3.0,11.0, 3.0, 3.0, 3.0, 3.0, 3.0,13.0, 3.0, 2.0, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 3.0, 3.0,10.0, 3.0, 3.0,11.0,10.0, },			
		};
	private Map<Chocolat, Variable> attractiviteChocolat;// Plus un chocolat a une forte attractivite (compare aux autres chocolats), plus ce chocolat aura une place importante dans la consommation globale de chocolat
	private Map<Chocolat, Map<IDistributeurChocolat, Double>> attractiviteDistributeur;// Pour un chocolat donne, plus l'attractivite d'un distributeur est grande (comparee a celle des autres distributeurs) plus sa part de marche sur ce chocolat sera grande
	private Variable deltaConsoMin, deltaConsoMax, conso;
	protected Map<Chocolat, Variable> stocksChocolat;
	protected Integer cryptogramme;
	protected Journal journal;
	private double distributionActuelle[];

	public ClientFinal() {
		this.conso=new Variable(getNom()+" consommation annuelle", this, 7000000);
		this.deltaConsoMin=new Variable(getNom()+" delta annuel min conso", this, 0.0);
		this.deltaConsoMax=new Variable(getNom()+" delta annuel max conso", this, 0.0);
		this.journal = new Journal(this.getNom()+" activites", this);
		this.distributionActuelle = Arrays.copyOf(DISTRIBUTIONS_ANNUELLES[0],24);
	}

	public String getNom() {
		return "CLIENTFINAL";
	}

	public String getDescription() {
		return "Client final ";
	}

	public Color getColor() {
		return new Color(231,76,60);
	}

	public void initialiser() {
		this.attractiviteDistributeur=new HashMap<Chocolat, Map<IDistributeurChocolat, Double>>();
		for (Chocolat choco : Chocolat.values()) {
			HashMap<IDistributeurChocolat, Double> attract = new HashMap<IDistributeurChocolat, Double>();
			for (IActeur acteur : Filiere.LA_FILIERE.getActeurs()) {
				if (acteur instanceof IDistributeurChocolat && ((IDistributeurChocolat)acteur).commercialise(choco)) {
					attract.put((IDistributeurChocolat)acteur, 1.0);
				}
			}
			this.attractiviteDistributeur.put(choco, attract);
		}
		this.attractiviteChocolat = new HashMap<Chocolat, Variable>();
		for (Chocolat choco : Chocolat.values()) {
			attractiviteChocolat.put(choco, new Variable(getNom()+" attractivite "+choco.name(), this, 1.0+Math.random()));
		}
	}

	public void initAttractiviteChoco(Chocolat choco, double val) {
		if (Filiere.LA_FILIERE==null || Filiere.LA_FILIERE.getEtape()<1) {
			attractiviteChocolat.get(choco).setValeur(this, val);
		} else {
			System.out.println("la methode initAttractiviteChoco de ClientFinal ne peut etre appelee qu'avant le premier step");
		}
	}
	public void setCryptogramme(Integer crypto) {
		this.cryptogramme = crypto;
	}

	private double ratioStep() {
		return this.distributionActuelle[Filiere.LA_FILIERE.getEtape()%24];
	}

	public void next() {
		// Evolution du volume de la consommation annuelle
		if (deltaConsoMin.getValeur()>deltaConsoMax.getValeur()) {
			double max = deltaConsoMin.getValeur();
			deltaConsoMin.setValeur(this,  deltaConsoMax.getValeur());
			deltaConsoMax.setValeur(this,  max);
		}
		double deltaConso = deltaConsoMin.getValeur() + (Math.random()*(deltaConsoMax.getValeur() - deltaConsoMin.getValeur()));
		conso.setValeur(this, conso.getValeur()*(1+(deltaConso/24.0)));

		journal.ajouter("delta conso = "+Journal.doubleSur(deltaConso, 4)+" ==> conso = "+Journal.doubleSur(conso.getValeur(),2));

		// Calcul du volume de la consommation a cette etape 
		double consoStep = conso.getValeur()*ratioStep();
		journal.ajouter(" le ratio a ce step est "+Journal.doubleSur(ratioStep(), 4)+" --> la consommation est de "+Journal.doubleSur(consoStep, 4));

		double totalAttractiviteChocolats = 0.0;
		for (Chocolat choco : Chocolat.values()) {
			totalAttractiviteChocolats+=attractiviteChocolat.get(choco).getValeur();
		}

		for (Chocolat choco : Chocolat.values()) {
			if (!attractiviteDistributeur.get(choco).keySet().isEmpty()) {
				journal.ajouter(" l'attractivite de "+choco.name()+" est de "+Journal.doubleSur(attractiviteChocolat.get(choco).getValeur(), 4));
				double consoStepChoco = consoStep*attractiviteChocolat.get(choco).getValeur()/totalAttractiviteChocolats;
				

				double totalAttractivite = 0.0;
				for (IDistributeurChocolat dist : this.attractiviteDistributeur.get(choco).keySet()) {
					totalAttractivite+=this.attractiviteDistributeur.get(choco).get(dist).doubleValue();
				}
				journal.ajouter(" le total de l'acttractivite des distributeur est "+Journal.doubleSur(totalAttractivite, 4));
				IDistributeurChocolat moinsCher = null;
				IDistributeurChocolat plusCher = null;
				for (IDistributeurChocolat dist : this.attractiviteDistributeur.get(choco).keySet()) {
					double quantiteDesiree = consoStepChoco*this.attractiviteDistributeur.get(choco).get(dist).doubleValue()/totalAttractivite;
					double enVente = dist.quantiteEnVente(choco);
					double quantiteAchetee = Math.min(quantiteDesiree, enVente);
					journal.ajouter("   pour "+dist.getNom()+" la quantite desiree est "+Journal.doubleSur(quantiteDesiree,4)+" et quantite en vente ="+Journal.doubleSur(enVente, 4));
					if (quantiteAchetee>0.0) {
						Filiere.LA_FILIERE.getBanque().virer(this, cryptogramme, dist, quantiteAchetee*dist.prix(choco));
						dist.vendre(this, choco, quantiteAchetee);
					}
					if (quantiteDesiree>enVente) {
						attractiviteDistributeur.get(choco).put(dist,attractiviteDistributeur.get(choco).get(dist)-0.01);
						journal.ajouter(" le client n'a pas trouve la quantite desiree. L'attractivite passe a "+Journal.doubleSur(attractiviteDistributeur.get(choco).get(dist).doubleValue(), 4));
					} else {
						attractiviteDistributeur.get(choco).put(dist,attractiviteDistributeur.get(choco).get(dist)+0.001);
						journal.ajouter(" le client a trouve la quantite desiree. L'attractivite passe a "+Journal.doubleSur(attractiviteDistributeur.get(choco).get(dist).doubleValue(), 4));
					}
					if (moinsCher==null || (moinsCher.prix(choco)>dist.prix(choco))) {
						moinsCher =dist;
					}
					if (plusCher==null || (plusCher.prix(choco)<dist.prix(choco))) {
						plusCher =dist;
					}
				}
				attractiviteDistributeur.get(choco).put(moinsCher, attractiviteDistributeur.get(choco).get(moinsCher)+0.01);
				journal.ajouter("   "+moinsCher.getNom()+" est le moins cher. Son attractivite est augmentee a "+Journal.doubleSur(attractiviteDistributeur.get(choco).get(moinsCher).doubleValue(), 4));
				attractiviteDistributeur.get(choco).put(plusCher, attractiviteDistributeur.get(choco).get(plusCher)-0.01);
				journal.ajouter("   "+plusCher.getNom()+" est le plus cher. Son attractivite est diminuee a "+Journal.doubleSur(attractiviteDistributeur.get(choco).get(plusCher).doubleValue(), 4));
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
		res.add(this.conso);
		res.add(deltaConsoMin);
		res.add(deltaConsoMax);
		return res;
	}

	public List<Variable> getParametres() {
		List<Variable> res=new ArrayList<Variable>();
		return res;
	}

	public List<Journal> getJournaux() {
		List<Journal> j= new ArrayList<Journal>();
		j.add(this.journal);
		return j;
	}

	public void notificationFaillite(IActeur acteur) {
	}

	public void notificationOperationBancaire(double montant) {
	}
}
