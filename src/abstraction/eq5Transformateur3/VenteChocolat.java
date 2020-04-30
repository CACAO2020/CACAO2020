package abstraction.eq5Transformateur3;

import abstraction.eq8Romu.produits.Chocolat;

/** @author Nicolas PIERRE  */
public class VenteChocolat {
    /**
     * Gère la vente de chocolat à la bourse
     * Elle est agrégé dans la classe Transformateur3.
     */
    private Transformateur3 acteur;
    private int tourDepuisDerniereVenteBG;
    private int tourDepuisDerniereVenteHG;
    private int tourDepuisDerniereVenteConcu;
    public VenteChocolat(Transformateur3 acteur) {
        this.acteur = acteur;
        tourDepuisDerniereVenteBG = 0;
        tourDepuisDerniereVenteHG = 0;
        tourDepuisDerniereVenteConcu = 0;
    }

    public double getOffre(Chocolat chocolat, double cours) {
        switch (chocolat) {
            case CHOCOLAT_BASSE:
                return this.offreBasse(cours);
            case CHOCOLAT_HAUTE:
                return this.offreHaute(cours, false);
            case CHOCOLAT_HAUTE_EQUITABLE:
                return this.offreHaute(cours, true);
            default:
                return this.offreMonopole(cours);
        }
    }
    
    private double offreMonopole(double cours) {
        return 0;
    }

    private double offreHaute(double cours, boolean equitable) {
        return 0;
    }

    private double offreBasse(double cours) {
        return 0;
    }


    public void livrer(Chocolat chocolat, double quantite) {
        //passer par les methodes de stock
    }
}