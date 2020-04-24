package abstraction.eq8Romu.cacaoCriee;

import java.util.List;

import abstraction.eq8Romu.produits.Feve;
import abstraction.fourni.Filiere;
import abstraction.fourni.Journal;


public class ExempleVendeurCacaoCriee extends ExempleAbsVendeurCacaoCriee implements IVendeurCacaoCriee {
	private double quantiteEnVente; // la quantite de feve que le vendeur souhaite vendre
	private double prixCourant;

	public ExempleVendeurCacaoCriee(Feve feve, double prodMin, double prodMax, double prixMin) {
		super(feve, prodMin, prodMax, prixMin);
		this.prixCourant = prixMin;
	}

	public void next() {
		super.next();
		journal.ajouter("le nombre maximum de propositions sans ventes possible lors d'une etape est "+Filiere.LA_FILIERE.getParametre("Sup.C.Criee max propositions sans vente").getValeur());
		this.quantiteEnVente=this.stockFeves.getValeur()/24;
		if (this.quantiteEnVente<LotCacaoCriee.QUANTITE_MIN) {
			if (this.stockFeves.getValeur()>LotCacaoCriee.QUANTITE_MIN) {
				this.quantiteEnVente = LotCacaoCriee.QUANTITE_MIN;
			} else {
				this.quantiteEnVente = 0.0;
			}
		}
	}

	public LotCacaoCriee getLotEnVente() {
		if (this.quantiteEnVente<LotCacaoCriee.QUANTITE_MIN) {
			this.journal.ajouter("ne propose pas de lot");
			return null;
		} else {
			this.journal.ajouter("propose un lot de "+Journal.doubleSur(this.quantiteEnVente,2)+" tonnes de "+this.feve.name());
			return new LotCacaoCriee(this, this.feve, this.quantiteEnVente, prixCourant*1.05);
		}
	}

	public void notifierAucuneProposition(LotCacaoCriee lot) {
		this.quantiteEnVente=lot.getQuantiteEnTonnes()/2;
		this.prixCourant=Math.max(this.prixMin.getValeur(), lot.getPrixMinPourUneTonne()*0.95);
		this.journal.ajouter("il n'y a pas eu de proposition -> diminution de la quantite en vente="+Journal.doubleSur(this.quantiteEnVente,2)+" et le prix courant devient "+Journal.doubleSur(prixCourant, 4));
	}

	public PropositionCriee choisir(List<PropositionCriee> propositions) {
		int max = 0;
		this.journal.ajouter("Choix parmi les propositions : ");
		this.journal.ajouter("   "+Journal.texteColore(propositions.get(0).getAcheteur(), Journal.doubleSur(propositions.get(0).getPrixPourUneTonne(), 4)));
		for (int i=1; i<propositions.size(); i++) {
			this.journal.ajouter("   "+Journal.texteColore(propositions.get(i).getAcheteur(), Journal.doubleSur(propositions.get(i).getPrixPourUneTonne(), 4)));
			if (propositions.get(i).getPrixPourUneTonne()>propositions.get(max).getPrixPourUneTonne()) {
				max=i;
			}
		}
		if (propositions.get(max).getPrixPourUneTonne()>=this.prixMin.getValeur()
			&& propositions.get(max).getPrixPourUneTonne()>=this.prixCourant*0.95) {
			this.prixCourant=this.prixCourant*1.02;// Il y a eu des acheteurs, je peux augmenter mes prix
			this.journal.ajouter("--> choix de "+Journal.texteColore(propositions.get(max).getAcheteur(), Journal.doubleSur(propositions.get(max).getPrixPourUneTonne(), 4)));
			this.journal.ajouter("--> le prix courant devient "+Journal.doubleSur(prixCourant, 4));
			return propositions.get(max);
		} else {
			this.prixCourant=Math.max(this.prixMin.getValeur(), this.prixCourant*0.95);
			this.journal.ajouter("--> pas de proposition retenue");
			this.journal.ajouter("--> le prix courant devient "+Journal.doubleSur(prixCourant, 4));
			return null;
		}
	}

	public void notifierVente(PropositionCriee proposition) {
		this.stockFeves.setValeur(this, this.stockFeves.getValeur()-proposition.getQuantiteEnTonnes());
		this.quantiteEnVente=0.0; // Cette version de vendeur s'arrete de proposer des lots a une etape des qu'il a su en vendre un.
		this.journal.ajouter("--> notificaion de la vente. On enleve "+Journal.doubleSur(proposition.getQuantiteEnTonnes(), 2)+" tonnes au stock qui passe a "+Journal.doubleSur(this.stockFeves.getValeur(), 2));
	}

}
